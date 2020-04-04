package cn.com.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * <p>TITLE：图片处理工具类 </p>
 *
 * @author: RM
 * @version 1.0
 * Created on 2018年10月26日
 * Copyright © 2018LiDaDa. All rights reserved.
 */
public class ImageUtil {

//	public static void main(String[] args) {
//		int parseInt = Integer.parseInt("38c2ad", 16);
//		Color c = new Color(parseInt);
//		Font f = new Font(null, Font.PLAIN, 32);
//		
//		File below = new File("f:/hb.jpg");
//		// 1.0932944606413995
//		compositeWaterMark(below, "01000044", "F:/11.jpg", 548, 1238, c, f);
//	}
	
	public static final String BASE_URL = "/static/images/";
	
	/**
	 * 图片类型
	 */
	public enum ImageType {
		POSTER,

		HEAD,

		STATIC,

		EXTEND
	}

	/**
	 * <p>TITLE：生成图片路径</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年10月26日
	 * 
	 * @param name
	 * @param params
	 * @return
	 */
	public static String createPath(String name, String params, ImageType imageType) {
		if (StringUtils.isEmpty(params)) {
			return imageType.name().toLowerCase() + "/" + name + ".jpg";
		} else {
			return imageType.name().toLowerCase() + "/" + params + "/" + name + ".jpg";
		}
	}
	
