package DAO;

import DTO.ChiTietLoHangDTO;
import DTO.ChiTietPhieuDTO;
import DTO.KhuVucSach1DTO;
import DTO.TaiKhoanDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.JDBCUtil;
import java.beans.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class KhuVucSach1DAO implements DAOinterface<KhuVucSach1DTO> {

    

    private final  ChiTietLoHangDAO chitietlohang = new ChiTietLoHangDAO();
    ArrayList<ChiTietLoHangDTO> chitiet = chitietlohang.selectAll();
    
    
 public static ArrayList<ChiTietPhieuDTO> selectCTP(int maphieu) {
        return ChiTietLoHangDAO.selectAll1(Integer.toString(maphieu));
    }
 
  public static int insert1(KhuVucSach1DTO t) {
        int result = 0;
        try (Connection con = JDBCUtil.getConnection()) {
            String sql = "INSERT INTO lohang (MLH, Ngay, TT, TongSoSp, TongTien) VALUES (?, ?, 1, ?, ?)";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, t.getMLH());
                pst.setTimestamp(2, t.getNgay());
                pst.setInt(3, t.getTongSoSp());
                pst.setDouble(4, t.getTongTien());
                
                result = pst.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhuVucSach1DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
 
    
   public String[] getArrMLH() {
    int size = chitiet.size();
    System.out.println("Size of chitiet: " + size); // In kích thước của danh sách chitiet

    String[] result = new String[size];
    for (int i = 0; i < size; i++) {
        result[i] = chitiet.get(i).getMLH();
    }

     for (ChiTietLoHangDTO item : chitiet) {
        System.out.println("MLH: " + item.getMLH() + ", MSP: " + item.getMSP());
    }
    return result;
}
    
    @Override
  public  int insert(KhuVucSach1DTO t) {
   int result = 0;
        try (Connection con = JDBCUtil.getConnection()) {
            String sql = "INSERT INTO lohang (MLH, Ngay, TT, TongSoSp, TongTien) VALUES (?, ?, 1, ?, ?)";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, t.getMLH());
                pst.setTimestamp(2, t.getNgay());
                pst.setInt(3, t.getTongSoSp());
                pst.setDouble(4, t.getTongTien());
                
                result = pst.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhuVucSach1DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
}

 @Override
    public int update(KhuVucSach1DTO t) {
        int result = 0;
        try (Connection con = JDBCUtil.getConnection()) {
            String sql = "UPDATE lohang SET Ngay = ?, TT = ?, TongSoSp = ?, TongTien = ? WHERE MLH = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setTimestamp(1, t.getNgay());
                pst.setInt(2, t.getTT());
                pst.setInt(3, t.getTongSoSp());
                pst.setDouble(4, t.getTongTien());
                pst.setString(5, t.getMLH());
                
                result = pst.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhuVucSach1DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE lohang SET TT = 0 WHERE  MLH = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(KhuVucSachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }


    
    
    public ArrayList<KhuVucSach1DTO> getAll() {
        ArrayList<KhuVucSach1DTO> result = new ArrayList<>();
        try (Connection con = JDBCUtil.getConnection()) {
            String sql = "SELECT MLH, Ngay, TT, TongSoSp, TongTien FROM lohang "; // lấy tất cả các lô hàng                                                                  // không phân biệt TT
            try (PreparedStatement pst = con.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    KhuVucSach1DTO kv = new KhuVucSach1DTO(
                        rs.getString("MLH"),
                        rs.getTimestamp("Ngay"),
                        rs.getInt("TT"),
                        rs.getInt("TongSoSp"),
                        rs.getDouble("TongTien")
                    );
                    result.add(kv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
public String[] getAll1() {
    ArrayList<String> resultList = new ArrayList<>();
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    try {
        // Kết nối đến cơ sở dữ liệu
        con = JDBCUtil.getConnection();
        
        // Kiểm tra kết nối
        if (con == null) {
            System.out.println("Failed to make connection!");
            return new String[0];
        }

        // Truy vấn SQL để lấy tất cả các lô hàng có TT = 1
        String sql = "SELECT MLH FROM lohang WHERE TT = 1";
        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        
        // Xử lý kết quả truy vấn
        while (rs.next()) {
            String MLH = rs.getString("MLH"); // Lấy dữ liệu kiểu String
            
            // Thêm mã lô hàng vào danh sách kết quả
            resultList.add(MLH);
        }
        
        // Kiểm tra kết quả
        if (resultList.isEmpty()) {
            System.out.println("No records found for TT = 1.");
        }
        
    } catch (Exception e) {
        e.printStackTrace(); // In ra lỗi để tiện theo dõi
    } 
    
    // Chuyển đổi từ ArrayList sang mảng chuỗi
    return resultList.toArray(new String[0]);
}




   @Override
    public KhuVucSach1DTO selectById(String t) {
        KhuVucSach1DTO result = null;
        try (Connection con = JDBCUtil.getConnection()) {
            String sql = "SELECT MLH, Ngay, TT, TongSoSp, TongTien FROM lohang WHERE MLH = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, t);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        result = new KhuVucSach1DTO(
                            rs.getString("MLH"),
                            rs.getTimestamp("Ngay"),
                            rs.getInt("TT"),
                            rs.getInt("TongSoSp"),
                            rs.getDouble("TongTien")
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlycuahangsach' AND   TABLE_NAME   = 'lohang'";
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
 public boolean doesNameExist(String tenkhuvuc) {
        // Replace with actual database query to check for existence
        String query = "SELECT COUNT(*) FROM lohang WHERE TEN = ?";
        try ( 
                Connection con = (Connection) JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, tenkhuvuc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<KhuVucSach1DTO> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   public static int findMaxMLH() {
    // Replace with actual database query to find the maximum value of MLH
    String query = "SELECT MAX(MLH) FROM lohang";
    int maxMLH =-1 ;  // Khởi tạo với giá trị nhỏ nhất có thể

    try (Connection con = JDBCUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            // Lấy giá trị MLH lớn nhất từ kết quả truy vấn
            maxMLH = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return maxMLH;
}

  public ArrayList<KhuVucSach1DTO> search(String keyword, String type) {
    ArrayList<KhuVucSach1DTO> results = new ArrayList<>();
   
    switch (type) {
//        case "Tất cả":
//            ArrayList<KhuVucSach1DTO> allResults = findAll(keyword);
//            for (KhuVucSach1DTO kv : allResults) {
//                // Thay vì gọi convertToDTO, tạo DTO trực tiếp ở đây
//                results.add(new KhuVucSach1DTO(kv.getMLH(), kv.getNgay(),1) );
//            }
//            break;
        case "Mã lô hàng":
            ArrayList<KhuVucSach1DTO> maLoHangResults = findByMaLoHang(keyword);
            for (KhuVucSach1DTO kv : maLoHangResults) {
                // Thay vì gọi convertToDTO, tạo DTO trực tiếp ở đây
                results.add(new KhuVucSach1DTO(kv.getMLH(), kv.getNgay() , 1 , kv.getTongSoSp(), kv.getTongTien()));
            }
            break;
        default:
            System.out.println("Loại tìm kiếm không hợp lệ.");
            break;
    }

    return results;
}

  public ArrayList<KhuVucSach1DTO> findByMaLoHang(String maLoHang) {
        ArrayList<KhuVucSach1DTO> results = new ArrayList<>();
        String query = "SELECT MLH, Ngay, TT, TongSoSp, TongTien FROM lohang WHERE MLH LIKE ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, "%" + maLoHang + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    KhuVucSach1DTO kv = new KhuVucSach1DTO(
                        rs.getString("MLH"),
                        rs.getTimestamp("Ngay"),
                        rs.getInt("TT"),
                        rs.getInt("TongSoSp"),
                        rs.getDouble("TongTien")
                    );
                    results.add(kv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
public  boolean updateLot(String lotCode, int additionalQuantity, int additionalPrice) {
    String query = "UPDATE lohang SET TongSoSp = TongSoSp + ?, TongTIEN = TongTIEN + ? WHERE MLH = ?";
    
    System.out.println("hello" + lotCode );
     System.out.println("hello" + additionalQuantity );
      System.out.println("hello" + additionalPrice );
    
    try (Connection con = JDBCUtil.getConnection();
         PreparedStatement pst = con.prepareStatement(query)) {
     
        pst.setInt(1, additionalQuantity);
        pst.setInt(2, additionalPrice);
        pst.setString(3,lotCode);

        int rowsUpdated = pst.executeUpdate();
        
        // Check if the update was successful
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


}

