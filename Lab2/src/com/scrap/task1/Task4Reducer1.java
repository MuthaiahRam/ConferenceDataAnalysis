package com.scrap.task1;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author Muthaiah
 * Task4Reducer1 - Class that encapsulates the Reducer function for Task 4 of Lab2 (Reduce phase 1)
 */
public class Task4Reducer1 extends Reducer<Text, Text, Text, Text> {
	Text value = new Text();

	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN, java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
	 */
	public void reduce(Text city, Iterable<Text> values, Context con)
			throws IOException, InterruptedException {

		StringBuilder builder = new StringBuilder();
		for (Text text : values) {
			builder.append(text.toString());
			builder.append("#"); // Append with a # for use in the second map phase
		}
		value.set(builder.toString());
		// Write the output with configuration object
		con.write(city, new Text(value));

	}

}
