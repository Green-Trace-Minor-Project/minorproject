package com.brightdata;

public class Quote {
    private String text;
    private String author;
    private String tags;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    // initializing the list of Quote data objects
    // that will contain the scraped data
    List<Quote> quotes = new ArrayList<>();

    // retrieving the list of product HTML elements
    // selecting all quote HTML elements
    Elements quoteElements = doc.select(".quote");

    // iterating over the quoteElements list of HTML quotes
    for (Element quoteElement : quoteElements) {
    // initializing a quote data object
    Quote quote = new Quote();

    // extracting the text of the quote and removing the 
    // special characters
    String text = quoteElement.select(".text").first().text()
        .replace("“", "")
        .replace("”", "");
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
    quote.setTags(String.join(", ", tags)); // merging the tags into a "A, B, ..., Z" string

    // adding the Quote object to the list of the scraped quotes
    quotes.add(quote);
    }
}