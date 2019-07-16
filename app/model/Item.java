package model;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item extends Model {

    @Id
    private Long id;
    private String name;
    private String author;
    private String type;
    private int year;
    private String textShort;
    private String textLong;

    public Item(){}


    public Item(Long id,String name, String author, String type, int year, String textShort, String textLong) {
        this.name = name;
        this.author = author;
        this.type = type;
        this.year = year;
        this.textShort = textShort;
        this.textLong = textLong;
        this.id=id;
    }

    private static Set<Item> items;

    static{
        items=new HashSet<>();
        items.add(new Item(1L,"name", "author", "single", 1999, "textSh", "textL"));
        items.add(new Item(2L,"name2", "author2", "single", 1998, "textSh2", "2textL"));
    }

    public static Set<Item> allItems(){
        return items;
    }

    public static Item findById(Long id){
        for(Item item: items){
            if(id.equals(item.id)){
                return item;
            }
        }
        return null;
    }

    public static void add(Item item){
        items.add(item);
    }
    public static boolean remove(Item item){
        return items.remove(item);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTextShort() {
        return textShort;
    }

    public void setTextShort(String textShort) {
        this.textShort = textShort;
    }

    public String getTextLong() {
        return textLong;
    }

    public void setTextLong(String textLong) {
        this.textLong = textLong;
    }
}


