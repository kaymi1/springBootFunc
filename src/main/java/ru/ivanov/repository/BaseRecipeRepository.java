package ru.ivanov.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ivanov.models.Recipe;

@Repository
public interface BaseRecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findByNameLike(String nameLike);
}
