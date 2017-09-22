package com.easyorder.common.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片处理工具类
 * @author Administrator
 *
 */
public class ImageUtils {
	private static Boolean DEFAULT_FORCE = false;
	private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);
	/**
	 * <p>Title: thumbnailImage</p>
	 * <p>Description: 根据图片路径生成缩略图 </p>
	 * @param imagePath    原图片路径
	 * @param w            缩略图宽
	 * @param h            缩略图高
	 * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
	 */
	public static InputStream thumbnailImage(InputStream inputStream, String uniqueKey, int w, int h, boolean force){
		try {
			// ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
			String types = Arrays.toString(ImageIO.getReaderFormatNames());
			String formatName = null;
			// 获取图片后缀
			if(uniqueKey.indexOf(".") > -1) {
				formatName = uniqueKey.substring(uniqueKey.lastIndexOf(".") + 1);
			}// 类型和图片后缀全部小写，然后判断后缀是否合法
			if(formatName == null || types.toLowerCase().indexOf(formatName.toLowerCase()) < 0){
				logger.error("Sorry, the image suffix is illegal. the standard image formatName is {}.", types);
				return null;
			}
			logger.info("target image's size, width:{}, height:{}.", w, h);
			Image img = ImageIO.read(inputStream);
			if(!force){
				// 根据原图与要求的缩略图比例，找到最合适的缩略图比例
				int width = img.getWidth(null);
				int height = img.getHeight(null);
				if((width * 1.0) / w < (height * 1.0) / h){
					if(width > w){
						h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w / (width * 1.0)));
						logger.info("change image's height, width:{}, height:{}.", w, h);
					}
				} else {
					if(height > h){
						w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h / (height * 1.0)));
						logger.info("change image's width, width:{}, height:{}.", w, h);
					}
				}
			}
			BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics g = bi.getGraphics();
			g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
			g.dispose();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			// 保持图片格式不变，防止存在颜色差别
			ImageIO.write(bi, formatName, bos);
			inputStream = new ByteArrayInputStream(bos.toByteArray());
			return inputStream;
		} catch (IOException e) {
			logger.error("generate thumbnail image failed.", e);
		}
		return null;
	}

	public static InputStream thumbnailImage(InputStream inputStream, String uniqueKey, int w, int h){
		return thumbnailImage(inputStream, uniqueKey, w, h, DEFAULT_FORCE);
	}

}
