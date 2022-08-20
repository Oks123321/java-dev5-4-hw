package com.goit.javacore5.feature.project_developer;

import com.goit.javacore5.feature.storage.Storage;
import jdk.jshell.execution.LoaderDelegate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectDevService {
    private Storage storage;
    private Connection conn;
    private PreparedStatement insertSt;

    public ProjectDevService(Storage storage) throws SQLException {
        conn = storage.getConnection();

        insertSt = conn.prepareStatement(
                "INSERT INTO project_developer (project_id, developer_id) VALUES(?, ?)"
        );

    }
    public void createNewProjectDevs(long[] project_ids, long[] developer_ids) throws SQLException {
        for (int i = 0; i < project_ids.length; i++) {
            long project_id = project_ids[i];
            long developer_id = developer_ids[i];



            insertSt.setLong(1,project_id);
            insertSt.setLong(2, developer_id);

            insertSt.addBatch();
        }

        insertSt.executeBatch();
    }

}
