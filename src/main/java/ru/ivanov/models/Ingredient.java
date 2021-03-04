package ru.ivanov.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import org.springframework.data.annotation.AccessType;

@Entity
@AccessType(AccessType.Type.FIELD)
@Data
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Ingredient implements Serializable {
    @Id @GeneratedValue
    private Long id;

    @Basic
    private String name;

    @Basic
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;

    public Ingredient setFieldsAndGetObject(String name, Integer amount){
        this.setName(name);
        this.setAmount(amount);
        return this;
    }
}
