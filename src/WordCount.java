/** Start of program**/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WordCount {
	
	private static String inputDirectory;
	private static String outputDirectory;
	private static Map<String,Integer> wordCounts = new TreeMap<String, Integer>();
	
	public static void main(String[] args) throws Exception{
		inputDirectory = "../wc_input";
		outputDirectory = "../wc_output";
		printWordCount();
	}
	
	/**
	 * Print the word counts in the mentioned directory
	 * @throws Exception
	 */
	private static void printWordCount()  throws Exception  {
		fetchWordCounts();//fetching the counts
		FileWriter fstream = new FileWriter(outputDirectory+"/wc_result.txt");
	    BufferedWriter out = new BufferedWriter(fstream);
	    Set<String> keys = wordCounts.keySet();
	    for(String key: keys){
	    	out.write(key+" \t\t\t "+wordCounts.get(key)+"\n");
        }
	    out.close();
	}
	
	
	/**
	 * Method to fetch the word counts for per line
	 * @return List of words per median
	 * @throws Exception
	 */
	private static Map<String,Integer> fetchWordCounts() throws Exception {
		//Fetch sorted list of all the file in the input directory
		String[] sortedFileList = fetchAllFiles();
		
		//Iterating through the file one by one in sorted order
		for (String file : sortedFileList) {
			try {
				//Reading first line of the file and storing the count of words per line
				FileInputStream fis = new FileInputStream(inputDirectory+"/"+file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String line = null;
				while ((line = br.readLine()) != null) {
					String[] wordCount = line.split(" ");
					for(String word : wordCount) {
						word = word.replaceAll("[^a-zA-Z]", "");
						word = word.toLowerCase();
						if (wordCounts.containsKey(word)){
							Integer value = wordCounts.get(word);
							wordCounts.put(word, value+1);
						} else {
							wordCounts.put(word, 1);
						}		
					}
				}
				br.close();
			} catch (Exception e) {
				System.out.println("Excetion while reading file " + file);
			}
		}
		
		//Calculating the median
		return wordCounts;
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
