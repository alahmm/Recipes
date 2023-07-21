package com.example.demo.Buisnesslayout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.List;
@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @JsonIgnore
    @Id
    @Column(name = "recipe_id")
    private long id;

    @NotNull
    @NonNull
    @NotBlank
    private String name;

    @NotNull
    @NonNull
    @NotBlank
    private String category;

    @Temporal(TemporalType.TIMESTAMP)
    private String orderDate;

    @NotNull
    @NonNull
    @NotBlank
    private String description;

    @Size(min = 1)
    @NotNull
    @ElementCollection
    private List<String> ingredients;

    @Size(min = 1)
    @NotNull
    @ElementCollection
    private List<String> directions;

}
