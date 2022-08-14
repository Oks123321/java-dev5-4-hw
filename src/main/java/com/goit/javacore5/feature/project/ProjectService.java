package com.goit.javacore5.feature.project;

import com.goit.javacore5.feature.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectService {
    private Storage storage;
    private Connection conn;
    private PreparedStatement insertSt;
    private PreparedStatement selectByIdSt;
    private PreparedStatement selectAllSt;
    private PreparedStatement renameSt;
    public ProjectService(Storage storage) throws SQLException {
        conn = storage.getConnection();

        insertSt = conn.prepareStatement(
                "INSERT INTO project (name, descriptions, date) VALUES(?, ?, ?)"
        );

        selectByIdSt = conn.prepareStatement(
                "SELECT name, descriptions, date FROM project WHERE id = ?"
        );
        selectAllSt = conn.prepareStatement("SELECT id FROM project");

        renameSt = conn.prepareStatement("UPDATE project SET name = ? WHERE name = ?");
    }
    public void createNewProjects(String[] names, String[] descriptions, String[] genders, String[] dates) throws SQLException {
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            String description = descriptions[i];
            String gender = genders[i];
            String date = dates[i];

            insertSt.setString(1, name);
            insertSt.setString(2, description);
            insertSt.setString(3, gender);
            insertSt.setString(4, date);

            insertSt.addBatch();
        }

        insertSt.executeBatch();
    }

    public boolean createNewProject(String name, String description, String date) {
        try {
            insertSt.setString(1, name);
            insertSt.setString(2,description);
            insertSt.setString(3, date);

            return insertSt.executeUpdate() == 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
