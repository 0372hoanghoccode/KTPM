
package GUI.Dialog;

import DAO.ChiTietLoHangDAO;
import DAO.KhuVucSach1DAO;
import DTO.ChiTietLoHangDTO;
import DTO.KhuVucSach1DTO;
import DTO.KhuVucSachDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import helper.Validation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import GUI.Panel.KhuVucSach1;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//đây nè
public final class KhuVucSach1Dialog extends JDialog implements ActionListener {

    private KhuVucSach1 jpkvk;
    private HeaderTitle titlePage;
    private JPanel pnmain,  pnmain_top,  pnmain_bottom, pnmain_btn;
    private ButtonCustom btnThem, btnCapNhat;
    private InputForm txtMaPhieu, txtThoiGian ,      malohang ,ghichu;
    private KhuVucSach1DTO kvk;
    DefaultTableModel tblModel;
    JTable table, tblImei;
    JScrollPane scrollTable;
  
   


    public KhuVucSach1Dialog(KhuVucSach1 jpkvk, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.jpkvk = jpkvk;
        initComponents(title, type);
        
    }

    public KhuVucSach1Dialog(KhuVucSach1 jpkvk, JFrame owner, String title, boolean modal, String type, KhuVucSach1DTO kvk) {
        super(owner, title, modal);
        this.jpkvk = jpkvk;
        this.kvk = kvk;
        initComponents(title, type);
       
    }

    // public void initComponents(String title, String type) {
    //     this.setSize(new Dimension(1000, 500));
    //     this.setLayout(new BorderLayout(0, 0));
    //     titlePage = new HeaderTitle(title.toUpperCase());
    //     pnmain = new JPanel(new BorderLayout());
    //     pnmain.setBackground(Color.white);
    //     pnmain_top = new JPanel(new GridLayout(1, 4));
    //     txtMaPhieu = new InputForm("Mã phiếu");
    //     txtNhanVien = new InputForm("Tên nhân viên");
    //     txtThoiGian = new InputForm("Thời gian tạo");

        
    //     txtMaPhieu.setEditable(false);
    //     txtNhanVien.setEditable(false);
    //     txtThoiGian.setEditable(false);

    //     pnmain_top.add(txtMaPhieu);
    //     pnmain_top.add(txtNhanVien);
    //     pnmain_top.add(txtThoiGian);

    //     pnbottom = new JPanel(new FlowLayout());
    //     pnbottom.setBorder(new EmptyBorder(10, 0, 10, 0));
    //     pnbottom.setBackground(Color.white);
    //     btnThem = new ButtonCustom("Thêm khu vực sách", "success", 14);
    //     btnCapNhat = new ButtonCustom("Lưu thông tin", "success", 14);
    //     // btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);

    //     //Add MouseListener btn
    //     btnThem.addActionListener(this);
    //     btnCapNhat.addActionListener(this);
    //     // btnHuyBo.addActionListener(this);

    //     switch (type) {
    //         case "create" -> pnbottom.add(btnThem);
    //         case "detail" -> {
    //             initInfo();
    //         }
    //         case "update" -> {
    //             pnbottom.add(btnCapNhat);
    //             initInfo();
    //         }
    //         default -> throw new AssertionError();
    //     }
    //     // pnbottom.add(btnHuyBo);
    //     this.add(titlePage, BorderLayout.NORTH);
    //     this.add(pnmain, BorderLayout.CENTER);
    //     this.add(pnbottom, BorderLayout.SOUTH);
    //     this.setLocationRelativeTo(null);
    //     this.setVisible(true);

