package com.ngmob.util;

import java.util.Random;

public class PassUtil {

	public static String generate(){
		String[] array = {"0","1","2","3","4","5","6","7","8","9"};
		StringBuilder sb = new StringBuilder();
		Random random = new Random(10);
		for(int i=0;i<4;i++){
			int j = random.nextInt();
			sb.append(array[j]);
		}
		return sb.toString();
	}
}
