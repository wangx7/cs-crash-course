/* Dijkstra 专题：共用脚本 */
const NS = 'http://www.w3.org/2000/svg';
const $ = (s, r = document) => r.querySelector(s);
const $$ = (s, r = document) => Array.from(r.querySelectorAll(s));
const sleep = ms => new Promise(r => setTimeout(r, ms));

function el(tag, attrs, parent) {
  const e = document.createElementNS(NS, tag);
  for (const k in (attrs || {})) e.setAttribute(k, attrs[k]);
  if (parent) parent.appendChild(e);
  return e;
}
function txt(parent, x, y, s, cls) {
  const t = el('text', { x, y, class: cls || 'svg-text' }, parent);
  t.textContent = s;
  return t;
}

/* ---------- 示例地图：和第 13 集同一张（单位：周） ---------- */
const GOT = {
  nodes: {
    HG: ['高庭', 170, 360], KL: ['君临', 500, 320], RR: ['奔流城', 260, 210],
    TR: ['三叉戟河', 440, 180], PY: ['派克城', 90, 150], WF: ['临冬城', 360, 50],
  },
  edges: [['HG', 'KL', 8], ['HG', 'RR', 10], ['HG', 'PY', 14], ['KL', 'TR', 5], ['KL', 'RR', 25],
          ['RR', 'TR', 2], ['RR', 'PY', 3], ['TR', 'WF', 10], ['PY', 'WF', 18]],
  src: 'HG', dst: 'WF', view: '0 0 600 420',
};

/* ---------- 画图：返回一个可以反复上色的句柄 ---------- */
function drawGraph(svg, G, opt = {}) {
  const N = G.nodes, r = opt.r || 24;
  svg.setAttribute('viewBox', G.view || '0 0 600 420');
  const gE = el('g', {}, svg), gW = el('g', {}, svg), gN = el('g', {}, svg);
  const edge = {}, node = {};
  if (G.directed) {
    const id = 'ah' + Math.random().toString(36).slice(2, 7);
    const defs = el('defs', {}, svg);
    const m = el('marker', { id, viewBox: '0 0 10 10', refX: 10, refY: 5, markerWidth: 7, markerHeight: 7, orient: 'auto-start-reverse' }, defs);
    el('path', { d: 'M0,0 L10,5 L0,10 z', fill: 'var(--muted)' }, m);
    G._marker = id;
  }
  G.edges.forEach(([a, b, w]) => {
    let [, x1, y1] = N[a], [, x2, y2] = N[b];
    const attrs = { x1, y1, x2, y2, class: 'edge' };
    if (G.directed) {
      const dx = x2 - x1, dy = y2 - y1, L = Math.hypot(dx, dy);
      attrs.x2 = x2 - dx / L * (r + 3); attrs.y2 = y2 - dy / L * (r + 3);
      attrs['marker-end'] = `url(#${G._marker})`;
    }
    const ln = el('line', attrs, gE);
    edge[a + '|' + b] = ln;
    if (!G.directed) edge[b + '|' + a] = ln;
    if (opt.noWeight) return;
    const mx = (x1 + x2) / 2, my = (y1 + y2) / 2;
    el('rect', { x: mx - 15, y: my - 11, width: 30, height: 21, rx: 6, class: 'ew-bg' }, gW);
    txt(gW, mx, my + 5, String(w), 'ew' + (w < 0 ? ' neg' : ''));
  });
  Object.entries(N).forEach(([k, [nm, x, y]]) => {
    const g = el('g', { class: 'node', 'data-id': k }, gN);
    el('circle', { cx: x, cy: y, r }, g);
    const c = txt(g, x, y + 5, opt.blank ? '' : '?', 'cost');
    const below = opt.labelAbove && opt.labelAbove.includes(k) ? y - r - 10 : y + r + 18;
    txt(g, x, below, nm, 'nm');
    node[k] = { g, c };
  });
  const api = {
    edge: (a, b) => edge[a + '|' + b],
    node: k => node[k],
    ids: Object.keys(N),
    paint({ cost = {}, cls = {}, ecls = {} } = {}) {
      Object.entries(node).forEach(([k, o]) => {
        if (!opt.blank) o.c.textContent = cost[k] === undefined || cost[k] === Infinity ? (opt.inf ? '∞' : '?') : cost[k];
        o.g.setAttribute('class', 'node' + (cls[k] ? ' ' + cls[k] : '') + (opt.clickable ? ' click' : ''));
      });
      Object.values(edge).forEach(e => e.setAttribute('class', 'edge'));
      Object.entries(ecls).forEach(([ab, c]) => { const [a, b] = ab.split('|'); const e = api.edge(a, b); if (e) e.setAttribute('class', 'edge ' + c); });
    },
    onClick(fn) { Object.entries(node).forEach(([k, o]) => o.g.addEventListener('click', () => fn(k))); },
  };
  return api;
}

