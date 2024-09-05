package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.ChiTietGioHangDTO;
import config.JDBCUtil;

public class ChiTietGioHangDAO implements ChiTietInterface<ChiTietGioHangDTO>{

    public static ChiTietGioHangDAO getInstance(){
        return new ChiTietGioHangDAO();
    }


    @Override
    public int insert(ArrayList<ChiTietGioHangDTO> t) {
        int result = 0;
        for (int i = 0; i < t.size(); i++) {
            try {
                Connection con = (Connection) JDBCUtil.getConnection();
                String sql = "INSERT INTO `CTGIOHANG` (`MKH`, `MSP`, `SL`, `MKM`, `TIENGIO`) VALUES (?,?,?,?,?)";
                PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                pst.setInt(1, t.get(i).getMKH());
                pst.setInt(2, t.get(i).getMSP());
                pst.setInt(3, t.get(i).getSL());
                pst.setString(4, t.get(i).getMKM());
                pst.setInt(5, t.get(i).getTIENGIO());
                result = pst.executeUpdate();
                JDBCUtil.closeConnection(con);
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietPhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public int insert(ChiTietGioHangDTO t) {
        int result = 0;
            try {
                Connection con = (Connection) JDBCUtil.getConnection();
                String sql = "INSERT INTO `CTGIOHANG` (`MKH`, `MSP`, `SL`, `MKM`, `TIENGIO`) VALUES (?,?,?,?,?)";
                PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                pst.setInt(1, t.getMKH());
                pst.setInt(2, t.getMSP());
                pst.setInt(3, t.getSL());
                pst.setString(4, t.getMKM());
                pst.setInt(5, t.getTIENGIO());
                result = pst.executeUpdate();
                JDBCUtil.closeConnection(con);
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietPhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        return result;
    }

    public int insertNoMKM(ChiTietGioHangDTO t) {
        int result = 0;
            try {
                Connection con = (Connection) JDBCUtil.getConnection();
                String sql = "INSERT INTO `CTGIOHANG` (`MKH`, `MSP`, `SL`, `TIENGIO`) VALUES (?,?,?,?)";
                PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                pst.setInt(1, t.getMKH());
                pst.setInt(2, t.getMSP());
                pst.setInt(3, t.getSL());
                pst.setInt(4, t.getTIENGIO());
                result = pst.executeUpdate();
                JDBCUtil.closeConnection(con);
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietPhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "DELETE FROM CTGIOHANG WHERE MKH = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietGioHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(ChiTietGioHangDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "DELETE FROM CTGIOHANG WHERE MKH = ? AND MSP = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMKH());
            pst.setInt(2, t.getMSP());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietGioHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(ArrayList<ChiTietGioHangDTO> t, String pk) {
        int result = this.delete(pk);
        if (result != 0) {
            result = this.insert(t);
        }
        return result;
    }

    public int update(ChiTietGioHangDTO t, String pk) {
        int result = this.delete(pk);
        if (result != 0) {
            result = this.insert(t);
        }
        return result;
    }

    @Override
    public ArrayList<ChiTietGioHangDTO> selectAll(String t) {
        ArrayList<ChiTietGioHangDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM CTGIOHANG WHERE MKH = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int mkh = rs.getInt("MKH");
                int masp = rs.getInt("MSP");
                int soluong = rs.getInt("SL");
                int tiengio = rs.getInt("TIENGIO");
                String mkm = rs.getString("MKM");
                ChiTietGioHangDTO ctphieu = new ChiTietGioHangDTO(mkh, masp, mkm, soluong, tiengio);
                result.add(ctphieu);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    
}
