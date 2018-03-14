package com.scrap.task1;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @author Muthaiah
 * CityListPerConferenceMapper - Class that encapsulates the Mapper fucntion for Task 3 of Lab2
 * 
 */
public class CityListPerConferenceMapper extends Mapper<LongWritable, Text, Text, Text>{
	
	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	public void map(LongWritable key, Text value, Context con)
			throws IOException, InterruptedException {

		String line = value.toString(); // A line in the input file (tsv format)
		String conferences[] = line.split("\t");
		//Form the key for the output of map phase
		Text outputKey = new Text(conferences[0].trim()); // The 0th column - conference acronym 
		//Write intermediate output from mapper <key, value>
		con.write(outputKey, new Text(conferences[3].split(",")[0].trim())); // The 3rd column, the city

	}

}
