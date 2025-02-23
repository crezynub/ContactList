package com.example.contactlist.repository;

import com.example.contactlist.exceptions.PersonNotFoundException;
import com.example.contactlist.model.Person;
import com.example.contactlist.repository.mapper.PeopleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
@Profile("prod")
public class ProdPeopleRepository  implements PeopleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Person> findAll() {
        log.debug("ProdPeopleRepository -> findAll");

        String sql = "SELECT * FROM person";

        return jdbcTemplate.query(sql, new PeopleRowMapper());
    }

    @Override
    public Optional<Person> findById(Long id) {
        log.debug("ProdPeopleRepository -> findAll with ID: {}",id);

        String sql = "SELECT * FROM person WHERE ID = ?";
        Person person = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[] {id}),
                        new RowMapperResultSetExtractor<>(new PeopleRowMapper(),1)
                )
        );
        return Optional.ofNullable(person);
    }

    @Override
    public Person save(Person person) {
        log.debug("ProdPeopleRepository -> save with Person: {}",person);

        person.setId(System.currentTimeMillis());
        String sql = "INSERT INTO person (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, person.getFirstName(),person.getLastName(),person.getEmail(),person.getPhone(),person.getId());

        return person;
    }

    @Override
    public Person update(Person person) {
        log.debug("ProdPeopleRepository -> update with Person: {}",person);
        Person existedPerson = findById(person.getId()).orElse(null);
        if (existedPerson != null) {
            String sql = "UPDATE person SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sql, person.getFirstName(),person.getLastName(),person.getEmail(),person.getPhone(),person.getId());
            return person;
        }
        log.warn("Task with ID {} not found", person.getId());
        throw new PersonNotFoundException("Person for update not found ID" + person.getId());
    }

    @Override
    public void delete(Long id) {
        log.debug("ProdPeopleRepository -> delete with ID: {}", id);
        String sql = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public void batchInsert(List<Person> people) {
        log.debug("ProdPeopleRepository -> batchInsert");
        String sql = "INSERT INTO person (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Person person = people.get(i);
                ps.setString(1,person.getFirstName());
                ps.setString(2,person.getLastName());
                ps.setString(3,person.getEmail());
                ps.setString(4,person.getPhone());
                ps.setLong(5,person.getId());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });
    }
}