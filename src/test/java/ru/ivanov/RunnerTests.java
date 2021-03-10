package ru.ivanov;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.ivanov.dao.RecipeDaoImpl;
import ru.ivanov.models.Ingredient;
import ru.ivanov.models.Recipe;
import ru.ivanov.repository.BaseRecipeRepository;
import ru.ivanov.repository.IngredientRepository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
@EntityScan("ru.ivanov.models")
class RunnerTests {

	@Autowired
	private RecipeDaoImpl recipeDao;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private BaseRecipeRepository baseRecipeRepository;

	@AfterEach
	public void clear() {
		JdbcTestUtils.deleteFromTables(new JdbcTemplate(dataSource), "RECIPE");
	}

	@Test
	public void createAndGetRecipesWithDAOTest(){
		List<Recipe> recipes = getRecipes(5, "A");
		recipeDao.addListRecipe(recipes);

		List<Recipe> recipesFromDB = recipeDao.getAllRecipes();
		for (int i = 0; i < recipes.size(); i++) {
			Assertions.assertEquals(recipes.get(i).getName(),recipesFromDB.get(i).getName());
		}
	}

	@Test
	public void createAndRemoveRecipeWithDAOTest(){
		List<Recipe> recipes = getRecipes(3, "B");
		recipeDao.addListRecipe(recipes);

		recipeDao.removeRecipe(recipes.get(0).getName());
		List<Recipe> recipesFromDB = recipeDao.getAllRecipes();
		Assertions.assertEquals(recipesFromDB.size(), 2);
	}

	@Test
	public void getRecipeByNameWithDAOTest(){
		List<Recipe> recipes = getRecipes(4, "R");
		recipeDao.addListRecipe(recipes);

		Recipe recipeFromDB = recipeDao.getRecipeByName(recipes.get(0).getName());
		Assertions.assertEquals(recipes.get(0).toString(), recipeFromDB.toString());
	}

	@Test
	public void getIngredientWithRepository(){
		Recipe recipe = new Recipe();
		recipe.setFieldsAndGetObject("A_recipe_name", getIngredients(4, "A"));
		recipeDao.addRecipe(recipe.getName(), recipe.getIngredients());

		List<Ingredient> ingredients = ingredientRepository.findIngredientByNameLike("A%");
		Assertions.assertTrue(recipe.getIngredients().containsAll(ingredients));
	}

	@Test
	public void addRecipeWithRepositoryTest(){
		Recipe recipe = new Recipe();
		recipe.setFieldsAndGetObject("A_recipe_name", getIngredients(4, "A"));
		baseRecipeRepository.save(recipe);

		Recipe recipeDB = baseRecipeRepository.findByNameLike("A%");
		Assertions.assertEquals(recipe.getId(), recipeDB.getId());
	}

	@Test
	public void removeRecipeWithRepositoryTest(){
		List<Recipe> recipes = getRecipes(4, "R");
		for (Recipe recipe : recipes) {
			baseRecipeRepository.save(recipe);
		}

		baseRecipeRepository.delete(recipes.get(2));
		List<Recipe> recipesDB = (List<Recipe>) baseRecipeRepository.findAll();
		Assertions.assertEquals(recipesDB.size(), recipes.size() - 1);
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
