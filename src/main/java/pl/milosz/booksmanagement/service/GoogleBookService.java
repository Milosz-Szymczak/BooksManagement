package pl.milosz.booksmanagement.service;

import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.dto.GoogleBookDto;

import java.io.IOException;
import java.util.Map;

@Service
public interface GoogleBookService {
    Map<String, GoogleBookDto> getAllGoogleBooks(String title) throws IOException;
}
