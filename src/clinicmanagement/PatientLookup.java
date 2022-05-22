package clinicmanagement;

import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ngoctienTNT
 */
public class PatientLookup extends javax.swing.JFrame {
    public PatientLookup(){
        initComponents();  
        getContentPane().setBackground(Color.white);
        try
        {
            DocDuLieu();
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
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
        jLabel2 = new javax.swing.JLabel();
        buttonAddEmployee = new customview.MyButton();
        buttonBack = new customview.MyButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new customview.MyTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(229, 229, 229));
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
        jLabel.setText("TRA CỨU BỆNH NHÂN");
        jPanel1.add(jLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 0, 273, 49));

        searchView.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchViewActionPerformed(evt);
            }
        });
        searchView.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchViewKeyPressed(evt);
            }
        });
        jPanel1.add(searchView, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 310, -1));

        avatar.setText("Avatar");
        jPanel1.add(avatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 50, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Trần Ngọc Tiến");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(836, 10, 120, 28));

        ImageIcon imageIconArrow = new ImageIcon(getClass().getResource("/assets/down-arrow.png"));
        Image imageArrow = imageIconArrow.getImage();
        Image newimgArrow = imageArrow.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        imageIconArrow = new ImageIcon(newimgArrow);
        buttonOption.setIcon(imageIconArrow);
        jPanel1.add(buttonOption, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 10, 30, 30));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(1, 84, 43));
        jLabel2.setText("DANH SÁCH BỆNH NHÂN");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(287, 0, -1, -1));

        buttonAddEmployee.setText("In danh sách");
        buttonAddEmployee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        buttonAddEmployee.setRadius(15);
        jPanel3.add(buttonAddEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(209, 580, 200, 30));

        buttonBack.setText("Quay lại");
        buttonBack.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        buttonBack.setRadius(15);
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBackActionPerformed(evt);
            }
        });
        jPanel3.add(buttonBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(733, 580, 130, 30));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã bệnh nhân", "Họ tên", "Ngày khám", "Loại bệnh", "Triệu chứng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Table);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 57, 956, 514));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void DocDuLieu() throws SQLException
    {
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT BENHNHAN.MaBenhNhan, TenBenhNhan, NgayKham, TenLoaiBenh, TrieuChung FROM BENHNHAN, PHIEUKHAMBENH, LOAIBENH WHERE BENHNHAN.MaBenhNhan = PHIEUKHAMBENH.MaBenhNhan AND PHIEUKHAMBENH.MaLoaiBenh = LOAIBENH.MaLoaiBenh");
        int STT = 0;
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        while (rs.next())
        {
            STT++;
            Vector vector = new Vector();
            vector.add(STT);
            vector.add(rs.getString("MaBenhNhan"));
            vector.add(rs.getString("TenBenhNhan"));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(rs.getDate("NgayKham"));
            vector.add(date);
            vector.add(rs.getString("TenLoaiBenh"));
            vector.add(rs.getString("TrieuChung"));
            model.addRow(vector);
        }
    }
    private void searchViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchViewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchViewActionPerformed

    private void searchViewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchViewKeyPressed
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel> (model);
        Table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(searchView.getText()));
    }//GEN-LAST:event_searchViewKeyPressed

    private void buttonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBackActionPerformed
        DanhSachKhamBenh dialog = new DanhSachKhamBenh();
        dialog.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_buttonBackActionPerformed
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PatientLookup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new PatientLookup().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private customview.MyTable Table;
    private javax.swing.JLabel avatar;
    private customview.MyButton buttonAddEmployee;
    private customview.MyButton buttonBack;
    private customview.MyButton buttonOption;
    private javax.swing.JLabel icon;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private customview.SearchView searchView;
    // End of variables declaration//GEN-END:variables
}
