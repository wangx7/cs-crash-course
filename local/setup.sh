#!/bin/bash
# 在新电脑上重建写课用的本地资料（macOS）。可以重复运行，已有的会跳过。
#   bash local/setup.sh
# 会做四件事：
#   1. 没有 JDK 8 就装 Amazon Corretto 8 到 ~/Library/Java/JavaVirtualMachines（不需要 sudo，不改默认 java）
#   2. 克隆 SpringBlade v3.7.1 到 local/springblade-3.7.1
#   3. 把 IDEA 中文语言包解开到 local/idea-zh
#   4. 按 local/refs/SOURCES.tsv 下载参考文档，并生成去掉标签的 .txt 方便检索
# 这三样是别人的版权材料，只放本地，不提交（见 .gitignore）。
set -euo pipefail
L="$(cd "$(dirname "$0")" && pwd)"

# 1. JDK 8
if /usr/libexec/java_home -v 1.8 >/dev/null 2>&1; then
  echo "JDK 8 已有：$(/usr/libexec/java_home -v 1.8)"
else
  case "$(uname -m)" in arm64) A=aarch64 ;; *) A=x64 ;; esac
  F="amazon-corretto-8-$A-macos-jdk.tar.gz"
  T="$(mktemp -d)"
  echo "下载 $F …"
  curl -fSL -o "$T/$F" "https://corretto.aws/downloads/latest/$F"
  WANT="$(curl -fsSL "https://corretto.aws/downloads/latest_checksum/$F")"
  GOT="$(md5 -q "$T/$F")"
  [ "$WANT" = "$GOT" ] || { echo "校验和不一致：$GOT ≠ $WANT"; exit 1; }
  tar -xzf "$T/$F" -C "$T"
  D="$HOME/Library/Java/JavaVirtualMachines"; mkdir -p "$D"
  V="$("$T"/amazon-corretto-8.jdk/Contents/Home/bin/java -version 2>&1 | sed -n 's/.*version "\(.*\)".*/\1/p')"
  mv "$T/amazon-corretto-8.jdk" "$D/corretto-$V"
  rm -rf "$T"
  echo "JDK 8 装好了：$D/corretto-$V"
fi

# 2. SpringBlade
if [ -d "$L/springblade-3.7.1/.git" ]; then echo "SpringBlade 已有"
else git -c advice.detachedHead=false clone -q --depth 1 --branch v3.7.1 https://github.com/chillzhuang/SpringBlade "$L/springblade-3.7.1" && echo "SpringBlade 克隆好了"; fi

# 3. IDEA 中文语言包（IDEA 升级后删掉 local/idea-zh 再运行一次）
J="/Applications/IntelliJ IDEA.app/Contents/plugins/localization-zh/lib/localization-zh.jar"
if [ -d "$L/idea-zh/messages" ]; then echo "IDEA 语言包已解开"
elif [ -f "$J" ]; then mkdir -p "$L/idea-zh" && unzip -q -o "$J" -d "$L/idea-zh" && echo "IDEA 语言包解开了"
else echo "没找到 $J，跳过（先装 IDEA 和中文语言包）"; fi

# 4. 参考文档
grep -v '^#' "$L/refs/SOURCES.tsv" | while IFS=$'\t' read -r name url _; do
  [ -n "$name" ] || continue
  if [ -s "$L/refs/$name.html" ]; then continue; fi
  if curl -fsSL -m 90 -o "$L/refs/$name.html" "$url"; then
    python3 -I "$L/bin/strip.py" "$L/refs/$name.html" > "$L/refs/$name.txt"
    echo "下载了 $name"
  else echo "下载失败：$url"; rm -f "$L/refs/$name.html"; fi
done
echo "完成。"
