#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
APK下载二维码生成器
生成包含APK下载链接的二维码
"""

import qrcode
from PIL import Image, ImageDraw, ImageFont
import os

def generate_qr_code():
    """生成APK下载二维码"""
    
    # APK下载链接 - 使用正确的raw.githubusercontent.com格式
    download_url = "https://raw.githubusercontent.com/JQ2020/market-app/main/apk/app-debug.apk"
    
    # 创建二维码
    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )
    
    qr.add_data(download_url)
    qr.make(fit=True)
    
    # 生成二维码图片
    qr_img = qr.make_image(fill_color="black", back_color="white")
    
    # 创建带标题的图片
    img_width = 400
    img_height = 500
    
    # 创建白色背景
    img = Image.new('RGB', (img_width, img_height), 'white')
    draw = ImageDraw.Draw(img)
    
    # 调整二维码大小并居中
    qr_size = 300
    qr_img = qr_img.resize((qr_size, qr_size))
    qr_x = (img_width - qr_size) // 2
    qr_y = 50
    
    # 粘贴二维码
    img.paste(qr_img, (qr_x, qr_y))
    
    # 添加标题文字
    try:
        # 尝试使用系统字体
        title_font = ImageFont.truetype("/System/Library/Fonts/PingFang.ttc", 24)
        subtitle_font = ImageFont.truetype("/System/Library/Fonts/PingFang.ttc", 16)
    except:
        # 如果系统字体不可用，使用默认字体
        title_font = ImageFont.load_default()
        subtitle_font = ImageFont.load_default()
    
    # 标题
    title = "📱 Android 应用商店"
    title_bbox = draw.textbbox((0, 0), title, font=title_font)
    title_width = title_bbox[2] - title_bbox[0]
    title_x = (img_width - title_width) // 2
    draw.text((title_x, 15), title, fill="black", font=title_font)
    
    # 副标题
    subtitle = "扫码下载 APK 文件"
    subtitle_bbox = draw.textbbox((0, 0), subtitle, font=subtitle_font)
    subtitle_width = subtitle_bbox[2] - subtitle_bbox[0]
    subtitle_x = (img_width - subtitle_width) // 2
    draw.text((subtitle_x, qr_y + qr_size + 20), subtitle, fill="gray", font=subtitle_font)
    
    # 版本信息
    version_info = "版本: v1.0.0 | 大小: ~19MB"
    version_bbox = draw.textbbox((0, 0), version_info, font=subtitle_font)
    version_width = version_bbox[2] - version_bbox[0]
    version_x = (img_width - version_width) // 2
    draw.text((version_x, qr_y + qr_size + 45), version_info, fill="gray", font=subtitle_font)
    
    # 保存图片
    output_path = "qr_code_download.png"
    img.save(output_path, "PNG", quality=95)
    
    print(f"✅ 二维码已生成: {output_path}")
    print(f"🔗 下载链接: {download_url}")
    print(f"📱 应用信息:")
    print(f"   - 名称: Android 应用商店")
    print(f"   - 版本: v1.0.0")
    print(f"   - 大小: ~19MB")
    print(f"   - 架构: MVVM + Jetpack Compose")
    print(f"   - 支持: Android 7.0+")

if __name__ == "__main__":
    generate_qr_code() 