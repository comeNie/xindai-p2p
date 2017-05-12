package com.qfw.common.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ImageCode extends HttpServlet {

	private static final long serialVersionUID = 8139480674149938230L;

	public void init() throws ServletException {
		super.init();// 锟斤拷始锟斤拷锟斤拷锟斤拷
	}

	// 锟斤拷锟斤拷图锟斤拷锟斤拷证锟斤拷锟斤拷锟街凤拷锟斤拷锟斤拷锟酵达拷小
	private Font mFont = new Font("Arial Black", Font.PLAIN, 16);

	// 锟斤拷锟斤拷锟斤拷锟斤拷色
	public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		// 锟斤拷锟斤拷锟缴拷锟絩bg
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);

		return new Color(r, g, b);
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 锟斤拷止锟斤拷傻锟揭筹拷锟斤拷锟斤拷荼锟斤拷锟斤拷妫拷锟街っ匡拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟街わ拷锟�
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		// 指锟斤拷图锟斤拷锟斤拷证锟斤拷图片锟侥达拷小
		int width = 72, height = 24;

		// 锟斤拷锟揭伙拷锟斤拷锟酵计�
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		// 锟斤拷图片锟叫伙拷锟斤拷锟斤拷锟斤拷
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(1, 1, width - 1, height - 1);
		g.setColor(new Color(200, 200, 200));
		g.drawRect(0, 0, width - 1, height - 1);
		g.setFont(mFont);

		// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷图片锟斤拷锟斤拷4锟斤拷锟斤拷锟斤拷锟�}锟竭斤拷锟芥画锟斤拷
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int x1 = random.nextInt(6) + 1;
			int y1 = random.nextInt(12) + 1;
			g.drawLine(x, y, x + x1, y + y1);
		}

		// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷图片锟斤拷锟斤拷4锟斤拷锟斤拷锟斤拷遥锟絵锟竭斤拷锟芥画锟斤拷
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int x1 = random.nextInt(6) + 1;
			int y1 = random.nextInt(12) + 1;
			g.drawLine(x, y, x - x1, y - y1);
		}

		// 锟矫憋拷锟斤拷锟节憋拷锟斤拷系统锟斤拷傻锟�锟斤拷锟斤拷址锟�
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			// 取锟斤拷一锟斤拷锟斤拷锟斤拷址锟�
			String tmp = getRandomChar();
			sRand += tmp;

			// 锟斤拷系统锟斤拷傻锟斤拷锟斤拷锟街凤拷锟斤拷拥锟酵硷拷锟斤拷锟街わ拷锟酵计拷锟�
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(tmp, 15 * i + 10, 15);
		}

		// 取锟斤拷锟矫伙拷session
		HttpSession session = request.getSession(true);

		// 锟斤拷系统锟斤拷傻锟酵硷拷锟斤拷锟街わ拷锟斤拷锟接碉拷锟矫伙拷session锟斤拷
		session.setAttribute("rand", sRand);
		g.dispose();// ??????

		// 锟斤拷锟酵硷拷锟斤拷锟街わ拷锟酵计�
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	// 锟斤拷锟斤拷锟斤拷锟街凤拷姆锟斤拷锟�
	public String getRandomChar() {
		int rand = (int) Math.round(Math.random() * 2);
		long itmp = 0;
		char ctmp = '\u0000';
		// 锟斤拷锟絩and锟斤拷值4锟斤拷锟斤拷锟斤拷锟揭伙拷锟斤拷写锟斤拷母锟斤拷小写锟斤拷母锟斤拷锟斤拷锟斤拷
		switch (rand) {
		// 锟斤拷纱锟叫达拷锟侥革拷锟斤拷锟斤拷
		case 1:
			itmp = Math.round(Math.random() * 25 + 65);
			ctmp = (char) itmp;
			return String.valueOf(ctmp);

			// 锟斤拷锟叫⌒达拷锟侥革拷锟斤拷锟斤拷
		case 2:
			itmp = Math.round(Math.random() * 25 + 97);
			ctmp = (char) itmp;
			return String.valueOf(ctmp);

			// 锟斤拷锟斤拷锟斤拷值锟斤拷锟斤拷
		default:
			itmp = Math.round(Math.random() * 9);
			return String.valueOf(itmp);
		}
	}
}
