/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.PreparedStatement;
import DTO.ChiTietLoHangDTO;
import DTO.ChiTietPhieuDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
/**
 *
 * @author ASUS Vivobook
 */

public class ChiTietLoHangDAO implements ChiTietInterface<ChiTietLoHangDTO>{
      public static ChiTietPhieuNhapDAO getInstance() {
        return new ChiTietPhieuNhapDAO();
}

    public ChiTietLoHangDAO() {
       
    }

    
    

 public ArrayList<ChiTietLoHangDTO> getByMaLoHang(String maLoHang) {
        ArrayList<ChiTietLoHangDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ctlohang WHERE MLH = ?";

        try (Connection con = JDBCUtil.getConnection(); 
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            // Thiết lập tham số cho truy vấn
            pstmt.setString(1, maLoHang);
            
            // Thực hiện truy vấn
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng DTO từ dữ liệu kết quả
                ChiTietLoHangDTO dto = new ChiTietLoHangDTO(
                    rs.getString("MLH"),
                    rs.getInt("MSP"),
                    rs.getInt("soLuong"),
                    rs.getInt("giaNhap")
                );
                // Thêm đối tượng vào danh sách
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list; // Trả về danh sách chi tiết lô hàng
    }
    
    
public static   boolean addProductToLot(ChiTietLoHangDTO productDetail) {
        String sql = "INSERT INTO ChiTietLoHang (maLoHang, maSanPham, soLuong, giaNhap) VALUES (?, ?, ?, ?)";

        try 
            (
                   Connection con = (Connection) JDBCUtil.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Thiết lập các tham số cho truy vấn
            pstmt.setString(1, productDetail.getMLH()); // Mã lô hàng
            pstmt.setInt(2, productDetail.getMSP()); // Mã sản phẩm
            pstmt.setInt(3, productDetail.getSoLuong()); // Số lượng sản phẩm
            pstmt.setDouble(4, productDetail.getGiaNhap()); // Giá nhập sản phẩm

            // Thực hiện truy vấn và kiểm tra số hàng bị ảnh hưởng
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu ít nhất 1 hàng bị ảnh hưởng
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }


  public static int getProductQuantityInLot(String lotCode, int productCode) {
        String sql = "SELECT soLuong FROM ctlohang WHERE MLH = ? AND MSP = ?";
        int quantity = 0;

        try (Connection con = JDBCUtil.getConnection(); 
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            // Thiết lập các tham số cho truy vấn
            pstmt.setString(1, lotCode); // Mã lô hàng
            pstmt.setInt(2, productCode); // Mã sản phẩm
            
            // Thực hiện truy vấn
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Lấy số lượng từ kết quả truy vấn
                quantity = rs.getInt("soLuong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantity; // Trả về số lượng sản phẩm trong lô
    }


 public static boolean updateProductInLot(ChiTietLoHangDTO productDetail) {
        // Câu lệnh SQL để cập nhật thông tin sản phẩm
        String sql = "UPDATE ctlohang SET soluong = ?, giaNhap = ? WHERE MLH = ? AND MSP = ?";

        try (
                Connection con = (Connection) JDBCUtil.getConnection();PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Thiết lập các tham số cho truy vấn
            pstmt.setInt(1, productDetail.getSoLuong()); // Cập nhật số lượng sản phẩm
            pstmt.setDouble(2, productDetail.getGiaNhap()); // Cập nhật giá nhập sản phẩm
            pstmt.setString(3, productDetail.getMLH()); // Mã lô hàng
            pstmt.setInt(4, productDetail.getMSP()); // Mã sản phẩm

            // Thực hiện truy vấn và kiểm tra số hàng bị ảnh hưởng
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu ít nhất 1 hàng bị ảnh hưởng
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
    @Override
    public int insert(ArrayList<ChiTietLoHangDTO> t) {
        
        String sql = "INSERT INTO ctlohang (maLoHang, maSanPham, soLuong, giaNhap) VALUES (?, ?, ?, ?)";

        try (
               Connection con = (Connection) JDBCUtil.getConnection();PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Thiết lập các tham số cho truy vấn
            pstmt.setString(1, t.get(0)); // Mã lô hàng
            pstmt.setString(2, t.getMaSanPham()); // Mã sản phẩm
            pstmt.setInt(3, t.getSoLuong()); // Số lượng sản phẩm
            pstmt.setDouble(4, t.getGiaNhap()); // Giá nhập sản phẩm

            // Thực hiện truy vấn và kiểm tra số hàng bị ảnh hưởng
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu ít nhất 1 hàng bị ảnh hưởng
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
    

    @Override
    public int delete(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(ArrayList<ChiTietLoHangDTO> t, String pk) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  public ArrayList<ChiTietLoHangDTO> selectAll() {
        ArrayList<ChiTietLoHangDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            // Kết nối đến cơ sở dữ liệu
            con = JDBCUtil.getConnection();
            
            // Câu truy vấn SQL để lấy tất cả các chi tiết lô hàng
            String sql = "SELECT MLH, MSP, GIANHAP,  SoLuong FROM ctlohang";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            // Xử lý kết quả truy vấn
            while (rs.next()) {
                String MLH = rs.getString("MLH"); // Lấy dữ liệu kiểu String
                int MSP = rs.getInt("MSP"); // Lấy dữ liệu kiểu int
                int GIANHAP = rs.getInt("GIANHAP"); // Lấy dữ liệu kiểu int
             //     int GIABAN = rs.getInt("GiABAN"); // Lấy dữ liệu kiểu int
                int SL = rs.getInt("SoLuong"); // Lấy dữ liệu kiểu int
                
                // Tạo đối tượng ChiTietLoHangDTO và thêm vào danh sách kết quả
                ChiTietLoHangDTO dto = new ChiTietLoHangDTO(MLH, MSP, GIANHAP, SL);
                result.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi để tiện theo dõi
        } 
        
        return result;
    }
    
    public ArrayList<String> findMLHByMSP(int msp) {
        ArrayList<String> mlhList = new ArrayList<>();
        String sql = "SELECT MLH FROM ctlohang WHERE MSP = ?";

        try ( Connection con = (Connection) JDBCUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // Set giá trị cho placeholder
            pstmt.setInt(1, msp);

            // Thực thi truy vấn
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Lấy giá trị của cột MLH và thêm vào danh sách
                    String mlh = rs.getString("MLH");
                    mlhList.add(mlh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mlhList;
    }

  
     
    public ArrayList<ChiTietPhieuDTO> selectAll1(String t) {
            ArrayList<ChiTietPhieuDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM ctlohang WHERE MLH = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int maphieu = rs.getInt("MLH");
                int MSP = rs.getInt("MSP");
                int SL = rs.getInt("GiaNhap");
                int tienxuat = rs.getInt("SL");
                ChiTietPhieuDTO ctphieu = new ChiTietPhieuDTO(maphieu, MSP, SL, tienxuat);
                result.add(ctphieu);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public ArrayList<ChiTietLoHangDTO> selectAll(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
      public static boolean checkProductInLot(String lotCode, int productCode) {
        String query = "SELECT COUNT(*) FROM ctlohang WHERE MLH = ? AND MSP = ?";
        try (
                Connection con = (Connection) JDBCUtil.getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, lotCode);
            preparedStatement.setInt(2, productCode);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Nếu số lượng sản phẩm trong kết quả lớn hơn 0 thì sản phẩm đã tồn tại
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
    
    
    
}
  