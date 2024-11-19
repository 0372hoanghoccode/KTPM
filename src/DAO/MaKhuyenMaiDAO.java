package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.JDBCUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import DTO.MaKhuyenMaiDTO;


public class MaKhuyenMaiDAO  {
    public static MaKhuyenMaiDAO getInstance() {
        return new MaKhuyenMaiDAO();
    }

    public int insert(MaKhuyenMaiDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO `MAKHUYENMAI`(`MKM`, `MNV`, `TGBD`,`TGKT`, `TT`) VALUES (?,?,?,?,1)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getMKM());
            pst.setInt(2, t.getMNV());
            pst.setTimestamp(3, t.getTGBD());
            pst.setTimestamp(4, t.getTGKT());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(MaKhuyenMaiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

  
    public int update(MaKhuyenMaiDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `MAKHUYENMAI` SET `MKM`=?,`MNV`=?,`TGBD`=?,`TGKT`=? WHERE MKM=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getMKM());
            pst.setInt(2, t.getMNV());
            pst.setTimestamp(3, t.getTGBD());
            pst.setTimestamp(4, t.getTGKT());
            pst.setInt(5, t.getMKM());
            
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(MaKhuyenMaiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

  
    public int delete(int t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE  `MAKHUYENMAI` SET TT = 0 WHERE `MKM` = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(MaKhuyenMaiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
        
    public ArrayList<MaKhuyenMaiDTO> selectAllFullTrangThai() {
        ArrayList<MaKhuyenMaiDTO> result = new ArrayList<MaKhuyenMaiDTO>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM MAKHUYENMAI";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int MKM = rs.getInt("MKM");
                int MNV = rs.getInt("MNV");
                Timestamp TGBD = rs.getTimestamp("TGBD");
                Timestamp TGKT = rs.getTimestamp("TGKT");
                 int TT = rs.getInt("TT");
                MaKhuyenMaiDTO kh = new MaKhuyenMaiDTO(MKM, MNV, TGBD, TGKT , TT );
                result.add(kh);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    
    public ArrayList<MaKhuyenMaiDTO> selectAll() {
        ArrayList<MaKhuyenMaiDTO> result = new ArrayList<MaKhuyenMaiDTO>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM MAKHUYENMAI WHERE TT=1";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int MKM = rs.getInt("MKM");
                int MNV = rs.getInt("MNV");
                Timestamp TGBD = rs.getTimestamp("TGBD");
                Timestamp TGKT = rs.getTimestamp("TGKT");
                 int TT = rs.getInt("TT");
                MaKhuyenMaiDTO kh = new MaKhuyenMaiDTO(MKM, MNV, TGBD, TGKT , TT );
                result.add(kh);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
      public static String[] selectAll1() {
        ArrayList<String> resultList = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = JDBCUtil.getConnection(); // Mở kết nối
            String sql = "SELECT MKM FROM MAKHUYENMAI WHERE TT=1"; // Truy vấn chỉ lấy cột MKM
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String mkm = rs.getString("MKM");
                resultList.add(mkm);
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có
        } 

        // Chuyển đổi ArrayList sang mảng String[]
        return resultList.toArray(new String[0]);
    }

 
    public MaKhuyenMaiDTO selectById(int t) {
        MaKhuyenMaiDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM MAKHUYENMAI WHERE MKM=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int MKM = rs.getInt("MKM");
                int MNV = rs.getInt("MNV");
                Timestamp TGBD = rs.getTimestamp("TGBD");
                Timestamp TGKT = rs.getTimestamp("TGKT");
                int TT = rs.getInt("TT");
                result = new MaKhuyenMaiDTO(MKM, MNV, TGBD, TGKT, TT );
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

  public int cancelMKM(int mkm) {
    int result = 0;
    try {
        // Lấy kết nối đến cơ sở dữ liệu
        Connection con = (Connection) JDBCUtil.getConnection();

        // Cập nhật trạng thái TT thành -1 cho mã khuyến mãi
        String sql = "UPDATE MAKHUYENMAI SET TT = -1 WHERE MKM = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, mkm);

        // Thực thi câu lệnh SQL
        result = pst.executeUpdate();

        // Đóng kết nối
        JDBCUtil.closeConnection(con);
    } catch (SQLException ex) {
        Logger.getLogger(MaKhuyenMaiDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
}


  
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'testne' AND   TABLE_NAME   = 'MAKHUYENMAI'";
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
            Logger.getLogger(MaKhuyenMaiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public static int getMaxMaKhuyenMai() {
    int result = -1;
    try {
        Connection con = JDBCUtil.getConnection(); // Giả định rằng JDBCUtil đã kết nối thành công
        String sql = "SELECT MAX(MKM) AS maxMaKhuyenMai FROM MAKHUYENMAI"; // Thay 'maKhuyenMai' bằng tên cột thực tế
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (!rs.isBeforeFirst()) { 
            System.out.println("No data");
        } else {
            while (rs.next()) {
                result = rs.getInt("maxMaKhuyenMai"); // Lấy giá trị max của cột maKhuyenMai
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(MaKhuyenMaiDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
}
    
    public static void updateTrangThaiMaKhuyenMai(int maKhuyenMai, int trangThai) {
    try {
        Connection con = JDBCUtil.getConnection();
        String sql = "UPDATE MAKHUYENMAI SET TT = ? WHERE MKM = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, trangThai);  // Đặt trạng thái mới
        pst.setInt(2, maKhuyenMai);  // Đặt mã khuyến mãi cần cập nhật

        pst.executeUpdate();
        pst.close();
        con.close();
    } catch (SQLException ex) {
        Logger.getLogger(MaKhuyenMaiDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public static boolean isMKMUsedInHoaDon(int mkm) {
    boolean isUsed = false;
    try {
         Connection con = JDBCUtil.getConnection();
        String sql = "SELECT COUNT(*) FROM ctphieuxuat WHERE MKM = ?";
         PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, mkm);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            isUsed = rs.getInt(1) > 0; // Nếu số lượng > 0, MKM đã được sử dụng
        }
        rs.close();
        ps.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return isUsed;
}

    
}
