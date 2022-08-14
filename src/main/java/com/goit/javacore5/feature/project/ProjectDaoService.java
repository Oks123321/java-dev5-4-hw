package com.goit.javacore5.feature.project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoService {
    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement selectMaxIdSt;
    private PreparedStatement getAllSt;
    private PreparedStatement updateSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement existsByIdSt;
    private PreparedStatement clearSt;
    private PreparedStatement searchSt;
    private PreparedStatement countSalaryDevSt;

    public ProjectDaoService(Connection connection) throws SQLException {

        createSt = connection.prepareStatement(
                "INSERT INTO project(name, description, date) VALUES(?, ?, ?)"
        );
        getByIdSt = connection.prepareStatement(
                "SELECT name, description, date FROM project WHERE id = ?"
        );
        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM project"
        );
        getAllSt = connection.prepareStatement(
                "SELECT id, name, description, date FROM project"
        );
        updateSt = connection.prepareStatement(
                "UPDATE project SET name = ?, description = ?, date =? WHERE id = ?"
        );
        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM project WHERE id = ?"
        );
        existsByIdSt = connection.prepareStatement(
                "SELECT count(*) > 0 AS projectExists FROM project WHERE id = ?"
        );
        clearSt = connection.prepareStatement(
                "DELETE FROM project"
        );
        searchSt = connection.prepareStatement(
                "SELECT id, name, description, date FROM project WHERE name LIKE ?"
        );

        );
    }


    public long create(Project project) throws SQLException {
        createSt.setString(1, project.getName());
        createSt.setString(2,
                project.getDescription() == null ? null : project.getDescription());
        createSt.setString(3,project.getDate() == null ? null : project.getDate().toString());

        createSt.executeUpdate();


        long id;

        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public Project getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);

        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }

            Project result = new Project();
            result.setId(id);
            result.setName(rs.getString("name"));

            String description = rs.getString("description");
            if (description != null) {
                result.setDescription(description);
            }

            String date = rs.getString("date");
            if (date != null) {
                result.setDate(LocalDate.parse(date));
            }

            return result;
        }

    }

    public List<Project> getAll() throws SQLException {
        try (ResultSet rs = getAllSt.executeQuery()) {
            List<Project> result = new ArrayList<>();

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getLong("id"));
                project.setName(rs.getString("name"));

                String description = rs.getString("description");
                if (description != null) {
                    project.setDescription(description);
                }
                result.add(project);

            }
            return result;
        }
    }

    public void update(Project project) throws SQLException {
        updateSt.setString(1, project.getName());
        updateSt.setString(2, project.getDescription());
       updateSt.setString(3, project.getDate().toString());
        updateSt.setLong(4, project.getId());


        updateSt.executeUpdate();
    }
/*  public void update(Human human) throws SQLException {
        updateSt.setString(1, human.getName());
        updateSt.setString(2, human.getBirthday().toString());
        updateSt.setString(3, human.getGender().name());
        updateSt.setLong(4, human.getId());

        updateSt.executeUpdate();
    }
*/
    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }

    public boolean exists(long id) throws SQLException {
        existsByIdSt.setLong(1, id);
        try (ResultSet rs = existsByIdSt.executeQuery()) {
            rs.next();

            return rs.getBoolean("projectExists");
        }
    }

    public long save(Project project) throws SQLException {
        if (exists(project.getId())) {
            update(project);
            return project.getId();
        }

        return create(project);
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<Project> searchByName(String query) throws SQLException {
        searchSt.setString(1, "%" + query + "%");
        try (ResultSet rs = searchSt.executeQuery()) {
            List<Project> result = new ArrayList<>();

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getLong("id"));
                project.setName(rs.getString("name"));

                String description = rs.getString("description");
                if (description != null) {
                    project.setDescription(description);
                }
                result.add(project);

            }
            return result;
        }

    }
}
