package com.jonasestevam.streaming.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.jonasestevam.streaming.enuns.CategoriesEnum;

import lombok.Data;

@Data
@Document
public class Video {
    @Id
    private String id;

    @Field
    private String title;

    @Field
    private String description;

    @Field
    private LocalDateTime publishedAt = LocalDateTime.now();

    @Field
    private String link;

    @Field
    private String ownerId;

    @Field
    private CategoriesEnum category;

    public void setPublishedAt(LocalDateTime publishedAt) {
        if (publishedAt == null) {
            this.publishedAt = LocalDateTime.now();
        } else {
            this.publishedAt = publishedAt;
        }
    }

}
