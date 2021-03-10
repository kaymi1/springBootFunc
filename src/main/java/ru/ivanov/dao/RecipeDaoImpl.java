package ru.ivanov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.models.Ingredient;
import ru.ivanov.models.Recipe;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeDaoImpl implements RecipeDao {

    private EntityManager entityManager;

    @Autowired
    public RecipeDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Recipe getRecipeByName(String name) {
        return entityManager.createQuery
                ("select r from Recipe r where r.name like :nameLike", Recipe.class)
                .setParameter("nameLike", name).getSingleResult();
    }

    @Override
    @Transactional
    public void addRecipe(String nameRecipe, List<Ingredient> ingredients) {
        Recipe recipe = new Recipe();
        recipe.setFieldsAndGetObject(nameRecipe, ingredients);
        entityManager.persist(recipe);
    }


    @Override
    @Transactional
    public void removeRecipe(String nameRecipe) {
        Recipe recipe1 = entityManager.createQuery("select r from Recipe r where r.name like :nameLike", Recipe.class)
                .setParameter("nameLike", nameRecipe)
                .getSingleResult();
        entityManager.remove(recipe1);
    }

    @Override
    @Transactional
    public void addListRecipe(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            entityManager.persist(recipe);
        }
    }

    @Override
    @Transactional
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = entityManager.createQuery("select r from Recipe r", Recipe.class)
                .getResultList();
        return recipes;
    }
}
