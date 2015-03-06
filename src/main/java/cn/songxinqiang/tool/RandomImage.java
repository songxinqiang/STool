/**
 * Copyright 2014 阿信(songxinqiang@vip.qq.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.songxinqiang.tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 验证码图片生成，可指定图片的尺寸和干扰线等参数
 * 
 * @author 宋信强--2014年9月10日--songxinqiang@vip.qq.com
 *
 */
public class RandomImage {

	private Random random = new Random();

	private Font font = new Font("Fixedsys", Font.CENTER_BASELINE, 18);

	/**
	 * 根据指定的字符和大小生成随机验证码图片
	 * 
	 * @param code
	 *            需要绘制到图片上的字符数组
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @param line
	 *            干扰线数量
	 * @return 图片的输入流
	 */
	public ByteArrayInputStream getImage(char[] code, int width, int height,
			int line) {
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i < line; i++) {
			drowLine(g, width, height);
		}
		// 绘制随机字符
		for (int i = 0; i < code.length; i++) {
			drowChar(g, width / code.length * i, random.nextInt(height / 3)
					+ height / 3, code[i]);
		}
		g.dispose();

		return convertImageToStream(image);
	}

	/**
	 * 将BufferedImage转换成ByteArrayInputStream
	 * 
	 * @param image
	 *            图片
	 * @return ByteArrayInputStream 流
	 */
	private ByteArrayInputStream convertImageToStream(BufferedImage image) {
		ByteArrayInputStream inputStream = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "JPEG", bos);
			byte[] bts = bos.toByteArray();
			inputStream = new ByteArrayInputStream(bts);
		} catch (IOException e1) {
		}
		return inputStream;
	}

	/*
	 * 获得颜色
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/*
	 * 绘制字符
	 */
	private char drowChar(Graphics g, int x, int y, char code) {
		g.setFont(font);
		g.setColor(getRandColor(10, 200));
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(code + "", x, y);
		return code;
	}

	/*
	 * 绘制干扰线
	 */
	private void drowLine(Graphics g, int width, int height) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(width / 2);
		int yl = random.nextInt(height / 2);
		g.drawLine(x, y, x + xl, y + yl);
	}
}
