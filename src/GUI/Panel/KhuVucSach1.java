package GUI.Panel;

import BUS.KhuVucSach1BUS;
import BUS.SanPhamBUS;
import DAO.ChiTietLoHangDAO;
import DAO.KhuVucSach1DAO;
import DTO.ChiTietLoHangDTO;
import DTO.KhuVucSach1DTO;

import DTO.SanPhamDTO;
import java.awt.*;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import helper.JTableExporter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import GUI.Main;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.itemTaskbar;
import GUI.Dialog.ChiTietPhieuDialog;
import GUI.Dialog.KhuVucSachDialog;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import GUI.Dialog.KhuVucSach1Dialog;

public final class KhuVucSach1 extends JPanel implements ActionListener, ItemListener {

    PanelBorderRadius main, functionBar;
    JPanel contentCenter;
    JTable tableKhuvuc;
    JScrollPane scrollPane;
    JScrollPane scrollTableSanPham;
    MainFunction mainFunction;
    IntegratedSearch search;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    Color BackgroundColor = new Color(211,211,211);
    DefaultTableModel tblModel;
    Main m;
    public KhuVucSach1DAO loHangDAO = new KhuVucSach1DAO();
    public KhuVucSach1BUS loHangbus = new KhuVucSach1BUS();
    public SanPhamBUS spBUS = new SanPhamBUS();

    public ArrayList<KhuVucSach1DTO> listKVK = loHangDAO.getAll();
    
    public ArrayList<SanPhamDTO> listSP = spBUS.getAll();

    private void initComponent() {
        scrollTableSanPham = new JScrollPane();
        tableKhuvuc = new JTable();
        tblModel = new DefaultTableModel();
        tableKhuvuc.setBackground(new Color(245, 250, 250)); 
        String[] header = new String[]{"Mã lô","Thời gian tạo", "Tổng số sản phẩm", "Tổng tiền", "Trạng thái"};
        tblModel.setColumnIdentifiers(header);
        tableKhuvuc.setModel(tblModel);
        scrollTableSanPham.setViewportView(tableKhuvuc);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tableKhuvuc.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(0).setPreferredWidth(2);
        columnModel.getColumn(2).setPreferredWidth(300);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);
        columnModel.getColumn(2).setCellRenderer(centerRenderer);
        columnModel.getColumn(3).setCellRenderer(centerRenderer);
        columnModel.getColumn(4).setCellRenderer(centerRenderer);
        tableKhuvuc.setFocusable(false);
tableKhuvuc.addMouseListener(new MouseAdapter() {
    @Override
    public void mousePressed(MouseEvent e) {
        int index = tableKhuvuc.getSelectedRow();
        if (index != -1) {
            // Lấy mã lô hàng từ hàng được chọn
            String maLoHang = tableKhuvuc.getValueAt(index, 0).toString(); // Giả định mã lô hàng nằm ở cột 0
           
             String tt = tableKhuvuc.getValueAt(index, 4).toString();

            // Kiểm tra trạng thái và ẩn/hiện nút xóa
            if (tt.equals("Đã xóa")) {
                mainFunction.btn.get("delete").setVisible(false); // Ẩn nút "delete" nếu TT = 0
            } else {
                mainFunction.btn.get("delete").setVisible(true); // Hiện nút "delete" nếu TT khác 0
            }
            ChiTietLoHangDAO chiTietLoHangBUS = new ChiTietLoHangDAO();
            ArrayList<ChiTietLoHangDTO> listChiTietLoHang = chiTietLoHangBUS.getByMaLoHang(maLoHang);
        }
    }
});
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

        String[] action = {"create", "detail","delete", "import", "export"};
        mainFunction = new MainFunction(m.user.getMNQ(), "khuvucsach", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả" , "Hoạt động", "Đã xóa",  "Mã lô hàng"});
        search.cbxChoose.addItemListener(this);
    search.txtSearchForm.addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        String type = (String) search.cbxChoose.getSelectedItem();
        String txt = search.txtSearchForm.getText();
        listKVK = loHangDAO.search(txt, type);

        if (listKVK.isEmpty()) {
            // Nếu không có sản phẩm nào, hiển thị thông báo trong bảng
            DefaultTableModel model = (DefaultTableModel) tableKhuvuc.getModel();
            model.setRowCount(0); // Xóa tất cả các dòng cũ
            model.addRow(new Object[]{"Không có sản phẩm", "", "", "", ""}); // Thêm dòng thông báo // tạm thời v ha
        } else {
            // Nếu có sản phẩm, tải dữ liệu vào bảng
            loadDataTable(listKVK);
        }
    }
});


        search.btnReset.addActionListener(this);
        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);
        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