/* ---------- Dijkstra 的逐步记录（原版：每轮扫描找最小） ---------- */
function neighbors(G, u) {
  const out = [];
  G.edges.forEach(([a, b, w]) => {
    if (a === u) out.push([b, w]);
    else if (!G.directed && b === u) out.push([a, w]);
  });
  return out;
}
function routeOf(prev, v, N) {
  const r = [N ? N[v][0] : v];
  while (prev[v] !== undefined) { v = prev[v]; r.unshift(N ? N[v][0] : v); }
  return r.join(' → ');
}
function dijkstraTrace(G, opt = {}) {
  const N = G.nodes, ids = Object.keys(N), src = opt.src || G.src, dst = opt.dst === undefined ? G.dst : opt.dst;
  const dist = {}, prev = {}, done = new Set();
  ids.forEach(k => { dist[k] = Infinity; });
  dist[src] = 0;
  const S = [];
  const nm = k => N[k][0];
  const snap = o => S.push({ dist: { ...dist }, prev: { ...prev }, done: new Set(done), ...o });
  snap({ type: 'init', text: `起点<b>${nm(src)}</b>的成本是 0；其他地方一律先记成 ∞（"还不知道怎么去"）。` });
  for (let round = 1; ; round++) {
    let u = null;
    ids.forEach(k => { if (!done.has(k) && dist[k] < Infinity && (u === null || dist[k] < dist[u])) u = k; });
    if (u === null) { snap({ type: 'end', text: '再也没有"已知但未确定"的地方了，算法结束。' }); break; }
    const cands = ids.filter(k => !done.has(k) && dist[k] < Infinity);
    done.add(u);
    snap({ type: 'pick', u, round, cands, text: `第 ${round} 轮 · ① 在候选者里挑成本最小的：${cands.map(k => `${nm(k)} ${dist[k]}`).join('、')} → 选 <b>${nm(u)}（${dist[u]}）</b>。它的成本从此<b>确定</b>，不会再变。` });
    if (u === dst && opt.stopAtDst !== false) {
      snap({ type: 'finish', u, round, text: `终点<b>${nm(u)}</b>已经被确定：最短成本 <b>${dist[u]}</b>，路线 ${routeOf(prev, u, N)}。可以停了。` });
      break;
    }
    const nb = neighbors(G, u);
    if (!nb.some(([v]) => !done.has(v))) snap({ type: 'relax-none', u, round, text: `② ${nm(u)}的邻居都已经确定了，没有可更新的。` });
    nb.forEach(([v, w]) => {
      if (done.has(v)) return;
      const nc = dist[u] + w, old = dist[v];
      let text, updated = false;
      if (nc < old) {
        updated = true;
        text = old === Infinity
          ? `② 松弛 ${nm(u)} → ${nm(v)}：${dist[u]} + ${w} = <b>${nc}</b> &lt; ∞，第一次发现${nm(v)}，记下成本 ${nc}、来路${nm(u)}。`
          : `② 松弛 ${nm(u)} → ${nm(v)}：${dist[u]} + ${w} = <b>${nc}</b> &lt; 原来的 ${old}，<b>更新</b>：成本改成 ${nc}，来路改成${nm(u)}。`;
        dist[v] = nc; prev[v] = u;
      } else text = `② 松弛 ${nm(u)} → ${nm(v)}：${dist[u]} + ${w} = ${nc}，不比原来的 ${old} 小，什么也不改。`;
      snap({ type: 'relax', u, v, w, old, nc, updated, round, text });
    });
  }
  return S;
}
/* 把一步记录画到图上 */
function paintStep(api, s, G) {
  const cls = {}, ecls = {};
  api.ids.forEach(k => {
    if (s.done.has(k)) cls[k] = 'done';
    else if (s.dist[k] < Infinity) cls[k] = 'cand';
  });
  Object.entries(s.prev).forEach(([v, u]) => { ecls[u + '|' + v] = 'tree'; });
  if (s.u) cls[s.u] = 'cur';
  if (s.type === 'relax') ecls[s.u + '|' + s.v] = 'try';
  if (s.type === 'finish' || s.path) {
    let v = s.u || G.dst;
    while (s.prev[v] !== undefined) { ecls[s.prev[v] + '|' + v] = 'path'; v = s.prev[v]; }
  }
  api.paint({ cost: s.dist, cls, ecls });
}

