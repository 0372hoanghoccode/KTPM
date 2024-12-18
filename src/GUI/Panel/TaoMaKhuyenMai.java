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
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.text.ParseException;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.PlainDocument;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import BUS.MaKhuyenMaiBUS;
import BUS.SanPhamBUS;
import DAO.ChiTietLoHangDAO;
import DAO.KhuVucSach1DAO;
import DAO.MaKhuyenMaiDAO;
import DAO.SanPhamDAO;
import DTO.ChiTietLoHangDTO;
import DTO.ChiTietMaKhuyenMaiDTO;
import DTO.NhanVienDTO;
import DTO.MaKhuyenMaiDTO;
import DTO.SanPhamDTO;
import GUI.Main;
import GUI.Component.ButtonCustom;
import GUI.Component.InputDate;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.PanelBorderRadius;
import GUI.Component.SelectForm;
import helper.Validation;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public final class TaoMaKhuyenMai extends JPanel implements ItemListener, ActionListener {
    
    PanelBorderRadius left, right;
    JTable tableMKM, tableSanPham;
    JScrollPane scrollTablePhieuNhap, scrollTableSanPham;
    JPanel contentCenter, left_top, content_btn, left_bottom;
    DefaultTableModel tblModel, tblModelSP; //table co san 
    ButtonCustom btnAddSp, btnDelete, btnNhapMKM; //, btnImport
    InputForm txtMaKM, txtNhanVien, txtMaSp, txtTenSp, txtPTG,txtGiaNhap, txtGiaBan,txtGiaBia;
    InputDate dateStart, dateEnd;
    JTextField txtTimKiem;
 String[] arrmlh ;
    Main m;
    Color BackgroundColor = new Color(211, 211, 211);

    SanPhamBUS spBUS = new SanPhamBUS();
    MaKhuyenMaiBUS MaKhuyenMaiBus = new MaKhuyenMaiBUS();
    NhanVienDTO nvDto;

    ArrayList<SanPhamDTO> listSP = spBUS.getAll(); // list ben kho 
    ArrayList<SanPhamDTO> listSp_lonhon0 = SanPhamDAO.filterProductsWithPositiveQuantity(listSP);
    ArrayList<SanPhamDTO> listSP_tmp = new ArrayList<>(); 
    ArrayList<ChiTietMaKhuyenMaiDTO> chitietMKM;
    int rowPhieuSelect = -1;
   KhuVucSach1DAO kvs1dao = new KhuVucSach1DAO();

    public TaoMaKhuyenMai(NhanVienDTO nv, String type, Main m ){
        this.nvDto = nv;
        this.m = m;
        chitietMKM = new ArrayList<>();
       
        arrmlh = kvs1dao.getAll1();
        initComponent(type);
        
          loadDataTalbeSanPham(listSp_lonhon0);
       
    }


    public void initComponent(String type){
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        
        //Phieu Nhap
        tableMKM = new JTable();
        tableMKM.setBackground(new Color(245, 250, 250));
        scrollTablePhieuNhap = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã SP", "Tên sản phẩm", "Phần trăm giảm"};
        tblModel.setColumnIdentifiers(header); //thiết lập tiêu đề cột, nhận một tham số là một mảng các chuỗi
        tableMKM.setModel(tblModel);
        scrollTablePhieuNhap.setViewportView(tableMKM);//thiết lập thành phần hiển thị cho viewport. Thành phần hiển thị là một component có thể cuộn (scrollable)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); //phương thức để định dạng văn bản, màu sắc, căn chỉnh và các thuộc tính hiển thị khác cho các ô trong bản
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tableMKM.getColumnModel();
        for (int i = 0; i < 4; i++) {
            if (i != 2) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        tableMKM.getColumnModel().getColumn(2).setPreferredWidth(200);
        tableMKM.setDefaultEditor(Object.class, null);
        tableMKM.setFocusable(false);
        scrollTablePhieuNhap.setViewportView(tableMKM);

        tableMKM.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = tableMKM.getSelectedRow();
                if (index != -1) {
                    tableSanPham.setSelectionMode(index);
                    setFormChiTietPhieu(chitietMKM.get(index));
                    rowPhieuSelect = index;
                    actionbtn("update");
                }
            }
        });

        //Table san pham
        tableSanPham = new JTable();
        scrollTableSanPham = new JScrollPane();
        tblModelSP = new DefaultTableModel();
        String[] headerSP = new String[]{"Mã SP", "Tên sản phẩm", "Giá Bìa"};
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
            // Lấy giá trị cột đầu tiên (mã sản phẩm) và chuyển sang kiểu int
            int columnMaIndex = tableSanPham.convertColumnIndexToModel(0);
            String maValueStr = tableSanPham.getValueAt(index, columnMaIndex).toString();

            try {
                int maValue = Integer.parseInt(maValueStr);
                System.out.println("Cột đầu tiên là: " + maValue);

                // Tìm sản phẩm trong danh sách dựa vào mã
                SanPhamDTO selectedSanPham = listSP.stream()
                    .filter(sp -> sp.getMSP() == maValue) // So sánh mã sản phẩm (int)
                    .findFirst()
                    .orElse(null);

                if (selectedSanPham != null) {
                    resetForm();
                    setInfoSanPham(selectedSanPham);

                    // Kiểm tra tồn tại và thực hiện hành động phù hợp
                    ChiTietMaKhuyenMaiDTO ctp = checkTonTai();
                    if (ctp == null) {
                        actionbtn("add");
                    } else {
                        actionbtn("update");
                        setFormChiTietPhieu(ctp);
                    }
                } else {
                    System.out.println("Không tìm thấy sản phẩm có mã: " + maValue);
                }
            } catch (NumberFormatException ex) {
                System.err.println("Không thể chuyển giá trị cột đầu tiên sang số nguyên: " + maValueStr);
            }
        }
    }
});



        left = new PanelBorderRadius();
        left.setLayout(new BorderLayout(0, 5));
        left.setBackground(Color.white);

        //content_CENTER (content_top + content_bottom) }-> left_top
        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1200, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(5, 5));
        this.add(contentCenter, BorderLayout.CENTER);

        left_top = new JPanel(); // Chứa tất cả phần ở phía trái trên cùng
        left_top.setLayout(new BorderLayout());
        left_top.setBorder(new EmptyBorder(5, 5, 10, 10));
        left_top.setOpaque(false);

        JPanel content_top, content_left, content_right, content_right_top;
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
                 ArrayList<SanPhamDTO> sanphamtimkiemlonhon0 = SanPhamDAO.filterProductsWithPositiveQuantity(rs);
                loadDataTalbeSanPham(sanphamtimkiemlonhon0);
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
        txtTenSp.setDisable();
        txtTenSp.setPreferredSize(new Dimension(100, 90));
        txtMaSp = new InputForm("Mã sản phẩm");
        txtMaSp.setEditable(false);
        txtMaSp.setDisable();
