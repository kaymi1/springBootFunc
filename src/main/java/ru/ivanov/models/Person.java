package ru.ivanov.models;

import lombok.*;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;

@Entity
@AccessType(AccessType.Type.FIELD)
@EqualsAndHashCode
@ToString
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id_person")
    @GeneratedValue
    private int id;
    @Column(name = "person_name")
    private String name;

    public Person(int id, String name){
        this.setId(id);
        this.setName(name);
    }

    public Person(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
