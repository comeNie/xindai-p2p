package com.qfw.common.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QrCodeUtil {
	public static void refereeUrl(String url,HttpServletResponse response){
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
//		 String text = "http://www.baidu.com";  
         int width = 300;  
         int height = 300;  
         //二维码的图片格式  
         String format = "gif";  
         Hashtable hints = new Hashtable();  
         //内容所使用编码  
         hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(url,  
			         BarcodeFormat.QR_CODE, width, height, hints);
			
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
			for (int x = 0; x < width; x++) { 
				for (int y = 0; y < height; y++) { 
					image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF); //二维码图片为黑白两色
				} 
			}
			ImageIO.write(image,"gif",response.getOutputStream());
			
//		  File outputFile = new File("d:"+File.separator+"new.gif");  
//		  MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		}catch (IOException e) {
			
		}catch (WriterException e1) {
			// TODO Auto-generated catch block
		}  
	}
}
