package com.goit.javacore5.feature.developer;


import lombok.Data;

@Data
public class Developer {
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


