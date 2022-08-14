package com.goit.javacore5.feature.developer;

import com.goit.javacore5.feature.storage.DatabaseInitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



class DeveloperDaoServiceTest {
    private Connection connection;
    private DeveloperDaoService daoService;

    @BeforeEach
    public void beforeEach() throws SQLException {

        final String connectionUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        daoService = new DeveloperDaoService(connection);

        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testThatDeveloperCreateCorrectly() throws SQLException {
        List<Developer> originalDevelopers = new ArrayList<>();


        Developer fullValueDeveloper = new Developer();
        fullValueDeveloper.setName("TestName");
        fullValueDeveloper.setAge(20);
        fullValueDeveloper.setGender(Developer.Gender.male);
        fullValueDeveloper.setSalary(1000);
        originalDevelopers.add(fullValueDeveloper);

        Developer nullAgeDeveloper = new Developer();
        nullAgeDeveloper.setName("TestName 1");
        nullAgeDeveloper.setAge(0);
        nullAgeDeveloper.setGender(Developer.Gender.male);
        nullAgeDeveloper.setSalary(1000);
        originalDevelopers.add(nullAgeDeveloper);

        Developer nullGenderDeveloper = new Developer();
        nullGenderDeveloper.setName("TestName 2");
        nullGenderDeveloper.setAge(20);
        nullGenderDeveloper.setGender(null);
        nullGenderDeveloper.setSalary(1000);
        originalDevelopers.add(nullGenderDeveloper);

        Developer nullSalaryDeveloper = new Developer();
        nullSalaryDeveloper.setName("TestName 2");
        nullSalaryDeveloper.setAge(20);
        nullSalaryDeveloper.setGender(Developer.Gender.male);
        nullSalaryDeveloper.setSalary(0);
        originalDevelopers.add(nullSalaryDeveloper);

        for (Developer original : originalDevelopers) {
            long id = daoService.create(original);

            Developer saved = daoService.getById(id);

            Assertions.assertEquals(id, saved.getId());
            Assertions.assertEquals(original.getName(), saved.getName());
            Assertions.assertEquals(original.getAge(), saved.getAge());
            Assertions.assertEquals(original.getGender(), saved.getGender());

        }
    }

    @Test
    public void getAllTest() throws SQLException {
        Developer expected = new Developer();
        expected.setName("TestName");
        expected.setAge(20);
        expected.setGender(Developer.Gender.male);

        long id = daoService.create(expected);
        expected.setId(id);

        List<Developer> expectedDevelopers = Collections.singletonList(expected);
        List<Developer> actualDevelopers = daoService.getAll();

        Assertions.assertEquals(expectedDevelopers, actualDevelopers);

    }

    @Test
    public void testUpdate() throws SQLException {

        Developer original = new Developer();
        original.setName("TestName");
        original.setAge(20);
        original.setGender(Developer.Gender.male);

        long id = daoService.create(original);
        original.setId(id);

        original.setName("NewName");
        original.setAge(25);
        original.setGender(Developer.Gender.female);
        daoService.update(original);

        Developer updated = daoService.getById(id);
        Assertions.assertEquals(id, updated.getId());
        Assertions.assertEquals("NewName", updated.getName());
        Assertions.assertEquals(25, updated.getAge());
        Assertions.assertEquals(Developer.Gender.female, updated.getGender());

    }
    @Test
    public void testDelete() throws SQLException {
        Developer expected = new Developer();
        expected.setName("TestName");
        expected.setAge(20);
        expected.setGender(Developer.Gender.male);

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
    @Test
    public void testExists() throws SQLException {
        Developer expected = new Developer();
        expected.setName("TestName");
        expected.setAge(25);
        expected.setGender(Developer.Gender.male);

        long id = daoService.create(expected);

        Assertions.assertTrue(daoService.exists(id));
    }
    @Test
    public void testSaveOnExistsUser() throws SQLException {
        Developer newDeveloper = new Developer();
        newDeveloper.setName("TestName");
        newDeveloper.setAge(25);
        newDeveloper.setGender(Developer.Gender.male);

        long id = daoService.save(newDeveloper);
        newDeveloper.setId(id);

        newDeveloper.setName("NewName");
        daoService.save(newDeveloper);

        Developer updated = daoService.getById(id);
        Assertions.assertEquals("NewName", updated.getName());
    }

    @Test
    public void testThatExistsReturnFalseForExistingDeveloper() throws SQLException {
        Assertions.assertFalse(daoService.exists(-1));
    }
    @Test
    public void testSaveOnNewUser() throws SQLException {
        Developer newDeveloper = new Developer();
        newDeveloper.setName("TestName");
        newDeveloper.setAge(20);
        newDeveloper.setGender(Developer.Gender.male);

        long id = daoService.save(newDeveloper);
        Assertions.assertTrue(daoService.exists(id));
    }

    @Test
    public void testSearchOnEmpty() throws SQLException {
        Assertions.assertEquals(
                Collections.emptyList(),
                daoService.searchByName("name")
        );
    }
    @Test
    public void testSearchOnFilledDb() throws SQLException {
        Developer newDeveloper = new Developer();
        newDeveloper.setName("TestName");
        newDeveloper.setAge(20);
        newDeveloper.setGender(Developer.Gender.male);

        long id = daoService.save(newDeveloper);

        List<Developer> actual = daoService.searchByName("Test");
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(id, actual.get(0).getId());

    }
}




