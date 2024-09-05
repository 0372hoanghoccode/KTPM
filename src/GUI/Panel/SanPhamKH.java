package GUI.Panel;

import BUS.GioHangBUS;
import BUS.SanPhamBUS;
import DAO.NhaXuatBanDAO;
import DTO.ChiTietGioHangDTO;
import DTO.GioHangDTO;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.MainKH;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Dialog.SanPhamKHDialog;
import helper.Formater;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public final class SanPhamKH extends JPanel implements ActionListener {
    PanelBorderRadius main, functionBar;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    JTable tableSanPham;
    JScrollPane scrollTableSanPham;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tblModel;
    MainKH m;
    public SanPhamBUS spBUS = new SanPhamBUS();
    GioHangBUS giohangBUS = new GioHangBUS();

    
    public ArrayList<DTO.SanPhamDTO> listSP = spBUS.getAll();

    Color BackgroundColor = new Color(193 ,237 ,220);

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        tableSanPham = new JTable();
        tableSanPham.setBackground(new Color(0xA1D6E2));
        scrollTableSanPham = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"STT", "Tên sản phẩm", "Tên tác giả", "Danh mục", "Năm xuất bản", "Nhà xuất bản", "Giá bán"};
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

        String[] action = {"create", "detail"};
        mainFunction = new MainFunction(m.user.getMNQ(), "xemhang", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã sản phẩm", "Tên sản phẩm"});
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String type = (String) search.cbxChoose.getSelectedItem();
                String txt = search.txtSearchForm.getText();
                listSP = spBUS.search(txt, type);
                loadDataTalbe(listSP);
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

    public SanPhamKH(MainKH m) {
        this.m = m;
        initComponent();
        loadDataTalbe(listSP);
    }

    public void loadDataTalbe(ArrayList<DTO.SanPhamDTO> result) {
        tblModel.setRowCount(0);

        for (DTO.SanPhamDTO sp : result) {
            tblModel.addRow(new Object[]{sp.getMSP(), sp.getTEN(), sp.getTENTG(), sp.getDANHMUC(), sp.getNAMXB()
                , NhaXuatBanDAO.getInstance().selectById(sp.getMNXB() + "").getTennxb()
                , Formater.FormatVND(sp.getTIENX())
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            int index = getRowSelected();
            if (index != -1) {
                long now = System.currentTimeMillis();
                Timestamp currenTime = new Timestamp(now);
                giohangBUS.add(new GioHangDTO(m.user.getMNV(), 0, currenTime, 1));
                ChiTietGioHangDTO tmp = giohangBUS.checkTT(m.user.getMNV(), listSP.get(index).getMSP());
                if(tmp != null) {
                    tmp.setSL(tmp.getSL()+1);
                    giohangBUS.updateCT(tmp);
                }
                else {
                    tmp = new ChiTietGioHangDTO(m.user.getMNV(), listSP.get(index).getMSP(), "", 1, listSP.get(index).getTIENX());
                    giohangBUS.addCT(tmp);
                }
                JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công !");
            }
        } else if (e.getSource() == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                new SanPhamKHDialog(this, owner, "Xem chi tiết sản phẩm", true, "view", listSP.get(index));
            }
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

}
