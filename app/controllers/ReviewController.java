package controllers;

import repository.ReviewRepository;

import javax.inject.Inject;

public class ReviewController {

    private final ReviewRepository reviewRepository;

    @Inject
    public ReviewController(ReviewRepository reviewRepository){
        this.reviewRepository=reviewRepository;
    }
}
