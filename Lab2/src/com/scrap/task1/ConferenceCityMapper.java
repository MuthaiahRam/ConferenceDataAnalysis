package com.scrap.task1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//Task1
/**
 * @author Muthaiah
 * ConferenceCityMapper - Class that encapsulates the Mapper function for Task 1 of Lab2
 */
public class ConferenceCityMapper extends
		Mapper<LongWritable, Text, Text, IntWritable> {

	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	public void map(LongWritable key, Text value, Context con)
			throws IOException, InterruptedException {

		String line = value.toString(); // A line in the input file
		String city = line.split("\t")[3]; // Split the line with tab spaces & the 4th field - city

		Text outputKey = new Text(city.split(",")[0].trim()); // The 0th field - the conference acronym
		// Write the output of the mapper < Conference , City>
		con.write(outputKey, new IntWritable(1)); // the count 1 

	}

}
