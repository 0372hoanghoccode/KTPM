package DAO;

import DTO.ChiTietPhieuTraDTO;
import DTO.PhieuTraDTO;
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


public class PhieuTraDAO implements DAOinterface<PhieuTraDTO> {
    
    public static PhieuTraDAO getInstance(){
        return new PhieuTraDAO();
    }

    @Override
    public int insert(PhieuTraDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO `PHIEUTRA` (`MPX`,`MNV`, `MKH`, `TIEN`, `TG`, `TT`) VALUES (?,?,?,?,?,1)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMP());
            pst.setInt(2, t.getMNV());
            pst.setInt(3, t.getMKH());
            pst.setInt(4, (int) t.getTIEN());
            pst.setTimestamp(5, t.getTG());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(PhieuTraDTO t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `PHIEUTRA` SET `MNV`=?, `MKH`=?, `TIEN`=?, `TG`=?, `TT`=? WHERE `MPX`=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getMNV());
            pst.setInt(2, t.getMKH());
            pst.setInt(3, (int) t.getTIEN());
            pst.setTimestamp(4, t.getTG());
            pst.setInt(5, t.getTT());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE PHIEUTRA SET TT = 0 WHERE MPX = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    

    @Override
    public ArrayList<PhieuTraDTO> selectAll() {
        ArrayList<PhieuTraDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM PHIEUTRA ORDER BY MPX DESC";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int MP = rs.getInt("MPX");
                Timestamp TG = rs.getTimestamp("TG");
                int MKH = rs.getInt("MKH");
                int MNV = rs.getInt("MNV");
                long TIEN = rs.getLong("TIEN");
                int TT = rs.getInt("TT");
                PhieuTraDTO PHIEUTRA = new PhieuTraDTO(MKH, MP, MNV, TG, TIEN, TT);
                result.add(PHIEUTRA);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public PhieuTraDTO selectById(String t) {
        PhieuTraDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM PHIEUTRA WHERE MPX=?";
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
                result = new PhieuTraDTO(MKH, MP, MNV, TG, TIEN, TT);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }
    
    public PhieuTraDTO cancel(int phieu) {
        PhieuTraDTO result = null;
        try {
            
            ArrayList<ChiTietPhieuTraDTO> chitietphieu = ChiTietPhieuTraDAO.getInstance().selectAll(phieu+"");
            ChiTietPhieuTraDAO.getInstance().reset(chitietphieu);
            
            deletePhieu(phieu);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
    public int deletePhieu(int t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "DELETE FROM `PHIEUTRA` WHERE MPX = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public ArrayList<PhieuTraDTO> selectAllofKH(int MKH) {
        ArrayList<PhieuTraDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM PHIEUTRA WHERE MKH=? ORDER BY MPX DESC";
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
                PhieuTraDTO PHIEUTRA = new PhieuTraDTO(kh, MP, MNV, TG, TIEN, TT);
                result.add(PHIEUTRA);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    public boolean checkSLPt(int maphieu) {
        SanPhamBUS spBus = new SanPhamBUS();
        ArrayList<SanPhamDTO> SP = new ArrayList<SanPhamDTO>();
        ArrayList<ChiTietPhieuTraDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM CTPHIEUTRA WHERE MPX=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, maphieu);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int masp = rs.getInt("MSP");
                int soluong = rs.getInt("SL");
                int tiennhap = rs.getInt("TIENNHAP");
                String lydo = rs.getString("LYDO");
                ChiTietPhieuTraDTO ct = new ChiTietPhieuTraDTO(maphieu, masp,  soluong, tiennhap, lydo);
                result.add(ct);
                SP.add(spBus.spDAO.selectById(ct.getMSP() + ""));
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        for (int i = 0; i < SP.size(); i++) {
            if(result.get(i).getSL() > SP.get(i).getSL()){
                return false;
            }
        }
        return true;
    }
    
    public int cancelPhieuTra(int maphieu){
        int result = 0;
        ArrayList<ChiTietPhieuTraDTO> arrCt = ChiTietPhieuTraDAO.getInstance().selectAll(Integer.toString(maphieu));
        for (ChiTietPhieuTraDTO chiTietPhieuTraDTO : arrCt) {
            SanPhamDAO.getInstance().updateSoLuongTon(chiTietPhieuTraDTO.getMSP(), -(chiTietPhieuTraDTO.getSL()));
        }
        ChiTietPhieuTraDAO.getInstance().delete(Integer.toString(maphieu));
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "DELETE FROM PHIEUTRA WHERE MPX = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, maphieu);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(PhieuTraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlycuahangsach' AND TABLE_NAME   = 'PHIEUTRA'";
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
            Logger.getLogger(PhieuTraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
