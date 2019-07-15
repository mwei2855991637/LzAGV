package com.lc;

import org.junit.Test;

import com.lc.util.FileUtil;

public class LocalTest {

	@Test
	public void f1() {
		String outPath = "D:/1.txt";
		String str = "手动钉钉多多多多多多多多多多多多多多多多多多多多多多";
		StringBuffer sBuffer = new StringBuffer();
		int i = 0;
		while (i++ < 10000) {
			sBuffer.append(str);
		}
		System.out.println(sBuffer.length());
		String end = sBuffer.toString();
//		System.out.println(end);
		System.out.println(end.length());
//		FileUtil.writeMemoToFileByIO(end, outPath);

//		FileUtil.saveFile(new ByteArrayInputStream(str.getBytes()), "D:/1.txt");
//		FileUtil.writeMemoToFileByRW(sBuffer, "D:/1.txt");
	}

}
