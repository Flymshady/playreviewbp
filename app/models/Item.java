package models;


import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Výpis 31
//Modelová třída s využitím Ebean
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

    public Item(){}

    public Item(String name, String author, String genre, String typeformat, int year, String textShort, String textLong) {
        this.name=name;
        this.author=author;
        this.genre=genre;
        this.typeFormat=typeformat;
        this.year=year;
        this.textShort=textShort;
        this.textLong=textLong;
    }
}


