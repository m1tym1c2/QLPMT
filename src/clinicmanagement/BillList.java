package clinicmanagement;

import static clinicmanagement.Home.scaleImage;
import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
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
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.joda.time.DateTime;

/**
 *
 * @author ngoctienTNT
 */
public class BillList extends javax.swing.JFrame {

    private Connection connection = null;
    private static boolean User = false;
    Locale localeVI = new Locale("vi", "VI");
    NumberFormat vi = NumberFormat.getInstance(localeVI);
    private static String CMND = "";

    public BillList() {
        initComponents();
        jButton3.setEnabled(false);
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
                statement.execute("SET DATEFORMAT DMY");
                ResultSet rs = statement.executeQuery("SELECT * FROM HOADON, PHIEUKHAMBENH, BENHNHAN "
                        + "WHERE HOADON.MaPhieuKhamBenh = PHIEUKHAMBENH.MaPhieuKhamBenh "
                        + "AND BENHNHAN.MaBenhNhan = PHIEUKHAMBENH.MaBenhNhan "
                        + "AND PHIEUKHAMBENH.NgayKham >= '" + strDate1 + "' AND PHIEUKHAMBENH.NgayKham <= '" + strDate2 + "'");

                DefaultTableModel model = (DefaultTableModel) tableDark1.getModel();
                model.setRowCount(0);
                int i = 0;
                while (rs.next()) {
                    i++;
                    SimpleDateFormat simp = new SimpleDateFormat("dd/MM/yyyy");
                    String data[] = {Integer.toString(i), rs.getString("MaHoaDon"), rs.getString("TenBenhNhan"),
                        simp.format(rs.getDate("NgayKham")), vi.format(rs.getInt("TienKham")), vi.format(rs.getInt("TienThuoc")),
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
    public BillList(String CMND) {
        initComponents();
        jButton3.setEnabled(false);
        Calendar ca = new GregorianCalendar();
        String day = ca.get(Calendar.DAY_OF_MONTH) + "";
        String month = ca.get(Calendar.MONTH) + 1 + "";
        String year = ca.get(Calendar.YEAR) + "";
        this.CMND = CMND;
        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1) {
            month = "0" + month;
        }
        JTableHeader header = tableDark1.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(tableDark1));
        jPanel1.setVisible(false);
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
        RetriveData();
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
                statement.execute("SET DATEFORMAT DMY");
                ResultSet rs = statement.executeQuery("SELECT * FROM HOADON, PHIEUKHAMBENH, BENHNHAN "
                        + "WHERE HOADON.MaPhieuKhamBenh = PHIEUKHAMBENH.MaPhieuKhamBenh "
                        + "AND BENHNHAN.MaBenhNhan = PHIEUKHAMBENH.MaBenhNhan "
                        + "AND PHIEUKHAMBENH.NgayKham >= '" + strDate + "' AND PHIEUKHAMBENH.NgayKham <= '" + strDate2 + "'");

                DefaultTableModel model = (DefaultTableModel) tableDark1.getModel();
                model.setRowCount(0);
                int i = 0;
                while (rs.next()) {
                    i++;
                    SimpleDateFormat simp = new SimpleDateFormat("dd/MM/yyyy");
                    String data[] = {Integer.toString(i), rs.getString("MaHoaDon"), rs.getString("TenBenhNhan"),
                        simp.format(rs.getDate("NgayKham")), vi.format(rs.getInt("TienKham")), vi.format(rs.getInt("TienThuoc")),
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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        ThongTinCaNhan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        DoiMatKhau = new javax.swing.JButton();
        DangXuat = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDark1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        Anhdaidien = new javax.swing.JLabel();
        Tentaikhoan = new javax.swing.JLabel();
        Nutmuiten = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        placeholderTextField1 = new customview.PlaceholderTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ThongTinCaNhan.setBackground(new java.awt.Color(0, 166, 84));
        ThongTinCaNhan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ThongTinCaNhan.setForeground(new java.awt.Color(255, 255, 255));
        ThongTinCaNhan.setText("Thông tin cá nhân");
        ThongTinCaNhan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ThongTinCaNhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ThongTinCaNhanMouseClicked(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278996697_723755712095971_8418662915417084857_n.png"))); // NOI18N

        DoiMatKhau.setBackground(new java.awt.Color(0, 166, 84));
        DoiMatKhau.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        DoiMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        DoiMatKhau.setText("Đổi mật khẩu");
        DoiMatKhau.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoiMatKhauActionPerformed(evt);
            }
        });

        DangXuat.setBackground(new java.awt.Color(242, 111, 51));
        DangXuat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        DangXuat.setForeground(new java.awt.Color(255, 255, 255));
        DangXuat.setText("Đăng xuất");
        DangXuat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DangXuatActionPerformed(evt);
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
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(DoiMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ThongTinCaNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(DangXuat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5))
                    .addComponent(ThongTinCaNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 10, 280, 180));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(1, 84, 43));
        jLabel3.setText("Từ ngày:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(1, 84, 43));
        jLabel4.setText("Đến ngày:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(1, 84, 43));
        jLabel2.setText("DANH SÁCH HÓA ĐƠN");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, -1));

