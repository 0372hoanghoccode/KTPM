/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.PreparedStatement;
import DTO.ChiTietLoHangDTO;
import DTO.ChiTietPhieuDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
/**
 *
 * @author ASUS Vivobook
 */

public class ChiTietLoHangDAO implements ChiTietInterface<ChiTietLoHangDTO>{
      public static ChiTietPhieuNhapDAO getInstance() {
        return new ChiTietPhieuNhapDAO();
}

    public ChiTietLoHangDAO() {
       
    }

    

    @Override
    public int insert(ArrayList<ChiTietLoHangDTO> t) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(ArrayList<ChiTietLoHangDTO> t, String pk) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  public ArrayList<ChiTietLoHangDTO> selectAll() {
        ArrayList<ChiTietLoHangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            // Kết nối đến cơ sở dữ liệu
            con = JDBCUtil.getConnection();
            
            // Câu truy vấn SQL để lấy tất cả các chi tiết lô hàng
            String sql = "SELECT MLH, MSP, GIANHAP,  SoLuong FROM ctlohang";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            // Xử lý kết quả truy vấn
            while (rs.next()) {
                String MLH = rs.getString("MLH"); // Lấy dữ liệu kiểu String
                int MSP = rs.getInt("MSP"); // Lấy dữ liệu kiểu int
                int GIANHAP = rs.getInt("GIANHAP"); // Lấy dữ liệu kiểu int
             //     int GIABAN = rs.getInt("GiABAN"); // Lấy dữ liệu kiểu int
                int SL = rs.getInt("SoLuong"); // Lấy dữ liệu kiểu int
                
                // Tạo đối tượng ChiTietLoHangDTO và thêm vào danh sách kết quả
                ChiTietLoHangDTO dto = new ChiTietLoHangDTO(MLH, MSP, GIANHAP, SL);
                result.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi để tiện theo dõi
        } 
        
        return result;
    }
    
    public ArrayList<String> findMLHByMSP(int msp) {
        ArrayList<String> mlhList = new ArrayList<>();
        String sql = "SELECT MLH FROM ctlohang WHERE MSP = ?";

        try ( Connection con = (Connection) JDBCUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // Set giá trị cho placeholder
            pstmt.setInt(1, msp);

            // Thực thi truy vấn
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Lấy giá trị của cột MLH và thêm vào danh sách
                    String mlh = rs.getString("MLH");
                    mlhList.add(mlh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mlhList;
    }

  
     
    public ArrayList<ChiTietPhieuDTO> selectAll1(String t) {
            ArrayList<ChiTietPhieuDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM ctlohang WHERE MLH = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int maphieu = rs.getInt("MLH");
                int MSP = rs.getInt("MSP");
                int SL = rs.getInt("GiaNhap");
                int tienxuat = rs.getInt("SL");
                ChiTietPhieuDTO ctphieu = new ChiTietPhieuDTO(maphieu, MSP, SL, tienxuat);
                result.add(ctphieu);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public ArrayList<ChiTietLoHangDTO> selectAll(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
    
    
    
    
    
    
}
  