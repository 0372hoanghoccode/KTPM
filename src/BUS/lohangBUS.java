/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ChiTietLoHangDAO;

import DAO.KhuVucSach1DAO;

import DTO.ChiTietPhieuDTO;
import DTO.PhieuXuatDTO;
import java.util.ArrayList;

/**
 *
 * @author ASUS Vivobook
 */
public class lohangBUS {
    
      private final KhuVucSach1DAO KhuVucSach1DAO = KhuVucSach1DAO.getInstance();

    private final ChiTietLoHangDAO ChiTietLoHangDAO = ChiTietLoHangDAO.getInstance();
    private ArrayList<PhieuXuatDTO> listPhieuXuat;
    
    public ArrayList<ChiTietPhieuDTO> selectCTP(int maphieu) {
        return ChiTietLoHangDAO.selectAll(Integer.toString(maphieu));
    }
    
}
