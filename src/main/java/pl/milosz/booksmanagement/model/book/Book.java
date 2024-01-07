package pl.milosz.booksmanagement.model.book;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "author", nullable = false)
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind", nullable = false)
    private Kind kind;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "confirm", nullable = false)
    private boolean confirm;

}
