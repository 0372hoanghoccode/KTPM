package GUI.Panel;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Timestamp;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.PlainDocument;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;


import BUS.PhieuNhapBUS;
import BUS.SanPhamBUS;
import DAO.ChiTietLoHangDAO;
import DAO.KhuVucSach1DAO;
import DTO.ChiTietLoHangDTO;
import DTO.ChiTietPhieuNhapDTO;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import DTO.SanPhamDTO;
import GUI.Main;
import GUI.Component.ButtonCustom;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.PanelBorderRadius;
import GUI.Component.SelectForm;
import GUI.Dialog.KhuVucSach1Dialog;
import helper.Formater;
import helper.Validation;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class TaoPhieuNhap extends JPanel implements ItemListener, ActionListener {
      JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this); //gọi phương thức compoment tổ tiên có kiểu window của compoment hiện tại
    private SelectForm cbbLoHang;
    PanelBorderRadius left, right;
    JTable tablePhieuNhap, tableSanPham; //tablePhieuNhap ở left_bottom chứa sp của phiếu, tableSanPham chứa sp đang bán
    JScrollPane scrollTablePhieuNhap, scrollTableSanPham;
    JPanel contentCenter, left_top, content_btn, left_bottom;
    DefaultTableModel tblModel, tblModelSP; //table co san 
    ButtonCustom btnAddSp, btnDelete, btnNhapHang; //, btnImport
    InputForm txtMaphieu, txtNhanVien, txtMaSp, txtTenSp, txtDongia, txtMaISBN, txtSoLuongSPnhap ;
    JTextField txtTimKiem;
    JLabel  lbltongtien;
   KhuVucSach1DAO kvs1dao = new KhuVucSach1DAO();
    
     String[] arrmlh ; 
    Main m;
    Color BackgroundColor = new Color(211, 211, 211);

    SanPhamBUS spBUS = new SanPhamBUS();
    PhieuNhapBUS phieunhapBus = new PhieuNhapBUS();
    NhanVienDTO nvDto;

    ArrayList<SanPhamDTO> listSP = spBUS.getAllkhacam1(); // list ben kho 
    // ArrayList<SanPhamDTO> listSP_tmp = new ArrayList<>(); 
    ArrayList<ChiTietPhieuNhapDTO> chitietphieu;
    HashMap<Integer, ArrayList<SanPhamDTO>> chitietsanpham = new HashMap<>();
    int maphieunhap;
    int rowPhieuSelect = -1;
    private ButtonCustom btnAdd;

    public TaoPhieuNhap(NhanVienDTO nv, String type, Main m ){
        this.nvDto = nv;
        this.m = m;
        maphieunhap = phieunhapBus.getMPMAX() + 1;
        System.out.println(maphieunhap);
        chitietphieu = new ArrayList<>();
        initComponent(type);
        loadDataTalbeSanPham(listSP);
    }

    // public void initPadding(){
    // }

    public void initComponent(String type){
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        
        arrmlh = kvs1dao.getAll1();
        
        //Phieu Nhap
        tablePhieuNhap = new JTable();
        tablePhieuNhap.setBackground(new Color(245, 250, 250));
        scrollTablePhieuNhap = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã SP", "Tên sản phẩm", "Đơn giá", "Số lượng" , "Nhập Vào Lô"};
        tblModel.setColumnIdentifiers(header); //thiết lập tiêu đề cột, nhận một tham số là một mảng các chuỗi
        tablePhieuNhap.setModel(tblModel);
        scrollTablePhieuNhap.setViewportView(tablePhieuNhap);//thiết lập thành phần hiển thị cho viewport. Thành phần hiển thị là một component có thể cuộn (scrollable)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); //phương thức để định dạng văn bản, màu sắc, căn chỉnh và các thuộc tính hiển thị khác cho các ô trong bản
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tablePhieuNhap.getColumnModel();
        for (int i = 0; i < 5; i++) {
            if (i != 2) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        tablePhieuNhap.getColumnModel().getColumn(2).setPreferredWidth(300);
        tablePhieuNhap.setDefaultEditor(Object.class, null);
        tablePhieuNhap.setFocusable(false);
        scrollTablePhieuNhap.setViewportView(tablePhieuNhap);

        tablePhieuNhap.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = tablePhieuNhap.getSelectedRow();
                if (index != -1) {
                    tableSanPham.setSelectionMode(index);
                    setFormChiTietPhieu(chitietphieu.get(index));
                    rowPhieuSelect = index;
                    actionbtn("update");
                }
            }
        });

        //Table san pham
        tableSanPham = new JTable();
        scrollTableSanPham = new JScrollPane();
        tblModelSP = new DefaultTableModel();
        String[] headerSP = new String[]{"Mã SP", "Tên sản phẩm", "SL tồn"};
        tblModelSP.setColumnIdentifiers(headerSP);
        tableSanPham.setModel(tblModelSP);
        scrollTableSanPham.setViewportView(tableSanPham);
        tableSanPham.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(300);
        tableSanPham.setDefaultEditor(Object.class, null);
        tableSanPham.setFocusable(false);
        scrollTableSanPham.setViewportView(tableSanPham);

        tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = tableSanPham.getSelectedRow();
                if (index != -1) {
                    resetForm();
                   // setInfoSanPham(listSP.get(index));
                   setInfoSanPham1(index);
                    ChiTietPhieuNhapDTO ctp = checkTonTai();
                    if (ctp == null) {
                        actionbtn("add");
                    } else {
                        actionbtn("update");
                        setFormChiTietPhieu(ctp);
                    }
                }
            }
        });

        //content_CENTER (chứa hết tất cả left+right) 
        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1200, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(5, 5));
        this.add(contentCenter, BorderLayout.CENTER);

        //LEFT
        left = new PanelBorderRadius();
        left.setLayout(new BorderLayout(0, 5));
        left.setBackground(Color.white);

        
        left_top = new JPanel(); // Chứa tất cả phần ở phía trái trên cùng, chứa {content_top, content_btn}
        left_top.setLayout(new BorderLayout());
        left_top.setBorder(new EmptyBorder(5, 5, 10, 10));
        left_top.setOpaque(false);

        JPanel content_top, content_left, content_right, content_right_top; //content_top {content_left + content_right}
        content_top = new JPanel(new GridLayout(1, 2, 5, 5));
        content_top.setOpaque(false);

        content_left = new JPanel(new BorderLayout(5, 5));
        content_left.setOpaque(false);
        content_left.setPreferredSize(new Dimension(0, 300));

        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(100, 40));
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Tên sản phẩm, mã sản phẩm");
        txtTimKiem.putClientProperty("JTextField.showClearButton", true);
        txtTimKiem.putClientProperty("JTextField.leadingIcon", new FlatSVGIcon("./icon/search.svg"));

        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) { //chạy khi nhả phím trong tìm kiếm
                ArrayList<SanPhamDTO> rs = spBUS.searchTrongTaoPhieu(txtTimKiem.getText(), "Tất cả");
                loadDataTalbeSanPham(rs);
            }
        });

        content_left.add(txtTimKiem, BorderLayout.NORTH);
        content_left.add(scrollTableSanPham, BorderLayout.CENTER);

        content_right = new JPanel(new BorderLayout(5, 5));
        content_right.setOpaque(false);

        content_right_top = new JPanel(new BorderLayout());
        content_right_top.setPreferredSize(new Dimension(100, 250));
        txtTenSp = new InputForm("Tên sản phẩm");
        txtTenSp.setEditable(false);
        txtTenSp.setPreferredSize(new Dimension(100, 90));
        txtMaSp = new InputForm("Mã sản phẩm");
        txtMaSp.setEditable(false);
        txtMaISBN = new InputForm("Mã ISBN");
        txtMaISBN.setEditable(false);
        txtMaISBN.setVisible(false);
        // cái này dùng để nhập isbn dô khung txtMaISBN có thể search đc sp, nhưng disable ko dùng ròi
