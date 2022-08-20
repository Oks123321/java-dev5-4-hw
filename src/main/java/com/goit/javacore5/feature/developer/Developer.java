package com.goit.javacore5.feature.developer;


import com.goit.javacore5.feature.project_developer.ProjectDev;
import lombok.Data;

@Data
public class Developer extends ProjectDev {
private long id;
private String name;
private int age;
private Gender gender;
private int salary;



    public enum Gender{
        male,
        female,
        unknown
    }
}


