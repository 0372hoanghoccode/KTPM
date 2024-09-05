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
import java.sql.Timestamp;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.PlainDocument;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import BUS.KhachHangBUS;
import BUS.PhieuTraBUS;
import BUS.PhieuXuatBUS;
import BUS.SanPhamBUS;
import DTO.ChiTietPhieuDTO;
import DTO.ChiTietPhieuTraDTO;
import DTO.NhanVienDTO;
import DTO.PhieuTraDTO;
import DTO.PhieuXuatDTO;
import DTO.SanPhamDTO;
import GUI.Main;
import GUI.Component.ButtonCustom;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.PanelBorderRadius;
import GUI.Component.SelectForm;
import helper.Formater;
import helper.Validation;


public final class TaoPhieuTra extends JPanel implements ItemListener, ActionListener {
    
    PanelBorderRadius left, right;
    JTable tablePhieuTra, tableSanPham; //tablePhieuTra ở left_bottom chứa sp của phiếu, tableSanPham chứa sp đang bán
    JScrollPane scrollTablePhieuTra, scrollTableSanPham;
    JPanel contentCenter, left_top, content_btn, left_bottom;
    DefaultTableModel tblModel, tblModelSP; //table co san 
    ButtonCustom btnAddSp, btnEditSP, btnDelete, btnTraHang; //, btnImport
    InputForm txtNhanVien, txtMaSp, txtTenSp, txtDongia, txtMaISBN, txtSoLuongSPtra, txtKhachHang, txtLydo ;
    SelectForm cbxMaphieu;
    JTextField txtTimKiem;
    JLabel  lbltongtien;

    Main m;
    Color BackgroundColor = new Color(193 ,237 ,220);

    SanPhamBUS spBUS = new SanPhamBUS();
    KhachHangBUS khBus = new KhachHangBUS();
    PhieuTraBUS phieutraBus = new PhieuTraBUS();
    PhieuXuatBUS phieuxuatBus = new PhieuXuatBUS();
    NhanVienDTO nvDto;

    ArrayList<SanPhamDTO> listSP = new ArrayList<SanPhamDTO>(); 
    ArrayList<ChiTietPhieuTraDTO> chitietphieu;
    int rowPhieuSelect = -1;
    int slmax = 0;

    public TaoPhieuTra(NhanVienDTO nv, String type, Main m ){
        this.nvDto = nv;
        this.m = m;
        chitietphieu = new ArrayList<>();
        initComponent(type);
        Fillter();
    }