    // }

 
public void initComponents(String title, String type) {
    this.setSize(new Dimension(1100, 500));
    this.setLayout(new BorderLayout(0, 0));
    titlePage = new HeaderTitle(title.toUpperCase());

    pnmain = new JPanel(new BorderLayout());

    // Tạo panel top với 2 input fields
    pnmain_top = new JPanel(new GridLayout(1, 2));
    txtMaPhieu = new InputForm("Mã Lô Hàng");
    txtThoiGian = new InputForm("Thời gian tạo");

    txtMaPhieu.setDisable();
    txtThoiGian.setDisable();

    pnmain_top.add(txtMaPhieu);
    pnmain_top.add(txtThoiGian);

    // Tạo panel bottom với các nút bấm
    pnmain_bottom = new JPanel(new GridLayout(1, 1));  // Chỉ chứa bảng nếu cần
    pnmain_bottom.setBorder(new EmptyBorder(5, 5, 5, 5));
    pnmain_bottom.setBackground(Color.WHITE);

    table = new JTable();
    scrollTable = new JScrollPane();
    tblModel = new DefaultTableModel();
    String[] header = new String[]{"STT", "Mã Sản phẩm", "Giá Nhập", "Số lượng"};
    tblModel.setColumnIdentifiers(header);
    table.setModel(tblModel);
    table.setFocusable(false);
    scrollTable.setViewportView(table);
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    table.setDefaultRenderer(Object.class, centerRenderer);
    table.getColumnModel().getColumn(2).setPreferredWidth(200);

    // Panel chứa các nút bấm bên dưới
    pnmain_btn = new JPanel(new FlowLayout());
    pnmain_btn.setBorder(new EmptyBorder(10, 0, 10, 0));
    pnmain_btn.setBackground(Color.white);
    btnThem = new ButtonCustom("Thêm khu vực sách", "success", 14);
    btnCapNhat = new ButtonCustom("Lưu thông tin", "success", 14);
    // btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);

    // Thêm ActionListener cho các nút
    btnThem.addActionListener(this);
    btnCapNhat.addActionListener(this);
    // btnHuyBo.addActionListener(this);

    // Tùy thuộc vào loại, cấu hình giao diện
    switch (type) {
        case "create":
            updateLayoutForCreate(); // Cập nhật layout cho loại "create"
            pnmain_btn.add(btnThem); // Thêm nút "Thêm khu vực sách" khi tạo mới
            break;
        case "detail":
            pnmain_bottom.add(scrollTable); // Hiển thị bảng khi xem chi tiết
            // pnmain_btn.add(btnCapNhat); // Thêm nút "Lưu thông tin" khi xem chi tiết
            initInfo(); // Phương thức để khởi tạo thông tin chi tiết
            break;
        case "update":
            pnmain_bottom.add(scrollTable); // Hiển thị bảng khi cập nhật
            pnmain_btn.add(btnCapNhat); // Thêm nút "Lưu thông tin" khi cập nhật
            initInfo(); // Phương thức để khởi tạo thông tin chi tiết
            break;
        default:
            throw new AssertionError();
    }

    // Thêm panel vào layout chính
    pnmain.add(pnmain_top, BorderLayout.NORTH);
    pnmain.add(pnmain_bottom, BorderLayout.CENTER);
    pnmain.add(pnmain_btn, BorderLayout.SOUTH);

    this.add(titlePage, BorderLayout.NORTH);
    this.add(pnmain, BorderLayout.CENTER);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
}
private void updateLayoutForCreate() {
    // Cập nhật layout cho loại "create"
    pnmain_top.setLayout(new GridLayout(2, 1)); // Đổi layout thành GridLayout với 2 hàng và 1 cột

    // Cập nhật các trường để hiển thị đúng theo layout mới
    pnmain_top.removeAll();
    pnmain_top.add(txtMaPhieu);
    pnmain_top.add(txtThoiGian);

    // Ẩn bảng và các thành phần khác nếu có
    pnmain_bottom.removeAll(); // Xóa tất cả các thành phần trong pnmain_bottom
        // Lấy giá trị mã lô hàng lớn nhất hiện tại và cộng thêm 1
        int i = KhuVucSach1DAO.findMaxMLH()+1;
    txtMaPhieu.setText(i+"");
    // Hiển thị thời gian hiện tại
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    txtThoiGian.setText(currentTime.toString()); // Hiển thị thời gian hiện tại
    

}



    


    // public void initComponent(String title) {
    //     this.setSize(new Dimension(1100, 500));
    //     this.setLayout(new BorderLayout(0, 0));
    //     titlePage = new HeaderTitle(title.toUpperCase());

    //     pnmain = new JPanel(new BorderLayout());

    //     pnmain_top = new JPanel(new GridLayout(1, 4));
    //     txtMaPhieu = new InputForm("Mã phiếu");
    //     txtNhanVien = new InputForm("Nhân viên nhập");
    //     txtThoiGian = new InputForm("Thời gian tạo");

    //     txtMaPhieu.setEditable(false);
    //     txtNhanVien.setEditable(false);
    //     txtThoiGian.setEditable(false);

    //     pnmain_top.add(txtMaPhieu);
    //     pnmain_top.add(txtNhanVien);
    //     pnmain_top.add(txtThoiGian);

    //     pnmain_bottom = new JPanel(new GridLayout(1, 5));
    //     pnmain_bottom.setBorder(new EmptyBorder(5, 5, 5, 5));
    //     pnmain_bottom.setBackground(Color.WHITE);

    //     // pnmain_bottom_left = new JPanel(new GridLayout(1, 1));
    //     table = new JTable();
    //     scrollTable = new JScrollPane();
    //     tblModel = new DefaultTableModel();
    //     String[] header = new String[]{"STT", "Mã SP", "Tên SP", "Đơn giá", "Số lượng"};
    //     tblModel.setColumnIdentifiers(header);
    //     table.setModel(tblModel);
    //     table.setFocusable(false);
    //     scrollTable.setViewportView(table);
    //     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    //     centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    //     table.setDefaultRenderer(Object.class, centerRenderer);
    //     table.getColumnModel().getColumn(2).setPreferredWidth(200);
    
    //     pnmain_bottom.add(scrollTable);

