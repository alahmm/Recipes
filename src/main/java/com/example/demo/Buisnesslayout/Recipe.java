package com.example.demo.Buisnesslayout;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.UpdateTimestamp;

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

    @UpdateTimestamp
    @Basic
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.time.LocalDateTime date;

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
