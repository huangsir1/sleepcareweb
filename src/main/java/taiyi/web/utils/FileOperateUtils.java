package taiyi.web.utils;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

/**
 * <b>文件读取类</b><br />
 * 1、按字节读取文件内容<br />
 * 2、按字符读取文件内容<br />
 * 3、按行读取文件内容<br />
 * 
 * @author qin_xijuan
 * 
 */
public class FileOperateUtils {
	static Logger logger = Logger.getLogger(FileOperateUtils.class);

	public static void main(String[] args) throws Exception {
		
		// Map<String, String[]> readAsMap =
		// readAsMap("/Users/jason/Desktop/1.txt");
		// System.out.println(readAsMap.get("riqi")[0]);
//		String readFile = readFile("/Users/jason/Downloads/金哥1_sleepdata_temp.txt");
//		Map<String, String[]> readBinary = readBinary("/Users/jason/Desktop/source.txt");
//		String[] strings = readBinary.get("riqi");
//		for (int i = 0; i < strings.length; i++) {
//			System.out.println(new Date(Long.parseLong(strings[i])));
//			
////		}
//		 String decompressToString = GZipUtils.decompressToString("/Users/jason/Desktop/source.txt");
//		 System.out.println(decompressToString.getBytes()[0]);
	}
	
	public static void read(DataInput dataInputStream) throws IOException {
		System.out.println(dataInputStream.readByte());
		System.out.println(dataInputStream.readByte());
		dataInputStream.skipBytes(10);
		System.out.println(dataInputStream.readLong());
		dataInputStream.skipBytes(1);
	}

	public static Map<String, String[]> readAsMap(String filePath) throws Exception {
		String file = null;
		boolean isGzip = false;
		try {
			file = GZipUtils.decompressToString(filePath);
			isGzip = true;
			logger.debug(filePath + " IS Gzip");
		} catch (Exception e) {
			logger.debug(filePath + " ISNOT Gzip");
			file = readFile(filePath);
		}
		String mailv[] = null, xueyang[] = null, riqi[] = null;
		try {
			file = file.replaceAll(
					"\\@\\#\\$\\%\\@\\#\\$\\%\\@\\#\\$\\%\\@\\#\\$\\%\\@\\#\\$\\%\\@\\#\\$\\%\\@\\#\\$\\%\\@\\#\\$\\%\r\n",
					"");
			String items[] = file.split("\n");
			// DateFormat in = new SimpleDateFormat("EEE MMM dd HH:mm:ss z
			// yyyy",
			// Locale.ENGLISH);
			// DateFormat out = new SimpleDateFormat("HH:mm:ss");
			mailv = new String[items.length - 1];
			xueyang = new String[items.length - 1];
			riqi = new String[items.length - 1];
			String[] title = items[0].split(",");
			// version
			if ("time".equalsIgnoreCase(title[7])) {
				for (int i = 1; i < items.length; i++) {
					String[] temp = items[i].split(",");
					String d0 = temp[0];// mailv
					String d2 = temp[2];// xueyang
					String d7 = temp[7];// riqi
					mailv[i - 1] = d0;
					xueyang[i - 1] = d2;
					riqi[i - 1] = d7;
				}
			} else {
				for (int i = 1; i < items.length; i++) {
					String[] temp = items[i].split(",");
					String d0 = temp[0];// mailv
					String d2 = temp[1];// xueyang
					String d9 = temp[9];// riqi
					mailv[i - 1] = d0;
					xueyang[i - 1] = d2;
					riqi[i - 1] = d9;
				}
			}
			Map<String, String[]> hashMap = new HashMap<String, String[]>();
			hashMap.put("mailv", mailv);
			hashMap.put("xueyang", xueyang);
			hashMap.put("riqi", riqi);
			logger.debug(filePath + " IS original text");
			return hashMap;
		} catch (Exception e) {
			logger.debug(filePath + " IS binary");
			DataInputStream dataInputStream = null;
			if (isGzip) {
				dataInputStream = new DataInputStream(new GZIPInputStream(new FileInputStream(filePath)));
			} else {
				dataInputStream = new DataInputStream(new FileInputStream(filePath));
			}
			return readBinary(dataInputStream);
		}

		
	}

	public static Map<String, String[]> readBinary(DataInput dataInput) throws IOException {
		ArrayList<String> mailv = Lists.newArrayList();
		ArrayList<String> xueyang = Lists.newArrayList();
		ArrayList<String> riqi = Lists.newArrayList();
		try {
			for (;;) {
				mailv.add(dataInput.readByte() + ""); // ml
				xueyang.add(dataInput.readByte() + ""); // xy
				dataInput.skipBytes(10);
				riqi.add(dataInput.readLong() + "");
				dataInput.skipBytes(1);
			}
		} catch (EOFException e) {
		}
		
		Map<String, String[]> hashMap = new HashMap<String, String[]>();
		hashMap.put("mailv", mailv.toArray(new String[0]));
		hashMap.put("xueyang", xueyang.toArray(new String[0]));
		hashMap.put("riqi", riqi.toArray(new String[0]));
		return hashMap;
	}

	public static String readFile(String file) throws Exception {
		StringBuilder sb = new StringBuilder();
		FileInputStream fis = new FileInputStream(file);

		// 得到文件通道
		FileChannel fc = fis.getChannel();

		// 分配与文件尺寸等大的缓冲区
		ByteBuffer bf = ByteBuffer.allocate((int) fc.size());

		// 整个文件内容全读入缓冲区，即是内存映射文件
		fc.read(bf);

		// 把缓冲中当前位置回复为零
		bf.rewind();

		// 输出缓冲区中的内容
		while (bf.hasRemaining()) {
			sb.append((char) bf.get());
		}
		fis.close();
		return sb.toString();
	}

	/**
	 * 利用NIO将内容输出到文件中
	 * 
	 * @param file
	 */
	public static void writeFileByNIO(String file, String content) {
		FileChannel fc = null;
		ByteBuffer buffer = null;
		try (FileOutputStream fos = new FileOutputStream(file)){
			// 第一步 获取一个通道
			fc = fos.getChannel();
			// buffer=ByteBuffer.allocate(1024);
			// 第二步 定义缓冲区
			buffer = ByteBuffer.wrap(content.getBytes());
			// 将内容写到缓冲区
			fos.flush();
			fc.write(buffer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fc != null) {
					fc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}