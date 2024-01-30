package com.jonasestevam.streaming.dto;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;
    private Set<String> favoriteList;

    public Set<String> getFaroviteList() {
        if (favoriteList == null) {
            this.favoriteList = new HashSet<>();
        }
        return this.favoriteList;
    }

}