        jDateChooser1.setDateFormatString("dd/MM/yyyy");
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });
        jPanel3.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 260, -1));

        jDateChooser2.setDateFormatString("dd/MM/yyyy");
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });
        jPanel3.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, 280, -1));

        tableDark1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã hóa đơn", "Tên khách hàng", "Ngày khám", "Tiền khám", "Tiền thuốc", "Tổng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableDark1);
        if (tableDark1.getColumnModel().getColumnCount() > 0) {
            tableDark1.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableDark1.getColumnModel().getColumn(1).setPreferredWidth(125);
            tableDark1.getColumnModel().getColumn(2).setPreferredWidth(250);
            tableDark1.getColumnModel().getColumn(3).setPreferredWidth(150);
            tableDark1.getColumnModel().getColumn(4).setPreferredWidth(150);
            tableDark1.getColumnModel().getColumn(5).setPreferredWidth(200);
            tableDark1.getColumnModel().getColumn(6).setPreferredWidth(200);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 1010, -1));

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 99, 28));
        jButton1.setText("In danh sách hóa đơn");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 580, 220, 40));

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 99, 28));
        jButton2.setText("Lập bảng báo cáo tháng");
        jButton2.setToolTipText("");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 580, 260, 40));

        jButton3.setBackground(new java.awt.Color(255, 204, 204));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 99, 28));
        jButton3.setText("Chi tiết hóa đơn");
        jButton3.setToolTipText("");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 580, 190, 40));

        jButton4.setBackground(new java.awt.Color(255, 204, 204));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 99, 28));
        jButton4.setText("Trở lại");
        jButton4.setToolTipText("");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 580, 130, 40));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 70, 1110, 650));

        jPanel2.setBackground(new java.awt.Color(209, 242, 225));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Anhdaidien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/image 6.png"))); // NOI18N
        jPanel2.add(Anhdaidien, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 0, -1, -1));

        Tentaikhoan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentaikhoan.setForeground(new java.awt.Color(0, 84, 42));
        Tentaikhoan.setText("Lê Phi Long\n");
        jPanel2.add(Tentaikhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 20, -1, -1));

        Nutmuiten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Screenshot 2022-04-26 103146.png"))); // NOI18N
        Nutmuiten.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Nutmuiten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NutmuitenMouseClicked(evt);
            }
        });
        jPanel2.add(Nutmuiten, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 20, -1, -1));

        jLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel.setForeground(new java.awt.Color(0, 84, 42));
        jLabel.setText("QUẢN LÝ DOANH THU");
        jPanel2.add(jLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 261, 49));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/Untitled-4 2.png"))); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277027184_555937372561581_5654092174016176725_n.png"))); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 30, -1, 30));

        placeholderTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        placeholderTextField1.setPlaceholder("Tìm kiếm...");
        placeholderTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeholderTextField1ActionPerformed(evt);
            }
        });
        jPanel2.add(placeholderTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 346, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 80));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        changeTableData();
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        changeTableData();
    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void NutmuitenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NutmuitenMouseClicked
        if (User == false) {
            jPanel1.setVisible(true);
            User = true;
        } else {
            jPanel1.setVisible(false);
            User = false;
        }
    }//GEN-LAST:event_NutmuitenMouseClicked

    private void ThongTinCaNhanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThongTinCaNhanMouseClicked
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
                        Home frame = new Home(CMND);
                        frame.setVisible(true);
                    }
                });
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_ThongTinCaNhanMouseClicked

    private void DoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoiMatKhauActionPerformed
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
                        BillList frame = new BillList(CMND);
                        frame.setVisible(true);
                    }
                });
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_DoiMatKhauActionPerformed

    private void DangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DangXuatActionPerformed
        try {
            Writer output = new BufferedWriter(new FileWriter("temp.log", false));
            ClinicManagement form;
            form = new ClinicManagement();
            form.setVisible(true);
            this.dispose();
        } catch (Exception e) {

        }
    }//GEN-LAST:event_DangXuatActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            var capture = new Robot().createScreenCapture(screenRect);
            DateTime time = DateTime.now();
            ImageIO.write(capture, "png", new File("D:\\" + time.toString("yyyy_MM_dd_HH_mm_ss") + ".png"));
            } catch (AWTException | IOException ex) {
                Logger.getLogger(BillList.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        BaoCaoDoanhThuThang dialog = new BaoCaoDoanhThuThang(CMND);
        dialog.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DefaultTableModel model = (DefaultTableModel) tableDark1.getModel();
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            connection = databaseConnection.getConnection(jLabel);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM HOADON WHERE HOADON.MaHoaDon = '" + model.getValueAt(tableDark1.getSelectedRow(), 1).toString() + "'");
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(this, rootPaneCheckingEnabled, CMND);
            rs.next();
            chiTietHoaDon.setMaPhieuKhamBenh(rs.getString("MaPhieuKhamBenh"));
            chiTietHoaDon.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(BillList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home(CMND).setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void placeholderTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeholderTextField1ActionPerformed
        DefaultTableModel model = (DefaultTableModel) tableDark1.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        tableDark1.setRowSorter(sorter);
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
            java.util.logging.Logger.getLogger(BillList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new BillList().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anhdaidien;
    private javax.swing.JButton DangXuat;
    private javax.swing.JButton DoiMatKhau;
    private javax.swing.JLabel Nutmuiten;
    private javax.swing.JLabel Tentaikhoan;
    private javax.swing.JButton ThongTinCaNhan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private customview.PlaceholderTextField placeholderTextField1;
    private javax.swing.JTable tableDark1;
    // End of variables declaration//GEN-END:variables
}
