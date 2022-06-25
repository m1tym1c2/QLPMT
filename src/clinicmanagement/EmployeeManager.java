package clinicmanagement;

import static clinicmanagement.Home.scaleImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ngoctienTNT
 */
public class EmployeeManager extends javax.swing.JFrame {

    private static String CMND = "";
    private boolean User = false;
    private String MaNV = "";

    public EmployeeManager() {
        initComponents();
        getContentPane().setBackground(Color.white);
    }

    public EmployeeManager(String CMND) {
        initComponents();
        this.CMND = CMND;
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(table));
        getContentPane().setBackground(Color.white);
        jPanel4.setVisible(false);
        RetriveData();
        InitData();
    }

    private void RetriveData() {
        if ("admin".equals(CMND)) {
            ImageIcon iconnull = new ImageIcon(getClass().getResource("/anh/NotSetAvt.png"));
            Anhdaidien.setIcon(iconnull);
            Tentaikhoan.setText("admin");
        } else {
            try {
                DatabaseConnection DTBC = new DatabaseConnection();
                Connection conn = DTBC.getConnection(this);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT TenNhanVien, HinhAnh FROM NHANVIEN "
                        + "WHERE CMND = '" + CMND + "'");
                if (rs.next()) {
                    Tentaikhoan.setText(rs.getString("TenNhanVien"));
                    try {

                        URL url = getClass().getResource(rs.getString("HinhAnh"));
                        File file = new File(url.getPath());
                        BufferedImage master = ImageIO.read(file);
                        try {
                            master = scaleImage(64, 64, master);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        int diameter = Math.min(master.getWidth(), master.getHeight());
                        BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);

                        Graphics2D g2d = mask.createGraphics();
                        g2d.fillOval(0, 0, diameter - 1, diameter - 1);
                        g2d.dispose();

                        BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
                        g2d = masked.createGraphics();
                        int x = (diameter - master.getWidth()) / 2;
                        int y = (diameter - master.getHeight()) / 2;
                        g2d.drawImage(master, x, y, null);
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
                        g2d.drawImage(mask, 0, 0, null);
                        g2d.dispose();
                        ImageIcon iconn = new ImageIcon(masked);
                        Anhdaidien.setIcon(iconn);
                    } catch (InvalidPathException | NullPointerException | IOException ex) {
                        ImageIcon iconnull = new ImageIcon(getClass().getResource("/anh/NotSetAvt.png"));
                        Anhdaidien.setIcon(iconnull);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Có lỗi xảy ra", "Đăng nhập thất bại", 2);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
            }
        }
    }

    private void InitData() {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.setRowCount(0);
        try {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT TenNhanVien, NHANVIEN.MaNhanVien, CHUCNANG.MaChucNang, TenChucNang, CMND, Email, NgaySinh FROM NHANVIEN, CHUCNANG, PHANQUYEN "
                        +" WHERE PHANQUYEN.MaNhanVien = NHANVIEN.MaNhanVien AND CHUCNANG.MaChucNang = PHANQUYEN.MaChucNang");
            SimpleDateFormat simpDate = new SimpleDateFormat("yyyy");
            while (rs.next()) {
                Vector row = new Vector();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                row.add(model.getRowCount() + 1);
                row.add(rs.getString("MaNhanVien"));
                row.add(rs.getString("TenNhanVien"));
                row.add(rs.getString("CMND"));
                Date date = rs.getDate("NgaySinh");
                row.add(simpDate.format(date));
                row.add(rs.getString("TenChucNang"));
                row.add(rs.getString("Email"));
                model.getRowCount();
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();
        Anhdaidien = new javax.swing.JLabel();
        Tentaikhoan = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        placeholderTextField1 = new customview.PlaceholderTextField();
        Nutmuiten = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(208, 242, 224));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/assets/icon.png"));
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        icon.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        icon.setIcon(imageIcon);
        icon.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 8, 50, 50));

        jLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel.setForeground(new java.awt.Color(1, 84, 43));
        jLabel.setText("QUẢN LÝ NHÂN VIÊN");
        jPanel1.add(jLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 261, 49));

        Anhdaidien.setText("Avatar");
        jPanel1.add(Anhdaidien, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 3, 64, 64));

        Tentaikhoan.setBackground(new java.awt.Color(1, 63, 65));
        Tentaikhoan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentaikhoan.setForeground(new java.awt.Color(1, 84, 43));
        Tentaikhoan.setText("Trần Ngọc Tiến");
        jPanel1.add(Tentaikhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 20, 190, 28));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277027184_555937372561581_5654092174016176725_n.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 15, -1, -1));

        placeholderTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        placeholderTextField1.setPlaceholder("Tìm kiếm...");
        placeholderTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeholderTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(placeholderTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 410, 40));

        Nutmuiten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Screenshot 2022-04-26 103146.png"))); // NOI18N
        Nutmuiten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NutmuitenMouseClicked(evt);
            }
        });
        jPanel1.add(Nutmuiten, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 20, 44, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1191, 70));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(1, 84, 43));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(1, 84, 43));
        jLabel2.setText("DANH SÁNH NHÂN VIÊN");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(0, 166, 84));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Thông tin cá nhân");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 11, 210, 49));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278996697_723755712095971_8418662915417084857_n.png"))); // NOI18N
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 14, -1, -1));

        jButton4.setBackground(new java.awt.Color(0, 166, 84));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Đổi mật khẩu");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 69, 210, 46));

        jButton5.setBackground(new java.awt.Color(242, 111, 51));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Đăng xuất");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 121, 210, 46));
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 65, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278367228_415742123260148_2336724036194180860_n.png"))); // NOI18N
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 121, 54, 46));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277367234_720289712438638_7547041272784298626_n.png"))); // NOI18N
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 65, -1, 50));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, 290, 170));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã nhân viên", "Tên nhân viên", "CMND", "Năm sinh", "Chức vụ", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(1).setPreferredWidth(75);
            table.getColumnModel().getColumn(2).setPreferredWidth(200);
            table.getColumnModel().getColumn(3).setPreferredWidth(75);
            table.getColumnModel().getColumn(4).setPreferredWidth(100);
            table.getColumnModel().getColumn(5).setPreferredWidth(200);
            table.getColumnModel().getColumn(6).setPreferredWidth(200);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 1100, 400));

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 99, 28));
        jButton2.setText("Thông tin nhân viên");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 490, 220, 40));

        jButton3.setBackground(new java.awt.Color(255, 204, 204));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 99, 28));
        jButton3.setText("Thêm nhân viên mới");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, 200, 40));

        jButton6.setBackground(new java.awt.Color(255, 204, 204));
        jButton6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 99, 28));
        jButton6.setText("Lập bảng tính lương nhân viên");
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 490, 280, 40));

        jButton7.setBackground(new java.awt.Color(255, 204, 204));
        jButton7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton7.setForeground(new java.awt.Color(0, 99, 28));
        jButton7.setText("Quay lại");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 490, 170, 40));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1191, 558));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        java.awt.EventQueue.invokeLater(() -> {
            UserInformation dialog = new UserInformation(new javax.swing.JFrame(), true);
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            for (WindowListener wl : dialog.getWindowListeners()) {
                dialog.removeWindowListener(wl);
            }
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    dialog.dispose();
                    EmployeeManager frame = new EmployeeManager(CMND);
                    frame.setVisible(true);
                }
            });
            dialog.setVisible(true);
        });
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            Writer output = new BufferedWriter(new FileWriter("temp.log", false));
            ClinicManagement form;
            form = new ClinicManagement();
            form.setVisible(true);
            this.dispose();
        } catch (Exception e) {

        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void NutmuitenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NutmuitenMouseClicked
        if (User == false) {
            jPanel4.setVisible(true);
            User = true;
        } else {
            jPanel4.setVisible(false);
            User = false;
        }      // TODO add your handling code here:
    }//GEN-LAST:event_NutmuitenMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Home home = new Home(CMND);
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        JTable source = (JTable) evt.getSource();
        int row = source.rowAtPoint(evt.getPoint());
        MaNV = source.getModel().getValueAt(row, 1) + "";
    }//GEN-LAST:event_tableMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if ("".equals(MaNV)){
            JOptionPane.showMessageDialog(this, "Chưa chọn nhận viên để xem thông tin", "Lỗi", ERROR_MESSAGE);
        }
        else if ("PMT000".equals(MaNV)){
            JOptionPane.showMessageDialog(this, "Không thể xem thông tin admin", "Lỗi", ERROR_MESSAGE);
        }
        else {
            java.awt.EventQueue.invokeLater(() -> {
            StaffInformation dialog = new StaffInformation(new javax.swing.JFrame(), true, MaNV, CMND);
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            for (WindowListener wl : dialog.getWindowListeners()) {
                dialog.removeWindowListener(wl);
            }
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    dialog.dispose();
                    EmployeeManager frame = new EmployeeManager(CMND);
                    frame.setVisible(true);
                }
            });
            dialog.setVisible(true);
        });
        this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            AddStaff dialog = new AddStaff(new javax.swing.JFrame(), true, CMND);
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            for (WindowListener wl : dialog.getWindowListeners()) {
                dialog.removeWindowListener(wl);
            }
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    dialog.dispose();
                    EmployeeManager frame = new EmployeeManager(CMND);
                    frame.setVisible(true);
                }
            });
            dialog.setVisible(true);
        });
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        LuongNhanVien home = new LuongNhanVien(CMND);
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void placeholderTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeholderTextField1ActionPerformed
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel> (model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(placeholderTextField1.getText()));
    }//GEN-LAST:event_placeholderTextField1ActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new EmployeeManager().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anhdaidien;
    private javax.swing.JLabel Nutmuiten;
    private javax.swing.JLabel Tentaikhoan;
    private javax.swing.JLabel icon;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private customview.PlaceholderTextField placeholderTextField1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
