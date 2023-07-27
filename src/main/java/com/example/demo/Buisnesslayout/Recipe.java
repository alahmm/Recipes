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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
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

}
