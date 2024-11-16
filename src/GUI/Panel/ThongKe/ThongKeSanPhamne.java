package GUI.Panel.ThongKe;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import BUS.ThongKeBUS;
import DAO.ChiTietPhieuNhapDAO;
import DAO.SanPhamDAO;
import DTO.ChiTietPhieuNhapDTO;
import DTO.SanPhamDTO;
import DTO.ThongKe.ThongKeSanPhamDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputDate;
import GUI.Component.InputForm;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import helper.Formater;
import helper.JTableExporter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ThongKeSanPhamne extends JPanel implements ActionListener, KeyListener {

    PanelBorderRadius nhapxuat_left, nhapxuat_center;
    JTable tblKH;
    JScrollPane scrollTblTonKho;
    DefaultTableModel tblModel;
    InputForm tenkhachhang;
    InputDate start_date, end_date;
    ButtonCustom chitietnhap, reset;
    ThongKeBUS thongkebus;
    public static ArrayList<SanPhamDTO> list;
    private JPanel pnmain;
    private int selectedMaSP;

    public ThongKeSanPhamne(ThongKeBUS thongkebus) {
        this.thongkebus = thongkebus;
        list = SanPhamDAO.selectAll1();
        initComponent();
        loadDataTable(list);
    }

    public void initComponent() {
        this.setLayout(new BorderLayout(10, 10));
        this.setOpaque(false);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        nhapxuat_left = new PanelBorderRadius();
        nhapxuat_left.setPreferredSize(new Dimension(300, 100));
        nhapxuat_left.setLayout(new BorderLayout());
        nhapxuat_left.setBorder(new EmptyBorder(0, 0, 0, 5));
        JPanel left_content = new JPanel(new GridLayout(4, 1));
        left_content.setPreferredSize(new Dimension(300, 360));
        nhapxuat_left.add(left_content, BorderLayout.NORTH);

        tenkhachhang = new InputForm("Tìm kiếm sản phẩm");
        tenkhachhang.getTxtForm().putClientProperty("JTextField.showClearButton", true);
        tenkhachhang.getTxtForm().addKeyListener(this);
     
      
        JPanel btn_layout = new JPanel(new BorderLayout());
        JPanel btninner = new JPanel(new GridLayout(1, 2));
        btninner.setOpaque(false);
        btn_layout.setPreferredSize(new Dimension(30, 36));
        btn_layout.setBorder(new EmptyBorder(20, 10, 0, 10));
        btn_layout.setBackground(Color.white);
        chitietnhap = new ButtonCustom("Chi Tiết Nhập", "chitietnhap", 14);
        reset = new ButtonCustom("Làm mới", "danger", 14);

        chitietnhap.addActionListener(this);
        reset.addActionListener(this);

        btninner.add(chitietnhap);
        btninner.add(reset);
        btn_layout.add(btninner, BorderLayout.NORTH);

        left_content.add(tenkhachhang);
//        left_content.add(start_date);
//        left_content.add(end_date);
        left_content.add(btn_layout);

        nhapxuat_center = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(nhapxuat_center, BoxLayout.Y_AXIS);
        nhapxuat_center.setLayout(boxly);

        tblKH = new JTable();
        tblKH.setBackground(new Color(245, 250, 250));

        scrollTblTonKho = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã Sách", "Tên Sách", "Số lô đã nhập", "Tổng tiền nhập", "Tổng tiền xuất"};
        tblModel.setColumnIdentifiers(header);
        tblKH.setModel(tblModel);
        
        tblKH.setAutoCreateRowSorter(true);
        tblKH.setDefaultEditor(Object.class, null);
        scrollTblTonKho.setViewportView(tblKH);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblKH.setDefaultRenderer(Object.class, centerRenderer);
        tblKH.setFocusable(false);
        tblKH.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblKH.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblKH.getColumnModel().getColumn(2).setPreferredWidth(200);
        
        TableSorter.configureTableColumnSorter(tblKH, 0, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tblKH, 1, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tblKH, 2, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tblKH, 3, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(tblKH, 4, TableSorter.VND_CURRENCY_COMPARATOR);
        TableSorter.configureTableColumnSorter(tblKH, 5, TableSorter.VND_CURRENCY_COMPARATOR);
        nhapxuat_center.add(scrollTblTonKho);

        this.add(nhapxuat_left, BorderLayout.WEST);
        this.add(nhapxuat_center, BorderLayout.CENTER);
         tblKH.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting()) {
            int selectedRow = tblKH.getSelectedRow();
            if (selectedRow >= 0) {
                 selectedMaSP = (int) tblKH.getValueAt(selectedRow, 1);
            }
        }
    });
    }

    public boolean validateSelectDate() throws ParseException {
        java.util.Date time_start = start_date.getDate();
        java.util.Date time_end = end_date.getDate();

        java.util.Date current_date = new java.util.Date();
        if (time_start != null && time_start.after(current_date)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            start_date.getDateChooser().setCalendar(null);
            return false;
        }
        if (time_end != null && time_end.after(current_date)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            end_date.getDateChooser().setCalendar(null);
            return false;
        }
        if (time_start != null && time_end != null && time_start.after(time_end)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi !", JOptionPane.ERROR_MESSAGE);
            end_date.getDateChooser().setCalendar(null);
            return false;
        }
        return true;
    }

    public void Fillter() throws ParseException {
        if (validateSelectDate()) {
            String input = tenkhachhang.getText() != null ? tenkhachhang.getText() : "";
         //   java.util.Date time_start = start_date.getDate() != null ? start_date.getDate() : new java.util.Date(0);
          //  java.util.Date time_end = end_date.getDate() != null ? end_date.getDate() : new java.util.Date(System.currentTimeMillis());
          //  this.list = 
          //  loadDataTable(list);
        }
    }

    public void loadDataTable(ArrayList<SanPhamDTO> result) {
        tblModel.setRowCount(0);
        int k = 1;
        for (SanPhamDTO i : result) {
            tblModel.addRow(new Object[]{
                k, i.getMSP() , i.getTEN() , SanPhamDAO.countLoHangByMaSP(i.getMSP()) , 
                ChiTietPhieuNhapDAO.getTotalCostByProductCode(i.getMSP()),
            });
            k++;
        }
    }

    public void resetForm() throws ParseException {
        tenkhachhang.setText("");
        start_date.getDateChooser().setCalendar(null);
        end_date.getDateChooser().setCalendar(null);
        Fillter();
    }

   public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if (source == chitietnhap) {
      
        if (selectedMaSP <=  0) {
            JOptionPane.showMessageDialog(null, 
                "Bạn chưa chọn dòng nào. Vui lòng chọn một dòng trong bảng.", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
        } else {
            // Gọi showTableForm và truyền mã sản phẩm đã chọn
            showTableForm(selectedMaSP);
        }
    } else if (source == reset) {
        try {
            resetForm();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeKhachHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            Fillter();
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(ThongKeKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

   
    public void showTableForm(int maSp) {
        // Tạo JFrame cho form
        JFrame frame = new JFrame("Chi Tiết Lô Hàng");
        frame.setSize(new Dimension(1100, 500));
        frame.setLayout(new BorderLayout(0, 0));

        // Tiêu đề
        HeaderTitle titlePage = new HeaderTitle("CHI TIẾT LÔ HÀNG");
        frame.add(titlePage, BorderLayout.NORTH);

        // Main panel
        pnmain = new JPanel(new BorderLayout());

        // Panel top với các input fields
        JPanel pnmain_top = new JPanel(new GridLayout(1, 2));
        InputForm txtMaPhieu = new InputForm("Mã Sản Phẩm");
        txtMaPhieu.setText(maSp+"");
        InputForm txtThoiGian = new InputForm("Tên Sản Phẩm");
        String ten =  SanPhamDAO.getTenSanPhamByMaSP(maSp);
        txtThoiGian.setText(ten);
        // Disable các input fields
        txtMaPhieu.setDisable();
        txtThoiGian.setDisable();

        pnmain_top.add(txtMaPhieu);
        pnmain_top.add(txtThoiGian);
        pnmain.add(pnmain_top, BorderLayout.NORTH);

        // Panel bottom chứa bảng
        JPanel pnmain_bottom = new JPanel(new BorderLayout());
        pnmain_bottom.setBorder(new EmptyBorder(5, 5, 5, 5));
        pnmain_bottom.setBackground(Color.WHITE);

        // Cấu hình bảng và thêm vào JScrollPane
        JTable table = new JTable();
        tblModel = new DefaultTableModel();
        int tongTien = 0 ; 
        String[] header = {"Nhập vào lô hàng", "Mã Phiếu Nhập", "Gíá Nhập", "Số Lượng", "Tổng tiền nhập"};
        tblModel.setColumnIdentifiers(header);
         ArrayList<ChiTietPhieuNhapDTO> listChiTiet = ChiTietPhieuNhapDAO.selectAllByProductCode(maSp);
         for (ChiTietPhieuNhapDTO chiTiet : listChiTiet) {
              tongTien =  chiTiet.getTIEN()*  chiTiet.getSL();
        Object[] row = {
            chiTiet.getMLH(),
            chiTiet.getMP(),
            chiTiet.getTIEN(),
            chiTiet.getSL() ,   
            tongTien 
           
        };
      
        tblModel.addRow(row);
    }
        table.setModel(tblModel);
        table.setFocusable(false);

        // Căn giữa dữ liệu trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);

        JScrollPane scrollTable = new JScrollPane(table);
        pnmain_bottom.add(scrollTable, BorderLayout.CENTER);

        // Thêm các panel vào JFrame
        pnmain.add(pnmain_bottom, BorderLayout.CENTER);
        frame.add(pnmain, BorderLayout.CENTER);

        // Hiển thị JFrame
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
