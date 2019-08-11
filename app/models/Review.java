package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;

//Modelová třída s využitím Ebean
@Entity
public class Review extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    @Constraints.Required
    public String textShort;
    @Constraints.Required
    public String textLong;
    public String personEmail;
    public String personId;
    @ManyToOne
    public Item item;

    public Review(String textShort, String textLong, String personEmail, String personId, Item item){
        this.textShort=textShort;
        this.textLong=textLong;
        this.personEmail=personEmail;
        this.personId=personId;
        this.item=item;
    }

    public static Finder<Long, Review> find = new Finder<>(Review.class);
}
