package GUI.Dialog;

import DAO.KhuVucSachDAO;
import DTO.KhuVucSachDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Panel.KhuVucSach;
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

public final class KhuVucSach1Dialog extends JDialog implements ActionListener {

    private KhuVucSach jpkvk;
    private HeaderTitle titlePage;
    private JPanel pnmain, pnbottom;
    private ButtonCustom btnThem, btnCapNhat;
    private InputForm tenkhuvuc;
    private InputForm ghichu;
    private KhuVucSachDTO kvk;

    public KhuVucSach1Dialog(KhuVucSach jpkvk, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.jpkvk = jpkvk;
        initComponents(title, type);
    }

    public KhuVucSach1Dialog(KhuVucSach jpkvk, JFrame owner, String title, boolean modal, String type, KhuVucSachDTO kvk) {
        super(owner, title, modal);
        this.jpkvk = jpkvk;
        this.kvk = kvk;
        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        this.setSize(new Dimension(500, 360));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());
        pnmain = new JPanel(new GridLayout(2, 2, 20, 0));
        pnmain.setBackground(Color.white);
        tenkhuvuc = new InputForm("Tên khu vực sách");
        ghichu = new InputForm("Ghi chú");

        pnmain.add(tenkhuvuc);
        pnmain.add(ghichu);

        pnbottom = new JPanel(new FlowLayout());
        pnbottom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnbottom.setBackground(Color.white);
        btnThem = new ButtonCustom("Thêm khu vực sách", "success", 14);
        btnCapNhat = new ButtonCustom("Lưu thông tin", "success", 14);
        // btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);

        //Add MouseListener btn
        btnThem.addActionListener(this);
        btnCapNhat.addActionListener(this);
        // btnHuyBo.addActionListener(this);

        switch (type) {
            case "create" -> pnbottom.add(btnThem);
            case "update" -> {
                pnbottom.add(btnCapNhat);
                initInfo();
            }
            default -> throw new AssertionError();
        }
        // pnbottom.add(btnHuyBo);
        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnmain, BorderLayout.CENTER);
        this.add(pnbottom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void initInfo() {
        tenkhuvuc.setText(kvk.getTenkhuvuc());
        ghichu.setText(kvk.getGhichu());
    }

       boolean Validation(){
        if (Validation.isEmpty(tenkhuvuc.getText())) {
            JOptionPane.showMessageDialog(this, "Tên khu vực sách không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
         }
          return true;
    }
    @Override
 public void actionPerformed(ActionEvent e) {
    KhuVucSachDAO dao = KhuVucSachDAO.getInstance();
    
    if (e.getSource() == btnThem && Validation()) {
        String tenkhuvuc1 = this.tenkhuvuc.getText();
        
        // Check for duplicate name
        if (dao.doesNameExist(tenkhuvuc1)) {
            JOptionPane.showMessageDialog(this, "Tên khu vực đã tồn tại. Vui lòng chọn tên khác.");
            return;
        }
        
        int makhuvuc = dao.getAutoIncrement();
        System.out.println("Adding new khu vuc: " + makhuvuc);
        String ghichu1 = this.ghichu.getText();
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
        String tenkhuvuc1 = this.tenkhuvuc.getText();
        
        // Check for duplicate name, but skip if it's the same as the current one
        if (dao.doesNameExist(tenkhuvuc1) && !tenkhuvuc1.equals(kvk.getTenkhuvuc())) {
            JOptionPane.showMessageDialog(this, "Tên khu vực đã tồn tại. Vui lòng chọn tên khác.");
            return;
        }
        
        String ghichu1 = this.ghichu.getText();
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
