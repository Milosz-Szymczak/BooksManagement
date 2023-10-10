package pl.milosz.booksmanagement.dto.googleBook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleBookDto {
    private String id;
    private String title;
    private String subTitle;
    private String publisher;
    private ArrayList<String> authors;
    private String publishedDate;
    private String isbn;
    private String language;
}
