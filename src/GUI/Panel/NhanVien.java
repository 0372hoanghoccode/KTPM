package GUI.Panel;

import BUS.NhanVienBUS;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Main;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public final class NhanVien extends JPanel {

    public JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    NhanVienBUS nvBus = new NhanVienBUS(this);
    PanelBorderRadius main, functionBar;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableNhanVien;
    JScrollPane scrollTableSanPham;
    MainFunction mainFunction;
    public IntegratedSearch search;
    Main m;
    ArrayList<DTO.NhanVienDTO> listnv = nvBus.getAlltrangthaifull();

    Color BackgroundColor = new Color(211, 211, 211);
    private DefaultTableModel tblModel;

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4 chỉ để thêm contentCenter ở giữa mà contentCenter không bị dính sát vào các thành phần khác
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

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // functionBar là thanh bên trên chứa các nút chức năng như thêm xóa sửa, và tìm kiếm
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentCenter.add(functionBar, BorderLayout.NORTH);

        String[] action = {"create", "update", "delete", "detail", "import", "export"};
        mainFunction = new MainFunction(m.user.getMNQ(), "nhanvien", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(nvBus);
        }
        functionBar.add(mainFunction);
        search = new IntegratedSearch(new String[]{"Tất cả", "Họ tên", "Email" });
    //    , "Còn làm việc", "Ngưng làm việc"
        functionBar.add(search);
        search.btnReset.addActionListener(nvBus);
        search.cbxChoose.addActionListener(nvBus);
        search.txtSearchForm.getDocument().addDocumentListener(new NhanVienBUS(search.txtSearchForm, this));

        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
//        main.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentCenter.add(main, BorderLayout.CENTER);

        tableNhanVien = new JTable();
        tableNhanVien.setBackground(new Color(245, 250, 250));
        scrollTableSanPham = new JScrollPane();
        tableNhanVien = new JTable();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"MNV", "Họ tên", "Giới tính", "Ngày Sinh", "SDT", "Email","Trạng thái"};

        tblModel.setColumnIdentifiers(header);
        tableNhanVien.setModel(tblModel);
        tableNhanVien.setFocusable(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        tableNhanVien.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableNhanVien.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableNhanVien.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableNhanVien.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableNhanVien.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableNhanVien.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableNhanVien.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        scrollTableSanPham.setViewportView(tableNhanVien);
        main.add(scrollTableSanPham);
        tableNhanVien.getSelectionModel().addListSelectionListener(event -> {
    if (!event.getValueIsAdjusting()) { // Đảm bảo chỉ xử lý khi việc chọn hoàn tất
        int selectedRow = tableNhanVien.getSelectedRow(); // Lấy dòng được chọn
        if (selectedRow >= 0) {
            String trangThai = tableNhanVien.getValueAt(selectedRow, tableNhanVien.getColumnCount() - 1).toString(); // Lấy giá trị cột cuối cùng
            if ("Đã ngưng làm việc".equalsIgnoreCase(trangThai.trim())) {
              mainFunction.btn.get("delete").setVisible(false); // Ẩn nút "delete" nếu TT = 0
              mainFunction.btn.get("update").setVisible(false); // Ẩn nút "delete" nếu TT = 0
            } else {
              mainFunction.btn.get("delete").setVisible(true); // Hiện nút "delete" nếu TT khác 0
                    mainFunction.btn.get("update").setVisible(true); // Ẩn nút "delete" nếu TT = 0
            }
        }
    }
});

    }

    

    public NhanVien(Main m) {
        this.m = m;
        initComponent();
        tableNhanVien.setDefaultEditor(Object.class, null);
        loadDataTalbe(listnv);
    }

    public int getRow() {
        return tableNhanVien.getSelectedRow();
    }

    public DTO.NhanVienDTO getNhanVien() {
        return listnv.get(tableNhanVien.getSelectedRow());
    }

    public void loadDataTalbe(ArrayList<DTO.NhanVienDTO> list) {
        listnv = list;
        tblModel.setRowCount(0);
          String TTstr =  "";
        for (DTO.NhanVienDTO nhanVien : listnv) {
              int TT = nhanVien.getTT();
            
               if (TT == 1 ){
                   TTstr = "Đang làm việc";
               }
               if (TT == -1 ){
                   TTstr = "Đã ngưng làm việc" ; 
               }
            tblModel.addRow(new Object[]{
              
                nhanVien.getMNV(), nhanVien.getHOTEN(), nhanVien.getGIOITINH() == 1 ? "Nam" : "Nữ", nhanVien.getNGAYSINH(), nhanVien.getSDT(), nhanVien.getEMAIL() , TTstr
            });
        }
    }
}
