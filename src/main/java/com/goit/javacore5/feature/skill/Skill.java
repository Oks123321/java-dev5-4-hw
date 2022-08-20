package com.goit.javacore5.feature.skill;

import lombok.Data;

@Data
public class Skill {
    private long id;
    private Branch branch;
    private Level level;

    public enum Branch {
        java,
        python,
        javaScript
    }

    public enum Level{
        junior,
        middle,
        senior
    }

}
