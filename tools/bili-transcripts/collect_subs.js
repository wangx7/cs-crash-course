// 在已登录 B 站的浏览器页面（www.bilibili.com 任意页）里运行，收集每一集"可信"的字幕地址。
// 用法：把 BVID 改成目标合集，整段粘进控制台（或 Claude 内置浏览器的 javascript_tool）运行，
// 等 window.__ver 不为 null 后，执行文件末尾注释里的两行导出 pages.json / ver.json。
//
// 为什么要"验证"：B 站播放器接口有两个毛病——
//   1. 偶尔返回别的分 P 的字幕；
//   2. 不少分 P 挂了一个只有一行空白的占位 zh-CN 字幕，把 AI 字幕挡住了。
// 所以只接受"最后一条字幕的结束时间 ≥ 0.85 × 视频时长、且 ≤ 时长 + 5 秒"的轨道，
// 并且同一个字幕文件不允许被两集重复使用。找不到可信轨道的集数留空，交给 transcribe.py 本地转写。
// （需要登录：未登录时接口不返回字幕列表。）

window.BVID = 'BV17F411T7Ao';
window.__ver = null; window.__vprog = 0;
(async () => {
  const v = await fetch(`https://api.bilibili.com/x/web-interface/view?bvid=${BVID}`, { credentials: 'include' }).then(r => r.json());
  window.__pages = v.data.pages; // [{page, cid, part, duration, ...}]
  const aid = v.data.aid, out = [], used = new Set(), checked = new Map();
  const sleep = ms => new Promise(r => setTimeout(r, ms));
  async function lastTo(path) {
    const key = path.split('?')[0];
    if (checked.has(key)) return checked.get(key);
    try {
      const j = await fetch('https://aisubtitle.hdslb.com/bfs/' + path).then(r => r.json());
      const t = j.body?.length ? j.body.at(-1).to : 0;
      checked.set(key, t); return t;
    } catch (e) { return -1; }
  }
  for (const pg of v.data.pages) {
    let best = null; const seen = {};
    // 两个接口轮流多问几次：同一集每次返回的字幕列表可能不一样
    for (let t = 0; t < 8 && !(best && best.lan === 'zh-CN'); t++) {
      const ep = t % 2 ? 'v2' : 'wbi/v2';
      let subs = [];
      try {
        const p = await fetch(`https://api.bilibili.com/x/player/${ep}?aid=${aid}&cid=${pg.cid}`, { credentials: 'include' }).then(r => r.json());
        subs = p.data?.subtitle?.subtitles || [];
      } catch (e) {}
      for (const s of subs) {
        if (!s.subtitle_url) continue;
        const path = s.subtitle_url.replace('//aisubtitle.hdslb.com/bfs/', ''), key = path.split('?')[0];
        if (used.has(key) || seen[key]) continue; seen[key] = 1;
        const lt = await lastTo(path);
        const ok = lt >= pg.duration * 0.85 && lt <= pg.duration + 5;
        if (ok && (!best || (s.lan === 'zh-CN' && best.lan !== 'zh-CN'))) best = { lan: s.lan, path, lt };
      }
      if (best && t >= 1) break;
      await sleep(250);
    }
    if (best) used.add(best.path.split('?')[0]);
    out.push([pg.page, best ? best.lan : '', best ? best.path : '', best ? Math.round(best.lt) : 0, pg.duration]);
    window.__vprog = pg.page;
  }
  window.__ver = out;
})();
'started';

// 跑完后（window.__vprog 等于总集数、window.__ver 有值）分别执行，把输出存成文件：
//   JSON.stringify(window.__pages.map(p => ({ page: p.page, part: p.part, cid: p.cid, duration: p.duration })))   → pages.json
//   JSON.stringify(window.__ver.filter(x => x[2]).map(x => [x[0], x[1], x[2], x[3]]))                              → ver.json
// 浏览器页面发不出到 localhost 的请求（CSP），所以只能经控制台 / javascript_tool 的返回值把 JSON 拿出来。
// auth_key 有时效（约几小时），拿到 ver.json 后尽快跑 dl_subs.py。
