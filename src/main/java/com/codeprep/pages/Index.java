package com.codeprep.pages;

import com.codeprep.dao.PersonDAO;
import com.codeprep.entities.Person;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Index {
    static final Logger LOG = LoggerFactory.getLogger(Index.class);

    @Inject
    private PersonDAO personDAO;

    @Property
    private String name;

    @Property
    private String country;

    @Property
    private List<Person> persons;

    @Property
    private Person person;

    @Property
    private BeanModel<Person> myModel;

    @Component(id = "personForm")
    private Form form;

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private Messages messages;

    @Component(id = "name")
    private TextField nameField;

    @Component(id = "country")
    private TextField countryField;

    void onValidateFromPersonForm() {
        if (name == null || name.trim().equals("")) {
            form.recordError(nameField, "Name is required.");
        }
        if (country == null || country.trim().equals("")) {
            form.recordError(countryField, "Country is required.");
        }
    }

    Object onSuccess() {
        LOG.info("success! " + name);
        person = new Person();
        person.setName(name);
        person.setCountry(country);
        personDAO.persist(person);
        return null;
    }

    void setupRender() {
        LOG.info("Bean model has been invoked");
        myModel = beanModelSource.createDisplayModel(Person.class, messages);
        myModel.add("action", null);
        myModel.include("id", "name", "country", "action");
        myModel.get("name").sortable(false);
        myModel.get("action").label("Operation");
        myModel.get("country").label("Country");
        persons = personDAO.findAll();
    }

    void onDelete(Long id) {
        Person person = personDAO.findById(id);
        personDAO.delete(person);
    }
}
