/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clinicmanagement;

import static clinicmanagement.Home.scaleImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultCaret;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ASUS
 */
public class ReportMedicineUsage extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    private boolean User = false;
    static private String CMND = "";

    public ReportMedicineUsage() {
        initComponents();
        jPanel1.setVisible(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setBackground(Color.white);
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(table));

    }

    public ReportMedicineUsage(String CMND) {
        initComponents();
        jPanel1.setVisible(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setBackground(Color.white);
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(table));
        this.CMND = CMND;
        RetriveData();
        ThangItemStateChanged(null);
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
                ResultSet rs = stm.executeQuery("SELECT TenNhanVien, HinhAnh, NHANVIEN.MaNhanVien, CHUCNANG.MaChucNang, TenChucNang FROM NHANVIEN, CHUCNANG, PHANQUYEN "
                        + "WHERE CMND = '" + CMND + "' AND PHANQUYEN.MaNhanVien = NHANVIEN.MaNhanVien AND CHUCNANG.MaChucNang = PHANQUYEN.MaChucNang");
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
                        ImageIcon icon = new ImageIcon(masked);
                        Anhdaidien.setIcon(icon);
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

    public int LoadData() throws SQLException {
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();

        ResultSet rs = stm.executeQuery("SELECT TenThuoc ,TenDonViTinh ,SoLuongDung ,SoLanDung "
                + "                      FROM THUOC, BAOCAOSUDUNGTHUOC  "
                + "                      WHERE THUOC.MaThuoc  = BAOCAOSUDUNGTHUOC.MaThuoc AND Thang = " + Thang.getSelectedItem().toString() + " AND NAM = " + Nam.getSelectedItem().toString());
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        while (rs.next()) {
            Vector row = new Vector();
            row.add(model.getRowCount() + 1);
            row.add(rs.getString("TenThuoc"));
            row.add(rs.getString("TenDonViTinh"));
            row.add(String.valueOf(rs.getInt("SoLuongDung")));
            row.add(String.valueOf(rs.getInt("SoLanDung")));
            model.getRowCount();
            model.addRow(row);
        }
        rs.close();
        stm.close();
        conn.close();
        return 1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        placeholderTextField2 = new customview.PlaceholderTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Anhdaidien = new javax.swing.JLabel();
        Tentaikhoan = new javax.swing.JLabel();
        Nutmuiten = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        placeholderTextField1 = new customview.PlaceholderTextField();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        inbaocao = new javax.swing.JButton();
        QuayLai = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Thang = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        Nam = new javax.swing.JComboBox<>();

        placeholderTextField2.setText("placeholderTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setForeground(java.awt.Color.white);
        setMinimumSize(new java.awt.Dimension(822, 618));
        setResizable(false);
        setSize(new java.awt.Dimension(990, 800));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(209, 242, 225));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277027184_555937372561581_5654092174016176725_n.png"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 27, -1, -1));

        Anhdaidien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/image 6.png"))); // NOI18N
        jPanel2.add(Anhdaidien, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 12, -1, -1));

        Tentaikhoan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentaikhoan.setForeground(new java.awt.Color(0, 84, 42));
        Tentaikhoan.setText("Lê Phi Long\n");
        jPanel2.add(Tentaikhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, -1, -1));

        Nutmuiten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Screenshot 2022-04-26 103146.png"))); // NOI18N
        Nutmuiten.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Nutmuiten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NutmuitenMouseClicked(evt);
            }
        });
        jPanel2.add(Nutmuiten, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 30, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277690175_419076512892132_8107806418371628641_n.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 80));

        placeholderTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        placeholderTextField1.setPlaceholder("Tìm kiếm...");
        placeholderTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                placeholderTextField1KeyPressed(evt);
            }
        });
        jPanel2.add(placeholderTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 22, 410, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 1000, 80));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278996697_723755712095971_8418662915417084857_n.png"))); // NOI18N

        jButton4.setBackground(new java.awt.Color(0, 166, 84));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Đổi mật khẩu");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

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

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278367228_415742123260148_2336724036194180860_n.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277367234_720289712438638_7547041272784298626_n.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel2))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 80, 280, 180));

        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên thuốc", "Đơn vị tính", "Số lượng dùng", "Số lần dùng"
            }
        ));
        table.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(30);
            table.getColumnModel().getColumn(1).setPreferredWidth(200);
            table.getColumnModel().getColumn(2).setPreferredWidth(200);
            table.getColumnModel().getColumn(3).setPreferredWidth(200);
            table.getColumnModel().getColumn(4).setPreferredWidth(200);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 940, 380));

        inbaocao.setBackground(new java.awt.Color(255, 204, 204));
        inbaocao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        inbaocao.setForeground(new java.awt.Color(0, 99, 28));
        inbaocao.setText("In báo cáo");
        inbaocao.setToolTipText("");
        inbaocao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        inbaocao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inbaocaoMouseClicked(evt);
            }
        });
        inbaocao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inbaocaoActionPerformed(evt);
            }
        });
        getContentPane().add(inbaocao, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 520, 200, 40));

        QuayLai.setBackground(new java.awt.Color(255, 204, 204));
        QuayLai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        QuayLai.setForeground(new java.awt.Color(0, 99, 28));
        QuayLai.setText("Quay lại");
        QuayLai.setToolTipText("");
        QuayLai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        QuayLai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                QuayLaiMouseClicked(evt);
            }
        });
        QuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuayLaiActionPerformed(evt);
            }
        });
        getContentPane().add(QuayLai, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 520, 160, 40));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(1, 84, 43));
        jLabel5.setText("Tháng:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, -1, -1));

        Thang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Thang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6" }));
        Thang.setSelectedIndex(4);
        Thang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ThangItemStateChanged(evt);
            }
        });
        getContentPane().add(Thang, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(1, 84, 43));
        jLabel6.setText("Năm:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, -1, -1));

        Nam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Nam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2022" }));
        Nam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NamItemStateChanged(evt);
            }
        });
        getContentPane().add(Nam, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NutmuitenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NutmuitenMouseClicked
        if (User == false) {
            jPanel1.setVisible(true);
            User = true;
        } else {
            jPanel1.setVisible(false);
            User = false;
        }
    }//GEN-LAST:event_NutmuitenMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

    }//GEN-LAST:event_jButton1MouseClicked

    private void inbaocaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inbaocaoMouseClicked
        try{
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh Sách Báo Cáo");
            
            Cell cell;
            XSSFRow row;
            int rownum = 0;
            row = sheet.createRow(0);
            
            cell =  row.createCell(0, CellType.STRING);
            cell.setCellValue("DANH SÁCH BÁO CÁO SỬ DỤNG THUỐC ");
            
            row = sheet.createRow(1);
            
            cell =  row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");

            // 
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Tên Thuốc");
            
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Đơn vị tính");
            
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Số lượng dùng");
            
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Số lần dùng");
            
            
            DefaultTableModel tbModel = (DefaultTableModel) table.getModel();
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
                
            }
            String ten = Thang.getSelectedItem().toString() + "-" + Nam.getSelectedItem().toString();
            System.out.println("Created file: " + ten);
            String save = "D:/"+"DANHSACHBAOCAOTHUOC-" + ten + ".xlsx";
            File file = new File(save);  
            file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            System.out.println("Created file: " + file.getAbsolutePath());
            
            Desktop.getDesktop().open(file);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_inbaocaoMouseClicked

    private void inbaocaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inbaocaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inbaocaoActionPerformed

    private void QuayLaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_QuayLaiMouseClicked
        MedicineUsageManagement frame = new MedicineUsageManagement();
        this.dispose();
        frame.setVisible(true);
    }//GEN-LAST:event_QuayLaiMouseClicked

    private void QuayLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuayLaiActionPerformed
        MedicineUsageManagement frame = new MedicineUsageManagement(CMND);
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_QuayLaiActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ClinicManagement form = new ClinicManagement();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed
    private void LapBaoCao() throws SQLException {
        DatabaseConnection dtc = new DatabaseConnection();
        Connection conn = dtc.getConnection(this);
        Statement statement = conn.createStatement();
        Statement stm = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT THUOC.MaThuoc, SUM(SoLuongDung), COUNT(*) FROM THUOC, CT_PHIEUKHAMBENH, PHIEUKHAMBENH WHERE THUOC.MaThuoc = CT_PHIEUKHAMBENH.MaThuoc AND CT_PHIEUKHAMBENH.MaPhieuKhamBenh = PHIEUKHAMBENH.MaPhieuKhamBenh AND MONTH(NgayKham) = " + Thang.getSelectedItem().toString() + " AND YEAR(NgayKham) = " + Nam.getSelectedItem().toString() + " GROUP BY THUOC.MaThuoc");
        while (rs.next()) {
            stm.executeUpdate("INSERT INTO BAOCAOSUDUNGTHUOC VALUES (" + Thang.getSelectedItem().toString() + "," + Nam.getSelectedItem().toString() + ",'" + rs.getString(1) + "'," + rs.getInt(2) + "," + rs.getInt(3) + ")");
        }
        rs.close();
        statement.close();
        conn.close();
    }
    private void ThangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ThangItemStateChanged
        try {
            DatabaseConnection dtc = new DatabaseConnection();
            Connection conn = dtc.getConnection(this);
            Statement statement = conn.createStatement();
            boolean flag = false;
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM BAOCAOSUDUNGTHUOC WHERE Thang = " + Thang.getSelectedItem().toString() + " AND NAM = " + Nam.getSelectedItem().toString());
            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    flag = false;
                } else {
                    flag = true;
                }
            }
            if (flag) {
                LoadData();
            } else {
                LapBaoCao();
                LoadData();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }//GEN-LAST:event_ThangItemStateChanged

    private void NamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NamItemStateChanged
        try {
            if (LoadData() == 0) {
                JOptionPane.showMessageDialog(this, "Thời gian này chưa có báo cáo, vui lòng chọn thời gian khác", "Chưa có thông tin", ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
    }//GEN-LAST:event_NamItemStateChanged

    private void placeholderTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placeholderTextField1KeyPressed
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(placeholderTextField1.getText()));
    }//GEN-LAST:event_placeholderTextField1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UserInformation dialog = new UserInformation(new javax.swing.JFrame(), true, CMND);
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                for (WindowListener wl : dialog.getWindowListeners()) {
                    dialog.removeWindowListener(wl);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        dialog.dispose();
                        ReportMedicineUsage frame = new ReportMedicineUsage(CMND);
                        frame.setVisible(true);
                    }
                });
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if ("admin".equals(CMND)) {
            JOptionPane.showMessageDialog(this, "admin không thể đổi mật khẩu", "Lỗi", ERROR_MESSAGE);
        } else {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    ChangePassword dialog = new ChangePassword(new javax.swing.JFrame(), true, CMND);
                    dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    for (WindowListener wl : dialog.getWindowListeners()) {
                        dialog.removeWindowListener(wl);
                    }
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            dialog.dispose();
                            ReportMedicineUsage frame = new ReportMedicineUsage(CMND);
                            frame.setVisible(true);
                        }
                    });
                    dialog.setVisible(true);
                }
            });
            this.dispose();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReportMedicineUsage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReportMedicineUsage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReportMedicineUsage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReportMedicineUsage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReportMedicineUsage().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anhdaidien;
    private javax.swing.JComboBox<String> Nam;
    private javax.swing.JLabel Nutmuiten;
    private javax.swing.JButton QuayLai;
    private javax.swing.JLabel Tentaikhoan;
    private javax.swing.JComboBox<String> Thang;
    private javax.swing.JButton inbaocao;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private customview.PlaceholderTextField placeholderTextField1;
    private customview.PlaceholderTextField placeholderTextField2;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
