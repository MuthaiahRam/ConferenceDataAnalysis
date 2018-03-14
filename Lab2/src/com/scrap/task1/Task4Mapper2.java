package com.scrap.task1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @author Muthaiah
 * Task4Mapper2 - Class that encapsulates the Mapper function for Task 4 of Lab2 (Mapper phase 2)
 */
public class Task4Mapper2 extends Mapper<Object, Text, Text, IntWritable> {

	public void map(Object key, Text value, Context con)
			throws IOException, InterruptedException {

		String line = value.toString();
		String input[] = line.split("\t");
		String city = input[0];
		String conferences[] = input[1].split("#"); //Split with the # added in reduce phase1
		for (int i = 0; i < conferences.length; i++) {
			String year = conferences[i].substring(conferences[i].length() - 4,
					conferences[i].length());
			Text outputKey = new Text(city.concat(year)); // Concatenate city with year for graph plot

			con.write(outputKey, new IntWritable(1)); // For every city,year combination, write 1
		}

	}

}
