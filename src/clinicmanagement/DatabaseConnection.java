/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinicmanagement;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.table.DefaultTableModel;

public class DatabaseConnection {
    private static String URL = "jdbc:sqlserver://PHILONG:1433;databaseName=QUANLYCANHTHI";
    private static String user = "philong";
    private static String password = "12345678";
    public Connection getConnection(Component c) throws SQLException
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(URL, user, password);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(c, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
        finally
        {
            return conn;
        }
    }
}
