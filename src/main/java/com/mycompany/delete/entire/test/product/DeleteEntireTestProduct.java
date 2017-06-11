/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.delete.entire.test.product;

import com.mycompany.delete.entire.test.product.util.MySQLHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cthies
 */
public class DeleteEntireTestProduct {

    public static void main(String[] args) {

        String v_host = System.getProperty("MYSQL_HOSTNAME", "localhost");
        String v_schm = System.getProperty("MYSQL_SCHEMA", "test");
        String v_user = System.getProperty("MYSQL_USERNAME", "root");
        String v_pass = System.getProperty("MYSQL_PASSWORD", "");
        
        MySQLHandler mySQLHandler = new MySQLHandler(v_host, v_schm, v_user, v_pass);
        
        ResultSet authors = mySQLHandler.executeQuery("select * from Authors");
        MySQLHandler.dumpRS(authors);
       
        ResultSet books = mySQLHandler.executeQuery("select * from Books");
        MySQLHandler.dumpRS(books);
       
        ResultSet joined = mySQLHandler.executeQuery("select B.Id, B.Title, 'by', A.Name from Books B join Authors A on A.Id = B.AuthorId");
        MySQLHandler.dumpRS(joined);
       
        
        
        
        mySQLHandler.close();
    }
    
 
    
}
