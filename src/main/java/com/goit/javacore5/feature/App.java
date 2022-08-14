/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.goit.javacore5.feature;

import com.goit.javacore5.feature.developer.DeveloperService;
import com.goit.javacore5.feature.storage.DatabaseInitService;
import com.goit.javacore5.feature.storage.Storage;

import java.sql.SQLException;

public class App {


    public static void main(String[] args) throws SQLException {
        Storage storage = Storage.getInstance();

//        new DatabaseInitService().initDb(storage);

        DeveloperService developerService = new DeveloperService(storage);

//        developerService.createNewDevelopers(
//                new String[]{"Harry Harryson", "Jack Sparrow", "Julia Petterson", "Mary Poppins", "Tom Soyer", "Andrew Smith"},
//                new int[]{35, 28, 30, 40, 18, 50},
//                new String[]{"male", "male", "female","female", "male", "male"});
    }
}
/*('Harry', 'Harryson', 35, 'male'),
('Jack', 'Sparrow', 28, 'male'),
('Julia', 'Petterson', 30, 'female'),
('Mary', 'Poppins', 40, 'female'),
('Tom', 'Soyer', 18, 'male'),
('Andrew', 'Smith', 50, 'male');*/
