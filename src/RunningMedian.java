/** Start of program**/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RunningMedian {

	private static String inputDirectory;
	private static String outputDirectory;
	
	public static void main(String[] args) throws Exception{
		inputDirectory = "../wc_input";
		outputDirectory = "../wc_output";

	fetchMedian();
	}

	private static void fetchMedian()  throws Exception  {
		FileWriter fstream = new FileWriter(outputDirectory+"/med_result.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		for (Float floatValue : calculateMedian()) {
			out.write(floatValue.toString()+"\n");
		}
		out.close();
	}
	
	/**
	* Method to calculate the median
	* @return List of medians
	* @throws Exception
	*/

	private static List<Float> calculateMedian() throws Exception {
		List<Float> medianValues = new ArrayList<Float>();
		List<Integer> wordCountPerline = fetchWordCounts();
		int counter = 0;
		for (int i=counter;i<=wordCountPerline.size()-1;i++) {
			//iterating the list from i to 0
			List<Integer> intermediateList = new ArrayList<Integer>();
			for (int j=i;j>=0;j--) {
				intermediateList.add(wordCountPerline.get(j));
			}
			//Calculating median one by one for every lines
			if(intermediateList != null){
				Float k = null; 
				Collections.sort(intermediateList);
				int size = intermediateList.size();
				if (size % 2 == 0) {//if even, get average of two middle values
					k = (intermediateList.get((size / 2) - 1) + intermediateList.get(size / 2)) / 2f;
				} else {//if odd, get the middle vales
					k = intermediateList.get(size / 2) / 1f;
				}
				medianValues.add(k);
			}
		}
		return medianValues;
	}

	/**
	* Method to fetch the word counts for per line
	* @return List of words per median
	* @throws Exception
	*/

	private static List<Integer> fetchWordCounts() throws Exception {
		//Fetch sorted list of all the file in the input directory
		String[] sortedFileList = fetchAllFiles();
		List<Integer> wordCountPerline = new ArrayList<Integer>();
		//Iterating through the file one by one in sorted order
		for (String file : sortedFileList) {
			try {
				//Reading first line of the file and storing the count of words per line
				FileInputStream fis = new FileInputStream(inputDirectory+"/"+file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String line = null;
				while ((line = br.readLine()) != null) {
					String[] wordCount = line.split(" ");
					wordCountPerline.add(wordCount.length);
				}
			br.close();
			} catch (Exception e) {
				System.out.println("Excetion while reading file " + file);
			}
		}

		//Calculating the median
		return wordCountPerline;
	}

	/**
	* Fetch sorted list of all the file in the input directory
	* @return List of sorted file names
	* @throws Exception
	*/

	private static String[] fetchAllFiles() throws Exception{
		File dir = new File(inputDirectory);
		//fetching all the files in the directory
		String[] fileArray = dir.list();
		Arrays.sort(fileArray);
		return fileArray;
	}
}

/** End of program**/
