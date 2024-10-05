package GUI.Panel;

import BUS.MaKhuyenMaiBUS;
import BUS.SanPhamBUS;
import DAO.MaKhuyenMaiDAO;
import DTO.MaKhuyenMaiDTO;
import DTO.NhanVienDTO;
import DTO.ChiTietMaKhuyenMaiDTO;
import DTO.SanPhamDTO;
import java.awt.*;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import helper.JTableExporter;
import helper.Formater;
import GUI.Main;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Dialog.MaKhuyenMaiDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import GUI.Component.InputDate;
import java.awt.event.KeyAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class MaKhuyenMai extends JPanel implements ActionListener, ItemListener {

    PanelBorderRadius main, functionBar, leftPanel;
    JPanel contentCenter;
    JTable tableMKM;
    JScrollPane scrollPane;
    JScrollPane scrollTableSanPham;
    MainFunction mainFunction;
    IntegratedSearch search;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    Color BackgroundColor = new Color(211, 211, 211);
    DefaultTableModel tblModel;
    public MaKhuyenMaiBUS mkmBUS = new MaKhuyenMaiBUS();
    public SanPhamBUS spBUS = new SanPhamBUS();

    public ArrayList<MaKhuyenMaiDTO> listMKM = mkmBUS.getAll();
    public ArrayList<ChiTietMaKhuyenMaiDTO> listCTMKM = mkmBUS.getAllct();
    public ArrayList<SanPhamDTO> listSP = spBUS.getAll();

    TaoMaKhuyenMai nhapMKM;
    Main m;
    NhanVienDTO nv;
    private InputDate dateStart;
    private InputDate dateEnd;

    private void initComponent() {
        tableMKM = new JTable();
        tableMKM.setBackground(new Color(245, 250, 250));
        scrollTableSanPham = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã khuyến mãi", "Thời gian bắt đầu", "Thời gian kết thúc", "Trạng thái"};
        tblModel.setColumnIdentifiers(header);
        tableMKM.setModel(tblModel);
        scrollTableSanPham.setViewportView(tableMKM);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tableMKM.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(0).setPreferredWidth(2);
        columnModel.getColumn(2).setPreferredWidth(300);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);
        columnModel.getColumn(2).setCellRenderer(centerRenderer);
        tableMKM.setFocusable(false);

        this.setBackground(BackgroundColor);
        this.setLayout(new GridLayout(1, 1));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setOpaque(true);

        // pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4 chỉ để thêm contentCenter ở giữa mà contentCenter không bị dính sát vào các thành phần khác
        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter);

        // functionBar là thanh bên trên chứa các nút chức năng như thêm xóa sửa, và tìm kiếm
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = {"create", "detail", "delete", "export"};
        mainFunction = new MainFunction(m.user.getMNQ(), "makhuyenmai", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String txt = search.txtSearchForm.getText();
                listMKM = mkmBUS.search(txt);
                loadDataTable(listMKM);
            }
        });

        search.btnReset.addActionListener(this);
        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);

    
        leftPanel = new PanelBorderRadius();
        leftPanel.setPreferredSize(new Dimension(250, 0));
        leftPanel.setLayout(new GridLayout(6, 1, 10, 0));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create and add date filter components
         dateStart = new InputDate("Ngày bắt đầu");
         dateEnd = new InputDate("Ngày kết thúc");
         
      dateStart.addDateChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    Fillter();
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
                    Fillter();
                } catch (ParseException ex) {
                    Logger.getLogger(MaKhuyenMai.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        leftPanel.add(dateStart);
        leftPanel.add(dateEnd);

        contentCenter.add(leftPanel, BorderLayout.WEST);   
        
        
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
//        main.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableSanPham);
       

    }
 

    public MaKhuyenMai(Main m, NhanVienDTO nv) {
        this.m = m;
        this.nv = nv;
        initComponent();
        tableMKM.setDefaultEditor(Object.class, null);
        loadDataTable(listMKM);
    }

public void loadDataTable(ArrayList<MaKhuyenMaiDTO> result) {
    tblModel.setRowCount(0);  
    Date now = new Date();  // Lấy ngày hiện tại

    for (MaKhuyenMaiDTO kvk : result) {
        Date ngayKetThuc = kvk.getTGKT();  // Lấy ngày kết thúc
        int intTrangThai = kvk.getTT();
        String stringTrangThai = "Còn hạn";

        // Kiểm tra nếu ngày kết thúc nhỏ hơn ngày hiện tại
        if (ngayKetThuc.before(now)) {
            stringTrangThai = "Hết hạn";
            intTrangThai = 0; // Đặt trạng thái thành hết hạn

            // Gọi hàm cập nhật trạng thái trong database
            MaKhuyenMaiDAO.updateTrangThaiMaKhuyenMai(kvk.getMKM(), intTrangThai);
        } else {
            stringTrangThai = "Còn hạn";
        }

        tblModel.addRow(new Object[]{
            kvk.getMKM(), 
            Formater.FormatTimeNgayThangNam(kvk.getTGBD()), 
            Formater.FormatTimeNgayThangNam(kvk.getTGKT()), 
            stringTrangThai
        });
    }
}

    public int getRowSelected() {
        int index = tableMKM.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã khuyến mãi");
        }
        return index;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            nhapMKM = new TaoMaKhuyenMai(nv, "create", m);
            m.setPanel(nhapMKM);
        } else if (e.getSource() == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                new MaKhuyenMaiDialog(m, "Thông tin mã khuyến mãi", true, listMKM.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("delete")) {
            int index = getRowSelected();
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa mã khuyến mãi!", "Xóa mã khuyến mãi",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    MaKhuyenMaiDTO mkm = listMKM.get(index);
                    int c = mkmBUS.cancelMKM(mkm.getMKM());
                        if (c == 0) {
                            JOptionPane.showMessageDialog(null, "Xóa mã khuyến mãi không thành công!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa mã khuyến mãi thành công!");
                            loadDataTable(mkmBUS.getAll());
                        }
                }
            }
        } else if (e.getSource() == search.btnReset) {
            search.txtSearchForm.setText("");
            listMKM = mkmBUS.getAll();
            loadDataTable(listMKM);
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tableMKM);
            } catch (IOException ex) {
                Logger.getLogger(MaKhuyenMai.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        String txt = search.txtSearchForm.getText();
        listMKM = mkmBUS.search(txt);
        loadDataTable(listMKM);
    }
    


public void Fillter() throws ParseException {
    if (validateSelectDate()) {
        // Print debug statement
        System.out.println("Filtering data...");

        // Retrieve the start and end dates
        Date time_start = dateStart.getDate();
        Date time_end = dateEnd.getDate();

        // If no start date is selected, use the earliest possible date
        if (time_start == null) {
            time_start = new Date(0); // Set to the earliest possible date (January 1, 1970)
        }

        // If no end date is selected, use the end of the current day
        if (time_end == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date()); // Set to the current date
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            time_end = calendar.getTime();
        }

        // Perform the filtering based on selected dates
        this.listMKM = mkmBUS.fillerPhieuNhap(time_start, time_end);

        // Load the filtered data into the table
        loadDataTable(listMKM);
    }
}

   public boolean validateSelectDate() throws ParseException {
    Date time_start = dateStart.getDate();
    Date time_end = dateEnd.getDate();

  //  Date current_date = new Date();
    
//    // Check if start date is in the future
//    if (time_start != null && time_start.after(current_date)) {
//        JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
//        dateStart.getDateChooser().setCalendar(null);
//        return false;
//    }
//    
//    // Check if end date is in the future
//    if (time_end != null && time_end.after(current_date)) {
//        JOptionPane.showMessageDialog(this, "Ngày kết thúc không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
//        dateEnd.getDateChooser().setCalendar(null);
//        return false;
//    }
    
    // Check if start date is after end date
    if (time_start != null && time_end != null && time_start.after(time_end)) {
        JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi !", JOptionPane.ERROR_MESSAGE);
        dateEnd.getDateChooser().setCalendar(null);
        return false;
    }
     if (time_start.equals(time_end)) {
        JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi !", JOptionPane.ERROR_MESSAGE);
        dateEnd.getDateChooser().setCalendar(null); // Reset ngày kết thúc
        return false;
    }
    
    return true;
}

   


  
    public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  
    public void keyPressed(KeyEvent e) {

    }

  


}
