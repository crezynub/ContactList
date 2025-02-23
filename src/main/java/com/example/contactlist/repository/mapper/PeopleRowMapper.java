package com.example.contactlist.repository.mapper;

import com.example.contactlist.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeopleRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

        Person person = new Person();

        person.setId(rs.getLong(Person.Fields.id));
        person.setFirstName(rs.getString(Person.Fields.firstName));
        person.setLastName(rs.getString(Person.Fields.lastName));
        person.setEmail(rs.getString(Person.Fields.email));
        person.setPhone(rs.getString(Person.Fields.phone));

        return person;
    }
}
