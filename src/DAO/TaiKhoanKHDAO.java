package DAO;

import config.JDBCUtil;
import helper.BCrypt;
import DTO.TaiKhoanDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaiKhoanKHDAO implements DAOinterface<TaiKhoanDTO>{
    
    public static TaiKhoanKHDAO getInstance(){
        return new TaiKhoanKHDAO();
    }

    @Override
    public int insert(TaiKhoanDTO t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO `TAIKHOANKH` (`MKH`, `TDN`, `MK`, `MNQ`, `TT`) VALUES (?,?,?,?,?)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getMNV());
            pst.setString(2, t.getTDN());
       //     pst.setString(3, BCrypt.hashpw(t.getMK(), BCrypt.gensalt(12)));
            pst.setString(3, t.getMK());
            pst.setInt(4, t.getMNQ());
            pst.setInt(5, t.getTT());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int update(TaiKhoanDTO t) {
    int result = 0;
    try {
        System.out.println("Hello2");
        Connection con = JDBCUtil.getConnection();
        String sql = "UPDATE TAIKHOANKH SET TDN = ?, MK = ?, TT = ?, MNQ = ? WHERE MKH = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, t.getTDN());  // Username
        pst.setString(2, t.getMK());   // Password
        pst.setInt(3, t.getTT());      // Status
        pst.setInt(4, t.getMNQ());     // Role
        pst.setInt(5, t.getMNV());     // User ID (Primary Key)
        result = pst.executeUpdate();
        JDBCUtil.closeConnection(con);
    } catch (SQLException ex) {
        Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
}


    public int updateTTCXL(String t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE TAIKHOANKH KH JOIN KHACHHANG KH ON KH.MKH = KH.MKH SET KH.TT = 2 WHERE `EMAIL` = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
}
    
    
 public void updatePass(String email, String password) {
    String sql = "UPDATE TAIKHOANKH tk " +
                 "JOIN KHACHHANG kh ON tk.MKH = kh.MKH " +
                 "SET tk.MK = ? " +
                 "WHERE kh.EMAIL = ?";
    
    try (Connection con = JDBCUtil.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        // Đặt giá trị cho các tham số trong câu lệnh SQL
        pst.setString(1, password);
        pst.setString(2, email);
        
        // Thực thi câu lệnh SQL
        pst.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, "Error updating password", ex);
    }
}

    public TaiKhoanDTO selectByEmail(String t) {
        TaiKhoanDTO tk = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM TAIKHOANKH TK JOIN KHACHHANG KH ON TK.MKH = KH.MKH WHERE KH.EMAIL = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int MKH = rs.getInt("MKH");
                String TDN = rs.getString("TDN");
                String MK = rs.getString("MK");
                int TT = rs.getInt("TT");
                int MNQ = rs.getInt("MNQ");
                tk = new TaiKhoanDTO(MKH, TDN, MK, MNQ, TT);
                return tk;
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception           
        }
        return tk;
    }
    public TaiKhoanDTO selectBySDT(String sdt) {
    TaiKhoanDTO tk = null;
    try {
        Connection con = JDBCUtil.getConnection();
        // Sử dụng bí danh khác nhau cho các bảng
        String sql = "SELECT * FROM TAIKHOANKH TK JOIN KHACHHANG KH ON TK.MKH = KH.MKH WHERE KH.SDT = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, sdt);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int MKH = rs.getInt("MKH");
            String TDN = rs.getString("TDN");
            String MK = rs.getString("MK");
            int TT = rs.getInt("TT");
            int MNQ = rs.getInt("MNQ");
            tk = new TaiKhoanDTO(MKH, TDN, MK, MNQ, TT);
            return tk;
        }
        JDBCUtil.closeConnection(con);
    } catch (Exception e) {
        e.printStackTrace(); // In thông báo lỗi ra console hoặc ghi vào log
    }
    return tk;
}


    
    public void sendOpt(String email, String opt){
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE TAIKHOANKH KH JOIN KHACHHANG KH ON KH.MKH = NV.MKH SET `OTP` = ? WHERE `EMAIL` = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, opt);
            pst.setString(2, email);
            pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean checkOtp(String email, String otp){
        boolean check = false;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM TAIKHOANKH KH JOIN KHACHHANG KH ON KH.MKH = NV.MKH WHERE NV.EMAIL = ? AND KH.OTP = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, otp);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                check = true;
                return check;
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return check;
    }

    @Override
    public int delete(String t) {
        int result = 0 ;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `TAIKHOANKH` SET `TT`= '-1' WHERE MKH = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(t));
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
//    public ArrayList<TaiKhoanDTO> selectAll() {
//        ArrayList<TaiKhoanDTO> result = new ArrayList<TaiKhoanDTO>();
//        try {
//            Connection con = (Connection) JDBCUtil.getConnection();
//            String sql = "SELECT * FROM TAIKHOANKH WHERE TT = '0' OR TT = '1' OR TT = '2'";
//            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
//            ResultSet rs = (ResultSet) pst.executeQuery();
//            while(rs.next()){
//                int MKH = rs.getInt("MKH");
//                String TDN = rs.getString("TDN");
//                String MK = rs.getString("MK");
//                int MNQ = rs.getInt("MNQ");
//                int TT = rs.getInt("TT");
//                TaiKhoanDTO tk = new TaiKhoanDTO(MKH, TDN, MK, MNQ, TT);
//                result.add(tk);
//            }
//            JDBCUtil.closeConnection(con);
//        } catch (Exception e) {
//        }
//        return result;
//    }
    public ArrayList<TaiKhoanDTO> selectAll() {
        System.out.print("Lấy data từ cơ sở dữ liệu nè");
        ArrayList<TaiKhoanDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOANKH WHERE TT = '0' OR TT = '1' OR TT = '2'";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int MKH = rs.getInt("MKH");
                String TDN = rs.getString("TDN");
                String MK = rs.getString("MK");
                int MNQ = rs.getInt("MNQ");
                int TT = rs.getInt("TT");

                TaiKhoanDTO tk = new TaiKhoanDTO(MKH, TDN, MK, MNQ, TT);
                result.add(tk);
            }
        } catch (Exception e) {
          //  LOGGER.log(Level.SEVERE, "Error retrieving accounts", e);
        }

        return result;
    }

    @Override
    public TaiKhoanDTO selectById(String t) {
        TaiKhoanDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM TAIKHOANKH WHERE MKH = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()) {
                int MKH = rs.getInt("MKH");
                String TDN = rs.getString("TDN");
                String MK = rs.getString("MK");
                int TT = rs.getInt("TT");
                int MNQ = rs.getInt("MNQ");
                TaiKhoanDTO tk = new TaiKhoanDTO(MKH, TDN, MK, MNQ, TT);
                result = tk;
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }
    
    public TaiKhoanDTO selectByUser(String t) {
        TaiKhoanDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM TAIKHOANKH WHERE TDN = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int MKH = rs.getInt("MKH");
                String TDN = rs.getString("TDN");
                String MK = rs.getString("MK");
                int TT = rs.getInt("TT");
                int MNQ = rs.getInt("MNQ");
                TaiKhoanDTO tk = new TaiKhoanDTO(MKH, TDN, MK, MNQ, TT);
                result = tk;
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
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlycuahangsach' AND  TABLE_NAME = 'TAIKHOANKH'";
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
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
