package com.goit.javacore5.feature.project_developer;

import com.goit.javacore5.feature.developer.Developer;
import com.goit.javacore5.feature.developer.DeveloperDaoService;
import com.goit.javacore5.feature.storage.DatabaseInitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//class ProjectAndDeveloperDaoServiceTest {
//    private Connection connection;
//    private ProjectAndDeveloperDaoService daoService;
//
//    @BeforeEach
//    public void beforeEach() throws SQLException {
//
//        final String connectionUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
//        new DatabaseInitService().initDb(connectionUrl);
//        connection = DriverManager.getConnection(connectionUrl);
//        daoService = new ProjectAndDeveloperDaoService(connection);
//
//      //  daoService.clear();
//    }
//
//    @AfterEach
//    public void afterEach() throws SQLException {
//        connection.close();
//    }
//
//    @Test
//    public void testThatDeveloperCreateCorrectly() throws SQLException {
//        List<ProjectAndDeveloper> pd = new ArrayList<>();
//
//
//        ProjectAndDeveloper fullValuePd = new ProjectAndDeveloper();
//        fullValueDeveloper.setName("TestName");
//        fullValueDeveloper.setAge(20);
//        fullValueDeveloper.setGender(Developer.Gender.male);
//        fullValueDeveloper.setSalary(1000);
//        originalDevelopers.add(fullValueDeveloper);
//
//        Developer nullAgeDeveloper = new Developer();
//        nullAgeDeveloper.setName("TestName 1");
//        nullAgeDeveloper.setAge(0);
//        nullAgeDeveloper.setGender(Developer.Gender.male);
//        nullAgeDeveloper.setSalary(1000);
//        originalDevelopers.add(nullAgeDeveloper);
//
//        Developer nullGenderDeveloper = new Developer();
//        nullGenderDeveloper.setName("TestName 2");
//        nullGenderDeveloper.setAge(20);
//        nullGenderDeveloper.setGender(null);
//        nullGenderDeveloper.setSalary(1000);
//        originalDevelopers.add(nullGenderDeveloper);
//
//        Developer nullSalaryDeveloper = new Developer();
//        nullSalaryDeveloper.setName("TestName 2");
//        nullSalaryDeveloper.setAge(20);
//        nullSalaryDeveloper.setGender(Developer.Gender.male);
//        nullSalaryDeveloper.setSalary(0);
//        originalDevelopers.add(nullSalaryDeveloper);
//
//        for (Developer original : originalDevelopers) {
//            long id = daoService.create(original);
//
//            Developer saved = daoService.getById(id);
//
//            Assertions.assertEquals(id, saved.getId());
//            Assertions.assertEquals(original.getName(), saved.getName());
//            Assertions.assertEquals(original.getAge(), saved.getAge());
//            Assertions.assertEquals(original.getGender(), saved.getGender());
//
//        }
//    }
//
//}