//        txtGiaNhap = new InputForm("Giá Nhập");
//        txtGiaNhap.setEditable(false);
        // txtGiaBan = new InputForm("Giá Bán");
        // txtGiaBan.setEditable(false);
        txtGiaBia = new InputForm("Giá Bìa");
        txtGiaBia.setEditable(false);
        txtGiaBia.setDisable();
        txtPTG = new InputForm("Phần trăm giảm");
        PlainDocument ptg = (PlainDocument) txtPTG.getTxtForm().getDocument();
        ptg.setDocumentFilter((new NumericDocumentFilter())); //chỉ cho nhập số

        JPanel merge1 = new JPanel(new BorderLayout());
        merge1.setPreferredSize(new Dimension(100, 50));
        merge1.add(txtMaSp, BorderLayout.WEST);
        merge1.add(txtPTG,BorderLayout.CENTER);

        JPanel merge2 = new JPanel(new GridLayout());
        merge2.setPreferredSize(new Dimension(100, 80));
       // merge2.add(txtGiaNhap, BorderLayout.WEST);
        merge2.add(txtGiaBia,BorderLayout.CENTER);
        // merge2.add(txtGiaBan, BorderLayout.EAST);
        

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

        // RIGHT 
        right = new PanelBorderRadius();
        right.setPreferredSize(new Dimension(320, 0));
        right.setBorder(new EmptyBorder(5, 5, 5, 5));
        right.setLayout(new BorderLayout());

        JPanel right_top, right_center, right_bottom;
        right_top = new JPanel(new GridLayout(5, 1, 0, 0));
        right_top.setPreferredSize(new Dimension(300, 450));
        right_top.setOpaque(false);
        txtMaKM = new InputForm("Mã khuyến mãi");
        txtMaKM.setDisable();
        txtNhanVien = new InputForm("Nhân viên tạo");
        txtNhanVien.setText(nvDto.getHOTEN());
        txtNhanVien.setDisable();
        txtNhanVien.setEditable(false);
        dateStart = new InputDate("Từ ngày");
        dateEnd = new InputDate("Đến ngày");
      //   cbbLoHang = new SelectForm("Lô Hàng", arrmlh); //Hieusua -thêm cái string lo hang vo
       dateStart.addDateChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    validateSelectDate();
                } catch (ParseException ex) {
                    Logger.getLogger(MaKhuyenMai.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Đăng ký PropertyChangeListener cho ngày kết thúc
        dateEnd.addDateChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    validateSelectDate();
                } catch (ParseException ex) {
                    Logger.getLogger(MaKhuyenMai.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        right_top.add(txtMaKM);
        right_top.add(txtNhanVien);
        right_top.add(dateStart);
        right_top.add(dateEnd);
     //   right_top.add(cbbLoHang);

        right_center = new JPanel();
        right_center.setPreferredSize(new Dimension(100, 100));
        right_center.setOpaque(false);

        right_bottom = new JPanel(new GridLayout(2, 1));
        right_bottom.setPreferredSize(new Dimension(300, 100));
        right_bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
        right_bottom.setOpaque(false);

        btnNhapMKM = new ButtonCustom("Tạo mã khuyến mãi", "excel", 14);
        btnNhapMKM.addActionListener(this);
        right_bottom.add(btnNhapMKM);

        right.add(right_top, BorderLayout.NORTH);
        right.add(right_center, BorderLayout.CENTER);// để trống
        right.add(right_bottom, BorderLayout.SOUTH);

        contentCenter.add(left, BorderLayout.CENTER);
        contentCenter.add(right, BorderLayout.EAST);
         int MKM = MaKhuyenMaiDAO.getMaxMaKhuyenMai()+1;
        txtMaKM.setText(MKM+"");
 
    }

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
       //   String selectedLotCode = (String) cbbLoHang.getSelectedItem();
        this.txtMaSp.setText(Integer.toString(sp.getMSP()));
        this.txtTenSp.setText(sp.getTEN());
        this.txtGiaBia.setText(sp.getTIENX()+"");
        
       // int gianhap = ChiTietLoHangDAO.TuMaLayGiaNhap(selectedLotCode ,sp.getMSP());
         //this.txtGiaNhap.setText(gianhap+"");
       
        
//        txtPTG.setText("0");
//           txtPTG.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//               int giabia = sp.getTIENX();
//            // Lấy phần trăm khuyến mãi từ TextField
//            String phantramKhuyenMaiStr = txtPTG.getText();
//            int phantramKhuyenMai = Integer.parseInt(phantramKhuyenMaiStr);
//            
//            // Tính giá sau khuyến mãi
//            int giasaukhikhuyenmai = giabia - (giabia * phantramKhuyenMai / 100);
//            
//            // Cập nhật giá vào TextField
//            txtGiaBan.setText(String.valueOf(giasaukhikhuyenmai));
//                System.out.print("hello");
//            }
//        });
         //  this.txtGiaNhap.setText(gianhap+"");
        
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

    public void setFormChiTietPhieu(ChiTietMaKhuyenMaiDTO phieu) { //set info vào inputform khi nhan ben tablephieunhap
        SanPhamDTO ctsp = spBUS.getByMaSP(phieu.getMSP());
        this.txtMaSp.setText(Integer.toString(ctsp.getMSP()));
        this.txtTenSp.setText(spBUS.getByMaSP(ctsp.getMSP()).getTEN());
        this.txtPTG.setText(Integer.toString(phieu.getPTG()));
    }
    
    public ChiTietMaKhuyenMaiDTO getInfoChiTietPhieu() {
        int masp = Integer.parseInt(txtMaSp.getText());
        int PTG = Integer.parseInt(txtPTG.getText());
        int mkm =Integer.parseInt( txtMaKM.getText());
        ChiTietMaKhuyenMaiDTO ctmkm = new ChiTietMaKhuyenMaiDTO(mkm, masp, PTG);
        return ctmkm;
    }


    public void loadDataTalbeSanPham(ArrayList<SanPhamDTO> result) {
        tblModelSP.setRowCount(0);
        for (SanPhamDTO sp : result) {
            tblModelSP.addRow(new Object[]{sp.getMSP(), sp.getTEN(), sp.getTIENX()});
        }
    }

    public void loadDataTableChiTietMKM(ArrayList<ChiTietMaKhuyenMaiDTO> ctMKM) {
        tblModel.setRowCount(0);
        int size = ctMKM.size();
        for (int i = 0; i < size; i++) {
            SanPhamDTO pb = spBUS.getByMaSP(ctMKM.get(i).getMSP());
            tblModel.addRow(new Object[]{
                i + 1, pb.getMSP(), spBUS.getByMaSP(pb.getMSP()).getTEN(), 
                ctMKM.get(i).getPTG() + "%"
            });
        }
    }

  public boolean validateNhap() {
    // Kiểm tra nếu trường mã sản phẩm bị rỗng
    if (Validation.isEmpty(txtMaSp.getText())) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm", "Chọn sản phẩm", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    
    // Kiểm tra lỗi cho phần trăm giảm
    String ptgText = txtPTG.getText();
    
    if (Validation.isEmpty(ptgText)) {
        JOptionPane.showMessageDialog(this, "Phần trăm giảm không được để rỗng!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    
    try {
        int ptgValue = Integer.parseInt(ptgText);
        
        if (ptgValue <= 0) {
            JOptionPane.showMessageDialog(this, "Phần trăm giảm phải lớn hơn 0%!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (ptgValue > 100) {
            JOptionPane.showMessageDialog(this, "Phần trăm giảm phải nhỏ hơn hoặc bằng 100%!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Phần trăm giảm phải là số nguyên hợp lệ!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    
    // Kiểm tra nếu trường mã khuyến mãi bị rỗng
    if (Validation.isEmpty(txtMaKM.getText())) {
        JOptionPane.showMessageDialog(this, "Mã khuyến mãi không được để rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    return true;
}



    public void resetForm() {
        this.txtMaSp.setText("");
        this.txtTenSp.setText("");
        this.txtPTG.setText("");
       
    }

    public ChiTietMaKhuyenMaiDTO checkTonTai() {
        ChiTietMaKhuyenMaiDTO p = MaKhuyenMaiBus.findCT(chitietMKM, Integer.parseInt(txtMaSp.getText())); 
        return p;
    }

    public void addCtMKM() { // them sp vao chitietphieu
        ChiTietMaKhuyenMaiDTO ctphieu = getInfoChiTietPhieu();
        ChiTietMaKhuyenMaiDTO p = MaKhuyenMaiBus.findCT(chitietMKM, ctphieu.getMSP());
        txtMaKM.setEditable(false);
        if (p == null) {
            chitietMKM.add(ctphieu);
            loadDataTableChiTietMKM(chitietMKM);
            resetForm();
        } else {
            int input = JOptionPane.showConfirmDialog(this, "Sản phẩm đã tồn tại trong mã !\nBạn có muốn chỉnh sửa không ?", "Sản phẩm đã tồn tại !", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == 0) {
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
            addCtMKM();
            
        } else if (source == btnDelete) {
            int index = tableMKM.getSelectedRow();
            chitietMKM.remove(index);
            actionbtn("add");
            loadDataTableChiTietMKM(chitietMKM);
            resetForm();
        } else if (source == btnNhapMKM) {
            try {
                eventBtnTao();
            } catch (ParseException ex) {
                Logger.getLogger(PhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
public boolean validateSelectDate() throws ParseException {
    Date time_start = dateStart.getDate();
    Date time_end = dateEnd.getDate();
    
    // Kiểm tra nếu ngày bắt đầu hoặc ngày kết thúc là null (rỗng)
    if (time_start == null) {
        JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được để rỗng!", "Lỗi !", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    if (time_end == null) {
        JOptionPane.showMessageDialog(this, "Ngày kết thúc không được để rỗng!", "Lỗi !", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Kiểm tra ngày bắt đầu không được lớn hơn ngày kết thúc
    if (time_start.after(time_end)) {
        JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi !", JOptionPane.ERROR_MESSAGE);
        dateEnd.getDateChooser().setCalendar(null); // Reset ngày kết thúc
        return false;
    }
    
    // Kiểm tra nếu ngày bắt đầu bằng ngày kết thúc
    if (time_start.equals(time_end)) {
        JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi !", JOptionPane.ERROR_MESSAGE);
        dateEnd.getDateChooser().setCalendar(null); // Reset ngày kết thúc
        return false;
    }

    return true;
}



    public void eventBtnTao() throws ParseException {
        if (chitietMKM.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào trong chương trình khuyến mãi!", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
        } else if (Validation.isEmpty(txtMaKM.getText())) {
            JOptionPane.showMessageDialog(this, "Mã khuyến mãi không được để rỗng!", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
        }
        else if(!MaKhuyenMaiBus.checkTT(Integer.parseInt(txtMaKM.getText()))) JOptionPane.showMessageDialog(this, "Mã khuyến mãi đã tồn tại!", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
        else {
            int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn tạo mã khuyến mãi!", "Xác nhận tạo phiếu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (input == 0) if(validateSelectDate()) {
                Date time_start_tmp = dateStart.getDate() != null ? dateStart.getDate() : new Date(System.currentTimeMillis());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time_start_tmp);
                calendar.add(Calendar.DAY_OF_MONTH, 10);
                Date time_end_tmp = dateEnd.getDate() != null ? dateEnd.getDate() : calendar.getTime();
                Timestamp time_start = new Timestamp(time_start_tmp.getTime());
                Timestamp time_end = new Timestamp(time_end_tmp.getTime());
                MaKhuyenMaiDTO MKM = new MaKhuyenMaiDTO(Integer.parseInt(txtMaKM.getText()), nvDto.getMNV(), time_start, time_end, 1 );
                boolean result = MaKhuyenMaiBus.add(MKM, chitietMKM);
                if (result) {
                    JOptionPane.showMessageDialog(this, "Tạo mã thành công !");
                    MaKhuyenMai pnlMKM = new MaKhuyenMai(m, nvDto);
                    m.setPanel(pnlMKM);
                } else {
                    JOptionPane.showMessageDialog(this, "Tạo mã không thành công !", "Cảnh báo !", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    
}