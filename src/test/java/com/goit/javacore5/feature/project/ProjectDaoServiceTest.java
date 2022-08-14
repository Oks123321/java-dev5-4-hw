package com.goit.javacore5.feature.project;


import com.goit.javacore5.feature.storage.DatabaseInitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ProjectDaoServiceTest {
    private Connection connection;
    private ProjectDaoService daoService;

    @BeforeEach
    public void beforeEach() throws SQLException {

        final String connectionUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        daoService = new ProjectDaoService(connection);

        daoService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    public void testThatProjectCreateCorrectly() throws SQLException {
        List<Project> originalProjects = new ArrayList<>();


        Project fullValueProject = new Project();
        fullValueProject.setName("TestName");
        fullValueProject.setDescription("description");
        fullValueProject.setDate(LocalDate.of(2021,07,15));

        originalProjects.add(fullValueProject);

        Project nullDescriptionProjects = new Project();
        nullDescriptionProjects.setName("TestName 1");
        nullDescriptionProjects.setDescription(null);
        nullDescriptionProjects.setDate(LocalDate.of(2021,07,15));

        originalProjects.add(nullDescriptionProjects);

        Project nullDateProjects = new Project();
        nullDateProjects.setName("TestName 1");
        nullDateProjects.setDescription("description");
        nullDateProjects.setDate(null);

        originalProjects.add(nullDateProjects);



        for (Project original : originalProjects) {
            long id = daoService.create(original);

            Project saved = daoService.getById(id);

          Assertions.assertEquals(id, saved.getId());
          Assertions.assertEquals(original.getName(), saved.getName());
          Assertions.assertEquals(original.getDescription(), saved.getDescription());
          Assertions.assertEquals(original.getDate(), saved.getDate());

        }
    }
    @Test
    public void getAllTest() throws SQLException {
        Project expected = new Project();
        expected.setName("TestName");
        expected.setDescription("description");


        long id = daoService.create(expected);
        expected.setId(id);

        List<Project> expectedProjects = Collections.singletonList(expected);
        List<Project> actualProjects = daoService.getAll();

        Assertions.assertEquals(expectedProjects, actualProjects);

    }

    @Test
    public void testUpdate() throws SQLException {

        Project original = new Project();
        original.setName("TestName");
        original.setDescription("description");
        original.setDate(LocalDate.now());

        long id = daoService.create(original);
        original.setId(id);

        original.setName("NewName");
        original.setDescription("NewDescription");
        original.setDate(LocalDate.now().minusMonths(1));

        daoService.update(original);

        Project updated = daoService.getById(id);
        Assertions.assertEquals(id, updated.getId());
        Assertions.assertEquals("NewName", updated.getName());
        Assertions.assertEquals("NewDescription", updated.getDescription());
        Assertions.assertEquals(LocalDate.now().minusMonths(1), updated.getDate());
    }
    @Test
    public void testDelete() throws SQLException {
        Project expected = new Project();
        expected.setName("TestName");
        expected.setDescription("description");

        long id = daoService.create(expected);
        daoService.deleteById(id);

        Assertions.assertNull(daoService.getById(id));
    }
    @Test
    public void testExists() throws SQLException {
        Project expected = new Project();
        expected.setName("TestName");
        expected.setDescription("description");

        long id = daoService.create(expected);

        Assertions.assertTrue(daoService.exists(id));
    }
    @Test
    public void testSaveOnExistsProject() throws SQLException {
        Project newProject = new Project();
        newProject.setName("TestName");
        newProject.setDescription("description");
        newProject.setDate(LocalDate.now());

        long id = daoService.save(newProject);
        newProject.setId(id);

        newProject.setName("NewName");
        daoService.save(newProject);

        Project updated = daoService.getById(id);
        Assertions.assertEquals("NewName", updated.getName());
    }

    @Test
    public void testThatExistsReturnFalseForExistingProject() throws SQLException {
        Assertions.assertFalse(daoService.exists(-1));
    }
    @Test
    public void testSaveOnNewProject() throws SQLException {
        Project newProject = new Project();
        newProject.setName("TestName");
        newProject.setDescription("NewDescription");


        long id = daoService.save(newProject);
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
        Project newProject = new Project();
        newProject.setName("TestName");
       newProject.setDescription("description");

        long id = daoService.save(newProject);

        List<Project> actual = daoService.searchByName("Test");
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(id, actual.get(0).getId());

    }


}