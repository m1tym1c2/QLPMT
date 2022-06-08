/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package clinicmanagement;

import static clinicmanagement.Home.scaleImage;
import static clinicmanagement.UserInformation.scaleImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author ngoctienTNT
 */
public class StaffInformation extends javax.swing.JDialog {

    /**
     * Creates new form StaffInformation
     *
     * @param parent
     * @param modal
     */
    private static String CMND = "";
    private static String MANV = "";
    private static boolean flag = false;

    public StaffInformation(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        getContentPane().setBackground(Color.white);
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
        } catch (ParseException ex) {
            Logger.getLogger(StaffInformation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public StaffInformation(java.awt.Frame parent, boolean modal, String MANV, String CMND) {
        super(parent, modal);
        initComponents();
        getContentPane().setBackground(Color.white);
        Calendar ca = new GregorianCalendar();
        String day = ca.get(Calendar.DAY_OF_MONTH) + "";
        String month = ca.get(Calendar.MONTH) + 1 + "";
        String year = ca.get(Calendar.YEAR) + "";
        this.CMND = CMND;
        this.MANV = MANV;
        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1) {
            month = "0" + month;
        }

        String dd = year + "-" + month + "-" + day;
        String ddd = "2022-01-01";
        String dddd = "2002-12-31";
        String ddddd = "2002-01-01";

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(ddd);            
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(dddd);
            Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse(ddddd);
            jDateChooser1.setDate(date1);
            jDateChooser2.setDate(date);
            FNgaySinh.setDate(date3);
            jDateChooser1.setMaxSelectableDate(date);
            jDateChooser2.setMaxSelectableDate(date);
            FNgaySinh.setMaxSelectableDate(date2);
        } catch (ParseException ex) {
            Logger.getLogger(StaffInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(table));
        RetriveData();
        InitData();
        flag = true;
    }

    private void RetriveData() {
        try {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            SimpleDateFormat simpDate = new SimpleDateFormat("dd/MM/yyyy");
            ResultSet rs = stm.executeQuery("SELECT * FROM CHUCNANG WHERE MaChucNang <> '000'");
            while (rs.next()) {
                cbb.addItem(rs.getString("TenChucNang"));
            }
            rs = stm.executeQuery("SELECT * FROM NHANVIEN, CHUCNANG, PHANQUYEN WHERE NhanVien.MaNhanVien = '" + MANV + "' AND CHUCNANG.MaChucNang = PHANQUYEN.MaChucNang"
                    + " AND PHANQUYEN.MaNhanVien = NHANVIEN.MaNhanVien");
            if (rs.next()) {

                FTen.setText(rs.getString("TenNhanVien"));
                MaNV.setText(rs.getString("MaNhanVien"));
                FCMND.setText(rs.getString("CMND"));
                FNgaySinh.setDate(rs.getDate("NgaySinh"));
                FDiaChi.setText(rs.getString("DiaChi"));
                cbb.setSelectedItem(rs.getString("TenChucNang"));
                FEmail.setText(rs.getString("Email"));
                FLuong.setText(rs.getString("LuongCB"));
                try {
                    URL url = getClass().getResource(rs.getString("HinhAnh"));
                    File file = new File(url.getPath());
                    BufferedImage master = ImageIO.read(file);
                    try {
                        master = scaleImage(310, 320, master);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(master);
                    Anhdaidien.setIcon(icon);
                } catch (InvalidPathException | NullPointerException | IOException ex) {
                    ImageIcon iconnull = new ImageIcon(getClass().getResource("/anh/NotSetAvt.png"));
                    Anhdaidien.setIcon(iconnull);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
    }

    private void InitData() {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.setRowCount(0);
        try {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd");

            ResultSet rs = stm.executeQuery("SELECT * FROM HOADON, BENHNHAN, phieukhambenh Where benhnhan.Mabenhnhan = phieukhambenh.mabenhnhan AND hoadon.maphieukhambenh = phieukhambenh.maphieukhambenh AND hoadon.manhanvien = '" + MANV + "'"
                    + " AND phieukhambenh.ngaykham >= '" + si.format(jDateChooser1.getDate()) + "' AND phieukhambenh.ngaykham <= '" + si.format(jDateChooser2.getDate()) + "'");
            SimpleDateFormat simpDate = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                Vector row = new Vector();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                row.add(rs.getString("MaHoaDon"));
                row.add(rs.getString("TenBenhnhan"));
                Date date = rs.getDate("NgayKham");
                row.add(simpDate.format(date));
                row.add(rs.getString("GiaTriHoaDon"));
                model.getRowCount();
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
    }

    public static BufferedImage scaleImage(int w, int h, BufferedImage img) throws Exception {
        BufferedImage bi;
        bi = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(img, 0, 0, w, h, null);
        g2d.dispose();
        return bi;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        FTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        MaNV = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        FCMND = new javax.swing.JTextField();
        FEmail = new javax.swing.JTextField();
        FLuong = new javax.swing.JTextField();
        cbb = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Anhdaidien = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        FDiaChi = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        FNgaySinh = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thong tin nhan vien");
        setPreferredSize(new java.awt.Dimension(740, 770));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 166, 84));
        jLabel2.setText("Họ và tên:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 166, 84));
        jLabel3.setText("CMND:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, -1, -1));

        FTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        FTen.setText("Trần Ngọc Tiến");
        FTen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(FTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 226, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(1, 84, 43));
        jLabel4.setText("THÔNG TIN NHÂN VIÊN");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 7, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 166, 84));
        jLabel5.setText("Ngày sinh:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 166, 84));
        jLabel6.setText("Địa chỉ:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 200, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 166, 84));
        jLabel7.setText("Chức vụ:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 166, 84));
        jLabel8.setText("Email:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 350, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 166, 84));
        jLabel9.setText("Mã NV:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 400, -1, -1));

        MaNV.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        MaNV.setForeground(new java.awt.Color(0, 51, 153));
        MaNV.setText("PMT001");
        getContentPane().add(MaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 166, 84));
        jLabel11.setText("Lương CB:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 390, -1, -1));

        FCMND.setEditable(false);
        FCMND.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        FCMND.setText("1234567");
        FCMND.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(FCMND, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, 226, -1));

        FEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        FEmail.setText("ngoctien@gmail.com");
        FEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(FEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 350, 226, -1));

        FLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        FLuong.setText("2000$");
        FLuong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(FLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 390, 226, -1));

        cbb.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbb.setBorder(null);
        cbb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbActionPerformed(evt);
            }
        });
        getContentPane().add(cbb, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 226, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(1, 84, 43));
        jLabel12.setText("CÁC HÓA ĐƠN ĐÃ LẬP");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 430, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(1, 84, 43));
        jLabel13.setText("Từ ngày");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 490, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(1, 84, 43));
        jLabel14.setText("Đến ngày:");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 490, -1, -1));

