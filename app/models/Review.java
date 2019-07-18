package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
public class Review extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    @Constraints.Required
    public String textShort;
    @Constraints.Required
    public String textLong;
    @ManyToOne
    public Person person;
    @ManyToOne
    public Item item;

    public static Finder<Long, Review> find = new Finder<>(Review.class);
}
