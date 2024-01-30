package com.jonasestevam.streaming.dto;

import java.time.LocalDateTime;

import com.jonasestevam.streaming.enuns.CategoriesEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VideoDTO {
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private CategoriesEnum category;

    private LocalDateTime publishedAt;
    private String link;
    private String ownerId;
    private String categoryName;
    private String categoryDescription;

    public void setCategoryName() {
        this.categoryName = this.category.getName();
    }

    public void setCategoryDescription() {
        this.categoryDescription = this.category.getDescription();

    }

}
