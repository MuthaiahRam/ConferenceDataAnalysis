# ConferenceDataAnalysis
Crawl websites for conference data for analysis

This homework task is accomplished using Cloudera VM. This sandbox is equipped with Hadoop Map Reduce environment.
IDE used – Eclipse
JDK 1.8
Jfreechart library to plot the graph

Commands: 

To get the top 10 records 

cat part-r-000001| awk '{FS=" ";$0=$0; print $NF"|"$0}'| sort -n -r|cut -d"|" -f2 

To execute the Hadoop Program 

hadoop jar "/home/cloudera/Desktop/Lab_2/Lab.jar" com.scrap.task1.Task1Driver /user/cloudera/lab2/input /user/cloudera/lab2/output1

Part 1 - Crawler
==================
Web crawler is a program that acts as an automated script which browses through the internet in a systematic way. The web crawler looks at the keywords in the pages, the kind of content each page has and the links, before returning the information to the search engine. This process is known as Web crawling. A web crawler gathers pages from the web and then, indexes them in a methodical and automated manner to support search engine queries.

Description:
==========
Initially, as given in the Homework page, I tried to install the Apache Nutch tool on Cloudera’s Virtual Machine used for Lab1. But, there was environment issue in setting up the config file. So, I developed a web crawler is developed and customized to crawl the Wificfp website and fetch results for categories,
1. Bigdata
2. Datamining
3. Databases
4. Artificial Intelligence

Procedure:
==========
The Web Crawler is implemented to crawl the webpage of the given URI and return the HTML content. From the HTML content, the Java code will parse the tags and retrieve the appropriate contents.

Part 2- Data Cleaning
=======================
The data from the crawler will be with some noise and unwanted contents. To cleanse the data, we use a tool called Open Refine.

Open Refine
===========
OpenRefine (formerly Google Refine) is a powerful tool for working with messy data: cleaning it; transforming it from one format into another; and extending it with web services and external data. OpenRefine can help explore large data sets with ease. OpenRefine can be used to link and extend your dataset with various webservices. Some services also allow OpenRefine to upload your cleaned data to a central database.

Description
============
1. The data has location as NA for conferences. Those rows are removed.
2. There are data that has location detail in one of the below formats. 
Format 1: city, state, country 
Format 2: city 
Format 3: country 
Format 4: city, state 
Format 5: city, country If location has more than one field (Format 1,4,5), the first field is considered as city. 
Nevertheless, if the location has just one data (say either city or country), we do not have a dictionary to differentiate if the field is a city or a country. So, we ignore those data as well using open refine. 3. The first column, the acronym of the conference contains the year as well. This column is split and made into two columns one containing the abbreviation of the conference and the other containing the year of the conference.

1. Compute and plot the number of conferences per city. Which are the top 10 locations.

Map phase:
We parse the TSV file containing the data. We split and get the city name and write it as the key. The value is written as 1, meaning the count.
Reduce Phase:
For every city, count the number of occurrences from the output of map.



2. Output the list of conferences per city

Map phase:
We parse the TSV file containing the data. We split and get the city name and write it as the key. The value is written as conference name.
Reduce Phase:
For every city, we concatenate the conference names as a list from the output of map.

3. For each conference regardless of the year (e.g., KDD), output the list of cities.

Map phase:We parse the TSV file containing the data. We split the conference name without considering the year in it and write it as the key. The value is written as city name.
Reduce Phase:
For every conference, we concatenate the city names separated by a comma as a list from the output of map.


4. For each city compute and plot a time series of #conferences per year.

This task has 2 parts. I learnt “Chaining map reduce jobs”. So, for this task, the flow is

[Input] Map1 --> Reduce1 --> Map2 --> Reduce2 [Output]
We have three inputs here. The city, the year and the name of the conference.

Map Phase 1:
We split and take the city from the TSV file input line and write this a key. For the value, We split and take the conference name and concatenate with the year from the TSV input file. This is written as an intermediate output in the Map phase.
Reduce Phase 1:
In the reduce phase, for every city as key in the map output, we concatenate the corresponding values and write as <city, list of conferences+year> as output of the reducer.

Map Phase 2:
From the output of the previous reduce phase, we read a line from the file (The file is kept in the HDFS itself). For every line, we fetch the city and concatenate with the year (substring from the conference field (we concatenated in first map phase)) and written as key and value is written as 1, meaning the count.
Reduce Phase 2:
In this phase, for each conference, every year, we add up all the 1s and write as the value. This output contains the answer for this task.

Now this output file is brought to the local file system from the HDFS and given as input for the graph plotting phase.

Graph Plotting phase
====================
JFreechart is a library open sourced to plot the graphs. Downloaded the latest version of JFreeChart.zip from the link
http://www.jfree.org/jfreechart/download/

The dataset for the plot must be prepared for x axis and y axis.
When the plot() method is called the graph gets plotted as an applet.

Lessons learnt
===============
1. Apache Nutch did not work as expected as the config file set up was difficult on cloudera machine.
2. Initially, I had developed multiple driver classes in the same project, one for each task. I learn the concepts of “Chaining Map reduce jobs” and making the map reduce job dependent on another job. Now I just have only one driver class and when trigger the command to start Job J1, all other Jobs J2 till J5 are executed in a sequence.
3. Learnt to use JFreeChart for plotting the graph.