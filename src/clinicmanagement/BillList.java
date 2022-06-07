package clinicmanagement;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.joda.time.DateTime;

/**
 *
 * @author ngoctienTNT
 */
public class BillList extends javax.swing.JFrame {

    private Connection connection = null;
    Locale localeVI = new Locale("vi", "VI");
    NumberFormat vi = NumberFormat.getInstance(localeVI);

    public BillList() {
        initComponents();
        btnInvoiceDetails.setEnabled(false);
        Calendar ca = new GregorianCalendar();
        String day = ca.get(Calendar.DAY_OF_MONTH) + "";
        String month = ca.get(Calendar.MONTH) + 1 + "";
        String year = ca.get(Calendar.YEAR) + "";

        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1) {
            month = "0" + month;
        }

        String dd = year + "-" + month + "-" + day;

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
            jDateChooser1.setDate(date);
            jDateChooser2.setDate(date);
            jDateChooser1.setMaxSelectableDate(date);
            jDateChooser2.setMaxSelectableDate(date);
            try {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                connection = databaseConnection.getConnection(jLabel);
                Statement statement = connection.createStatement();
                var formatter = new SimpleDateFormat("dd/MM/yyyy");

                Date date1 = jDateChooser1.getDate();
                String strDate1 = formatter.format(date1);
                Date date2 = jDateChooser2.getDate();
                String strDate2 = formatter.format(date2);

                ResultSet rs = statement.executeQuery("SELECT * FROM HOADON, PHIEUKHAMBENH, BENHNHAN "
                        + "WHERE HOADON.MaPhieuKhamBenh = PHIEUKHAMBENH.MaPhieuKhamBenh "
                        + "AND BENHNHAN.MaBenhNhan = PHIEUKHAMBENH.MaBenhNhan "
                        + "AND PHIEUKHAMBENH.NgayKham >= '" + strDate1 + "' AND PHIEUKHAMBENH.NgayKham <= '" + strDate2 + "'");

                DefaultTableModel model = (DefaultTableModel) tableDark1.getModel();
                model.setRowCount(0);
                int i = 0;
                while (rs.next()) {
                    i++;
                    String data[] = {Integer.toString(i), rs.getString("MaHoaDon"), rs.getString("TenBenhNhan"),
                        rs.getString("NgayKham"), vi.format(rs.getInt("TienKham")), vi.format(rs.getInt("TienThuoc")),
                        vi.format(rs.getInt("TienKham") + rs.getInt("TienThuoc"))};
                    DefaultTableModel tbModel = (DefaultTableModel) tableDark1.getModel();
                    tbModel.addRow(data);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietBaoCaoThang.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ParseException ex) {
            Logger.getLogger(BillList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void changeTableData() {
        if (jDateChooser1.getDate() != null && jDateChooser2.getDate() != null) {
            Date date = jDateChooser1.getDate();
            var formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = formatter.format(date);
            Date date2 = jDateChooser2.getDate();
            String strDate2 = formatter.format(date2);

            try {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                connection = databaseConnection.getConnection(jLabel);
                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery("SELECT * FROM HOADON, PHIEUKHAMBENH, BENHNHAN "
                        + "WHERE HOADON.MaPhieuKhamBenh = PHIEUKHAMBENH.MaPhieuKhamBenh "
                        + "AND BENHNHAN.MaBenhNhan = PHIEUKHAMBENH.MaBenhNhan "
                        + "AND PHIEUKHAMBENH.NgayKham >= '" + strDate + "' AND PHIEUKHAMBENH.NgayKham <= '" + strDate2 + "'");

                DefaultTableModel model = (DefaultTableModel) tableDark1.getModel();
                model.setRowCount(0);
                int i = 0;
                while (rs.next()) {
                    i++;
                    String data[] = {Integer.toString(i), rs.getString("MaHoaDon"), rs.getString("TenBenhNhan"),
                        rs.getString("NgayKham"), vi.format(rs.getInt("TienKham")), vi.format(rs.getInt("TienThuoc")),
                        vi.format(rs.getInt("TienKham") + rs.getInt("TienThuoc"))};
                    DefaultTableModel tbModel = (DefaultTableModel) tableDark1.getModel();
                    tbModel.addRow(data);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietBaoCaoThang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();
        searchView = new customview.SearchView();
        avatar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        buttonOption = new customview.MyButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDark1 = new customview.MyTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        buttonAddEmployee = new customview.MyButton();
        btnInvoiceDetails = new customview.MyButton();
        buttonBack = new customview.MyButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        buttonSalaryEmployee = new customview.MyButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

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
        jLabel.setForeground(new java.awt.Color(0, 84, 42));
        jLabel.setText("QUẢN LÝ DOANH THU");
        jPanel1.add(jLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 0, 261, 49));

        searchView.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchView.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                searchViewInputMethodTextChanged(evt);
            }
        });
        jPanel1.add(searchView, new org.netbeans.lib.awtextra.AbsoluteConstraints(402, 10, 320, -1));

        avatar.setText("Avatar");
        jPanel1.add(avatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 50, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Trần Ngọc Tiến");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 20, 96, 28));

        ImageIcon imageIconArrow = new ImageIcon(getClass().getResource("/assets/down-arrow.png"));
        Image imageArrow = imageIconArrow.getImage();
        Image newimgArrow = imageArrow.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        imageIconArrow = new ImageIcon(newimgArrow);
        buttonOption.setIcon(imageIconArrow);
        jPanel1.add(buttonOption, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 10, 40, 40));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableDark1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Tên khách hàng", "Ngày khám", "Tiền khám", "Tiền thuốc", "Tổng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableDark1.setShowHorizontalLines(true);
        tableDark1.setShowVerticalLines(true);
        tableDark1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDark1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableDark1);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 134, 970, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(1, 84, 43));
        jLabel3.setText("Từ ngày:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(1, 84, 43));
        jLabel4.setText("Đến ngày:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 90, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(1, 84, 43));
        jLabel2.setText("DANH SÁCH HÓA ĐƠN");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, -1));

        buttonAddEmployee.setText("In danh sách hóa đơn");
        buttonAddEmployee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        buttonAddEmployee.setRadius(15);
        buttonAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddEmployeeActionPerformed(evt);
            }
        });
        jPanel3.add(buttonAddEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 590, -1, -1));

        btnInvoiceDetails.setText("Chi tiết hóa đơn");
        btnInvoiceDetails.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnInvoiceDetails.setRadius(15);
        btnInvoiceDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvoiceDetailsActionPerformed(evt);
            }
        });
        jPanel3.add(btnInvoiceDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 590, -1, -1));

        buttonBack.setText("Quay lại");
        buttonBack.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        buttonBack.setRadius(15);
        jPanel3.add(buttonBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 590, -1, -1));

        jDateChooser1.setDateFormatString("dd/MM/yyyy");
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });
        jPanel3.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 260, -1));

        jDateChooser2.setDateFormatString("dd/MM/yyyy");
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });
        jPanel3.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 90, 280, -1));

        buttonSalaryEmployee.setText("Lập bảng báo cáo tháng");
        buttonSalaryEmployee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        buttonSalaryEmployee.setRadius(15);
        buttonSalaryEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSalaryEmployeeActionPerformed(evt);
            }
        });
        jPanel3.add(buttonSalaryEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 590, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1032, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        changeTableData();
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        changeTableData();
    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void searchViewInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_searchViewInputMethodTextChanged
        DefaultTableModel model = (DefaultTableModel) tableDark1.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        tableDark1.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(searchView.getText()));
    }//GEN-LAST:event_searchViewInputMethodTextChanged

    private void tableDark1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDark1MouseClicked
        if (tableDark1.getSelectedRow() >= 0)
            btnInvoiceDetails.setEnabled(true);
        else
            btnInvoiceDetails.setEnabled(false);
    }//GEN-LAST:event_tableDark1MouseClicked

    private void buttonSalaryEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSalaryEmployeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonSalaryEmployeeActionPerformed

    private void btnInvoiceDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvoiceDetailsActionPerformed
        DefaultTableModel model = (DefaultTableModel) tableDark1.getModel();
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            connection = databaseConnection.getConnection(jLabel);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM HOADON WHERE HOADON.MaHoaDon = " + model.getValueAt(tableDark1.getSelectedRow(), 1).toString());
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(this, rootPaneCheckingEnabled);
            rs.next();
            chiTietHoaDon.setMaPhieuKhamBenh(rs.getString("MaPhieuKhamBenh"));
            chiTietHoaDon.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(BillList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInvoiceDetailsActionPerformed

    private void buttonAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddEmployeeActionPerformed
        try {
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            var capture = new Robot().createScreenCapture(screenRect);
            DateTime time = DateTime.now();
            ImageIO.write(capture, "png", new File("D:\\" + time.toString("yyyy_MM_dd_HH_mm_ss") + ".png"));
        } catch (AWTException | IOException ex) {
            Logger.getLogger(BillList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonAddEmployeeActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BillList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new BillList().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avatar;
    private customview.MyButton btnInvoiceDetails;
    private customview.MyButton buttonAddEmployee;
    private customview.MyButton buttonBack;
    private customview.MyButton buttonOption;
    private customview.MyButton buttonSalaryEmployee;
    private javax.swing.JLabel icon;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private customview.SearchView searchView;
    private customview.MyTable tableDark1;
    // End of variables declaration//GEN-END:variables
}
