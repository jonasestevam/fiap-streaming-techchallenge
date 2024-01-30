package com.jonasestevam.streaming.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document
public class User {

    @Id
    private String id;

    @Field
    private String name;

    @Indexed(unique = true)
    private String email;

    private Set<String> faroviteList;

}
