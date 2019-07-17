package models;


import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    @Constraints.Required
    public String name;
    @Constraints.Required
    public String author;
    @Constraints.Required
    public String typeFormat;
    @Constraints.Required
    public String genre;
    @Constraints.Required
    public int year;
    public String textShort;
    public String textLong;

    public static Finder<Long, Item> find = new Finder<>(Item.class);

}


