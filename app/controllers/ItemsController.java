package controllers;

import com.google.inject.Inject;
import model.Item;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Set;


import views.html.items.*;

public class ItemsController extends Controller {


    @Inject
    FormFactory formFactory;


    //for all items
    public Result index(){
        Set<Item> items=Item.allItems();
        return ok(index.render(items));
    }

    //creating item
    public Result create(){
        Form<Item> itemForm = formFactory.form(Item.class);

        return ok(create.render(itemForm));
    }

    public Result save(){
        Form<Item> itemForm = formFactory.form(Item.class).bindFromRequest();
        Item item = itemForm.get();
        Item.add(item);
        return  redirect(routes.ItemsController.index());
    }
    public Result edit(Long id){

        Item item = Item.findById(id);
        if(item==null){
            return notFound("Item Not Found");
        }
        Form<Item> itemForm = formFactory.form(Item.class).fill(item);

        return ok(edit.render(itemForm));
    }

    public Result update(){
        Form<Item> itemForm = formFactory.form(Item.class).bindFromRequest();
        Item item = itemForm.get();
        Item oldItem = Item.findById(item.getId());
        if(oldItem == null){
            return notFound("Item Not Found "+item.getId());
        }
        oldItem.setName(item.getName());
        oldItem.setAuthor(item.getAuthor());
        oldItem.setType(item.getType());
        oldItem.setYear(item.getYear());
        oldItem.setTextShort(item.getTextShort());
        oldItem.setTextLong(item.getTextLong());
        return redirect(routes.ItemsController.index());
    }

    public Result remove(Long id){

        Item item = Item.findById(id);
        if(item==null){
            return notFound("Item Not Found "+item.getId());
        }
        Item.remove(item);
        return  redirect(routes.ItemsController.index());
    }

    public Result detail(Long id){
        Item item = Item.findById(id);
        if(item==null){
            return notFound("Item Not Found "+item.getId());
        }
        return ok(detail.render(item));
    }


}
