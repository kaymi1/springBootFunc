package ru.ivanov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ivanov.dao.RecipeDao;
import ru.ivanov.models.Ingredient;
import ru.ivanov.models.Recipe;
import ru.ivanov.repository.BaseRecipeRepository;
import ru.ivanov.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RecipeDao recipeDao;

    @GetMapping("/people")
    public String index(Model model){
        model.addAttribute("people", personRepository.findAll());
        return "index";
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
