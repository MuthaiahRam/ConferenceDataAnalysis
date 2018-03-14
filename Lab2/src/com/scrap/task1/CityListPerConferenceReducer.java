package com.scrap.task1;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

//task 3
/**
 * @author Muthaiah 
 * CityListPerConferenceReducer - Class that encapsulates the Reducer fucntion for Task 3 of Lab2
 */
public class CityListPerConferenceReducer extends
		Reducer<Text, Text, Text, Text> {
	Text value = new Text(); // A line from intermediate output of mapper

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN,
	 * java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
	 */
	public void reduce(Text conference, Iterable<Text> values, Context con)
			throws IOException, InterruptedException {

		StringBuilder builder = new StringBuilder();
		for (Text text : values) {
			builder.append(text.toString());
			builder.append(","); // append a comma to values.
		}
		// delete the comma at the end of the string
		builder.setLength(builder.length() - 1);
		value.set(builder.toString());

		// Write output of the reduce phase
		con.write(conference, new Text(value));

	}

}
