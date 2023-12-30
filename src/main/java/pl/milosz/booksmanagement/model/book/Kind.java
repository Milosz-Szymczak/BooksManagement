package pl.milosz.booksmanagement.model.book;

import lombok.Getter;


@Getter
public enum Kind {
    OTHER("Other"),
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    SCIENCE_FICTION("Science Fiction"),
    THRILLER("Thriller"),
    ROMANCE("Romance"),
    FANTASY("Fantasy"),
    SELF_HELP("Self-Help"),
    BIOGRAPHY_AUTOBIOGRAPHY("Biography/Autobiography"),
    HISTORY("History"),
    BUSINESS_ECONOMICS("Business/Economics");

    private final String displayName;
    Kind(String displayName) {
        this.displayName = displayName;
    }
}
