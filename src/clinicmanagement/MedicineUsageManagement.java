/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clinicmanagement;

import static clinicmanagement.AddMedicine.donvi;
import static clinicmanagement.AddMedicine.mathuoc;
import static clinicmanagement.AddMedicine.soluong;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author ASUS
 */
public class MedicineUsageManagement extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    private boolean User = false;
    private double tyle = 1;
    private static String CMND = "";
    
    public MedicineUsageManagement() {
        initComponents();
        jPanel1.setVisible(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setBackground(Color.white);
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(table));
        try
        {
            LoadData();           
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
    }
    
    public MedicineUsageManagement( String CMND) {
        initComponents();
        jPanel1.setVisible(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setBackground(Color.white);
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        JTableHeader header = table.getTableHeader();
        this.CMND = CMND;
        header.setDefaultRenderer(new HeaderRenderer(table));
        try
        {
            LoadData();           
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
    }
     public void LoadData()throws SQLException{
        
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();        
        
        ResultSet rs = stm.executeQuery("SELECT Distinct CT_PHIEUNHAPTHUOC.MaPhieuNhapThuoc,CT_PHIEUNHAPTHUOC.MaThuoc,TenThuoc ,NgayNhap\n" +
                                        ",SoLuongTon,TenDonViTinh ,DonGiaNhap ,DonGiaBan \n" +
                                        "FROM THUOC, CT_PHIEUNHAPTHUOC , PHIEUNHAPTHUOC , (SELECT   max(ct1.MaPhieuNhapThuoc) MaPhieuNhapThuoc\n" +
                                        "                                                   FROM CT_PHIEUNHAPTHUOC ct1													\n" +
                                        "                                                   GROUP BY MaThuoc) CTNHAPTHUOC \n" +
                                        "WHERE THUOC.MaThuoc  = CT_PHIEUNHAPTHUOC.MaThuoc  \n" +
                                        "AND CT_PHIEUNHAPTHUOC.MaPhieuNhapThuoc = PHIEUNHAPTHUOC.MaPhieuNhapThuoc\n" +
                                        "AND CTNHAPTHUOC.MaPhieuNhapThuoc = CT_PHIEUNHAPTHUOC.MaPhieuNhapThuoc");
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        while (rs.next())
        {
            Vector row = new Vector();          
            row.add(model.getRowCount()+1);
            row.add(rs.getString("MaThuoc"));
            row.add(rs.getString("TenThuoc"));
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");        
            String strDate = formatter.format(rs.getDate("NgayNhap"));
            row.add(strDate);
            
            row.add(String.valueOf(rs.getInt("SoLuongTon")));
            row.add(rs.getString("TenDonViTinh"));
            row.add(String.valueOf(rs.getInt("DonGiaNhap")));
            row.add(String.valueOf(rs.getInt("DonGiaBan")));
            model.getRowCount();
            model.addRow(row);            
        }
        rs.close(); 
        stm.close();
        conn.close();
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
        jLabel4 = new javax.swing.JLabel();
        BaoCaoSD = new javax.swing.JButton();
        TyGia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Luu = new javax.swing.JButton();
        ThemThuoc = new javax.swing.JButton();
        QuayLai = new javax.swing.JButton();
        ThemLoaiThuocMoi = new javax.swing.JButton();
        Themdv = new javax.swing.JButton();
        themcd = new javax.swing.JButton();

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

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278996697_723755712095971_8418662915417084857_n.png"))); // NOI18N

        jButton4.setBackground(new java.awt.Color(0, 166, 84));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Đổi mật khẩu");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã thuốc", "Tên thuốc", "Ngày nhập", "Lượng tồn", "ĐVT", "Giá nhập", "Giá bán", ""
            }
        ));
        table.setEditingColumn(0);
        table.setEditingRow(0);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(30);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(150);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(50);
            table.getColumnModel().getColumn(5).setPreferredWidth(50);
            table.getColumnModel().getColumn(6).setPreferredWidth(100);
            table.getColumnModel().getColumn(7).setPreferredWidth(100);
            table.getColumnModel().getColumn(8).setPreferredWidth(20);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 940, 380));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(1, 84, 43));
        jLabel4.setText("%");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, -1, -1));

        BaoCaoSD.setBackground(new java.awt.Color(255, 204, 204));
        BaoCaoSD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        BaoCaoSD.setForeground(new java.awt.Color(0, 99, 28));
        BaoCaoSD.setText("Báo cáo sử dụng thuốc");
        BaoCaoSD.setToolTipText("");
        BaoCaoSD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BaoCaoSD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BaoCaoSDMouseClicked(evt);
            }
        });
        getContentPane().add(BaoCaoSD, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 80, 220, 40));

        TyGia.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TyGia.setText("110");
        TyGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TyGiaActionPerformed(evt);
            }
        });
        getContentPane().add(TyGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 85, 50, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(1, 84, 43));
        jLabel5.setText("Tỷ giá Nhập - Bán: ");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        Luu.setBackground(new java.awt.Color(255, 204, 204));
        Luu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Luu.setForeground(new java.awt.Color(0, 99, 28));
        Luu.setText("Lưu");
        Luu.setToolTipText("");
        Luu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Luu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LuuMouseClicked(evt);
            }
        });
        Luu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LuuActionPerformed(evt);
            }
        });
        getContentPane().add(Luu, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 70, 40));

        ThemThuoc.setBackground(new java.awt.Color(255, 204, 204));
        ThemThuoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ThemThuoc.setForeground(new java.awt.Color(0, 99, 28));
        ThemThuoc.setText("Thêm loại thuốc mới");
        ThemThuoc.setToolTipText("");
        ThemThuoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ThemThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ThemThuocMouseClicked(evt);
            }
        });
        getContentPane().add(ThemThuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 200, 40));

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
        getContentPane().add(QuayLai, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 520, 160, 40));

        ThemLoaiThuocMoi.setBackground(new java.awt.Color(255, 204, 204));
        ThemLoaiThuocMoi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ThemLoaiThuocMoi.setForeground(new java.awt.Color(0, 99, 28));
        ThemLoaiThuocMoi.setText("Thêm thuốc đã có");
        ThemLoaiThuocMoi.setToolTipText("");
        ThemLoaiThuocMoi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ThemLoaiThuocMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ThemLoaiThuocMoiMouseClicked(evt);
            }
        });
        getContentPane().add(ThemLoaiThuocMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 520, 200, 40));

        Themdv.setBackground(new java.awt.Color(255, 204, 204));
        Themdv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Themdv.setForeground(new java.awt.Color(0, 99, 28));
        Themdv.setText("Thêm đơn vị");
        Themdv.setToolTipText("");
        Themdv.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Themdv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ThemdvMouseClicked(evt);
            }
        });
        Themdv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemdvActionPerformed(evt);
            }
        });
        getContentPane().add(Themdv, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 520, 200, 40));

        themcd.setBackground(new java.awt.Color(255, 204, 204));
        themcd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        themcd.setForeground(new java.awt.Color(0, 99, 28));
        themcd.setText("Thêm cách dùng\n");
        themcd.setToolTipText("");
        themcd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        themcd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                themcdMouseClicked(evt);
            }
        });
        themcd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themcdActionPerformed(evt);
            }
        });
        getContentPane().add(themcd, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 520, 200, 40));

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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UserInformation dialog = new UserInformation(new javax.swing.JFrame(), true);
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                for (WindowListener wl : dialog.getWindowListeners()) {
                    dialog.removeWindowListener(wl);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        dialog.dispose();
                        MedicineUsageManagement frame = new MedicineUsageManagement();
                        frame.setVisible(true);
                    }
                });
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void BaoCaoSDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BaoCaoSDMouseClicked
        
        ReportMedicineUsage form = new ReportMedicineUsage();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BaoCaoSDMouseClicked

    private void TyGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TyGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TyGiaActionPerformed
    public void LUU()throws SQLException{
        tyle = Integer.valueOf(TyGia.getText());
        
        DatabaseConnection DTBC = new DatabaseConnection();
        Connection conn = DTBC.getConnection(this);
        Statement stm = conn.createStatement();  
        
        stm.executeUpdate("UPDATE CT_PHIEUNHAPTHUOC set DonGiaBan = DonGiaNhap*" + tyle/100 );
         
        stm.close();
        conn.close();
        TyGia.setText(String.valueOf(tyle));
    }
    private void LuuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LuuMouseClicked
        JOptionPane.showMessageDialog(this, "Bạn có chắc đổi tỷ lệ giá bán hay không?", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        try
        {
            LUU();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
        try
        {
            LoadData();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
    }//GEN-LAST:event_LuuMouseClicked

    private void ThemThuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThemThuocMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddNewMedicine().setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_ThemThuocMouseClicked

    private void QuayLaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_QuayLaiMouseClicked
        Home frame = new Home();
        this.dispose();
        frame.setVisible(true);
    }//GEN-LAST:event_QuayLaiMouseClicked

    private void LuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LuuActionPerformed
        tyle = Integer.valueOf(TyGia.getText());
    }//GEN-LAST:event_LuuActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ClinicManagement form = new ClinicManagement();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void ThemLoaiThuocMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThemLoaiThuocMoiMouseClicked
        tyle = Integer.valueOf(TyGia.getText());
        AddMedicine.setData(tyle);
        java.awt.EventQueue.invokeLater(() -> {
            AddMedicine dialog = new AddMedicine(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
        this.dispose();
    }//GEN-LAST:event_ThemLoaiThuocMoiMouseClicked

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        int row = table.getSelectedRow();
        String mathuoc = table.getModel().getValueAt(row, 1).toString();
        ManagementDrugUse.SetData(mathuoc);
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new ManagementDrugUse().setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(MedicineUsageManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.dispose();
    }//GEN-LAST:event_tableMouseClicked

    private void ThemdvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThemdvMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DanhSachDonViTinh().setVisible(true);
            }
        });
    }//GEN-LAST:event_ThemdvMouseClicked

    private void themcdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_themcdMouseClicked
        
    }//GEN-LAST:event_themcdMouseClicked

    private void themcdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themcdActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DanhSachCachDung(CMND).setVisible(true);
            }
        });
    }//GEN-LAST:event_themcdActionPerformed

    private void ThemdvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemdvActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DanhSachDonViTinh(CMND).setVisible(true);
            }
        });
    }//GEN-LAST:event_ThemdvActionPerformed

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
            java.util.logging.Logger.getLogger(MedicineUsageManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MedicineUsageManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MedicineUsageManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MedicineUsageManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new MedicineUsageManagement().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anhdaidien;
    private javax.swing.JButton BaoCaoSD;
    private javax.swing.JButton Luu;
    private javax.swing.JLabel Nutmuiten;
    private javax.swing.JButton QuayLai;
    private javax.swing.JLabel Tentaikhoan;
    private javax.swing.JButton ThemLoaiThuocMoi;
    private javax.swing.JButton ThemThuoc;
    private javax.swing.JButton Themdv;
    private javax.swing.JTextField TyGia;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private customview.PlaceholderTextField placeholderTextField1;
    private customview.PlaceholderTextField placeholderTextField2;
    private javax.swing.JTable table;
    private javax.swing.JButton themcd;
    // End of variables declaration//GEN-END:variables
}
