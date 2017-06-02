package xinxing.boss.admin.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * POI读取Excel示例，分2003和2007
 * 
 * @author mzw
 * 
 */
public class ReadExcel {

	/**
	 * 读取Excel2003的示例方法（注意，此数据如果是数值，已经强制转换为整型）
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<List<String>> readFromXLS2003(String filePath,int index) {
		File excelFile = null;// Excel文件对象
		InputStream is = null;// 输入流对象
		String cellStr = null;// 单元格，最终按字符串处理
		List<List<String>> studentList = new ArrayList<List<String>>();// 返回封装数据的List
		try {
			excelFile = new File(filePath);
			is = new FileInputStream(excelFile);// 获取文件输入流
			HSSFWorkbook workbook2003 = new HSSFWorkbook(is);// 创建Excel2003文件对象
			HSSFSheet sheet = workbook2003.getSheetAt(index);// 取出第一个工作表，索引是0
			// 开始循环遍历行，表头不处理，从1开始
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				List<String> list = new ArrayList<String>();
				HSSFRow row = sheet.getRow(i);// 获取行对象
				if (row == null) {// 如果为空，不处理
					continue;
				}
				// 循环遍历单元格
				for (int j = 0; j < row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell(j);// 获取单元格对象
					if (cell == null) {// 单元格为空设置cellStr为空串
						cellStr = "";
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理
						cellStr = String.valueOf(cell.getBooleanCellValue());
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {// 对数字值的处理
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy/MM/dd HH:mm:ss");
							Date date = cell.getDateCellValue();
							cellStr = sdf.format(date);
						} else {
							cellStr = (long)cell.getNumericCellValue() + "";
						}
					} else {// 其余按照字符串处理
						cellStr = cell.getStringCellValue();
					}
					list.add(cellStr);
				}
				studentList.add(list);// 数据装入List
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {// 关闭文件流
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return studentList;
	}
	
	/**
	 * 魔豆道具赠送 for 2007>=xlsx（注意，此数据如果是数值，已经强制转换为整型）
	 * @param filePath 文件路径（目录+文件名）
	 * @param index （读取第几个工作表  从0开始）
	 * @return
	 */
	public static List<List<String>> readFromXLS2007(String filePath,int index) {
		File excelFile = null;// Excel文件对象
		InputStream is = null;// 输入流对象
		String cellStr = null;// 单元格，最终按字符串处理
		List<List<String>> studentList = new ArrayList<List<String>>();// 返回封装数据的List
		try {
			excelFile = new File(filePath);
			is = new FileInputStream(excelFile);// 获取文件输入流
			XSSFWorkbook workbook2007 = new XSSFWorkbook(is);// 创建Excel2003文件对象
			XSSFSheet sheet = workbook2007.getSheetAt(index);// 取出第一个工作表，索引是0
			// 开始循环遍历行，表头不处理，从1开始
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				List<String> list = new ArrayList<String>();
				XSSFRow row = sheet.getRow(i);// 获取行对象
				if (row == null) {// 如果为空，不处理
					continue;
				}
				// 循环遍历单元格
				for (int j = 0; j < row.getLastCellNum(); j++) {
					XSSFCell cell = row.getCell(j);// 获取单元格对象
					if (cell == null) {// 单元格为空设置cellStr为空串
						cellStr = "";
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理
						cellStr = String.valueOf(cell.getBooleanCellValue());
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {// 对数字值的处理
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy/MM/dd HH:mm:ss");
							Date date = cell.getDateCellValue();
							cellStr = sdf.format(date);
						} else {
							cellStr = (long)cell.getNumericCellValue() + "";
						}
					} else {// 其余按照字符串处理
						cellStr = cell.getStringCellValue();
					}
					list.add(cellStr);
				}
				studentList.add(list);// 数据装入List
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {// 关闭文件流
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return studentList;
	}

//	public static List<List<String>> readFromXLSX2007(String filePath) {
//		File excelFile = null;// Excel文件对象
//		InputStream is = null;// 输入流对象
//		String cellStr = null;// 单元格，最终按字符串处理
//		List<List<String>> studentList = new ArrayList<List<String>>();// 返回封装数据的List
//		List<String> student = null;// 每一个学生信息对象
//		try {
//			excelFile = new File(filePath);
//			is = new FileInputStream(excelFile);// 获取文件输入流
//			XSSFWorkbook workbook2007 = new XSSFWorkbook(is);// 创建Excel2003文件对象
//			XSSFSheet sheet = workbook2007.getSheetAt(0);// 取出第一个工作表，索引是0
//			// 开始循环遍历行，表头不处理，从1开始
//			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//				student = new ArrayList<String>();// 实例化Student对象
//				XSSFRow row = sheet.getRow(i);// 获取行对象
//				if (row == null) {// 如果为空，不处理
//					continue;
//				}
//				// 循环遍历单元格
//				for (int j = 0; j < row.getLastCellNum(); j++) {
//					XSSFCell cell = row.getCell(j);// 获取单元格对象
//
//					if (cell == null) {// 单元格为空设置cellStr为空串
//						cellStr = "";
//					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理
//						cellStr = String.valueOf(cell.getBooleanCellValue());
//					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {// 对数字值的处理
//
//						if (HSSFDateUtil.isCellDateFormatted(cell)) {
//							SimpleDateFormat sdf = new SimpleDateFormat(
//									"yyyy/MM/dd HH:mm:ss");
//							Date date = cell.getDateCellValue();
//							cellStr = sdf.format(date);
//						} else {
//							cellStr = cell.getNumericCellValue() + "";
//						}
//					} else {// 其余按照字符串处理
//						cellStr = cell.getStringCellValue();
//					}
//					
//					list.add(cellStr);
//				}
//				studentList.add(student);// 数据装入List
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {// 关闭文件流
//			if (is != null) {
//				try {
//					is.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return studentList;
//	}
	public static void main(String[] args) {
		String computerLoginName = "Administrator";
		List<List<String>> lists =ReadExcel.readFromXLS2003("C:\\Users\\"+computerLoginName+"\\Desktop\\批量到账_赠送 .xls",0);
//		System.out.println(lists.size());
		for (int i = 0; i < lists.size(); i++) {
			List<String> list = lists.get(i);
			for(String s : list){
				System.out.print(s + "\t");
			}
			System.out.println();
			
//			if (list.get(1).endsWith("钻石")) {
//				System.out.println("2");
//			}
//			System.out.println((long)Double.parseDouble(list.get(0)));
			
//			String uidStr = list.get(0);
//			System.out.println(uidStr.length());
//			long uid = (long) Double.parseDouble(uidStr.replace(" ", ""));
//			String reward = list.get(1);
//			int costType;
//			int rewardNum = 0;
//			if (reward.endsWith("钻石")) {
//				costType = TradeCostType.COST_JEWEL.type;
//				try {
//					rewardNum = Integer.parseInt(reward.substring(0,
//							reward.indexOf("钻石")));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else {
//				costType = TradeCostType.COST_BEAN.type;
//				try {
//					rewardNum = Integer.parseInt(reward.substring(0,
//							reward.indexOf("魔豆")));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			System.out.println(uidStr+":"+uid+":"+costType+":"+rewardNum);
		}
	}
}
