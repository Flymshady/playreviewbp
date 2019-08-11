package repository;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import models.Item;
import play.db.ebean.EbeanConfig;
import javax.inject.Inject;
import java.util.List;

//Výpis 33
//Repository pro Item
public class ItemRepository {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public ItemRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext){
        this.ebeanServer=Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext=executionContext;
    }

    //Metoda pro nalezení položek podle žánru
    public List<Item> getByGenre(String genre){
        List<Item> items = Ebean.find(Item.class)
                .where()
                .eq("genre", genre)
                .findList();
        return items;
    }






}