package customview;

import java.awt.Color;
import javax.swing.JTable;

/**
 *
 * @author ngoctienTNT
 */
public class MyTable extends JTable{

    public MyTable() {
        getTableHeader().setOpaque(false);
        getTableHeader().setBackground(new Color(32, 136,203));
        getTableHeader().setForeground(new Color(255,255,255));
        setRowHeight(25);
    }
    
}
