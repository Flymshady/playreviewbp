package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
public class Person extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    @Constraints.Required
    public String username;
    @Constraints.Required
    public String password;
    @Constraints.Required
    public String name;
    @ManyToOne
    public Role role;

    public static Finder<Long, Person> find = new Finder<>(Person.class);
}
