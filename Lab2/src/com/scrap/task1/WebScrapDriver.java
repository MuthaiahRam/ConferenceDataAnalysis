package com.scrap.task1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.web.util.WikiCFPScraperTemplate;

/**
 * @author Muthaiah WebScrapDriver - A driver class that creates and executes
 *         Hadoop Jobs for task 1, 2, 3, 4 of Lab2
 */
@SuppressWarnings("unused")
public class WebScrapDriver extends Configured implements Tool {

	private static final String OUTPUT_PATH = "intermediate_output";

	/**
	 * @param args
	 * @throws Exception
	 * Driver method - entry point for the project
	 */
	public static void main(String[] args) throws Exception

	{
		// ToolRunner.run(new Configuration(), new Task1Driver(), args);
		WikiCFPScraperTemplate.crawl();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int run(String[] args) throws Exception {
		// Task 1 of Lab 2
		
		Configuration c = new Configuration();
		Job j = new Job(c, "Job1");

		j.setJarByClass(WebScrapDriver.class); // driver class loading 

		j.setMapperClass(ConferenceCityMapper.class); //mapper class for the Job
 
		j.setReducerClass(ConferenceCityReducer.class); //reducer class for the job

		j.setMapOutputKeyClass(Text.class); //ouput key format of the mapper

		j.setMapOutputValueClass(IntWritable.class); //output value format of the mapper

		j.setOutputKeyClass(Text.class); //ouput key format of the reducer

		j.setOutputValueClass(IntWritable.class); //output value format of the reducer

		FileInputFormat.addInputPath(j, new Path(args[0])); // Input path of the tsv file from command lien arguments

		FileOutputFormat.setOutputPath(j, new Path(args[1] + "/task1")); //Output path in HDFS file system

		j.waitForCompletion(true); // Not to start next job  until this job is completed
		// Task 2 of Lab 2

		Job j1 = new Job(c, "Job2");

		j1.setJarByClass(WebScrapDriver.class);

		j1.setMapperClass(ConferenceCityListMapper.class);

		j1.setReducerClass(ConferenceCityListReducer.class);

		j1.setMapOutputKeyClass(Text.class);

		j1.setMapOutputValueClass(Text.class);

		j1.setOutputKeyClass(Text.class);

		j1.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(j1, new Path(args[0]));

		FileOutputFormat.setOutputPath(j1, new Path(args[1] + "/task2"));

		j1.waitForCompletion(true);
		// Task 3 of Lab 2

		Job j2 = new Job(c, "Job3");

		j2.setJarByClass(WebScrapDriver.class);

		j2.setMapperClass(CityListPerConferenceMapper.class);

		j2.setReducerClass(CityListPerConferenceReducer.class);

		j2.setMapOutputKeyClass(Text.class);

		j2.setMapOutputValueClass(Text.class);

		j2.setOutputKeyClass(Text.class);

		j2.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(j2, new Path(args[0]));

		FileOutputFormat.setOutputPath(j2, new Path(args[1] + "/task3"));

		j2.waitForCompletion(true);

		// Task 4 of Lab 2- Phase 1

		Job j3 = new Job(c, "Job4");

		j3.setJarByClass(WebScrapDriver.class);

		j3.setMapperClass(Task4Mapper1.class);

		j3.setReducerClass(Task4Reducer1.class);

		j3.setMapOutputKeyClass(Text.class);

		j3.setMapOutputValueClass(Text.class);

		j3.setOutputKeyClass(Text.class);

		j3.setOutputValueClass(Text.class);
		// j3.setInputFormatClass(TextInputFormat.class);
		// j3.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(j3, new Path(args[0]));

		FileOutputFormat.setOutputPath(j3, new Path(OUTPUT_PATH));

		j3.waitForCompletion(true);

		// Task 4 of lab 2 - phase 2

		Job j4 = new Job(c, "Job5");

		j4.setJarByClass(WebScrapDriver.class);

		j4.setMapperClass(Task4Mapper2.class);

		j4.setReducerClass(Task4Reducer2.class);

		j4.setMapOutputKeyClass(Text.class);

		j4.setMapOutputValueClass(IntWritable.class);

		j4.setOutputKeyClass(Text.class);

		j4.setOutputValueClass(IntWritable.class);
		// j4.setInputFormatClass(TextInputFormat.class);
		// j4.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(j4, new Path(OUTPUT_PATH));

		FileOutputFormat.setOutputPath(j4, new Path(args[1] + "/task4"));

		return j4.waitForCompletion(true) ? 0 : 1;

	}

}
