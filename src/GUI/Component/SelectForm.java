package GUI.Component;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SelectForm extends JPanel{
    private JLabel lblTitle;
    public JComboBox cbb;
    
    public SelectForm(String title, String[] obj) {
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(0, 10, 5, 10));
        
        lblTitle = new JLabel(title);
        cbb = new JComboBox(obj);
        
        this.add(lblTitle);
        this.add(cbb);
    }
    
    public void setArr(String[] obj) {
        this.cbb.setModel(new DefaultComboBoxModel(obj));
    }
    
    public String getValue() {
        return (String) cbb.getSelectedItem();
    }
    
    public Object getSelectedItem() {
        return cbb.getSelectedItem();
    }
    
    public int getSelectedIndex() {
        return cbb.getSelectedIndex();
    }
    
    public void setSelectedIndex(int i) {
        cbb.setSelectedIndex(i);
    }
    
    public void setSelectedItem(Object a) {
        cbb.setSelectedItem(a);
    }

    public JLabel getLblTitle() {
        return lblTitle;
    }

    public void setLblTitle(JLabel lblTitle) {
        this.lblTitle = lblTitle;
    }

    public JComboBox getCbb() {
        return cbb;
    }

    public void setCbb(JComboBox cbb) {
        this.cbb = cbb;
    }
    
    public void setDisable(){
        cbb.setEnabled(false);
    }
      public void setArr(ArrayList<String> list) {
        // Chuyển đổi ArrayList<String> thành mảng String
        String[] array = list.toArray(new String[0]);
        this.cbb.setModel(new DefaultComboBoxModel<>(array));
    }

    // Phương thức cũ (nếu bạn vẫn muốn sử dụng)
  public void addActionListener(ActionListener listener) {
        cbb.addActionListener(listener);
    }
public void clearAndAddItems(int[] items) {
    cbb.removeAllItems(); // Xóa tất cả các mục hiện tại
    for (int item : items) {
        cbb.addItem(String.valueOf(item)); // Chuyển đổi int thành String và thêm vào JComboBox
    }
}

    public void updateOptions(String[] arrmlh) {
        cbb.removeAllItems(); // Xóa tất cả các mục hiện tại
    for (String option : arrmlh) {
        cbb.addItem(option); // Thêm các mục mới
    }
    this.repaint(); // Làm mới giao diện
    }


  
}
