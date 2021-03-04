package ru.ivanov;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.util.Assert;
import ru.ivanov.dao.RecipeDaoImpl;
import ru.ivanov.models.Ingredient;
import ru.ivanov.models.Recipe;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@EntityScan("ru.ivanov.models")
class RunnerTests {

	@Autowired
	private RecipeDaoImpl recipeDao;

	@Autowired
	private DataSource dataSource;

	@AfterEach
	public void clear() {
		JdbcTestUtils.deleteFromTables(new JdbcTemplate(dataSource), "RECIPE");
	}

	@Test
	public void createAndGetRecipesTest(){
		List<Recipe> recipes = getRecipes(5, "A");
		recipeDao.addListRecipe(recipes);

		List<Recipe> recipesFromDB = recipeDao.getAllRecipes();
		for (int i = 0; i < recipes.size(); i++) {
			Assertions.assertEquals(recipes.get(i).getName(),recipesFromDB.get(i).getName());
		}
	}

	@Test
	public void createAndRemoveRecipeTest(){
		List<Recipe> recipes = getRecipes(3, "B");
		recipeDao.addListRecipe(recipes);

		recipeDao.removeRecipe(recipes.get(0).getName());
		List<Recipe> recipesFromDB = recipeDao.getAllRecipes();
		Assertions.assertEquals(recipesFromDB.size(), 2);
	}

	@Test
	public void getRecipeByNameTest(){
		List<Recipe> recipes = getRecipes(4, "R");
		recipeDao.addListRecipe(recipes);

		Recipe recipeFromDB = recipeDao.getRecipeByName(recipes.get(0).getName());
		Assertions.assertEquals(recipes.get(0).toString(), recipeFromDB.toString());
	}

	private static List<Ingredient> getIngredients(Integer countOfIngredients, String prefix){
		List<Ingredient> ingredients = new ArrayList<>();
		for (int i = 0; i < countOfIngredients; i++) {
			Ingredient ingredient = new Ingredient();
			ingredient.setFieldsAndGetObject(prefix + "_ing_name_" + i + 2, i + 5);
			ingredients.add(ingredient);
		}
		return ingredients;
	}

	private static List<Recipe> getRecipes(Integer countOfRecipes, String prefix){
		List<Recipe> recipes = new ArrayList<>();
		for (int i = 0; i < countOfRecipes; i++) {
			Recipe recipe = new Recipe();
			recipe.setFieldsAndGetObject(prefix + "_recipe_name_" + i + 1, getIngredients(i, prefix));
			recipes.add(recipe);
		}
		return recipes;
	}

}
