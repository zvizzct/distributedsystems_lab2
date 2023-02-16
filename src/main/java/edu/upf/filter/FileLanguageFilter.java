package edu.upf.filter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import edu.upf.model.SimplifiedTweet;

/**
 * A class that implements the LanguageFilter interface.
 * This class filters the tweets based on the specified language.
 */
public class FileLanguageFilter implements LanguageFilter {

    // The input file path
    private final String inputFile;
    // The output file path
    private final String outputFile;

    /**
     * Constructor that takes the input and output file paths as parameters.
     * 
     * @param inputFile  The path of the input file.
     * @param outputFile The path of the output file.
     */
    public FileLanguageFilter(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    /**
     * The implementation of the filterLanguage method from the LanguageFilter
     * interface.
     * This method filters the tweets based on the specified language.
     * 
     * @param language The language to filter the tweets by.
     * @throws Exception if an error occurs while filtering the language.
     */
    @Override
    public void filterLanguage(String language) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            // Read each line of the input file
            while ((line = reader.readLine()) != null) {
                // Convert the line from JSON to a SimplifiedTweet object
                Optional<SimplifiedTweet> tweet = SimplifiedTweet.fromJson(line);
                // If the tweet is present and its language matches the specified language,
                // write the tweet to the output file
                if (tweet.isPresent() && tweet.get().getLanguage().equals(language)) {
                    writer.write(tweet.get().toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new Exception("An error occurred while filtering the language", e);
        }
    }

}
