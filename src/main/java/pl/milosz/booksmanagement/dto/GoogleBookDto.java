package pl.milosz.booksmanagement.dto;

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
    private String title;
    private String subTitle;
    private ArrayList<String> authors;
    private String publishedDate;
    private String id;
    private String isbn;
}
