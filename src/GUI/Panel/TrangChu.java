package GUI.Panel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import GUI.Component.PanelShadow;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class TrangChu extends JPanel {

    JPanel top, center, bar1, bar2;
    PanelShadow content[];
    JPanel info[];
    JLabel title, subTit, infoDetail[], objDetail[], objDetail1[], infoIcon[];
        String[][] getSt = {
        {"SỨ MỆNH", "convenient_100px.svg",         "<html>Hiện nay, Công ty chúng tôi đã và đang ngày càng nỗ lực<br><br> hơn trong hoạt động sản xuất kinh doanh, tiếp tục góp <br><br> phần vào sự nghiệp phát triển “văn hóa đọc”, làm cho <br><br> những giá trị vĩnh hằng của sách ngày càng thấm sâu vào <br><br> đời sống văn hóa tinh thần của xã hội, nhằm góp phần tích <br><br> cực, đáp ứng yêu cầu nâng cao dân trí, bồi dưỡng nhân tài <br><br> và nguồn nhân lực cho sự nghiệp công nghiệp hóa, hiện <br><br> đại hóa đất nước .</html>"},
        {"BẢO MẬT & HIỆU QUẢ", "secure_100px.svg",  "<html>Thông tin cá nhân và thông tin liên quan đến sách mượn <br><br> thường được bảo mật và chỉ được truy cập bởi người dùng <br><br> hoặc những người được ủy quyền Nhờ vào tính năng đặc <br><br> biệt của mã ISBN giúp xác định được thông tin về từng <br><br> cuốn sách một cách nhanh chóng và chính xác, giúp cho <br><br> việc quản lý sách được thực hiện một cách hiệu quả hơn..</html>"},
        {"HƯỚNG PHÁT TRIỂN ", "effective_100px.svg","<html>Cửa hàng có thể cung cấp các sản phẩm sách tái chế hoặc <br><br> triển khai các hoạt động bảo vệ môi trường, như giảm <br><br> thiểu việc sử dụng túi nhựa, khuyến khích khách hàng tái <br><br> sử dụng túi vải, và tham gia vào các hoạt động cộng <br><br> đồng về bảo vệ môi trường. Mở rộng dịch vụ thu mua, trao <br><br> đổi và bán lại sách cũ, giúp khách hàng có thêm lựa chọn <br><br> và thúc đẩy sự bền vững trong việc sử dụng sách.</html>"},
    };
    Color MainColor = new Color(0x55BEC0);
    Color FontColor = new Color(96, 125, 139);
    Color BackgroundColor = new Color(0x55BEC0);
    Color HowerFontColor = new Color(225, 232, 232);

    private void initComponent() {
        this.setBackground(new Color(225, 232, 232));
        this.setBounds(0, 200, 300, 1200);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        top = new JPanel();
        top.setBackground(MainColor);
        top.setPreferredSize(new Dimension(1100, 200));
        top.setLayout(new FlowLayout(1, 0, 10));

        JLabel slogan = new JLabel();
        slogan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/slogan4.png")));
        
        top.add(slogan);

        this.add(top, BorderLayout.NORTH);

        center = new JPanel();
        center.setBackground(BackgroundColor);
        center.setPreferredSize(new Dimension(1100, 800));
        center.setLayout(new GridLayout(1 , 3 , 10, 0));
        center.setBorder(new EmptyBorder(10,30,30,30));


        content = new PanelShadow[getSt.length];
        info = new JPanel[3];
        infoDetail = new JLabel[3];
        objDetail = new JLabel[3];
        objDetail1 = new JLabel[3];


        for (int i = 0; i < getSt.length; i++) {
            content[i] = new PanelShadow();
            content[i] = new PanelShadow(getSt[i][1], getSt[i][0], getSt[i][2]);

            center.add(content[i]);

        }

        this.add(center, BorderLayout.CENTER);

    }

    public TrangChu() {
        initComponent();
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
    }


}
