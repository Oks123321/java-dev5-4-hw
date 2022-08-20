package com.goit.javacore5.feature.developer_skill;

import com.goit.javacore5.feature.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DevSkillService {
    private Storage storage;
    private Connection conn;
    private PreparedStatement insertSt;

    public DevSkillService(Storage storage) throws SQLException {
        conn = storage.getConnection();

        insertSt = conn.prepareStatement(
                "INSERT INTO developer_skill (developer_id, skill_id) VALUES(?, ?)"
        );

    }

    public void createNewDevSkills(long[] developer_ids, long[] skill_ids) throws SQLException {
        for (int i = 0; i < developer_ids.length; i++) {
            long developer_id = developer_ids[i];
            long skill_id = skill_ids[i];

            insertSt.setLong(1, developer_id);
            insertSt.setLong(2, skill_id);


            insertSt.addBatch();
        }

        insertSt.executeBatch();
    }
}
