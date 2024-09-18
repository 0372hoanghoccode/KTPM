package GUI.Panel;

import java.util.ArrayList;
import java.util.Arrays;
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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.PlainDocument;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import BUS.KhachHangBUS;
import BUS.MaKhuyenMaiBUS;
import BUS.PhieuXuatBUS;
import BUS.GioHangBUS;
import BUS.SanPhamBUS;
import DAO.ChiTietLoHangDAO;
import DTO.ChiTietGioHangDTO;
import DTO.ChiTietLoHangDTO;
import DTO.ChiTietMaKhuyenMaiDTO;
import DTO.ChiTietPhieuDTO;
import DTO.GioHangDTO;
import DTO.PhieuXuatDTO;
import DTO.SanPhamDTO;
import DTO.TaiKhoanDTO;
import GUI.MainKH;
import GUI.Component.ButtonCustom;
import GUI.Component.InputForm;
import GUI.Component.Notification;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.PanelBorderRadius;
import helper.Formater;

public final class GioHang extends JPanel {
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this); //gọi phương thức compoment tổ tiên có kiểu window của compoment hiện tại
    // kiểu như cái listKhachHang thì cho owner dô sẽ gọi đc cái jframe của listkhachhang
    PanelBorderRadius right, left;
    JPanel  contentCenter, left_top, content_btn, left_bottom; //là cái main cữ
    JTable tableGioHang, tableSanPham;
    JScrollPane scrollTableGioHangNhap, scrollTableSanPham;
    DefaultTableModel tblModel, tblModelSP; //table co san 
    ButtonCustom btnAddSp, btnDelete, btnDatHang;
    InputForm txtTenSp, txtMaSp, txtMaISBN, txtSoLuongSPxuat, txtMaGiamGia, txtGiaGiam, txtMaKM;
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
    GioHangBUS giohangBUS = new GioHangBUS();
    PhieuXuatBUS pxbus = new PhieuXuatBUS();

    // SanPhamBUS chiTietSanPhamBUS = new SanPhamBUS();
    KhachHangBUS khachHangBUS = new KhachHangBUS();
    ArrayList<ChiTietGioHangDTO> chitietgiohang = new ArrayList<>();
    ArrayList<DTO.SanPhamDTO> listSP = spBUS.getAll();
    ArrayList<DTO.ChiTietMaKhuyenMaiDTO> listctMKM = new ArrayList<>();

    TaiKhoanDTO tk;
    private JLabel lbltongtien;
    private MainKH mainChinh;
    // private ButtonCustom btnQuayLai; //chua use
    private InputForm txtGiaXuat;

    public GioHang(MainKH mainChinh, TaiKhoanDTO tk, String type) {
        this.mainChinh = mainChinh;
        this.tk = tk;
        this.type = type;
        maphieu = pxbus.getMPMAX() + 1;
        long now = System.currentTimeMillis();
        Timestamp currenTime = new Timestamp(now);
        giohangBUS.add(new GioHangDTO(tk.getMNV(), 0, currenTime, 1));
        chitietgiohang = giohangBUS.getAllct(tk.getMNV());
        initComponent(type);
        loadDataTalbeSanPham(listSP);
        loadDataTableChiTietGioHang(chitietgiohang);
    }

    private void initComponent(String type) {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Phiếu xuất
        tableGioHang = new JTable();
        scrollTableGioHangNhap = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã SP", "Tên sản phẩm", "Đơn giá", "Số lượng","Giá Giảm","Giá thanh toán","Mã khuyến mãi"};
        tblModel.setColumnIdentifiers(header);
        tableGioHang.setModel(tblModel);
        scrollTableGioHangNhap.setViewportView(tableGioHang);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tableGioHang.getColumnModel();
        for (int i = 0; i < 5; i++) {
            if (i != 2) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        tableGioHang.getColumnModel().getColumn(2).setPreferredWidth(300);
        tableGioHang.setFocusable(false);
        tableGioHang.setDefaultEditor(Object.class, null);
        scrollTableGioHangNhap.setViewportView(tableGioHang);

        tableGioHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = tableGioHang.getSelectedRow();
                if (index != -1) {
                    tableSanPham.setSelectionMode(index);
                    setFormChiTietGioHang(chitietgiohang.get(index) , listSP);
                    // ChiTietGioHangDTO ctphieu = chitietphieu.get(index);
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

        tableSanPham.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = tableSanPham.getSelectedRow();
                if (index != -1) {
                    resetForm();
                    setInfoSanPham(listSP.get(index) , listSP);
                    if (!checkTonTai()) {
                        actionbtn("add");
                    } else {
                        actionbtn("update");
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
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Tên sản phẩm, mã sản phẩm, ...");
        txtTimKiem.putClientProperty("JTextField.showClearButton", true);
        txtTimKiem.putClientProperty("JTextField.leadingIcon", new FlatSVGIcon("./icon/search.svg"));

        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) { //chạy khi nhả phím trong tìm kiếm
                ArrayList<SanPhamDTO> rs = spBUS.search(txtTimKiem.getText(), "Tất cả");
                loadDataTalbeSanPham(rs);
            }
        });

        content_left.add(txtTimKiem, BorderLayout.NORTH);
        content_left.add(scrollTableSanPham, BorderLayout.CENTER);

        content_right = new JPanel(new BorderLayout(5, 5));
        content_right.setOpaque(false);
        // content_right.setBackground(Color.WHITE);

        content_right_top = new JPanel(new BorderLayout());
        content_right_top.setPreferredSize(new Dimension(300, 450)); 
        
        txtMaISBN = new InputForm("Mã ISBN");
        txtMaISBN.setEditable(false);
        txtMaISBN.setVisible(false);
        txtMaISBN.setPreferredSize(new Dimension(130, 20));
        txtMaSp = new InputForm("Mã sản phẩm");
        txtMaSp.setEditable(false);
        txtMaSp.setPreferredSize(new Dimension(130, 20));
        txtTenSp = new InputForm("Tên sản phẩm");
        txtTenSp.setEditable(false);
        txtTenSp.setPreferredSize(new Dimension(280, 20));
        txtGiaXuat = new InputForm("Giá bán");
        txtGiaXuat.setEditable(false);
        txtGiaXuat.setPreferredSize(new Dimension(130, 20));
        PlainDocument dongia = (PlainDocument) txtGiaXuat.getTxtForm().getDocument();
        dongia.setDocumentFilter(new NumericDocumentFilter()); // chỉ cho nhập số
        txtSoLuongSPxuat = new InputForm("Số lượng");
        txtSoLuongSPxuat.setPreferredSize(new Dimension(130, 20));
        PlainDocument soluong = (PlainDocument) txtSoLuongSPxuat.getTxtForm().getDocument();
        soluong.setDocumentFilter(new NumericDocumentFilter()); // chỉ cho nhập số
        txtMaKM = new InputForm("Mã giảm giá");
        txtMaKM.setPreferredSize(new Dimension(130, 20));
        txtMaKM.setVisible(false);
        txtGiaGiam = new InputForm("Giá giảm");
        txtGiaGiam.setText(" ");
        txtGiaGiam.setVisible(false);
        txtGiaGiam.setEditable(false);
        txtGiaGiam.setPreferredSize(new Dimension(130, 20));
        
        // Xử lý sự kiện khi mã giảm giá thay đổi
//        txtMaKM.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                String txt = txtMaKM.getText();
//                for (ChiTietMaKhuyenMaiDTO i : listctMKM) {
//                    if (i.getMKM().equals(txt)) {
//                        double giaxuat = Integer.parseInt(txtGiaXuat.getText());
//                        double phantramgiam = i.getPTG();
//                        int giagiam = (int) (giaxuat * (1 - phantramgiam / 100));
//                        txtGiaGiam.setText(Integer.toString(giagiam));
//                    }
//                }
//            }
//        });
        
        // Tạo JPanel cho tất cả các thành phần
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5)); // 6 hàng, 2 cột
        formPanel.setPreferredSize(new Dimension(300, 160)); 

        formPanel.add(txtMaISBN);
        formPanel.add(new JLabel()); 
        formPanel.add(txtMaSp);
        formPanel.add(txtTenSp);
        formPanel.add(txtGiaXuat);
        formPanel.add(txtSoLuongSPxuat);
      //  formPanel.add(txtMaKM);
        formPanel.add(txtGiaGiam);
        
        content_right_top.add(formPanel, BorderLayout.CENTER);
        
        // tong tiền
        JPanel pn_tongtien = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pn_tongtien.setOpaque(false);
        JLabel lbltien = new JLabel("TỔNG TIỀN: ");
        lbltien.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 14));
        lbltien.setForeground(new Color(255, 51, 51));
        lbltongtien = new JLabel("0đ");
        lbltongtien.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 14));
        pn_tongtien.add(lbltien);
        pn_tongtien.add(lbltongtien);
        
        // nút đặt hàng
        ButtonCustom btnDatHang = new ButtonCustom("Đặt hàng", "excel", 14);
        btnDatHang.setPreferredSize(new Dimension(120, 30)); 
        btnDatHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventBtnDatHang();
            }
        });
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(300, 70));
        bottomPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(pn_tongtien, BorderLayout.NORTH);
        bottomPanel.add(btnDatHang, BorderLayout.SOUTH);
        
        content_right_top.add(bottomPanel, BorderLayout.SOUTH);
        
        content_right.add(content_right_top, BorderLayout.NORTH);
        
        content_top.add(content_left);
        content_top.add(content_right);
        left_top.add(content_top, BorderLayout.CENTER);
        
        // content_btn - 4 nút ở left_top (South)
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
                    addCtGioHang(listSP);
                    // Thông báo dạng popup
                    Notification thongbaoNoi = new Notification(mainChinh, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Thêm sản phẩm thành công!");
                    thongbaoNoi.showNotification();
                    loadDataTableChiTietGioHang(chitietgiohang);
                    actionbtn("update");
                }
            }
        });
        
        // btnEditSP.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         int index = tableGioHang.getSelectedRow();
        //         if (index < 0) {
        //             JOptionPane.showMessageDialog(null, "Vui lòng chọn cấu hình cần chỉnh");
        //         } else {
        //             chitietgiohang.get(index).setSL(Integer.parseInt(txtSoLuongSPxuat.getText()));
        //             if (!txtGiaGiam.getText().equals(" ")) {
        //                 chitietgiohang.get(index).setTIENGIO(Integer.parseInt(txtGiaGiam.getText()));
        //                 chitietgiohang.get(index).setMKM(txtMaKM.getText());
        //             } else {
        //                 chitietgiohang.get(index).setTIENGIO(Integer.parseInt(txtGiaXuat.getText()));
        //             }
        //             giohangBUS.updateCT(chitietgiohang.get(index));
        //             loadDataTableChiTietGioHang(chitietgiohang);
        //         }
        //     }
        // });
        
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tableGioHang.getSelectedRow();
                if (index < 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn cấu hình cần xóa");
                } else {
                    giohangBUS.deleteCT(chitietgiohang.get(index));
                    chitietgiohang.remove(index);
                    actionbtn("add");
                    loadDataTableChiTietGioHang(chitietgiohang);
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
        
        left_bottom = new JPanel();
        left_bottom.setOpaque(false);
        left_bottom.setPreferredSize(new Dimension(0, 250));
        left_bottom.setBorder(new EmptyBorder(0, 5, 10, 10));
        BoxLayout boxly = new BoxLayout(left_bottom, BoxLayout.Y_AXIS);
        left_bottom.setLayout(boxly);
        left_bottom.add(scrollTableGioHangNhap);
        left.add(left_top, BorderLayout.CENTER);
        left.add(left_bottom, BorderLayout.SOUTH);
        

        // RIGHT 
        //đoạn right này đã bị vứt đừng quan tâm nó
        right = new PanelBorderRadius();
        right.setPreferredSize(new Dimension(0, 0));
        right.setBorder(new EmptyBorder(5, 5, 5, 5));
        right.setLayout(new BorderLayout());

        JPanel right_top, right_center, right_bottom;
        right_top = new JPanel(new GridLayout(2, 1, 0, 0));
        right_top.setPreferredSize(new Dimension(300, 180));

        right_center = new JPanel(new BorderLayout());
        right_center.setOpaque(false);

        JPanel khachJPanel = new JPanel(new BorderLayout());
        khachJPanel.setPreferredSize(new Dimension(0, 40));
        khachJPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        khachJPanel.setOpaque(false);
        JPanel kJPanelLeft = new JPanel(new GridLayout(1, 1));
        kJPanelLeft.setOpaque(false);
        kJPanelLeft.setPreferredSize(new Dimension(40, 0));

        khachJPanel.add(kJPanelLeft, BorderLayout.EAST);
        JPanel khPanel = new JPanel(new GridLayout(2, 1, 5, 0));
        khPanel.setBackground(Color.WHITE);
        khPanel.setPreferredSize(new Dimension(0, 80));
        khPanel.add(khachJPanel);
        right_center.add(khPanel, BorderLayout.NORTH);

        right_bottom = new JPanel(new GridLayout(2, 1));
        right_bottom.setPreferredSize(new Dimension(300, 100));
        right_bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
        right_bottom.setOpaque(false);

        right.add(right_top, BorderLayout.NORTH);
        right.add(right_center, BorderLayout.CENTER);
        right.add(right_bottom, BorderLayout.SOUTH);

        contentCenter.add(left, BorderLayout.CENTER);
        contentCenter.add(right, BorderLayout.EAST);
        // actionbtn("add");
    }
    //end rright phần bị bỏ

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
        this.txtMaISBN.setText("");
        this.txtGiaXuat.setText("");
        this.txtSoLuongSPxuat.setText("");
        // this.txtMaGiamGia.setText("");
        this.txtGiaGiam.setText(" ");
    }

    public String[] getMaGiamGiaTable(int masp) {
        listctMKM = mkmBUS.Getctmkm(masp);
        int size = listctMKM.size();
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arr.add(listctMKM.get(i).getMKM());
        }
        String[] tmp = new String[arr.size()];
        for (int i = 0; i < tmp.length; i++) tmp[i] = arr.get(i);
        tmp = Stream.concat(Stream.of("Chọn"), Arrays.stream(tmp)).toArray(String[]::new);
        return tmp;
    }


    public void setInfoSanPham(SanPhamDTO sp,  ArrayList<DTO.SanPhamDTO> result) {
        masp = sp.getMSP();
        this.txtMaSp.setText(Integer.toString(sp.getMSP()));
        this.txtTenSp.setText(sp.getTEN());
      //  this.txtMaISBN.setText(sp.getISBN());
       
          ChiTietLoHangDAO chiTietLoHangDAO = new ChiTietLoHangDAO();
    
            for (SanPhamDTO sp1 : result) {
      
             
              
             this.txtGiaXuat.setText(sp.getTIENX()+"");
        
    }
        
    }

    

    public void setFormChiTietGioHang(ChiTietGioHangDTO phieu , ArrayList<DTO.SanPhamDTO> result) { //set info vào inputform khi nhan ben tablephieunhap
        SanPhamDTO ctsp = spBUS.getByMaSP(phieu.getMSP());
        // ChiTietMaKhuyenMaiDTO ctmkm = mkmBUS.findCT(listctMKM, ctsp.getMSP());
        this.txtMaSp.setText(Integer.toString(ctsp.getMSP()));
        this.txtTenSp.setText(spBUS.getByMaSP(ctsp.getMSP()).getTEN());
       // this.txtGiaXuat.setText(Integer.toString(phieu.getTIENGIO()));
        ChiTietLoHangDAO chiTietLoHangDAO = new ChiTietLoHangDAO();
    
    for (SanPhamDTO sp : result) {
      
              ChiTietLoHangDTO string= findMinLohangWithValidQuantity(sp.getMSP());
            
            if (string != null )
            //  this.txtGiaXuat.setText(string.getGiaNhap() * 2 + "");
            this.txtGiaXuat.setText("100");
            else {
                this.txtGiaXuat.setText( "");
            }
        
    }
        this.txtSoLuongSPxuat.setText(Integer.toString(phieu.getSL()));
        getMaGiamGiaTable(ctsp.getMSP());
    }

    public void loadDataTalbeSanPham(ArrayList<DTO.SanPhamDTO> result) {
          tblModelSP.setRowCount(0); // Xóa tất cả các hàng hiện tại trong bảng
    int soluong ; 
    ChiTietLoHangDAO chiTietLoHangDAO = new ChiTietLoHangDAO();
    
    for (SanPhamDTO sp : result) {
      
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
         
              this.txtGiaXuat.setText(sp.getTIENX()+"");
           
        
    }
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

    public void loadDataTableChiTietGioHang(ArrayList<ChiTietGioHangDTO> ctGioHang) {
        tblModel.setRowCount(0);
        int size = ctGioHang.size();
        sum = 0;
        for (int i = 0; i < size; i++) {
            SanPhamDTO phienban = spBUS.getByMaSP(ctGioHang.get(i).getMSP());
            sum += ctGioHang.get(i).getTIENGIO() * ctGioHang.get(i).getSL();
            tblModel.addRow(new Object[]{
                i + 1, phienban.getMSP(), spBUS.getByMaSP(phienban.getMSP()).getTEN(), 
                Formater.FormatVND(ctGioHang.get(i).getTIENGIO()), ctGioHang.get(i).getSL(), ctGioHang.get(i).getGIAGIAM(),
                ctGioHang.get(i).getGIATHANHTOAN(), ctGioHang.get(i).getMKM()
            });
        }
        lbltongtien.setText(Formater.FormatVND(sum));
    }

    

    public boolean checkTonTai() {
        ChiTietGioHangDTO p = giohangBUS.findCT(chitietgiohang, Integer.parseInt(txtMaSp.getText())); 
            //kiểm tra coi masp này có trong chitietphieu này chưa 
        
        return p != null;
    }

    public boolean checkInfo() {
          String text = txtMaSp.getText();
          System.out.print("Mã bao nhiêu "  + text);
            // Chuyển đổi giá trị từ String sang int
            int maSp = Integer.parseInt(text);
        ChiTietLoHangDTO chitiet = laymin(maSp);
        boolean check = true;
        int index = tableSanPham.getSelectedRow();
        if (txtMaSp.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm","Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            check = false;
        } else if (txtGiaXuat.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Giá nhập không được để rỗng !", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            check = false;
        } else if (txtSoLuongSPxuat.getText().equals("") || Integer.parseInt(txtSoLuongSPxuat.getText()) > chitiet.getSoLuong()) {
            JOptionPane.showMessageDialog(null, "Số lượng không được để rỗng và không lớn hơn đang có!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            check = false;
        } 
        return check;
    }

    public void addCtGioHang(ArrayList<DTO.SanPhamDTO> result) { // them sp vao chitietphieu
       
        int giaxuat;
//        String text = txtMaSp.getText();
//             int maSp = Integer.parseInt(text);
//        ChiTietLoHangDTO chitiet = laymin(maSp);
        
         giaxuat = Integer.parseInt(txtGiaXuat.getText());
        int soluong = Integer.parseInt(txtSoLuongSPxuat.getText());
        ChiTietGioHangDTO ctphieu = new ChiTietGioHangDTO(tk.getMNV(), masp, soluong, giaxuat,0,giaxuat, "");
        ChiTietGioHangDTO p = giohangBUS.findCT(chitietgiohang, ctphieu.getMSP());
        if (p == null) {
            chitietgiohang.add(ctphieu);
            giohangBUS.addCT(ctphieu);
            loadDataTableChiTietGioHang(chitietgiohang);
            resetForm();
        } else {
            int input = JOptionPane.showConfirmDialog(this, "Sản phẩm đã tồn tại trong phiếu !\nBạn có muốn chỉnh sửa không ?", "Sản phẩm đã tồn tại !", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == 0) {
                setFormChiTietGioHang(ctphieu , listSP);
            }
        }
    }

    public void eventBtnDatHang() {
        if (chitietgiohang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào trong phiếu !", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
        } else {
            int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn tạo phiếu xuất !", "Xác nhận tạo phiếu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == 0) {
                    long now = System.currentTimeMillis();
                    Timestamp currenTime = new Timestamp(now);
                    PhieuXuatDTO phieuXuat = new PhieuXuatDTO(tk.getMNV(), maphieu, 1, currenTime, sum, 2);
                    ArrayList <ChiTietPhieuDTO> ctpx = new ArrayList<ChiTietPhieuDTO>();
                    chitietgiohang = giohangBUS.getAllct(tk.getMNV());
                    for(ChiTietGioHangDTO i : chitietgiohang) {
                        ctpx.add(new ChiTietPhieuDTO(maphieu, i.getMSP(), i.getSL(), i.getTIENGIO(), i.getGIAGIAM() , i.getTIENGIO() , i.getMKM()));
                    }
                    pxbus.insertGH(phieuXuat, ctpx); //update số lượng trong kho
                    JOptionPane.showMessageDialog(null, "Tạo đơn hàng thành công !");
                    giohangBUS.delete(tk.getMNV());
                    chitietgiohang = giohangBUS.getAllct(tk.getMNV());
                    loadDataTableChiTietGioHang(chitietgiohang);
            }
        }
    }
    
    public ChiTietLoHangDTO laymin(int sp){
         ChiTietLoHangDTO string = null; 
               string= findMinLohangWithValidQuantity(sp);
     
           return string ; 
        
    } 
    
}