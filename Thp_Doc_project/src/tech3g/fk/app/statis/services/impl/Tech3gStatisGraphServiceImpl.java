/**
 *
 */
package tech3g.fk.app.statis.services.impl;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import tech3g.common.biz.BaseService;
import tech3g.fk.app.statis.actions.Tech3gStatisSpbGraphBean;
import tech3g.fk.app.statis.services.Tech3gStatisGraphService;

/**
 * @author tech3g
 *
 */
public class Tech3gStatisGraphServiceImpl extends BaseService implements Tech3gStatisGraphService {

	public void initSpbGraph(Tech3gStatisSpbGraphBean viewBean) {

		JFreeChart chart = getBarChart();

		viewBean.setChat(chart);
	}

	private static JFreeChart getBarChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(2, "Marks", "Rahul");
		dataset.setValue(7, "Marks", "Vinod");
		dataset.setValue(4, "Marks", "Deepak");
		dataset.setValue(9, "Marks", "Prashant");
		dataset.setValue(6, "Marks", "Chandan");

		JFreeChart chart = ChartFactory.createBarChart("BarChart using JFreeChart", "Student", "Marks", dataset, PlotOrientation.VERTICAL, false, true, false);
		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setPaint(Color.blue);
		CategoryPlot p = chart.getCategoryPlot();
		p.setRangeGridlinePaint(Color.red);
		return chart;
	}
}
