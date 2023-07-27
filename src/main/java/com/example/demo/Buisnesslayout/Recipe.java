package com.example.demo.Buisnesslayout;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "reciipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "newRecipes")
public class Recipe {
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Id
    private long id;

    @NotNull
    @NonNull
    @NotBlank
    private String name;

    @NotNull
    @NonNull
    @NotBlank
    private String category;

    @UpdateTimestamp
    @Basic
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.time.LocalDateTime date;

    @NotNull
    @NonNull
    @NotBlank
    private String description;

    @OrderColumn
    @ElementCollection
    @NotNull
    @NotNull
    @Size(min = 1)
    private String[] ingredients;

    @OrderColumn
    @ElementCollection
    @NotNull
    @NotNull
    @Size(min = 1)
    private String[] directions;


    @JsonIgnore
    private String author;

/*    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser myUser;*/

    public Recipe(@NotNull @NonNull String name, @NotNull @NonNull String category, LocalDateTime date, @NotNull @NonNull String description, @NotNull @NotNull @Size(min = 1) String[] ingredients, @NotNull @NotNull @Size(min = 1) String[] directions, String author) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
        this.author = author;
    }
}
