package GUI.Dialog;

import BUS.NhanVienBUS;
import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputDate;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import helper.Validation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

public class NhanVienDialog extends JDialog {

    private NhanVienBUS nv;
    private HeaderTitle titlePage;
    private JPanel main, bottom;
    private ButtonCustom btnAdd, btnEdit, btnExit;
    private InputForm name;
    private InputForm sdt;
    private InputForm email;
    private ButtonGroup gender;
    private JRadioButton male;
    private JRadioButton female;
    private InputDate jcBd;
    private NhanVienDTO nhanVien;

    public NhanVienDialog(NhanVienBUS nv, JFrame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        this.nv = nv;
        init(title, type);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public NhanVienDialog(NhanVienBUS nv, JFrame owner, boolean modal, String title, String type, DTO.NhanVienDTO nhanVien) {
        super(owner, title, modal);
        this.nv = nv;
        this.nhanVien = nhanVien;
        init(title, type);
        name.setText(nhanVien.getHOTEN());
        sdt.setText(nhanVien.getSDT());
        email.setText(nhanVien.getEMAIL());
        if (nhanVien.getGIOITINH() == 1) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }
        jcBd.setDate(nhanVien.getNGAYSINH());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void init(String title, String type) {
        this.setSize(new Dimension(450, 590));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());

        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.white);
        name = new InputForm("Họ và tên");
        sdt = new InputForm("Số điện thoại");
        PlainDocument phonex = (PlainDocument) sdt.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));
        email = new InputForm("Email");
        male = new JRadioButton("Nam");
        female = new JRadioButton("Nữ");
        gender = new ButtonGroup();
        gender.add(male);
        gender.add(female);
        JPanel jpanelG = new JPanel(new GridLayout(2, 1, 0, 2));
        jpanelG.setBackground(Color.white);
        jpanelG.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel jgender = new JPanel(new GridLayout(1, 2));
        jgender.setSize(new Dimension(500, 80));
        jgender.setBackground(Color.white);
        jgender.add(male);
        jgender.add(female);
        JLabel labelGender = new JLabel("Giới tính");
        jpanelG.add(labelGender);
        jpanelG.add(jgender);
        JPanel jpaneljd = new JPanel();
        jpaneljd.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel lbBd = new JLabel("Ngày sinh");
        lbBd.setSize(new Dimension(100, 100));
        jpaneljd.setSize(new Dimension(500, 100));
        jpaneljd.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpaneljd.setBackground(Color.white);
        jcBd = new InputDate("Ngày sinh");
        jcBd.setSize(new Dimension(100, 100));
        jpaneljd.add(lbBd);
        jpaneljd.add(jcBd);
        main.add(name);
        main.add(email);
        main.add(sdt);
        main.add(jpanelG);
        main.add(jcBd);

        bottom = new JPanel(new FlowLayout());
        bottom.setBorder(new EmptyBorder(10, 0, 10, 0));
        bottom.setBackground(Color.white);
        btnAdd = new ButtonCustom("Thêm nhân viên ", "success", 14);
        btnEdit = new ButtonCustom("Lưu thông tin", "success", 14);
        btnExit = new ButtonCustom("Hủy bỏ", "danger", 14);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (ValidationInput()) {
                        // Kiểm tra email mới trước khi thêm
                        if (checkEmail(email.getText(), null)) { // null vì không có email hiện tại
                            if (checkSdt(sdt.getText(),null)) {
                                try {
                                    int txt_gender = -1;
                                    if (male.isSelected()) {
                                        txt_gender = 1;
                                    } else if (female.isSelected()) {
                                        txt_gender = 0;
                                    }
                                    int manv = NhanVienDAO.getInstance().getAutoIncrement();
                                    String txtName = name.getText();
                                    String txtSdt = sdt.getText();
                                    String txtEmail = email.getText();
                                    Date birthDay = jcBd.getDate();
                                    java.sql.Date sqlDate = new java.sql.Date(birthDay.getTime());
                                    NhanVienDTO nV = new NhanVienDTO(manv, txtName, txt_gender, sqlDate, txtSdt, 1, txtEmail);
                                    NhanVienDAO.getInstance().insert(nV);
                                    nv.insertNv(nV);
                                    nv.loadTable();
                                    dispose();
                                } catch (ParseException ex) {
                                    Logger.getLogger(NhanVienDialog.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(NhanVienDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (ValidationInput()) {
                        // Kiểm tra email mới và loại trừ email của tài khoản hiện tại
                        if (checkEmail(email.getText(), nhanVien.getEMAIL   ())) { // Sử dụng email của tài khoản hiện tại
                            if (checkSdt(sdt.getText(), nhanVien.getSDT())) {
                                try {
                                    int txt_gender = -1;
                                    if (male.isSelected()) {
                                        txt_gender = 1;
                                    } else if (female.isSelected()) {
                                        txt_gender = 0;
                                    }
                                    String txtName = name.getText();
                                    String txtSdt = sdt.getText();
                                    String txtEmail = email.getText();
                                    Date birthDay = jcBd.getDate();
                                    java.sql.Date sqlDate = new java.sql.Date(birthDay.getTime());
                                    NhanVienDTO nV = new NhanVienDTO(nhanVien.getMNV(), txtName, txt_gender, sqlDate, txtSdt, 1, txtEmail);
                                    NhanVienDAO.getInstance().update(nV);
                                    nv.listNv.set(nv.getIndex(), nV);
                                    nv.loadTable();
                                    dispose();
                                } catch (ParseException ex) {
                                    Logger.getLogger(NhanVienDialog.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(NhanVienDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        

        switch (type) {
            case "create" ->
                bottom.add(btnAdd);
            case "update" ->{
                bottom.add(btnEdit);
                System.out.print("Nút sửa đã được nhấn");
            }
            case "detail" -> {
                name.setDisable();
                sdt.setDisable();
                email.setDisable();
                Enumeration<AbstractButton> enumeration = gender.getElements();
                while (enumeration.hasMoreElements()) {
                    enumeration.nextElement().setEnabled(false);
                }
                jcBd.setDisable();
            }
            default ->
                throw new AssertionError();
        }

        bottom.add(btnExit);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(main, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);

    }

   boolean ValidationInput() throws ParseException {
    // Kiểm tra tên nhân viên không được rỗng và có ít nhất 6 ký tự
    if (Validation.isEmpty(name.getText())) {
        JOptionPane.showMessageDialog(this, "Tên nhân viên không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        return false;
    } else if (name.getText().length() < 6) {
        JOptionPane.showMessageDialog(this, "Tên nhân viên ít nhất 6 kí tự", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    // Kiểm tra email không được rỗng và phải đúng cú pháp
    if (Validation.isEmpty(email.getText()) || !Validation.isEmail(email.getText())) {
        JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng cú pháp", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    // Kiểm tra số điện thoại không được rỗng, phải là 10 ký tự số và bắt đầu bằng số 0
    String phoneNumber = sdt.getText();
    if (Validation.isEmpty(phoneNumber) || !Validation.isNumber(phoneNumber) || phoneNumber.length() != 10 || !phoneNumber.startsWith("0")) {
        JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng, phải là 10 ký tự số và bắt đầu bằng số 0", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    // Kiểm tra ngày sinh không được null
    if (jcBd.getDate() == null) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    // Kiểm tra giới tính đã được chọn
    if (!male.isSelected() && !female.isSelected()) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    // Nếu tất cả các kiểm tra đều thành công, trả về true
    return true;
}

    
    public boolean checkEmail(String email, String currentEmail){
    // Kiểm tra xem email đã tồn tại trong hệ thống và không phải là email của tài khoản đang đăng nhập
    if (NhanVienDAO.getInstance().selectByEmail(email) != null && !email.equals(currentEmail)) {
        // Hiển thị thông báo nếu email đã tồn tại và không phải là của tài khoản hiện tại
        JOptionPane.showMessageDialog(null, "Tài khoản email này đã được sử dụng trong hệ thống!");
        return false;
    }
    return true;
    }

    public boolean checkSdt(String phoneNumber, String currentphoneNumber) {
    // Kiểm tra số điện thoại trong cơ sở dữ liệu
    if (!(NhanVienDAO.getInstance().selectBySdt(phoneNumber) == null) && !phoneNumber.equals(currentphoneNumber) ) {
        JOptionPane.showMessageDialog(this, "Số điện thoại này đã được sử dụng trong hệ thống!", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    return true;
}

}
