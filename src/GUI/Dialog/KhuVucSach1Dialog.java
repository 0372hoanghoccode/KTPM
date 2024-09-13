package GUI.Dialog;

import DAO.KhuVucSachDAO;
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

//đây nè
public final class KhuVucSach1Dialog extends JDialog implements ActionListener {

    private KhuVucSach1 jpkvk;
    private HeaderTitle titlePage;
    private JPanel pnmain,  pnmain_top,  pnmain_bottom, pnmain_btn;
    private ButtonCustom btnThem, btnCapNhat;
    private InputForm txtMaPhieu, txtNhanVien, txtThoiGian;
    private KhuVucSachDTO kvk;
    DefaultTableModel tblModel;
    JTable table, tblImei;
    JScrollPane scrollTable;
   


    public KhuVucSach1Dialog(KhuVucSach1 jpkvk, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.jpkvk = jpkvk;
        initComponents(title, type);
    }

    public KhuVucSach1Dialog(KhuVucSach1 jpkvk, JFrame owner, String title, boolean modal, String type, KhuVucSachDTO kvk) {
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
    
        // Tạo panel top với 3 input fields
        pnmain_top = new JPanel(new GridLayout(1, 4));
        txtMaPhieu = new InputForm("Mã phiếu");
        txtNhanVien = new InputForm("Tên nhân viên");
        txtThoiGian = new InputForm("Thời gian tạo");
    
        txtMaPhieu.setEditable(false);
        txtNhanVien.setEditable(false);
        txtThoiGian.setEditable(false);
    
        pnmain_top.add(txtMaPhieu);
        pnmain_top.add(txtNhanVien);
        pnmain_top.add(txtThoiGian);
    
        // Tạo panel bottom với các nút bấm
        pnmain_bottom = new JPanel(new GridLayout(1, 5));
        pnmain_bottom.setBorder(new EmptyBorder(5, 5, 5, 5));
        pnmain_bottom.setBackground(Color.WHITE);
    
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
    
        switch (type) {
            case "create" -> pnmain_btn.add(btnThem);
            case "detail" -> initInfo();
            case "update" -> {
                pnmain_btn.add(btnCapNhat);
                initInfo();
            }
            default -> throw new AssertionError();
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
        txtMaPhieu.setText(kvk.getTenkhuvuc());
        txtNhanVien.setText(kvk.getGhichu());
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
    KhuVucSachDAO dao = KhuVucSachDAO.getInstance();
    
    if (e.getSource() == btnThem && Validation()) {
        String tenkhuvuc1 = this.txtMaPhieu.getText();
        
        // Check for duplicate name
        if (dao.doesNameExist(tenkhuvuc1)) {
            JOptionPane.showMessageDialog(this, "Tên khu vực đã tồn tại. Vui lòng chọn tên khác.");
            return;
        }
        
        int makhuvuc = dao.getAutoIncrement();
        System.out.println("Adding new khu vuc: " + makhuvuc);
        String ghichu1 = this.txtNhanVien.getText();
        KhuVucSachDTO newKhuVuc = new KhuVucSachDTO(makhuvuc, tenkhuvuc1, ghichu1);

        // Adding new khu vuc
        jpkvk.kvkBUS.add(newKhuVuc);
        System.out.println("New khu vuc added: " + newKhuVuc);

        // Reload data table
        jpkvk.loadDataTable(jpkvk.listKVK);
        System.out.println("Data table reloaded.");

        // Close the window
        dispose();
    }
    //  else if (e.getSource() == btnHuyBo) {
    //     System.out.println("Operation cancelled.");
    //     dispose();
    // }
     else if (e.getSource() == btnCapNhat && Validation()) {
        String tenkhuvuc1 = this.txtMaPhieu.getText();
        
        // Check for duplicate name, but skip if it's the same as the current one
        if (dao.doesNameExist(tenkhuvuc1) && !tenkhuvuc1.equals(kvk.getTenkhuvuc())) {
            JOptionPane.showMessageDialog(this, "Tên khu vực đã tồn tại. Vui lòng chọn tên khác.");
            return;
        }
        
        String ghichu1 = this.txtNhanVien.getText();
        KhuVucSachDTO updatedKhuVuc = new KhuVucSachDTO(kvk.getMakhuvuc(), tenkhuvuc1, ghichu1);

        // Update khu vuc
        jpkvk.kvkBUS.update(updatedKhuVuc);
        System.out.println("Khu vuc updated: " + updatedKhuVuc);

        // Reload data table
        jpkvk.loadDataTable(jpkvk.listKVK);
        System.out.println("Data table reloaded.");

        // Close the window
        dispose();
    }
}



}
