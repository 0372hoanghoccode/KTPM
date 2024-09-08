package DAO;

import DTO.ChiTietKiemKeDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietKiemKeDAO implements ChiTietInterface<ChiTietKiemKeDTO>{
    
    public static ChiTietKiemKeDAO getInstance(){
        return new ChiTietKiemKeDAO();
    }

    @Override
public int insert(ArrayList<ChiTietKiemKeDTO> t) {
    int result = 0;
    Connection con = null;
    PreparedStatement pst = null;
    
    try {
        con = JDBCUtil.getConnection(); // Mở kết nối trước vòng lặp

        String sql = "INSERT INTO CTPHIEUKIEMKE (MPKK, MSP, TRANGTHAISP, GHICHU) VALUES (?, ?, ?, ?)";
        pst = con.prepareStatement(sql);

        for (ChiTietKiemKeDTO dto : t) {
            pst.setInt(1, dto.getMP());
            pst.setInt(2, dto.getMSP());
            pst.setInt(3, dto.getTRANGTHAISP());
            pst.setString(4, dto.getGHICHU());
            
            result += pst.executeUpdate(); // Cộng dồn số lượng bản ghi đã chèn
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(ChiTietPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        // Có thể xử lý thêm lỗi ở đây
    } finally {
        // Đảm bảo đóng tài nguyên trong khối finally
        try {
            if (pst != null) pst.close();
            if (con != null) JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            Logger.getLogger(ChiTietPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    return result; // Trả về tổng số bản ghi đã chèn thành công
}


    @Override
    public int delete(String maphieu) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "DELETE FROM CTPHIEUKIEMKE WHERE MPKK = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maphieu);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(ArrayList<ChiTietKiemKeDTO> t, String pk) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ChiTietKiemKeDTO> selectAll(String t) {
        ArrayList<ChiTietKiemKeDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM CTPHIEUKIEMKE WHERE MPKK = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int MPKK = rs.getInt("MPKK");
                int MSP = rs.getInt("MSP");
                int TTSP = rs.getInt("TRANGTHAISP");
                String ghichu = rs.getString("GHICHU");
                ChiTietKiemKeDTO ctphieu = new ChiTietKiemKeDTO(MPKK, MSP,TTSP,ghichu);
                result.add(ctphieu);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    
}