//        main.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableSanPham);

        // right = new JPanel();
        // right.setBackground(BackgroundColor);
        // right.setLayout(new FlowLayout(0, 4, 10));
        // right.setPreferredSize(new Dimension(0, 800));// set width = 0 để bỏ cái panel bên phải đi
        // JLabel tit = new JLabel("Danh sách sản phẩm trong khu vực");
        // tit.setFont(new java.awt.Font(FlatRobotoFont.FAMILY, 1, 16));
        // right.add(tit);
//        right.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm trong kho", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14)));
        // scrollPane = new JScrollPane(right, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        // scrollPane.setBackground(BackgroundColor);
        // contentCenter.add(scrollPane, BorderLayout.EAST);
    }

    public KhuVucSach1(Main m) {
        this.m = m;
        initComponent();
        tableKhuvuc.setDefaultEditor(Object.class, null);
        loadDataTable(listKVK);
    }

   public void loadDataTable(ArrayList<KhuVucSach1DTO> result) {
    // Reset lại bảng trước khi thêm dữ liệu mới
    tblModel.setRowCount(0);
    for (KhuVucSach1DTO kvk : result) {
        String trangThai;
        
        // Kiểm tra giá trị của TT và gán trạng thái tương ứng
        if (kvk.getTT()==1 ) 
            trangThai = "Hoạt động";
        else
            trangThai = "Đã xóa"; 
        // Thêm dữ liệu vào bảng với trạng thái đã chuyển đổi
        tblModel.addRow(new Object[]{
            kvk.getMLH(), kvk.getNgay(), kvk.getTongSoSp(), kvk.getTongTien(), trangThai
        });
    }
}


    public void importExcel() {
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;
        JFileChooser jf = new JFileChooser();
        int result = jf.showOpenDialog(null);
        jf.setDialogTitle("Open file");
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = jf.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);
                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);
                 //   int id = KhuVucSachDAO.getInstance().getAutoIncrement();
                    String tenkvk = excelRow.getCell(0).getStringCellValue();
                    String ghichu = excelRow.getCell(1).getStringCellValue();
          //          loHangDAO.add(new KhuVucSachDTO(id, tenkvk, ghichu));
                    tblModel.setRowCount(0);
                    loadDataTable(listKVK);
                }
                JOptionPane.showMessageDialog(this, "Nhập thành công");
            } catch (FileNotFoundException ex) {
                System.out.println("Lỗi đọc file");
            } catch (IOException ex) {
                System.out.println("Lỗi đọc file");
            }
        }

        loadDataTable(listKVK);
    }


    public int getRowSelected() {
        int index = tableKhuvuc.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khu vực lô");
        }
        return index;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            new KhuVucSach1Dialog(this, owner, "Thêm khu vực lô", true, "create");
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            int index = getRowSelected();
            if (index != -1) {
                   new KhuVucSach1Dialog(this, owner, "Thêm khu vực lô", true, "create");
            }
        }
        
        else if (e.getSource() == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                      new KhuVucSach1Dialog(this, owner, "Thêm khu vực lô", true, "detail" ,listKVK.get(index) );
            }
        }
        else if (e.getSource() == mainFunction.btn.get("delete")) {        
      int index = getRowSelected();
      if (index != -1) {
          int input = JOptionPane.showConfirmDialog(null,
                  "Bạn có chắc chắn muốn xóa khu vực!", "Xóa khu vực lô",
                  JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
          if (input == 0) {
              System.out.println("mã xóa nè " +listKVK.get(index) );
              if (listKVK.get(index).getTongSoSp() > 0) {
                  JOptionPane.showMessageDialog(this, "Không thể xóa khu vực vì có sản phẩm ở trong lô.");
              } else {
                  loHangDAO.delete(listKVK.get(index).getMLH());
                  loadDataTable(listKVK);
                  JOptionPane.showMessageDialog(this, "Xóa khu vực thành công.");
              }
          }
      }
  }

         else if (e.getSource() == search.btnReset) {
            search.txtSearchForm.setText("");
            listKVK = loHangDAO.getAll();
            loadDataTable(listKVK);
        } else if (e.getSource() == mainFunction.btn.get("import")) {
            importExcel();
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tableKhuvuc);
            } catch (IOException ex) {
                Logger.getLogger(KhuVucSach.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String type = (String) search.cbxChoose.getSelectedItem();
        String txt = search.txtSearchForm.getText();
        listKVK = loHangDAO.search(txt, type);
        loadDataTable(listKVK);
    }
}
