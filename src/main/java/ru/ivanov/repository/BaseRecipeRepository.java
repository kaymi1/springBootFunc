package ru.ivanov.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ivanov.models.Recipe;

public interface BaseRecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findByNameLike(String nameLike);
}
