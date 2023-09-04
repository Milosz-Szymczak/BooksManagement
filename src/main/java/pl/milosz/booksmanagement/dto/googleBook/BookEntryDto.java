package pl.milosz.booksmanagement.dto.googleBook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookEntryDto {
    private String key;
    private GoogleBookDto value;

}
