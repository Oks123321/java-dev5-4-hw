package com.goit.javacore5.feature.project_developer;

import com.goit.javacore5.feature.developer.Developer;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDevDaoService {
    private PreparedStatement createSt;
    private PreparedStatement getByProjectIdSt;
    private PreparedStatement getByDeveloperIdSt;
    private PreparedStatement getAllSt;
//    private PreparedStatement countSalaryDevSt;
    private PreparedStatement updateSt;
    private PreparedStatement clearSt;
    private PreparedStatement getSalaryFromProjectSt;
    private PreparedStatement getByProjectIdSalarySt;


    public ProjectDevDaoService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement(
                "INSERT INTO project_developer (project_id, developer_id) VALUES(?, ?)"
        );
        getByProjectIdSt = connection.prepareStatement(
                "SELECT project_id, developer_id FROM project_developer WHERE project_id = ?"
        );
        getByDeveloperIdSt = connection.prepareStatement(
                "SELECT project_id, developer_id FROM project_developer WHERE developer_id = ?"
        );

        getAllSt = connection.prepareStatement(
                "SELECT project_id, developer_id FROM project_developer"
        );


//        countSalaryDevSt = connection.prepareStatement("SELECT project_id, SUM(salary)" +
//                " FROM developer LEFT JOIN project_developer " +
//                "ON developer.id = project_developer.developer_id WHERE project_id = ?");

        updateSt = connection.prepareStatement(
                "UPDATE project_developer SET project_id = ?, developer_id = ? WHERE project_id = ?"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM project_developer"
        );
        getSalaryFromProjectSt = connection.prepareStatement(
                "SELECT project_id, developer_id,  salary" +
                " FROM developer LEFT JOIN project_developer " +
                "ON developer.id = project_developer.developer_id "
        );
        getByProjectIdSalarySt = connection.prepareStatement("SELECT project_id, developer_id,  salary" +
                " FROM developer LEFT JOIN project_developer " +
                "ON developer.id = project_developer.developer_id WHERE project_id = ?"
        );

//developer_id, id, name, age, gender,
    }

    public long create(ProjectDev projectDev) throws SQLException {
        createSt.setLong(1, projectDev.getProject_id());
        createSt.setLong(2, projectDev.getDeveloper_id());


        return (projectDev.getProject_id() + projectDev.getDeveloper_id());
    }

    public ProjectDev getByProjectId(long project_id) throws SQLException {
        getByProjectIdSt.setLong(1, project_id);

        try (ResultSet rs = getByProjectIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }

            ProjectDev result = new ProjectDev();
            result.setProject_id(project_id);


            long developer_id = rs.getLong("developer_id");
            if (developer_id != 0) {
                result.setDeveloper_id(developer_id);
            }

            return result;
        }
    }

    public ProjectDev getByDeveloperId(long developer_id) throws SQLException {
        getByDeveloperIdSt.setLong(1, developer_id);

        try (ResultSet rs = getByDeveloperIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }


            ProjectDev result = new ProjectDev();
            result.setDeveloper_id(developer_id);


            long project_id = rs.getLong("project_id");
            if (project_id != 0) {
                result.setProject_id(project_id);
            }

            return result;
        }

    }

    public List<ProjectDev> getAll() throws SQLException {
        try (ResultSet rs = getAllSt.executeQuery()) {
            List<ProjectDev> result = new ArrayList<>();

            while (rs.next()) {
                ProjectDev projectDev = new ProjectDev();
                projectDev.setProject_id(rs.getLong("project_id"));
                projectDev.setDeveloper_id(rs.getLong("developer_id"));


                result.add(projectDev);

            }
            return result;
        }
    }

    public void update(ProjectDev projectDev) throws SQLException {
        updateSt.setLong(1, projectDev.getProject_id());
        updateSt.setLong(2, projectDev.getDeveloper_id());

        updateSt.executeUpdate();
    }


    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<ProjectDev> getSalaryFromProject() throws SQLException {
        try (ResultSet rs = getSalaryFromProjectSt.executeQuery()) {
            List<ProjectDev> result = new ArrayList<>();

            while (rs.next()) {
                ProjectDev projectDev = new ProjectDev();

                projectDev.setProject_id(rs.getLong("project_id"));
                projectDev.setDeveloper_id(rs.getLong("developer_id"));

                result.add(projectDev);

                Developer developer = new Developer();
//                developer.setId(rs.getLong("id"));
//                developer.setName(rs.getString("name"));
//                developer.setAge(rs.getInt("age"));
//                developer.setGender(Developer.Gender.valueOf(rs.getString("gender")));
                developer.setSalary(rs.getInt("salary"));
                result.add(developer);

            }
            return result;

        }
    }
//    public ProjectDev getByProjectIdSalary(long project_id) throws SQLException {
//        getByProjectIdSalarySt.setLong(1, project_id);
//
//        try (ResultSet rs = getByProjectIdSt.executeQuery()) {
//            if (!rs.next()) {
//                return null;
//            }
//
//            ProjectDev result = new ProjectDev();
//            result.setProject_id(project_id);
//
//
//            long developer_id = rs.getLong("developer_id");
//            if (developer_id != 0) {
//                result.setDeveloper_id(developer_id);
//            }
//
//            return result;
//        }
//    }

}





