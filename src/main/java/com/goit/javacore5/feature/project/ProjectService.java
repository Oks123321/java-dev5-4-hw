package com.goit.javacore5.feature.project;

import com.goit.javacore5.feature.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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
                "INSERT INTO project (name, description, date) VALUES(?, ?, ?)"
        );

        selectByIdSt = conn.prepareStatement(
                "SELECT name, description, date FROM project WHERE id = ?"
        );
        selectAllSt = conn.prepareStatement("SELECT id FROM project");

        renameSt = conn.prepareStatement("UPDATE project SET name = ? WHERE name = ?");
    }
    public void createNewProjects(String[] names, String[] descriptions, LocalDate[] dates) throws SQLException {
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            String description = descriptions[i];
            LocalDate date = dates[i];

            insertSt.setString(1, name);
            insertSt.setString(2, description);
            insertSt.setString(3, date.toString());


            insertSt.addBatch();
        }

        insertSt.executeBatch();
    }

    public boolean createNewProject(String name, String description, LocalDate date) {
        try {
            insertSt.setString(1, name);
            insertSt.setString(2,description);
            insertSt.setString(3, date.toString());

            return insertSt.executeUpdate() == 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
