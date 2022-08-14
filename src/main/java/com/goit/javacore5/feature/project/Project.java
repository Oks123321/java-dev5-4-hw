package com.goit.javacore5.feature.project;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Project {
    private long id;
    private String name;
    private String description;
    private LocalDate date;

}

