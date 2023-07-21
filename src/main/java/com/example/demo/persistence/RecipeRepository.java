package com.example.demo.persistence;

import com.example.demo.Buisnesslayout.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findRecipeById(Long id);
    @Query(value = "select * from recipes where category Like Concat('%',category,'%') order by order_date DESC", nativeQuery = true)
    List<Recipe> findByCategoryEquals(String category);

    List<Recipe> findByNameContaining(String name);
}

