package ru.ivanov.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.AccessType;

@Entity
@AccessType(AccessType.Type.FIELD)
@Data
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Recipe implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Basic
    private String name;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER)
    // Lazy == size 0
    private List<Ingredient> ingredients = new ArrayList<>();

    public Recipe setFieldsAndGetObject(String name, List<Ingredient> ingredients){
        this.setName(name);
        this.setIngredients(ingredients);
        return this;
    }
}
