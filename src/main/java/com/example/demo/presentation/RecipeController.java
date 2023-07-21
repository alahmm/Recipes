package com.example.demo.presentation;

import com.example.demo.Buisnesslayout.ID;
import com.example.demo.Buisnesslayout.Recipe;
import com.example.demo.Buisnesslayout.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class RecipeController {
    private ObjectMapper objectMapper = new ObjectMapper();

    private ID idNew = new ID();
    @Autowired
    RecipeService recipeService;
    private long id;
    public String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        long currentTimestamp = zonedDateTime.toInstant().toEpochMilli();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
    @PostMapping("/api/recipe/new")
    public ResponseEntity<String> RecipeSetter(@Valid @RequestBody Recipe recipeNew) throws JsonProcessingException {

          id = recipeService.getHowManyElements();

          Recipe createdRecipe = recipeService.save(new Recipe(id,
                  recipeNew.getName(), recipeNew.getCategory(), getTime(), recipeNew.getDescription(), recipeNew.getIngredients(),
                  recipeNew.getDirections()));
          idNew.setId(id);
          return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                  writeValueAsString(idNew), HttpStatus.OK);

    }
    @GetMapping("/api/recipe/{iD}")
    public ResponseEntity<String> getRecipe(@PathVariable long iD)  {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            Recipe recipe = recipeService.findRecipeById(iD);
            if (recipe != null) {

                ResponseEntity<String> responseEntity = new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                        writeValueAsString(recipe), HttpStatus.OK);
                return responseEntity;
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/recipe/{iD}")
    public ResponseEntity<String> updateRecipe(@PathVariable("iD") @Min(1) long iD,
                                               @Valid @RequestBody Recipe newRecipe) throws JsonProcessingException {
        Recipe recipe = recipeService.findRecipeById(iD);
        if (recipe != null) {
            recipeService.delete(iD);
            recipe.setId(iD);
            recipe.setName(newRecipe.getName());
            recipe.setDescription(newRecipe.getDescription());
            recipe.setCategory(newRecipe.getCategory());
            recipe.setIngredients(newRecipe.getIngredients());
            recipe.setDirections(newRecipe.getDirections());
            recipe.setOrderDate(getTime());
            recipeService.save(recipe);
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/recipe/search")
    public ResponseEntity<String> getRecipe(@RequestParam Optional<String> name,
                                            @RequestParam Optional<String> category)
            throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        List<Recipe> recipes;
        if ((name.isPresent() && category.isPresent()) || (name.isEmpty() && category.isEmpty()) ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (name.isPresent()){
            String value = name.get();
            recipes = recipeService.findAllRecipesByName(value);
        } else {
            recipes = recipeService.findAllRecipesByCategory(String.valueOf(category));
        }
        return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                    writeValueAsString(recipes), HttpStatus.OK);
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
