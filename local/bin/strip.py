# 把 HTML 去掉标签、合并空白，输出纯文本，方便用 g.py 检索
import re, sys, html
s = open(sys.argv[1], encoding='utf-8', errors='ignore').read()
s = re.sub(r'<script.*?</script>|<style.*?</style>', '', s, flags=re.S)
print(re.sub(r'\s+', ' ', html.unescape(re.sub(r'<[^>]+>', ' ', s))))
