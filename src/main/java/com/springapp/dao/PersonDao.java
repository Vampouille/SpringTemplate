package com.springapp.dao;

import com.springapp.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonDao extends CrudRepository<Person, Long> {

    public List<Person> findBySurname(String surname);

    public List<Person> findByName(String name);
}
