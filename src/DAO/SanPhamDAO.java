package DAO;

import static DAO.ChiTietLoHangDAO.getTotalQuantityForProduct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.JDBCUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import DTO.SanPhamDTO;

public class SanPhamDAO implements DAOinterface<SanPhamDTO> {

    public static SanPhamDAO getInstance() {
        return new SanPhamDAO();
    }

    @Override
    public int insert(SanPhamDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            // `ISBN`
            String sql = "INSERT INTO `SANPHAM` (`MSP`, `TEN`, `HINHANH`, "
                  + "`DANHMUC`, `NAMXB`, `MNXB`, `TENTG`, `MKVS`, `SL`,`GiaBan`,`TT`) VALUES (?,?,?,?,?,?,?,?,?,?,0)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getMSP());
            pst.setString(2, t.getTEN());
            pst.setString(3, t.getHINHANH());
            pst.setString(4, t.getDANHMUC());
            pst.setInt(5, t.getNAMXB());
            pst.setInt(6, t.getMNXB());
            pst.setString(7, t.getTENTG());
            pst.setInt(8, t.getMKVS());
          
            pst.setInt(9, t.getSL());
                      pst.setInt(10, t.getTIENX());
//            pst.setString(12, t.getISBN());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
   public int update(SanPhamDTO t) {
    int result = 0;
    Connection con = null;
    PreparedStatement pst = null;

    try {
        // Lấy kết nối cơ sở dữ liệu
        con = JDBCUtil.getConnection();
        
        // Câu lệnh SQL để cập nhật thông tin sản phẩm
        String sql = "UPDATE SANPHAM SET "
                + "TEN = ?, "
                + "HINHANH = ?, "
                + "DANHMUC = ?, "
                + "NAMXB = ?, "
                + "MNXB = ?, "
                + "TENTG = ?, "
                + "MKVS = ?, "
                + "SL = ?, "
                + "GiaBan = ? "
                + "WHERE MSP = ?";

        // Tạo đối tượng PreparedStatement
        pst = con.prepareStatement(sql);

        // Gán giá trị cho các tham số của câu lệnh SQL
        pst.setString(1, t.getTEN());
        pst.setString(2, t.getHINHANH());
        pst.setString(3, t.getDANHMUC());
        pst.setInt(4, t.getNAMXB());
        pst.setInt(5, t.getMNXB());
        pst.setString(6, t.getTENTG());
        pst.setInt(7, t.getMKVS());
        pst.setInt(8, t.getSL());
        pst.setInt(9, t.getTIENX());
        pst.setInt(10, t.getMSP()); // Đặt giá trị cho điều kiện WHERE

        // Thực thi câu lệnh SQL
        result = pst.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return result;
}


    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `SANPHAM` SET `TT` = -1 WHERE MSP = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
        @Override
    public  ArrayList<SanPhamDTO> selectAll() {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM SANPHAM";
         //    WHERE `TT`= 1
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int madm = rs.getInt("MSP");
                String tendm = rs.getString("TEN");
                String HINHANH = rs.getString("HINHANH");
                String DANHMUC = rs.getString("DANHMUC");
                int NAMXB = rs.getInt("NAMXB");
                int MNXB = rs.getInt("MNXB");
                String TENTG = rs.getString("TENTG");
                int MKVS = rs.getInt("MKVS");
                int TIENX = rs.getInt("GiaBan");
              //  int TIENN = rs.getInt("TIENN");
                int SL = rs.getInt("SL");
                 int TT = rs.getInt("TT");
              //  String ISBN = rs.getString("ISBN");
                SanPhamDTO sp = new SanPhamDTO(madm, tendm, HINHANH, DANHMUC, NAMXB, MNXB, TENTG, MKVS, TIENX, SL, TT );
                result.add(sp);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }

    public  ArrayList<SanPhamDTO> selectAlltrangthaikhacam1() {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM SANPHAM  WHERE `TT`!= -1 " ; 
         
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int madm = rs.getInt("MSP");
                String tendm = rs.getString("TEN");
                String HINHANH = rs.getString("HINHANH");
                String DANHMUC = rs.getString("DANHMUC");
                int NAMXB = rs.getInt("NAMXB");
                int MNXB = rs.getInt("MNXB");
                String TENTG = rs.getString("TENTG");
                int MKVS = rs.getInt("MKVS");
                int TIENX = rs.getInt("GiaBan");
              //  int TIENN = rs.getInt("TIENN");
                int SL = rs.getInt("SL");
                 int TT = rs.getInt("TT");
              //  String ISBN = rs.getString("ISBN");
                SanPhamDTO sp = new SanPhamDTO(madm, tendm, HINHANH, DANHMUC, NAMXB, MNXB, TENTG, MKVS, TIENX, SL, TT );
                result.add(sp);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }
          public  static ArrayList<SanPhamDTO> selectAll1() {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM SANPHAM WHERE `TT`= 1";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int madm = rs.getInt("MSP");
                String tendm = rs.getString("TEN");
                String HINHANH = rs.getString("HINHANH");
                String DANHMUC = rs.getString("DANHMUC");
                int NAMXB = rs.getInt("NAMXB");
                int MNXB = rs.getInt("MNXB");
                String TENTG = rs.getString("TENTG");
                int MKVS = rs.getInt("MKVS");
                int TIENX = rs.getInt("GiaBan");
              //  int TIENN = rs.getInt("TIENN");
                int SL = rs.getInt("SL");
                   int TT  = rs.getInt("TT");
              //  String ISBN = rs.getString("ISBN");
                SanPhamDTO sp = new SanPhamDTO(madm, tendm, HINHANH, DANHMUC, NAMXB, MNXB, TENTG, MKVS, TIENX, SL , TT );
                result.add(sp);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public SanPhamDTO selectById(String t) {
        SanPhamDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM SANPHAM WHERE MSP=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int madm = rs.getInt("MSP");
                String tendm = rs.getString("TEN");
                String HINHANH = rs.getString("HINHANH");
                String DANHMUC = rs.getString("DANHMUC");
                int NAMXB = rs.getInt("NAMXB");
                int MNXB = rs.getInt("MNXB");
                String TENTG = rs.getString("TENTG");
                int MKVS = rs.getInt("MKVS");
                int TIENX = rs.getInt("TIENX");
                int SL = rs.getInt("SL");
                int TT = rs.getInt("TT");
                result = new SanPhamDTO(madm, tendm, HINHANH, DANHMUC, NAMXB, MNXB, TENTG, MKVS, TIENX, SL, TT);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }
    
  

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'testne' AND   TABLE_NAME   = 'SANPHAM'";
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
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    
  
    public static int getMaxMaSanPham() {
        int result = -1;
        try {
            Connection con = JDBCUtil.getConnection(); // Giả định rằng JDBCUtil đã kết nối thành công
            String sql = "SELECT MAX(MSP) AS maxMaSanPham FROM SANPHAM"; // Thay 'maSanPham' bằng tên cột tương ứng
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) { 
                System.out.println("No data");
            } else {
                while (rs.next()) {
                    result = rs.getInt("maxMaSanPham"); // Lấy giá trị max của cột maSanPham
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    return result;
}

//    public int updateSoLuongTon(int MSP, int soluong) {
//        int quantity_current = this.selectById(Integer.toString(MSP)).getSL();
////        System.out.print("Còn nhiêu sau khi bán:" + quantity_current);
////        System.out.println("Số lượng trả nè: " + soluong);
//        
//        int result = 0;
//        int quantity_change = quantity_current + soluong;
//        try {
//            Connection con = (Connection) JDBCUtil.getConnection();
//            String sql = "UPDATE `SANPHAM` SET `SL`=? WHERE MSP = ?";
//            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
//            pst.setInt(1, quantity_change);
//            pst.setInt(2, MSP);
//            result = pst.executeUpdate();
//            JDBCUtil.closeConnection(con);
//        } catch (SQLException ex) {
//            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return result;
//    }
       public int updateSoLuongTonSauKhiTraHang(int MSP, int soluong) {
        int quantity_current = this.selectById(Integer.toString(MSP)).getSL();
//        System.out.print("Còn nhiêu sau khi bán:" + quantity_current);
//        System.out.println("Số lượng trả nè: " + soluong);
        
        int result = 0;
        int quantity_change = quantity_current - soluong;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `SANPHAM` SET `SL`=? WHERE MSP = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, quantity_change);
            pst.setInt(2, MSP);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
        public static String getTenSanPhamByMaSP(int maSP) {
        String tenSP = null;
        // SQL truy vấn để lấy tên sản phẩm từ mã sản phẩm
        String sql = "SELECT ten FROM SanPham WHERE MSP = ?";

        try (Connection con = JDBCUtil.getConnection();  // Kết nối cơ sở dữ liệu
             PreparedStatement pst = con.prepareStatement(sql)) {  // Tạo PreparedStatement
            
            // Thiết lập tham số truy vấn
            pst.setInt(1, maSP);

            // Thực hiện truy vấn
            ResultSet rs = pst.executeQuery();
            
            // Xử lý kết quả truy vấn
            if (rs.next()) {
                tenSP = rs.getString("ten");  // Lấy tên sản phẩm
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tenSP;  // Trả về tên sản phẩm
    }
            
        public static ArrayList<SanPhamDTO> filterProductsWithPositiveQuantity(ArrayList<SanPhamDTO> listSP) {
        ArrayList<SanPhamDTO> filteredList = new ArrayList<>();
        
        for (SanPhamDTO sanpham : listSP) {
            int productCode = sanpham.getMSP(); // Lấy mã sản phẩm từ đối tượng SanPhamDTO
            int totalQuantity = getTotalQuantityForProduct(productCode); // Lấy tổng số lượng từ cơ sở dữ liệu
            
            if (totalQuantity > 0) {
                filteredList.add(sanpham); // Thêm sản phẩm vào danh sách mới nếu tổng số lượng > 0
            }
        }
        
        return filteredList; // Trả về danh sách sản phẩm đã lọc
    }
      
       public static int countLoHangByMaSP(int maSP) {
        int soLuongLoHang = 0;

        // SQL truy vấn để đếm số lô hàng có mã sản phẩm
        String sql = "SELECT COUNT(*) AS SoLuongLoHang FROM ctlohang WHERE MSP = ?";

        try (Connection con = JDBCUtil.getConnection();  // Kết nối đến cơ sở dữ liệu
             PreparedStatement pst = con.prepareStatement(sql)) {  // Tạo PreparedStatement
            
            // Thiết lập tham số truy vấn
            pst.setInt(1, maSP);

            // Thực hiện truy vấn
            ResultSet rs = pst.executeQuery();
            
            // Xử lý kết quả truy vấn
            if (rs.next()) {
                soLuongLoHang = rs.getInt("SoLuongLoHang");  // Lấy tổng số lượng lô hàng
            }
        } catch (SQLException e) {
            e.printStackTrace();  // In lỗi nếu có
        }

        return soLuongLoHang;  // Trả về tổng số lượng lô hàng
    }
}
