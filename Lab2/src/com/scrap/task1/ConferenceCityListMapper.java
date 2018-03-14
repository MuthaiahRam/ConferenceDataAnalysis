package com.scrap.task1;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
//task 2
/**
 * @author Muthaiah
 * ConferenceCityListMapper - Class that encapsulates the Mapper fucntion for Task 2 of Lab2
 */
public class ConferenceCityListMapper extends Mapper<LongWritable, Text, Text, Text>{
	
	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	public void map(LongWritable key, Text value, Context con)
			throws IOException, InterruptedException {

		String line = value.toString(); // A line in the input tsv file
		String conference[] = line.split("\t"); // Split the line by tab spaces to get 4 fields
		

		Text outputKey = new Text(conference[3].split(",")[0].trim()); // The 0th field from the input file - city
		//Write the output of the <city: conf title>
		con.write(outputKey, new Text(conference[2].trim()));// The 2nd field from the input file - Conference Title 

	}

}
