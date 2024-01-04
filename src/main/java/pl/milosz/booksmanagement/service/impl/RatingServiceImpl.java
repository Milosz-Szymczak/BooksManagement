package pl.milosz.booksmanagement.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.booksmanagement.model.book.Rating;
import pl.milosz.booksmanagement.repository.RatingReposotory;
import pl.milosz.booksmanagement.service.RatingService;

import java.util.List;

@Service
class RatingServiceImpl implements RatingService {

    private final RatingReposotory ratingReposotory;

    public RatingServiceImpl(RatingReposotory ratingReposotory) {
        this.ratingReposotory = ratingReposotory;
    }

    @Override
    public List<Rating> getRatingsBooks() {
        return ratingReposotory.findAll();
    }
}
