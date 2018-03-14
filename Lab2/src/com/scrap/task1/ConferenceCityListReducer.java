package com.scrap.task1;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

//task 2
/**
 * @author Muthaiah
 * ConferenceCityListReducer - Class that encapsulates the Reducer fucntion for Task 2 of Lab2
 */
public class ConferenceCityListReducer extends Reducer<Text, Text, Text, Text> {
	Text value = new Text();

	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN, java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
	 */
	public void reduce(Text city, Iterable<Text> values, Context con)
			throws IOException, InterruptedException {
		StringBuilder builder = new StringBuilder();
		for (Text text : values) {
			builder.append(text.toString());
			builder.append("\t"); // Concatenate all conference names  
		}
		value.set(builder.toString());
		//Write the output of the reduce phase - <City, List of conference titles>
		con.write(city, new Text(value));

	}

}
