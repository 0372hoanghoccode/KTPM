package GUI.Dialog;

import BUS.KhuVucSachBUS;
import BUS.NhaXuatBanBUS;
import BUS.SanPhamBUS;
import DAO.SanPhamDAO;
import DTO.SanPhamDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.InputImage;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.SelectForm;
import GUI.Panel.SanPham;
import helper.Validation;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.PlainDocument;

public final class SanPhamDialog extends JDialog implements ActionListener {

    private HeaderTitle titlePage;
    private JPanel pninfosanpham, pnbottom, pnCenter, pninfosanphamright, pnmain;
    private ButtonCustom  btnAddSanPham;
    private SelectForm cbbLoHang;
    InputForm tenSP, tenTG, namXB, danhmuc, isbn;
    InputForm txtgianhap, txtgiaxuat;
    SelectForm  cbNXB;
    SelectForm khuvuc;
    InputImage hinhanh;
    JTable tblcauhinh;
    JScrollPane scrolltblcauhinh;
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    GUI.Panel.SanPham jpSP;

    
    KhuVucSachBUS kvkhoBus = new KhuVucSachBUS();
    NhaXuatBanBUS nxbBus = new NhaXuatBanBUS();
    SanPhamBUS spBus = new SanPhamBUS();

    SanPhamDTO sp;
    String[] arrkhuvuc;
    String[] arrnxb;
    int masp;
    private ButtonCustom btnSaveCH;

    public void init(SanPham jpSP) {
        this.jpSP = jpSP;
        masp = jpSP.spBUS.spDAO.getAutoIncrement();
        arrkhuvuc = kvkhoBus.getArrTenKhuVuc();
        arrnxb = nxbBus.getArrTenNhaXuatBan();        
    }

    public SanPhamDialog(SanPham jpSP, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        init(jpSP);
        initComponents(title, type);
    }

    public SanPhamDialog(SanPham jpSP, JFrame owner, String title, boolean modal, String type, SanPhamDTO sp) {
        super(owner, title, modal);
        init(jpSP);
        this.sp = sp;
        initComponents(title, type);
    }

    public void initCardOne(String type) {
        pnCenter = new JPanel(new BorderLayout());
        pninfosanpham = new JPanel(new GridLayout(3, 4, 0, 0));
        pninfosanpham.setBackground(Color.WHITE);
        pnCenter.add(pninfosanpham, BorderLayout.CENTER);

        pninfosanphamright = new JPanel();
        pninfosanphamright.setBackground(Color.WHITE);
        pninfosanphamright.setPreferredSize(new Dimension(300, 600));
        pninfosanphamright.setBorder(new EmptyBorder(0, 10, 0, 10));
        pnCenter.add(pninfosanphamright, BorderLayout.WEST);

        tenSP = new InputForm("Tên sản phẩm");
        danhmuc = new InputForm("Danh mục");
        namXB = new InputForm("Năm xuất bản");
        tenTG = new InputForm("Tên tác giả");
        cbNXB = new SelectForm("Nhà xuất bản", arrnxb);
        khuvuc = new SelectForm("Khu vực sách", arrkhuvuc);
        PlainDocument NamXB = (PlainDocument)namXB.getTxtForm().getDocument();
        NamXB.setDocumentFilter((new NumericDocumentFilter()));
        txtgianhap = new InputForm("Số Lượng");
        PlainDocument nhap = (PlainDocument)txtgianhap.getTxtForm().getDocument();
        nhap.setDocumentFilter((new NumericDocumentFilter()));
        txtgiaxuat = new InputForm("Giá xuất");
        PlainDocument xuat = (PlainDocument)txtgiaxuat.getTxtForm().getDocument();
        xuat.setDocumentFilter((new NumericDocumentFilter()));
        cbbLoHang = new SelectForm("Lô Hàng",  new String[]{"Lô 1", "Lô2", "Lô 3"}); //Hieusua -thêm cái string lo hang vo


        // isbn.setVisible(false);
        hinhanh = new InputImage("Hình minh họa");

        pninfosanpham.add(tenSP);
        pninfosanpham.add(danhmuc);
        pninfosanpham.add(namXB);
        pninfosanpham.add(tenTG);
        pninfosanpham.add(cbNXB);
        pninfosanpham.add(khuvuc);
        pninfosanpham.add(txtgianhap);
        pninfosanpham.add(txtgiaxuat);
        pninfosanpham.add(cbbLoHang);
        pninfosanphamright.add(hinhanh);

        pnbottom = new JPanel(new FlowLayout());
        pnbottom.setBorder(new EmptyBorder(20, 0, 10, 0));
        pnbottom.setBackground(Color.white);
        
        switch (type) {
            case "update" -> {
                initView();
                btnSaveCH = new ButtonCustom("Lưu thông tin", "success", 14);
                btnSaveCH.addActionListener(this);
                pnbottom.add(btnSaveCH);
            }
            case "create" -> {
                initCreate();
                btnAddSanPham = new ButtonCustom("Thêm sản phẩm", "success", 14);
                btnAddSanPham.addActionListener(this);
                pnbottom.add(btnAddSanPham);
            }
        }

        // btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        // btnHuyBo.addActionListener(this);
        // pnbottom.add(btnHuyBo);
        pnCenter.add(pnbottom, BorderLayout.SOUTH);
    }

