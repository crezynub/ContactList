package com.example.contactlist.listener;

import com.example.contactlist.model.Person;
import com.example.contactlist.service.PeopleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PersonCreatorImpl implements PersonCreator {

    private final PeopleService peopleService;

    @Override
    @EventListener(ApplicationStartedEvent.class)
    public void createPerson() {
        List<Person> people = new ArrayList<>();

        people.add(new Person(System.currentTimeMillis()+1,"Виктор","Кузнецов","viktor@mail.ru","+79198205140"));
        people.add(new Person(System.currentTimeMillis()+2,"Петр","Казаков","petr@mail.ru","+79198205141"));
        people.add(new Person(System.currentTimeMillis()+3,"Артем","Саган","artem@mail.ru","+79198205142"));
        people.add(new Person(System.currentTimeMillis()+4,"Вика","Шевцова","vika@mail.ru","+79198205143"));
        people.add(new Person(System.currentTimeMillis()+5,"Паша","Сметанин","pasha@mail.ru","+79198205144"));

        peopleService.batchInsert(people);
    }
}
