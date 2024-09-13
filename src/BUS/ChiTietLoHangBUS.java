/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ChiTietLoHangDAO;
import DTO.ChiTietLoHangDTO;
import java.util.ArrayList;

/**
 *
 * @author ASUS Vivobook
 */
public class ChiTietLoHangBUS {
    private ChiTietLoHangDAO chiTietLoHangDAO;

    public ChiTietLoHangBUS() {
        chiTietLoHangDAO = new ChiTietLoHangDAO(); // Khởi tạo DAO
    }

    public ArrayList<ChiTietLoHangDTO> getByMaLoHang(String maLoHang) {
        return chiTietLoHangDAO.getByMaLoHang(maLoHang); // Gọi DAO để lấy dữ liệu
    }
}