//        txtMaISBN.getTxtForm().addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(java.awt.event.KeyEvent evt) {
//                ArrayList<SanPhamDTO> rs = spBUS.search(txtMaISBN.getText(), "ISBN");
//                loadDataTalbeSanPham(rs);
//            //thêm load lại inputform
//            }
//        });

        txtDongia = new InputForm("Giá nhập");
        PlainDocument dongia = (PlainDocument) txtDongia.getTxtForm().getDocument();
        dongia.setDocumentFilter((new NumericDocumentFilter()));   //chỉ cho nhập số
        txtSoLuongSPnhap = new InputForm("Số lượng");
        PlainDocument soluong = (PlainDocument) txtSoLuongSPnhap.getTxtForm().getDocument();
        soluong.setDocumentFilter((new NumericDocumentFilter())); //chỉ cho nhập số

        JPanel merge1 = new JPanel(new BorderLayout());
        merge1.setPreferredSize(new Dimension(100, 50));
        merge1.add(txtMaSp, BorderLayout.CENTER);
        merge1.add(txtMaISBN, BorderLayout.EAST);

        JPanel merge2 = new JPanel(new GridLayout());
        merge2.setPreferredSize(new Dimension(100, 80));
        merge2.add(txtDongia);
        merge2.add(txtSoLuongSPnhap);

        content_right_top.add(txtTenSp, BorderLayout.NORTH);
        content_right_top.add(merge1, BorderLayout.CENTER);
        content_right_top.add(merge2, BorderLayout.SOUTH);
        content_right.add(content_right_top, BorderLayout.NORTH);

        content_top.add(content_left);
        content_top.add(content_right);
        left_top.add(content_top, BorderLayout.CENTER);

        //content_btn  -  4 nút ở left_top (South) 
        content_btn = new JPanel();
        content_btn.setPreferredSize(new Dimension(0, 47));
        content_btn.setLayout(new GridLayout(1, 4, 5, 5));
        content_btn.setBorder(new EmptyBorder(8, 5, 0, 10));
        content_btn.setOpaque(false);
        btnAddSp = new ButtonCustom("Thêm sản phẩm", "success", 14);
        // btnEditSP = new ButtonCustom("Sửa sản phẩm", "warning", 14);
        btnDelete = new ButtonCustom("Xoá sản phẩm", "danger", 14);
            // btnImport = new ButtonCustom("Nhập Excel", "excel", 14);
        btnAddSp.addActionListener(this);
        // btnEditSP.addActionListener(this);
        btnDelete.addActionListener(this);
            // btnImport.addActionListener(this);
        // btnEditSP.setEnabled(false);
        btnDelete.setEnabled(false);
        content_btn.add(btnAddSp);
            // content_btn.add(btnImport);
        // content_btn.add(btnEditSP);
        content_btn.add(btnDelete);
        left_top.add(content_btn, BorderLayout.SOUTH);

        //left_bottom này là danh sách nhập ở left phía nam
        left_bottom = new JPanel();
        left_bottom.setOpaque(false);
        left_bottom.setPreferredSize(new Dimension(0, 250));
        left_bottom.setBorder(new EmptyBorder(0, 5, 10, 10));
        BoxLayout boxly = new BoxLayout(left_bottom, BoxLayout.Y_AXIS);
        left_bottom.setLayout(boxly);
        left_bottom.add(scrollTablePhieuNhap);
        left.add(left_top, BorderLayout.CENTER);
        left.add(left_bottom, BorderLayout.SOUTH);

     right = new PanelBorderRadius();