        Anhdaidien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/demo.jpg"))); // NOI18N
        getContentPane().add(Anhdaidien, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 310, 320));

        jDateChooser1.setDateFormatString("dd-MM-yyyy");
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 490, 180, -1));

        jDateChooser2.setDateFormatString("dd-MM-yyyy");
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });
        getContentPane().add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 490, 180, -1));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Tên khách hàng", "Ngày khám", "Giá trị"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 520, 660, 160));

        FDiaChi.setColumns(20);
        FDiaChi.setLineWrap(true);
        FDiaChi.setRows(5);
        FDiaChi.setWrapStyleWord(true);
        jScrollPane2.setViewportView(FDiaChi);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, 230, -1));

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 99, 28));
        jButton1.setText("Xóa nhân viên");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 690, -1, -1));

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 99, 28));
        jButton2.setText("Cập nhật thông tin");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 690, -1, -1));

        FNgaySinh.setDateFormatString("dd-MM-yyyy");
        FNgaySinh.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                FNgaySinhPropertyChange(evt);
            }
        });
        getContentPane().add(FNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 160, 180, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        if (jDateChooser2.getDate() != null) {
            InitData();

        }
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        if (jDateChooser2.getDate() != null) {
            InitData();
            try {
                jDateChooser1.setMaxSelectableDate(jDateChooser2.getDate());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không thể trước ngày kết thúc", "Lỗi", ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            SimpleDateFormat si = new SimpleDateFormat("YYYY-MM-dd HH:mm:SS");
            stm.execute("Update nhanvien set TenNhanVien = N'" + FTen.getText() + "' where manhanvien = '" + MANV + "'");
            stm.execute("Update nhanvien set LuongCB = N'" + FLuong.getText() + "' where manhanvien = '" + MANV + "'");
            stm.execute("Update nhanvien set Email = N'" + FEmail.getText() + "' where manhanvien = '" + MANV + "'");
            stm.execute("Update nhanvien set DiaChi = N'" + FDiaChi.getText() + "' where manhanvien = '" + MANV + "'");
            stm.execute("Update nhanvien set NgaySinh = '" + si.format(FNgaySinh.getDate()) + "' where manhanvien = '" + MANV + "'");
            String MaChucNang = "";
            switch (cbb.getSelectedIndex()) {
                case 0:
                    MaChucNang = "001";
                    break;
                case 1:
                    MaChucNang = "002";
                    break;
                case 2:
                    MaChucNang = "003";
                    break;
                case 3:
                    MaChucNang = "004";
                    break;
                default:
                    break;
            }
            stm.execute("Update phanquyen set machucnang = '" + MaChucNang + "' where manhanvien = '" + MANV + "'");

            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void FNgaySinhPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_FNgaySinhPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_FNgaySinhPropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("Select manhanvien from nhanvien where cmnd = '" + CMND + "'");
            String MANVCM = "";
            if (rs.next()) {
                MANVCM = rs.getString("manhanvien");
            }
            if (MANVCM == null ? MANV == null : MANVCM.equals(MANV)) {
                JOptionPane.showMessageDialog(this, "Bạn không thể xóa bản thân", "Lỗi", ERROR_MESSAGE);
            } else {
                int reply = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa tài khoản này không", "Cảnh báo", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "Lệnh đã không được thực hiện");
                } else {
                    stm.execute("delete from phanquyen where manhanvien = '" + MANV + "'");
                    stm.execute("delete from phieukhambenh where manhanvien = '" + MANV + "'");
                    stm.execute("delete from nhanvien where manhanvien = '" + MANV + "'");
                    JOptionPane.showMessageDialog(this, "Đã xóa tài khoản thành công");
                    this.dispose();
                    EmployeeManager em = new EmployeeManager(CMND);
                    em.setVisible(true);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbActionPerformed
        if (FNgaySinh.getDate() == null || flag == false) {

        } else {
            try {
                DatabaseConnection DTBC = new DatabaseConnection();
                Connection conn = DTBC.getConnection(this);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("Select nhanvien.manhanvien, TenChucnang from nhanvien, phanquyen, chucnang where cmnd = '" + CMND + "' and nhanvien.manhanvien = phanquyen.manhanvien and chucnang.machucnang = phanquyen.machucnang");
                String MANVCM = "";
                if (rs.next()) {
                    MANVCM = rs.getString("manhanvien");
                }
                if (MANVCM == null ? MANV == null : MANVCM.equals(MANV)) {
                    JOptionPane.showMessageDialog(this, "Bạn không thể thay đổi chức năng của bản thân", "Lỗi", ERROR_MESSAGE);
                    cbb.setSelectedItem(rs.getString("TenChucNang"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_cbbActionPerformed

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
            java.util.logging.Logger.getLogger(StaffInformation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StaffInformation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StaffInformation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StaffInformation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            StaffInformation dialog = new StaffInformation(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anhdaidien;
    private javax.swing.JTextField FCMND;
    private javax.swing.JTextArea FDiaChi;
    private javax.swing.JTextField FEmail;
    private javax.swing.JTextField FLuong;
    private com.toedter.calendar.JDateChooser FNgaySinh;
    private javax.swing.JTextField FTen;
    private javax.swing.JLabel MaNV;
    private javax.swing.JComboBox<String> cbb;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
