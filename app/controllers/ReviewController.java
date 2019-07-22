package controllers;

import io.ebean.annotation.Transactional;
import models.Item;
import models.Review;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.Secure;
import org.pac4j.play.scala.Pac4jScalaTemplateHelper;
import org.pac4j.play.store.PlaySessionStore;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import repository.ReviewRepository;
import views.html.reviews.adminIndex;
import views.html.reviews.create;
import views.html.reviews.edit;
import views.html.reviews.index;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class ReviewController extends Controller {

    private final ReviewRepository reviewRepository;
    private final FormFactory formFactory;

    @Inject
    public ReviewController(ReviewRepository reviewRepository, FormFactory formFactory){
        this.reviewRepository=reviewRepository;
        this.formFactory=formFactory;
    }

    @com.google.inject.Inject
    private PlaySessionStore playSessionStore;

    @com.google.inject.Inject
    private Pac4jScalaTemplateHelper<CommonProfile> pac4jScalaTemplateHelper;

    //creating item
    @Secure(clients = "OidcClient")
    public Result create(Long itemId){
        Form<Review> reviewForm = formFactory.form(Review.class);

        return ok(create.render(reviewForm, itemId, pac4jScalaTemplateHelper));
    }

    public Result index(Long itemId){
        List<Review> reviews = reviewRepository.findByItemId(itemId);
        return ok(index.render(reviews,itemId, pac4jScalaTemplateHelper));
    }
    @Secure(clients = "OidcClient", authorizers = "admin")
    public Result adminIndex(Long itemId){
        List<Review> reviews = reviewRepository.findByItemId(itemId);
        return ok(adminIndex.render(reviews,itemId, pac4jScalaTemplateHelper));
    }

    @Transactional
    @Secure(clients = "OidcClient")
    public Result save(Long itemId){
        Form<Review> reviewForm = formFactory.form(Review.class).bindFromRequest();
        System.out.println(reviewForm);
        if(reviewForm.hasErrors()){
            return badRequest(create.render(reviewForm, itemId, pac4jScalaTemplateHelper));
        }
        Review review = reviewForm.get();
        review.personEmail=getUserProfile().get().getEmail();
        review.personId=getUserProfile().get().getId();
        review.item=Item.find.byId(itemId);
        review.save();
        return  redirect(routes.ReviewController.index(review.item.id));
    }
    @Secure(clients = "OidcClient")
    public Result edit(Long itemId, Long id){
        if(Item.find.byId(itemId)==null){
            return notFound("Item Not Found");
        }
        Review review = Review.find.byId(id);

        if(review==null){
            return notFound("Review Not Found");
        }
        System.out.println(review.personId);
        System.out.println(getUserProfile().get().getId());
        if(!review.personId.equals(getUserProfile().get().getId())){
            return forbidden("Forbidden access");
        }
        Form<Review> reviewForm = formFactory.form(Review.class).fill(review);

        return ok(edit.render(reviewForm, id,itemId,pac4jScalaTemplateHelper));
    }
    @Transactional
    @Secure(clients = "OidcClient")
    public Result update(Long itemId, Long id){

        Form<Review> reviewForm = formFactory.form(Review.class).bindFromRequest();
        if(reviewForm.hasErrors()){
            return badRequest(edit.render(reviewForm,id,itemId, pac4jScalaTemplateHelper));
        }
        Review review = reviewForm.get();
        Review tmp = Review.find.byId(id);
        Review oldReview = Review.find.byId(id);
        oldReview.id=tmp.id;
        oldReview.personId=tmp.personId;
        oldReview.personEmail=tmp.personEmail;
        oldReview.item=tmp.item;
        if(oldReview == null){
            return notFound("Item Not Found "+review.id);
        }
        if(!oldReview.personId.equals(getUserProfile().get().getId())){
            return forbidden("Forbidden access");
        }
        if(!Item.find.byId(itemId).equals(Item.find.byId(oldReview.item.id))){
            return notFound("Item Not Found: "+itemId+"/"+oldReview.item.id);
        }
        if(Item.find.byId(itemId)==null){
            return notFound("Item Not Found");
        }

        oldReview.textShort=review.textShort;
        oldReview.textLong=review.textLong;
        oldReview.update();
        return redirect(routes.ReviewController.index(itemId));
    }

    @Transactional
    @Secure(clients = "OidcClient")
    public Result remove(Long itemId, Long id){
        if(Item.find.byId(itemId)==null){
            return notFound("Item Not Found "+itemId);
        }
        Review review = Review.find.byId(id);
        if(review==null){
            return notFound("Review Not Found "+review.id);
        }
        if(!Item.find.byId(itemId).equals(Item.find.byId(review.item.id))){
            return notFound("Item Not Found: "+itemId+"/"+review.item.id);
        }
        if(!getUserProfile().get().getRoles().contains("ROLE_ADMIN")){
            if(!review.personId.equals(getUserProfile().get().getId())){
                return forbidden("Forbidden access");
            }
        }
        review.delete();
        return  redirect(routes.ReviewController.index(review.item.id));
    }


    private Optional<CommonProfile> getUserProfile() {
        PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
        ProfileManager<CommonProfile> profileManager = new ProfileManager(context);
        Optional<CommonProfile> profile = profileManager.get(true);
        return profile;
    }

}
