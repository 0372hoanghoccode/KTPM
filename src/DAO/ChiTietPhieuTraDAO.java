package DAO;

import DTO.ChiTietPhieuTraDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.ResultSet;


public class ChiTietPhieuTraDAO implements ChiTietInterface<ChiTietPhieuTraDTO> {

    public static ChiTietPhieuTraDAO getInstance() {
        return new ChiTietPhieuTraDAO();
    }

    @Override
    public int insert(ArrayList<ChiTietPhieuTraDTO> t) {
        int result = 0;
        for (int i = 0; i < t.size(); i++) {
            try {
                Connection con = (Connection) JDBCUtil.getConnection();
                String sql = "INSERT INTO `CTPHIEUTRA` (`MPX`, `MSP`, `SL`, `TIENTHU`, `LYDO`) VALUES (?,?,?,?,?)";
                PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                pst.setInt(1, t.get(i).getMP());
                pst.setInt(2, t.get(i).getMSP());
                int SL = -(t.get(i).getSL());
                pst.setInt(3, t.get(i).getSL());
                SanPhamDAO.getInstance().updateSoLuongTon(t.get(i).getMSP(), SL);
                pst.setInt(4, t.get(i).getTIEN());
                pst.setString(5, t.get(i).getLYDO());
                result = pst.executeUpdate();
                JDBCUtil.closeConnection(con);
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietPhieuTraDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    

    public int reset(ArrayList<ChiTietPhieuTraDTO> t){
        int result = 0;
        for (int i = 0; i < t.size(); i++) {
        SanPhamDAO.getInstance().updateSoLuongTon(t.get(i).getMSP(), -(t.get(i).getSL()));
        delete(t.get(i).getMP()+"");
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "DELETE FROM CTPHIEUTRA WHERE MPX = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietPhieuTraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(ArrayList<ChiTietPhieuTraDTO> t, String pk) {
        int result = this.delete(pk);
        if (result != 0) {
            result = this.insert(t);
        }
        return result;
    }

    @Override
    public ArrayList<ChiTietPhieuTraDTO> selectAll(String t) {
        ArrayList<ChiTietPhieuTraDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM CTPHIEUTRA WHERE MPX = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int maphieu = rs.getInt("MPX");
                int MSP = rs.getInt("MSP");
                int SL = rs.getInt("SL");
                int tienxuat = rs.getInt("TIENTHU");
                String lydo = rs.getString("LYDO");
                ChiTietPhieuTraDTO ctphieu = new ChiTietPhieuTraDTO(maphieu, MSP, SL, tienxuat, lydo);
                result.add(ctphieu);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

}
