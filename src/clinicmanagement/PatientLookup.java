package clinicmanagement;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ngoctienTNT
 */
public class PatientLookup extends javax.swing.JFrame {
    private static String CMND = "";
    
    public PatientLookup(){
        initComponents();  
        getContentPane().setBackground(Color.white);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        try
        {
            DocDuLieu();
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }
    
    public PatientLookup(String CMND){
        initComponents();  
        getContentPane().setBackground(Color.white);
        this.CMND = CMND;
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

        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        placeholderTextField1 = new customview.PlaceholderTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277027184_555937372561581_5654092174016176725_n.png"))); // NOI18N
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, -1, -1));

        placeholderTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        placeholderTextField1.setPlaceholder("Tìm kiếm...");
        placeholderTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeholderTextField1ActionPerformed(evt);
            }
        });
        jPanel3.add(placeholderTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 410, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(1, 84, 43));
        jLabel2.setText("DANH SÁCH BỆNH NHÂN");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, -1, -1));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã bệnh nhân", "Họ tên", "Ngày khám", "Loại bệnh", "Triệu chứng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(Table);
        if (Table.getColumnModel().getColumnCount() > 0) {
            Table.getColumnModel().getColumn(0).setPreferredWidth(50);
            Table.getColumnModel().getColumn(1).setPreferredWidth(125);
            Table.getColumnModel().getColumn(2).setPreferredWidth(250);
            Table.getColumnModel().getColumn(4).setPreferredWidth(125);
            Table.getColumnModel().getColumn(5).setPreferredWidth(200);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 950, 460));

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 99, 28));
        jButton1.setText("In danh sách");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 630, 160, 40));

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 99, 28));
        jButton2.setText("Quay lại");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 630, 160, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
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
    private void placeholderTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeholderTextField1ActionPerformed
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel> (model);
        Table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(placeholderTextField1.getText()));
    }//GEN-LAST:event_placeholderTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        DanhSachKhamBenh dialog = new DanhSachKhamBenh(CMND);
        dialog.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        try{
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh Sách Bệnh Nhân");
            
            Cell cell;
            XSSFRow row;
            int rownum = 0;
            row = sheet.createRow(0);
            
            cell = row.createCell(2, CellType.NUMERIC);
            cell.setCellValue("DANH SÁCH BỆNH NHÂN");
            
            row = sheet.createRow(1);
            cell =  row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");  
            // 
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã bệnh nhân");
            
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Họ tên");
            
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Ngày khám");
            
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Loại bệnh");
            
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Triệu chứng");
            
            
            DefaultTableModel tbModel = (DefaultTableModel) Table.getModel();
            for ( int i = 0 ; i < tbModel.getRowCount();  i++ ) {                
                row = sheet.createRow(i+2);
                // EmpNo (A)
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(tbModel.getValueAt(i, 0).toString());
                // EmpName (B)
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(tbModel.getValueAt(i, 1).toString());
                // Salary (C)
                cell = row.createCell(2, CellType.NUMERIC);
                cell.setCellValue(tbModel.getValueAt(i, 2).toString());
                // Grade (D)
                cell = row.createCell(3, CellType.NUMERIC);
                cell.setCellValue(tbModel.getValueAt(i, 3).toString());
                // Bonus (E)
                cell = row.createCell(4, CellType.NUMERIC);
                cell.setCellValue(tbModel.getValueAt(i, 4).toString());
                
                cell = row.createCell(5, CellType.NUMERIC);
                cell.setCellValue(tbModel.getValueAt(i, 4).toString());
                
            }
            
            String save = "D:/DANHSACHBENHNHAN.xlsx";
            File file = new File(save);  
            file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            System.out.println("Created file: " + file.getAbsolutePath());
            
            Desktop.getDesktop().open(file);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1MouseClicked
    
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
    private javax.swing.JTable Table;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private customview.PlaceholderTextField placeholderTextField1;
    // End of variables declaration//GEN-END:variables
}