    public void initComponents(String title, String type) {
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        this.setSize(new Dimension(1150, 480));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());

        pnmain = new JPanel(new CardLayout());

        initCardOne(type);

        pnmain.add(pnCenter);

        switch (type) {
            case "view" -> setInfo(sp);
            case "update" -> setInfo(sp);
            default -> {
            }
        }
//                throw new AssertionError();

        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnmain, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public String addImage(String urlImg) {
        Random randomGenerator = new Random();
        int ram = randomGenerator.nextInt(1000);
        File sourceFile = new File(urlImg);
        String destPath = "./src/img_product";
        File destFolder = new File(destPath);
        String newName = ram + sourceFile.getName();
        try {
            Path dest = Paths.get(destFolder.getPath(), newName);
            Files.copy(sourceFile.toPath(), dest);
        } catch (IOException e) {
        }
        return newName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        // if(source == btnHuyBo){
        //     dispose();
        // }
         if (source == btnAddSanPham && checkCreate()) {
            eventAddSanPham();
        }  
    else if(source == btnSaveCH) {
    // Lấy thông tin sản phẩm mới từ phương thức getInfo()
    SanPhamDTO snNew = getInfo();

    // Lấy số lượng hiện tại của sản phẩm từ cơ sở dữ liệu
    //int currentQuantity = spBus.getSPbyISBN(snNew.getMSP()).getSL();
    //snNew.setSL(currentQuantity);

    // Kiểm tra xem có thay đổi hình ảnh không
    if(!snNew.getHINHANH().equals(this.sp.getHINHANH())) {
        // Cập nhật hình ảnh mới
        snNew.setHINHANH(addImage(snNew.getHINHANH()));
    }

    // Đảm bảo mã sản phẩm mới không thay đổi
    snNew.setMSP(this.sp.getMSP());

    // Cập nhật thông tin sản phẩm trong cơ sở dữ liệu
    SanPhamDAO.getInstance().update(snNew);

    // Cập nhật thông tin sản phẩm trong danh sách
    this.jpSP.spBUS.update(snNew);

    // Tải lại dữ liệu vào bảng
    this.jpSP.loadDataTalbe(this.jpSP.spBUS.getAll());

    // Hiển thị thông báo thành công
    JOptionPane.showMessageDialog(this, "Sửa thông tin sản phẩm thành công!");
}

        
    }

