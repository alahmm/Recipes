package com.example.demo.presentation;

import com.example.demo.Buisnesslayout.ID;
import com.example.demo.Buisnesslayout.MyUser;
import com.example.demo.Buisnesslayout.Recipe;
import com.example.demo.Buisnesslayout.RecipeService;
import com.example.demo.Buisnesslayout.userService;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
public class RecipeController {
    private ObjectMapper objectMapper = new ObjectMapper();

    private ID idNew = new ID();
    @Autowired
    RecipeService recipeService;
    @Autowired
    userService userService;
    @Autowired
    PasswordEncoder encoder;

    private long id;
    private long user_id;
    @PostMapping("/api/register")
    public ResponseEntity<HttpStatus> register(@Valid @RequestBody MyUser user)  {
        MyUser user1 = userService.findUserByEmail(user.getEmail());
        if (user1 == null) {
            user_id = userService.getHowManyElementsUsers();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setId(user_id);
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/api/recipe/new")
    public ResponseEntity<String> RecipeSetter(@Valid @RequestBody Recipe recipeNew,
                                               @AuthenticationPrincipal UserDetails details) throws JsonProcessingException {
        id = recipeService.getHowManyElements();
        Recipe recipe = new Recipe(id,
                recipeNew.getName(), recipeNew.getCategory(),
                LocalDateTime.now(), recipeNew.getDescription(), recipeNew.getIngredients(),
                recipeNew.getDirections(), details.getUsername());
        recipeService.save(recipe);
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

                return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                        writeValueAsString(recipe), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/recipe/{iD}")
    public ResponseEntity<String> updateRecipe(@PathVariable("iD") @Min(1) long iD,
                                               @Valid @RequestBody Recipe newRecipe, @AuthenticationPrincipal UserDetails details)  {
        Recipe recipe = recipeService.findRecipeById(iD);
        if (recipe != null) {
            if (details.getUsername().equals(recipe.getAuthor())) {

                recipeService.delete(recipe);
                //recipe.setId(iD);
                recipe.setName(newRecipe.getName());
                recipe.setDescription(newRecipe.getDescription());
                recipe.setCategory(newRecipe.getCategory());
                recipe.setIngredients(newRecipe.getIngredients());
                recipe.setDirections(newRecipe.getDirections());
                recipeService.save(recipe);
                return new ResponseEntity<>( HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/api/recipe/search")
    public ResponseEntity<String> getRecipe(@RequestParam Optional<String> name,
                                            @RequestParam Optional<String> category)
    {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            List<Recipe> recipes;
            if ((name.isPresent() && category.isPresent()) || (name.isEmpty() && category.isEmpty()) ) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else if (category.isPresent()){
                recipes = recipeService.findAllRecipesByCategory(category.get());
                return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(recipes), HttpStatus.OK);
            } else {
                recipes = recipeService.findAllRecipesByName(name.get());
                return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(recipes), HttpStatus.OK);

            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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
    public ResponseEntity<String> deleteRecipe(@PathVariable long iD, @AuthenticationPrincipal UserDetails details) {

        Recipe recipe = recipeService.findRecipeById(iD);
        if (recipe != null) {
            if (recipe.getAuthor().equals(details.getUsername())) {
                recipeService.delete(recipe);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND
        );
    }

}
