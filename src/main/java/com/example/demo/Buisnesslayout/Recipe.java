package com.example.demo.Buisnesslayout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Table(name = "recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @JsonIgnore
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recipe_id")
    private long id;

    @NotBlank
    @Column
    private String name;

    @NotBlank
    @Column
    private String description;

    @Size(min = 1)
    @Column
    @ElementCollection
    private List<String> ingredients;

    @Size(min = 1)
    @Column
    @ElementCollection
    private List<String> directions;
}
