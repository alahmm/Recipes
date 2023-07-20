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
        List<Long> listOfId = new ArrayList<>();
       recipeRepository.findAll().forEach(s -> listOfId.add(s.getId()));
       return listOfId.size() + 1;
    }
    public void delete(long id) {
        recipeRepository.deleteById(id);
    }
    public void deleteAll() {
        recipeRepository.deleteAll();
    }

}
