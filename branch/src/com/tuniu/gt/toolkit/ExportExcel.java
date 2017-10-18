package com.tuniu.gt.toolkit;

import java.io.IOException;
import java.io.OutputStream;

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
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * EXCEL报表工具类.
 * 
 * @author yanjun
 * @version $Revision:$
 */
public class ExportExcel {
	
	private Logger logger = Logger.getLogger(getClass());

	private HSSFWorkbook wb = null;

	private HSSFSheet sheet = null;
	
	private HSSFCellStyle cellStyle = null;

	/**
	 * @param wb
	 * @param sheet
	 */
	public ExportExcel(HSSFWorkbook wb, HSSFSheet sheet, short align) {
		super();
		this.wb = wb;
		this.sheet = sheet;
		
		cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(align);
		cellStyle.setWrapText(true);// 指定单元格自动换行
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
	}

	/**
	 * @return the sheet
	 */
	public HSSFSheet getSheet() {
		return sheet;
	}

	/**
	 * @param sheet
	 *            the sheet to set
	 */
	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	/**
	 * @return the wb
	 */
	public HSSFWorkbook getWb() {
		return wb;
	}

	/**
	 * @param wb
	 *            the wb to set
	 */
	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}

	/**
	 * 设置单元格风格
	 * 
	 * @param fontSize
	 *            文字大小
	 * @param colorFlag
	 *            背景颜色设置
	 */
	public HSSFCellStyle createColumStyle(short fontSize, boolean colorFlag) {

		HSSFCellStyle cellStyle = wb.createCellStyle();

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 设置单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight(fontSize);
		cellStyle.setFont(font);

		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		// 设置单元格背景色
		if (colorFlag) {
			// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			// cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}

		return cellStyle;

	}

	/**
	 * 创建通用EXCEL头部
	 * 
	 * @param headString
	 *            头部显示的字符
	 * @param colSum
	 *            该报表的列数
	 */
	public void createNormalHead(String headString, int colSum) {

		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle cellStyle = createColumStyle((short) 300, true);
		// 设置第一行
		HSSFCell cell = null;
		row.setHeight((short) 400);

		for (int i = 0; i < colSum; i++) {
			cell = row.createCell(i);
			cell.setCellValue("");
			cell.setCellStyle(cellStyle);
		}
		cell = row.getCell(0);
		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString(headString));

		// 指定合并区域
		CellRangeAddress region = new CellRangeAddress(0, 0, 0,
				(short) colSum - 1);
		sheet.addMergedRegion(region);

	}

	/**
	 * 创建通用报表字段列
	 * 
	 * @param params
	 *            统计条件数组
	 * @param rowNum
	 *            需要创建的行
	 * @param colSum
	 *            需要合并到的列索引
	 */
	public void createNormalFieldRow(String[] params, int rowNum, int colSum) {

		HSSFRow row = sheet.createRow(rowNum);

		HSSFCellStyle cellStyle = createColumStyle((short) 250, true);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 指定单元格左对齐
		// 设置第一行
		HSSFCell cell = null;
		row.setHeight((short) 300);

		for (int i = 0; i < colSum; i++) {
			cell = row.createCell(i);
			cell.setCellValue("");
			cell.setCellStyle(cellStyle);
		}
		cell = row.getCell(0);
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString(params[0] + "：" + params[1]));

		// 指定合并区域
		CellRangeAddress region = new CellRangeAddress(rowNum, rowNum, 0,
				(short) colSum - 1);
		sheet.addMergedRegion(region);

	}

	/**
	 * 创建内容单元格
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * @param row
	 *            HSSFRow
	 * @param col
	 *            short型的列索引
	 * @param align
	 *            对齐方式
	 * @param val
	 *            列值
	 */
	public void createCell(HSSFWorkbook wb, HSSFRow row, int col, String val) {
		HSSFCell cell = row.createCell(col);
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString(val));
	}

	/**
	 * 创建合计行
	 * 
	 * @param cellValue
	 *            统计条件数组
	 * @param rowNum
	 *            需要创建的行
	 * @param colSum
	 *            需要合并到的列索引
	 * @param cellValue
	 */
	public void createLastSumRow(int rowNum, int colSum, String[] cellValue) {

		HSSFRow lastRow = sheet.createRow((short) (rowNum));
		HSSFCellStyle cellStyle = createColumStyle((short) 250, false);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 指定单元格右对齐
		// 设置第一行
		HSSFCell cell = null;

		for (int i = 0; i < colSum; i++) {
			cell = lastRow.createCell(i);
			cell.setCellValue("");
			cell.setCellStyle(cellStyle);
		}

		HSSFCell sumCell = lastRow.createCell(0);
		sumCell.setCellStyle(cellStyle);
		sumCell.setCellValue(new HSSFRichTextString(cellValue[0]));

		// 指定合并区域
		CellRangeAddress region = new CellRangeAddress(rowNum, rowNum, 0,
				(short) colSum - 1);
		sheet.addMergedRegion(region);
		// 重新设置风格
		// cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格右对齐
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		// cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 计算合计金额
		sumCell = lastRow.createCell(colSum);
		sumCell.setCellStyle(cellStyle);
		sumCell.setCellValue(new HSSFRichTextString(cellValue[1]));
		// 盖章处
		sumCell = lastRow.createCell(colSum + 1);
		cellStyle.setWrapText(true);// 指定单元格自动换行
		sumCell.setCellStyle(cellStyle);
		sumCell.setCellValue(new HSSFRichTextString(cellValue[2]));

	}

	/**
	 * 文件下载
	 * 
	 * @param response
	 * @param fileName
	 *            文件名称
	 */
	public void download(HttpServletResponse response, String fileName)
			throws IOException {
		OutputStream out = null;
		out = response.getOutputStream();// 取得输出流
		try {
			response.reset();// 清空输出流
			// 设置响应头和下载保存的文件名 modBY YANGJIAN3 导出文件名字支持中文 20140605
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String( fileName.getBytes("gb2312"), "ISO8859-1" ));// 设定输出文件头
			// 定义输出类型
			response.setContentType("application/vnd.ms-excel");
			wb.write(out);
			out.close();
			// 这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
			response.flushBuffer();// 强行将响应缓存中的内容发送到目的地

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}