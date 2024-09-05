    package GUI.Panel;

import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import BUS.PhieuXuatBUS;
import DTO.PhieuXuatDTO;
import DTO.TaiKhoanDTO;
import GUI.Component.InputDate;
import GUI.Component.InputForm;
import GUI.MainKH;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.SelectForm;
import GUI.Component.TableSorter;
import GUI.Dialog.ChiTietPhieuKHDialog;
import helper.Formater;
import helper.JTableExporter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public final class DonHang extends JPanel implements ActionListener, KeyListener, PropertyChangeListener, ItemListener {

    PanelBorderRadius main, functionBar, box;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableDonHang;
    JScrollPane scrollTableDonHang;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tblModel;
    SelectForm cbxKhachHang, cbxNhanVien;
    InputDate dateStart, dateEnd;
    InputForm moneyMin, moneyMax;

    MainKH m;
    TaiKhoanDTO tk;

    Color BackgroundColor = new Color(193 ,237 ,220);

    ArrayList<PhieuXuatDTO> listPhieuXuat;

    NhanVienBUS nvBUS = new NhanVienBUS();
    PhieuXuatBUS pxBUS = new PhieuXuatBUS();
    KhachHangBUS khachHangBUS = new KhachHangBUS();

    public DonHang(MainKH m, TaiKhoanDTO tk) {
        this.m = m;
        this.tk = tk;
        initComponent();
        this.listPhieuXuat = pxBUS.getAll(tk.getMNV());
        loadDataTalbe(this.listPhieuXuat);
    }

    private void initComponent() {

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = {"detail", "export"};
        mainFunction = new MainFunction(m.user.getMNQ(), "donhang", action);
        functionBar.add(mainFunction);

        //Add Event MouseListener
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã phiếu", "Khách hàng", "Nhân viên xuất"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addKeyListener(this);
        search.btnReset.addActionListener(this);
        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);


        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);

        tableDonHang = new JTable();
        tableDonHang.setBackground(new Color(0xA1D6E2));
        scrollTableDonHang = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã phiếu xuất", "Khách hàng", "Nhân viên", "Thời gian", "Tổng tiền", "Trạng thái"};
        tblModel.setColumnIdentifiers(header);
        tableDonHang.setModel(tblModel);
        tableDonHang.setFocusable(false);
        tableDonHang.setAutoCreateRowSorter(true);
        scrollTableDonHang.setViewportView(tableDonHang);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableDonHang.setDefaultRenderer(Object.class, centerRenderer);
        scrollTableDonHang.setViewportView(tableDonHang);
        tableDonHang.setFocusable(false);
        TableSorter.configureTableColumnSorter(tableDonHang, 0, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableDonHang, 1, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableDonHang, 5, TableSorter.VND_CURRENCY_COMPARATOR);

        main.add(scrollTableDonHang);

    }

    public void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == mainFunction.btn.get("detail")) {
            if (getRow() < 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn phiếu cần xem!");
            } else {
                new ChiTietPhieuKHDialog(m, "Thông tin phiếu xuất", true, pxBUS.getSelect(getRow()));
            }
        } else if (source == search.btnReset) {
            resetForm();
        } else if (source == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tableDonHang);
            } catch (IOException ex) {
                Logger.getLogger(DonHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void loadDataTalbe(ArrayList<PhieuXuatDTO> listphieuxuat) {
        tblModel.setRowCount(0);
        int size = listphieuxuat.size();

        for (int i = 0; i < size; i++) {
            String trangthaiString = "";
            switch (listphieuxuat.get(i).getTT()) {
                case 1 -> {
                    trangthaiString = "Đã duyệt";
                }
                case 0 -> {
                    trangthaiString = "Đã hủy";
                }
                case 2 -> {
                    trangthaiString = "Chờ xử lý";
                }
            }
            tblModel.addRow(new Object[]{
                i + 1,
                listphieuxuat.get(i).getMP(),
                khachHangBUS.getTenKhachHang(listphieuxuat.get(i).getMKH()),
                nvBUS.getNameById(listphieuxuat.get(i).getMNV()),
                Formater.FormatTime(listphieuxuat.get(i).getTG()),
                Formater.FormatVND(listphieuxuat.get(i).getTIEN()), trangthaiString});
        }
    }

    public int getRow() {
        return tableDonHang.getSelectedRow();
    }

    public void Fillter() throws ParseException {
        if (validateSelectDate()) {
            int type = search.cbxChoose.getSelectedIndex();
            int makh = cbxKhachHang.getSelectedIndex() == 0 ? 0 : khachHangBUS.getByIndex(cbxKhachHang.getSelectedIndex() - 1).getMaKH();
            int manv = cbxNhanVien.getSelectedIndex() == 0 ? 0 : nvBUS.getByIndex(cbxNhanVien.getSelectedIndex() - 1).getMNV();
            String input = search.txtSearchForm.getText() != null ? search.txtSearchForm.getText() : "";
            Date time_start = dateStart.getDate() != null ? dateStart.getDate() : new Date(0);
            Date time_end = dateEnd.getDate() != null ? dateEnd.getDate() : new Date(System.currentTimeMillis());
            String min_price = moneyMin.getText();
            String max_price = moneyMax.getText();
            this.listPhieuXuat = pxBUS.fillerPhieuXuat(type, input, makh, manv, time_start, time_end, min_price, max_price);
            loadDataTalbe(listPhieuXuat);
        }
    }

    public void resetForm() {
        this.listPhieuXuat = pxBUS.getAll();
        loadDataTalbe(listPhieuXuat);
    }

    public boolean validateSelectDate() throws ParseException {
        Date time_start = dateStart.getDate();
        Date time_end = dateEnd.getDate();

        Date current_date = new Date();
        if (time_start != null && time_start.after(current_date)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            dateStart.getDateChooser().setCalendar(null);
            return false;
        }
        if (time_end != null && time_end.after(current_date)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            dateEnd.getDateChooser().setCalendar(null);
            return false;
        }
        if (time_start != null && time_end != null && time_start.after(time_end)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            dateEnd.getDateChooser().setCalendar(null);
            return false;
        }
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            Fillter();
        } catch (ParseException ex) {
            Logger.getLogger(DonHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            Fillter();
        } catch (ParseException ex) {
            Logger.getLogger(DonHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        try {
            Fillter();
        } catch (ParseException ex) {
            Logger.getLogger(DonHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
