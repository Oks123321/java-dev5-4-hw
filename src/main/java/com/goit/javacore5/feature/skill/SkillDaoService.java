package com.goit.javacore5.feature.skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillDaoService {
    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement selectMaxIdSt;
    private PreparedStatement getAllSt;

    private PreparedStatement updateSt;

    private PreparedStatement deleteByIdSt;

    private PreparedStatement existsByIdSt;

    private PreparedStatement clearSt;

    private PreparedStatement searchSt;

    public SkillDaoService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement(
                "INSERT INTO skill (branch, level) VALUES(?, ?)"
        );
        getByIdSt = connection.prepareStatement(
                "SELECT branch, level FROM skill WHERE id = ?"
        );
        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM skill"
        );
        getAllSt = connection.prepareStatement(
                "SELECT id, branch, level FROM skill"
        );
        updateSt = connection.prepareStatement(
                "UPDATE skill SET branch = ?, level = ? WHERE id = ?"
        );
        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM skill WHERE id = ?"
        );
        existsByIdSt = connection.prepareStatement(
                "SELECT count(*) > 0 AS skillExists FROM skill WHERE id = ?"
        );
        clearSt = connection.prepareStatement(
                "DELETE FROM skill"
        );
        searchSt = connection.prepareStatement(
                "SELECT id, branch, level FROM skill WHERE branch LIKE ?"
        );
    }

    public long create(Skill skill) throws SQLException {
        createSt.setString(1, skill.getBranch() == null ? null : skill.getBranch().name());
        createSt.setString(2, skill.getLevel() == null ? null : skill.getLevel().name());

        createSt.executeUpdate();


        long id;

        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public Skill getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);

        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }

            Skill result = new Skill();
            result.setId(id);

            String branch = rs.getString("branch");
            if (branch != null) {
                result.setBranch(Skill.Branch.valueOf(branch));
            }
            String level = rs.getString("level");
            if (level != null) {
                result.setLevel(Skill.Level.valueOf(level));
            }


            return result;
        }
    }

    public List<Skill> getAll() throws SQLException {
        try (ResultSet rs = getAllSt.executeQuery()) {
            List<Skill> result = new ArrayList<>();

            while (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getLong("id"));

                String branch = rs.getString("branch");
                if (branch != null) {
                    skill.setBranch(Skill.Branch.valueOf(branch));
                }
                String level = rs.getString("level");
                if (level != null) {
                    skill.setLevel(Skill.Level.valueOf(level));
                }
                result.add(skill);

            }
            return result;
        }
    }


    public void update(Skill skill) throws SQLException {

        updateSt.setString(3, skill.getBranch().name());
        updateSt.setString(3, skill.getBranch().name());

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

            return rs.getBoolean("skillExists");
        }
    }

    public long save(Skill skill) throws SQLException {
        if (exists(skill.getId())) {
            update(skill);
            return skill.getId();
        }

        return create(skill);
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<Skill> searchByName(String query) throws SQLException {
        searchSt.setString(1, "%" + query + "%");
        try (ResultSet rs = searchSt.executeQuery()) {
            List<Skill> result = new ArrayList<>();

            while (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getLong("id"));


                String branch = rs.getString("branch");
                if (branch != null) {
                    skill.setBranch(Skill.Branch.valueOf(branch));
                }
                String level = rs.getString("level");
                if (level != null) {
                    skill.setLevel(Skill.Level.valueOf(level));
                }
                result.add(skill);

            }
            return result;
        }

    }
}
