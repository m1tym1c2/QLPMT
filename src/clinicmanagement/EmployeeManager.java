package clinicmanagement;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author ngoctienTNT
 */
public class EmployeeManager extends javax.swing.JFrame {
    public EmployeeManager() {
        initComponents();     
        getContentPane().setBackground(Color.white);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

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
        buttonSalaryEmployee = new customview.MyButton();
        buttonBack = new customview.MyButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDark1 = new customview.MyTable();

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
        jLabel.setForeground(new java.awt.Color(1, 84, 43));
        jLabel.setText("QUẢN LÝ NHÂN VIÊN");
        jPanel1.add(jLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 0, 261, 49));

        searchView.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(searchView, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 370, -1));

        avatar.setText("Avatar");
        jPanel1.add(avatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 0, 50, 50));

        jLabel1.setBackground(new java.awt.Color(1, 63, 65));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(1, 84, 43));
        jLabel1.setText("Trần Ngọc Tiến");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(886, 20, 120, 28));

        ImageIcon imageIconArrow = new ImageIcon(getClass().getResource("/assets/down-arrow.png"));
        Image imageArrow = imageIconArrow.getImage();
        Image newimgArrow = imageArrow.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        imageIconArrow = new ImageIcon(newimgArrow);
        buttonOption.setIcon(imageIconArrow);
        jPanel1.add(buttonOption, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 10, 40, 40));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel2.setBackground(new java.awt.Color(1, 84, 43));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(1, 84, 43));
        jLabel2.setText("DANH SÁNH NHÂN VIÊN");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.weightx = 3.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jLabel2, gridBagConstraints);

        buttonAddEmployee.setText("Thêm nhân viên");
        buttonAddEmployee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        buttonAddEmployee.setRadius(15);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(buttonAddEmployee, gridBagConstraints);

        buttonSalaryEmployee.setText("Lập bảng tính lương nhân viên");
        buttonSalaryEmployee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        buttonSalaryEmployee.setRadius(15);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(buttonSalaryEmployee, gridBagConstraints);

        buttonBack.setText("Quay lại");
        buttonBack.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        buttonBack.setRadius(15);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(buttonBack, gridBagConstraints);

        tableDark1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã nhân viên", "Tên nhân viên", "CMND", "Năm sinh", "Chức vụ", "Email", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableDark1.setShowGrid(false);
        tableDark1.setShowHorizontalLines(true);
        tableDark1.setShowVerticalLines(true);
        jScrollPane2.setViewportView(tableDark1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 3.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 30);
        jPanel3.add(jScrollPane2, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JLabel avatar;
    private customview.MyButton buttonAddEmployee;
    private customview.MyButton buttonBack;
    private customview.MyButton buttonOption;
    private customview.MyButton buttonSalaryEmployee;
    private javax.swing.JLabel icon;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private customview.SearchView searchView;
    private customview.MyTable tableDark1;
    // End of variables declaration//GEN-END:variables
}
