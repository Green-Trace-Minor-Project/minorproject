package com.brightdata;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // the URL of the target website's home page
        String baseUrl = "https://quotes.toscrape.com";

        // initializing the list of Quote data objects
        // that will contain the scraped data
        List<Quote> quotes = new ArrayList<>();

        // downloading the target website with an HTTP GET request
        Document doc = Jsoup
                .connect(baseUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                .get();

        // looking for the "Next →" HTML element
        Elements nextElements = doc.select(".next");

        // if there is a next page to scrape
        while (!nextElements.isEmpty()) {
            // getting the "Next →" HTML element
            Element nextElement = nextElements.first();

            // extracting the relative URL of the next page
            String relativeUrl = nextElement.getElementsByTag("a").first().attr("href");

            // building the complete URL of the next page
            String completeUrl = baseUrl + relativeUrl;

            // connecting to the next page
            doc = Jsoup
                    .connect(completeUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .get();

            // retrieving the list of product HTML elements
            // selecting all quote HTML elements
            Elements quoteElements = doc.select(".quote");

            // iterating over the quoteElements list of HTML quotes
            for (Element quoteElement : quoteElements) {
                // initializing a quote data object
                Quote quote = new Quote();

                // extracting the text of the quote and removing the
                // special characters
                String text = quoteElement.select(".text").first().text();
                String author = quoteElement.select(".author").first().text();

                // initializing the list of tags
                List<String> tags = new ArrayList<>();

                // iterating over the list of tags
                for (Element tag : quoteElement.select(".tag")) {
                    // adding the tag string to the list of tags
                    tags.add(tag.text());
                }

                // storing the scraped data in the Quote object
                quote.setText(text);
                quote.setAuthor(author);
                quote.setTags(String.join(", ", tags)); // merging the tags into a "A; B; ...; Z" string

                // adding the Quote object to the list of the scraped quotes
                quotes.add(quote);
            }

            // looking for the "Next →" HTML element in the new page
            nextElements = doc.select(".next");
        }

        // initializing the output CSV file
        File csvFile = new File("output.csv");
        // using the try-with-resources to handle the
        // release of the unused resources when the writing process ends
        try (PrintWriter printWriter = new PrintWriter(csvFile, StandardCharsets.UTF_8)) {
            // to handle BOM
            printWriter.write('\ufeff');

            // iterating over all quotes
            for (Quote quote : quotes) {
                // converting the quote data into a
                // list of strings
                List<String> row = new ArrayList<>();

                // wrapping each field with between quotes
                // to make the CSV file more consistent
                row.add("\"" + quote.getText() + "\"");
                row.add("\"" +quote.getAuthor() + "\"");
                row.add("\"" +quote.getTags() + "\"");

                // printing a CSV line
                printWriter.println(String.join(",", row));
            }
        }
    }
}