package repository;

import io.ebean.*;
import models.Item;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.concurrent.CompletableFuture.supplyAsync;


public class ItemRepository {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public ItemRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext){
        this.ebeanServer=Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext=executionContext;
    }

    public List<Item> getByGenre(String genre){
        List<Item> items = Ebean.find(Item.class)
                .where()
                .eq("genre", genre)
                .findList();
        return items;
    }






}