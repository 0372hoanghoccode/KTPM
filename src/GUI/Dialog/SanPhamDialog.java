package GUI.Dialog;

import BUS.KhuVucSachBUS;
import BUS.NhaXuatBanBUS;
import BUS.SanPhamBUS;
import DAO.ChiTietLoHangDAO;
import DAO.KhuVucSach1DAO;
import DAO.SanPhamDAO;
import DTO.ChiTietLoHangDTO;
import DTO.SanPhamDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.InputImage;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.SelectForm;
import GUI.Panel.KhuVucSach1;
import GUI.Panel.SanPham;
import helper.Validation;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public final class SanPhamDialog extends JDialog implements ActionListener {

    private HeaderTitle titlePage;
    private JPanel pninfosanpham, pnbottom, pnCenter, pninfosanphamright, pnmain;
    private ButtonCustom  btnAddSanPham;
     SelectForm cbbLoHang;
    InputForm tenSP, tenTG, namXB, danhmuc, masp1;
    InputForm txtsoluong, txtgiaxuat,txtgiabia;
    SelectForm  cbNXB,cbbMKM;
    SelectForm khuvuc;
    InputImage hinhanh;
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    GUI.Panel.SanPham jpSP;

    
    KhuVucSachBUS kvkhoBus = new KhuVucSachBUS();
    NhaXuatBanBUS nxbBus = new NhaXuatBanBUS();
    SanPhamBUS spBus = new SanPhamBUS();
    KhuVucSach1DAO kvs1dao = new KhuVucSach1DAO();
    SanPhamDTO sp;
    String[] arrkhuvuc;
    String[] arrnxb;
    String[] arrmlh ; 
    int masp;
    private ButtonCustom btnSaveCH;
  
    
    public void init(SanPham jpSP) {
        this.jpSP = jpSP;
       masp = SanPhamDAO.getMaxMaSanPham()+1;
    
        arrkhuvuc = kvkhoBus.getArrTenKhuVuc();
        arrnxb = nxbBus.getArrTenNhaXuatBan();        
        arrmlh = kvs1dao.getArrMLH();
//        for (String mlh : arrmlh) {
//        System.out.println("MLH: " + mlh);
//    }
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
        masp =  sp.getMSP();
        initComponents(title, type);
    }

    public void initCardOne(String type) {
        pnCenter = new JPanel(new BorderLayout());
        pninfosanpham = new JPanel(new GridLayout(4, 4, 0, 0));
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
        txtsoluong = new InputForm("Số Lượng");
        txtsoluong.setDisable();
        PlainDocument nhap = (PlainDocument)txtsoluong.getTxtForm().getDocument();
        nhap.setDocumentFilter((new NumericDocumentFilter()));

//        PlainDocument xuat = (PlainDocument)txtgiaxuat.getTxtForm().getDocument();
      //  xuat.setDocumentFilter((new NumericDocumentFilter()));
  //      cbbLoHang = new SelectForm("Lô Hàng",  arrmlh); //Hieusua -thêm cái string lo hang vo
        txtgiabia = new InputForm("Giá Bìa");
          PlainDocument nhap1 = (PlainDocument)txtgiabia.getTxtForm().getDocument();
        nhap1.setDocumentFilter((new NumericDocumentFilter()));
//        txtgiabia.setDisable();
        //cbbMKM = new SelectForm("Mã Khuyến Mãi",  new String[]{"MKM1", "MKM2", "MKM3"});//Hieusua -thêm cái string mkm zo
     
  masp1 = new InputForm("Mã sản phẩm");
        masp1.setVisible(false);
        hinhanh = new InputImage("Hình minh họa");

        pninfosanpham.add(tenSP);
        pninfosanpham.add(danhmuc);
        pninfosanpham.add(namXB);
        pninfosanpham.add(tenTG);
        pninfosanpham.add(cbNXB);
        pninfosanpham.add(khuvuc);
        pninfosanpham.add(txtsoluong);
        pninfosanpham.add(txtgiabia);
   //     pninfosanpham.add(cbbLoHang);
   //   pninfosanpham.add(cbbMKM);

     
        pninfosanphamright.add(hinhanh);
        

        pnbottom = new JPanel(new FlowLayout());
        pnbottom.setBorder(new EmptyBorder(20, 0, 10, 0));
        pnbottom.setBackground(Color.white);
        
        switch (type) {
            case "update" -> {
                initView();
                btnSaveCH = new ButtonCustom("Lưu thông tin", "success", 14);
                btnSaveCH.addActionListener(this);
                 tenSP.setDisable();
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
            case "view" -> {
                   cbbLoHang = new SelectForm("Lô Hàng",  arrmlh); //Hieusua -thêm cái string lo hang vo
       
        txtgiabia.setDisable();
       // cbbMKM = new SelectForm("Mã Khuyến Mãi",  new String[]{"MKM1", "MKM2", "MKM3"});//Hieusua -thêm cái string mkm zo
        pninfosanpham.add(txtgiabia);
        pninfosanpham.add(cbbLoHang);
       // pninfosanpham.add(cbbMKM);
        setInfo(sp);
             ChiTietLoHangDAO dao = new ChiTietLoHangDAO();
        ArrayList<String> mlhList = dao.findMLHByMSP(masp);
        cbbLoHang.setArr(mlhList);
         String selectedLotCode = (String) cbbLoHang.getSelectedItem();
          int quantity = dao.getProductQuantityInLot(selectedLotCode, masp);
                txtsoluong.setText(Integer.toString(quantity));
                //                int gianhap = dao.TuMaLayGiaNhap(selectedLotCode, masp);
             //   int giaBan = gianhap * 2 ; 
             //     txtgiaxuat.setText(Integer.toString(giaBsan));
        cbbLoHang.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedLotCode = (String) cbbLoHang.getSelectedItem();
            if (selectedLotCode != null) {
                // Hiển thị số lượng sản phẩm của mã lô hàng đã chọn
                int quantity = dao.getProductQuantityInLot(selectedLotCode, masp);
               // int gianhap = dao.TuMaLayGiaNhap(selectedLotCode, masp);
               // int giaBan = gianhap * 2 ; 
                txtsoluong.setText(Integer.toString(quantity));
                  //   txtgiaxuat.setText(Integer.toString(giaBan));
            }
        }
    });

   
    khuvuc.setDisable();
    tenSP.setDisable();
    tenTG.setDisable();
    namXB.setDisable();
    danhmuc.setDisable();;
    masp1.setDisable();
    cbNXB.setDisable();
   // cbbLoHang.setDisable();
    hinhanh.setUnable();
                 
            }
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
        SanPhamDTO sp = LayThongTinDeThem();
        sp.setHINHANH(addImage(sp.getHINHANH()));
        if (jpSP.spBUS.add(sp)) {
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công !");
            jpSP.loadDataTalbe(jpSP.listSP);
            dispose();
        }
    }

     public SanPhamDTO LayThongTinDeThem() {
        String vtensp = tenSP.getText();
        String hinhanh = this.hinhanh.getUrl_img();
        String danhMuc = danhmuc.getText();
        int naMXB  = Integer.parseInt(namXB.getText());
        int MNXB = nxbBus.getAll().get(this.cbNXB.getSelectedIndex()).getManxb();
        String TenTG = tenTG.getText();
        int MKVK = kvkhoBus.getAll().get(this.khuvuc.getSelectedIndex()).getMakhuvuc();
        int tIENX = Integer.parseInt(txtgiabia.getText());
       //    ChiTietLoHangDAO dao = new ChiTietLoHangDAO();
    
       // ArrayList<String> mlhList = dao.findMLHByMSP(masp);
   
 
        SanPhamDTO result = new SanPhamDTO(masp, vtensp, hinhanh, danhMuc, naMXB, MNXB, TenTG, MKVK, tIENX, 0);
        // Sản phẩm : int MSP, String TEN, String HINHANH, 
        //String DANHMUC, int NAMXB, int MNXB, String TENTG, int MKVS, int TIENX, String MLH, int SL
        return result;
    }
    
    
    
    
    
    public SanPhamDTO getInfo() {
        String vtensp = tenSP.getText();
        String hinhanh = this.hinhanh.getUrl_img();
        String danhMuc = danhmuc.getText();
        int naMXB  = Integer.parseInt(namXB.getText());
        int MNXB = nxbBus.getAll().get(this.cbNXB.getSelectedIndex()).getManxb();
        String TenTG = tenTG.getText();
        int MKVK = kvkhoBus.getAll().get(this.khuvuc.getSelectedIndex()).getMakhuvuc();
        int tIENX = Integer.parseInt(txtgiabia.getText());
//           ChiTietLoHangDAO dao = new ChiTietLoHangDAO();
//    
//        ArrayList<String> mlhList = dao.findMLHByMSP(masp);
   
  //   Cập nhật ComboBox với danh sách mã lô hàng
//        cbbLoHang.setArr(mlhList);
//        
//        cbbLoHang.addActionListener(new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String selectedLotCode = (String) cbbLoHang.getSelectedItem();
//            if (selectedLotCode != null) {
//                // Hiển thị số lượng sản phẩm của mã lô hàng đã chọn
//                int quantity = dao.getProductQuantityInLot(selectedLotCode, masp);
//                txtsoluong.setText(Integer.toString(quantity));
//            }
//        }
//    });
        SanPhamDTO result = new SanPhamDTO(masp, vtensp, hinhanh, danhMuc, naMXB, MNXB, TenTG, MKVK, tIENX, 0);
        // Sản phẩm : int MSP, String TEN, String HINHANH, 
        //String DANHMUC, int NAMXB, int MNXB, String TENTG, int MKVS, int TIENX, String MLH, int SL
        return result;
    }

    public void setInfo(SanPhamDTO sp) {
      //  txtgiaxuat = new InputForm("Giá bán");
       // txtgiaxuat.setDisable();
     //   pninfosanpham.add(txtgiaxuat);
        hinhanh.setUrl_img(sp.getHINHANH());
        tenSP.setText(sp.getTEN());
        danhmuc.setText(sp.getDANHMUC()); 
        namXB.setText(Integer.toString(sp.getNAMXB()));
        cbNXB.setSelectedIndex(nxbBus.getIndexByMaNXB(sp.getMNXB()));
        tenTG.setText(sp.getTENTG());
        khuvuc.setSelectedIndex(kvkhoBus.getIndexByMaKVK(sp.getMKVS()));
        txtgiabia.setText(Integer.toString(sp.getTIENX()));
       
   
    // Cập nhật ComboBox với danh sách mã lô hàng
//        cbbLoHang.setArr(mlhList);
//         String selectedLotCode = (String) cbbLoHang.getSelectedItem();
//          int quantity = dao.getProductQuantityInLot(selectedLotCode, masp);
//                txtsoluong.setText(Integer.toString(quantity));
//                //                int gianhap = dao.TuMaLayGiaNhap(selectedLotCode, masp);
//             //   int giaBan = gianhap * 2 ; 
//             //     txtgiaxuat.setText(Integer.toString(giaBan));
//        cbbLoHang.addActionListener(new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String selectedLotCode = (String) cbbLoHang.getSelectedItem();
//            if (selectedLotCode != null) {
//                // Hiển thị số lượng sản phẩm của mã lô hàng đã chọn
//                int quantity = dao.getProductQuantityInLot(selectedLotCode, masp);
//               // int gianhap = dao.TuMaLayGiaNhap(selectedLotCode, masp);
//               // int giaBan = gianhap * 2 ; 
//                txtsoluong.setText(Integer.toString(quantity));
//                  //   txtgiaxuat.setText(Integer.toString(giaBan));
//            }
//        }
//    });
  
    }

     public boolean checkCreate() {
           if (Validation.isEmpty(tenSP.getText()) 
                   
                    && Validation.isEmpty(danhmuc.getText()) 
                    && Validation.isEmpty(namXB.getText())
                    && Validation.isEmpty(tenTG.getText()) 
                    && Validation.isEmpty(txtgiabia.getText())) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return false;
            }

           if (Validation.isEmpty(tenSP.getText())) {
    JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm!");
    return false;
} else {
    String productName = tenSP.getText();

    // Kiểm tra dấu cách ở đầu
    if (productName.startsWith(" ")) {
        JOptionPane.showMessageDialog(this, "Tên sản phẩm không được có dấu cách ở đầu!");
        return false;
    }

    // Kiểm tra dấu cách ở cuối
    if (productName.endsWith(" ")) {
        JOptionPane.showMessageDialog(this, "Tên sản phẩm không được có dấu cách ở cuối!");
        return false;
    }
}


            if (Validation.isEmpty((String) cbNXB.getSelectedItem())) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà xuất bản!");
                return false;
            }

          if (Validation.isEmpty(danhmuc.getText())) {
    JOptionPane.showMessageDialog(this, "Vui lòng nhập danh mục!");
    return false;
} else {
    String categoryName = danhmuc.getText();

    // Kiểm tra dấu cách ở đầu
    if (categoryName.startsWith(" ")) {
        JOptionPane.showMessageDialog(this, "Danh mục không được có dấu cách ở đầu!");
        return false;
    }

    // Kiểm tra dấu cách ở cuối
    if (categoryName.endsWith(" ")) {
        JOptionPane.showMessageDialog(this, "Danh mục không được có dấu cách ở cuối!");
        return false;
    }
}


            if (Validation.isEmpty(namXB.getText())) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập năm xuất bản!");
                return false;
            } else {
             String yearText = namXB.getText();
                try {
                int year = Integer.parseInt(yearText);
                int currentYear = java.time.Year.now().getValue();
                if (yearText.length() != 4 || year > currentYear) {
                    JOptionPane.showMessageDialog(this, "Năm xuất bản phải có 4 chữ số và không vượt quá năm hiện tại!");
                    return false;
                }
                  } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Năm xuất bản phải là số!");
                return false;
                 }
            }
