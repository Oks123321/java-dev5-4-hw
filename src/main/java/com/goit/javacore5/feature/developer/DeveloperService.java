package com.goit.javacore5.feature.developer;

import com.goit.javacore5.feature.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                "INSERT INTO developer (name, age, gender, salary) VALUES(?, ?, ?, ?)"
        );

        selectByIdSt = conn.prepareStatement(
                "SELECT name, age, gender, salary FROM developer WHERE id = ?"
        );
        selectAllSt = conn.prepareStatement("SELECT id FROM developer");

        renameSt = conn.prepareStatement("UPDATE developer SET name = ? WHERE name = ?");
    }
    public void createNewDevelopers(String[] names, int[] ages, String[] genders, int[] salaries) throws SQLException {
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            int age = ages[i];
            String gender = genders[i];
            int salary = salaries[i];

            insertSt.setString(1, name);
            insertSt.setInt(2, age);
            insertSt.setString(3, gender);
            insertSt.setInt(4, salary);

            insertSt.addBatch();
        }

        insertSt.executeBatch();
    }

    public boolean createNewDeveloper(String name, int age, String gender, int salary) {
        try {
            insertSt.setString(1, name);
            insertSt.setInt(2,age);
            insertSt.setString(3,gender);
            insertSt.setInt(4, salary);
            return insertSt.executeUpdate() == 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
     public String getDeveloperInfo(long id) {
            try {
                selectByIdSt.setLong(1, id);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }

            try(ResultSet rs = selectByIdSt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Developer with id " + id + " not found!");
                    return null;
                }

                String name = rs.getString("name");
                int age = rs.getInt("age");
                int salary = rs.getInt("salary");
                String gender = rs.getString("gender");


                return "name: " + name  + ", age: " + age + ", gender: " + gender + ", salary: " + salary;

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


        public void rename(Map<String, String> renameMap) throws SQLException {
            conn.setAutoCommit(false);

            for (Map.Entry<String, String> keyValue : renameMap.entrySet()) {
                renameSt.setString(1, keyValue.getKey());
                renameSt.setString(2, keyValue.getKey());
                renameSt.setString(3, keyValue.getKey());
                renameSt.setString(4, keyValue.getKey());

                renameSt.addBatch();
            }

            try {
                renameSt.executeBatch();

                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
            } finally {
                conn.setAutoCommit(true);
            }
        }

}

