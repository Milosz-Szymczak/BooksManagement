package pl.milosz.booksmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private long id;
    private String imageLink;
    private String title;
    private String publisher;
    private String author;
    private String kind;
    private String releaseDate;
    private String isbn;
    private String language;
    private boolean confirm;
}