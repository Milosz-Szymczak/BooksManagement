package pl.milosz.booksmanagement.service.impl.googleBook;

import org.json.JSONArray;
import org.springframework.stereotype.Component;
import pl.milosz.booksmanagement.dto.googleBook.GoogleBookDto;
import pl.milosz.booksmanagement.exception.HttpConnectionException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Component
class GoogleBookConnector {

    private final GoogleBookResponse googleBookResponse;

    public GoogleBookConnector(GoogleBookResponse googleBookResponse) {
        this.googleBookResponse = googleBookResponse;
    }

    public void tryConnectWithGoogleApi(Map<String, GoogleBookDto> allGoogleBooks, String title) {
        HttpURLConnection connection = null;
        try {
            connection = connectWithGoogleApi(title);
            searchGoogleBook(connection, allGoogleBooks);

        } catch (HttpConnectionException | IOException e) {
            throw new HttpConnectionException("Error while communicating with API Google");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private HttpURLConnection connectWithGoogleApi(String title) throws IOException {
        String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + title;
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    private void searchGoogleBook(HttpURLConnection connection,
                                         Map<String, GoogleBookDto> allGoogleBooks) throws IOException {

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            JSONArray items = googleBookResponse.getListOfBooks(connection);

            googleBookResponse.getAllBooks(items, allGoogleBooks);
        } else {
            throw new HttpConnectionException("Error during data retrieval from API Google. Response code: " + responseCode);
        }
    }
}