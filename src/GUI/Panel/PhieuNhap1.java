// package GUI.Panel;

// import java.awt.BorderLayout;
// import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.GridLayout;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.event.ItemEvent;
// import java.awt.event.ItemListener;
// import java.awt.event.KeyEvent;
// import java.awt.event.KeyListener;
// import java.beans.PropertyChangeEvent;
// import java.beans.PropertyChangeListener;
// import java.io.IOException;
// import java.text.ParseException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Date;
// import java.util.logging.Level;
// import java.util.logging.Logger;
// import java.util.stream.Stream;

// import javax.swing.BoxLayout;
// import javax.swing.JLabel;
// import javax.swing.JOptionPane;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
// import javax.swing.border.EmptyBorder;
// import javax.swing.table.DefaultTableCellRenderer;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.text.PlainDocument;

// import BUS.NhanVienBUS;

// import DAO.ChiTietLoHangDAO;
// import DAO.KhuVucSach1DAO;

// import DTO.ChiTietLoHangDTO;
// import DTO.KhuVucSach1DTO;

// import DTO.NhanVienDTO;

// import GUI.Main;
// import GUI.Component.InputDate;
// import GUI.Component.InputForm;
// import GUI.Component.IntegratedSearch;
// import GUI.Component.MainFunction;
// import GUI.Component.NumericDocumentFilter;
// import GUI.Component.PanelBorderRadius;
// import GUI.Component.SelectForm;
// import GUI.Component.TableSorter;
// import GUI.Dialog.ChiTietPhieuDialog;
// import helper.Formater;
// import helper.JTableExporter;

// public final class PhieuNhap1 extends JPanel implements ActionListener, KeyListener, PropertyChangeListener, ItemListener {

//     PanelBorderRadius main, functionBar, box;
//     JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
//     JTable tablePhieuNhap;
//     JScrollPane scrollTablePhieuNhap;
//     MainFunction mainFunction;
//     IntegratedSearch search;
//     DefaultTableModel tblModel;
//     SelectForm  cbxNhanVien;
//     InputDate dateStart, dateEnd;
//     InputForm moneyMin, moneyMax;

//     TaoPhieuNhap nhapKho;
//     Main m;
//     NhanVienDTO nv;
//     KhuVucSach1DAO lohangdao = new KhuVucSach1DAO();
   
//     NhanVienBUS nvBUS = new NhanVienBUS();
//     ArrayList<KhuVucSach1DTO> listPhieu;

//     Color BackgroundColor = new Color(211, 211, 211);

//     public PhieuNhap1(Main m, NhanVienDTO nv) {
//         this.m = m;
//         this.nv = nv;
//         initComponent();
//         this.listPhieu = lohangdao.getAll();
//          for (KhuVucSach1DTO khuVuc : listPhieu) {
//             System.out.println("Mã Khu Vực: " + khuVuc.getMLH());
//             System.out.println("Tên Khu Vực: " + khuVuc.getTT());
           
//             System.out.println("------------------------------");
//         }
//         loadDataTable(this.listPhieu);
//     }

//     public void initPadding() {
//         pnlBorder1 = new JPanel();
//         pnlBorder1.setPreferredSize(new Dimension(0, 10));
//         pnlBorder1.setBackground(BackgroundColor);
//         this.add(pnlBorder1, BorderLayout.NORTH);

//         pnlBorder2 = new JPanel();
//         pnlBorder2.setPreferredSize(new Dimension(0, 10));
//         pnlBorder2.setBackground(BackgroundColor);
//         this.add(pnlBorder2, BorderLayout.SOUTH);

//         pnlBorder3 = new JPanel();
//         pnlBorder3.setPreferredSize(new Dimension(10, 0));
//         pnlBorder3.setBackground(BackgroundColor);
//         this.add(pnlBorder3, BorderLayout.EAST);

//         pnlBorder4 = new JPanel();
//         pnlBorder4.setPreferredSize(new Dimension(10, 0));
//         pnlBorder4.setBackground(BackgroundColor);
//         this.add(pnlBorder4, BorderLayout.WEST);
//     }

//     private void initComponent() {
//         this.setBackground(BackgroundColor);
//         this.setLayout(new BorderLayout(0, 0));
//         this.setOpaque(true);

