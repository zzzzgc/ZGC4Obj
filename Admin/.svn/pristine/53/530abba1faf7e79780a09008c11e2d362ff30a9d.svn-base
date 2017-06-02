/*
 * 文件名：TableData.java
 * 版权：
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改内容：
 */
package xinxing.boss.admin.common.excel;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 表格数据对象
 * Title:  Excel导出工具<br>
 * Description: 封装了单个Sheet里除了表格标题及副标题等信息外的对象，包括表格表头对象、数据行对象集合及其操作方法等<br>
 * Copyright: eastcom Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:邮箱">jiangxd</a><br>
 * @e-mail: jiangxd@eastcom-sw.com<br>
 * @version 2.0 <br>
 * @creatdate 2012-11-7 下午03:37:50 <br>
 *
 */
public class TableData {
	
	private static DecimalFormat format = new DecimalFormat("0.##");
	
	/**
	 * 字符串型
	 */
	public static final int STYLE_TYPE_STRING = 0;

	/**
	 * 浮点型，保留2位小数
	 */
	public static final int STYLE_TYPE_FLOAT_2 = 1;

	/**
	 * 浮点型，保留3位小数
	 */
	public static final int STYLE_TYPE_FLOAT_3 = 2;

	/**
	 * 整形
	 */
	public static final int STYLE_TYPE_INTEGER = 3;

	/**
	 * 红色背景
	 */
	public static final int STYLE_TYPE_RED_BG = 10;

	/**
	 * 黄色背景
	 */
	public static final int STYLE_TYPE_YELLOW_BG = 11;

	/**
	 * 绿色背景
	 */
	public static final int STYLE_TYPE_GREEN_BG = 12;

	/**
	 * sheet名称
	 */
	private String sheetTitle;
	
	/**
	 * 列表头对象
	 */
	private TableHeaderMetaData header;

	/**
	 * 行对象集合
	 */
	private LinkedList<TableDataRow> rows;

	/**
	 * 总行数
	 */
	private int totalRows;
	
	public TableData(){}

	public TableData(TableHeaderMetaData header) {
		this.header = header;
		rows = new LinkedList<TableDataRow>();
	}

	/**
	 * 小计
	 */
	public void compute(){
		for (int i = 0; i < header.getColumnCount(); i++){
			TableColumn tc = header.getColumnAt(i);
			if (tc.isVisible() && tc.isGrouped() && tc.isDisplaySummary()){
				//buildSummary(i);
			}
		}
	}
	
	class SummaryData{
		String key;
		int index;
		int count;
		TableDataRow row;
		
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append(key).append("\t").append(index).append("\t").append(count)
				.append("\t");
			for (TableDataCell cell:row.getCells()){
				sb.append(cell.getValue()).append("\t");
			}
			return sb.toString();
		}		
	}
	
		
	//GETTER和SETTER方法
	/**
	 * 获取列表头对象 
	 * 
	 * @return
	 */
	public TableHeaderMetaData getTableHeader() {
		return header;
	}

	/**
	 * 添加行对象
	 * @param row 行对象
	 */
	public void addRow(TableDataRow row) {
		rows.add(row);
	}

	public List<TableDataRow> getRows() {
		return rows;
	}

	/**
	 * 根据行号获取行对象
	 * @param index 行号
	 * @return
	 */
	public TableDataRow getRowAt(int index) {
		return rows.get(index);
	}

	/**
	 * 获取数据行数
	 * @return
	 */
	public int getRowCount() {
		return rows.size();
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public void setHeader(TableHeaderMetaData header) {
		this.header = header;
	}

	public void setRows(LinkedList<TableDataRow> rows) {
		this.rows = rows;
	}

	public String getSheetTitle() {
		return sheetTitle;
	}

	public void setSheetTitle(String sheetTitle) {
		this.sheetTitle = sheetTitle;
	}

}
