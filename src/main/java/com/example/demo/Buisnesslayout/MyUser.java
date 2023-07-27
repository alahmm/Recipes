package com.example.demo.Buisnesslayout;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
public class MyUser{

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

/*    @OneToMany(mappedBy = "MyUser", cascade = CascadeType.ALL)
    private List<Recipe> recipes = new ArrayList<>();*/

}
