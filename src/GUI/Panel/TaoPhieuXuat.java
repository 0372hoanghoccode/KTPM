package GUI.Panel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;
import java.awt.*;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.PlainDocument;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import GUI.Panel.KhachHang ; 
import GUI.Dialog.KhachHangDialog;
import BUS.KhachHangBUS;
import BUS.MaKhuyenMaiBUS;
import BUS.PhieuXuatBUS;
import BUS.SanPhamBUS;
import DAO.ChiTietLoHangDAO;
import DAO.ChiTietPhieuXuatDAO;
import DAO.KhachHangDAO;
import DAO.NhanVienDAO;
import DAO.PhieuXuatDAO;
import DAO.SanPhamDAO;
import DTO.ChiTietLoHangDTO;
import DTO.ChiTietMaKhuyenMaiDTO;
import DTO.ChiTietPhieuDTO;
import DTO.ChiTietPhieuXuatDTO;
import DTO.KhachHangDTO;
import DTO.MaKhuyenMaiDTO;
import DTO.NhanVienDTO;
import DTO.PhieuXuatDTO;
import DTO.SanPhamDTO;
import DTO.TaiKhoanDTO;
import GUI.Main;
import GUI.Component.ButtonCustom;
import GUI.Component.InputForm;
import GUI.Component.Notification;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.PanelBorderRadius;
import GUI.Component.SelectForm;
import GUI.Dialog.KhachHangDialog;
import GUI.Dialog.ListKhachHang;
import helper.Formater;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class TaoPhieuXuat extends JPanel {
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this); //gọi phương thức compoment tổ tiên có kiểu window của compoment hiện tại
    // kiểu như cái listKhachHang thì cho owner dô sẽ gọi đc cái jframe của listkhachhang
    PanelBorderRadius right, left;
    JPanel  contentCenter, left_top, content_btn, left_bottom; //là cái main cữ
    JTable tablePhieuXuat, tableSanPham;
    JScrollPane scrollTablePhieuNhap, scrollTableSanPham;
    DefaultTableModel tblModel, tblModelSP; //table co san 
    ButtonCustom btnAddSp, btnDelete, btnNhapHang,btnaddKH;
    InputForm txtMaphieu, txtNhanVien, txtTenSp, txtMaSp, txtMaISBN, txtSoLuongSPxuat, txtMaGiamGia, txtGiaGiam;
    SelectForm cbxMaKM; 
    JTextField txtTimKiem;
    Color BackgroundColor = new Color(211, 211, 211);
    
    int sum; //do ctpxuất ko có sẵn tính tiền 
    int maphieu;
    int masp;
    int manv;
    int makh = -1;
    String type;

    // ArrayList<SanPhamDTO> ctpb;
    SanPhamBUS spBUS = new SanPhamBUS();
    MaKhuyenMaiBUS mkmBUS = new MaKhuyenMaiBUS();
    PhieuXuatBUS phieuXuatBUS = new PhieuXuatBUS();
    // SanPhamBUS chiTietSanPhamBUS = new SanPhamBUS();
    KhachHangBUS khachHangBUS = new KhachHangBUS();
    ArrayList<ChiTietPhieuDTO> chitietphieu = new ArrayList<>();
    ArrayList<DTO.SanPhamDTO> listSP = spBUS.getAll();
    ArrayList<SanPhamDTO> listSPlonhon0 = SanPhamDAO.filterProductsWithPositiveQuantity(listSP);
    ArrayList<DTO.ChiTietMaKhuyenMaiDTO> listctMKM = new ArrayList<>();

    TaiKhoanDTO tk;
    private JLabel lbltongtien;
    private JTextField txtKh;
    private Main mainChinh;
    // private ButtonCustom btnQuayLai; //chua use
    private InputForm txtGiaXuat;

    public TaoPhieuXuat(Main mainChinh, TaiKhoanDTO tk, String type) {
        this.mainChinh = mainChinh;
        this.tk = tk;
        this.type = type;
        maphieu = phieuXuatBUS.getMPMAX() + 1;
      //  System.out.print("mã lớn nhất +1 nè : " + maphieu);
        initComponent(type);
        loadDataTableSanPham(listSPlonhon0);
    }

    private void initComponent(String type) {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Phiếu xuất
        tablePhieuXuat = new JTable();
        scrollTablePhieuNhap = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã SP", "Tên SP", "Đơn giá", "Số lượng" , "Giá Giảm" , "Giá thanh toán"};
        tblModel.setColumnIdentifiers(header);
        tablePhieuXuat.setModel(tblModel);
        scrollTablePhieuNhap.setViewportView(tablePhieuXuat);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tablePhieuXuat.getColumnModel();
        for (int i = 0; i < 5; i++) {
            if (i != 2) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        tablePhieuXuat.getColumnModel().getColumn(2).setPreferredWidth(200);
        tablePhieuXuat.setFocusable(false);
        tablePhieuXuat.setDefaultEditor(Object.class, null);
        scrollTablePhieuNhap.setViewportView(tablePhieuXuat);

        tablePhieuXuat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = tablePhieuXuat.getSelectedRow();
                if (index != -1) {
                    tableSanPham.setSelectionMode(index);
                    setFormChiTietPhieu(chitietphieu.get(index));
                    // ChiTietPhieuDTO ctphieu = chitietphieu.get(index);
                    // SanPhamDTO ctspSell = spBUS.getByMaSP(ctphieu.getMSP());
                    // setInfoSanPham(ctspSell);
                    actionbtn("update");
                }
            }
        });

        // Table sản phẩm
        tableSanPham = new JTable();
        tableSanPham.setBackground(new Color(245, 250, 250));
        scrollTableSanPham = new JScrollPane();
        tblModelSP = new DefaultTableModel();
        String[] headerSP = new String[]{"Mã SP", "Tên sản phẩm", "Số lượng tồn"};
        tblModelSP.setColumnIdentifiers(headerSP);
        tableSanPham.setModel(tblModelSP);
        scrollTableSanPham.setViewportView(tableSanPham);
        tableSanPham.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(300);
        tableSanPham.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableSanPham.setFocusable(false);
        scrollTableSanPham.setViewportView(tableSanPham);
       tableSanPham.addMouseListener(new MouseAdapter() {
@Override
public void mousePressed(MouseEvent e) {
    int index = tableSanPham.getSelectedRow();
    if (index != -1) {
        int columnMaIndex = tableSanPham.convertColumnIndexToModel(0);
        String maValueStr = tableSanPham.getValueAt(index, columnMaIndex).toString();
       int maValue = Integer.parseInt(maValueStr);

        // Tìm sản phẩm trong danh sách có mã trùng với maValue
        SanPhamDTO selectedSanPham = listSPlonhon0.stream()
            .filter(sp -> sp.getMSP()== (maValue)) // So sánh mã sản phẩm
            .findFirst() // Lấy sản phẩm đầu tiên tìm thấy
            .orElse(null); // Nếu không có, trả về null

        if (selectedSanPham != null) {
            resetForm();
            setInfoSanPham(selectedSanPham);
            actionbtn(checkTonTai() ? "update" : "add");
        } else {
            System.out.println("Không tìm thấy sản phẩm có mã: " + maValue);
        }
    }
}

});

        //content_CENTER (chứa hết tất cả left+right) 
        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
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

        JPanel content_top, content_left, content_right, content_right_top; //content_top {content_left + content_right} -> trong left_top
        content_top = new JPanel(new GridLayout(1, 2, 5, 5));
        content_top.setOpaque(false);

        content_left = new JPanel(new BorderLayout(5, 5));
        content_left.setOpaque(false);
        content_left.setPreferredSize(new Dimension(0, 300));

        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(100, 40));
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Tên sản phẩm , mã sản phẩm ");
        txtTimKiem.putClientProperty("JTextField.showClearButton", true);
        txtTimKiem.putClientProperty("JTextField.leadingIcon", new FlatSVGIcon("./icon/search.svg"));

        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) { //chạy khi nhả phím trong tìm kiếm
                ArrayList<SanPhamDTO> rs = spBUS.searchTrongTaoPhieu(txtTimKiem.getText(), "Tất cả");
                 ArrayList<SanPhamDTO> sanphamtimkiemlonhon0 = SanPhamDAO.filterProductsWithPositiveQuantity(rs);
                loadDataTableSanPham(sanphamtimkiemlonhon0);
            }
        });

        content_left.add(txtTimKiem, BorderLayout.NORTH);
        content_left.add(scrollTableSanPham, BorderLayout.CENTER);

        content_right = new JPanel(new BorderLayout(5, 5));
        content_right.setOpaque(false);
        // content_right.setBackground(Color.WHITE);

        content_right_top = new JPanel(new BorderLayout());
        content_right_top.setPreferredSize(new Dimension(100, 335));
        txtTenSp = new InputForm("Tên sản phẩm");
         txtTenSp.setDisable();
        txtTenSp.setEditable(false);
        txtTenSp.setPreferredSize(new Dimension(100, 90));
        txtMaSp = new InputForm("Mã sản phẩm");
         txtMaSp.setDisable();
        txtMaSp.setEditable(false);
        txtMaISBN = new InputForm("Mã ISBN");
         txtMaISBN.setEditable(false);
        txtMaISBN.setVisible(false); // Ẩn thành phần InputForm

        txtGiaXuat = new InputForm("Giá xuất");
        txtGiaXuat.setDisable();
        txtGiaXuat.setEditable(false);
       
        txtSoLuongSPxuat = new InputForm("Số lượng");
        PlainDocument soluong = (PlainDocument) txtSoLuongSPxuat.getTxtForm().getDocument();
        soluong.setDocumentFilter((new NumericDocumentFilter())); //chỉ cho nhập số
         txtMaGiamGia = new InputForm("Mã giảm giá");
        String[] maGiamGia = {"Chọn"};
        cbxMaKM = new SelectForm("Mã giảm giá", maGiamGia);
        txtGiaGiam = new InputForm("Giá giảm");
          txtGiaGiam.setDisable();
        txtGiaGiam.setText("");
        txtGiaGiam.setEditable(false);
        cbxMaKM.cbb.addItemListener((ItemListener) new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int index = cbxMaKM.cbb.getSelectedIndex();
                if(index != 0)
                {
                    double giaxuat = Integer.parseInt(txtGiaXuat.getText());
                    double phantramgiam = (double) listctMKM.get(index - 1).getPTG();
                    int giagiam = (int) (giaxuat * (1 - phantramgiam/100));
                    txtGiaGiam.setText(Integer.toString(giagiam));
                }
            }
            
        });
         txtMaGiamGia.getTxtForm().addKeyListener(new KeyAdapter() {
                 @Override
                 public void keyReleased(java.awt.event.KeyEvent evt) {
                     ArrayList<MaKhuyenMaiDTO> rs = mkmBUS.search(txtMaGiamGia.getText());
           
                 }
             });

            
        JPanel merge1 = new JPanel(new BorderLayout());
        merge1.setPreferredSize(new Dimension(100, 50));
        merge1.add(txtMaSp, BorderLayout.CENTER);
        merge1.add(txtMaISBN, BorderLayout.EAST);

        JPanel merge2 = new JPanel(new GridLayout(2,2));
        merge2.setPreferredSize(new Dimension(100, 160));
        merge2.add(txtGiaXuat,BorderLayout.CENTER);
        merge2.add(txtSoLuongSPxuat,BorderLayout.EAST);
        //  merge2.add(txtMaGiamGia);
        merge2.add(cbxMaKM,BorderLayout.WEST);
        merge2.add(txtGiaGiam, BorderLayout.CENTER);

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

        btnAddSp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInfo()) {
                    addCtPhieu();
                    //thông báo dạng popup dùng của Notification trong Compoment của Gui
                    Notification thongbaoNoi = new Notification(mainChinh,  Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Thêm sản phẩm thành công!");
                    thongbaoNoi.showNotification();
                    loadDataTableChiTietPhieu(chitietphieu);
                    actionbtn("update");
                }

            }
            
        });

        // btnEditSP.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         int index = tablePhieuXuat.getSelectedRow();
        //         if (index < 0) {
        //             JOptionPane.showMessageDialog(null, "Vui lòng chọn cấu hình cần chỉnh");
        //         } else {
        //             chitietphieu.get(index).setSL(Integer.parseInt(txtSoLuongSPxuat.getText()));
        //             if(!txtGiaGiam.getText().equals(" ")) 
        //                 chitietphieu.get(index).setTIEN(Integer.parseInt(txtGiaGiam.getText()));
        //             else
        //                 chitietphieu.get(index).setTIEN(Integer.parseInt(txtGiaXuat.getText()));                    
        //             loadDataTableChiTietPhieu(chitietphieu);
        //         }
        //     }
        // });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tablePhieuXuat.getSelectedRow();
                if (index < 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn cấu hình cần xóa");
                } else {
                    chitietphieu.remove(index);
                    actionbtn("add");
                    loadDataTableChiTietPhieu(chitietphieu);
                    resetForm();
                }
            }
        });

        // btnEditSP.setEnabled(false);
        btnDelete.setEnabled(false);
        content_btn.add(btnAddSp);
        // content_btn.add(btnEditSP);
        content_btn.add(btnDelete);
        left_top.add(content_btn, BorderLayout.SOUTH);

        //left_bottom này là danh sách xuất ở left phía nam, chứa tablelistnhap
        left_bottom = new JPanel();
        left_bottom.setOpaque(false);
        left_bottom.setPreferredSize(new Dimension(0, 250));
        left_bottom.setBorder(new EmptyBorder(0, 5, 10, 10));
        BoxLayout boxly = new BoxLayout(left_bottom, BoxLayout.Y_AXIS);
        left_bottom.setLayout(boxly);
        left_bottom.add(scrollTablePhieuNhap);
        left.add(left_top, BorderLayout.CENTER);
        left.add(left_bottom, BorderLayout.SOUTH);

        // RIGHT 
        right = new PanelBorderRadius();
        right.setPreferredSize(new Dimension(320, 0));
        right.setBorder(new EmptyBorder(5, 5, 5, 5));
        right.setLayout(new BorderLayout());

        JPanel right_top, right_center, right_bottom, pn_tongtien;
        right_top = new JPanel(new GridLayout(2, 1, 0, 0));
        right_top.setPreferredSize(new Dimension(300, 180));
        txtMaphieu = new InputForm("Mã phiếu xuất");
        txtMaphieu.setEditable(false);
        txtMaphieu.setDisable();
        txtNhanVien = new InputForm("Nhân viên xuất");
      //  txtNhanVien.setEditable(false);
        txtNhanVien.setDisable();
        //maphieu = PhieuXuatDAO.getInstance().getAutoIncrement();
        manv = tk.getMNV();
        txtMaphieu.setText("PX" + maphieu);
        
        NhanVienDTO nhanvien = NhanVienDAO.getInstance().selectById(tk.getMNV() + "");
        txtNhanVien.setText(nhanvien.getHOTEN());
        right_top.add(txtMaphieu);
        right_top.add(txtNhanVien);

        right_center = new JPanel(new BorderLayout());
        right_center.setOpaque(false);

        JPanel khachJPanel = new JPanel(new BorderLayout());
        khachJPanel.setPreferredSize(new Dimension(0, 40));
        khachJPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        khachJPanel.setOpaque(false);
        JPanel kJPanelLeft = new JPanel(new GridLayout(1, 2));
        kJPanelLeft.setOpaque(false);
        kJPanelLeft.setPreferredSize(new Dimension(80, 0));
        btnaddKH = new ButtonCustom("+", "excel", 14);
        
        ButtonCustom btnKh = new ButtonCustom("Chọn khách hàng", "success", 14);
        kJPanelLeft.add(btnKh);
        kJPanelLeft.add(btnaddKH,BorderLayout.WEST);
        
        btnKh.addActionListener((ActionEvent e) -> {
            new ListKhachHang(TaoPhieuXuat.this, owner, "Chọn khách hàng", true);
        });
         btnaddKH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int maKhachHangBanDau = KhachHangDAO.getMaxMaKhachHang();
               // System.out.print("Mã khách hàng ban đầu là : " + maKhachHangBanDau);
                KhachHang KhachHang = new KhachHang(mainChinh);
                new KhachHangDialog(KhachHang, owner, "Thêm khách hàng", true,"create");
                 int maKhachHangSauKhiNhanNut = KhachHangDAO.getMaxMaKhachHang();
                 //    System.out.print("Mã khách hàng sau khi nhấn nút là : " + maKhachHangSauKhiNhanNut);
                 if (maKhachHangBanDau!=maKhachHangSauKhiNhanNut)
                     setKhachHang(maKhachHangSauKhiNhanNut);
                 else
                     return;
                
            }
        });

        txtKh = new JTextField("");
        txtKh.setEditable(false);
        khachJPanel.add(kJPanelLeft, BorderLayout.EAST);
        khachJPanel.add(txtKh, BorderLayout.CENTER);
        JPanel khPanel = new JPanel(new GridLayout(3, 1, 5, 0));
        khPanel.setBackground(Color.WHITE);
        khPanel.setPreferredSize(new Dimension(0, 120));
      JLabel khachKhangJLabel = new JLabel("Khách hàng");