//         tablePhieuNhap = new JTable();
//         tablePhieuNhap.setBackground(new Color(245, 250, 250));
//         scrollTablePhieuNhap = new JScrollPane();
//         tblModel = new DefaultTableModel();
//          String[] header = new String[]{"Mã lô", "Ghi Chú " , "Thời gian", "Trang thái"};
//         tblModel.setColumnIdentifiers(header);
//         tablePhieuNhap.setModel(tblModel);
//         tablePhieuNhap.setDefaultEditor(Object.class, null);
//         scrollTablePhieuNhap.setViewportView(tablePhieuNhap);
//         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//         centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//         tablePhieuNhap.setDefaultRenderer(Object.class, centerRenderer);
//         tablePhieuNhap.setFocusable(false);
//         tablePhieuNhap.getColumnModel().getColumn(0).setPreferredWidth(10);
//         tablePhieuNhap.getColumnModel().getColumn(1).setPreferredWidth(10);
//         tablePhieuNhap.getColumnModel().getColumn(2).setPreferredWidth(200);

//         tablePhieuNhap.setAutoCreateRowSorter(true);
//         TableSorter.configureTableColumnSorter(tablePhieuNhap, 0, TableSorter.INTEGER_COMPARATOR);
//         TableSorter.configureTableColumnSorter(tablePhieuNhap, 1, TableSorter.INTEGER_COMPARATOR);
//         TableSorter.configureTableColumnSorter(tablePhieuNhap, 5, TableSorter.VND_CURRENCY_COMPARATOR);

//         this.setBackground(BackgroundColor);
//         this.setLayout(new BorderLayout(0, 0));
//         this.setOpaque(true);

//         initPadding();

//         contentCenter = new JPanel();
//         contentCenter.setPreferredSize(new Dimension(1100, 600));
//         contentCenter.setBackground(BackgroundColor);
//         contentCenter.setLayout(new BorderLayout(10, 10));
//         this.add(contentCenter, BorderLayout.CENTER);

//         functionBar = new PanelBorderRadius();
//         functionBar.setPreferredSize(new Dimension(0, 100));
//         functionBar.setLayout(new GridLayout(1, 2, 50, 0));
//         functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

//         String[] action = {"create", "detail", "export"};
//         mainFunction = new MainFunction(m.user.getMNQ(), "nhaphang", action);

//         //Add Event MouseListener
//         for (String ac : action) {
//             mainFunction.btn.get(ac).addActionListener(this);
//         }

//         functionBar.add(mainFunction);

//         String[] objToSearch = {"Tất cả", "Mã phiếu nhập",  "Nhân viên nhập"};
//         search = new IntegratedSearch(objToSearch);
//         search.cbxChoose.addItemListener(this);
//         search.txtSearchForm.addKeyListener(this);
//         search.btnReset.addActionListener(this);
//         functionBar.add(search);

//         contentCenter.add(functionBar, BorderLayout.NORTH);

//         box = new PanelBorderRadius();
//         box.setPreferredSize(new Dimension(250, 0));
//         box.setLayout(new GridLayout(6, 1, 10, 0));
//         box.setBorder(new EmptyBorder(0, 5, 150, 5));
//         contentCenter.add(box, BorderLayout.WEST);

//         // Handle
//         String[] listNv = nvBUS.getArrTenNhanVien();
//         listNv = Stream.concat(Stream.of("Tất cả"), Arrays.stream(listNv)).toArray(String[]::new);

//         // init
//         cbxNhanVien = new SelectForm("Nhân viên nhập", listNv);
//         dateStart = new InputDate("Từ ngày");
//         dateEnd = new InputDate("Đến ngày");
//         moneyMin = new InputForm("Từ số tiền (VND)");
//         moneyMax = new InputForm("Đến số tiền (VND)");

//         PlainDocument doc_min = (PlainDocument) moneyMin.getTxtForm().getDocument();
//         doc_min.setDocumentFilter(new NumericDocumentFilter());

//         PlainDocument doc_max = (PlainDocument) moneyMax.getTxtForm().getDocument();
//         doc_max.setDocumentFilter(new NumericDocumentFilter());

//         // add listener

//         cbxNhanVien.getCbb().addItemListener(this);
//         dateStart.getDateChooser().addPropertyChangeListener(this);
//         dateEnd.getDateChooser().addPropertyChangeListener(this);
//         moneyMin.getTxtForm().addKeyListener(this);
//         moneyMax.getTxtForm().addKeyListener(this);

//         box.add(cbxNhanVien);
//         box.add(dateStart);
//         box.add(dateEnd);
//         box.add(moneyMin);
//         box.add(moneyMax);

//         main = new PanelBorderRadius();
//         BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
//         main.setLayout(boxly);
//         main.setBorder(new EmptyBorder(0, 0, 0, 0));
//         contentCenter.add(main, BorderLayout.CENTER);
//         main.add(scrollTablePhieuNhap);
//     }

//      public void loadDataTable(ArrayList<KhuVucSach1DTO> result) {
//         tblModel.setRowCount(0);
//         for (KhuVucSach1DTO kvk : result) {
//             tblModel.addRow(new Object[]{
//                 kvk.getMLH(), kvk.getNgay(),kvk.getTT()
//             });
//         }
//     }

