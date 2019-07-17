package repository;

import io.ebean.*;
import models.Item;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;


public class ItemRepository {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public ItemRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext){
        this.ebeanServer=Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext=executionContext;
    }





}
