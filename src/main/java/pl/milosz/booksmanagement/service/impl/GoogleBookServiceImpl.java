package pl.milosz.booksmanagement.service.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.dto.googleBook.GoogleBookDto;
import pl.milosz.booksmanagement.service.GoogleBookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleBookServiceImpl implements GoogleBookService {

    @Override
    public Map<String, GoogleBookDto> getAllGoogleBooks(String title) {

        Map<String, GoogleBookDto> allGoogleBooks = new HashMap<>();
        try {
            HttpURLConnection connection = connectWithGoogleApi(title);

            searchGoogleBook(connection, allGoogleBooks);

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allGoogleBooks;
    }

    private static void searchGoogleBook(HttpURLConnection connection, Map<String, GoogleBookDto> allGoogleBooks) throws IOException {

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            JSONArray items = getListOfBooks(connection);

            getAllBooks(items, allGoogleBooks);
        } else {
            System.out.println("Błąd podczas pobierania danych: " + responseCode);
        }
    }

    private static HttpURLConnection connectWithGoogleApi(String title) throws IOException {
        String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + title;
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    private static JSONArray getListOfBooks(HttpURLConnection connection) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONArray items = jsonResponse.getJSONArray("items");
        return items;
    }

    private static void getAllBooks(JSONArray items, Map<String, GoogleBookDto> allGoogleBooks) {

        for (int i = 0; i < items.length(); i++) {
            JSONObject book = items.getJSONObject(i);

            String bookID = book.has("id") ? book.getString("id") : "";
            JSONObject volumeInfo = book.has("volumeInfo") ? book.getJSONObject("volumeInfo") : new JSONObject();
            String bookTitle = volumeInfo.has("title") ? volumeInfo.getString("title") : "";
            String bookSubTitle = volumeInfo.has("subtitle") ? volumeInfo.getString("subtitle") : "";
            String publisher = volumeInfo.has("publisher") ? volumeInfo.getString("publisher") : "";
            ArrayList<String> bookAuthors = new ArrayList<>();
            JSONArray authors = volumeInfo.has("authors") ? volumeInfo.getJSONArray("authors") : new JSONArray();
            if (!authors.isEmpty()) {
                for (int j = 0; j < authors.length(); j++) {
                    bookAuthors.add(authors.getString(j));
                }
            }

            JSONArray identifiersArray = volumeInfo.has("industryIdentifiers") ?
                    volumeInfo.getJSONArray("industryIdentifiers") : new JSONArray();

            String languageBook = volumeInfo.has("language") ? volumeInfo.getString("language") : "";

            String isbn10Identifier = null;
            for (int j = 0; j < identifiersArray.length(); j++) {
                JSONObject identifierObject = identifiersArray.getJSONObject(j);

                if ("ISBN_10".equals(identifierObject.getString("type"))) {
                    isbn10Identifier = identifierObject.getString("identifier");
                    break;
                }
            }
            String bookPublisherDate = volumeInfo.has("publishedDate") ?
                    volumeInfo.getString("publishedDate") : "";

            allGoogleBooks.put(bookID, new GoogleBookDto(bookID, bookTitle, bookSubTitle, publisher, bookAuthors, bookPublisherDate, isbn10Identifier, languageBook));
        }
    }
}
