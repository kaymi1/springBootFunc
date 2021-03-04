package ru.ivanov.dao;

import ru.ivanov.models.Ingredient;
import ru.ivanov.models.Recipe;

import java.util.List;

public interface RecipeDao {
     Recipe getRecipeByName(String name);
     void addRecipe(String nameRecipe, List<Ingredient> ingredients);
     void removeRecipe(String nameRecipe);
     void addListRecipe(List<Recipe> recipes);
     List<Recipe> getAllRecipes();
}
