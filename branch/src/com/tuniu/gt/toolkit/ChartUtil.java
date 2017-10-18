package com.tuniu.gt.toolkit;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.vo.ChartVo;

/**
 * 图表生成工具
 * 
 * @author WangMingFang
 * @version 1.0
 * @since 20140226
 */
public class ChartUtil {
	
	private static Logger logger = Logger.getLogger(ChartUtil.class);

	/**
	 * 生成3D柱状图
	 */
	public static String createBarChart3D(ChartVo vo) {
		logger.info("createBarChart3D Begin, ChartVo Title is " + vo.getTitle());
		
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN"); // 创建主题样式
        standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20)); // 设置标题字体
        standardChartTheme.setRegularFont(new Font("宋体",Font.PLAIN,15)); // 设置图例的字体
        standardChartTheme.setLargeFont(new Font("宋体",Font.PLAIN,15)); // 设置轴向的字体
        ChartFactory.setChartTheme(standardChartTheme);// 应用主题样式

		CategoryDataset dataset = 
			DatasetUtilities.createCategoryDataset(vo.getRowKeys(), vo.getColumnKeys(), vo.getData());

		JFreeChart chart = ChartFactory.createBarChart3D(vo.getTitle(), vo.getxAxisName(), vo.getyAxisName(), 
				dataset, PlotOrientation.VERTICAL, true, true, false);

		CategoryPlot plot = chart.getCategoryPlot();

		plot.setBackgroundPaint(Color.white); // 设置网格背景颜色

		plot.setDomainGridlinePaint(Color.pink); // 设置网格竖线颜色

		plot.setRangeGridlinePaint(Color.pink); // 设置网格横线颜色

		BarRenderer3D renderer = new BarRenderer3D(); // 显示每个柱的数值，并修改该数值的字体属性
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);

		/*
		 * 默认的数字显示在柱子中，通过如下两句可调整数字的显示 
		 * 注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
		 */
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);

		/* 设置每个地区所包含的平行柱的之间距离 */
		renderer.setItemMargin(0.4);
		plot.setRenderer(renderer);

		logger.info("createBarChart3D Ing, tempFile is " + System.getProperty("java.io.tmpdir"));
		String filename = "CreateError.png";
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, vo.getWidth(), vo.getHight(), null, null);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		String graphURL = Constant.CONFIG.getProperty("web_url") + "DisplayChart?filename=" + filename;
		
		logger.info("createBarChart3D End, graphURL is " + graphURL);

		return graphURL;
	}

}
