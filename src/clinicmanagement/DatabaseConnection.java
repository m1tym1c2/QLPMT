package clinicmanagement;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://NGOCTIENTNT:1433;databaseName=QUANLYPHONGMACHTU";
    private static final String user = "sa";
    private static final String password = "12345678";

    public Connection getConnection(Component c) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, user, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(c, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        } finally {
            return conn;
        }
    }
}
