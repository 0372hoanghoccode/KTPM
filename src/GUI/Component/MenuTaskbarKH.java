package GUI.Component;

import DAO.ChiTietQuyenDAO;
import DAO.KhachHangDAO;
import DAO.NhomQuyenDAO;
import DTO.ChiTietQuyenDTO;
import DTO.KhachHangDTO;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;
import GUI.MainKH;
import GUI.Panel.DonHang;
import GUI.Panel.GioHang;
// import GUI.login_page;
import GUI.Panel.SanPhamKH;
import GUI.login_page;
import GUI.Panel.TrangChu;
import GUI.Dialog.MyAccountKH;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class MenuTaskbarKH extends JPanel {

    login_page login ;
    TrangChu trangChu;
    SanPhamKH sanPham;
    GioHang gioHang;
    DonHang donHang;
    JScrollPane scrollPaneMenuTask ;
    String[][] getSt = {
        {"Trang chủ", "home.svg", "trangchu"},
        {"Sản phẩm", "book.svg", "xemhang"},
        {"Giỏ hàng", "shopping_cart_icon.svg", "giohang"},
        {"Đơn hàng", "export.svg", "donhang"},
        {"Đăng xuất", "log_out.svg", "dangxuat"},
    };

    MainKH main;
    TaiKhoanDTO user;
    public ArrayList <itemTaskbar> listitem;
    ArrayList <Integer> check = new ArrayList<>();

    JLabel lblTenNhomQuyen, lblUsername;
    JScrollPane scrollPane;

    //tasbarMenu chia thành 3 phần chính là pnlCenter, pnlTop, pnlBottom
    JPanel pnlCenter, pnlTop, pnlBottom, bar1, bar2, bar3, bar4;

    Color FontColor = new Color(96, 125, 139);
    Color DefaultColor = new Color(255, 255, 255);
    Color HowerFontColor = new Color(1, 87, 155);
    Color HowerBackgroundColor = new Color(193,237,220);
    private ArrayList<ChiTietQuyenDTO> listQuyen;
    NhomQuyenDTO nhomQuyenDTO;
    public KhachHangDTO khachHangDTO;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

    public MenuTaskbarKH(MainKH main) {
        this.main = main;
        this.user = new TaiKhoanDTO(0,"","",5,1);
        listQuyen = ChiTietQuyenDAO.getInstance().selectAll(Integer.toString(user.getMNQ()));
        initComponent();
    }

    public MenuTaskbarKH(MainKH main, TaiKhoanDTO tk) {
        this.main = main;
        this.user = tk;
        this.nhomQuyenDTO = NhomQuyenDAO.getInstance().selectById(Integer.toString(tk.getMNQ()));
        this.khachHangDTO = KhachHangDAO.getInstance().selectById(Integer.toString(tk.getMNV()));
        listQuyen = ChiTietQuyenDAO.getInstance().selectAll(Integer.toString(tk.getMNQ()));
        initComponent();
    }


    private void initComponent() {
        listitem = new ArrayList<itemTaskbar>();
        this.setOpaque(true);
        this.setBackground(DefaultColor);
        this.setLayout(new BorderLayout(0, 0));

        // bar1, bar là các đường kẻ mỏng giữa taskbarMenu và MainContent
        pnlTop = new JPanel();
        pnlTop.setPreferredSize(new Dimension(250, 80));
        pnlTop.setBackground(DefaultColor);
        pnlTop.setLayout(new BorderLayout(0, 0));
        this.add(pnlTop, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BorderLayout(0, 0));
        pnlTop.add(info, BorderLayout.CENTER);

        // Cái info này bỏ vô cho đẹp tí, mai mốt có gì xóa đi đê hiển thị thông tin tài khoản và quyền
        if(user.getMNV() != 0) in4(info);
        else in4_default(info);

        bar1 = new JPanel();
        bar1.setBackground(new Color(204, 214, 219));
        bar1.setPreferredSize(new Dimension(1, 0));
        pnlTop.add(bar1, BorderLayout.EAST);

        bar2 = new JPanel();
        bar2.setBackground(new Color(204, 214, 219));
        bar2.setPreferredSize(new Dimension(0, 1));
        pnlTop.add(bar2, BorderLayout.SOUTH);

        pnlCenter = new JPanel();
        pnlCenter.setPreferredSize(new Dimension(230, 600));
        pnlCenter.setBackground(DefaultColor);
        pnlCenter.setLayout(new FlowLayout());

        bar3 = new JPanel();
        bar3.setBackground(new Color(204, 214, 219));
        bar3.setPreferredSize(new Dimension(1, 1));
        this.add(bar3, BorderLayout.EAST);

        scrollPane = new JScrollPane(pnlCenter, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(5, 10, 0, 10));
        this.add(scrollPane, BorderLayout.CENTER);

        pnlBottom = new JPanel();
        pnlBottom.setPreferredSize(new Dimension(250, 50));
        pnlBottom.setBackground(DefaultColor);
        pnlBottom.setLayout(new BorderLayout(0, 0));

        bar4 = new JPanel();
        bar4.setBackground(new Color(204, 214, 219));
        bar4.setPreferredSize(new Dimension(1, 1));
        pnlBottom.add(bar4, BorderLayout.EAST);

        this.add(pnlBottom, BorderLayout.SOUTH);

        for (int i = 0; i < getSt.length; i++) {
                if (i + 1 == getSt.length) {
                    if(user.getMNV() == 0) {
                        listitem.add(new itemTaskbar("login.svg", "Đăng nhập"));
                    } else {
                        listitem.add(new itemTaskbar(getSt[i][1], getSt[i][0]));
                    }
                    pnlBottom.add(listitem.get(i));
                } else {
                    listitem.add(new itemTaskbar(getSt[i][1], getSt[i][0]));
                    if (i != 0) {
                        if (!checkRole(getSt[i][2])) {
                            listitem.get(i).setVisible(false);
                            check.add(i);
                        }
                    }
                }
            }
        
        listitem.get(0).setBackground(HowerBackgroundColor);
        listitem.get(0).setForeground(HowerFontColor);
        listitem.get(0).isSelected = true;

        for (int i = 0; i < getSt.length; i++) {
            listitem.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    pnlMenuTaskbarMousePress(evt);
                }
            });
        }

        listitem.get(0).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                trangChu = new TrangChu();
                main.setPanel(trangChu);
            }
        });

        listitem.get(1).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                sanPham = new SanPhamKH(main);
                main.setPanel(sanPham);

            }
        });
        listitem.get(2).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                gioHang = new GioHang(main, user, "create");
                main.setPanel(gioHang);

            }
        });
        listitem.get(3).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                donHang = new DonHang(main, user);
                main.setPanel(donHang);

            }
        });
        if(user.getMNV() == 0) {
            listitem.get(4).addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    login = new login_page();
                    main.dispose();
                }
            });
        }
        else {
                listitem.get(4).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent evt) {
                    
                    int input = JOptionPane.showConfirmDialog(null,
                            "Bạn có chắc chắn muốn đăng xuất?", "Đăng xuất",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (input == 0) {
                        login_page login = new login_page();
                        main.dispose();
                        login.setVisible(true);
                    }
                }
            });
        }

        int count = 0;
        for (Integer i : check) listitem.remove((int) i - count++);
        if(listitem.size() - 1 <= 12) pnlCenter.setPreferredSize(new Dimension(230, listitem.size() - 1));
        for (int i = 0; i < listitem.size() - 1; i++) pnlCenter.add(listitem.get(i));
    }

    public boolean checkRole(String s) {
        boolean check = false;
        for (int i = 0; i < listQuyen.size(); i++) {
            if (listQuyen.get(i).getHanhdong().equals("view")) {
                if (s.equals(listQuyen.get(i).getMachucnang())) {
                    check = true;
                    return check;
                }
            }
        }
        return check;
    }

    public void pnlMenuTaskbarMousePress(MouseEvent evt) {

        for (int i = 0; i < listitem.size(); i++) {
            if (evt.getSource() == listitem.get(i)) {
                listitem.get(i).isSelected = true;
                listitem.get(i).setBackground(HowerBackgroundColor);
                listitem.get(i).setForeground(HowerFontColor);
            } else {
                listitem.get(i).isSelected = false;
                listitem.get(i).setBackground(DefaultColor);
                listitem.get(i).setForeground(FontColor);
            }
        }
    }
    public void resetChange(){
        this.khachHangDTO = new KhachHangDAO().selectById(String.valueOf(khachHangDTO.getMaKH()));
    }
    public void in4_default(JPanel info) {
        JPanel pnlIcon = new JPanel(new FlowLayout());
        pnlIcon.setPreferredSize(new Dimension(60, 0));
        pnlIcon.setOpaque(false);
        info.add(pnlIcon, BorderLayout.WEST);
        JLabel lblIcon = new JLabel();
        lblIcon.setPreferredSize(new Dimension(50, 70));
        lblIcon.setIcon(new FlatSVGIcon("./icon/man_50px.svg"));

        pnlIcon.add(lblIcon);

        JPanel pnlInfo = new JPanel();
        pnlInfo.setOpaque(false);
        pnlInfo.setLayout(new FlowLayout(0, 10, 5));
        pnlInfo.setBorder(new EmptyBorder(15, 0, 0, 0));
        info.add(pnlInfo, BorderLayout.CENTER);
        lblUsername = new JLabel("Xin chào quý khách!");
        lblUsername.putClientProperty("FlatLaf.style", "font: 150% $semibold.font");
        pnlInfo.add(lblUsername);

        lblTenNhomQuyen = new JLabel("Chế độ xem");
        lblTenNhomQuyen.putClientProperty("FlatLaf.style", "font: 120% $light.font");
        lblTenNhomQuyen.setForeground(Color.GRAY);
        pnlInfo.add(lblTenNhomQuyen);
    }

    public void in4(JPanel info) {
        JPanel pnlIcon = new JPanel(new FlowLayout());
        pnlIcon.setPreferredSize(new Dimension(60, 0));
        pnlIcon.setOpaque(false);
        info.add(pnlIcon, BorderLayout.WEST);
        JLabel lblIcon = new JLabel();
        lblIcon.setPreferredSize(new Dimension(50, 70));
        lblIcon.setIcon(new FlatSVGIcon("./icon/man_50px.svg"));

        pnlIcon.add(lblIcon);

        JPanel pnlInfo = new JPanel();
        pnlInfo.setOpaque(false);
        pnlInfo.setLayout(new FlowLayout(0, 10, 5));
        pnlInfo.setBorder(new EmptyBorder(15, 0, 0, 0));
        info.add(pnlInfo, BorderLayout.CENTER);
        lblUsername = new JLabel(khachHangDTO.getHoten());
        lblUsername.putClientProperty("FlatLaf.style", "font: 150% $semibold.font");
        pnlInfo.add(lblUsername);

        lblTenNhomQuyen = new JLabel(nhomQuyenDTO.getTennhomquyen());
        lblTenNhomQuyen.putClientProperty("FlatLaf.style", "font: 120% $light.font");
        lblTenNhomQuyen.setForeground(Color.GRAY);
        pnlInfo.add(lblTenNhomQuyen);

        lblIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                new MyAccountKH(owner, MenuTaskbarKH.this, "Chỉnh sửa thông tin tài khoản", true);
            }
        });
    }
}
