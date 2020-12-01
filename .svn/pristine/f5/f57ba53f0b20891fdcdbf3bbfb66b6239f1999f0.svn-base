package com.zqkj.utils.captcha;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.core.util.ImageUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;

public class LineCaptcha extends AbstractCaptcha{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LineCaptcha(int width, int height) {
		this(width, height, 5, 250);
	}

	public LineCaptcha(int width, int height, int codeCount, int interfereCount) {
		super(width, height, codeCount, interfereCount);
	}
	
	public BufferedImage createImage(String code) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		ThreadLocalRandom random = RandomUtil.getRandom();
		Graphics2D g = ImageUtil.createGraphics(image, ObjectUtil.defaultIfNull(this.background, Color.WHITE));

		drawInterfere(g, random);

		g.setFont(this.font);
		FontMetrics metrics = g.getFontMetrics();
		int minY = metrics.getAscent() - metrics.getLeading() - metrics.getDescent();
		int len = this.generator.getLength();
		int charWidth = (int) (width * 0.8 / len);
		int marginLeft = (int) (width * 0.05);
		for (int i = 0; i < len; i++) {
			g.setColor(ImageUtil.randomColor(random));
			g.drawString(String.valueOf(code.charAt(i)), i * charWidth + marginLeft, RandomUtil.randomInt(minY, this.height));
		}
		return image;
	}

	private void drawInterfere(Graphics2D g, ThreadLocalRandom random) {
		for (int i = 0; i < this.interfereCount; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs + random.nextInt(width / 8);
			int ye = ys + random.nextInt(height / 8);
			g.setColor(ImageUtil.randomColor(random));
			g.drawLine(xs, ys, xe, ye);
		}
	}
}
