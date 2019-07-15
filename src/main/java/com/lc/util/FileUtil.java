package com.lc.util;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class FileUtil {

	// *********************************** 供外部调用 **********************************************

	/**
	 * SpringMVC 用io流的方式提供文件下载
	 * 
	 * @param fileName 用户下载时显示的文件名
	 * @param path     文件存放的系统中的绝对路径
	 * @return
	 * @throws IOException
	 */
	public static ResponseEntity<byte[]> downloadFile(String fileName, String path) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		fileName = URLEncoder.encode(fileName, "utf-8");

		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);

		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path)), headers, HttpStatus.CREATED);
	}

	/**
	 * 保存用户上传的文件,文件名自动从 fileItem里获得
	 * @param fileItem
	 * @param outPath	"xxx/xx/xx",文件路径
	 * @return
	 */
	public static boolean saveFile(FileItem fileItem, String outPath) {
		try {
			saveFile(fileItem.getInputStream(), outPath + "/" + fileItem.getName());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 保存文件
	 * @param inPath	输入路径
	 * @param outPath	输出路径
	 * @return
	 */
	public static boolean saveFile(String inPath, String outPath) {
		try {
			return saveFile(new FileInputStream(inPath), outPath);
		} catch (FileNotFoundException e) {
			System.err.println("File Not Find!! Path : " + inPath);
			return false;
		}
	}

	/**
	 * 保存文件
	 * @param inPath	输入流
	 * @param outPath	输出路径
	 * @return
	 */
	public static boolean saveFile(InputStream inputStream, String outPath) {
		return baseFileIO(inputStream, outPath, true);
	}

	/**
	 * 保存文件，需要提供一个写入的内容
	 * @param string
	 * @param outPath
	 * @return
	 */
	public static boolean writeMemoToFileByRW(String string, String outPath) {
		return baseFileRW(string, outPath, true, "utf-8");
	}

	/**
	 * 保存文件，需要提供一个写入的内容
	 * @param string
	 * @param outPath
	 * @return
	 */
	public static boolean writeMemoToFileByIO(String string, String outPath) {
		return baseFileIO(new ByteArrayInputStream(string.getBytes()), outPath, true);
	}

	// *********************************** 内部封装 **********************************************
	/**
	 * 删除指定文件，或指定文件夹及其子目录！（谨慎操作）
	 * 
	 * @param file
	 */
	public static void deleteDir(File file) {
		if (file.isDirectory()) {
			for (File s : file.listFiles()) {
				deleteDir(s);
			}
		}
		file.delete();
	}

	/**
	 * 最底层的字节输入输出流（InputStream/OutputStream）
	 * 
	 * @param InputStream
	 * @param outPath
	 * @return
	 */
	private static boolean baseFileIO(InputStream is, String outPath, boolean append) {
		// 判断输出路径，不存在则自动创建
		File outFile = pathExist(outPath);

		byte[] b = new byte[10 * 1024];
		int len = 0;
		OutputStream os = null;
		try {
			os = new FileOutputStream(outFile, append);
			while ((len = is.read(b)) != -1) {
				os.write(b, 0, len);
			}
		} catch (Exception e) {
			System.err.println("I/O errer !!!");
			return false;
		} finally {
			try {
				os.close();
			} catch (IOException e) {
			}
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	/**
	 * 最底层的字符输入输出流（Read/Write）<br>
	 * <b>使用BufferWrite写入文件的时候，需要手动调用 write.flush() 才能正确写入文件</b>
	 * @param memo 
	 * @param outPath
	 * @param charset
	 * @return
	 */
	private static boolean baseFileRW(String memo, String outPath, boolean append, String charset) {
		// 判断输出路径，不存在则自动创建
		File outFile = pathExist(outPath);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile, append), charset));
			writer.write(memo);
			// 手动提交缓存区的数据
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e1) {
			}
		}
		return true;
	}

	/**
	 * 将 StringBuffer 转为一个指定大小的字符串数组
	 * 
	 * @param buffer
	 * @return
	 */
	private static List<String> bufferToStringArray(StringBuffer buffer) {
		long before = System.currentTimeMillis();
		int strSize = 5 * 1024;
		List<String> list = new ArrayList<>();
		int end = 0, bufferLength = buffer.length();
		for (int i = 0; i < buffer.length() / strSize + 1; i++) {
			end = (i + 1) * strSize;
			list.add(buffer.substring(i * strSize, bufferLength < end ? bufferLength : end));
		}
		long after = System.currentTimeMillis();
		System.out.println("deal StringBuffer used time : " + (after - before));
		return list;
	}

	/**
	 * 根据目标路径，判断是否需要创建文件父路径
	 * @param outPath
	 * @return
	 */
	private static File pathExist(String outPath) {
		File outFile = new File(outPath);
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}
		return outFile;
	}

	// test function to sure work
	public static void main(String[] args) {

	}

}
