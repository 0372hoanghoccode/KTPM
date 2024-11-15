package GUI.Component;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class PanelShadow extends JPanel {

    int shadowSize = 3;
    float shadowOpacity = 0.3f;
    JPanel iconBackground;
    JLabel lblIcon, lblTitle, lblContent;
    Color HowerBackgroundColor = new Color(187, 222, 251);

    Color MainColor = new Color(255, 255, 255);
    Color FontColor = new Color(0, 0, 0);
    Color BackgroundColor = new Color(0x55BEC0);
    Color HowerFontColor = new Color(255, 255, 255);

    public PanelShadow() {
        setOpaque(false);
    }

    public PanelShadow(String linkIcon, String title, String content) {
        this.setPreferredSize(new Dimension(1400, 300));
        this.setBackground(new Color(0x55BEC0));
        this.putClientProperty( FlatClientProperties.STYLE, "arc: 30" );
        this.setLayout(new FlowLayout(0 ,20 , 10));
        this.setBorder(new EmptyBorder(0,20,50,20));

        iconBackground = new JPanel();
        iconBackground.setPreferredSize(new Dimension(200, 125));
        iconBackground.setBackground(BackgroundColor);
        iconBackground.putClientProperty( FlatClientProperties.STYLE, "arc: 30" );
        iconBackground.setLayout(new FlowLayout(1,20,10));

        lblIcon = new JLabel();
        lblIcon.setIcon(new FlatSVGIcon("./icon/" + linkIcon));
        iconBackground.add(lblIcon);
        iconBackground.add(lblIcon, BorderLayout.CENTER);
        this.add(iconBackground);

        lblTitle = new JLabel("<html>" + title.toUpperCase() + "</html>");
        lblTitle.setForeground(FontColor);
        lblTitle.putClientProperty("FlatLaf.style", "font: 190% $h4.font");
        this.add(lblTitle);

        lblContent = new JLabel(content);
        lblContent.setForeground(Color.BLACK);
        lblContent.putClientProperty("FlatLaf.style", "font: 100% $h4.font");
    
        this.add(lblContent);
        
    }

//    @Override
//    protected void paintComponent(Graphics grphcs) {
////        createShadow(grphcs);
//    }
//
//    private void createShadow(Graphics grphcs) {
//        Graphics2D g2 = (Graphics2D) grphcs;
//        int size = shadowSize * 2;
//        int x = 0;
//        int y = 0;
//        int width = getWidth() - size;
//        int height = getHeight() - size;
//        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g = img.createGraphics();
//        g2.setBackground(HowerBackgroundColor);
//
//        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.fillRoundRect(0, 0, width, height, 30, 30);
//
//        //Create Shadow
//        ShadowRenderer render = new ShadowRenderer(size, shadowOpacity, shadowColor);
//        g2.drawImage(render.createShadow(img), 0, 0, null);
//        g2.setPaint(shadowColor);
//
//        g2.drawImage(img, x, y, null);
//    }
}
