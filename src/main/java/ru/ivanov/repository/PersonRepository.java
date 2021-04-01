package ru.ivanov.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ivanov.models.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