if (Validation.isEmpty(tenTG.getText())) {
    JOptionPane.showMessageDialog(this, "Vui lòng nhập tên tác giả!");
    return false;
} else {
    String authorName = tenTG.getText();

    // Kiểm tra dấu cách ở đầu
    if (authorName.startsWith(" ")) {
        JOptionPane.showMessageDialog(this, "Tên tác giả không được có dấu cách ở đầu!");
        return false;
    }

    // Kiểm tra dấu cách ở cuối
    if (authorName.endsWith(" ")) {
        JOptionPane.showMessageDialog(this, "Tên tác giả không được có dấu cách ở cuối!");
        return false;
    }

    // Kiểm tra có chứa số
    if (authorName.matches(".*\\d.*")) {
        JOptionPane.showMessageDialog(this, "Tên tác giả không được chứa số!");
        return false;
    }

    // Kiểm tra ký tự đặc biệt
    if (!authorName.matches("[A-Za-zÀ-ỹ ]+")) {
        JOptionPane.showMessageDialog(this, "Tên tác giả không được chứa ký tự đặc biệt!");
        return false;
    }
}


            if (Validation.isEmpty(txtgiabia.getText())) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá bìa!");
                return false;
            }

            if (hinhanh.getUrl_img() == null) {
                JOptionPane.showMessageDialog(this, "Chưa thêm ảnh sản phẩm!");
                return false;
            }

            return true;
        }


    public boolean checkUpdate() {
        boolean check = true;
        if (Validation.isEmpty(tenSP.getText()) || Validation.isEmpty((String) cbNXB.getSelectedItem())
                || Validation.isEmpty(danhmuc.getText()) || Validation.isEmpty(namXB.getText())
                || Validation.isEmpty(tenTG.getText()) || Validation.isEmpty(txtsoluong.getText())
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
        masp1.setEditable(false);
    }
    public void initCreate() {
        masp1.setEditable(true);
    }
}