	/**
	 * <p>TITLE：图片添加水印 </p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年10月26日
	 * 
	 * @param below	底部图片
	 * @param waterMark	水印文本
	 * @param target	生成的图片路径
	 * @param x	水印文字相对位置X轴
	 * @param y	水印文字相对位置Y轴
	 * @param color	水印颜色
	 * @param font	水印字体
	 * @return
	 */
	public static String compositeWaterMark(File below, String waterMark, String basePath, String target, Integer x, Integer y, int color, int font) {
		OutputStream outs = null;
		try {
			// 通过ImageIO获取图像文件的像素大小 即宽/高
			Image srcImg = ImageIO.read(below);
			int srcImgWidth = srcImg.getWidth(null);
			int srcImgHeight = srcImg.getHeight(null);
			
			// 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
			//根据图片的背景设置水印颜色
            g.setColor(new Color(color));
			//设置字体
            g.setFont(new Font(null, Font.PLAIN, font));

            //设置水印的坐标
            g.drawString(waterMark, x, y);
            g.dispose();
            
            // 输出图片
            File file = new File(basePath + target);
			if (file.isFile()) {
				file.delete();
			}
			
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				outs = new FileOutputStream(basePath + target);
			}
            
            ImageIO.write(bufImg, "jpg", outs);
            outs.flush(); 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (outs != null) {
				try {
					outs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return target;
	}
	
	/**
	 * <p>TITLE：图片合成 </p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年10月26日
	 * 
	 * @param below	底部图片
	 * @param above	顶部图片
	 * @param target	生成的图片路径
	 * @param x	顶部图片相对位置X轴
	 * @param y	顶部图片相对位置Y轴
	 * @return
	 */
	public static String compositeImage(File below, File above, String target, Integer x, Integer y) {
		OutputStream outs = null;
		try {
			// 通过ImageIO获取图像文件的像素大小 即宽/高
			Image imageTemp = ImageIO.read(below);
			int width = imageTemp.getWidth(null);
			int height = imageTemp.getHeight(null);
			// 1.创建缓存图片对象--BufferedImage
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 2.创建java绘图工具对象--Graphics2D
			Graphics2D graphics2D = bufferedImage.createGraphics();
			// 3.使用绘图工具对象将原图绘制到缓存图片对象
			graphics2D.drawImage(imageTemp, 0, 0, width, height, null);
			
			// 二维码图片处理 
			BufferedImage insertImage = ImageIO.read(above);
			int qrcodeWidth = insertImage.getWidth(null);
			int qrcodeHeight = insertImage.getHeight(null);
			// 保证二位绘图在图片以内
			if (x > (width - qrcodeWidth)) {
				x = width - qrcodeWidth;
			}
			if (y > (height - qrcodeHeight)) {
				y = height - qrcodeHeight;
			}
			
			
			// 将图片绘制到缓存图片对象上
			graphics2D.drawImage(insertImage, null, x, y);
			// 释放工具
			graphics2D.dispose();
			
			// 处理完成的图片写到磁盘
			File file = new File(target);
			if (file.isFile()) {
				file.delete();
			}
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				outs = new FileOutputStream(target);
			}
			
			// 5.创建图片编码工具类--JPEGImageEncoder
			ImageIO.write(bufferedImage, "jpg", outs);
			outs.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (outs != null) {
				try {
					outs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return target;
	}

	/**
	 * 前景色
	 */
	private static int onColor = 0xFF000000;
	/**
	 * 背景色
	 */
	private static int offColor = 0xFFFFFFFF;
	/**
	 * 白边大小，取值范围0~4(二维码白边设置为0)
	 */
	private static int margin = 0;
	/**
	 * 二维码容错率
	 */
	private static ErrorCorrectionLevel level = ErrorCorrectionLevel.Q;
	
	/**
	 * 生成二维码
	 * 
	 * @param txt
	 *            //二维码内容
	 * @param imgPath
	 *            //二维码保存物理路径
	 * @param imgName
	 *            //二维码文件名称
	 * @param suffix
	 *            //图片后缀名
	 * @param size
	 * 			  //二维码大小
	 */
	public static File generateQRImage(String txt, String imgPath, String imgName, String suffix, int size) {
		try {
			File filePath = new File(imgPath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			// 指定纠错等级
			hints.put(EncodeHintType.ERROR_CORRECTION, level);
			// 指定编码格式
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			// 设置白边
			hints.put(EncodeHintType.MARGIN, margin);
			//1、生成二维码  
            BitMatrix encode = new MultiFormatWriter().encode(txt, BarcodeFormat.QR_CODE, size, size, hints);
            //2、获取二维码宽高  
            int codeWidth = encode.getWidth();  
            int codeHeight = encode.getHeight();  
              
            //3、将二维码放入缓冲流  
            BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);  
            for (int i = 0; i < codeWidth; i++) {  
                for (int j = 0; j < codeHeight; j++) {  
                    //4、循环将二维码内容定入图片  
                    image.setRGB(i, j, encode.get(i, j) ? onColor : offColor);  
                }  
            }  
            
            //如果图片不存在创建图片  
    		File outPutImage = new File(imgPath, imgName);
            if (!outPutImage.isFile()) {
            	outPutImage.createNewFile();
    		}
            //5、将二维码写入图片  
            ImageIO.write(image, suffix, outPutImage);  
            return outPutImage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 生成带logo的二维码图片
	 * 
	 * @param txt
	 *            //二维码内容
	 * @param logoPath
	 *            //logo绝对物理路径
	 * @param imgPath
	 *            //二维码保存绝对物理路径
	 * @param imgName
	 *            //二维码文件名称
	 * @param suffix
	 *            //图片后缀名
	 * @param size
	 * 			  //二维码大小
	 * @throws Exception
	 */
	public static File generateQRImage(String txt, File logo, String imgPath, String imgName, String suffix, int size) {
		try {
			File filePath = new File(imgPath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}

			if (imgPath.endsWith("/")) {
				imgPath += imgName;
			} else {
				imgPath += "/" + imgName;
			}

			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, level);
			// 设置白边
			hints.put(EncodeHintType.MARGIN, margin);
			BitMatrix encode = new MultiFormatWriter().encode(txt, BarcodeFormat.QR_CODE, size, size, hints);
			File qrcodeFile = new File(imgPath);
			return writeToFile(encode, suffix, qrcodeFile, logo);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param matrix
	 *            二维码矩阵相关
	 * @param format
	 *            二维码图片格式
	 * @param file
	 *            二维码图片文件
	 * @param logoPath
	 *            logo路径
	 * @throws IOException
	 */
	private static File writeToFile(BitMatrix matrix, String format, File file, File logo) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		Graphics2D gs = image.createGraphics();

		int ratioWidth = image.getWidth() * 2 / 10;
		int ratioHeight = image.getHeight() * 2 / 10;
		// 载入logo
		Image img = ImageIO.read(logo);
		int logoWidth = img.getWidth(null) > ratioWidth ? ratioWidth : img.getWidth(null);
		int logoHeight = img.getHeight(null) > ratioHeight ? ratioHeight : img.getHeight(null);

		int x = (image.getWidth() - logoWidth) / 2;
		int y = (image.getHeight() - logoHeight) / 2;

		gs.drawImage(img, x, y, logoWidth, logoHeight, null);
		gs.setColor(Color.black);
		gs.setBackground(Color.WHITE);
		gs.dispose();
		img.flush();
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
		return file;
	}

	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? onColor : offColor);
			}
		}
		return image;
	}
}
