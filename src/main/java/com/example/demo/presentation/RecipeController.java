package com.example.demo.presentation;

import com.example.demo.Buisnesslayout.ID;
import com.example.demo.Buisnesslayout.Recipe;
import com.example.demo.Buisnesslayout.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
public class RecipeController {
    private ObjectMapper objectMapper = new ObjectMapper();
    private ConcurrentMap<Integer, Recipe> map = new ConcurrentHashMap<>();
    private ID idNew = new ID();
    @Autowired
    RecipeService recipeService;
    //private long id = recipeService == null ? 1 : recipeService.getHowManyElements();
    private long id;
    @PostMapping("/api/recipe/new")
    public ResponseEntity<String> RecipeSetter(@Valid @RequestBody Recipe recipeNew) {

      try {
          id = recipeService.getHowManyElements();
          Recipe createdRecipe = recipeService.save(new Recipe(id,
                  recipeNew.getName(), recipeNew.getDescription(), recipeNew.getIngredients(),
                  recipeNew.getDirections()));
          idNew.setId(id);
          return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                  writeValueAsString(idNew), HttpStatus.OK);
      } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

    }
    @GetMapping("/api/recipe/{iD}")
    public ResponseEntity<String> getRecipe(@PathVariable long iD) throws JsonProcessingException {
            Recipe recipe = recipeService.findRecipeById(iD);
            if (recipe != null) {
                return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                        writeValueAsString(recipe), HttpStatus.OK);
            }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/api/recipe")
    public ResponseEntity<String> deleteAll() {
        try {
            recipeService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND
            );
        }
    }
    @DeleteMapping("/api/recipe/{iD}")
    public ResponseEntity<String> deleteRecipe(@PathVariable long iD) {
        try {
            recipeService.delete(iD);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND
            );
        }

    }
}
