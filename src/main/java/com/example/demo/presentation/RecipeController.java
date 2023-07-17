package com.example.demo.presentation;

import com.example.demo.Buisnesslayout.ID;
import com.example.demo.Buisnesslayout.Recipe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
public class RecipeController {
    private ObjectMapper objectMapper = new ObjectMapper();
    private ConcurrentMap<Integer, Recipe> map = new ConcurrentHashMap<>();
    public int id = 1;
    private ID idNew = new ID();

    @PostMapping("/api/recipe/new")
    public ResponseEntity<String> RecipeSetter(@RequestBody Recipe recipeNew) throws JsonProcessingException {
        map.put(id, recipeNew);
        idNew.setId(id);
        id++;
        return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                writeValueAsString(idNew), HttpStatus.OK);
    }
    @GetMapping("/api/recipe/{iD}")
    public ResponseEntity<String> getRecipe(@PathVariable int iD) throws JsonProcessingException {
        for (Map.Entry<Integer, Recipe> mapNew : map.entrySet()
             ) {
            if (mapNew.getKey() == iD) {
                return new ResponseEntity<>(objectMapper.writerWithDefaultPrettyPrinter().
                        writeValueAsString(mapNew.getValue()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND
        );
    }
}
