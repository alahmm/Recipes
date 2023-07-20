package com.example.demo.persistence;

import com.example.demo.Buisnesslayout.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findRecipeById(Long id);
}