//     public int getRowSelected() {
//         int index = tablePhieuNhap.getSelectedRow();
//         if (index == -1) {
//             JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập");
//         }
//         return index;
//     }

// //    public void Fillter() throws ParseException {
// //        if (validateSelectDate()) {
// //            int type = search.cbxChoose.getSelectedIndex();
// //            int manv = cbxNhanVien.getSelectedIndex() == 0 ? 0 : nvBUS.getByIndex(cbxNhanVien.getSelectedIndex() - 1).getMNV();
// //            String input = search.txtSearchForm.getText() != null ? search.txtSearchForm.getText() : "";
// //            Date time_start = dateStart.getDate() != null ? dateStart.getDate() : new Date(0);
// //            Date time_end = dateEnd.getDate() != null ? dateEnd.getDate() : new Date(System.currentTimeMillis());
// //            String min_price = moneyMin.getText();
// //            String max_price = moneyMax.getText();
// //            this.listPhieu = lohangdao.fillerPhieuNhap(type, input, manv, time_start, time_end, min_price, max_price);
// //            loadDataTalbe(listPhieu);
// //        }
// //    }

// //    public void resetForm() {
// //        cbxNhanVien.setSelectedIndex(0);
// //        search.cbxChoose.setSelectedIndex(0);
// //        search.txtSearchForm.setText("");
// //        moneyMin.setText("");
// //        moneyMax.setText("");
// //        dateStart.getDateChooser().setCalendar(null);
// //        dateEnd.getDateChooser().setCalendar(null);
// //        this.listPhieu = phieunhapBUS.getAllList();
// //        loadDataTalbe(listPhieu);
// //    }

//     public boolean validateSelectDate() throws ParseException {
//         Date time_start = dateStart.getDate();
//         Date time_end = dateEnd.getDate();

//         Date current_date = new Date();
//         if (time_start != null && time_start.after(current_date)) {
//             JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
//             dateStart.getDateChooser().setCalendar(null);
//             return false;
//         }
//         if (time_end != null && time_end.after(current_date)) {
//             JOptionPane.showMessageDialog(this, "Ngày kết thúc không được lớn hơn ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
//             dateEnd.getDateChooser().setCalendar(null);
//             return false;
//         }
//         if (time_start != null && time_end != null && time_start.after(time_end)) {
//             JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi !", JOptionPane.ERROR_MESSAGE);
//             dateEnd.getDateChooser().setCalendar(null);
//             return false;
//         }
//         return true;
//     }

//     @Override
//     public void actionPerformed(ActionEvent e) {
//         Object source = e.getSource();
//         if (source == mainFunction.btn.get("create")) {
//             nhapKho = new TaoPhieuNhap(nv, "create", m);
//             m.setPanel(nhapKho);
//         } else if (source == mainFunction.btn.get("detail")) {
//             int index = getRowSelected();
//             if (index != -1) {
// //                nhapKho = new TaoPhieuNhap(nv, "view", listPhieu.get(index), m);
// //                m.setPanel(nhapKho);
//                 new ChiTietPhieuDialog(m, "Thông tin lô hàng", true, listPhieu.get(index));
//             }
//         } else if (source == search.btnReset) {
//            // resetForm();
//         } else if (source == mainFunction.btn.get("export")) {
//             try {
//                 JTableExporter.exportJTableToExcel(tablePhieuNhap);
//             } catch (IOException ex) {
//                 Logger.getLogger(PhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
//             }
//         }
//     }

//     @Override
//     public void keyTyped(KeyEvent e) {
// //        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//     }

//     @Override
//     public void keyPressed(KeyEvent e) {

//     }

//  //   @Override
// //    public void keyReleased(KeyEvent e) {
// //        try {
// //            Fillter();
// //        } catch (ParseException ex) {
// //            Logger.getLogger(PhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
// //        }
// //    }

// //    @Override
//    // public void propertyChange(PropertyChangeEvent evt) {
// //        try {
// //      //      Fillter();
// //        } catch (ParseException ex) {
// //            Logger.getLogger(PhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
// //        }
//    // }

//     //@Override
//   //  public void itemStateChanged(ItemEvent e) {
// //        try {
// //         //   Fillter();
// //        } catch (ParseException ex) {
// //            Logger.getLogger(PhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
// //        }
// //    }

//     @Override
//     public void keyReleased(KeyEvent e) {
//         throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//     }

//     @Override
//     public void propertyChange(PropertyChangeEvent evt) {
//         throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//     }

//     @Override
//     public void itemStateChanged(ItemEvent e) {
//         throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//     }
// }
