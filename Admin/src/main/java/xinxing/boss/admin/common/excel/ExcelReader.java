package xinxing.boss.admin.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	/**
	 * 文件路径
	 */
    private String filePath;
    /**
     * 表名
     */
    private String sheetName;
    /**
     * 表所在名次
     */
    private int sheetAt;
    
    /**
     * 工作空间对象
     */
    private Workbook workBook;
    /**
     * 表对象
     */
    private Sheet sheet;
    /**
     * 表头行
     */
    private Row sheetHeadRow;
    
    /**
     * 表数据(所有)
     */
    private List<List<String>> listData;
    /**
     * 表数据(表头)
     */
    private List<String> columnHeaderList;
    /**
     * 表数据(数据)
     */
    private List<Map<String,String>> mapData;
    
    /**
     * 是否取到 表对象了
     */
    private boolean flag;
    /**
     * 初始化表 init for sheetName
     * @param filePath 文件uri地址
     * @param sheetName 工作表表名
     */
    public ExcelReader(String filePath, String sheetName) {
        this.filePath = filePath;
        this.sheetName = sheetName;
        this.flag = false;
       this.init();
    }   
    
    /**
     * 初始化表 init for sheetName
     * @param filePath 文件uri地址
     * @param indxe 工作表表名次
     */
    public ExcelReader(String filePath, int indxe) {
        this.filePath = filePath;
        this.sheetAt = indxe;
        this.flag = false;
       this.init();
    }   
    
    /**
     * 初始化
     */
    public void init(){
    	 FileInputStream inStream = null;
         try {
         	//获取文件流
             inStream = new FileInputStream(new File(filePath));
             //根据文件流创建exl工作空间
             workBook = WorkbookFactory.create(inStream);
             if (sheetName!=null) {
            	 sheet = workBook.getSheet(sheetName);  
			}else{
				if (sheetAt<=workBook.getNumberOfSheets()) {
					sheet=workBook.getSheetAt(sheetAt-1);
				}
			}
             //根据表名获取表,根据第二行来算
             sheetHeadRow=sheet.getRow(1);
         } catch (Exception e) {
             e.printStackTrace();
         }finally{
             try {
                 if(inStream!=null){
                     inStream.close();
                 }                
             } catch (IOException e) {                
                 e.printStackTrace();
             }
         }
    }
    /**
     * 获取单元格对象的值
     * @param cell 单元格对象
     * @return 单元格对象的值
     * @see TransferDownstream
     */
    private String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                    	DataFormatter formatter = new DataFormatter();
                        cellValue = formatter.formatCellValue(cell);
                    } else {
                    	//精度细分处理
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue.trim();
    }
    
    /**
     * 总共有多少行数据(先用)
     * @return  行 数量
     */
    public int getRowCount() {
    	 //需要表数据
        if(!flag){
            this.getSheetData();
        }  
        //行数为  1,2,3,4,5 不是0,1,2,3
		return sheet.getLastRowNum()+1;
	}
    
    /**
     * 总共有多少列数据(根据第一行确定的,不可全信)(后用)
     * @param row 行对象
     * @return 列 数量
     */
    public int getCellCount() {
    	 //需要表数据
        if(!flag){
            this.getSheetData();
        }  
        return sheetHeadRow.getLastCellNum()+1;
	}

    /**
     * 获取表的数据到mapData和listData中
     */
    private void getSheetData() {
        listData = new ArrayList<List<String>>();
        mapData = new ArrayList<Map<String, String>>();    
        columnHeaderList = new ArrayList<String>();
        //遍历所有的表
        for (int i = 0; i < sheet.getLastRowNum()+1; i++) {
        	
            Row row = sheet.getRow(i);//取出一行的数据
            
            Map<String, String> map = new HashMap<String, String>();
            
            List<String> list = new ArrayList<String>();
            
            if (row != null) {
            	//遍历所有行
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);//取出一个单元格
                    if (i == 0){//把第一行的数据保存为表头
                        columnHeaderList.add(getCellValue(cell));
                    }
                    else{//其他行为数据
                        map.put(columnHeaderList.get(j), this.getCellValue(cell));
                    }
                    //所有数据
                    list.add(this.getCellValue(cell));
                }
            }
            if (i > 0){
                mapData.add(map);
            }
            listData.add(list);
        }
        flag = true;
    }
    
    /**
     * 获取   <表数据(所有)>  的单元格数据
     * @param row 第几行
     * @param col 第几个
     * @return 单元格对象
     */
    public String getCellData(int row, int col){
    	//不为负数
        if(row<=0 || col<=0){
            return null;
        }
        //需要表数据
        if(!flag){
            this.getSheetData();
        }        
        
        if(listData.size()>=row && listData.get(row-1).size()>=col){
        	String value = listData.get(row-1).get(col-1);
            return value!=null?value:"";
        }else{
            return "";
        }
    }
    
    /**
     * 获取   <表数据(数据)>  的单元格数据
     * @param row 第几行
     * @param headerName 表头 上的名字
     * @return  单元格对象
     */
    public String getCellData(int row, String headerName){
        if(row<=0){
            return null;
        }
        if(!flag){
            this.getSheetData();
        }        
        if(mapData.size()>=row && mapData.get(row-1).containsKey(headerName)){
            return mapData.get(row-1).get(headerName);
        }else{
            return null;
        }
    }

    

    public static void main(String[] args) {
        ExcelReader eh = new ExcelReader("E:\\workspace\\test.xls","Sheet1");
        System.out.println(eh.getCellData(1, 1));
        System.out.println(eh.getCellData(1, "test1"));
    }
}
