/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeus.springappgenerator;

import com.codeus.springappgenerator.Connectors.MySqlConnector;
import com.codeus.springappgenerator.Entities.DatabaseConnection;
import com.codeus.springappgenerator.Entities.DatabaseTable;
import com.codeus.springappgenerator.Service.DatabaseConnector;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class SpringAppGenerator {

    public static void main(String[] args) {
        DatabaseConnection mysqlConnection = new DatabaseConnection("localhost", "root", "", "islape", 3306);
        String baseDir = "C:\\Users\\PC\\Desktop\\SpringGenTest\\";
        String ext = ".java";

        String entitiesFolderName = "Entities";
        String repositoriesFolderName = "Repositories";
        String dtoFolderName = "Dtos";
        String controllersFolderName = "Controllers";

        try {
            DatabaseConnector mySqlConnector = new MySqlConnector(mysqlConnection);

            List<DatabaseTable> tables = mySqlConnector.findTables();
            
//            System.out.println("Display Tables");
//            tables.forEach(t -> {System.out.println(t);});

            //Entities
//            tables.forEach(t -> {
//                saveToFile(t.getEntity(), t.getName()+ext, entitiesFolderName, baseDir);
//            });
            //DTO
//          System.out.println("Generating Dtos ....");
            tables.forEach(t -> {
                System.out.println(t.getController("com.codeus.gen")+"\n\n");
//                saveToFile(t.getDto(t, "com.codeus.gen"), t.getName()+ext, dtoFolderName, baseDir);
            });
//          System.out.println("Dtos Generation Complete ...");
//            
//            //Repository
//            tables.forEach(t -> {
//                System.out.println(t.getRepository("com.codeus.gen"));
//                Util.saveToFile(t.getRepository("com.codeus.gen"), t.getName()+ext, repositoriesFolderName, baseDir);
//            });
//            
//            //Controller
//            tables.forEach(t -> {
//                Util.saveToFile(t.getController("com.codeus.gen"), t.getName()+ext, controllersFolderName, baseDir);    
//                Logger.getLogger(SpringAppGenerator.class.getName()).log(Level.INFO, "Generated {0}{1}", new Object[]{t.getName(), ext});
//            });

        } catch (Exception ex) {
            Logger.getLogger(SpringAppGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
