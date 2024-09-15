package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

import java.util.ArrayList;

public class KhachHangBUS {

    private final KhachHangDAO khDAO = new KhachHangDAO();
    public ArrayList<KhachHangDTO> listKhachHang = new ArrayList<>();

    public KhachHangBUS() {
        listKhachHang = khDAO.selectAll();
    }

    public ArrayList<KhachHangDTO> getAll() {
        return this.listKhachHang;
    }

    public KhachHangDTO getByIndex(int index) {
        return this.listKhachHang.get(index);
    }

    public int getByTen(String index) {
        int i = 0;
        int vitri = -1;
        while (i < this.listKhachHang.size() && vitri == -1) {
            if (listKhachHang.get(i).getHoten().equals(index)) {
                vitri = i;
            } else {
                i++;
            }
        }
        return listKhachHang.get(i).getMaKH();
    }

    public int getIndexByMaDV(int makhachhang) {
        int i = 0;
        int vitri = -1;
        while (i < this.listKhachHang.size() && vitri == -1) {
            if (listKhachHang.get(i).getMaKH() == makhachhang) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public Boolean add(KhachHangDTO kh) {
        boolean check = khDAO.insert(kh) != 0;
        if (check) {
            this.listKhachHang.add(kh);
        }
        return check;
    }

    public Boolean delete(KhachHangDTO kh) {
        boolean check = khDAO.delete(Integer.toString(kh.getMaKH())) != 0;
        if (check) {
            this.listKhachHang.remove(getIndexByMaDV(kh.getMaKH()));
        }
        return check;
    }

    public Boolean update(KhachHangDTO kh) {
        boolean check = khDAO.update(kh) != 0;
        if (check) {
            this.listKhachHang.set(getIndexByMaDV(kh.getMaKH()), kh);
        }
        return check;
    }

    public ArrayList<KhachHangDTO> search(String text, String type) {
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        text = text.toLowerCase(); // chuyển text thành chữ thường để so sánh
    
        for (KhachHangDTO khachHang : this.listKhachHang) {
            boolean match = false;
            
            switch (type) {
                case "Tất cả" -> {
                    if (String.valueOf(khachHang.getMaKH()).toLowerCase().contains(text) ||
                        khachHang.getHoten().toLowerCase().contains(text) ||
                        khachHang.getDiachi().toLowerCase().contains(text) ||
                        khachHang.getSdt().toLowerCase().contains(text)) {
                        match = true;
                    }
                }
                case "Mã khách hàng" -> {
                    if (String.valueOf(khachHang.getMaKH()).toLowerCase().contains(text)) {
                        match = true;
                    }
                }
                case "Tên khách hàng" -> {
                    if (khachHang.getHoten().toLowerCase().contains(text)) {
                        match = true;
                    }
                }
                case "Địa chỉ" -> {
                    if (khachHang.getDiachi().toLowerCase().contains(text)) {
                        match = true;
                    }
                }
                case "Số điện thoại" -> {
                    if (khachHang.getSdt().toLowerCase().contains(text)) {
                        match = true;
                    }
                }
                default -> throw new IllegalArgumentException("Loại tìm kiếm không hợp lệ: " + type);
            }
    
            if (match) {
                result.add(khachHang); // nếu điều kiện đúng, thêm vào danh sách kết quả
            }
        }
    
        return result;
    }
    

    public String getTenKhachHang(int makh) {
        String name = "";
        for (KhachHangDTO khachHangDTO : listKhachHang) {
            if (khachHangDTO.getMaKH() == makh) {
                name = khachHangDTO.getHoten();
            }
        }
        return name;
    }

    public String[] getArrTenKhachHang() {
        int size = listKhachHang.size();
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = listKhachHang.get(i).getHoten();
        }
        return result;
    }

    public KhachHangDTO selectKh(int makh) {
        return khDAO.selectById(makh + "");
    }

    public int getMKHMAX() {
        int s = 1;
        listKhachHang = khDAO.selectAlll();
        for (KhachHangDTO i : listKhachHang) {
            if(i.getMaKH() > s) s = i.getMaKH();
        }
        return s;
    }

}
