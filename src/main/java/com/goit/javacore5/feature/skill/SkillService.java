package com.goit.javacore5.feature.skill;

import com.goit.javacore5.feature.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkillService {
    private Storage storage;
    private Connection conn;
    private PreparedStatement insertSt;
    private PreparedStatement selectByIdSt;
    private PreparedStatement selectAllSt;
    private PreparedStatement renameSt;
    public SkillService(Storage storage) throws SQLException {
        conn = storage.getConnection();

        insertSt = conn.prepareStatement(
                "INSERT INTO skill (branch, level) VALUES(?, ?)"
        );

        selectByIdSt = conn.prepareStatement(
                "SELECT branch FROM skill WHERE id = ?"
        );
        selectAllSt = conn.prepareStatement("SELECT id FROM skill");

        renameSt = conn.prepareStatement("UPDATE skill SET branch = ? WHERE branch = ?");
    }
    public void createNewSkills(String[] branches, String[] levels) throws SQLException {
        for (int i = 0; i < branches.length; i++) {
            String branch = branches[i];
            String level = levels[i];

            insertSt.setString(1, branch);
            insertSt.setString(2, level);

            insertSt.addBatch();
        }

        insertSt.executeBatch();
    }

    public boolean createNewSkill(String branch, String level) {
        try {
            insertSt.setString(1, branch);
            insertSt.setString(2,level);

            return insertSt.executeUpdate() == 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
    public String getSkillInfo(long id) {
        try {
            selectByIdSt.setLong(1, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        try(ResultSet rs = selectByIdSt.executeQuery()) {
            if (!rs.next()) {
                System.out.println("Skill with id " + id + " not found!");
                return null;
            }

            String branch = rs.getString("branch");
            String level = rs.getString("level");

            return "branch: " + branch + ", level: " + level;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Long> getIds() {
        List<Long> result = new ArrayList<>();

        try (ResultSet rs = selectAllSt.executeQuery()) {
            while(rs.next()) {
                result.add(rs.getLong("id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

}
