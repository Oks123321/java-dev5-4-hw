package com.goit.javacore5.feature.developer;

import com.goit.javacore5.feature.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeveloperService {
    private Storage storage;
    private Connection conn;
    private PreparedStatement insertSt;
    private PreparedStatement selectByIdSt;
    private PreparedStatement selectAllSt;
    private PreparedStatement renameSt;
    public DeveloperService(Storage storage) throws SQLException {
        conn = storage.getConnection();

        insertSt = conn.prepareStatement(
                "INSERT INTO developer (name, age, gender) VALUES(?, ?, ?)"
        );

        selectByIdSt = conn.prepareStatement(
                "SELECT name, age FROM developer WHERE id = ?"
        );
        selectAllSt = conn.prepareStatement("SELECT id FROM developer");

        renameSt = conn.prepareStatement("UPDATE developer SET name = ? WHERE name = ?");
    }
    public void createNewDevelopers(String[] names, int[] ages, String[] genders ) throws SQLException {
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            int age = ages[i];
            String gender = genders[i];

            insertSt.setString(1, name);
            insertSt.setInt(2, age);
            insertSt.setString(3, gender);

            insertSt.addBatch();
        }

        insertSt.executeBatch();
    }

    public boolean createNewDeveloper(String name, int age, String gender) {
        try {
            insertSt.setString(1, name);
            insertSt.setInt(2,age);
            insertSt.setString(3,gender);
            return insertSt.executeUpdate() == 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

}

