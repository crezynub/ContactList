package com.example.contactlist.service;

import com.example.contactlist.model.Person;
import com.example.contactlist.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;

    @Override
    public List<Person> findAll() {
        log.debug("DevPeopleService -> findAll");
        return peopleRepository.findAll();
    }

    @Override
    public Person findById(Long id) {
        log.debug("DevPeopleService -> findById ID: {}",id);
        return peopleRepository.findById(id).orElse(null);
    }

    @Override
    public Person save(Person person) {
        log.debug("DevPeopleService -> save Person {}",person);
        return peopleRepository.save(person);
    }

    @Override
    public Person update(Person person) {
        log.debug("DevPeopleService -> update Person {}",person);
        return peopleRepository.update(person);
    }

    @Override
    public void delete(Long id) {
        log.debug("DevPeopleService -> delete ID: {}",id);
        peopleRepository.delete(id);
    }

    @Override
    public void batchInsert(List<Person> people) {
        log.debug("DevPeopleService -> batchInsert");
        peopleRepository.batchInsert(people);
    }
}