    //     pnmain_btn = new JPanel(new FlowLayout());
    //     pnmain_btn.setBorder(new EmptyBorder(10, 0, 10, 0));
    //     pnmain_btn.setBackground(Color.white);
    //     btnPdf = new ButtonCustom("Xuất file PDF", "success", 14);
    //     // btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
    //     btnDuyet = new ButtonCustom("Duyệt phiếu", "success", 14);
    //     btnDuyet.addActionListener(this);
    //     btnPdf.addActionListener(this);
    //     // btnHuyBo.addActionListener(this);
    //     pnmain_btn.add(btnDuyet);
    //     pnmain_btn.add(btnPdf);
    //     // pnmain_btn.add(btnHuyBo);

    //     pnmain.add(pnmain_top, BorderLayout.NORTH);
    //     pnmain.add(pnmain_bottom, BorderLayout.CENTER);
    //     pnmain.add(pnmain_btn, BorderLayout.SOUTH);

    //     this.add(titlePage, BorderLayout.NORTH);
    //     this.add(pnmain, BorderLayout.CENTER);
    //     this.setLocationRelativeTo(null);
    // }


public void initInfo() {
    // Format date as "dd-MM-yyyy"
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    // Assuming kvk.getNgay() returns a Date or Timestamp object, format it
    Date ngay = kvk.getNgay();  // Make sure kvk.getNgay() returns a Date or you convert it to Date

    // Format the date to the desired string format
    String formattedDate = dateFormat.format(ngay);

    // Set other fields
    txtMaPhieu.setText(kvk.getMLH());
    txtThoiGian.setText(formattedDate);  // Set the formatted date

    int i = 0;
    ArrayList<ChiTietLoHangDTO> chitietlohang = ChiTietLoHangDAO.getByMaLoHang(kvk.getMLH());
    for (ChiTietLoHangDTO product : chitietlohang) {
        tblModel.addRow(new Object[]{
            i,
            product.getMSP(),
            product.getSoLuong(),
            product.getGiaNhap()
        });
        i++;
    }
}
       boolean Validation(){
        if (Validation.isEmpty(txtMaPhieu.getText())) {
            JOptionPane.showMessageDialog(this, "Tên khu vực sách không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
          return true;
    }
   
@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnThem && Validation()) {
        String maPhieuText = txtMaPhieu.getText();
        String thoiGianText = txtThoiGian.getText();
        // Kiểm tra dữ liệu từ các ô nhập liệu
        if (maPhieuText.isEmpty() || thoiGianText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }     
        Timestamp thoiGian;
        try {
            thoiGian = Timestamp.valueOf(thoiGianText);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Thời gian không hợp lệ. Vui lòng nhập đúng định dạng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        KhuVucSach1DTO newKhuVuc = new KhuVucSach1DTO(
            maPhieuText, // Mã khu vực mới
            thoiGian,    // Thời gian hiện tại
            1            // Trạng thái
        );
        int result = KhuVucSach1DAO.insert1(newKhuVuc);
        // Kiểm tra kết quả và thông báo thành công
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Thêm lô thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dispose();
             
   
            jpkvk.loadDataTable(jpkvk.listKVK);
           
        } else {
            JOptionPane.showMessageDialog(this, "Thêm lô không thành công!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    } else if (e.getSource() == btnCapNhat && Validation()) {
        String maPhieuText = txtMaPhieu.getText();
        String thoiGianText = txtThoiGian.getText();
        
        // Kiểm tra dữ liệu từ các ô nhập liệu
        if (maPhieuText.isEmpty() || thoiGianText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Kiểm tra tên khu vực có bị trùng không, nhưng bỏ qua nếu tên không thay đổi
//        if (dao.doesNameExist(maPhieuText) && !maPhieuText.equals(kvk.getMaPhieu())) {
//            JOptionPane.showMessageDialog(this, "Tên khu vực đã tồn tại. Vui lòng chọn tên khác.");
//            return;
//        }
        
        // Chuyển đổi chuỗi thời gian thành Timestamp
        Timestamp thoiGian;
        try {
            thoiGian = Timestamp.valueOf(thoiGianText);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Thời gian không hợp lệ. Vui lòng nhập đúng định dạng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
//        // Tạo đối tượng DTO mới
//        KhuVucSach1DTO updatedKhuVuc = new KhuVucSach1DTO(
//            kvk.getMaKhuVuc(), // Mã khu vực hiện tại (không thay đổi)
//            maPhieuText,      // Mã khu vực mới
//            thoiGian,         // Thời gian mới
//            kvk.getTrangThai() // Trạng thái hiện tại (không thay đổi)
//        );
//
//        // Cập nhật khu vực sách
//        jpkvk.kvkBUS.update(updatedKhuVuc);
    //    System.out.println("Khu vực sách đã được cập nhật: " + updatedKhuVuc);

        // Tải lại bảng dữ liệu
        jpkvk.loadDataTable(jpkvk.listKVK);
        System.out.println("Bảng dữ liệu đã được tải lại.");

        // Đóng cửa sổ
        dispose();
    }
}

}
