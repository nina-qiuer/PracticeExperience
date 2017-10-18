package com.tuniu.gt.toolkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Excel格式数据导出接口
 * @author yuliang
 *
 */
public class DataExportExcel {
	
	private static Logger logger = Logger.getLogger(DataExportExcel.class);
	
	private HttpServletResponse response;
	//下载的excel文件名
	private String fileName;
	//excel标题
	private String tilte;
	//map格式数据，string-列标题，list-列数据
	private Map<String,List<Object>> mapData;
	//list格式数据，listData第一行为数据列标题list，其后各行为具体数据list。
	private List<List<Object>> listData;
	
	public List<List<Object>> getListData() {
		return listData;
	}

	public void setList(List<List<Object>> listData) {
		this.listData = listData;
	}

	public HttpServletResponse getResponse(){
		return this.response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getTilte() {
		return tilte;
	}
	
	public void setTilte(String tilte) {
		this.tilte = tilte;
	}
	
	
	public Map<String, List<Object>> getMapData() {
		return mapData;
	}

	public void setMapData(Map<String, List<Object>> mapData) {
		this.mapData = mapData;
	}
	
	/**
	 * 构造方法，传入参数为map格式
	 * @param mapData    数据集合，key-列名称 value-列数据list
	 * @param fileName   下载使用的文件名
	 * @param title      excel表格头
	 * @param response   下载使用
	 */
	public DataExportExcel(Map<String,List<Object>> mapData, String fileName, String title, HttpServletResponse response){
		this.mapData = mapData;
		this.fileName = fileName;
		this.tilte = title;
		this.response = response;
	}
	
	/**
	 * 构造方法，传入参数为list格式
	 * @param listData    数据集合，第一列为列标题list，其后为每行数据list
	 * 
	 * @param fileName   下载使用的文件名
	 * @param title      excel表格头
	 * @param response   下载使用
	 */
	public DataExportExcel(List<List<Object>> listData, String fileName, String title, HttpServletResponse response){
		this.listData = listData;
		this.fileName = fileName;
		this.tilte = title;
		this.response = response;
	}
	
	
	/**
	 * 传入数据参数为map时，调用此方法生成excel
	 */
	public void createExcelFromMap(){
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		ExportExcel exportExcel = new ExportExcel(wb, sheet, HSSFCellStyle.ALIGN_CENTER_SELECTION);
		
		List<List<Object>> list = new ArrayList<List<Object>>();
		ArrayList<String> dataTitle = new ArrayList<String>();
		int dataRowCount = 0;//数据行数。不包括列标题
		
		Set<String> key = mapData.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
            String s = (String) it.next();
            dataTitle.add(s);
        }
		
		//计算excel列数
		int number = this.mapData.size();
		// 给工作表列定义列宽(实际应用自己更改列数)
		for (int i = 0; i < number; i++) {
			sheet.setColumnWidth(i, 4500);
		}

		// 创建单元格样式
		HSSFCellStyle cellStyle = exportExcel.createColumStyle((short)250,false);

		// 创建报表头部
		exportExcel.createNormalHead(this.tilte, number);
		
		// 设置列头
		HSSFRow rowHead = sheet.createRow(1);

		// 设置行高
		rowHead.setHeight((short) 800);

		HSSFCell rowCell = null;
		// 创建列标题,取出列标题对应的数据放入list
		for (int i = 0; i < number; i++) {
			rowCell = rowHead.createCell(i);
			rowCell.setCellStyle(cellStyle);
			rowCell.setCellValue(new HSSFRichTextString(dataTitle.get(i).toString()));
			//创建列标题对应的值
			List<Object>listTemp = mapData.get(dataTitle.get(i));
			list.add(listTemp);
			dataRowCount = listTemp.size();
		}
		//列标题对应数据写入excel
		for (int j = 0; j < dataRowCount; j++) {
			HSSFRow rowContent = sheet.createRow((short) j+2);
			for (int i = 0; i < number; i++) {
				//String value = String.valueOf(list.get(i).get(j));
				exportExcel.createCell(wb, rowContent, (short) i, String.valueOf(list.get(i).get(j)));
			}
			
		}
		
		try {
			exportExcel.download(this.response, this.fileName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	public static void exportExcel(List<String> columnList, List<Map<String, Object>> data, String fileName, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		ExportExcel exportExcel = new ExportExcel(wb, sheet, HSSFCellStyle.ALIGN_CENTER_SELECTION);
		
		/* 写标题行 */
		HSSFRow titleRow = sheet.createRow(0);
		for (int i=0; i<columnList.size(); i++) {
			exportExcel.createCell(wb, titleRow, (short) i, columnList.get(i));
		}
		
		/* 写数据行 */
		for (int i=0; i<data.size(); i++) {
			Map<String, Object> map = data.get(i);
			HSSFRow row = sheet.createRow((short) i+1);
			row.setHeight((short) 500);
			for (int j=0; j<columnList.size(); j++) {
				String key = columnList.get(j);
				String value = null == map.get(key) ? "" : String.valueOf(map.get(key));
				exportExcel.createCell(wb, row, (short) j, value);
			}
		}
		
		try {
			exportExcel.download(response, fileName);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	public static void exportExcelCollect(List<String> columnList, List<Map<String, Object>> data, String fileName, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		ExportExcel exportExcel = new ExportExcel(wb, sheet, HSSFCellStyle.ALIGN_CENTER_SELECTION);
		
		HSSFCellStyle cellStyle= wb.createCellStyle();
		HSSFFont fontHead = wb.createFont();
		fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		fontHead.setFontHeightInPoints((short) 20);// 9号字体
		fontHead.setFontName("宋体");// 宋体
		fontHead.setColor(HSSFColor.BLUE.index);//颜色
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setFont(fontHead);
        sheet.setColumnWidth(0,4000);
        sheet.setColumnWidth(1,3000);
        sheet.setColumnWidth(2,4000);
        sheet.setColumnWidth(3,4000);
        sheet.setColumnWidth(4,5000);
        sheet.setColumnWidth(6,4000);
        sheet.setColumnWidth(8,5000);
        sheet.setColumnWidth(12,5000);
        sheet.setColumnWidth(13,5000);
        sheet.setColumnWidth(14,5000);
        sheet.setColumnWidth(15,5000);
        sheet.setColumnWidth(16,5000);
        sheet.setColumnWidth(17,4000);
        sheet.setColumnWidth(18,4000);
        sheet.setColumnWidth(19,4000);
        sheet.setColumnWidth(20
        		,4000);
		/* 写标题行 */
		HSSFRow titleRow = sheet.createRow(0);
		for (int i=0; i<columnList.size(); i++) {
			exportExcel.createCell(wb, titleRow, (short) i, columnList.get(i));
		}
		
		/* 写数据行 */
		for (int i=0; i<data.size(); i++) {
			Map<String, Object> map = data.get(i);
			HSSFRow row = sheet.createRow((short) i+1);
			row.setHeight((short) 500);
			for (int j=0; j<columnList.size(); j++) {
				String key = columnList.get(j);
				String value = null == map.get(key) ? "" : String.valueOf(map.get(key));
				exportExcel.createCell(wb, row, (short) j, value);
			}
		}
		
		try {
			exportExcel.download(response, fileName);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 传入数据参数为list时，调用此方法生成excel
	 */
	public void createExcelFromList(){
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		ExportExcel exportExcel = new ExportExcel(wb, sheet, HSSFCellStyle.ALIGN_CENTER_SELECTION);
		
		//某一行的数据list
		List<Object> rowDataList = null;
		//数据行数,包括列标题
		int rowCount = this.listData.size();
		//数据列数
		int columnCount = this.listData.get(0).size();
		
		// 给工作表列定义列宽(实际应用自己更改列数)
		for (int i = 0; i < columnCount; i++) {
			sheet.setColumnWidth(i, 4500);
		}
		
		// 创建单元格样式
		//HSSFCellStyle cellStyle = exportExcel.createColumStyle((short)250,false);
	
		// 创建报表头部
		exportExcel.createNormalHead(this.tilte, columnCount);
		
		//循环创建列标题及每行数据
		for (int i = 0; i < rowCount; i++) {
			rowDataList = listData.get(i);
			HSSFRow rowContent = sheet.createRow((short) i+1);
			for (int j = 0; j < columnCount; j++) {
				exportExcel.createCell(wb, rowContent, (short) j, String.valueOf(rowDataList.get(j)));
			}
		}
		
		try {
			//excel下载
			exportExcel.download(this.response, this.fileName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
