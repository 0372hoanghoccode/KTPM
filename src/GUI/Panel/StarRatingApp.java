package GUI.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StarRatingApp {

    public static void main(String[] args) {
        // Apply FlatLaf styling
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Create the main frame
        JFrame mainFrame = new JFrame("ĐÁNH GIÁ");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 450); // Adjust size to accommodate all components
        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(Color.WHITE); // Set background color to white

        // Create and add title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(Color.ORANGE); // Set background color to orange

        JLabel titleLabel = new JLabel("ĐÁNH GIÁ SẢN PHẨM");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK); // Set font color to black for contrast
        titlePanel.add(titleLabel);
        mainFrame.add(titlePanel, BorderLayout.NORTH);

        // Create and add image panel
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout());
        imagePanel.setBackground(Color.WHITE); // Set background color to white
        // Replace "2.png" with the actual path to your image
        JLabel imageLabel = new JLabel(new ImageIcon("./src/img/2.png"));
        imagePanel.add(imageLabel);
        mainFrame.add(imagePanel, BorderLayout.CENTER);

        // Create panel to hold star buttons
        JPanel starPanel = new JPanel();
        starPanel.setLayout(new FlowLayout());
        starPanel.setBackground(Color.WHITE); // Set background color to white
        
        // Create star buttons
        JButton[] starButtons = new JButton[5];
        for (int i = 0; i < starButtons.length; i++) {
            // Replace "ngoisao.png" with the actual path to your star image
            ImageIcon starIcon = new ImageIcon("./src/img/ngoisao.png");
            // Optionally, scale the image to fit the button
            Image starImage = starIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            starButtons[i] = new JButton(new ImageIcon(starImage));
            starButtons[i].setPreferredSize(new Dimension(50, 50)); // Adjust size as needed
            starButtons[i].setBorder(BorderFactory.createEmptyBorder()); // Remove button border
            starButtons[i].setContentAreaFilled(false); // Remove default button background
            final int rating = i + 1;
            starButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(mainFrame, "Bạn đánh giá: " + rating + " sao \nCám ơn bạn đã đánh giá!");
                    // Close the application after clicking OK
                    System.exit(0);
                }
            });
            starPanel.add(starButtons[i]);
        }

        // Create and add a panel for star rating
        JPanel starRatingPanel = new JPanel();
        starRatingPanel.setLayout(new FlowLayout());
        starRatingPanel.setBackground(Color.WHITE); 
        starRatingPanel.add(starPanel);
        mainFrame.add(starRatingPanel, BorderLayout.SOUTH);

        // Display frame
        mainFrame.setVisible(true);
    }
}
