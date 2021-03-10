package ru.ivanov.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ivanov.models.Ingredient;
import ru.ivanov.models.Recipe;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    List<Ingredient> findByNameLike(String nameLike);

    /* Custom implementation default findByNameLike */
    @Query("select i from Recipe r join Ingredient i on r.id = i.recipe.id where i.name like :nameLike")
    List<Ingredient> findIngredientByNameLike(@Param("nameLike") String nameLike);
}
