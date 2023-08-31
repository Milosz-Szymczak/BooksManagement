package pl.milosz.booksmanagement.service.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.dto.GoogleBookDto;
import pl.milosz.booksmanagement.service.GoogleBookService;

import java.io.BufferedReader;
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
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + title;
            URL url = new URL(apiUrl);

            // Otwieranie połączenia HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Odczytywanie odpowiedzi
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parsowanie JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray items = jsonResponse.getJSONArray("items");

                for (int i = 0; i < items.length(); i++) {
                    JSONObject book = items.getJSONObject(i);
                    String bookID = book.getString("id");
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                    String bookTitle = volumeInfo.getString("title");
                    String bookSubTitle;
                    if (volumeInfo.has("subtitle")) {
                        bookSubTitle = volumeInfo.getString("subtitle");
                    } else {
                        bookSubTitle = "not exist";
                    }
                    JSONArray authors = volumeInfo.getJSONArray("authors");
                    ArrayList<String> bookAuthors = new ArrayList<>();
                    for (int j = 0; j < authors.length(); j++) {
                        bookAuthors.add(authors.getString(j));
                    }
                    JSONArray identifiersArray = volumeInfo.getJSONArray("industryIdentifiers");
                    Long isbn10Identifier = null;
                    for (int j = 0; j < identifiersArray.length(); j++) {
                        JSONObject identifierObject = identifiersArray.getJSONObject(j);
                        if ("ISBN_10".equals(identifierObject.getString("type"))) {
                            isbn10Identifier = identifierObject.getLong("identifier");
                            break;
                        }
                    }
                    String bookPublisherDate = volumeInfo.getString("publishedDate");

                    allGoogleBooks.put(bookID, new GoogleBookDto(bookTitle, bookSubTitle, bookAuthors, bookPublisherDate, bookID, isbn10Identifier));
                }
            } else {
                System.out.println("Błąd podczas pobierania danych: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (Map.Entry<String, GoogleBookDto> entry: allGoogleBooks.entrySet()) {
            System.out.println(entry);
        }
        return allGoogleBooks;
    }
}