right.setPreferredSize(new Dimension(320, 0));
right.setBorder(new EmptyBorder(5, 5, 5, 5));
right.setLayout(new BorderLayout());

// Phần Right Top
JPanel right_top = new JPanel(new GridLayout(4, 1, 0, 0));
right_top.setPreferredSize(new Dimension(300, 360));
right_top.setOpaque(false);

// Mã phiếu nhập
txtMaphieu = new InputForm("Mã phiếu nhập");
txtMaphieu.setText("PN" + maphieunhap);
txtMaphieu.setEditable(false);

// Nhân viên nhập
txtNhanVien = new InputForm("Nhân viên nhập");
txtNhanVien.setText(nvDto.getHOTEN());
txtNhanVien.setEditable(false);

// Lô Hàng ComboBox
cbbLoHang = new SelectForm("Lô Hàng", arrmlh);
cbbLoHang.setPreferredSize(new Dimension(240, 30));

JPanel comboBoxPanel = new JPanel();
comboBoxPanel.setLayout(new BorderLayout());
comboBoxPanel.setOpaque(false);
comboBoxPanel.setBorder(new EmptyBorder(5, 5, 0, 0));
comboBoxPanel.add(cbbLoHang, BorderLayout.CENTER);

// Nút Add sản phẩm
JPanel buttonPanel = new JPanel();
buttonPanel.setLayout(new BorderLayout());
buttonPanel.setOpaque(false);
buttonPanel.setBorder(new EmptyBorder(40, 5, 5, 5));
btnAdd = new ButtonCustom("+", "excel", 14);
btnAdd.setPreferredSize(new Dimension(40, 30));
buttonPanel.add(btnAdd, BorderLayout.WEST);

