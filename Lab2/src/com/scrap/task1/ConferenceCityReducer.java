package com.scrap.task1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
//task1
/**
 * @author Muthaiah
 * ConferenceCityReducer - Class that encapsulates the Reducer function for Task 1 of Lab2
 */
public class ConferenceCityReducer extends
		Reducer<Text, IntWritable, Text, IntWritable> {

	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN, java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
	 */
	public void reduce(Text city, Iterable<IntWritable> values, Context con)
			throws IOException, InterruptedException {

		int cityCount = 0;

		for (IntWritable value : values) {

			cityCount += value.get(); // Sum all the 1s for every city

		}
		//Write the output of the reducer < City, # of conferences>
		con.write(city, new IntWritable(cityCount));

	}

}
