package DAO;

import DTO.ChiTietPhieuDTO;
import DTO.PhieuXuatDTO;
import DTO.SanPhamDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import BUS.SanPhamBUS;
import javax.swing.JOptionPane;



public class PhieuXuatDAO implements DAOinterface<PhieuXuatDTO> {
    
    public static PhieuXuatDAO getInstance(){
        return new PhieuXuatDAO();
    }

    @Override
    public int insert(PhieuXuatDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO `PHIEUXUAT` (`MPX` , `MNV`, `MKH`, `TIEN`, `TG`, `TT`) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMP());
            pst.setInt(2, t.getMNV());
            pst.setInt(3, t.getMKH());
            pst.setInt(4, (int) t.getTIEN());
            pst.setTimestamp(5, t.getTG());
            pst.setInt(6, t.getTT());
     
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print("Xuất thành công DAO phiếu xuất ");
        return result;
    }

    @Override
    public int update(PhieuXuatDTO t) {
    int result = 0;
    try {
        Connection con = (Connection) JDBCUtil.getConnection();
        String sql = "UPDATE `PHIEUXUAT` SET `MNV`=?, `MKH`=?, `TIEN`=?, `TG`=?, `TT`=? WHERE `MPX`=?";
        PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
        pst.setInt(1, t.getMNV());
        pst.setInt(2, t.getMKH());
        pst.setInt(3, (int) t.getTIEN());
        pst.setTimestamp(4, t.getTG());
        pst.setInt(5, t.getTT());
        pst.setInt(6, t.getMP());  // Thêm cập nhật MPX
        result = pst.executeUpdate();
        JDBCUtil.closeConnection(con);
    } catch (SQLException ex) {
        Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
}


    @Override
    public int delete(String t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE PHIEUXUAT SET TT = 0 WHERE MPX = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    

    @Override
    public ArrayList<PhieuXuatDTO> selectAll() {
        ArrayList<PhieuXuatDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM PHIEUXUAT ORDER BY MPX DESC";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int MP = rs.getInt("MPX");
                Timestamp TG = rs.getTimestamp("TG");
                int MKH = rs.getInt("MKH");
                int MNV = rs.getInt("MNV");
                long TIEN = rs.getLong("TIEN");
                int TT = rs.getInt("TT");
                PhieuXuatDTO PHIEUXUAT = new PhieuXuatDTO(MKH, MP, MNV, TG, TIEN, TT);
                result.add(PHIEUXUAT);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public PhieuXuatDTO selectById(String t) {
        PhieuXuatDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM PHIEUXUAT WHERE MPX=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int MP = rs.getInt("MPX");
                Timestamp TG = rs.getTimestamp("TG");
                int MKH = rs.getInt("MKH");
                int MNV = rs.getInt("MNV");
                long TIEN = rs.getLong("TIEN");
                int TT = rs.getInt("TT");
                result = new PhieuXuatDTO(MKH, MP, MNV, TG, TIEN, TT);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }

    public ArrayList<PhieuXuatDTO> selectByMKH(String t) {
        ArrayList<PhieuXuatDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM PHIEUXUAT WHERE MKH=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int MP = rs.getInt("MPX");
                Timestamp TG = rs.getTimestamp("TG");
                int MKH = rs.getInt("MKH");
                int MNV = rs.getInt("MNV");
                long TIEN = rs.getLong("TIEN");
                int TT = rs.getInt("TT");
                PhieuXuatDTO tmp = new PhieuXuatDTO(MKH, MP, MNV, TG, TIEN, TT);
                result.add(tmp);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }
    

       public void updateStatusToDeleted(int phieuId) throws SQLException {
        String query = "UPDATE PhieuXuat SET TT = ? WHERE MPX = ?";

        try (    Connection con = (Connection) JDBCUtil.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            // Set the status to -1 (or another value indicating deletion)
            statement.setInt(1, 0); // Status value
            statement.setInt(2, phieuId); // ID of the record to update

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No rows affected. The record with ID " + phieuId + " might not exist.");
            }
        }
    }
    
    public int updateDP(String t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE PHIEUXUAT SET TT = 1 WHERE MPX = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public int deletePhieu(int t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "DELETE FROM `PHIEUXUAT` WHERE MPX = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public ArrayList<PhieuXuatDTO> selectAllofKH(int MKH) {
        ArrayList<PhieuXuatDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM PHIEUXUAT WHERE MKH=? ORDER BY MPX DESC";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, MKH);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int MP = rs.getInt("MPX");
                Timestamp TG = rs.getTimestamp("TG");
                int kh = rs.getInt("MKH");
                int MNV = rs.getInt("MNV");
                long TIEN = rs.getLong("TIEN");
                int TT = rs.getInt("TT");
                PhieuXuatDTO PHIEUXUAT = new PhieuXuatDTO(kh, MP, MNV, TG, TIEN, TT);
                result.add(PHIEUXUAT);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }


    public boolean checkSLPx(int maphieu) {
        SanPhamBUS spBus = new SanPhamBUS();
        ArrayList<SanPhamDTO> SP = new ArrayList<SanPhamDTO>();
        ArrayList<ChiTietPhieuDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM CTPHIEUXUAT WHERE MPX=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, maphieu);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int masp = rs.getInt("MSP");
                int soluong = rs.getInt("SL");
                int tiennhap = rs.getInt("TIENXUAT");
                ChiTietPhieuDTO ct = new ChiTietPhieuDTO(maphieu, masp,  soluong, tiennhap);
                result.add(ct);
                SP.add(spBus.spDAO.selectById(ct.getMSP() + ""));
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
//        for (int i = 0; i < SP.size(); i++) {
//            if(result.get(i).getSL() > SP.get(i).getSL()){
//                return false;
//            }
//        }
        return true;
    }
    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlycuahangsach' AND TABLE_NAME   = 'PHIEUXUAT'";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs2 = pst.executeQuery(sql);
            if (!rs2.isBeforeFirst() ) {
                System.out.println("No data");
            } else {
                while ( rs2.next() ) {
                    result = rs2.getInt("AUTO_INCREMENT");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
     public static void updatePhieuXuatStatus(int mp, int status) throws SQLException {
        String sql = "UPDATE PHIEUXUAT SET TT = ? WHERE MPX = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, status);
            pst.setInt(2, mp);
            pst.executeUpdate();
        }
    }
} 
