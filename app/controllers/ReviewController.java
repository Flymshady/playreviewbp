package controllers;

import play.mvc.Controller;
import repository.ReviewRepository;

import javax.inject.Inject;

public class ReviewController extends Controller {

    private final ReviewRepository reviewRepository;

    @Inject
    public ReviewController(ReviewRepository reviewRepository){
        this.reviewRepository=reviewRepository;
    }
}