// Gộp ComboBox và Button thành một hàng ngang
JPanel kJPanelLeft = new JPanel();
kJPanelLeft.setLayout(new BoxLayout(kJPanelLeft, BoxLayout.X_AXIS));
kJPanelLeft.setOpaque(false);
kJPanelLeft.add(comboBoxPanel);
kJPanelLeft.add(buttonPanel);

// Add các thành phần vào Right Top
right_top.add(txtMaphieu);
right_top.add(txtNhanVien);
right_top.add(kJPanelLeft);

// Thêm Right Top vào panel chính
right.add(right_top, BorderLayout.NORTH);

// Phần Right Bottom
JPanel right_bottom = new JPanel(new GridLayout(2, 1));
right_bottom.setPreferredSize(new Dimension(300, 100));
right_bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
right_bottom.setOpaque(false);

// Tổng tiền
JPanel pn_tongtien = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
pn_tongtien.setOpaque(false);
JLabel lbltien = new JLabel("TỔNG TIỀN: ");
lbltien.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 18));
lbltien.setForeground(new Color(255, 51, 51));
lbltongtien = new JLabel("0đ");
lbltongtien.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 18));
pn_tongtien.add(lbltien);
pn_tongtien.add(lbltongtien);
right_bottom.add(pn_tongtien);

// Nút Nhập Hàng
btnNhapHang = new ButtonCustom("Nhập hàng", "excel", 14);
    btnNhapHang.addActionListener(this);
right_bottom.add(btnNhapHang);

// Thêm Right Bottom vào panel chính
right.add(right_bottom, BorderLayout.SOUTH);

// Phần Center (để trống cho logic sau này)
JPanel right_center = new JPanel();
right_center.setPreferredSize(new Dimension(100, 100));
right_center.setOpaque(false);
right.add(right_center, BorderLayout.CENTER);

// Thêm panel Right vào contentCenter
contentCenter.add(left, BorderLayout.CENTER);
contentCenter.add(right, BorderLayout.EAST);

// Sự kiện cho nút Add
btnAdd.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        KhuVucSach1 khuvuc = new KhuVucSach1(m);
        new KhuVucSach1Dialog(khuvuc, owner, "Thêm khu vực lô", true, "create");
         arrmlh = kvs1dao.getAll1();
        ;
          cbbLoHang.updateOptions(arrmlh); 
    }
});

