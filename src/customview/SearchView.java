package customview;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author ngoctienTNT
 */
public class SearchView extends JTextField{
    private final Icon icon;
    
    public SearchView() {
        setBackground(Color.WHITE);
        setOpaque(false);
        setBorder(new EmptyBorder(10, 40, 10, 10));
        setSelectionColor(new Color(80,199,255));
        icon = new ImageIcon(getClass().getResource("/assets/search.png"));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setBackground(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); 
        super.paintComponent(g); 
        
         // Create Button
        int marginButton = 5;
        int buttonSize = height - marginButton * 2;
        GradientPaint gra = new GradientPaint (0, 0, Color.WHITE, width, 0, new Color(3,175,225));
        g2.setPaint (gra);
        g2.fillOval(buttonSize/4, height/2 - buttonSize/2, buttonSize, buttonSize);
        
        // Create Button Icon
        int marginimage = 5;
        int imageSize = buttonSize - marginimage * 2;
        Image image = ((ImageIcon) icon).getImage ();
        g2.drawImage(image, buttonSize/3, height/2 - buttonSize/2+5, imageSize, imageSize, null);
        g2.dispose();        
    } 
}
