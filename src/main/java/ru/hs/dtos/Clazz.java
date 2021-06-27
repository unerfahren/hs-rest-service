package ru.hs.dtos;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonClassDescription
public class Clazz {
    private String slug;
    private int id;
    private String name;
}
