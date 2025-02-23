package com.example.contactlist.service;

import com.example.contactlist.model.Person;

import java.util.List;

public interface PeopleService {

    List<Person> findAll();

    Person findById(Long id);

    Person save(Person person);

    Person update(Person person);

    void delete(Long id);

    void batchInsert(List<Person> people);
}