/* 计算机科学速成课 · 图文版 —— 各集共用脚本 */
const NS = 'http://www.w3.org/2000/svg';
const $ = (s, r = document) => r.querySelector(s);
const $$ = (s, r = document) => Array.from(r.querySelectorAll(s));

function el(tag, attrs, parent) {
  const e = document.createElementNS(NS, tag);
  for (const k in (attrs || {})) e.setAttribute(k, attrs[k]);
  if (parent) parent.appendChild(e);
  return e;
}
function txt(parent, x, y, s, cls) {
  const t = el('text', { x, y, class: cls || 't-sub' }, parent);
  t.textContent = s;
  return t;
}

const ccStore = {
  get(k, d) { try { const v = localStorage.getItem(k); return v == null ? d : JSON.parse(v); } catch (e) { return d; } },
  set(k, v) { try { localStorage.setItem(k, JSON.stringify(v)); } catch (e) {} },
};

document.addEventListener('DOMContentLoaded', () => {
  // 目录高亮
  const links = $$('nav.toc a');
  const secs = links.map(a => document.getElementById(a.getAttribute('href').slice(1))).filter(Boolean);
  if (secs.length && 'IntersectionObserver' in window) {
    const io = new IntersectionObserver(entries => {
      entries.forEach(en => {
        if (!en.isIntersecting) return;
        links.forEach(a => {
          const on = a.getAttribute('href') === '#' + en.target.id;
          a.classList.toggle('active', on);
          if (on && window.innerWidth <= 860) a.scrollIntoView({ inline: 'center', block: 'nearest' });
        });
      });
    }, { rootMargin: '-30% 0px -60% 0px' });
    secs.forEach(s => io.observe(s));
  }

  // 自测题：点一下显示参考答案
  $$('.q').forEach(q => {
    const b = $('button', q);
    if (b) b.addEventListener('click', () => {
      q.classList.toggle('open');
      b.textContent = q.classList.contains('open') ? '收起' : '看参考答案';
    });
  });

  // 读完打卡（目录页会显示）
  const done = $('#mark-read');
  if (done) {
    const n = +document.body.dataset.ep;
    const read = new Set(ccStore.get('cc-read', []));
    const paint = () => { done.textContent = read.has(n) ? '✓ 已读完这一集' : '读完了，打个卡'; done.classList.toggle('primary', !read.has(n)); };
    done.addEventListener('click', () => {
      read.has(n) ? read.delete(n) : read.add(n);
      ccStore.set('cc-read', [...read].sort((a, b) => a - b));
      paint();
    });
    paint();
  }
});

/* ---------- 逻辑门 / 晶体管电路绘图（第 3 集起复用） ---------- */
function wire(svg, d, sig) {
  const a = { d, class: 'wire' };
  if (sig) a['data-sig'] = sig;
  return el('path', a, svg);
}
function dot(svg, x, y, sig) {
  return el('circle', { cx: x, cy: y, r: 4.5, class: 'dot', 'data-sig': sig }, svg);
}
// 门占据 x..x+50、y..y+40；两输入在 y+12 / y+28，单输入在 y+20，输出在 (x+50, y+20)
function gatePath(type, x, y) {
  switch (type) {
    case 'AND': return `M${x},${y} H${x + 25} A20,20 0 0 1 ${x + 25},${y + 40} H${x} Z`;
    case 'OR': return `M${x},${y} Q${x + 15},${y + 20} ${x},${y + 40} Q${x + 35},${y + 40} ${x + 50},${y + 20} Q${x + 35},${y} ${x},${y} Z`;
    case 'XOR': return `M${x + 8},${y} Q${x + 23},${y + 20} ${x + 8},${y + 40} Q${x + 38},${y + 40} ${x + 50},${y + 20} Q${x + 38},${y} ${x + 8},${y} Z`;
    case 'NOT': return `M${x},${y + 4} L${x + 38},${y + 20} L${x},${y + 36} Z`;
  }
}
function gate(svg, type, x, y, sig, label) {
  const g = el('g', { class: 'gate', 'data-sig': sig }, svg);
  el('path', { d: gatePath(type, x, y), class: 'gate-body' }, g);
  if (type === 'XOR') el('path', { d: `M${x},${y} Q${x + 15},${y + 20} ${x},${y + 40}`, class: 'gate-line' }, g);
  if (type === 'AND') el('line', { x1: x + 45, y1: y + 20, x2: x + 50, y2: y + 20, class: 'gate-line' }, g);
  if (type === 'NOT') el('circle', { cx: x + 43, cy: y + 20, r: 5, class: 'gate-body' }, g);
  if (label) txt(g, type === 'NOT' ? x + 14 : (type === 'AND' ? x + 21 : x + 25), y + 24, label, 'gate-label');
  return g;
}
function inNode(svg, x, y, sig, label, onClick) {
  const g = el('g', { class: 'in-node', 'data-sig': sig, tabindex: '0', role: 'button', 'aria-label': '切换输入 ' + label }, svg);
  el('circle', { cx: x, cy: y, r: 15 }, g);
  txt(g, x, y + 5, '0', 'in-val');
  txt(g, x, y - 22, label, 'node-label');
  const fire = e => { e.preventDefault(); onClick(); };
  g.addEventListener('click', fire);
  g.addEventListener('keydown', e => { if (e.key === 'Enter' || e.key === ' ') fire(e); });
  return g;
}
function lampNode(svg, x, y, sig, label, below) {
  const g = el('g', { class: 'lamp-node', 'data-sig': sig }, svg);
  el('circle', { cx: x, cy: y, r: 15 }, g);
  txt(g, x, y + 5, '0', 'lamp-val');
  txt(g, x, below ? y + 36 : y - 22, label, 'node-label');
  return g;
}
// 晶体管符号：上电极 (x,y)，下电极 (x,y+60)，控制线从左边 (x-34,y+30) 接入；side='R' 时从右边 (x+34,y+30) 接入
function transistor(svg, x, y, sig, label, side) {
  const g = el('g', { class: 'tr', 'data-sig': sig }, svg);
  const gx = side === 'R' ? x + 16 : x - 16;
  el('rect', { x: x - 7, y: y + 8, width: 14, height: 44, rx: 3, class: 'tr-ch' }, g);
  el('line', { x1: gx, y1: y + 12, x2: gx, y2: y + 48, class: 'tr-gate' }, g);
  el('path', { d: `M${x},${y} V${y + 8} M${x},${y + 52} V${y + 60}`, class: 'gate-line' }, g);
  if (label) txt(g, side === 'R' ? x - 14 : x + 14, y + 34, label, side === 'R' ? 't-sub end' : 't-sub');
  return g;
}
function ground(svg, x, y) {
  el('path', { d: `M${x - 14},${y} H${x + 14} M${x - 9},${y + 6} H${x + 9} M${x - 4},${y + 12} H${x + 4}`, class: 'ground' }, svg);
}
function setSignals(svg, sig) {
  svg.querySelectorAll('[data-sig]').forEach(e => {
    const k = e.getAttribute('data-sig');
    if (!(k in sig)) return;
    const v = !!sig[k];
    e.classList.toggle('on', v);
    for (const c of e.children) {
      if (c.classList && (c.classList.contains('in-val') || c.classList.contains('lamp-val'))) c.textContent = v ? '1' : '0';
    }
  });
}
