package com.example.demo.Buisnesslayout;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class user {

    @Id
    private long id;

    @Pattern(regexp = ".*@.*\\.*")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @OneToMany
    @JoinColumn(name="user-id", nullable = false)
    private List<Recipe> recipes = new ArrayList<>();

}
