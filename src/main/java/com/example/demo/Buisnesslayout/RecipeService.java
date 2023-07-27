package com.example.demo.Buisnesslayout;

import com.example.demo.persistence.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    public Recipe findRecipeById(Long id) {
        return recipeRepository.findRecipeById(id);
    }
    public Recipe save(Recipe toSave) {
        return recipeRepository.save(toSave);
    }
    public long getHowManyElements() {

       return recipeRepository.count() + 1;
    }
    public List<Recipe> findAllRecipesByName(String name) {

        return recipeRepository.findByNameIgnoreCaseContainsOrderByDateDesc(name);
    }
    public List<Recipe> findAllRecipesByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }
    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }    public void deleteAll() {
        recipeRepository.deleteAll();
    }

}
