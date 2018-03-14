package com.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

@SuppressWarnings("serial")
public class PlotGraphTask4 extends ApplicationFrame {

	public PlotGraphTask4(String applicationTitle, String chartTitle)
			throws NumberFormatException, IOException {
		super(applicationTitle);
		//Bar chart to plot year and number of conferences
		JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Year",
				"No. of Conferences", createDataset(),
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
	}

	/**
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	private CategoryDataset createDataset() throws NumberFormatException,
			IOException {

		ArrayList<String> cityList = new ArrayList<String>(); // An arraylist to hold the list of cities
		ArrayList<Integer> countList = new ArrayList<Integer>();// An arraylist to hold the list of counts
		File file = new File(
				"/home/cloudera/Desktop/Lab2/output1/task4/part-r-000001");

		BufferedReader br = new BufferedReader(new FileReader(file));

		String line;
		// Plot the graph for years 2018,2017,2016,2015
		while ((line = br.readLine()) != null) {
			if ((line.contains("2017") || line.contains("2018")
					|| line.contains("2016") || line.contains("2015") || line
						.contains("2014")) && (line.startsWith("A"))) {
				cityList.add(line.split("\t")[0]);
				countList.add(Integer.valueOf(line.split("\t")[1]));
			}

		}
		br.close();

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i = 0; i < cityList.size() && i < countList.size(); i++) {
			dataset.addValue(countList.get(i),
					cityList.get(i).substring(0, cityList.get(i).length() - 4),
					cityList.get(i).substring(cityList.get(i).length() - 4));
		}
		return dataset;
	}

	/**
	 * @param args
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NumberFormatException,
			IOException {
		PlotGraphTask4 chart = new PlotGraphTask4(
				"No of conference per city per year",
				"Which city had the most?");
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
}
