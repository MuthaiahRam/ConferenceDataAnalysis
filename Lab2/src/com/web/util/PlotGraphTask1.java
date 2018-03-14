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

/**
 * @author Muthaiah
 * PlotGraphTask1- This class encapsulates the functionality to plot graph for task1
 */
@SuppressWarnings("serial")
public class PlotGraphTask1 extends ApplicationFrame {

	/**
	 * @param applicationTitle
	 * @param chartTitle
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public PlotGraphTask1(String applicationTitle, String chartTitle) throws NumberFormatException, IOException {
		super(applicationTitle);
		//Bar chart for plot city vs number of conferences
		JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "City Name",
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
	private CategoryDataset createDataset() throws NumberFormatException, IOException {
		ArrayList<String> cityList = new ArrayList<String>(); // to hold the cities
		ArrayList<Integer> countList = new ArrayList<Integer>(); // to hold the count of conferences
		File file = new File("/home/cloudera/Desktop/Lab2/output1/task1/part-r-000001"); //file location

		  BufferedReader br = new BufferedReader(new FileReader(file));

		  String line;
		  int count=0;
		  while ((line = br.readLine()) != null && count <10){
		    cityList.add(line.split("\t")[0]);
		  countList.add(Integer.valueOf(line.split("\t")[1]));
		  count++;
		  }
		  br.close();
		final String city = "city";
		
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		// Add all the cities and count to the dataset used for plotting graph
		for(int i=0;i<cityList.size()&& i< countList.size();i++){
			dataset.addValue(countList.get(i), city, cityList.get(i));
		}

		return dataset;
	}
	
	 /**
	 * @param args
	 * @throws NumberFormatException
	 * @throws IOException
	 * Driver function to call the plot functionality
	 */
	public static void main( String[ ] args ) throws NumberFormatException, IOException {
		 PlotGraphTask1 chart = new PlotGraphTask1("No of conference per city", 
	         "Which city had the most?");
	      chart.pack( );        
	      RefineryUtilities.centerFrameOnScreen( chart );        
	      chart.setVisible( true ); 
		
	   }
}
