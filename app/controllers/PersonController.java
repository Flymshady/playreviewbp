package controllers;

import play.mvc.Controller;
import repository.PersonRepository;
import repository.RoleRepository;

import javax.inject.Inject;

public class PersonController extends Controller {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;

    @Inject
    public PersonController(PersonRepository personRepository, RoleRepository roleRepository){
        this.personRepository=personRepository;
        this.roleRepository=roleRepository;
    }
}
