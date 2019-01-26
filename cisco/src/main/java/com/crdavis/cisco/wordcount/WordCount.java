package com.crdavis.cisco.wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * "Quick and dirty" program to count word occurrences in a text file
 * @author charlie.davis
 *
 */
public class WordCount {

	public static void main (String... args) {
		if (args.length == 0) {
			System.err.println("path name of file must be provided as first and only argument");
			return;
		}

		File inputFile = new File(args[0]);
		if (!inputFile.canRead()) {
			System.err.println("file is not readable");
		}

		Map<String, Integer> wordFrequency = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

			while (line != null) {
				countWords(wordFrequency, line);
				line = reader.readLine();
			}
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}

		List<String> wordsFound = wordFrequency.keySet().stream().sorted().collect(Collectors.toList());
		for (String word : wordsFound) {
			System.out.printf("%s: %d\n", word, wordFrequency.get(word));
		}
	}

	/**
	 * Count occurrences of words in a line of text.
	 * @param wordFrequency map of word frequencies in which to accumulate counts
	 * @param line the line of text to be scanned
	 */
	static void countWords(Map<String, Integer> wordFrequency, String line) {
		// We rely on the regular expression definition of a word -- quick and dirty after all
		String[] words = line.split("\\W+");
		for (String word : words) {
			if (word.isEmpty())
				continue;	// If there are no words on the line, split() returns an array containing one empty string
			// Fold everything to one case
			word = word.toLowerCase();
			if (!wordFrequency.containsKey(word))
				wordFrequency.put(word, 1);
			else
				wordFrequency.put(word, wordFrequency.get(word) + 1);
		}
	}
}
