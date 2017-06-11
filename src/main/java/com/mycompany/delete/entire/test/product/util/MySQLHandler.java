/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.delete.entire.test.product.util;

import com.mycompany.delete.entire.test.product.DeleteEntireTestProduct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cthies
 */
public class MySQLHandler {

    private final String host;
    private final String schm;
    private final String user;
    private final String pass;

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public MySQLHandler(String v_host, String v_schm, String v_user, String v_pass) {
        this.host = v_host;
        this.schm = v_schm;
        this.user = v_user;
        this.pass = v_pass;
        init();
    }

    private void init() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            //Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://" + this.host + "/" + this.schm + "?"
                            + "user=" + this.user + "&password=" + this.pass);

            resultSet = executeQuery("select VERSION()");
            writeMetaData(resultSet);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("com.mycompany.delete.entire.test.product.util.MySQLHandler.init()" + e.getMessage()
            );
            //} catch (ClassNotFoundException ex) {
            //    Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //close();
        }
    }

    public ResultSet executeQuery(String simpleQuery) {
        ResultSet rs = null;
        try {
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            rs = statement.executeQuery(simpleQuery);
            resultSet = rs;
        } catch (SQLException ex) {
            Logger.getLogger(MySQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public static void dumpRS(ResultSet rs) {
        try {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                System.out.print(" " + rs.getMetaData().getColumnName(i));
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(" " + rs.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeleteEntireTestProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void writeMetaData(ResultSet rs) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + rs.getMetaData().getTableName(1));
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            System.out.println("Column " + i + " " + rs.getMetaData().getColumnName(i));
        }
    }

    // You need to close the resultSet
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            System.out.println("com.mycompany.delete.entire.test.product.util.MySQLHandler.init()" + e.getMessage()
            );
        }
    }
}
