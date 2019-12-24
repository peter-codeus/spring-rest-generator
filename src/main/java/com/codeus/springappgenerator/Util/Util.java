/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeus.springappgenerator.Util;

import com.codeus.springappgenerator.Entities.Case;
import com.codeus.springappgenerator.SpringAppGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class Util {
    
    
    public static void saveToFile(String data, String filename, String folder, String baseDir) {
        PrintWriter printWriter = null;
        try {
            new File(baseDir, folder).mkdir();
            printWriter = new PrintWriter(new File(baseDir + folder, filename));
            printWriter.print(data);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpringAppGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }

    }

    public static String transformCase(String value, Case casing) {
        value = value.toLowerCase();
        String[] valueParts = value.split("_");
        Boolean lowerCasePresent = value.contains("_");
        if (valueParts.length > 1 && lowerCasePresent) {
            value = "";
            for (int i = 1; i < valueParts.length; i++) {
                value += valueParts[i].substring(0, 1).toUpperCase() + valueParts[i].substring(1);
            }
        }

        switch (casing) {
            case CAMEL:
                if (lowerCasePresent) {
                    return valueParts[0].substring(0, 1).toUpperCase() + valueParts[0].substring(1) + value;
                }
                return value.substring(0, 1).toUpperCase() + value.substring(1);
            case ATTRIBUTE:
                if (!lowerCasePresent) {
                    return value;
                }
                return valueParts[0] + value;
        }
        return "";
    }
}
