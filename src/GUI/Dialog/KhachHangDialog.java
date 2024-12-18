package GUI.Dialog;

import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.ButtonCustom;
import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import GUI.Panel.KhachHang;
import GUI.Component.NumericDocumentFilter;
import helper.Validation;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

public class KhachHangDialog extends JDialog implements MouseListener {

    KhachHang jpKH;
    private HeaderTitle titlePage;
    private JPanel pnlMain, pnlButtom;
    private ButtonCustom btnThem, btnCapNhat;
    private InputForm tenKH, sdtKH, diachiKH, emailKH;
    private JTextField maKH;
    KhachHangDTO kh;

    public KhachHangDialog(KhachHang jpKH, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.jpKH = jpKH;
        tenKH = new InputForm("Tên khách hàng");
        sdtKH = new InputForm("Số điện thoại");
        PlainDocument phonex = (PlainDocument)sdtKH.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));
        diachiKH = new InputForm("Địa chỉ");
        emailKH = new InputForm("Email");
        initComponents(title, type);
    }

    public KhachHangDialog(KhachHang jpKH, JFrame owner, String title, boolean modal, String type, DTO.KhachHangDTO kh) {
        super(owner, title, modal);
        this.kh=kh;
        maKH = new JTextField("");
        setMaKH(Integer.toString(kh.getMaKH()));
        tenKH = new InputForm("Tên khách hàng");
        setTenKH(kh.getHoten());
        sdtKH = new InputForm("Số điện thoại");
        setSdtKH(kh.getSdt());
        diachiKH = new InputForm("Địa chỉ");
        setDiaChiKH(kh.getDiachi());
        emailKH = new InputForm("Email");
        setEmailKH(kh.getEMAIL());
        this.jpKH = jpKH;
        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        this.setSize(new Dimension(500, 500));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());
        pnlMain = new JPanel(new GridLayout(3, 1, 20, 0));
        pnlMain.setBackground(Color.white);

        pnlMain.add(tenKH);
        pnlMain.add(sdtKH);
        pnlMain.add(diachiKH);
        pnlMain.add(emailKH);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);
        btnThem = new ButtonCustom("Thêm khách hàng", "success", 14);
        btnCapNhat = new ButtonCustom("Lưu thông tin", "success", 14);
        // btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);

        //Add MouseListener btn
        btnThem.addMouseListener(this);
        btnCapNhat.addMouseListener(this);
        // btnHuyBo.addMouseListener(this);

        switch (type) {
            case "create" ->
                pnlButtom.add(btnThem);
            case "update" ->
                pnlButtom.add(btnCapNhat);
            case "view" -> {
                tenKH.setDisable();
                sdtKH.setDisable();
                diachiKH.setDisable();
                emailKH.setDisable();
            }
            default ->
                throw new AssertionError();
        }
        // pnlButtom.add(btnHuyBo);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlButtom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setTenKH(String name) {
        tenKH.setText(name);
    }

    public String getTenKH() {
        return tenKH.getText();
    }

    public String getMaKH() {
        return maKH.getText();
    }

    public void setMaKH(String id) {
        this.maKH.setText(id);
    }

    public String getSdtKH() {
        return sdtKH.getText();
    }

    public void setSdtKH(String id) {
        this.sdtKH.setText(id);
    }

    public String getDiaChiKH() {
        return diachiKH.getText();
    }

    public void setDiaChiKH(String id) {
        this.diachiKH.setText(id);
    }

    public String getEmailKH() {
        return emailKH.getText();
    }

    public void setEmailKH(String id) {
        this.emailKH.setText(id);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    boolean Validation(){
       if (Validation.isEmpty(tenKH.getText())) {
    JOptionPane.showMessageDialog(this, "Tên khách hàng không được rỗng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
    return false;
}

// Kiểm tra ký tự đặc biệt (ngoại trừ dấu cách và dấu '-')
if (tenKH.getText().matches(".*[^a-zA-ZÀ-ỹ\\s\\-].*")) {
    JOptionPane.showMessageDialog(this, "Tên khách hàng không được chứa ký tự đặc biệt (ngoại trừ dấu cách và dấu -)", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
    return false;
}

// Kiểm tra tên có chứa số
if (tenKH.getText().matches(".*\\d.*")) {
    JOptionPane.showMessageDialog(this, "Tên khách hàng không được chứa số", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
    return false;
}
         String sdt = sdtKH.getText();

// Kiểm tra số điện thoại không được rỗng
if (Validation.isEmpty(sdt)) {
    JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
    return false;
}

// Kiểm tra số điện thoại phải là 10 ký tự số
if (!Validation.isNumber(sdt) || sdt.length() != 10) {
    JOptionPane.showMessageDialog(this, "Số điện thoại phải là 10 ký tự số", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
    return false;
}

// Kiểm tra số điện thoại phải bắt đầu bằng số 0
if (!sdt.startsWith("0")) {
    JOptionPane.showMessageDialog(this, "Số điện thoại phải bắt đầu bằng số 0", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
    return false;
}

// Nếu tất cả các kiểm tra đều thành công, tiếp tục xử lý

        else  if (Validation.isEmpty(diachiKH.getText())) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
        if (Validation.isEmpty(emailKH.getText())) {
    JOptionPane.showMessageDialog(this, "Email không được rỗng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
    return false;
}

if (!Validation.isEmail(emailKH.getText())) {
    JOptionPane.showMessageDialog(this, "Email phải đúng định dạng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
    return false;
}
          return true;
    }
    @Override
    public void mousePressed(MouseEvent e) {
      if (e.getSource() == btnThem && Validation()) {
    int id = KhachHangDAO.getInstance().getAutoIncrement();
    long now = System.currentTimeMillis();
    Timestamp currenTime = new Timestamp(now);
    
    // Tạo đối tượng khách hàng
    KhachHangDTO newKhachHang = new DTO.KhachHangDTO(id, tenKH.getText(), sdtKH.getText(), diachiKH.getText(), emailKH.getText(), currenTime);
    
    // Thêm khách hàng vào danh sách
    boolean isAdded = jpKH.khachhangBUS.add(newKhachHang);
    
    if (isAdded) {
        // Cập nhật lại bảng dữ liệu
        jpKH.loadDataTable(jpKH.listkh);
        JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    // Đóng cửa sổ hiện tại
    dispose();
}

        // else if (e.getSource() == btnHuyBo) {
        //     dispose();
        // }
//        else if (e.getSource() == btnCapNhat && Validation()) {
//            
//            jpKH.khachhangBUS.update(new DTO.KhachHangDTO(kh.getMaKH(), tenKH.getText(), sdtKH.getText(), diachiKH.getText(), emailKH.getText()));
//            
//            jpKH.loadDataTable(jpKH.listkh);
//            dispose();
//        }
else if (e.getSource() == btnCapNhat && Validation()) {
    // Clone the existing list
    ArrayList<KhachHangDTO> updatedList = new ArrayList<>(jpKH.listkh);
    
    // Create the updated KhachHangDTO object
    KhachHangDTO updatedKhachHang = new KhachHangDTO(
        kh.getMaKH(), // Existing ID
        tenKH.getText(),
        sdtKH.getText(),
        diachiKH.getText(),
        emailKH.getText(),
        kh.getNgaythamgia() // Preserve the existing timestamp
    );
    
    // Find and update the record in the new list
    for (int i = 0; i < updatedList.size(); i++) {
        KhachHangDTO kh1 = updatedList.get(i);
        if (kh1.getMaKH() == updatedKhachHang.getMaKH()) {
            updatedList.set(i, updatedKhachHang);
            break;
        }
    }
    
    // Perform the update in the database
    jpKH.khachhangBUS.update(updatedKhachHang);
    
    // Reload the data table with the new list
    jpKH.loadDataTable(updatedList);
    
    // Dispose of the current window or dialog
    dispose();
}

    }

    public static boolean isPhoneNumber(String str) {
        // Loại bỏ khoảng trắng và dấu ngoặc đơn nếu có
        str = str.replaceAll("\\s+", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\-", "");

        // Kiểm tra xem chuỗi có phải là một số điện thoại hợp lệ hay không
        if (str.matches("\\d{10}")) { // Kiểm tra số điện thoại 10 chữ số
            return true;
        } else if (str.matches("\\d{3}-\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu gạch ngang
            return true;
        } else if (str.matches("\\(\\d{3}\\)\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu ngoặc đơn
            return true;
        } else {
            return false; // Trả về false nếu chuỗi không phải là số điện thoại hợp lệ
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