    public void eventAddSanPham() {
        SanPhamDTO sp = getInfo();
        sp.setHINHANH(addImage(sp.getHINHANH()));
        if (jpSP.spBUS.add(sp)) {
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công !");
            jpSP.loadDataTalbe(jpSP.listSP);
            dispose();
        }
    }

    public SanPhamDTO getInfo() {
        String vtensp = tenSP.getText();
        String hinhanh = this.hinhanh.getUrl_img();
        String danhMuc = danhmuc.getText();
        int naMXB  = Integer.parseInt(namXB.getText());
        int MNXB = nxbBus.getAll().get(this.cbNXB.getSelectedIndex()).getManxb();
        String TenTG = tenTG.getText();
        int MKVK = kvkhoBus.getAll().get(this.khuvuc.getSelectedIndex()).getMakhuvuc();
        int tIENX = Integer.parseInt(txtgiaxuat.getText());
        int tIENN = Integer.parseInt(txtgianhap.getText());
     //   String ISBN = isbn.getText();
        SanPhamDTO result = new SanPhamDTO(masp, vtensp, hinhanh, danhMuc, naMXB, MNXB, TenTG, MKVK, tIENX, tIENN, 0, null);
        return result;
    }

    public void setInfo(SanPhamDTO sp) {
        hinhanh.setUrl_img(sp.getHINHANH());
        tenSP.setText(sp.getTEN());
        danhmuc.setText(sp.getDANHMUC());
        namXB.setText(Integer.toString(sp.getNAMXB()));
        cbNXB.setSelectedIndex(nxbBus.getIndexByMaNXB(sp.getMNXB()));
        tenTG.setText(sp.getTENTG());
        khuvuc.setSelectedIndex(kvkhoBus.getIndexByMaKVK(sp.getMKVS()));
        txtgiaxuat.setText(Integer.toString(sp.getTIENX()));
        txtgianhap.setText(Integer.toString(sp.getTIENN()));
        cbbLoHang.setSelectedItem(sp.getISBN()); //Hieusua - sửa lại set lô hàng
    }


    public boolean checkCreate() {
        boolean check = true;
        if (Validation.isEmpty(tenSP.getText()) || Validation.isEmpty((String) cbNXB.getSelectedItem())
                || Validation.isEmpty(danhmuc.getText()) || Validation.isEmpty(namXB.getText())
                || Validation.isEmpty(tenTG.getText()) || Validation.isEmpty(txtgianhap.getText())
                || Validation.isEmpty(txtgiaxuat.getText()) 
                ) {
        //    || Validation.isEmpty(isbn.getText())
            check = false;
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin !");
        } 
//        else if(!spBus.checkISBN(isbn.getText())) {
//                JOptionPane.showMessageDialog(this, "Mã ISBN đã tồn tại!"); 
//                check = false;
//            }
            else {
                if(hinhanh.getUrl_img() == null) {
                    JOptionPane.showMessageDialog(this, "Chưa thêm ảnh sản phẩm!"); 
                    check = false;
                }
            }
        return check;
    }

    public boolean checkUpdate() {
        boolean check = true;
        if (Validation.isEmpty(tenSP.getText()) || Validation.isEmpty((String) cbNXB.getSelectedItem())
                || Validation.isEmpty(danhmuc.getText()) || Validation.isEmpty(namXB.getText())
                || Validation.isEmpty(tenTG.getText()) || Validation.isEmpty(txtgianhap.getText())
                || Validation.isEmpty(txtgiaxuat.getText()) ) {
        //    || Validation.isEmpty(isbn.getText())
            check = false;
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin !");
        }
            else {
                if(hinhanh.getUrl_img() == null) {
                    JOptionPane.showMessageDialog(this, "Chưa thêm ảnh sản phẩm!"); 
                    check = false;
                }
            }
        return check;
    }
    public void initView() {
        isbn.setEditable(false);
    }
    public void initCreate() {
        isbn.setEditable(true);
    }
}


