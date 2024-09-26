package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import GUI.Dialog.QuenMatKhau;
import GUI.Dialog.RegisterDialog;
import javax.swing.border.EmptyBorder;
import GUI.Component.InputForm;
import helper.BCrypt;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import DAO.TaiKhoanDAO;
import DAO.TaiKhoanKHDAO;
import DTO.TaiKhoanDTO;
import java.util.ArrayList;


public class login_page extends JFrame implements KeyListener{

    private JPanel login_nhap;
    private JLabel lb1 , lb2, lb3, lb_img_1;
    private InputForm txtUsername, txtPassword;
    private JButton bt;

    public login_page() {
        init();
        txtUsername.setText("admin");
        txtPassword.setPass("123456");
        this.setVisible(true);
    }

    private void init() { 
        FlatRobotoFont.install();
        FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
        UIManager.put("PasswordField.showRevealButton", true);
    
        this.setTitle("Đăng nhập" );
        this.setSize(new Dimension(450 , 750));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainKH main;
                try {
                    main = new MainKH(new TaiKhoanDTO(0,"","",5,1));
                    main.setVisible(true);
                } catch (UnsupportedLookAndFeelException e1) {
                    e1.printStackTrace();
                }
            }
        });
    
        this.setLayout(new BorderLayout(0 , 0));
        JFrame jf = this ;
    
        // Container to hold both the image and login form
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);
        this.add(container, BorderLayout.CENTER);
    
        imgIntro(container);  // Add image section at the top
        
        login_nhap = new JPanel();
        login_nhap.setBackground(Color.WHITE);
        login_nhap.setLayout(new FlowLayout(1, 0, 10 ));
        login_nhap.setBorder(new EmptyBorder(20, 0, 0, 0));
        login_nhap.setPreferredSize(new Dimension(500, 740));
        
        GridBagConstraints gbc = new GridBagConstraints();
        lb1 = new JLabel("ĐĂNG NHẬP VÀO HỆ THỐNG");
        lb1.setFont(new Font("Tahoma", Font.BOLD , 20));
        lb1.setForeground(Color.ORANGE); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        login_nhap.add(lb1);
        
        JPanel paneldn = new JPanel();
        paneldn.setBackground(Color.WHITE);
        paneldn.setPreferredSize(new Dimension(400, 150));
        paneldn.setLayout(new GridLayout(2, 1));
    
        txtUsername = new InputForm(" Tên đăng nhập");
        txtUsername.setPreferredSize(new Dimension(300, 40));
        paneldn.add(txtUsername);
    
        txtPassword = new InputForm(" Mật khẩu", "password");
        txtPassword.setPreferredSize(new Dimension(300, 40));
        gbc.gridy++;
        gbc.gridx = 0;
        paneldn.add(txtPassword);
    
        txtUsername.getTxtForm().addKeyListener(this);
        txtPassword.getTxtPass().addKeyListener(this);
        
        login_nhap.add(paneldn);
    
        lb2 = new JLabel("<html><u><i style='font-size: 10px;'>  </i></u></html>", JLabel.LEFT);
        lb2.setPreferredSize(new Dimension(200,50));
        lb2.setForeground(Color.BLACK); 
        lb2.addMouseListener(new MouseAdapter() {
            @Override   
            public void mouseEntered(MouseEvent e) {
                lb2.setForeground(new Color(0, 202,232));
            }
            @Override
            public void mouseClicked(MouseEvent evt) {
                QuenMatKhau qmk = new QuenMatKhau(jf, true);
                qmk.setVisible(true);
            }
            public void mouseExited(MouseEvent e) {
                lb2.setForeground(Color.BLACK);
            }
        });
        login_nhap.add(lb2);
    
        lb3 = new JLabel("<html><u><i style='font-size: 10px;'>Đăng kí tài khoản ?</i></u></html>", JLabel.RIGHT);
        lb3.setPreferredSize(new Dimension(200,50));
        lb3.setForeground(Color.BLACK); 
        lb3.addMouseListener(new MouseAdapter() {
            @Override   
            public void mouseEntered(MouseEvent e) {
                lb3.setForeground(Color.ORANGE);
            }
            @Override
            public void mouseClicked(MouseEvent evt) {
                new RegisterDialog(jf, true);
            }
            public void mouseExited(MouseEvent e) {
                lb3.setForeground(Color.BLACK);
            }
        });
        login_nhap.add(lb3);
    
        JPanel buttonPanel = new JPanel(); 
        buttonPanel.setBackground(Color.WHITE);   
        bt = new JButton("ĐĂNG NHẬP");
        bt.setPreferredSize(new Dimension(300, 40));
        bt.setLayout(new FlowLayout(1, 0, 15));
        bt.setBackground(Color.ORANGE);
        bt.setFont(new Font("Tahoma", Font.BOLD, 20)); 
        bt.setForeground(Color.WHITE);
        buttonPanel.add(bt);
        
        bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bt.setBackground(new Color(0, 202,232));
            }
            public void mousePressed(MouseEvent evt) {
                try {
                    checkLogin();
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(login_page.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            public void mouseExited(MouseEvent e) {
                bt.setBackground(Color.ORANGE);
            }
        });
    
        login_nhap.add(buttonPanel); 
        
        container.add(login_nhap);  // Add login form below the image
    }
    
    public void printAllAccounts() {
    // Lấy danh sách tất cả tài khoản nhân viên
    ArrayList<TaiKhoanDTO> danhSachTaiKhoan = TaiKhoanDAO.getInstance().selectAll();

    // Xây dựng thông báo
    StringBuilder info = new StringBuilder();
    info.append("Danh sách tài khoản nhân viên:\n\n");

    for (TaiKhoanDTO tk : danhSachTaiKhoan) {
        info.append("Mã nhân viên: ").append(tk.getMNV()).append("\n");
        info.append("Tên tài khoản: ").append(tk.getTDN()).append("\n");
        info.append("Mật khẩu chưa mã hóa: ").append(tk.getMK()).append("\n");
        info.append("----------------------------\n");
    }

    // Hiển thị thông tin
    JOptionPane.showMessageDialog(null, info.toString(), "Danh Sách Tài Khoản", JOptionPane.INFORMATION_MESSAGE);
}

