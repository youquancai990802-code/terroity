#!/bin/bash

# 尋找目錄下的第一個 .jar 檔，若有指定參數則優先使用參數
JAR_FILE="${1:-$(ls *.jar 2>/dev/null | head -n 1)}"

if [ -z "$JAR_FILE" ]; then
    echo "錯誤：找不到任何 .jar 檔案！"
    echo "用法: ./decompile.sh [檔名.jar]"
    exit 1
fi

OUTPUT_DIR="decompiled_src"
CFR_JAR="cfr.jar"
CFR_URL="https://www.benf.org/other/cfr/cfr-0.152.jar"

if [ ! -f "$CFR_JAR" ]; then
    echo "正在下載 CFR 反編譯器..."
    curl -sL "$CFR_URL" -o "$CFR_JAR"
fi

echo "=========================================="
echo "正在反編譯: $JAR_FILE"
echo "=========================================="
mkdir -p "$OUTPUT_DIR"

java -jar "$CFR_JAR" "$JAR_FILE" --outputdir "$OUTPUT_DIR"

if [ $? -eq 0 ]; then
    echo "=========================================="
    echo "完成！原始碼已匯出至: $OUTPUT_DIR"
    echo "=========================================="
else
    echo "反編譯過程發生錯誤。"
    exit 1
fi
