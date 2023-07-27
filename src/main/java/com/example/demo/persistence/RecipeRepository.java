package com.example.demo.persistence;

import com.example.demo.Buisnesslayout.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findRecipeById(Long id);
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<Recipe> findByNameIgnoreCaseContainsOrderByDateDesc(String name);
}