public void checkLogin() throws UnsupportedLookAndFeelException {
    String usernameCheck = txtUsername.getText();
    String passwordCheck = txtPassword.getPass();

    // Check for empty fields and provide specific feedback
    if (usernameCheck.equals("") && passwordCheck.equals("")) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập và mật khẩu", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
    } else if (usernameCheck.equals("")) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
    } else if (passwordCheck.equals("")) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
    } else {
        TaiKhoanDTO tk = TaiKhoanDAO.getInstance().selectByUser(usernameCheck);
        if (tk == null) {
            TaiKhoanDTO tkkh = TaiKhoanKHDAO.getInstance().selectByUser(usernameCheck);
            if (tkkh == null) {
                JOptionPane.showMessageDialog(this, "Tài khoản của bạn không tồn tại trên hệ thống", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            } else {
                if (tkkh.getTT() == 0) {
                    JOptionPane.showMessageDialog(this, "Tài khoản của bạn đang bị khóa", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (passwordCheck.equals(tkkh.getMK())) { // So sánh trực tiếp
                        try {
                            this.dispose();
                            MainKH main = new MainKH(tkkh);
                            main.setVisible(true);
                        } catch (UnsupportedLookAndFeelException ex) {
                            Logger.getLogger(login_page.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Mật khẩu không chính xác.");
                    }                    
                }
            }
        } else {
            if (tk.getTT() == 0) {
                JOptionPane.showMessageDialog(this, "Tài khoản của bạn đang bị khóa", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            } else {
                if (BCrypt.checkpw(passwordCheck, tk.getMK())) {
                    try {
                        this.dispose();
                        Main main = new Main(tk);
                        main.setVisible(true);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(login_page.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Mật khẩu không khớp", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}



    

    // public static void main(String[] args) {
        // FlatRobotoFont.install();
        // FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
        // FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
        // FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
        // FlatIntelliJLaf.registerCustomDefaultsSource("style");
        // FlatIntelliJLaf.setup();

        // UIManager.put("PasswordField.showRevealButton", true);
        // new login_page();
    // }

    
public void imgIntro(JPanel container) {
    JPanel bo = new JPanel();
    bo.setBorder(new EmptyBorder(3, 10, 5, 5));
    bo.setPreferredSize(new Dimension(350, 200));  // Adjust the size to your preference
    bo.setBackground(Color.WHITE);

    lb_img_1 = new JLabel(new ImageIcon("./src/img/2.png"));  // Ensure the image path is correct
    bo.add(lb_img_1);

    container.add(bo);  // Add image panel to the container
}
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                checkLogin();
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(login_page.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}