    public void initComponent(String type){
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        
        //Phieu Tra
        tablePhieuTra = new JTable();
        tablePhieuTra.setBackground(new Color(0xA1D6E2));
        scrollTablePhieuTra = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã SP", "Tên sản phẩm", "Đơn giá", "Số lượng"};
        tblModel.setColumnIdentifiers(header); //thiết lập tiêu đề cột, nhận một tham số là một mảng các chuỗi
        tablePhieuTra.setModel(tblModel);
        scrollTablePhieuTra.setViewportView(tablePhieuTra);//thiết lập thành phần hiển thị cho viewport. Thành phần hiển thị là một component có thể cuộn (scrollable)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); //phương thức để định dạng văn bản, màu sắc, căn chỉnh và các thuộc tính hiển thị khác cho các ô trong bản
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tablePhieuTra.getColumnModel();
        for (int i = 0; i < 5; i++) {
            if (i != 2) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        tablePhieuTra.getColumnModel().getColumn(2).setPreferredWidth(300);
        tablePhieuTra.setDefaultEditor(Object.class, null);
        tablePhieuTra.setFocusable(false);
        scrollTablePhieuTra.setViewportView(tablePhieuTra);

        tablePhieuTra.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = tablePhieuTra.getSelectedRow();
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
        String[] headerSP = new String[]{"Mã SP", "Tên sản phẩm", "SL đơn"};
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
                    setInfoSanPham(listSP.get(index));
                    ChiTietPhieuTraDTO ctp = checkTonTai();
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
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Tên sản phẩm, mã sản phẩm, ...");
        txtTimKiem.putClientProperty("JTextField.showClearButton", true);
        txtTimKiem.putClientProperty("JTextField.leadingIcon", new FlatSVGIcon("./icon/search.svg"));

        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) { //chạy khi nhả phím trong tìm kiếm
                ArrayList<SanPhamDTO> rs = spBUS.search(listSP, txtTimKiem.getText(), "Tất cả");
                loadDataTalbeSanPham2(rs);
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

        txtDongia = new InputForm("Giá trả");
        PlainDocument dongia = (PlainDocument) txtDongia.getTxtForm().getDocument();
        dongia.setDocumentFilter((new NumericDocumentFilter()));   //chỉ cho nhập số
        txtSoLuongSPtra = new InputForm("Số lượng");
        PlainDocument soluong = (PlainDocument) txtSoLuongSPtra.getTxtForm().getDocument();
        soluong.setDocumentFilter((new NumericDocumentFilter())); //chỉ cho nhập số

        txtLydo = new InputForm("Lý do trả");

        JPanel merge1 = new JPanel(new BorderLayout());
        merge1.setPreferredSize(new Dimension(100, 50));
        merge1.add(txtMaSp, BorderLayout.WEST);
        merge1.add(txtMaISBN, BorderLayout.CENTER);

        JPanel merge2 = new JPanel(new GridLayout());
        merge2.setPreferredSize(new Dimension(100, 80));
        merge2.add(txtDongia);
        merge2.add(txtSoLuongSPtra);
        merge2.add(txtLydo);

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
        btnEditSP = new ButtonCustom("Sửa sản phẩm", "warning", 14);
        btnDelete = new ButtonCustom("Xoá sản phẩm", "danger", 14);
            // btnImport = new ButtonCustom("Nhập Excel", "excel", 14);
        btnAddSp.addActionListener(this);
        btnEditSP.addActionListener(this);
        btnDelete.addActionListener(this);
            // btnImport.addActionListener(this);
        btnEditSP.setEnabled(false);
        btnDelete.setEnabled(false);
        content_btn.add(btnAddSp);
            // content_btn.add(btnImport);
        content_btn.add(btnEditSP);
        content_btn.add(btnDelete);
        left_top.add(content_btn, BorderLayout.SOUTH);

        //left_bottom này là danh sách nhập ở left phía nam
        left_bottom = new JPanel();
        left_bottom.setOpaque(false);
        left_bottom.setPreferredSize(new Dimension(0, 250));
        left_bottom.setBorder(new EmptyBorder(0, 5, 10, 10));
        BoxLayout boxly = new BoxLayout(left_bottom, BoxLayout.Y_AXIS);
        left_bottom.setLayout(boxly);
        left_bottom.add(scrollTablePhieuTra);
        left.add(left_top, BorderLayout.CENTER);
        left.add(left_bottom, BorderLayout.SOUTH);

        // RIGHT 
        right = new PanelBorderRadius();
        right.setPreferredSize(new Dimension(320, 0));
        right.setBorder(new EmptyBorder(5, 5, 5, 5));
        right.setLayout(new BorderLayout());

        JPanel right_top, right_center, right_bottom, pn_tongtien;
        right_top = new JPanel(new GridLayout(4, 1, 0, 0));
        right_top.setPreferredSize(new Dimension(300, 360));
        right_top.setOpaque(false);

        cbxMaphieu = new SelectForm("Mã phiếu xuất muốn lập", getMPX());
        cbxMaphieu.getCbb().addItemListener(this);
        cbxMaphieu.setSelectedIndex(0);
        txtNhanVien = new InputForm("Nhân viên nhập");
        txtNhanVien.setText(nvDto.getHOTEN());
        txtNhanVien.setEditable(false);
        txtKhachHang = new InputForm("Khách hàng");
        txtKhachHang.setEditable(false);
        right_top.add(cbxMaphieu);
        right_top.add(txtNhanVien);
        right_top.add(txtKhachHang);

        right_center = new JPanel();
        right_center.setPreferredSize(new Dimension(100, 100));
        right_center.setOpaque(false);

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

        btnTraHang = new ButtonCustom("Trả hàng", "excel", 14);
        btnTraHang.addActionListener(this);
        right_bottom.add(btnTraHang);

        right.add(right_top, BorderLayout.NORTH);
        right.add(right_center, BorderLayout.CENTER);// để trống
        right.add(right_bottom, BorderLayout.SOUTH);

        contentCenter.add(left, BorderLayout.CENTER);
        contentCenter.add(right, BorderLayout.EAST);
    }

    public String[] getMPX() {
        String[] mpx = phieuxuatBus.getArrMPX();
        String[] mpt = phieutraBus.getArrMPX();
        if(mpt == null || mpt.length == 0) return mpx;
        else if(mpx == null || mpx.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có hóa đơn để lập phiếu!", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
            PhieuTra pnlPhieu = new PhieuTra(m, nvDto);
            m.setPanel(pnlPhieu);
        }
        else {
            int size = 0;
            for(int i = 0; i < mpt.length; i++) {
                for(int j = 0; j < mpx.length; j++) if(mpt[i].equals(mpx[j])) {
                    mpx[j] = "0";
                }
                else size++;
            }
            if(size == mpx.length) {
                JOptionPane.showMessageDialog(this, "Tất cả hóa đơn đã lập phiếu!", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
                PhieuTra pnlPhieu = new PhieuTra(m, nvDto);
                m.setPanel(pnlPhieu);
            }
            else {
                String[] tmp = new String[size];
                size = 0;
                for(int j = 0; j < mpx.length; j++) if(!mpx[j].equals("0")) tmp[size++] = mpx[j];
                return tmp;
            }
            
        }
        return null;
    }

    public void actionbtn(String type) { //ẩn hiện button
        boolean val_1 = type.equals("add");
        boolean val_2 = type.equals("update");
        btnAddSp.setEnabled(val_1);
        // btnImport.setEnabled(val_1);
        btnEditSP.setEnabled(val_2);
        btnDelete.setEnabled(val_2);
        content_btn.revalidate();
        content_btn.repaint();
    }

    public void setInfoSanPham(SanPhamDTO sp) { //set info vào inputform khi nhan ben tablesanpham
        this.txtMaSp.setText(Integer.toString(sp.getMSP()));
        this.txtTenSp.setText(sp.getTEN());
        this.txtSoLuongSPtra.setText(Integer.toString(sp.getSL()));
        this.txtMaISBN.setText(sp.getISBN());
        this.txtDongia.setText(Integer.toString(sp.getTIENN()/2));
        slmax = sp.getSL();
    }

    public void setFormChiTietPhieu(ChiTietPhieuTraDTO phieu) { //set info vào inputform khi nhan ben tablephieunhap
        SanPhamDTO ctsp = spBUS.getByMaSP(phieu.getMSP());

        this.txtMaSp.setText(Integer.toString(ctsp.getMSP()));
        this.txtTenSp.setText(spBUS.getByMaSP(ctsp.getMSP()).getTEN());
        this.txtDongia.setText(Integer.toString(phieu.getTIEN()));
        this.txtSoLuongSPtra.setText(Integer.toString(phieu.getSL()));
    }
    
    public ChiTietPhieuTraDTO getInfoChiTietPhieu() {
        int masp = Integer.parseInt(txtMaSp.getText());
        int gianhap = Integer.parseInt(txtDongia.getText());
        int soluong = Integer.parseInt(txtSoLuongSPtra.getText());
        int mp = phieuxuatBus.getByIndex(cbxMaphieu.getSelectedIndex()).getMP();
        ChiTietPhieuTraDTO ctphieu = new ChiTietPhieuTraDTO(mp, masp, soluong, gianhap, "");
        return ctphieu;
    }


    public void loadDataTalbeSanPham(ArrayList<ChiTietPhieuDTO> ctPt) {
        tblModelSP.setRowCount(0);
        for (int i = 0; i < ctPt.size(); i++) {
            SanPhamDTO sp = spBUS.getByMaSP(ctPt.get(i).getMSP());
            sp.setSL(ctPt.get(i).getSL());
            listSP.add(sp);
            tblModelSP.addRow(new Object[]{sp.getMSP(), sp.getTEN(), sp.getSL()});
        }
    }

    public void loadDataTalbeSanPham2(ArrayList<SanPhamDTO> result) {
        tblModelSP.setRowCount(0);
        for (SanPhamDTO sp : result) {
            tblModelSP.addRow(new Object[]{sp.getMSP(), sp.getTEN(), sp.getSL()});
        }
    }

    public void loadDataTableChiTietPhieu(ArrayList<ChiTietPhieuTraDTO> ctPhieu) {
        tblModel.setRowCount(0);
        int size = ctPhieu.size();
        for (int i = 0; i < size; i++) {
            SanPhamDTO pb = spBUS.getByMaSP(ctPhieu.get(i).getMSP());
            tblModel.addRow(new Object[]{
                i + 1, pb.getMSP(), spBUS.getByMaSP(pb.getMSP()).getTEN(), 
                Formater.FormatVND(ctPhieu.get(i).getTIEN()), ctPhieu.get(i).getSL()
            });
        }
        lbltongtien.setText(Formater.FormatVND(phieutraBus.getTIEN(ctPhieu)));
    }

    public boolean validateTra() {
        int phuongthuc = 0;
        if (Validation.isEmpty(txtMaSp.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm", "Chọn sản phẩm", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Validation.isEmpty(txtDongia.getText())) {
            JOptionPane.showMessageDialog(this, "Giá trả không được để rỗng !", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (phuongthuc == 0) {
            if (Validation.isEmpty(txtLydo.getText())) {
                JOptionPane.showMessageDialog(this, "Ly dó không được để rỗng!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (Validation.isEmpty(txtSoLuongSPtra.getText()) || !Validation.isNumber(txtSoLuongSPtra.getText()) || Integer.parseInt(txtSoLuongSPtra.getText()) > slmax ) {
                JOptionPane.showMessageDialog(this, "Số lượng không được để rỗng, phải là số và không lớn hơn số lượng trong đơn hàng!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } 
    
        return true;
    }


    public void resetForm() {
        this.txtMaSp.setText("");
        this.txtTenSp.setText("");
        this.txtDongia.setText("");
        this.txtSoLuongSPtra.setText("");
        this.txtMaISBN.setText("");
        this.txtLydo.setText("");
    }

    public ChiTietPhieuTraDTO checkTonTai() {
        ChiTietPhieuTraDTO p = phieutraBus.findCT(chitietphieu, Integer.parseInt(txtMaSp.getText())); 
        return p;
    }

    public void addCtPhieu() { // them sp vao chitietphieu
        ChiTietPhieuTraDTO ctphieu = getInfoChiTietPhieu();
        ChiTietPhieuTraDTO p = phieutraBus.findCT(chitietphieu, ctphieu.getMSP());
        if (p == null) {
            chitietphieu.add(ctphieu);
            loadDataTableChiTietPhieu(chitietphieu);
            resetForm();
        } else {
            int input = JOptionPane.showConfirmDialog(this, "Sản phẩm đã tồn tại trong phiếu !\nBạn có muốn chỉnh sửa không ?", "Sản phẩm đã tồn tại !", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == 0) {
                setFormChiTietPhieu(ctphieu);
            }
        }
    }

    public void Fillter() {
        PhieuXuatDTO tmppx = phieuxuatBus.getByIndex(cbxMaphieu.getSelectedIndex());
        String name = khBus.getTenKhachHang(tmppx.getMKH());
        txtKhachHang.setText(name);
        resetForm();
        int mpx = tmppx.getMP();
        ArrayList <ChiTietPhieuDTO> tmp = phieuxuatBus.selectCTP(mpx);
        loadDataTalbeSanPham(tmp);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        chitietphieu = new ArrayList<>();
        loadDataTableChiTietPhieu(chitietphieu);
        Fillter();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnAddSp && validateTra()) {
            addCtPhieu();
            
        } else if (source == btnDelete) {
            int index = tablePhieuTra.getSelectedRow();
            chitietphieu.remove(index);
            actionbtn("add");
            loadDataTableChiTietPhieu(chitietphieu);
            resetForm();
        } else if (source == btnEditSP) {
            chitietphieu.get(rowPhieuSelect).setSL(Integer.parseInt(txtSoLuongSPtra.getText()));
            chitietphieu.get(rowPhieuSelect).setTIEN(Integer.parseInt(txtDongia.getText()));
            loadDataTableChiTietPhieu(chitietphieu);
        } else if (source == btnTraHang) {
            eventBtnTraHang();
        } 
    }
    
    public void eventBtnTraHang() {
        if (chitietphieu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào trong phiếu !", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
        } else {
            int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn tạo phiếu trả !", "Xác nhận tạo phiếu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == 0) {
                int mp = phieuxuatBus.getByIndex(cbxMaphieu.getSelectedIndex()).getMP();
                int makh = khBus.getByTen(txtKhachHang.getText());
                long now = System.currentTimeMillis();
                Timestamp currenTime = new Timestamp(now);
                PhieuTraDTO pn = new PhieuTraDTO(makh, mp, nvDto.getMNV(), currenTime, phieutraBus.getTIEN(chitietphieu), 1);
                boolean result = phieutraBus.add(pn, chitietphieu);
                if (result) {
                    JOptionPane.showMessageDialog(this, "Trả hàng thành công !");
                    PhieuTra pnlPhieu = new PhieuTra(m, nvDto);
                    m.setPanel(pnlPhieu);
                } else {
                    JOptionPane.showMessageDialog(this, "Trả hàng không thành công !", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}

