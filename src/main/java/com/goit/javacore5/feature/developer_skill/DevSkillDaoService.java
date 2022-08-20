package com.goit.javacore5.feature.developer_skill;

import com.goit.javacore5.feature.project_developer.ProjectDev;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevSkillDaoService {
    private PreparedStatement createSt;
    private PreparedStatement getBySkillIdSt;
    private PreparedStatement getByDeveloperIdSt;
    private PreparedStatement getAllSt;

    private PreparedStatement updateSt;
    private PreparedStatement clearSt;



    public DevSkillDaoService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement(
                "INSERT INTO developer_skill (developer_id, skill_id) VALUES(?, ?)"
        );
        getByDeveloperIdSt = connection.prepareStatement(
                "SELECT developer_id, skill_id FROM developer_skill WHERE developer_id = ?"
        );
        getBySkillIdSt = connection.prepareStatement(
                "SELECT developer_id, skill_id FROM developer_skill WHERE skill_id = ?"
        );

        getAllSt = connection.prepareStatement(
                "SELECT developer_id, skill_id FROM developer_skill"
        );



        updateSt = connection.prepareStatement(
                "UPDATE developer_skill SET  developer_id = ?, skill_id = ? WHERE skill_id = ?"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM developer_skill"
        );

    }

    public long create(DevSkill devSkill) throws SQLException {
        createSt.setLong(1, devSkill.getDeveloper_id());
        createSt.setLong(2, devSkill.getSkill_id());


        return (devSkill.getDeveloper_id() + devSkill.getSkill_id());
    }


    public DevSkill getByDeveloperId(long developer_id) throws SQLException {
        getByDeveloperIdSt.setLong(1, developer_id);

        try (ResultSet rs = getByDeveloperIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }


            DevSkill result = new DevSkill();
            result.setDeveloper_id(developer_id);


            long skill_id = rs.getLong("skill_id");
            if (skill_id != 0) {
                result.setSkill_id(skill_id);
            }

            return result;
        }

    }
public DevSkill getBySkillId(long skill_id) throws SQLException {
    getBySkillIdSt.setLong(1, skill_id);

    try (ResultSet rs = getBySkillIdSt.executeQuery()) {
        if (!rs.next()) {
            return null;
        }

        DevSkill result = new DevSkill();
        result.setSkill_id(skill_id);


        long developer_id = rs.getLong("developer_id");
        if (developer_id != 0) {
            result.setDeveloper_id(developer_id);
        }

        return result;
    }
}

    public List<DevSkill> getAll() throws SQLException {
        try (ResultSet rs = getAllSt.executeQuery()) {
            List<DevSkill> result = new ArrayList<>();

            while (rs.next()) {
                DevSkill devSkill = new DevSkill();
                                devSkill.setDeveloper_id(rs.getLong("developer_id"));
                devSkill.setSkill_id(rs.getLong("skill_id"));

                result.add(devSkill);

            }
            return result;
        }
    }

    public void update(DevSkill devSkill) throws SQLException {

        updateSt.setLong(1, devSkill.getDeveloper_id());
        updateSt.setLong(2, devSkill.getSkill_id());

        updateSt.executeUpdate();
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

}
