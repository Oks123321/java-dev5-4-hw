package com.goit.javacore5.feature.developer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperDaoService {
    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement selectMaxIdSt;
    private PreparedStatement getAllSt;
    private PreparedStatement updateSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement existsByIdSt;
    private PreparedStatement clearSt;
    private PreparedStatement searchSt;

    public DeveloperDaoService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement(
                "INSERT INTO developer(name, age, gender, salary) VALUES(?, ?, ?, ?)"
        );
        getByIdSt = connection.prepareStatement(
                "SELECT name, age, gender, salary FROM developer WHERE id = ?"
        );
        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM developer"
        );
        getAllSt = connection.prepareStatement(
                "SELECT id, name, age, gender, salary FROM developer"
        );
        updateSt = connection.prepareStatement(
                "UPDATE developer SET name = ?, age = ?, gender = ?, salary = ? WHERE id = ?"
        );
        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM developer WHERE id = ?"
        );
        existsByIdSt = connection.prepareStatement(
                "SELECT count(*) > 0 AS developerExists FROM developer WHERE id = ?"
        );
        clearSt = connection.prepareStatement(
                "DELETE FROM developer"
        );
        searchSt = connection.prepareStatement(
                "SELECT id, name, age, gender, salary FROM developer WHERE name LIKE ?"
        );
    }

    public long create(Developer developer) throws SQLException {
        createSt.setString(1, developer.getName());
        createSt.setInt(2,
                developer.getAge() == 0 ? 0 : developer.getAge());
        createSt.setString(3,
                developer.getGender() == null ? null : developer.getGender().name());
        createSt.setInt(4, developer.getSalary() == 0 ? 0 : developer.getSalary());
        createSt.executeUpdate();


        long id;

        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public Developer getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);

        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }

            Developer result = new Developer();
            result.setId(id);
            result.setName(rs.getString("name"));
            int age = rs.getInt("age");
            if (age != 0) {
                result.setAge(rs.getInt("age"));
            }
            String gender = rs.getString("gender");
            if (gender != null) {
                result.setGender(Developer.Gender.valueOf(gender));
            }
            int salary = rs.getInt("salary");
            if (salary != 0) {
                result.setSalary(rs.getInt("salary"));
            }


            return result;
        }

    }

    public List<Developer> getAll() throws SQLException {
        try (ResultSet rs = getAllSt.executeQuery()) {
            List<Developer> result = new ArrayList<>();

            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setName(rs.getString("name"));

                int age = rs.getInt("age");
                if (age != 0) {
                    developer.setAge(age);
                }
                String gender = rs.getString("gender");
                if (gender != null) {
                    developer.setGender(Developer.Gender.valueOf(gender));
                }
                int salary = rs.getInt("salary");
                if (salary != 0) {
                    developer.setSalary(salary);
                }
                result.add(developer);

            }
            return result;
        }
    }

    public void update(Developer developer) throws SQLException {
        updateSt.setString(1, developer.getName());
        updateSt.setInt(2, developer.getAge());
        updateSt.setString(3, developer.getGender().name());
        updateSt.setInt(4, developer.getSalary());
        updateSt.setLong(5, developer.getId());

        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }

    public boolean exists(long id) throws SQLException {
        existsByIdSt.setLong(1, id);
        try (ResultSet rs = existsByIdSt.executeQuery()) {
            rs.next();

            return rs.getBoolean("developerExists");
        }
    }

    public long save(Developer developer) throws SQLException {
        if (exists(developer.getId())) {
            update(developer);
            return developer.getId();
        }

        return create(developer);
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<Developer> searchByName(String query) throws SQLException {
        searchSt.setString(1, "%" + query + "%");
        try (ResultSet rs = searchSt.executeQuery()) {
            List<Developer> result = new ArrayList<>();

            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setName(rs.getString("name"));

                int age = rs.getInt("age");
                if (age != 0) {
                    developer.setAge(age);
                }
                String gender = rs.getString("gender");
                if (gender != null) {
                    developer.setGender(Developer.Gender.valueOf(gender));
                }
                int salary = rs.getInt("salary");
                if (salary != 0) {
                    developer.setSalary(salary);
                }
                result.add(developer);

            }
            return result;
        }

    }
}




