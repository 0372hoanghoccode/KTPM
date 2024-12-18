package GUI.Panel;

import BUS.SanPhamBUS;
import DAO.ChiTietLoHangDAO;
import DAO.KhuVucSachDAO;
import DAO.NhaXuatBanDAO;
import DTO.SanPhamDTO;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Main;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Dialog.SanPhamDialog;
import helper.JTableExporter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public final class SanPham extends JPanel implements ActionListener {
    PanelBorderRadius main, functionBar;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    JTable tableSanPham;
    JScrollPane scrollTableSanPham;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tblModel;
    Main m;
    public SanPhamBUS spBUS = new SanPhamBUS();
    
    public ArrayList<DTO.SanPhamDTO> listSP = spBUS.getAll();

    Color BackgroundColor = new Color(211,211,211);

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        tableSanPham = new JTable();
        tableSanPham.setBackground(new Color(245, 250, 250));  
        scrollTableSanPham = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã SP", "Tên sản phẩm", "Số lượng tồn", "Tên tác giả", 
            "Danh mục", "Năm xuất bản", "Nhà xuất bản", "Khu vực sách","Trạng thái "};
        tblModel.setColumnIdentifiers(header);
        tableSanPham.setModel(tblModel);
        scrollTableSanPham.setViewportView(tableSanPham);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tableSanPham.getColumnModel();
        for (int i = 0; i < header.length; i++) {
            if (i != 1) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(180);
        tableSanPham.setFocusable(false);
        tableSanPham.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableSanPham, 2, TableSorter.INTEGER_COMPARATOR);
        tableSanPham.setDefaultEditor(Object.class, null);
        initPadding();

        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // functionBar là thanh bên trên chứa các nút chức năng như thêm xóa sửa, và tìm kiếm
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

      String[] action = {"create", "update", "delete", "detail", "export"};
        mainFunction = new MainFunction(m.user.getMNQ(), "sanpham", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);
        
        
        
        tableSanPham.addMouseListener(new MouseAdapter() {
    public void mousePressed(MouseEvent e) {
        int index = tableSanPham.getSelectedRow();
        if (index != -1) {
            // Lấy mã lô hàng từ hàng được chọn
            String maLoHang = tableSanPham.getValueAt(index, 0).toString(); // Giả định mã lô hàng nằm ở cột 0
           
             String tt = tableSanPham.getValueAt(index, 8).toString();

            // Kiểm tra trạng thái và ẩn/hiện nút xóa
            if (tt.equals("Nghỉ bán")) {
                mainFunction.btn.get("delete").setVisible(false); // Ẩn nút "delete" nếu TT = 0
                mainFunction.btn.get("update").setVisible(false); // Ẩn nút "delete" nếu TT = 0
            } else {
                mainFunction.btn.get("delete").setVisible(true); // Hiện nút "delete" nếu TT khác 0
                mainFunction.btn.get("update").setVisible(true); // Ẩn nút "delete" nếu TT = 0
            }
           
        }
    }
      });  

        search = new IntegratedSearch(new String[]{"Tất cả","Tên Tác giả","Tên sản phẩm","Đang bán","Hết hàng", "Nghỉ bán"});
    search.txtSearchForm.addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        performSearch(); // Gọi hàm tìm kiếm chung
    }
});

// Lắng nghe sự kiện thay đổi giá trị trong ComboBox
search.cbxChoose.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        performSearch(); // Gọi hàm tìm kiếm chung
    }
});

        search.btnReset.addActionListener((ActionEvent e) -> {
            search.txtSearchForm.setText("");
            listSP = spBUS.getAll();
            loadDataTalbe(listSP);
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableSanPham);
    }

    public SanPham(Main m) {
        this.m = m;
        initComponent();
        loadDataTalbe(listSP);
    }

    public void loadDataTalbe(ArrayList<DTO.SanPhamDTO> result) {
        tblModel.setRowCount(0);
        System.out.print("hello");
        for (DTO.SanPhamDTO sp : result) {
            int tongSoLuong = 0;
            try {
                tongSoLuong = ChiTietLoHangDAO.getTotalQuantityByProduct(sp.getMSP());
            } catch (SQLException ex) {
                Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
              String trangThai;
        if (sp.getTT() == -1) {
            trangThai = "Nghỉ bán";
        } else if (tongSoLuong == 0 && sp.getTT()== 0 ) {
            trangThai = "Hết hàng";
        } else {
            trangThai = "Đang bán";
        }

            tblModel.addRow(new Object[]{sp.getMSP(), sp.getTEN(), tongSoLuong, sp.getTENTG(), sp.getDANHMUC(), sp.getNAMXB()
                , NhaXuatBanDAO.getInstance().selectById(sp.getMNXB() + "").getTennxb()
                , KhuVucSachDAO.getInstance().selectById(sp.getMKVS() + " ").getTenkhuvuc() ,trangThai 
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
                if (e.getSource() == mainFunction.btn.get("create")) {
            new SanPhamDialog(this, owner, "Thêm sản phẩm mới", true, "create");
        } else if (e.getSource() == mainFunction.btn.get("update")) {
  
            int index = getRowSelected();
            if (index != -1) {
            new SanPhamDialog(this, owner, "Chỉnh sửa sản phẩm", true, "update", listSP.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("delete")) {
    int index = getRowSelected();
    if (index != -1) {
        int input = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa Sản phẩm :)!", "Xóa sản phẩm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (input == 0) {

            int sum = 0;
            try {
                sum = ChiTietLoHangDAO.getTotalQuantityByProduct(listSP.get(index).getMSP());
            } catch (SQLException ex) {
                Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (sum == 0) {
                boolean i = spBUS.delete(listSP.get(index));  
                if (i) {
                    System.out.println("Xóa thành công!");
                  
                } else {
                    System.out.println("Xóa thất bại!");
                }
            } else {
                // Thông báo khi sản phẩm vẫn còn số lượng
                JOptionPane.showMessageDialog(null, "Sản phẩm vẫn còn số lượng, không thể xóa!", "Không thể xóa", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    listSP = spBUS.getAll();
  loadDataTalbe(listSP);
}
 else if (e.getSource() == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
             if (index != -1) {
            // Lấy sản phẩm từ danh sách bằng chỉ số
            SanPhamDTO selectedProduct = listSP.get(index);
            
            // Tạo và hiển thị dialog chi tiết sản phẩm
            new SanPhamDialog(this, owner ,  "Xem chi tiết sản phẩm", true, "view", selectedProduct);
        }
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tableSanPham);
            } catch (IOException ex) {
                Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(e.getSource() == mainFunction.btn.get("import")) {
            JOptionPane.showMessageDialog(null, "Chức năng không khả dụng");
        }
    }

    public int getRowSelected() {
        int index = tableSanPham.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm");
        }
        return index;
    }

    private void initPadding() {
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
private void performSearch() {
    String type = (String) search.cbxChoose.getSelectedItem(); // Lấy loại tìm kiếm
    String txt = search.txtSearchForm.getText(); // Lấy từ khóa tìm kiếm
    listSP = spBUS.search(txt, type); // Gọi hàm tìm kiếm
    loadDataTalbe(listSP); // Tải lại bảng dữ liệu
}
}
