package com.scrap.task1;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @author Muthaiah
 * Task4Mapper1 -  Class that encapsulates the Mapper function for Task 4 of Lab2 (Mapper phase 1)
 */
public class Task4Mapper1 extends Mapper<Object, Text, Text, Text>{
	
	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	public void map(Object key, Text value, Context con)
			throws IOException, InterruptedException {
		// A line from the map file 
		String line = value.toString();
		// Split the line with a tab spaces
		String conferences[] = line.split("\t");
		//Output key for the intermediate map results - the city
		Text outputKey = new Text(conferences[3].split(",")[0].trim());
		//Write the output with configuration object
		con.write(outputKey, new Text(conferences[0].trim().concat(conferences[1])));

	}

}
