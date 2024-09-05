package GUI.Dialog;

import BUS.KhachHangBUS;
import BUS.TaiKhoanBUS;
import DAO.KhachHangDAO;
import DAO.TaiKhoanKHDAO;
import DTO.KhachHangDTO;
import DTO.TaiKhoanDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.MenuTaskbarKH;
import GUI.Component.NumericDocumentFilter;
import helper.BCrypt;
import helper.Validation;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;



public class MyAccountKH extends JDialog implements ActionListener {

    CardLayout card;
    ButtonCustom save, cancel;
    HeaderTitle title;
    JPanel top, center, bottom;
    InputForm current_pwd, phone,address, EMAIL, new_pwd, confirm;
    KhachHangDTO kh;
    TaiKhoanBUS tkbus;
    KhachHangBUS khbus;
    MenuTaskbarKH menuTaskbar;
    JLabel[] jl;
    JPanel[] panel;
    JLabel change;
    JPanel pn_1 , pn_2 , pn_3;
    InputForm current_pass , new_pass, confirm_pass;
    Boolean check  = false ;

    public MyAccountKH(JFrame owner, MenuTaskbarKH menutaskbar, String title, boolean modal) {
        super(owner, title, modal);
        initComponent(menutaskbar);
        this.setLocationRelativeTo(null);
    }

