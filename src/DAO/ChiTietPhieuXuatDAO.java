package DAO;

import DTO.ChiTietMaKhuyenMaiDTO;
import DTO.ChiTietPhieuDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import BUS.MaKhuyenMaiBUS;
import DTO.ChiTietPhieuNhapDTO;
import DTO.ChiTietPhieuXuatDTO;

import java.sql.ResultSet;


public class ChiTietPhieuXuatDAO implements ChiTietInterface<ChiTietPhieuDTO> {

    public static ChiTietPhieuXuatDAO getInstance() {
        return new ChiTietPhieuXuatDAO();
    }

    
    
public static void insert(ChiTietPhieuDTO chiTietPhieu) throws SQLException {
    String sql = "INSERT INTO ctphieuxuat (MPX, MSP, SL, Tienxuat, Giagiam, GiaThanhToan, MKM) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try (
        Connection con = JDBCUtil.getConnection(); // Không cần cast
        PreparedStatement pstmt = con.prepareStatement(sql)
    ) {
        pstmt.setInt(1, chiTietPhieu.getMP());
        pstmt.setInt(2, chiTietPhieu.getMSP());
        pstmt.setInt(3, chiTietPhieu.getSL());
        pstmt.setInt(4, chiTietPhieu.getTIEN());
        pstmt.setInt(5, chiTietPhieu.getGiaGiam()); // Giả sử có getter cho giagiam
        pstmt.setInt(6, chiTietPhieu.getGiaThanhToan()); // Giả sử có getter cho giaThanhToan
        pstmt.setString(7, chiTietPhieu.getMKM()); // Giả sử có getter cho MKM
        
        pstmt.executeUpdate();
    }
}

    
    @Override
  public int insert(ArrayList<ChiTietPhieuDTO> t) {
    int result = 0;
    String sql = "INSERT INTO `CTPHIEUXUAT` (`MPX`, `MSP`, `SL`, `TIENXUAT`, `GIAGIAM`, `GIATHANHTOAN`, `MKM`) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection con = JDBCUtil.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {
        
        for (ChiTietPhieuDTO chiTiet : t) {
            pst.setInt(1, chiTiet.getMP());
            pst.setInt(2, chiTiet.getMSP());
            pst.setInt(3, chiTiet.getSL());
            pst.setInt(4, chiTiet.getTIEN());
            pst.setInt(5, chiTiet.getGiaGiam()); // Đảm bảo getter có sẵn
            pst.setInt(6, chiTiet.getGiaThanhToan()); // Đảm bảo getter có sẵn
            pst.setString(7, chiTiet.getMKM()); // Đảm bảo getter có sẵn
            
            result += pst.executeUpdate();
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(ChiTietPhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return result;
}


    public int insertGH(ArrayList<ChiTietPhieuDTO> t) {
        int result = 0;
        for (int i = 0; i < t.size(); i++) {
            try {
                Connection con = (Connection) JDBCUtil.getConnection();
                String sql = "INSERT INTO `CTPHIEUXUAT` (`MPX`, `MSP`, `SL`, `TIENXUAT`) VALUES (?,?,?,?)";
                PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
                pst.setInt(1, t.get(i).getMP());
                pst.setInt(2, t.get(i).getMSP());
                pst.setInt(3, t.get(i).getSL());
                pst.setInt(4, t.get(i).getTIEN());
                result = pst.executeUpdate();
                JDBCUtil.closeConnection(con);
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietPhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
//    public int insert(ArrayList<ChiTietPhieuDTO> t, ArrayList<ChiTietMaKhuyenMaiDTO> ctmkm) {
//        int result = 0;
//        for (int i = 0; i < t.size(); i++) {
//            try {
//                Connection con = (Connection) JDBCUtil.getConnection();
//                String sql = "INSERT INTO `CTPHIEUXUAT` (`MPX`, `MSP`, `MKM`, SL`,  `TIENXUAT`) VALUES (?,?,?,?.?)";
//                PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
//                pst.setInt(1, t.get(i).getMP());
//                pst.setInt(2, t.get(i).getMSP());
//                MaKhuyenMaiBUS mkmbus = new MaKhuyenMaiBUS();
//                ChiTietMaKhuyenMaiDTO mkm = mkmbus.findCT(ctmkm, t.get(i).getMSP());
//                pst.setString(4, mkm.getMKM());
//                int SL = -(t.get(i).getSL());
//                pst.setInt(4, t.get(i).getSL());
//                SanPhamDAO.getInstance().updateSoLuongTon(t.get(i).getMSP(), SL);
//                pst.setInt(5, t.get(i).getTIEN());
//                result = pst.executeUpdate();
//                JDBCUtil.closeConnection(con);
//            } catch (SQLException ex) {
//                Logger.getLogger(ChiTietPhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return result;
//    }

//    public int reset(ArrayList<ChiTietPhieuDTO> t){
//        int result = 0;
//        for (int i = 0; i < t.size(); i++) {
//        SanPhamDAO.getInstance().updateSoLuongTon(t.get(i).getMSP(), +(t.get(i).getSL()));
//        delete(t.get(i).getMP()+"");
//        }
//        return result;
//    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "DELETE FROM CTPHIEUXUAT WHERE MPX = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietPhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(ArrayList<ChiTietPhieuDTO> t, String pk) {
        int result = this.delete(pk);
        if (result != 0) {
            result = this.insert(t);
        }
        return result;
    }

    @Override
public ArrayList<ChiTietPhieuDTO> selectAll(String t) {
    ArrayList<ChiTietPhieuDTO> result = new ArrayList<>();
    try {
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT MPX, MSP, SL, TIENXUAT, GIAGIAM, GIATHANHTOAN, MKM FROM CTPHIEUXUAT WHERE MPX = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, t);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            int maphieu = rs.getInt("MPX");
            int MSP = rs.getInt("MSP");
            int SL = rs.getInt("SL");
            int tienxuat = rs.getInt("TIENXUAT");
            int giagiam = rs.getInt("GIAGIAM"); // Lấy giá giảm
            int giathanhToan = rs.getInt("GIATHANHTOAN"); // Lấy giá thanh toán
            String maKM = rs.getString("MKM"); // Lấy mã khuyến mãi
            
            ChiTietPhieuDTO ctphieu = new ChiTietPhieuDTO(maphieu, MSP, SL, tienxuat, giagiam, giathanhToan, maKM);
            result.add(ctphieu);
        }
        
        JDBCUtil.closeConnection(con);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return result;
}


public static ArrayList<ChiTietPhieuXuatDTO> getChiTietByPhieuXuat(int maPhieuXuat) {
    ArrayList<ChiTietPhieuXuatDTO> chiTietList = new ArrayList<>();
    String sql = "SELECT * FROM ctphieuxuat WHERE MPX = ?";

    try (Connection con = JDBCUtil.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setInt(1, maPhieuXuat);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            // Tạo đối tượng ChiTietPhieuXuatDTO từ kết quả truy vấn
            ChiTietPhieuXuatDTO chiTiet = new ChiTietPhieuXuatDTO(
                rs.getInt("MPX"),
                rs.getInt("MSP"),
                rs.getInt("SL"),
                rs.getInt("TIENXUAT"),
                rs.getInt("GIAGIAM"), // Thêm giá giảm
                rs.getInt("GIATHANHTOAN"), // Thêm giá thanh toán
                rs.getString("MKM") // Thêm mã khuyến mãi
            );
            chiTietList.add(chiTiet);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return chiTietList;
}

  public static int getTotalCostByProductCode(int masp) {
    int totalCost = 0;
    try {
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT SUM(GiaThanhToan*SL) AS TotalCost FROM CTPHIEUXuat WHERE MSP = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, masp);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            totalCost = rs.getInt("TotalCost");
        }
        JDBCUtil.closeConnection(con);
    } catch (SQLException e) {
        System.out.println(e);
    }
    return totalCost;
}
  public static ArrayList<ChiTietPhieuXuatDTO> selectAllByProductCode(int masp) {
    ArrayList<ChiTietPhieuXuatDTO> result = new ArrayList<>();
    try {
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT * FROM CTPHIEUXuat WHERE MSP = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, masp);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int maphieu = rs.getInt("MPX");
            int soluong = rs.getInt("SL");
            int tienxuat = rs.getInt("TIENXuat");
            int giaGiam = rs.getInt("GiaGiam");
            int giaThanhToan = rs.getInt("giaThanhToan");
            String MKM = rs.getString("MKM");
            ChiTietPhieuXuatDTO ctphieu = new ChiTietPhieuXuatDTO(maphieu, masp, soluong, tienxuat, giaGiam , giaThanhToan, MKM);
            result.add(ctphieu);
        }
        JDBCUtil.closeConnection(con);
    } catch (SQLException e) {
        System.out.println(e);
    }
    return result;
}
  
}
