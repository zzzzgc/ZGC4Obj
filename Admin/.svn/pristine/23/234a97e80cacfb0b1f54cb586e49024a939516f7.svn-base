package xinxing.boss.admin.common.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtil {
	public static String getStrByFilePath(String addTargetUrl) throws FileNotFoundException {
		Scanner in = new Scanner(new File(addTargetUrl));
		StringBuffer sb = new StringBuffer();
		while (in.hasNext()) {
			String str = in.nextLine();
			sb.append(str + "\r\n");
		}
		in.close();
		return sb.toString();
	}
	
	public static  void appendFile(String fileUrl, String str) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileUrl, true);
			fw.write(str);
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static void writeFile(String fileUrl, String str) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileUrl, false);
			fw.write(str);
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
