package GUI.Dialog;

import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import BUS.TaiKhoanBUS;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import GUI.MainKH;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.MenuTaskbar;
import GUI.Component.NumericDocumentFilter;
import helper.Validation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

import java.awt.Frame;




public class RegisterDialog extends JDialog implements ActionListener {

    ButtonCustom save, cancel;
    HeaderTitle title;
    JPanel top, center, bottom;
    InputForm  tnd, hoten, phone, email, password, confirm;
    NhanVienDTO nv;
    TaiKhoanBUS tkbus;
    NhanVienBUS nvbus;
    MenuTaskbar menuTaskbar;
    JLabel[] jl;
    JPanel[] panel;
    JLabel change;
    JPanel pn_1 , pn_2 , pn_3;
    Frame tmp;
    public RegisterDialog(Frame parent, boolean modal) {
        super(parent, modal);
        tmp = parent;
        initComponent();
        setLocationRelativeTo(null);
    }

    public void initComponent() {
        this.setSize(400, 600);
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.setResizable(false);
        top = new JPanel();
        top.setBackground(new Color(0x279C40));
        top.setLayout(new FlowLayout(0, 0, 0));
        title = new HeaderTitle("ĐĂNG KÍ TÀI KHOẢN KHÁCH HÀNG");
        title.setColor("279C40");
        top.add(title);
        this.add(top, BorderLayout.NORTH);

        center = new JPanel(new GridLayout(4,1));
        center.setBorder(new EmptyBorder(20, 10,20, 10));
        center.setBackground(Color.WHITE);
        String opt[] = {"Tên đăng nhập", "Họ tên", "Số điện thoại", "Email", "Mật khẩu", "Xác nhận mật khẩu"};
        panel = new JPanel[opt.length];
        panel[0] = new JPanel(new GridLayout(1, 1));
        panel[0].setPreferredSize(new Dimension(400, 100));
        tnd = new InputForm(opt[0]);
        panel[0].add(tnd);

        panel[1] = new JPanel(new GridLayout(1, 1));
        panel[1].setPreferredSize(new Dimension(400, 100));
        hoten = new InputForm(opt[1]);
        panel[1].add(hoten);

        panel[2] = new JPanel(new GridLayout(1, 1));
        panel[2].setPreferredSize(new Dimension(400, 100));
        phone = new InputForm(opt[2]);
        PlainDocument phonex = (PlainDocument) phone.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter())); // dòng này và trên để chỉ nhập số
        panel[2].add(phone);

        panel[3] = new JPanel(new GridLayout(1, 1));
        panel[3].setPreferredSize(new Dimension(400, 100));
        email = new InputForm(opt[3]);
        panel[3].add(email);

        panel[4] = new JPanel(new GridLayout(1, 1));
        panel[4].setPreferredSize(new Dimension(400, 100));
        password = new InputForm(opt[4], "password");
        panel[4].add(password);

        panel[5] = new JPanel(new GridLayout(1 , 1));
        panel[5].setPreferredSize(new Dimension(400, 100));
        confirm = new InputForm(opt[5],"password");
        panel[5].add(confirm);


        center.add(panel[0]);
        center.add(panel[1]);
        center.add(panel[2]);
        center.add(panel[3]);
        center.add(panel[4]);
        center.add(panel[5]);
        this.add(center, BorderLayout.CENTER);

        bottom = new JPanel(new GridLayout(1,1));
        bottom.setBackground(new Color(0x66F284));

        save = new ButtonCustom("Đăng kí", "register", 15);
        save.addActionListener(this);
        bottom.add(save);
        this.add(bottom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == save) {
            
            TaiKhoanBUS tkBUS = new TaiKhoanBUS();
            if(Validation.isEmpty(tnd.getText()) || tnd.getText().length() <= 3) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập không được rỗng và dưới 3 kí tự", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            }
            else if(!tkBUS.checkTDN(tnd.getText())) JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            else if(Validation.isEmpty(hoten.getText())) JOptionPane.showMessageDialog(this, "Họ tên không được rỗng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            else if(Validation.isEmpty(phone.getText()) || phone.getText().length() != 10 || !Validation.isNumber(phone.getText())) JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và có 10 số", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            else if(!Validation.isEmail(email.getText())) JOptionPane.showMessageDialog(this, "Email không được rỗng và đúng định dạng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            else if(Validation.isEmpty(password.getPass()) || password.getPass().length() < 6) JOptionPane.showMessageDialog(this, "Mật khẩu không được rỗng và nhiều hơn 6 kí tự", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            else if(!password.getPass().equals(confirm.getPass())) JOptionPane.showMessageDialog(this, "Mật khẩu không trùng nhau", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            else {
                KhachHangBUS khBUS = new KhachHangBUS();
                String TDN = tnd.getText();
                String HOTEN = hoten.getText();
                String SDT = phone.getText();
                String EMAIL = email.getText();
                String PASSWORD = password.getPass();
                KhachHangDTO kh = new KhachHangDTO(khBUS.getMKHMAX() + 1, HOTEN, SDT, null, EMAIL);
                TaiKhoanDTO tk = new TaiKhoanDTO(khBUS.getMKHMAX() + 1, TDN, PASSWORD, 4, 1);
                khBUS.add(kh);
                tkBUS.addAccKH(tk);
                this.dispose();
                tmp.dispose();
                MainKH main;
                try {
                    main = new MainKH(tk);
                    main.setVisible(true);
                } catch (UnsupportedLookAndFeelException e1) {
                    e1.printStackTrace();
                }
                menuTaskbar.resetChange();
            }
        }
    }
}
