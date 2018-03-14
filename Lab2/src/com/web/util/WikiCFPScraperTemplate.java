package com.web.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Muthaiah
 * WikiCFPScraperTemplate - Class that encapsulates the functionality to crawl wikicfp web pages
 */
public class WikiCFPScraperTemplate {
	public static int DELAY = 7; // Delay while crawling the wikicfp web pages

	/**
	 * crawl() - to crawl all the wikicfp web pages for the categories - data
	 * mining, data bases, machine learning and artificial intelligence crawl()
	 * - crawls the web page from page # 1 to page # 20 for each category The
	 * crawled results are written to the file as [Conference Acronym |
	 * Conference Name | Location]
	 */
	public static void crawl() {
		try {
			String[] uriCategory = { "data mining", "databases",
					"machine learning", "artificial intelligence" };
			int numOfPages = 20;
			int firstPage = 1;
			// File to store the output of the crawl
			String fileName = "wikicfp_crawl_" + ".txt";
			Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName), "UTF-8"));
			// File to store if any error occured
			String checkName = "check.txt";
			File file = new File(checkName);
			file.createNewFile();
			BufferedWriter writer2 = new BufferedWriter(new FileWriter(file));
			writer2.write("collect missing data...");
			writer2.newLine();
			for (int k = 0; k < uriCategory.length; k++) {
				// now start crawling the all 'numOfPages' pages from
				// 'firstPage'
				for (int i = firstPage; i <= numOfPages; i++) {
					// Create the request to read the page
					// and get the number of total results
					String linkToScrape = "http://www.wikicfp.com/cfp/call?conference="
							+ URLEncoder.encode(uriCategory[k], "UTF-8")
							+ "&page=" + i;
					String content = getPageFromUrl(linkToScrape);
					// parse or store the content of page 'i' here in 'content'
					ArrayList<List<List<String>>> out = read(i, content);
					for (int i1 = 0; i1 < out.get(0).size(); i1++) {
						for (int i2 = 0; i2 < out.get(0).get(i1).size(); i2++) {
							String tmp = out.get(0).get(i1).get(i2);
							writer.write(tmp);

							if (i2 < out.get(0).get(i1).size() - 1) {
								writer.write("\t");
							}
						}
						writer.write("\n");

					}

					// If data miss occurs
					if (out.size() == 2) {
						for (int i11 = 0; i11 < out.get(1).size(); i11++) {
							for (int i22 = 0; i22 < out.get(1).get(i11).size(); i22++) {
								String tmp2 = out.get(1).get(i11).get(i22);
								writer2.write(tmp2);

								if (i22 < out.get(1).get(i11).size() - 1) {
									writer2.write(" ");
								}
							}
							writer2.newLine();

						}
					}

					Thread.sleep(DELAY * 1000); // Wikicfp courtesy policy for
												// crawling
				}

				writer2.newLine();
				writer2.write("Collecting missing data end.");

			}
			writer.close();
			writer2.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<List<List<String>>> read(int page, String content) {
		ArrayList<List<List<String>>> pack = new ArrayList<List<List<String>>>();

		ArrayList<List<String>> interesting = new ArrayList<List<String>>(); // crawl data
		ArrayList<List<String>> empty = new ArrayList<List<String>>(); // empty data

		// Select the table
		int ini = content.indexOf("table cellpadding=\"3\"");
		int end = content.indexOf("/table", ini);
		int i = 1; // item

		// crawl the table (20 items per page)
		while (true) {
			List<String> element = new ArrayList<String>();
			List<String> emptyOne = new ArrayList<String>();

			// Use URL link to get acronym
			int pre = content.indexOf("a href=", ini);
			// After the table or not find
			if (pre >= end || pre == -1) {
				break;
			}
			pre = content.indexOf(">", pre);
			int post = content.indexOf("</", pre);
			String acronym = content.substring(pre + 1, post).trim();

			element.add(acronym);
			// Check acronym
			String tmp1 = page + "_" + i + "_acronym";
			if (acronym.equals("")) {
				emptyOne.add(tmp1);
			}

			// Get name
			pre = content.indexOf("td align=\"left\"", post);
			pre = content.indexOf(">", pre);
			post = content.indexOf("</", pre);
			String name = content.substring(pre + 1, post).trim();
			element.add(name);

			// Check name
			String tmp2 = page + "_" + i + "_name";
			if (name.equals("")) {
				emptyOne.add(tmp2);
			}

			// get location
			pre = content.indexOf("td align=\"left\"", post);
			pre = content.indexOf("td align=\"left\"", pre + 1);
			pre = content.indexOf(">", pre);
			post = content.indexOf("</", pre);
			String location = content.substring(pre + 1, post).trim();
			element.add(location);

			// Check location
			String tmp3 = page + "_" + i + "_location";
			if (location.equals("")) {
				emptyOne.add(tmp3);
			}

			// If location is 'N/A', it is a journal
			if (!location.equals("N/A")) {
				interesting.add(element);
				if (!emptyOne.isEmpty()) {
					empty.add(emptyOne);
				}
			}

			ini = post;
			i++;
		}

		pack.add(interesting);
		if (!empty.isEmpty()) {
			pack.add(empty);
		}

		return pack;
	}

	/**
	 * Given a string URL returns a string with the page contents Adapted from
	 * example in
	 * http://docs.oracle.com/javase/tutorial/networking/urls/readingWriting
	 * .html
	 * 
	 * @param link
	 * @return
	 * @throws IOException
	 */
	public static String getPageFromUrl(String link) throws IOException {
		URL thePage = new URL(link);
		URLConnection yc = thePage.openConnection();
		// Change encoding to 'UTF-8'
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream(), "UTF-8"));
		String inputLine;
		String output = "";
		while ((inputLine = in.readLine()) != null) {
			output += inputLine + "\n";
		}
		in.close();
		return output;
	}

}