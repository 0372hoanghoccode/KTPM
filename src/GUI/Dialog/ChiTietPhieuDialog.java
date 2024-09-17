package GUI.Dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

import BUS.PhieuNhapBUS;
import BUS.PhieuXuatBUS;
import BUS.SanPhamBUS;
import DAO.ChiTietLoHangDAO;
import DAO.ChiTietPhieuXuatDAO;

import DAO.KhachHangDAO;
import DAO.KhuVucSach1DAO;
import DAO.NhanVienDAO;
import DAO.PhieuXuatDAO;
import DAO.SanPhamDAO;
import DTO.ChiTietLoHangDTO;
import DTO.ChiTietPhieuDTO;
import DTO.ChiTietPhieuXuatDTO;
import DTO.KhuVucSach1DTO;
import DTO.PhieuNhapDTO;
import DTO.PhieuTraDTO;
import DTO.PhieuXuatDTO;
import DTO.SanPhamDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Panel.KhuVucSach1;
import helper.Formater;
import helper.writePDF;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ChiTietPhieuDialog extends JDialog implements ActionListener {

    HeaderTitle titlePage;
    JPanel pnmain, pnmain_top, pnmain_bottom, pnmain_btn; //bỏ pnmain_bottom_right, pnmain_bottom_left 
    InputForm txtMaPhieu, txtNhanVien, txtThoiGian;
    DefaultTableModel tblModel;
    JTable table, tblImei;
    JScrollPane scrollTable;

    PhieuNhapDTO phieunhap;
    PhieuXuatDTO phieuxuat;
    KhuVucSach1DTO lohang ; 
    SanPhamBUS spBus = new SanPhamBUS();
    PhieuNhapBUS phieunhapBus;
    PhieuXuatBUS phieuxuatBus;
    KhuVucSach1DAO lohangBUS ; 
    
    ButtonCustom btnPdf, btnDuyet;

    ArrayList<ChiTietPhieuDTO> chitietphieu;

    // HashMap<Integer, ArrayList<SanPhamDTO>> chitietsanpham = new HashMap<>();
    //phieu nhap
    public ChiTietPhieuDialog(JFrame owner, String title, boolean modal, PhieuNhapDTO phieunhapDTO) {
        super(owner, title, modal);
        this.phieunhap = phieunhapDTO;
        phieunhapBus = new PhieuNhapBUS();
        chitietphieu = phieunhapBus.getChiTietPhieu_Type(phieunhapDTO.getMP());
        initComponent(title);
        if(phieunhapDTO.getTT() != 2) {
            btnDuyet.setEnabled(false);
        }
        initPhieuNhap();
        loadDataTableChiTietPhieu(chitietphieu);
        this.setVisible(true);
    }
    //phieu xuat
    public ChiTietPhieuDialog(JFrame owner, String title, boolean modal, PhieuXuatDTO phieuxuatDTO) {
        super(owner, title, modal);
        this.phieuxuat = phieuxuatDTO;
        
        phieuxuatBus = new PhieuXuatBUS();
        
        chitietphieu = phieuxuatBus.selectCTP(phieuxuatDTO.getMP());
        initComponent1(title);
        if(phieuxuatDTO.getTT() != 2) {
            btnDuyet.setEnabled(false);
        }
        initPhieuXuat();
        loadDataTableChiTietPhieuXuat(chitietphieu);
        this.setVisible(true);
    }
    // Phiếu lô hàng 
        public ChiTietPhieuDialog(JFrame owner, String title, boolean modal, KhuVucSach1DTO  khuvucsach1DTO) {
        super(owner, title, modal);
        this.lohang = khuvucsach1DTO;
        
        lohangBUS = new KhuVucSach1DAO();
        String mlhString = khuvucsach1DTO.getMLH();

int mlhInt;
    // Chuyển đổi từ String sang int
    mlhInt = Integer.parseInt(mlhString);
        chitietphieu = lohangBUS.selectCTP(mlhInt);
        initComponent(title);
//        if(phieuxuatDTO.getTT() != 2) {
//            btnDuyet.setEnabled(false);
//        }
        initPhieuXuat();
        loadDataTableChiTietPhieu(chitietphieu);
        this.setVisible(true);
    }


    public void initPhieuNhap() {
        txtMaPhieu.setText("PN" + Integer.toString(this.phieunhap.getMP()));
        txtNhanVien.setText(NhanVienDAO.getInstance().selectById(phieunhap.getMNV() + "").getHOTEN());
        txtThoiGian.setText(Formater.FormatTime(phieunhap.getTG()));
    }

    public void initPhieuXuat() {
        txtMaPhieu.setText("PX" + Integer.toString(this.phieuxuat.getMP()));
        txtNhanVien.setText(NhanVienDAO.getInstance().selectById(phieuxuat.getMNV() + "").getHOTEN());
        txtThoiGian.setText(Formater.FormatTime(phieuxuat.getTG()));
    }

    

    public void loadDataTableChiTietPhieu(ArrayList<ChiTietPhieuDTO> ctPhieu) {
        tblModel.setRowCount(0);
        for (int i = 0; i < ctPhieu.size(); i++) {
            SanPhamDTO sp = spBus.getByMaSP(ctPhieu.get(i).getMSP());
            System.out.print("Mã sản phẩm là " + sp.getMSP()) ; 
            tblModel.addRow(new Object[]{
                i + 1, sp.getMSP(),sp.getTEN(), 
                Formater.FormatVND(ctPhieu.get(i).getTIEN()), ctPhieu.get(i).getSL()
            });
        }
    }
    
   public void loadDataTableChiTietPhieuXuat(ArrayList<ChiTietPhieuDTO> ctPhieu) {
    tblModel.setRowCount(0); // Xóa tất cả các hàng hiện tại trong bảng

    for (int i = 0; i < ctPhieu.size(); i++) {
        ChiTietPhieuDTO ct = ctPhieu.get(i);
        SanPhamDTO sp = spBus.getByMaSP(ct.getMSP());
        
        // Lấy dữ liệu cho các cột mới
        int giaGiam = ct.getGiaGiam(); // Giả sử ChiTietPhieuDTO có phương thức getGiagiam()
        int giaThanhToan = ct.getGiaThanhToan(); // Giả sử ChiTietPhieuDTO có phương thức getGiaThanhToan()
        String maKM = ct.getMKM(); // Giả sử ChiTietPhieuDTO có phương thức getMKM()

        // Thêm một hàng mới vào bảng
        tblModel.addRow(new Object[]{
            i + 1, 
            sp.getMSP(), 
            sp.getTEN(), 
            Formater.FormatVND(ct.getTIEN()), 
            ct.getSL(), 
            Formater.FormatVND(giaGiam), 
            Formater.FormatVND(giaThanhToan), 
            maKM
        });
    }
}


    public void initComponent(String title) {
        this.setSize(new Dimension(1100, 500));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());

        pnmain = new JPanel(new BorderLayout());

        pnmain_top = new JPanel(new GridLayout(1, 4));
        txtMaPhieu = new InputForm("Mã phiếu");
        txtNhanVien = new InputForm("Nhân viên nhập");
        txtThoiGian = new InputForm("Thời gian tạo");

        txtMaPhieu.setEditable(false);
        txtNhanVien.setEditable(false);
        txtThoiGian.setEditable(false);

        pnmain_top.add(txtMaPhieu);
        pnmain_top.add(txtNhanVien);
        pnmain_top.add(txtThoiGian);

        pnmain_bottom = new JPanel(new GridLayout(1, 5));
        pnmain_bottom.setBorder(new EmptyBorder(5, 5, 5, 5));
        pnmain_bottom.setBackground(Color.WHITE);

        // pnmain_bottom_left = new JPanel(new GridLayout(1, 1));
        table = new JTable();
        scrollTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã SP", "Tên SP", "Đơn giá", "Số lượng"};
        tblModel.setColumnIdentifiers(header);
        table.setModel(tblModel);
        table.setFocusable(false);
        scrollTable.setViewportView(table);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
    
        pnmain_bottom.add(scrollTable);

        pnmain_btn = new JPanel(new FlowLayout());
        pnmain_btn.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnmain_btn.setBackground(Color.white);
        btnPdf = new ButtonCustom("Xuất file PDF", "success", 14);
        // btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnDuyet = new ButtonCustom("Duyệt phiếu", "success", 14);
        btnDuyet.addActionListener(this);
        btnPdf.addActionListener(this);
        // btnHuyBo.addActionListener(this);
        pnmain_btn.add(btnDuyet);
        pnmain_btn.add(btnPdf);
        // pnmain_btn.add(btnHuyBo);

        pnmain.add(pnmain_top, BorderLayout.NORTH);
        pnmain.add(pnmain_bottom, BorderLayout.CENTER);
        pnmain.add(pnmain_btn, BorderLayout.SOUTH);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnmain, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }

    
    public void initComponent1(String title) {
    this.setSize(new Dimension(1100, 500));
    this.setLayout(new BorderLayout(0, 0));
    titlePage = new HeaderTitle(title.toUpperCase());

    pnmain = new JPanel(new BorderLayout());

    pnmain_top = new JPanel(new GridLayout(1, 4));
    txtMaPhieu = new InputForm("Mã phiếu");
    txtNhanVien = new InputForm("Nhân viên nhập");
    txtThoiGian = new InputForm("Thời gian tạo");

    txtMaPhieu.setEditable(false);
    txtNhanVien.setEditable(false);
    txtThoiGian.setEditable(false);

    pnmain_top.add(txtMaPhieu);
    pnmain_top.add(txtNhanVien);
    pnmain_top.add(txtThoiGian);

    pnmain_bottom = new JPanel(new BorderLayout());
    pnmain_bottom.setBorder(new EmptyBorder(5, 5, 5, 5));
    pnmain_bottom.setBackground(Color.WHITE);

    // Tạo bảng và các thành phần liên quan
    table = new JTable();
    scrollTable = new JScrollPane();
    tblModel = new DefaultTableModel();
    
    // Cập nhật tiêu đề cột để bao gồm các cột mới
    String[] header = new String[]{"STT", "Mã SP", "Tên SP", "Đơn giá", "Số lượng", "Giá giảm", "Giá thanh toán", "Mã KM"};
    tblModel.setColumnIdentifiers(header);
    
    // Cập nhật mô hình bảng
    table.setModel(tblModel);
    table.setFocusable(false);
    scrollTable.setViewportView(table);
    
    // Căn giữa nội dung các ô trong bảng
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    table.setDefaultRenderer(Object.class, centerRenderer);

    // Cập nhật độ rộng của các cột
    table.getColumnModel().getColumn(2).setPreferredWidth(200); // Tên SP
    table.getColumnModel().getColumn(5).setPreferredWidth(100); // Giá giảm
    table.getColumnModel().getColumn(6).setPreferredWidth(100); // Giá thanh toán
    table.getColumnModel().getColumn(7).setPreferredWidth(100); // Mã KM

    pnmain_bottom.add(scrollTable, BorderLayout.CENTER);

    pnmain_btn = new JPanel(new FlowLayout());
    pnmain_btn.setBorder(new EmptyBorder(10, 0, 10, 0));
    pnmain_btn.setBackground(Color.white);
    btnPdf = new ButtonCustom("Xuất file PDF", "success", 14);
    // btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
    btnDuyet = new ButtonCustom("Duyệt phiếu", "success", 14);
    btnDuyet.addActionListener(this);
    btnPdf.addActionListener(this);
    // btnHuyBo.addActionListener(this);
    pnmain_btn.add(btnDuyet);
    pnmain_btn.add(btnPdf);
    // pnmain_btn.add(btnHuyBo);

    pnmain.add(pnmain_top, BorderLayout.NORTH);
    pnmain.add(pnmain_bottom, BorderLayout.CENTER);
    pnmain.add(pnmain_btn, BorderLayout.SOUTH);

    this.add(titlePage, BorderLayout.NORTH);
    this.add(pnmain, BorderLayout.CENTER);
    this.setLocationRelativeTo(null);
}

    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        // if (source == btnHuyBo) {
        //     dispose();
        // }
        if (source == btnPdf) {
            writePDF w = new writePDF();
            if (this.phieuxuat != null) {
                w.writePX(phieuxuat.getMP());
            }
            if (this.phieunhap != null) {
                w.writePN(phieunhap.getMP());
            }
        }
       if (source == btnDuyet) {
    if (this.phieuxuat != null) {
        // Kiểm tra số lượng phiếu xuất
        if (!phieuxuatBus.checkSLPx(phieuxuat.getMP())) {
            JOptionPane.showMessageDialog(null, "Không đủ số lượng để tạo phiếu!");
        } else {
       

            // Lấy chi tiết phiếu xuất
            ArrayList<ChiTietPhieuXuatDTO> chitiet = ChiTietPhieuXuatDAO.getChiTietByPhieuXuat(phieuxuat.getMP());
            
            boolean hasError = false;

            for (ChiTietPhieuXuatDTO chitietphieuxuat : chitiet) {
                // Lấy chi tiết lô hàng tương ứng với mã sản phẩm
                ChiTietLoHangDTO chitietlohang = laymin(chitietphieuxuat.getMSP());
                if (chitietlohang != null) {
                    int soluongUpdate = chitietlohang.getSoLuong() - chitietphieuxuat.getSL();

                    if (soluongUpdate >= 0) {
                        System.out.println("Số lượng mới là " + soluongUpdate);
                        
                        try {
                            // Cập nhật số lượng trong lô hàng
                            ChiTietLoHangDAO.updateQuantity(chitietlohang.getMSP(), chitietlohang.getMLH(), -chitietphieuxuat.getSL());
                        } catch (SQLException ex) {
                            Logger.getLogger(ChiTietPhieuDialog.class.getName()).log(Level.SEVERE, null, ex);
                            hasError = true;
                            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật số lượng trong lô hàng!");
                        }
                    } else {
                        // Số lượng không đủ
                        JOptionPane.showMessageDialog(null, "Số lượng không đủ cho sản phẩm: " + chitietphieuxuat.getMSP());
                        hasError = true;
                        break; // Dừng việc xử lý khi có lỗi số lượng
                    }
                } else {
                    System.out.println("Không tìm thấy lô hàng cho sản phẩm: " + chitietphieuxuat.getMSP());
                    hasError = true;
                    break; // Dừng việc xử lý khi không tìm thấy lô hàng
                }
            }

            if (!hasError) {
                // Thông báo duyệt thành công nếu không có lỗi
                JOptionPane.showMessageDialog(null, "Duyệt phiếu xuất thành công!");
                     // Cập nhật trạng thái của phiếu xuất
            phieuxuat.setTT(1);
            try {
                // Cập nhật phiếu xuất vào cơ sở dữ liệu nếu cần
                // phieuxuatBus.update(phieuxuat);
                PhieuXuatDAO.updatePhieuXuatStatus(phieuxuat.getMP(), phieuxuat.getTT());
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietPhieuDialog.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật phiếu xuất!");
                return; // Dừng thực hiện nếu có lỗi khi cập nhật
            }
                this.dispose();
            }
        }
    }
}


        
    }
     public ChiTietLoHangDTO laymin(int sp){
         ChiTietLoHangDTO string = null; 
               string= findMinLohangWithValidQuantity(sp);
     
           return string ; 
        
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
}
