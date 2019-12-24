/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeus.springappgenerator.Service;

import java.util.List;
import com.codeus.springappgenerator.Entities.DatabaseConnection;
import com.codeus.springappgenerator.Entities.DatabaseTable;

/**
 *
 * @author PC
 */
public interface DatabaseConnector {
    public void init(DatabaseConnection dbConnection) throws Exception;
    public List<DatabaseTable> findTables();
    public Boolean tableExist(DatabaseTable dbTable);
}
