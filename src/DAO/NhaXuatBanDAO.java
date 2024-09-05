package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.JDBCUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import DTO.NhaXuatBanDTO;

public class NhaXuatBanDAO implements DAOinterface<NhaXuatBanDTO>{
    public static NhaXuatBanDAO getInstance(){
        return new NhaXuatBanDAO();
    }

    @Override
    public int insert(NhaXuatBanDTO t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO `NHAXUATBAN`(`MNXB`, `TEN`, `DIACHI`, `EMAIL`, `SDT`, `TT`) VALUES (?,?,?,?,?,1)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getManxb());
            pst.setString(2, t.getTennxb());
            pst.setString(3, t.getDiachi());
            pst.setString(4, t.getEmail());
            pst.setString(5, t.getSdt());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(NhaXuatBanDTO t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `NHAXUATBAN` SET `TEN`=?,`DIACHI`=?,`EMAIL`=?,`SDT`=? WHERE `MNXB`= ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t.getTennxb());
            pst.setString(2, t.getDiachi());
            pst.setString(3, t.getEmail());
            pst.setString(4, t.getSdt());
            pst.setInt(5, t.getManxb());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE NHAXUATBAN SET TT = 0 WHERE MNXB = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<NhaXuatBanDTO> selectAll() {
        ArrayList<NhaXuatBanDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM NHAXUATBAN WHERE TT = 1";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int mancc = rs.getInt("MNXB");
                String tenncc = rs.getString("TEN");
                String DIACHI = rs.getString("DIACHI");
                String EMAIL = rs.getString("EMAIL");
                String SDT = rs.getString("SDT");
                NhaXuatBanDTO ncc = new NhaXuatBanDTO(mancc, tenncc, DIACHI, EMAIL, SDT);
                result.add(ncc);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public NhaXuatBanDTO selectById(String t) {
        NhaXuatBanDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM NHAXUATBAN WHERE MNXB=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int mancc = rs.getInt("MNXB");
                String tenncc = rs.getString("TEN");
                String DIACHI = rs.getString("DIACHI");
                String EMAIL = rs.getString("DIACHI");
                String SDT = rs.getString("SDT");
                
                result = new NhaXuatBanDTO(mancc,tenncc,DIACHI,EMAIL,SDT);
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
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlycuahangsach' AND   TABLE_NAME   = 'NHAXUATBAN'";
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
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
