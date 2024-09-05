package GUI.Dialog;

import BUS.NhaXuatBanBUS;
import BUS.SanPhamBUS;
import DTO.SanPhamDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.InputImage;
import GUI.Component.NumericDocumentFilter;
import GUI.Panel.SanPhamKH;
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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.PlainDocument;

public final class SanPhamKHDialog extends JDialog implements ActionListener {

    private HeaderTitle titlePage;
    private JPanel pninfosanpham, pnbottom, pnCenter, pninfosanphamright, pnmain;
    private ButtonCustom btnHuyBo;
    InputForm tenSP, tenTG, namXB, danhmuc, nxb;
    InputForm txtgiaxuat;
    InputImage hinhanh;
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    GUI.Panel.SanPhamKH jpSP;

    
    NhaXuatBanBUS nxbBus = new NhaXuatBanBUS();
    SanPhamBUS spBus = new SanPhamBUS();

    SanPhamDTO sp;
    int masp;

    public void init(SanPhamKH jpSP) {
        this.jpSP = jpSP;
        masp = jpSP.spBUS.spDAO.getAutoIncrement();
    }

    public SanPhamKHDialog(SanPhamKH jpSP, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        init(jpSP);
        initComponents(title, type);
    }

    public SanPhamKHDialog(SanPhamKH jpSP, JFrame owner, String title, boolean modal, String type, SanPhamDTO sp) {
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
        tenSP.setEditable(false);
        danhmuc = new InputForm("Danh mục");
        danhmuc.setEditable(false);
        namXB = new InputForm("Năm xuất bản");
        namXB.setEditable(false);
        tenTG = new InputForm("Tên tác giả");
        tenTG.setEditable(false);
        nxb = new InputForm("Nhà xuất bản");
        nxb.setEditable(false);
        PlainDocument NamXB = (PlainDocument)namXB.getTxtForm().getDocument();
        NamXB.setDocumentFilter((new NumericDocumentFilter()));
        txtgiaxuat = new InputForm("Giá bán");
        txtgiaxuat.setEditable(false);
        PlainDocument xuat = (PlainDocument)txtgiaxuat.getTxtForm().getDocument();
        xuat.setDocumentFilter((new NumericDocumentFilter()));
        hinhanh = new InputImage("Hình minh họa");

        pninfosanpham.add(tenSP);
        pninfosanpham.add(danhmuc);
        pninfosanpham.add(namXB);
        pninfosanpham.add(tenTG);
        pninfosanpham.add(nxb);
        pninfosanpham.add(txtgiaxuat);
        pninfosanphamright.add(hinhanh);

        pnbottom = new JPanel(new FlowLayout());
        pnbottom.setBorder(new EmptyBorder(20, 0, 10, 0));
        pnbottom.setBackground(Color.white);
        

        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnHuyBo.addActionListener(this);
        pnbottom.add(btnHuyBo);
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
//                throw new AssertionError();

        switch (type) {
            case "view" -> setInfo(sp);
            default -> {
            }
        }
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
        if(source == btnHuyBo){
            dispose();
        }
        
    }

    public void setInfo(SanPhamDTO sp) {
        hinhanh.setUrl_img(sp.getHINHANH());
        tenSP.setText(sp.getTEN());
        danhmuc.setText(sp.getDANHMUC());
        namXB.setText(Integer.toString(sp.getNAMXB()));
        nxb.setText(nxbBus.getTenNhaXuatBan(sp.getMNXB()));
        tenTG.setText(sp.getTENTG());
        txtgiaxuat.setText(Integer.toString(sp.getTIENX()));
    }

}


