package com.example.contactlist.repository;

import com.example.contactlist.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
@Slf4j
@Profile("dev")
public class DevPeopleRepository implements PeopleRepository {

    private final List<Person> people = new CopyOnWriteArrayList<>();

    @Override
    public List<Person> findAll() {
        log.debug("InDevPeopleRepository -> findAll");
        return people;
    }

    @Override
    public Optional<Person> findById(Long id) {
        log.debug("InDevPeopleRepository -> findById ID: {}",id);
        return people.stream()
                .filter(t->t.getId().equals(id))
                .findFirst();
    }

    @Override
    public Person save(Person person) {
        log.debug("InDevPeopleRepository -> save Person: {}", person);
        person.setId(System.currentTimeMillis());
        people.add(person);
        return person;
    }

    @Override
    public Person update(Person person) {
        log.debug("InDevPeopleRepository -> update Person: {}", person);
        Person existedPerson = findById(person.getId()).orElse(null);
        if(existedPerson !=null) {
            existedPerson.setFirstName(person.getFirstName());
            existedPerson.setLastName(person.getLastName());
            existedPerson.setEmail(person.getEmail());
            existedPerson.setPhone(person.getPhone());
        }
        return existedPerson;
    }

    @Override
    public void delete(Long id) {
        log.debug("InDevPeopleRepository -> delete ID: {}", id);
        findById(id).ifPresent(people :: remove);
    }

    @Override
    public void batchInsert(List<Person> people) {
        this.people.addAll(people);
    }
}