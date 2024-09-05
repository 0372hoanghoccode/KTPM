package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.GioHangDTO;
import config.JDBCUtil;

public class GioHangDAO implements DAOinterface<GioHangDTO> {
    public static GioHangDAO getInstance(){
        return new GioHangDAO();
    }

    @Override
    public int insert(GioHangDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO `GIOHANG` (`MKH`, `TIEN`, `TG`, `TT`) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMKH());
            pst.setInt(2, (int) t.getTIEN());
            pst.setTimestamp(3, t.getTG());
            pst.setInt(4, t.getTT());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(GioHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(GioHangDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `GIOHANG` SET  `TIEN`=?, `TG`=?, `TT`=? WHERE `MKH`=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, (int)t.getTIEN());
            pst.setTimestamp(2, t.getTG());
            pst.setInt(3, t.getMKH()); 
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception ex) {
            Logger.getLogger(GioHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "DELETE FROM GIOHANG WHERE MKH = ?";
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
    public ArrayList<GioHangDTO> selectAll() {
        ArrayList<GioHangDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM GIOHANG ";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                Timestamp TG = rs.getTimestamp("TG");
                int MKH = rs.getInt("MKH");
                long TIEN = rs.getLong("TIEN");
                int TT = rs.getInt("TT");
                GioHangDTO PHIEUXUAT = new GioHangDTO(MKH, TIEN, TG, TT);
                result.add(PHIEUXUAT);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public GioHangDTO selectById(String t) {
        GioHangDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM GIOHANG WHERE MKH=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                Timestamp TG = rs.getTimestamp("TG");
                int MKH = rs.getInt("MKH");
                long TIEN = rs.getLong("TIEN");
                int TT = rs.getInt("TT");
                result = new GioHangDTO(MKH, TIEN, TG, TT);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }
    

    @Override
    public int getAutoIncrement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAutoIncrement'");
    }
}
