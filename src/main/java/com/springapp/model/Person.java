package com.springapp.model;

import javax.persistence.*;

import org.hibernate.annotations.Type;


@Entity
@NamedNativeQuery(name = "Person.findByFuzzIs",
                  query = "SELECT * FROM Person WHERE name = :fuzz",
                  resultClass = Person.class)

public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
//    Need to be created outside of JPA
//    @Type(type = "com.springapp.util.PostGresArrayStringType")
//    private String[] roles;
//    
//    public String[] getRoles() {
//        return roles;
//    }
//
//    public void setRoles(String[] roles) {
//        this.roles = roles;
//    }

    public Person() {
    }

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", surname=" + surname
                + "]";
    }
}