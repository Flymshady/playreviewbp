package models;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import repository.ReviewRepository;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;

public class ReviewTest {

    @InjectMocks
    private ReviewRepository reviewRepository;
    @Mock
    private Item mockItem;

    @Test
    public void testItemModel() {
        //play test helper pro fakeapp s inmemory db
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run(){
                mockItem.id=999L;
                String personEmail="email";
                String personId="personId";
                String textShort="textShort";
                String textLong="textLong";
                Review review = new Review(textShort, textLong,personEmail,personId,mockItem);
                review.save();
            //    Review savedReview = Review.find.byId(review.id);


            //    assertNotNull(savedReview);
            //    assertEquals(savedReview.item.id, item.id);
                List<Review> reviewList = reviewRepository.findByItemId(1L);
                assertTrue(reviewList.isEmpty());


            }
        });
    }
}
