package GUI.Panel.ThongKe;

import BUS.ThongKeBUS;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public final class ThongKe extends JPanel {

    JTabbedPane tabbedPane;
    JPanel tongquan, khachhang, doanhthu , sanpham;
    ThongKeTonKho nhapxuat;
    Color BackgroundColor = new Color(211, 211, 211);
    ThongKeBUS thongkeBUS = new ThongKeBUS();

    public ThongKe() {
        initComponent();
    }

    public void initComponent() {
        this.setLayout(new GridLayout(1, 1));
        this.setBackground(BackgroundColor);

        tongquan = new ThongKeTongQuan(thongkeBUS);
      //  nhapxuat = new ThongKeTonKho(thongkeBUS);
        khachhang = new ThongKeKhachHang(thongkeBUS);
        doanhthu = new ThongKeDoanhThu(thongkeBUS);
        sanpham = new ThongKeSanPhamne(thongkeBUS);
        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);
        tabbedPane.addTab("Tổng quan", tongquan);
      //  tabbedPane.addTab("Tồn kho", nhapxuat);
        tabbedPane.addTab("Doanh thu", doanhthu);
        tabbedPane.addTab("Khách hàng", khachhang);
      tabbedPane.addTab("Sản phẩm", sanpham);

        this.add(tabbedPane);
    }
}
