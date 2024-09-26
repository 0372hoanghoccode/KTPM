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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;




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
        this.setSize(400, 700);
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.setResizable(false);
        top = new JPanel();
        top.setBackground(new Color(0xFEA837));
        top.setLayout(new FlowLayout(0, 0, 0));
        title = new HeaderTitle("ĐĂNG KÍ TÀI KHOẢN KHÁCH HÀNG");
        title.setColor("FEA837");
        top.add(title);
        this.add(top, BorderLayout.NORTH);

        center = new JPanel(new GridLayout(6,1));
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
        bottom.setBackground(new Color(0xFEA837));

        save = new ButtonCustom("Đăng kí", "register", 15);
        save.addActionListener(this);
        bottom.add(save);
        this.add(bottom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        
        
          SwingUtilities.invokeLater(() -> {
        tnd.getTxtForm().requestFocusInWindow();
      });
        this.setVisible(true);
       
        
    }
    

    @Override
 
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == save) {
        TaiKhoanBUS tkBUS = new TaiKhoanBUS();
        KhachHangBUS khBUS = new KhachHangBUS();

        String tdn = tnd.getText().trim();
        String HOTEN = hoten.getText().trim();
        String phoneNumber = phone.getText().trim();
        String emailAddress = email.getText().trim();
        String passwordText = password.getPass().trim();
        String confirmPasswordText = confirm.getPass().trim();

        // Kiểm tra các trường nhập liệu
        if (!validateInput(tdn, HOTEN, phoneNumber, emailAddress, passwordText, confirmPasswordText, tkBUS)) {
            return;  // Dừng lại nếu có lỗi
        }

        // Nếu tất cả các kiểm tra đều thành công, tiếp tục xử lý
        int newId = khBUS.getMKHMAX() + 1;
        KhachHangDTO kh = new KhachHangDTO(newId, HOTEN, phoneNumber, null, emailAddress);
        TaiKhoanDTO tk = new TaiKhoanDTO(newId, tdn, passwordText, 4, 1);

        // Thêm khách hàng và tài khoản vào hệ thống
        khBUS.add(kh);
        tkBUS.addAccKH(tk);

        // Đóng cửa sổ hiện tại và mở cửa sổ mới
        this.dispose();
        tmp.dispose();
        try {
            MainKH main = new MainKH(tk);
            main.setVisible(true);
        } catch (UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
    }
}

// Hàm kiểm tra đầu vào
private boolean validateInput(String tdn, String HOTEN, String phoneNumber, String emailAddress, String passwordText, String confirmPasswordText, TaiKhoanBUS tkBUS) {
    // Kiểm tra tên đăng nhập
    if (!isUsernameValid(tdn, tkBUS)) {
        return false;  // Nếu tên đăng nhập không hợp lệ, trả về false
    }

    // Kiểm tra họ tên
    if (Validation.isEmpty(HOTEN)) {
        showMessage("Họ tên không được rỗng");
        return false; // Nếu họ tên rỗng, trả về false
    }
    
    if (!Validation.isNameValid(HOTEN)) {
        showMessage("Họ tên không hợp lệ (không được chứa số và ký tự đặc biệt)");
        return false; // Nếu họ tên không hợp lệ, trả về false
    }   
    
    // Kiểm tra số điện thoại
    if (Validation.isEmpty(phoneNumber)) {
        showMessage("Số điện thoại không được rỗng");
        return false; // Nếu số điện thoại rỗng, trả về false
    }

    // Kiểm tra tính hợp lệ của số điện thoại
    if (!Validation.isPhoneNumber(phoneNumber)) {
        showMessage("Số điện thoại không hợp lệ");
        return false;  // Nếu số điện thoại không hợp lệ, trả về false
    }

    // Kiểm tra sự tồn tại của số điện thoại
    if (!tkBUS.checkSDT(phoneNumber)) {
        showMessage("Số điện thoại đã tồn tại");
        return false; // Nếu số điện thoại đã tồn tại, trả về false
    }

    // Kiểm tra email
     if (Validation.isEmpty(emailAddress)) {
        showMessage("Email không được rỗng");
        return false; // Nếu email rỗng, trả về false
    }

    // Kiểm tra định dạng email
    if (!Validation.isEmail(emailAddress)) {
        showMessage("Email không đúng định dạng");
        return false; // Nếu email không đúng định dạng, trả về false
    }

    // Kiểm tra sự tồn tại của email
    if (!tkBUS.checkEmail(emailAddress)) {
        showMessage("Email đã tồn tại");
        return false; // Nếu email đã tồn tại, trả về false
    }
     

    // Kiểm tra mật khẩu
    if (Validation.isEmpty(passwordText)) {
        showMessage("Mật khẩu không được rỗng");
        return false; // Trả về false nếu mật khẩu rỗng
    }

    // Kiểm tra độ dài mật khẩu
    if (passwordText.length() < 6) {
        showMessage("Mật khẩu phải dài hơn 6 ký tự");
        return false; // Trả về false nếu mật khẩu ngắn hơn 6 ký tự
    }

    // Kiểm tra sự trùng khớp của mật khẩu
    if (!passwordText.equals(confirmPasswordText)) {
        showMessage("Mật khẩu không trùng nhau");
        return false; // Nếu mật khẩu không trùng nhau, trả về false
    }

    return true;  // Tất cả các kiểm tra đều thành công
}


// Hàm hiển thị thông báo
private void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
}

private boolean isUsernameValid(String tdn, TaiKhoanBUS tkBUS) {
    if (tdn == null) {
        return true; // Không báo lỗi
    }
    // Kiểm tra xem tên đăng nhập có rỗng hay không
    if (Validation.isEmpty(tdn)) {
        showMessage("Tên đăng nhập không được rỗng");
        return false;
    }

    // Kiểm tra độ dài tên đăng nhập
    if (tdn.length() < 5) {
        showMessage("Tên đăng nhập phải dài ít nhất 5 ký tự");
        return false;
    }

    // Kiểm tra ký tự hợp lệ
    if (!tdn.matches("^[a-zA-Z0-9._-]+$")) {
        showMessage("Tên đăng nhập chỉ cho phép ký tự chữ cái, số, dấu chấm, dấu gạch dưới và dấu gạch ngang");
        return false;
    }

    // Kiểm tra sự tồn tại của tên đăng nhập
    if (!tkBUS.checkTDN(tdn)) {
        showMessage("Tên đăng nhập đã tồn tại");
        return false;
    }

    return true; // Tên đăng nhập hợp lệ
}
private boolean validateEmail(String emailAddress) {
    
      TaiKhoanBUS tkBUS = new TaiKhoanBUS();
        KhachHangBUS khBUS = new KhachHangBUS();
    // Kiểm tra email không được rỗng
   

    // Nếu tất cả các kiểm tra đều thành công, trả về true
    return true;
}


}
