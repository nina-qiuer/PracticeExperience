/**
 * 
 */
package com.tuniu.gt.toolkit;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import org.apache.struts2.ServletActionContext;

/**
 * @author jiangye
 * 
 */
public class POIUtil<T> {
	
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param headers
	 *            表格属性列名集合
	 * @param exportFields
	 * 			   输出的bean中的字段集合
	 * @param dataset
	 *            导出到excel中的数据集合
	 * @param cellDataMapperHandler
	 *            单元格数据映射处理，有特殊转换规则的字段需要实现回调方法。
	 */          
	@SuppressWarnings("unchecked")
	public void exportExcel(List<String> headers, List<String> exportFields,
			Collection<T> dataset,CellDataMapperHandler  cellDataMapperHandler)
			throws Exception {
		
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet();
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		HSSFCellStyle headStyle = workbook.createCellStyle();
		// 设置这些样式
		headStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont headFont = workbook.createFont();
		headFont.setColor(HSSFColor.VIOLET.index);
		headFont.setFontHeightInPoints((short) 12);
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		headStyle.setFont(headFont);
		// 生成并设置另一个样式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont cellFont = workbook.createFont();
		cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		cellStyle.setFont(cellFont);

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(headStyle);
			HSSFRichTextString text = new HSSFRichTextString(headers.get(i));
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		Field[] fields = null;
		List<String> fieldNameList = new ArrayList<String>();
        while(it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            if(fields == null) {
                fields = t.getClass().getDeclaredFields();
                for(Field field : fields) {
                    fieldNameList.add(field.getName());
                }
            }
            
            int cellIndex = 0;

            // 利用反射，根据exportFields列表属性的先后顺序，动态调用getXxx()方法得到属性值
            for(String cellField : exportFields) {
                if(fieldNameList.contains(cellField) || "id".equals(cellField)) {
                    HSSFCell cell = row.createCell(cellIndex++);
                    cell.setCellStyle(cellStyle);

                    String getMethodName = "get" + cellField.substring(0, 1).toUpperCase() + cellField.substring(1);

                    Class tCls = t.getClass();
                    if("id".equals(cellField)) {
                        tCls = tCls.getSuperclass();
                    }
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                  
                    // 点睛之笔啊
                   textValue = cellDataMapperHandler.mapToCell(cellField, value);
                    

                    HSSFRichTextString richString = new HSSFRichTextString(textValue);
                    HSSFFont richFont = workbook.createFont();
                    richFont.setColor(HSSFColor.BLUE.index);
                    richString.applyFont(richFont);
                    cell.setCellValue(richString);
                }
            }

        }
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String fileName = "list"+ (new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".xls";
		OutputStream out = null;
		try {
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="+ fileName);
			response.setContentType("application/vnd.ms-excel");
			out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
			response.flushBuffer();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			
		}
		

	}
}
