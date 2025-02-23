package com.example.contactlist.repository;

import com.example.contactlist.model.Person;

import java.util.List;
import java.util.Optional;

public interface PeopleRepository {

    List<Person> findAll();

    Optional<Person> findById(Long id);

    Person save(Person person);

    Person update(Person person);

    void delete(Long id);

    void batchInsert(List<Person> people);
}
