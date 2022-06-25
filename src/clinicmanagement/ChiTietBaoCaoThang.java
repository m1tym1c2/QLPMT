package clinicmanagement;

import static clinicmanagement.Home.scaleImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JPanel;
import org.joda.time.DateTime;

/**
 *
 * @author Dell
 */
public class ChiTietBaoCaoThang extends javax.swing.JFrame {

    private boolean User = false;
    private Connection connection = null;
    Locale localeVI = new Locale("vi", "VI");
    private static String CMND = "";
    NumberFormat vi = NumberFormat.getInstance(localeVI);

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
        thang.setText(month);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
        nam.setText(year);
    }

    private String month;
    private String year;

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            connection = databaseConnection.getConnection(this);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM HOADON, PHIEUKHAMBENH "
                    + "WHERE HOADON.MaPhieuKhamBenh = PHIEUKHAMBENH.MaPhieuKhamBenh AND MONTH(NgayKham) = "
                    + month + " AND YEAR(NgayKham) = " + year);
            int sumHoaDon = 0;
            while (rs.next()) {
                sumHoaDon += rs.getInt("GiaTriHoaDon");
            }
            jLabel4.setText(vi.format(sumHoaDon) + " VND");

            rs = statement.executeQuery("SELECT * FROM NHANVIEN");
            int sumLuong = 0;
            while (rs.next()) {
                sumLuong += (int) rs.getInt("LuongCB") * rs.getFloat("HeSo") + rs.getInt("TienThuong");
            }
            jLabel9.setText(vi.format(sumLuong) + " VND");

            rs = statement.executeQuery("SELECT * FROM PHIEUNHAPTHUOC WHERE MONTH(NgayNhap) = "
                    + month + " AND YEAR(NgayNhap) = " + year);
            int sumThuoc = 0;
            while (rs.next()) {
                sumThuoc += rs.getInt("GiaTriPhieuNhap");
            }

            JFreeChart pieChart = createPieChart(createDataset(sumLuong, sumThuoc, true));
            chartPanel = new ChartPanel(pieChart);
            chartPanel.setBounds(0, 0, 340, 330);
            chartPanel.setBackground(Color.white);
            jPanel3.add(chartPanel);

            rs = statement.executeQuery("SELECT * FROM PHIEUKHAMBENH "
                    + "WHERE  MONTH(NgayKham) = " + month + " AND YEAR(NgayKham) = " + year);

            int sumTienKham = 0;
            int sumTienThuoc = 0;

            while (rs.next()) {
                sumTienKham += rs.getInt("TienKham");
                sumTienThuoc += rs.getInt("TienThuoc");
            }

            JFreeChart pieChart2 = createPieChart(createDataset(sumTienKham, sumTienThuoc, false));
            chartPanel = new ChartPanel(pieChart2);
            chartPanel.setBounds(0, 0, 340, 330); //set size PieChart
            jPanel5.add(chartPanel);

            jLabel10.setText(vi.format(sumThuoc) + " VND");
            jLabel11.setText(vi.format(sumHoaDon - sumLuong - sumThuoc) + " VND");
            jLabel12.setText("Tổng thu: " + vi.format(sumHoaDon) + " VND");
            jLabel13.setText("Tổng chi: " + vi.format(sumLuong + sumThuoc) + "0 VND");
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietBaoCaoThang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ChiTietBaoCaoThang() {
        initComponents();
        jPanel4.setVisible(false);
    }

    public ChiTietBaoCaoThang(String CMND) {
        initComponents();
        jPanel4.setVisible(false);
        this.CMND = CMND;
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

    private static PieDataset createDataset(int nhanVien, int thuoc, boolean check) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        if (check) {
            dataset.setValue("Nhân Viên", nhanVien);
        } else {
            dataset.setValue("Tiền Khám", nhanVien);
        }
        dataset.setValue("Thuốc", thuoc);
        return dataset;
    }

    private static JFreeChart createPieChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart("", dataset, true, true, false);

        return chart;
    }

    public BufferedImage createImage(JPanel panel) {

        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.paint(g);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        Anhdautrang = new javax.swing.JLabel();
        Tentaikhoan = new javax.swing.JLabel();
        Anhdaidien = new javax.swing.JLabel();
        Nutmuiten = new javax.swing.JLabel();
        Tentrang = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        Tentrang1 = new javax.swing.JLabel();
        Thang = new javax.swing.JLabel();
        Nam = new javax.swing.JLabel();
        thang = new javax.swing.JLabel();
        nam = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1220, 653));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setBackground(new java.awt.Color(0, 166, 84));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Thông tin cá nhân");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 11, -1, 49));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278996697_723755712095971_8418662915417084857_n.png"))); // NOI18N
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 14, -1, -1));

        jButton4.setBackground(new java.awt.Color(0, 166, 84));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Đổi mật khẩu");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 69, 201, 46));

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
        jPanel4.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 121, 200, 46));
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 65, -1, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278367228_415742123260148_2336724036194180860_n.png"))); // NOI18N
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 121, 54, 46));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277367234_720289712438638_7547041272784298626_n.png"))); // NOI18N
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 65, -1, 50));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 90, 280, 180));

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 99, 28));
        jButton1.setText("In báo cáo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 600, 175, 36));

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 99, 28));
        jButton2.setText("Quay lại");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 600, 174, 36));

        jPanel2.setBackground(new java.awt.Color(208, 242, 224));

        Anhdautrang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Untitled-4 2.png"))); // NOI18N
        Anhdautrang.setText("jLabel5");

        Tentaikhoan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentaikhoan.setForeground(new java.awt.Color(0, 84, 42));
        Tentaikhoan.setText("Lê Phi Long\n");

        Anhdaidien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/image 6.png"))); // NOI18N

        Nutmuiten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Screenshot 2022-04-26 103146.png"))); // NOI18N
        Nutmuiten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NutmuitenMouseClicked(evt);
            }
        });

        Tentrang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentrang.setForeground(new java.awt.Color(0, 84, 42));
        Tentrang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tentrang.setText("<html>BÁO CÁO DOANH THU<br>THEO THÁNG</html>");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(Anhdautrang, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tentrang, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 580, Short.MAX_VALUE)
                .addComponent(Anhdaidien, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Tentaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Nutmuiten, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tentrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Anhdautrang, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Anhdaidien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Nutmuiten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Tentaikhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1240, 70));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Tentrang1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Tentrang1.setForeground(new java.awt.Color(0, 84, 42));
        Tentrang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tentrang1.setText("CHI TIẾT DOANH THU THÁNG\n");
        jPanel6.add(Tentrang1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 530, 50));

        Thang.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Thang.setForeground(new java.awt.Color(0, 84, 42));
        Thang.setText("Tháng:");
        jPanel6.add(Thang, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, -1, 42));

        Nam.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Nam.setForeground(new java.awt.Color(0, 84, 42));
        Nam.setText("Năm:");
        jPanel6.add(Nam, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, -1, 42));

        thang.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        thang.setText("5");
        jPanel6.add(thang, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, 31, 42));

        nam.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        nam.setText("2022");
        jPanel6.add(nam, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 54, 42));

        jLabel2.setBackground(new java.awt.Color(102, 102, 102));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Nguồn chi");
        jPanel6.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Nguồn thu");
        jPanel6.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, -1, 20));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(340, 330));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 84, 42));
        jLabel13.setText("Tổng chi: 0 VND");
        jPanel6.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 460, -1, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 84, 42));
        jLabel12.setText("Tổng thu: 0 VND");
        jPanel6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 460, -1, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 84, 42));
        jLabel6.setText("Tổng lợi nhuận:");
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 380, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("0 VND");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 410, -1, 18));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("0 VND");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 340, -1, 18));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 84, 42));
        jLabel8.setText("Nhập thuốc:");
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 310, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 84, 42));
        jLabel7.setText("Lương nhân viên:");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 230, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("0 VND");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 260, -1, 18));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("0 VND");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 170, -1, 18));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 84, 42));
        jLabel5.setText("Nguồn chi:");
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 200, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 84, 42));
        jLabel3.setText("Nguồn thu từ các phiếu khám bệnh:");
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 140, -1, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 1210, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            new BaoCaoDoanhThuThang(CMND).setVisible(true);
        });
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
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
                    MedicineUsageManagement frame = new MedicineUsageManagement();
                    frame.setVisible(true);
                }
            });
            dialog.setVisible(true);
        });
        this.dispose();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ClinicManagement form = new ClinicManagement();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void NutmuitenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NutmuitenMouseClicked
        if (User == false) {
            jPanel4.setVisible(true);
            User = true;
        } else {
            jPanel4.setVisible(false);
            User = false;
        }
    }//GEN-LAST:event_NutmuitenMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Desktop desktop = Desktop.getDesktop();  
            DateTime time = DateTime.now();
            File outputfile = new File("D:\\BC_"+ time.toString("yyyy_MM_dd_HH_mm_ss") +".png");
            ImageIO.write(createImage(jPanel6), "png", outputfile);
            JOptionPane.showMessageDialog(this, "Đã lưu ở "+outputfile.getAbsolutePath());
            if (outputfile.exists()) //checks file exists or not  
            {
                desktop.open(outputfile);              //opens the specified file  
            }
        } catch (Exception ex) {
            Logger.getLogger(BillList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if ("admin".equals(CMND)) {
            JOptionPane.showMessageDialog(this, "Không thể xem thông tin admin", "Lỗi", ERROR_MESSAGE);
        } else {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    ChiTietBaoCaoThang dialog = new ChiTietBaoCaoThang(CMND);
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
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(ChiTietBaoCaoThang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChiTietBaoCaoThang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChiTietBaoCaoThang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChiTietBaoCaoThang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ChiTietBaoCaoThang().setVisible(true);
        });
    }
    private org.jfree.chart.ChartPanel chartPanel;
    //private org.jfree.chart.ChartPanel chartPanel2;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anhdaidien;
    private javax.swing.JLabel Anhdautrang;
    private javax.swing.JLabel Nam;
    private javax.swing.JLabel Nutmuiten;
    private javax.swing.JLabel Tentaikhoan;
    private javax.swing.JLabel Tentrang;
    private javax.swing.JLabel Tentrang1;
    private javax.swing.JLabel Thang;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel nam;
    private javax.swing.JLabel thang;
    // End of variables declaration//GEN-END:variables
}
