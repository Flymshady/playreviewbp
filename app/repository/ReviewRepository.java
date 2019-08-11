package repository;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import models.Review;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.List;

//Repository pro Review
public class ReviewRepository {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public ReviewRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext){
        this.ebeanServer= Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext=executionContext;
    }

    public List<Review> findByItemId(Long itemId){
        List<Review> reviews = Ebean.find(Review.class)
                .where()
                .eq("item_id", itemId)
                .findList();
        return reviews;
    }

}