/* ---------- 上一步 / 下一步 ---------- */
function stepper(prefix, steps, paint) {
  let k = 0;
  const prev = $(`#${prefix}-prev`), next = $(`#${prefix}-next`), reset = $(`#${prefix}-reset`);
  const go = () => {
    paint(steps[k], k);
    if (prev) prev.disabled = k === 0;
    if (next) next.disabled = k === steps.length - 1;
  };
  if (prev) prev.onclick = () => { if (k > 0) { k--; go(); } };
  if (next) next.onclick = () => { if (k < steps.length - 1) { k++; go(); } };
  if (reset) reset.onclick = () => { k = 0; go(); };
  go();
  return { set(s) { steps = s; k = 0; go(); }, get k() { return k; } };
}

/* ---------- 计分小测验（同参考页面） ---------- */
function quiz(sel, Q, msgs) {
  const box = $(sel);
  let score = 0, done = 0;
  const s = document.createElement('div');
  s.className = 'score';
  Q.forEach((it, qi) => {
    const d = document.createElement('div');
    d.className = 'q';
    d.innerHTML = `<div class="qt">${qi + 1}. ${it.q}</div><div class="opts"></div><div class="ex"></div>`;
    const opts = $('.opts', d);
    it.o.forEach((o, oi) => {
      const b = document.createElement('button');
      b.className = 'btn';
      b.innerHTML = o;
      b.addEventListener('click', () => {
        if (d.classList.contains('done')) return;
        d.classList.add('done');
        done++;
        const right = oi === it.a;
        if (right) score++;
        b.classList.add(right ? 'right' : 'wrong');
        $$('button', opts)[it.a].classList.add('right');
        $('.ex', d).innerHTML = (right ? '✓ 答对了。' : '✗ 不对。') + it.e;
        update();
      });
      opts.appendChild(b);
    });
    box.appendChild(d);
  });
  box.appendChild(s);
  function update() {
    if (done < Q.length) { s.textContent = `已答 ${done} / ${Q.length}`; return; }
    s.textContent = `得分：${score} / ${Q.length} ` + (score === Q.length ? msgs[0] : score >= Q.length - 2 ? msgs[1] : msgs[2]);
  }
  update();
}

/* ---------- 目录高亮 ---------- */
document.addEventListener('DOMContentLoaded', () => {
  const links = $$('nav.toc a');
  if (!links.length || !('IntersectionObserver' in window)) return;
  const map = {};
  links.forEach(a => { map[a.getAttribute('href').slice(1)] = a; });
  const obs = new IntersectionObserver(entries => {
    entries.forEach(e => {
      if (!e.isIntersecting) return;
      links.forEach(a => a.classList.remove('active'));
      const a = map[e.target.id];
      if (a) {
        a.classList.add('active');
        const nav = a.closest('nav');
        if (nav && nav.scrollWidth > nav.clientWidth) nav.scrollLeft = a.offsetLeft - 20;
      }
    });
  }, { rootMargin: '-30% 0px -65% 0px' });
  $$('section.ch').forEach(s => obs.observe(s));
});
