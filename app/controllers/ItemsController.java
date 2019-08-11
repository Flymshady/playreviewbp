package controllers;


import io.ebean.annotation.Transactional;
import models.Item;
import models.Review;
import org.pac4j.core.context.HttpConstants;
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
import repository.DatabaseExecutionContext;
import repository.ItemRepository;
import repository.ReviewRepository;
import views.html.items.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

//Výpis 28, 29, 32, 38
//Controller třída pro manipulaci s Item
public class ItemsController extends Controller {

    private final FormFactory formFactory;
    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;
    private DatabaseExecutionContext databaseExecutionContext;


    @Inject
    public ItemsController(FormFactory formFactory, ItemRepository itemRepository, ReviewRepository reviewRepository, DatabaseExecutionContext databaseExecutionContext){
        this.formFactory=formFactory;
        this.itemRepository=itemRepository;
        this.reviewRepository=reviewRepository;
        this.databaseExecutionContext=databaseExecutionContext;
    }

    //všechny položky
    //Index akce pro návrat status 200 OK
    public Result index(){
        List<Item> items=Item.find.all();
        return ok(index.render(items, pac4jScalaTemplateHelper));
    }
    //admin index
    @Secure(clients = "OidcClient", authorizers = "admin")
    public Result adminIndex(){
        List<Item> items=Item.find.all();
        return ok(adminIndex.render(items, pac4jScalaTemplateHelper));
    }
    @Secure(clients = "OidcClient", authorizers = "admin")
    public Result adminGetByGenre(String genre){
        List<Item> items = itemRepository.getByGenre(genre);
        return ok(adminIndex.render(items, pac4jScalaTemplateHelper));
    }
    public Result getByGenre(String genre){
        List<Item> items = itemRepository.getByGenre(genre);
       return ok(index.render(items, pac4jScalaTemplateHelper));
    }

    //vytváření položky
    @Secure(clients = "OidcClient", authorizers = "admin")
    public Result create(){
        Form<Item> itemForm = formFactory.form(Item.class);
        return ok(create.render(itemForm, pac4jScalaTemplateHelper));
    }
    @Transactional
    @Secure(clients = "OidcClient", authorizers = "admin")
    public Result save(){
        Form<Item> itemForm = formFactory.form(Item.class).bindFromRequest();
        if(itemForm.hasErrors()){
            return badRequest(create.render(itemForm, pac4jScalaTemplateHelper));
        }
        Item item = itemForm.get();
        item.save();
        return  redirect(routes.ItemsController.adminIndex());
    }
    @Secure(clients = "OidcClient", authorizers = "admin")
    public Result edit(Long id){
        Item item = Item.find.byId(id);
        if(item==null){
            return notFound(views.html.error400.render().toString()).as((HttpConstants.HTML_CONTENT_TYPE));
        }
        Form<Item> itemForm = formFactory.form(Item.class).fill(item);

        return ok(edit.render(itemForm, id,pac4jScalaTemplateHelper));
    }
    @Transactional
    @Secure(clients = "OidcClient", authorizers = "admin")
    public Result update(Long id){
        Form<Item> itemForm = formFactory.form(Item.class).bindFromRequest();
        if(itemForm.hasErrors()){
            return badRequest(edit.render(itemForm,id, pac4jScalaTemplateHelper));
        }
        Item item = itemForm.get();
        Item oldItem = Item.find.byId(id);
        if(oldItem == null){
            return notFound(views.html.error400.render().toString()).as((HttpConstants.HTML_CONTENT_TYPE));
        }
        oldItem.name=item.name;
        oldItem.author=item.author;
        oldItem.typeFormat=item.typeFormat;
        oldItem.year=item.year;
        oldItem.genre=item.genre;
        oldItem.textShort=item.textShort;
        oldItem.textLong=item.textLong;
        oldItem.update();
        return redirect(routes.ItemsController.adminIndex());
    }
    @Transactional
    @Secure(clients = "OidcClient", authorizers = "admin")
    public Result remove(Long id){

        Item item = Item.find.byId(id);
        if(item==null){
            return notFound(views.html.error400.render().toString()).as((HttpConstants.HTML_CONTENT_TYPE));
        }
        if(!reviewRepository.findByItemId(id).isEmpty()){
            for(Review review : reviewRepository.findByItemId(id)){
                review.delete();
            }
        }
        item.delete();
        return  redirect(routes.ItemsController.adminIndex());
    }
    @Secure(clients = "OidcClient", authorizers = "admin")
    public Result adminDetail(Long id){
        Item item = Item.find.byId(id);
        if(item==null){
            return notFound(views.html.error400.render().toString()).as((HttpConstants.HTML_CONTENT_TYPE));
        }
        return ok(adminDetail.render(item, pac4jScalaTemplateHelper));
    }

    public Result detail(Long id){
        Item item = Item.find.byId(id);
        if(item==null){
            return notFound(views.html.error400.render().toString()).as((HttpConstants.HTML_CONTENT_TYPE));
        }
        return ok(detail.render(item, pac4jScalaTemplateHelper));
    }

    @com.google.inject.Inject
    private PlaySessionStore playSessionStore;

    @com.google.inject.Inject
    private Pac4jScalaTemplateHelper<CommonProfile> pac4jScalaTemplateHelper;


    private Optional<CommonProfile> getUserProfile() {
        PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
        ProfileManager<CommonProfile> profileManager = new ProfileManager(context);
        Optional<CommonProfile> profile = profileManager.get(true);
        return profile;
    }


}