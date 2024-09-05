package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.JDBCUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import DTO.KhuVucSachDTO;

public class KhuVucSachDAO implements DAOinterface<KhuVucSachDTO> {

    public static KhuVucSachDAO getInstance() {
        return new KhuVucSachDAO();
    }

    @Override
    public int insert(KhuVucSachDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO `KHUVUCSACH`(`MKVS`, `TEN`,`GHICHU`,`TT`) VALUES (?,?,?,1)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getMakhuvuc());
            pst.setString(2, t.getTenkhuvuc());
            pst.setString(3, t.getGhichu());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(KhuVucSachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(KhuVucSachDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `KHUVUCSACH` SET `TEN`=?,`GHICHU`=? WHERE `MKVS`=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t.getTenkhuvuc());
            pst.setString(2, t.getGhichu());
            pst.setInt(3, t.getMakhuvuc());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(KhuVucSachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE KHUVUCSACH SET TT = 0 WHERE  MKVS = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(KhuVucSachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<KhuVucSachDTO> selectAll() {
        ArrayList<KhuVucSachDTO> result = new ArrayList<KhuVucSachDTO>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM KHUVUCSACH WHERE TT = 1";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int MKVS = rs.getInt("MKVS");
                String TEN = rs.getString("TEN");
                String GHICHU = rs.getString("GHICHU");
                KhuVucSachDTO kvk = new KhuVucSachDTO(MKVS, TEN, GHICHU);
                result.add(kvk);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public KhuVucSachDTO selectById(String t) {
        KhuVucSachDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM KHUVUCSACH WHERE MKVS=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int MKVS = rs.getInt("MKVS");
                String TEN = rs.getString("TEN");
                String GHICHU = rs.getString("GHICHU");
                result = new KhuVucSachDTO(MKVS, TEN, GHICHU);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlycuahang' AND   TABLE_NAME   = 'KHUVUCSACH'";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs2 = pst.executeQuery(sql);
            if (!rs2.isBeforeFirst()) {
                System.out.println("No data");
            } else {
                while (rs2.next()) {
                    result = rs2.getInt("AUTO_INCREMENT");

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhuVucSachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