    public void initComponent(MenuTaskbarKH menutaskbar) {
        tkbus = new TaiKhoanBUS();
        khbus = new KhachHangBUS();
        this.menuTaskbar = menutaskbar;
        this.setSize(400, 500);
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.setResizable(false);
        kh = menuTaskbar.khachHangDTO; // ????
        top = new JPanel();
        top.setBackground(Color.WHITE);
        top.setLayout(new FlowLayout(0, 0, 0));
        title = new HeaderTitle("");
        top.add(title);
        this.add(top, BorderLayout.NORTH);

        center_1();

        bottom = new JPanel(new FlowLayout(1, 20, 10));
        bottom.setBackground(Color.WHITE);

        cancel = new ButtonCustom("Hủy", "danger", 15);
        cancel.addActionListener(this);
        bottom.add(cancel);
        save = new ButtonCustom("Lưu", "success", 15);
        save.addActionListener(this);
        bottom.add(save);
        this.add(bottom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void center_1() {
        check =  false;
        title.setText("CHỈNH SỬA THÔNG TIN");
        center = new JPanel(new GridLayout(5,1));
        center.setBorder(new EmptyBorder(20, 10, 0, 10));
        center.setBackground(Color.WHITE);
        String opt[] = {"Số điện thoại","Địa chỉ", "Email", "Mật khẩu"};
        panel = new JPanel[4];
        panel[0] = new JPanel(new GridLayout(1, 1));
        panel[0].setPreferredSize(new Dimension(400, 100));
        phone = new InputForm(opt[0]);
        PlainDocument phonex = (PlainDocument) phone.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));
        phone.setText(kh.getSdt());
        panel[0].add(phone);
        panel[1] = new JPanel(new GridLayout(1, 1));
        panel[1].setPreferredSize(new Dimension(400, 100));
        address = new InputForm(opt[1]);
        address.setText(kh.getDiachi());
        panel[1].add(address);
        panel[2] = new JPanel(new GridLayout(1, 1));
        panel[2].setPreferredSize(new Dimension(400, 100));
        EMAIL = new InputForm(opt[2]);
        EMAIL.setText(kh.getEMAIL());
        panel[2].add(EMAIL);
        panel[3] = new JPanel(new GridLayout(1, 1));
        panel[3].setPreferredSize(new Dimension(400, 100));
        current_pwd = new InputForm(opt[3]);
        current_pwd.setText("********");
        current_pwd.setDisable();
        panel[3].add(current_pwd);
        change = new JLabel("<html><p style = 'font-size : 11px;'>Thay đổi</p></html>" , JLabel.RIGHT);
        change.setPreferredSize(new Dimension(25,25));
        change.setBorder(new EmptyBorder(0 , 0 , 30,20));
        change.addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseEntered(MouseEvent e) {
                change.setForeground(new Color(173, 216, 230));
            }
            @Override 
            public void mouseClicked(MouseEvent e) { 
                center_2();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                change.setForeground(Color.BLACK);
            }
        });
        center.add(panel[0]);
        center.add(panel[1]);
        center.add(panel[2]);
        center.add(panel[3]);
        center.add(change);
        this.add(center, BorderLayout.CENTER);
    }

    public void center_2() {
        check = true;
        title.setText("Chỉnh sửa mật khẩu");
        center.removeAll();
        center.setLayout(new GridLayout(3,1));
        center.setBorder(new EmptyBorder(10,10,20,10));
        pn_1 = new JPanel();
        pn_1.setLayout(new GridLayout(1,1));
        current_pass = new InputForm("Nhập mật khẩu hiện tại :","password");
        pn_1.add(current_pass);
        pn_2 = new JPanel();
        pn_2.setLayout(new GridLayout(1,1));
        new_pass = new InputForm("Nhập mật khẩu mới :","password");
        pn_2.add(new_pass);
        pn_3 = new JPanel();
        pn_3.setLayout(new GridLayout(1,1));
        confirm_pass = new InputForm("Nhập lại mật khẩu mới :","password");
        pn_3.add(confirm_pass);

        center.add(pn_1);
        center.add(pn_2);
        center.add(pn_3);
        center.repaint();
        center.validate();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            if(check ) {
                center.removeAll();
                center_1();
            } else {
                this.dispose();
            }
        }
        if(e.getSource() == save) {
            if(check)  {
                            TaiKhoanDTO tkdto = tkbus.getTaiKhoan(tkbus.getTaiKhoanByMaKH(kh.getMaKH()));
                            if (Validation.isEmpty(current_pass.getPass())) {
                                JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không được rỗng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                            } else if (Validation.isEmpty(new_pass.getPass())||new_pass.getPass().length()<6) {
                                JOptionPane.showMessageDialog(this, "Mật khẩu mới không được rỗng và có ít nhất 6 ký tự", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                            } else if (Validation.isEmpty(confirm_pass.getPass())) {
                                JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không được rỗng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                                return;
                            } else if (!new_pass.getPass().equals(confirm_pass.getPass()) ) {
                                JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp với mật khẩu mới", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                                return;
                            } else {
                                if (BCrypt.checkpw(current_pass.getPass(), tkdto.getMK())) {
                                    String pass = BCrypt.hashpw(confirm_pass.getPass(), BCrypt.gensalt(12));
                                    TaiKhoanKHDAO.getInstance().updatePass(kh.getEMAIL(), pass);
                                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                                    current_pass.setPass("");
                                    new_pass.setPass("");
                                    confirm_pass.setPass("");
                                    center.removeAll();
                                    center_1();
                                } else {
                                    JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không đúng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                    }
            else if (!check) {
                String text_phone = phone.getText(); 
                String text_email = EMAIL.getText(); 
                String text_diachi = address.getText();
                if(text_phone.equals(kh.getSdt()) &&  text_email.equals(kh.getEMAIL()) && text_diachi.equals(kh.getDiachi())) JOptionPane.showMessageDialog(this, "Đã sửa gì đâu mà lưu ?" , "Chỉnh sửa", JOptionPane.WARNING_MESSAGE);
                else {
                    boolean changed = false;
                    if (!text_phone.equals(kh.getSdt()) ) {
                        if (Validation.isEmpty(phone.getText()) || phone.getText().length() != 10) {
                        JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải có 10 ký tự sô", "Chỉnh sửa số điện thoại", JOptionPane.WARNING_MESSAGE);
                    } else {
                        KhachHangDTO khdto = new KhachHangDTO(kh.getMaKH(), kh.getHoten(), text_phone, kh.getDiachi(), kh.getEMAIL());
                        KhachHangDAO.getInstance().update(khdto);
                        changed = true;
                        }
                    }

                    if (!text_diachi.equals(kh.getDiachi())) {
                        if (Validation.isEmpty(address.getText())) {
                        JOptionPane.showMessageDialog(this, "Địa chỉ không được rỗng", "Chỉnh sửa", JOptionPane.WARNING_MESSAGE);
                        } else {
                            KhachHangDTO khdto = new KhachHangDTO(kh.getMaKH(), kh.getHoten(), text_phone, text_diachi, kh.getEMAIL());
                            KhachHangDAO.getInstance().update(khdto);
                            changed = true;
                        }
                    }

                    if (!text_email.equals(kh.getEMAIL()) ) {
                        if (Validation.isEmpty(EMAIL.getText()) || !Validation.isEmail(EMAIL.getText())) {
                        JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng định dạng", "Chỉnh sửa EMAIL", JOptionPane.WARNING_MESSAGE);
                        } else {
                            KhachHangDTO khdto = new KhachHangDTO(kh.getMaKH(), kh.getHoten(), text_phone, kh.getDiachi(), text_email);
                            KhachHangDAO.getInstance().update(khdto);
                            changed = true;
                        }
                    }

                    if(changed) {
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                        this.dispose();
                    }
                }
            }
        } 
        menuTaskbar.resetChange();
    }
}
