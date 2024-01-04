package pl.milosz.booksmanagement.service.impl.googleBook;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import pl.milosz.booksmanagement.dto.googleBook.GoogleBookDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
class GoogleBookResponse {
    public JSONArray getListOfBooks(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getJSONArray("items");
    }

    public void getAllBooks(JSONArray items, Map<String, GoogleBookDto> allGoogleBooks) {
        for (int i = 0; i < items.length(); i++) {
            JSONObject book = items.getJSONObject(i);
            String bookID = book.has("id") ? book.getString("id") : "";

            JSONObject volumeInfo = book.has("volumeInfo") ? book.getJSONObject("volumeInfo") : new JSONObject();
            String bookTitle = volumeInfo.has("title") ? volumeInfo.getString("title") : "";
            String bookSubTitle = volumeInfo.has("subtitle") ? volumeInfo.getString("subtitle") : "";
            String publisher = volumeInfo.has("publisher") ? volumeInfo.getString("publisher") : "";
            String languageBook = volumeInfo.has("language") ? volumeInfo.getString("language") : "";
            String bookPublisherDate = volumeInfo.has("publishedDate") ? volumeInfo.getString("publishedDate") : "";
            JSONObject imageLinks = volumeInfo.has("imageLinks") ? volumeInfo.getJSONObject("imageLinks") : new JSONObject();
            String imageLink = imageLinks.has("thumbnail") ? imageLinks.getString("thumbnail") : "";

            List<String> categoriesOfBook = getCategories(volumeInfo);
            List<String> bookAuthors = getAuthors(volumeInfo);
            String isbn = getISBN(volumeInfo);

            allGoogleBooks.put(bookID, new GoogleBookDto(imageLink, bookTitle, bookSubTitle, publisher, categoriesOfBook,
                    bookAuthors, bookPublisherDate, isbn, languageBook));
        }
    }

    private static List<String> getCategories(JSONObject volumeInfo) {
        JSONArray categories = volumeInfo.has("categories") ? volumeInfo.getJSONArray("categories") : new JSONArray();
        List<String> categoriesOfBook = new ArrayList<>();
        if (!categories.isEmpty()) {
            for (int j = 0; j < categories.length(); j++) {
                categoriesOfBook.add(categories.getString(j));
            }
        }
        return categoriesOfBook;
    }

    private static List<String> getAuthors(JSONObject volumeInfo) {
        List<String> bookAuthors = new ArrayList<>();
        JSONArray authors = volumeInfo.has("authors") ? volumeInfo.getJSONArray("authors") : new JSONArray();
        if (!authors.isEmpty()) {
            for (int j = 0; j < authors.length(); j++) {
                bookAuthors.add(authors.getString(j));
            }
        }
        return bookAuthors;
    }

    public String getISBN(JSONObject volumeInfo) {
        JSONArray identifiersArray = volumeInfo.has("industryIdentifiers") ?
                volumeInfo.getJSONArray("industryIdentifiers") : new JSONArray();

        String isbn10Identifier = null;
        for (int j = 0; j < identifiersArray.length(); j++) {
            JSONObject identifierObject = identifiersArray.getJSONObject(j);

            if ("ISBN_10".equals(identifierObject.getString("type"))) {
                isbn10Identifier = identifierObject.getString("identifier");
                break;
            }
        }
        return isbn10Identifier;
    }
}