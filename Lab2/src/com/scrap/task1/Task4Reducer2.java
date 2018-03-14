package com.scrap.task1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author Muthaiah
 * Task4Reducer2 - Class that encapsulates the Reducer function for Task 4 of Lab2 (Reduce phase 1)
 */
public class Task4Reducer2 extends
		Reducer<Text, IntWritable, Text, IntWritable> {
	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN, java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
	 */
	public void reduce(Text conference, Iterable<IntWritable> values,
			Context con) throws IOException, InterruptedException {

		int sum = 0; 

		for (IntWritable value : values) {

			sum += value.get(); // sum all the 1s for city, year combination

		}
		//Write the final output with configuration object 
		con.write(conference, new IntWritable(sum));

	}

}
