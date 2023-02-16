package edu.upf;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.upf.filter.FileLanguageFilter;
import edu.upf.uploader.S3Uploader;

public class TwitterLanguageFilterApp {
    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);
        String language = argsList.get(0);
        String outputFile = argsList.get(1);
        String bucket = argsList.get(2);
        System.out.println("Language: " + language + ". Output file: " + outputFile +
                ". Destination bucket: " + bucket);

        long start = System.currentTimeMillis();

        for (String inputFile : argsList.subList(3, argsList.size())) {

            System.out.println("Processing: " + inputFile);
            try {
                final FileLanguageFilter filter = new FileLanguageFilter(inputFile, outputFile);
                filter.filterLanguage(language);
                // print how many tweets were filtered
            } catch (Exception e) {
                System.err.println("ERROR filtering the file: " + e.getMessage());
            }

        }

        try {
            final S3Uploader uploader = new S3Uploader(bucket, language, "default");
            uploader.upload(Arrays.asList(outputFile));
        } catch (Exception e) {
            System.err.println("ERROR uploading to S3: " + e.getMessage());
        }

        long end = System.currentTimeMillis();
        System.out.println("Total time taken for processing and uploading: " + (end - start) + "ms");
    }

}
