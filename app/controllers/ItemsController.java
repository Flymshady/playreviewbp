package controllers;


import models.Item;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;


import repository.ItemRepository;
import views.html.items.*;

import javax.inject.Inject;


public class ItemsController extends Controller {


    private final FormFactory formFactory;
    private final ItemRepository itemRepository;


    @Inject
    public ItemsController(FormFactory formFactory, ItemRepository itemRepository){
        this.formFactory=formFactory;
        this.itemRepository=itemRepository;

    }




    //for all items
    public Result index(){
        List<Item> items=Item.find.all();
        return ok(index.render(items));
    }

    public Result getByGenre(String genre){
        List<Item> items = itemRepository.getByGenre(genre);
        System.out.println(items);
       return ok(index.render(items));
    }

    //creating item
    public Result create(){
        Form<Item> itemForm = formFactory.form(Item.class);

        return ok(create.render(itemForm));
    }

    public Result save(){
        Form<Item> itemForm = formFactory.form(Item.class).bindFromRequest();

        if(itemForm.hasErrors()){
            return badRequest(create.render(itemForm));
        }
        Item item = itemForm.get();
        item.save();
        return  redirect(routes.ItemsController.index());
    }
    public Result edit(Long id){

        Item item = Item.find.byId(id);
        if(item==null){
            return notFound("Item Not Found");
        }
        Form<Item> itemForm = formFactory.form(Item.class).fill(item);

        return ok(edit.render(itemForm));
    }

    public Result update(){
        Form<Item> itemForm = formFactory.form(Item.class).bindFromRequest();
        if(itemForm.hasErrors()){
            return badRequest(edit.render(itemForm));
        }
        Item item = itemForm.get();
        Item oldItem = Item.find.byId(item.id);
        if(oldItem == null){
            return notFound("Item Not Found "+item.id);
        }
        oldItem.name=item.name;
        oldItem.author=item.author;
        oldItem.typeFormat=item.typeFormat;
        oldItem.year=item.year;
        oldItem.genre=item.genre;
        oldItem.textShort=item.textShort;
        oldItem.textLong=item.textLong;
        oldItem.update();
        return redirect(routes.ItemsController.index());
    }

    public Result remove(Long id){

        Item item = Item.find.byId(id);
        if(item==null){
            return notFound("Item Not Found "+item.id);
        }
        item.delete();
        return  redirect(routes.ItemsController.index());
    }

    public Result detail(Long id){
        Item item = Item.find.byId(id);
        if(item==null){
            return notFound("Item Not Found "+item.id);
        }
        return ok(detail.render(item));
    }


}