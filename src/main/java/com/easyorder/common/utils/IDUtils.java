package com.easyorder.common.utils;

import java.util.Random;

/**
 * 各类ID生成工具类
 * @author qiudequan
 *
 */
public class IDUtils {
	private IDUtils() {

	}

	public static String generateProductId() {
		long millis = System.currentTimeMillis();
		Random random = new Random();
		int end2 = random.nextInt(99);
		String str = millis + String.format("%02d", end2);
		long id = new Long(str);
		return String.valueOf(id);
	}
	
	public static void main(String[] args) {
		String generateProductId = IDUtils.generateProductId();
		System.out.println(generateProductId);
	}

}