//khachKhangJLabel.setFocusable(false); // Không cho phép lấy focus
//khachKhangJLabel.setEnabled(false); // Không cho tương tác

        khachKhangJLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
       

        khPanel.add(khachKhangJLabel);
        khPanel.add(khachJPanel);
        right_center.add(khPanel, BorderLayout.NORTH);

        right_bottom = new JPanel(new GridLayout(2, 1));
        right_bottom.setPreferredSize(new Dimension(300, 100));
        right_bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
        right_bottom.setOpaque(false);

        pn_tongtien = new JPanel(new FlowLayout(1, 20, 0));
        pn_tongtien.setOpaque(false);
        JLabel lbltien = new JLabel("TỔNG TIỀN: ");
        lbltien.setFont(new Font(FlatRobotoFont.FAMILY, 1, 18));
        lbltongtien = new JLabel("0đ");
        lbltongtien.setFont(new Font(FlatRobotoFont.FAMILY, 1, 18));
        lbltien.setForeground(new Color(255, 51, 51));
        pn_tongtien.add(lbltien);
        pn_tongtien.add(lbltongtien);
        right_bottom.add(pn_tongtien);

        btnNhapHang = new ButtonCustom("Xuất hàng", "excel", 14);
        btnNhapHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eventBtnNhapHang();
                } catch (SQLException ex) {
                    Logger.getLogger(TaoPhieuXuat.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        right_bottom.add(btnNhapHang);

        right.add(right_top, BorderLayout.NORTH);
        right.add(right_center, BorderLayout.CENTER);
        right.add(right_bottom, BorderLayout.SOUTH);

        contentCenter.add(left, BorderLayout.CENTER);
        contentCenter.add(right, BorderLayout.EAST);
        // actionbtn("add");
    }

    public void actionbtn(String type) {
        boolean val_1 = type.equals("add");
        boolean val_2 = type.equals("update");
        btnAddSp.setEnabled(val_1);
        // btnEditSP.setEnabled(val_2);
        btnDelete.setEnabled(val_2);
        content_btn.revalidate();
        content_btn.repaint();
    }
    public void resetForm() {
        this.txtTenSp.setText("");
        this.txtMaSp.setText("");
       // this.txtMaISBN.setText("");
      //  this.txtGiaXuat.setText("");
        this.txtSoLuongSPxuat.setText("");
        // this.txtMaGiamGia.setText("");
//        this.txtGiaGiam.setText(" ");
    }

    public String[] getMaGiamGiaTable(int masp) {
        listctMKM = mkmBUS.Getctmkm(masp);
        int size = listctMKM.size();
        ArrayList<String> arr = new ArrayList<>();
         for (ChiTietMaKhuyenMaiDTO item : listctMKM) {
        // Kiểm tra và thêm mã giảm giá dạng chuỗi
        if (!validateSelectDate(item)) {
            arr.add(String.valueOf(item.getMKM())); // Chuyển mã giảm giá thành chuỗi
        }
    }
        String[] tmp = new String[arr.size()];
        for (int i = 0; i < tmp.length; i++) tmp[i] = arr.get(i);
        tmp = Stream.concat(Stream.of("Chọn"), Arrays.stream(tmp)).toArray(String[]::new);
        return tmp;
    }

    public boolean validateSelectDate(DTO.ChiTietMaKhuyenMaiDTO tmp) {
        MaKhuyenMaiDTO a = mkmBUS.selectMkm(tmp.getMKM());
        Date time_start = a.getTGBD();
        Date time_end = a.getTGKT();
        Date current_date = new Date();
        if (time_start != null && time_start.after(current_date)) {
            return false;
        }
        if (time_end != null && time_end.after(current_date)) {
            return false;
        }
        if (time_start != null && time_end != null && time_start.after(time_end)) {
            return false;
        }
        return true;
    }

    public void setInfoSanPham(SanPhamDTO sp) {
        masp = sp.getMSP();
        this.txtMaSp.setText(Integer.toString(sp.getMSP()));
        this.txtTenSp.setText(sp.getTEN());
          ChiTietLoHangDAO chiTietLoHangDAO = new ChiTietLoHangDAO();
          ArrayList<ChiTietLoHangDTO> loHangList = chiTietLoHangDAO.findLohangByMSP(sp.getMSP());
        
        // Tìm lô hàng có mã nhỏ nhất
        ChiTietLoHangDTO minLohang = findMinLohangWithValidQuantity(masp);
          this.txtGiaXuat.setText(sp.getTIENX()+"");
       cbxMaKM.setArr(getMaGiamGiaTable(sp.getMSP()));
        
    }

    

    public void setFormChiTietPhieu(ChiTietPhieuDTO phieu) { //set info vào inputform khi nhan ben tablephieunhap
        SanPhamDTO ctsp = spBUS.getByMaSP(phieu.getMSP());
         ChiTietMaKhuyenMaiDTO ctmkm = mkmBUS.findCT(listctMKM, ctsp.getMSP());
        this.txtMaSp.setText(Integer.toString(ctsp.getMSP()));
        this.txtTenSp.setText(spBUS.getByMaSP(ctsp.getMSP()).getTEN());
                  ChiTietLoHangDAO chiTietLoHangDAO = new ChiTietLoHangDAO();
          ArrayList<ChiTietLoHangDTO> loHangList = chiTietLoHangDAO.findLohangByMSP(phieu.getMSP());
        
        // Tìm lô hàng có mã nhỏ nhất
        ChiTietLoHangDTO minLohang = findMinLohangWithValidQuantity(ctsp.getMSP());
           this.txtGiaXuat.setText(ctsp.getTIENX()+"");
         //  System.out.print("hello nè " + ctsp.getTIENX());
        this.txtSoLuongSPxuat.setText(Integer.toString(phieu.getSL()));
        cbxMaKM.setArr(getMaGiamGiaTable(ctsp.getMSP()));
    }





  public ChiTietLoHangDTO findMinLohangWithValidQuantity(int maSP) {
    // Lấy danh sách các chi tiết lô hàng từ DAO
    ArrayList<ChiTietLoHangDTO> loHangList = new ChiTietLoHangDAO().findLohangByMSP(maSP);
    
    // Khởi tạo biến để lưu đối tượng lô hàng nhỏ nhất
    ChiTietLoHangDTO minLohang = null;
    int minCode = Integer.MAX_VALUE; // Giá trị lớn nhất để tìm mã nhỏ nhất
    
    // Duyệt qua danh sách các chi tiết lô hàng
    for (ChiTietLoHangDTO loHang : loHangList) {
        String mlh = loHang.getMLH(); // Mã lô hàng
        int soLuong = loHang.getSoLuong(); // Số lượng lô hàng
        
        // Kiểm tra số lượng lô hàng
        if (soLuong > 0) {
            try {
                int currentCode = Integer.parseInt(mlh); // Chuyển đổi mã lô hàng từ String sang int
                
                // Nếu chưa tìm thấy lô hàng hợp lệ nào hoặc mã hiện tại nhỏ hơn mã đã tìm thấy
                if (minLohang == null || currentCode < minCode) {
                    minCode = currentCode;
                    minLohang = loHang; // Cập nhật đối tượng lô hàng nhỏ nhất
                }
            } catch (NumberFormatException e) {
                // Xử lý trường hợp mã lô hàng không phải là số hợp lệ
                System.err.println("Mã lô hàng không phải là số hợp lệ: " + mlh);
            }
        }
    }
    
    // Trả về đối tượng lô hàng nhỏ nhất hoặc null nếu không tìm thấy lô hàng hợp lệ
    return minLohang;
}



// Phương pháp loadDataTableSanPham đã được điều chỉnh
public void loadDataTableSanPham(ArrayList<SanPhamDTO> result) {
    tblModelSP.setRowCount(0); // Xóa tất cả các hàng hiện tại trong bảng
    int soluong ; 
    ChiTietLoHangDAO chiTietLoHangDAO = new ChiTietLoHangDAO();
    
    for (SanPhamDTO sp : result) {
      //  System.out.print(sp.getMSP());
              ChiTietLoHangDTO string= findMinLohangWithValidQuantity(sp.getMSP());
               if (string == null)
                    soluong = 0 ; 
               else 
                    soluong = string.getSoLuong();
            tblModelSP.addRow(new Object[]{
                sp.getMSP(), 
                sp.getTEN(), 
               soluong  // Số lượng trong lô hợp lệ
            });
            // Cập nhật giá xuất
//            if (string != null )
//              this.txtGiaXuat.setText(string.getGiaNhap() * 2 + "");
//            else {
//                this.txtGiaXuat.setText( "");
//            }
        
    }
}

    public void loadDataTableChiTietPhieu(ArrayList<ChiTietPhieuDTO> ctPhieu) {
        tblModel.setRowCount(0);
        int size = ctPhieu.size();
        sum = 0;
//try {
//    // Kiểm tra nếu chuỗi giá xuất không rỗng
//    if (!giaXuatStr.isEmpty()) {
//        giaXuat = Integer.parseInt(giaXuatStr); // Chuyển đổi chuỗi thành số nguyên
//    }
//
//    // Kiểm tra nếu chuỗi giá giảm không rỗng
//    if (!giaGiamStr.isEmpty()) {
//        giaGiam = Integer.parseInt(giaGiamStr); // Chuyển đổi chuỗi thành số nguyên
//    }
//
//            // Thực hiện phép toán
//          giathanhtoan = giaXuat - giaGiam;
//
//} catch (NumberFormatException e) {
//    // Xử lý lỗi khi chuyển đổi chuỗi thành số nguyên
//    System.out.println("Lỗi khi chuyển đổi chuỗi thành số nguyên: " + e.getMessage());
//    // Có thể hiển thị thông báo lỗi cho người dùng nếu cần
//} 

for (int i = 0; i < size; i++) {
    SanPhamDTO phienban = spBUS.getByMaSP(ctPhieu.get(i).getMSP());
   
    sum += ctPhieu.get(i).getGiaThanhToan() * ctPhieu.get(i).getSL();
    
    tblModel.addRow(new Object[]{
        i + 1, phienban.getMSP(), phienban.getTEN(), 
        Formater.FormatVND(ctPhieu.get(i).getTIEN()), ctPhieu.get(i).getSL(), ctPhieu.get(i).getGiaGiam(), Formater.FormatVND(chitietphieu.get(i).getGiaThanhToan())
    });
}

lbltongtien.setText(Formater.FormatVND(sum));
    }

    

    public boolean checkTonTai() {
        ChiTietPhieuDTO p = phieuXuatBUS.findCT(chitietphieu, Integer.parseInt(txtMaSp.getText())); 
            //kiểm tra coi masp này có trong chitietphieu này chưa 
        
        return p != null;
    }

  public boolean checkInfo() {
    boolean check = true;
    int index = tableSanPham.getSelectedRow();  // Giả sử tableSanPham là bảng sản phẩm đã chọn
    int availableQuantity = 0;  // Khai báo biến lưu số lượng có sẵn
   // System.out.println("Hello mã " + txtMaSp.getText());
    String text = txtMaSp.getText(); // Lấy giá trị từ trường văn bản
    

    if (txtMaSp.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
        check = false;
    } else {
      int number = Integer.parseInt(text); // Chuyển đổi giá trị thành kiểu int
            ChiTietLoHangDAO chiTietLoHangDAO = new ChiTietLoHangDAO();
            ArrayList<ChiTietLoHangDTO> loHangList = chiTietLoHangDAO.findLohangByMSP(Integer.parseInt(txtMaSp.getText()));
            ChiTietLoHangDTO minLohang = findMinLohangWithValidQuantity(number);
//            for (ChiTietLoHangDTO loHang : loHangList) {
//                System.out.println(loHang.getMLH());
//                  System.out.println(loHang.getMSP());
//                    System.out.println(loHang.getSoLuong());
//        }
            
            if (minLohang != null) {
                availableQuantity = minLohang.getSoLuong();
              //  System.out.print("hello " + availableQuantity);
            }

           if (txtSoLuongSPxuat.getText().trim().isEmpty()) {
    JOptionPane.showMessageDialog(null, "Số lượng không được để rỗng!", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
    check = false;
} else {
    try {
        int exportQuantity = Integer.parseInt(txtSoLuongSPxuat.getText());
        if (exportQuantity <= 0) {
            JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            check = false;
        } else if (exportQuantity > availableQuantity) {
            JOptionPane.showMessageDialog(null, "Số lượng không được lớn hơn số lượng hiện có!", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            check = false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Số lượng xuất phải là một số hợp lệ!", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
        check = false;
    }
}

        
    }

    return check;
}


   public void addCtPhieu() { 
    int masp = Integer.parseInt(txtMaSp.getText());
    int giaxuat = Integer.parseInt(txtGiaXuat.getText());
    int index = cbxMaKM.cbb.getSelectedIndex();
    int selectedItem; // Lưu mã giảm giá dưới dạng int

    int giagiam;
    int giaThanhToan;

    if (index != 0) {
        selectedItem = listctMKM.get(index - 1).getMKM(); // Lấy mã giảm giá
        double phantramgiam = (double) listctMKM.get(index - 1).getPTG();
        giagiam = (int) (giaxuat * phantramgiam / 100);
        giaThanhToan = (int) (giaxuat - giagiam);
    } else {
        selectedItem = 0; // Không có mã giảm giá
        giagiam = 0;
        giaThanhToan = giaxuat;
    }

    int soluong = Integer.parseInt(txtSoLuongSPxuat.getText());

    // Tạo đối tượng ChiTietPhieuDTO với selectedItem là int
    ChiTietPhieuDTO ctphieu = new ChiTietPhieuDTO(maphieu, masp, soluong, giaxuat, giagiam, giaThanhToan, selectedItem);

    // Kiểm tra nếu sản phẩm đã tồn tại
    ChiTietPhieuDTO p = phieuXuatBUS.findCT(chitietphieu, ctphieu.getMSP());
    if (p == null) {
        chitietphieu.add(ctphieu);
        loadDataTableChiTietPhieu(chitietphieu);
        resetForm();
    } else {
        int input = JOptionPane.showConfirmDialog(this, "Sản phẩm đã tồn tại trong phiếu !\nBạn có muốn chỉnh sửa không?", "Sản phẩm đã tồn tại!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            setFormChiTietPhieu(ctphieu);
        }
    }
}


public void eventBtnNhapHang() throws SQLException {
    if (chitietphieu.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào trong phiếu !", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
    } else if (makh == -1) {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
    } else {
        int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn tạo phiếu xuất !", "Xác nhận tạo phiếu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {
            long now = System.currentTimeMillis();
            Timestamp currenTime = new Timestamp(now);
            PhieuXuatDTO phieuXuat = new PhieuXuatDTO(makh, maphieu, tk.getMNV(), currenTime, sum, 1);
           // System.out.print("hehe" + maphieu);
                    
            // Thêm phiếu xuất vào cơ sở dữ liệu
            phieuXuatBUS.insert(phieuXuat); // Thêm phiếu xuất
          
            // Xác định lô hàng cần cập nhật
            ChiTietLoHangDAO chiTietLoHangDAO = new ChiTietLoHangDAO();
            ChiTietPhieuXuatDAO chiTietPhieuXuatDAO = new ChiTietPhieuXuatDAO(); // Tạo đối tượng DAO cho chi tiết phiếu xuất
            
            for (ChiTietPhieuDTO chiTiet : chitietphieu) {
                int maSp = chiTiet.getMSP();
                int tien = chiTiet.getTIEN();
                
                ArrayList<ChiTietLoHangDTO> loHangList = chiTietLoHangDAO.findLohangByMSP(maSp);
                ChiTietLoHangDTO minLohang = findMinLohangWithValidQuantity(maSp);
                
             //   System.out.print(chiTiet.getMP());
                if (minLohang != null) {
                    String maLoHang = minLohang.getMLH();
                    
                    int soLuongXuat = chiTiet.getSL(); 
                 //   System.out.print("Số lượng xuất " + soLuongXuat);
                    
                    // Cập nhật số lượng trong kho
                    chiTietLoHangDAO.updateQuantity(maSp ,maLoHang, -soLuongXuat); // Trừ số lượng xuất bán
                       
                    // Thêm chi tiết phiếu xuất vào cơ sở dữ liệu
                    ChiTietPhieuXuatDTO chiTietPhieuXuat =
                  new ChiTietPhieuXuatDTO(chiTiet.getMP(), maSp, soLuongXuat, tien, chiTiet.getGiaGiam() , chiTiet.getGiaThanhToan() , chiTiet.getMKM()); // Mã khuyến mãi là 0 nếu không có
                    chiTietPhieuXuatDAO.insert(chiTietPhieuXuat);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy lô hàng hợp lệ cho sản phẩm mã " + maSp, "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            JOptionPane.showMessageDialog(null, "Xuất hàng thành công !");
            mainChinh.setPanel(new PhieuXuat(mainChinh, tk));
        }
    }
}



private int getTotalQuantityFromPhieuXuat(ArrayList<ChiTietPhieuDTO> chitietphieu) {
    int totalQuantity = 0;
    for (ChiTietPhieuDTO chiTiet : chitietphieu) {
        totalQuantity += chiTiet.getSL();
    }
    return totalQuantity;
}

    public void setKhachHang(int index) {
        makh = index;
        KhachHangDTO khachhang = khachHangBUS.selectKh(makh);
        txtKh.setText(khachhang.getHoten());
    }

   
}