// Sự kiện cho ComboBox Lô Hàng
cbbLoHang.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        resetForm();
        tableSanPham.setRowSelectionInterval(0, 0);
        tableSanPham.scrollRectToVisible(tableSanPham.getCellRect(0, 0, true));
    }
});
    }
    
  

    
    /********************************************************************************************************************************************************/
    public void actionbtn(String type) { //ẩn hiện button
        boolean val_1 = type.equals("add");
        boolean val_2 = type.equals("update");
        btnAddSp.setEnabled(val_1);
        // btnImport.setEnabled(val_1);
        // btnEditSP.setEnabled(val_2);
        btnDelete.setEnabled(val_2);
        content_btn.revalidate();
        content_btn.repaint();
    }
    

    public void setInfoSanPham(SanPhamDTO sp) { //set info vào inputform khi nhan ben tablesanpham
        this.txtMaSp.setText(Integer.toString(sp.getMSP()));
        this.txtTenSp.setText(sp.getTEN());
        // ArrayList<SanPhamDTO> spdto = spBUS.getAll();;
        // cbxDanhMuc.setArr(getThongTinSach(sp.getMSP()));
        
       // this.txtDongia.setText(Integer.toString(sp.getTIENX()));
    }

  public void setInfoSanPham1(int index) {
    // Lấy giá trị từ cột 0 (Mã sản phẩm)
    int columnMaSpIndex = tableSanPham.convertColumnIndexToModel(0); // Cột 0 theo mô hình
    Object maSpValue = tableSanPham.getValueAt(index, columnMaSpIndex);
    this.txtMaSp.setText(maSpValue.toString());

    // Lấy giá trị từ cột 1 (Tên sản phẩm)
    int columnTenSpIndex = tableSanPham.convertColumnIndexToModel(1); // Cột 1 theo mô hình
    Object tenSpValue = tableSanPham.getValueAt(index, columnTenSpIndex);
    this.txtTenSp.setText(tenSpValue.toString());

    // Nếu cần cập nhật thêm trường khác, bạn có thể thêm logic tương tự
    // Ví dụ: Giá sản phẩm hoặc danh mục (nếu có)
    // this.txtDongia.setText(sp.getTIENX().toString());
}

    // public ArrayList<SanPhamDTO> getChiTietSanPham() {
    //     // int ctsp = listSP_tmp.get(.getSelectedIndex()).getMSP();
    //     ArrayList<SanPhamDTO> result = new ArrayList<>();

    //     long isbn = Long.parseLong(txtMaISBN.getText());
    //     int soluong = Integer.parseInt(txtSoLuongSPnhap.getText());
    //     for (long i = isbn; i < isbn + soluong; i++) {
    //         result.add(new SanPhamDTO(Long.toString(i), phienbansp, maphieunhap, 0, 1));
    //     }
    //     return result;
    // }

    public void setFormChiTietPhieu(ChiTietPhieuNhapDTO phieu) { //set info vào inputform khi nhan ben tablephieunhap
        SanPhamDTO ctsp = spBUS.getByMaSP(phieu.getMSP());

        this.txtMaSp.setText(Integer.toString(ctsp.getMSP()));
        this.txtTenSp.setText(spBUS.getByMaSP(ctsp.getMSP()).getTEN());
        // this.cbxDanhMuc.setArr(getThongTinSach(pb.getDANHMUC()));
        // this.cbxDanhMuc.setSelectedIndex(spBUS.getIndexByMaPhienBan(spdto, phieu.getMaphienbansp()));
        this.txtDongia.setText(Integer.toString(phieu.getTIEN()));
        this.txtSoLuongSPnhap.setText(Integer.toString(phieu.getSL()));
    }
    
    public ChiTietPhieuNhapDTO getInfoChiTietPhieu() {
        int masp = Integer.parseInt(txtMaSp.getText());
        int gianhap = Integer.parseInt(txtDongia.getText());
        // ArrayList<SanPhamDTO> ctSP = getChiTietSanPham();
        // int soluong = ctSP.size();
        // chitietsanpham.put(masp, getChiTietSanPham());
        int soluong = Integer.parseInt(txtSoLuongSPnhap.getText());
         String selectedLotCode = (String) cbbLoHang.getSelectedItem();
          int lotCodeInt = Integer.parseInt(selectedLotCode);
        ChiTietPhieuNhapDTO ctphieu = new ChiTietPhieuNhapDTO(maphieunhap, masp, soluong, gianhap, lotCodeInt);
        return ctphieu;
    }


    public void loadDataTalbeSanPham(ArrayList<SanPhamDTO> result) {
        tblModelSP.setRowCount(0);
        int sl = 0 ; 
        for (SanPhamDTO sp : result) {
            try {
                sl = ChiTietLoHangDAO.getTotalQuantityByProduct(sp.getMSP());
            } catch (SQLException ex) {
                Logger.getLogger(TaoPhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            tblModelSP.addRow(new Object[]{sp.getMSP(), sp.getTEN(), sl});
        }
    }

    public void loadDataTableChiTietPhieu(ArrayList<ChiTietPhieuNhapDTO> ctPhieu) {
        tblModel.setRowCount(0);
        int size = ctPhieu.size();
        for (int i = 0; i < size; i++) {
            SanPhamDTO pb = spBUS.getByMaSP(ctPhieu.get(i).getMSP());
            tblModel.addRow(new Object[]{
                i + 1, pb.getMSP(), spBUS.getByMaSP(pb.getMSP()).getTEN(), 
                Formater.FormatVND(ctPhieu.get(i).getTIEN()), ctPhieu.get(i).getSL() , ctPhieu.get(i).getMLH()
            });
        }
        lbltongtien.setText(Formater.FormatVND(phieunhapBus.getTIEN(ctPhieu)));
    }

public boolean validateNhap() {
    int phuongthuc = 0; // Đảm bảo rằng biến này được sử dụng đúng cách trong hàm

    String selectedLotCode = (String) cbbLoHang.getSelectedItem();

    // Kiểm tra nếu mã sản phẩm không hợp lệ
    if (Validation.isEmpty(txtMaSp.getText())) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm", "Chọn sản phẩm", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    int maSp;
    try {
        maSp = Integer.parseInt(txtMaSp.getText());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Mã sản phẩm phải là số nguyên hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Kiểm tra nếu giá nhập để rỗng
    if (Validation.isEmpty(txtDongia.getText())) {
        JOptionPane.showMessageDialog(this, "Giá nhập không được để rỗng !", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    // Kiểm tra số lượng
    if (phuongthuc == 0) {
        if (Validation.isEmpty(txtSoLuongSPnhap.getText()) || !Validation.isNumber(txtSoLuongSPnhap.getText())) {
            JOptionPane.showMessageDialog(this, "Số lượng không được để rỗng và phải là số!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    // Kiểm tra xem sản phẩm đã tồn tại trong lô hàng chưa
    boolean productExistsInLot = phieunhapBus.checkProductInLot(selectedLotCode, maSp);
    if (productExistsInLot) {
        JOptionPane.showMessageDialog(this, "Sản phẩm đã nhập trong lô hàng này.", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    // Nếu tất cả các điều kiện đều hợp lệ
    return true;
}



    public void resetForm() {
        this.txtMaSp.setText("");
        this.txtTenSp.setText("");
        // String[] arr = {"Chọn danh muc"};
        // this.cbxDanhMuc.setArr(arr);
        this.txtDongia.setText("");
        this.txtSoLuongSPnhap.setText("");
        this.txtMaISBN.setText("");
    }

    public ChiTietPhieuNhapDTO checkTonTai() {
       
         String selectedLotCode = (String) cbbLoHang.getSelectedItem();
          int lotCodeInt = Integer.parseInt(selectedLotCode);
        ChiTietPhieuNhapDTO p = phieunhapBus.findCT(chitietphieu, Integer.parseInt(txtMaSp.getText()) , lotCodeInt); 
            //kiểm tra coi masp này có trong chitietphieu này chưa 
        return p;
    }

  public void addCtPhieu() {
    // Lấy thông tin chi tiết phiếu nhập từ form
    ChiTietPhieuNhapDTO ctphieu = getInfoChiTietPhieu();

    // Tìm sản phẩm trong danh sách chi tiết phiếu nhập dựa trên cả MSP và MLH
    ChiTietPhieuNhapDTO existingProduct = phieunhapBus.findCT(chitietphieu, ctphieu.getMSP(), ctphieu.getMLH());

    if (existingProduct == null) {
        // Nếu sản phẩm không tồn tại trong chi tiết phiếu, thêm sản phẩm vào danh sách
        chitietphieu.add(ctphieu);
        // Tải lại dữ liệu vào bảng để cập nhật danh sách sản phẩm
        loadDataTableChiTietPhieu(chitietphieu);
        // Đặt lại form để chuẩn bị cho thông tin tiếp theo
        resetForm();
    } else {
        // Nếu sản phẩm đã tồn tại trong chi tiết phiếu, thông báo cho người dùng và hỏi xem có muốn chỉnh sửa không
        int input = JOptionPane.showConfirmDialog(this,
                "Sản phẩm với mã sản phẩm và mã lô đã tồn tại trong phiếu.\nBạn có muốn chỉnh sửa không?",
                "Sản phẩm đã tồn tại",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
        
        if (input == JOptionPane.OK_OPTION) {
            // Nếu người dùng chọn "OK", gọi hàm để chỉnh sửa thông tin sản phẩm
            setFormChiTietPhieu(ctphieu);
        }
    }
}


    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'itemStateChanged'");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");

        Object source = e.getSource();
        if (source == btnAddSp && validateNhap()) {
            addCtPhieu();
            
        } else if (source == btnDelete) {
            int index = tablePhieuNhap.getSelectedRow();
            chitietphieu.remove(index);
          //  actionbtn("add");
            loadDataTableChiTietPhieu(chitietphieu);
            resetForm();
        } else if (source == btnNhapHang) {
            eventBtnNhapHang();
        } 
    }
    
public void eventBtnNhapHang() {
    if (chitietphieu.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào trong phiếu!", "Cảnh báo!", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn tạo phiếu nhập!", "Xác nhận tạo phiếu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
    if (input == JOptionPane.OK_OPTION) {
        int quantity = 0;
        int price = 0;
        int tongsoluong = 0 ; 
        int tongtien = 0 ; 
        String selectedLotCode = (String) cbbLoHang.getSelectedItem();
        System.out.println("Mã combobox là: " + selectedLotCode);
        
        boolean allProductsUpdated = true;
        
        for (ChiTietPhieuNhapDTO product : chitietphieu) {
            int productCode = product.getMSP();
            quantity = product.getSL();
            price = product.getTIEN();
           // tongsoluong += quantity ;
            tongtien =price*quantity ; 
            
            System.out.println("Sản phẩm mã: " + productCode);
            System.out.println("Số lượng sản phẩm: " + quantity);
            System.out.println("Giá nhập sản phẩm: " + price);
            
            ChiTietLoHangDTO productDetail = new ChiTietLoHangDTO(product.getMLH()+"", productCode, price, quantity); // product.getMLH() is a String

            boolean addResult = phieunhapBus.addProductToLot(productDetail);

            if (addResult) {
                String lotCode = product.getMLH()+""; // Assuming MLH is a String
                KhuVucSach1DAO hehe = new KhuVucSach1DAO();
                boolean updateResult = hehe.updateLot(lotCode, quantity, tongtien);
                
                if (!updateResult) {
                    allProductsUpdated = false;
                    System.out.println("Failed to update lot with MLH: " + lotCode);
                    break;
                }
            } else {
                allProductsUpdated = false;
                System.out.println("Failed to add product to lot: Product Code = " + productCode);
                break;
            }
        }
        
        if (allProductsUpdated) {
            long now = System.currentTimeMillis();
            Timestamp currenTime = new Timestamp(now);
            PhieuNhapDTO pn = new PhieuNhapDTO(maphieunhap, nvDto.getMNV(), currenTime, phieunhapBus.getTIEN(chitietphieu), 1);
            boolean result = phieunhapBus.add(pn, chitietphieu, chitietsanpham);

            if (result) {
                JOptionPane.showMessageDialog(this, "Nhập hàng thành công!");
                PhieuNhap pnlPhieu = new PhieuNhap(m, nvDto);
                m.setPanel(pnlPhieu);
            } else {
                JOptionPane.showMessageDialog(this, "Nhập hàng không thành công!", "Cảnh báo!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm không thành công!", "Cảnh báo!", JOptionPane.ERROR_MESSAGE);
        }
    }
}


}



/*
getInfoChiTietPhieu
getChiTietSanPham

*/