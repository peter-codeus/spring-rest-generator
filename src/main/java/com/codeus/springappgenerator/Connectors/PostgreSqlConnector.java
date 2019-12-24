/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeus.springappgenerator.Connectors;

import com.codeus.springappgenerator.Entities.DatabaseConnection;
import com.codeus.springappgenerator.Entities.DatabaseTable;
import com.codeus.springappgenerator.Service.DatabaseConnector;

import java.util.List;

/**
 *
 * @author PC
 */
public class PostgreSqlConnector implements DatabaseConnector {

    @Override
    public void init(DatabaseConnection dbConnection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DatabaseTable> findTables() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean tableExist(DatabaseTable dbTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
