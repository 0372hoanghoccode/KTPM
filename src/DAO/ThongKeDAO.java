package DAO;

import DTO.ThongKe.ThongKeDoanhThuDTO;
import DTO.ThongKe.ThongKeKhachHangDTO;
import DTO.ThongKe.ThongKeTheoThangDTO;
import DTO.ThongKe.ThongKeTonKhoDTO;
import DTO.ThongKe.ThongKeTungNgayTrongThangDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ThongKeDAO {

    public static ThongKeDAO getInstance() {
        return new ThongKeDAO();
    }

    public static ArrayList<ThongKeTonKhoDTO> getThongKeTonKho(String text, Date timeStart, Date timeEnd) {
        ArrayList<ThongKeTonKhoDTO> result = new ArrayList<ThongKeTonKhoDTO>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeEnd.getTime());
        // Đặt giá trị cho giờ, phút, giây và mili giây của Calendar
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = """
                            WITH nhap AS (
                            SELECT MSP, SUM(SL) AS sl_nhap
                            FROM CTPHIEUNHAP
                            JOIN PHIEUNHAP ON PHIEUNHAP.MPN = CTPHIEUNHAP.MPN
                            WHERE TG BETWEEN ? AND ?
                            GROUP BY MSP
                            ),
                            xuat AS (
                            SELECT MSP, SUM(SL) AS sl_xuat
                            FROM CTPHIEUXUAT
                            JOIN PHIEUXUAT ON PHIEUXUAT.MPX = CTPHIEUXUAT.MPX
                            WHERE TG BETWEEN ? AND ?
                            GROUP BY MSP
                            ),
                            nhap_dau AS (
                            SELECT CTPHIEUNHAP.MSP, SUM(CTPHIEUNHAP.SL) AS sl_nhap_dau
                            FROM PHIEUNHAP
                            JOIN CTPHIEUNHAP ON PHIEUNHAP.MPN = CTPHIEUNHAP.MPN
                            WHERE PHIEUNHAP.TG < ?
                            GROUP BY CTPHIEUNHAP.MSP
                            ),
                            xuat_dau AS (
                            SELECT CTPHIEUXUAT.MSP, SUM(CTPHIEUXUAT.SL) AS sl_xuat_dau
                            FROM PHIEUXUAT
                            JOIN CTPHIEUXUAT ON PHIEUXUAT.MPX = CTPHIEUXUAT.MPX
                            WHERE PHIEUXUAT.TG < ?
                            GROUP BY CTPHIEUXUAT.MSP
                            ),
                            dau_ky AS (
                            SELECT
                                SANPHAM.MSP,
                                COALESCE(nhap_dau.sl_nhap_dau, 0) - COALESCE(xuat_dau.sl_xuat_dau, 0) AS SLdauky
                            FROM SANPHAM
                            LEFT JOIN nhap_dau ON SANPHAM.MSP = nhap_dau.MSP
                            LEFT JOIN xuat_dau ON SANPHAM.MSP = xuat_dau.MSP
                            ),
                            temp_table AS (
                            SELECT SANPHAM.MSP, SANPHAM.TEN, dau_ky.SLdauky, COALESCE(nhap.sl_nhap, 0) AS SLnhap, COALESCE(xuat.sl_xuat, 0)  AS SLxuat, (dau_ky.SLdauky + COALESCE(nhap.sl_nhap, 0) - COALESCE(xuat.sl_xuat, 0)) AS SLcuoiky
                            FROM dau_ky
                            LEFT JOIN nhap ON dau_ky.MSP = nhap.MSP
                            LEFT JOIN xuat ON dau_ky.MSP = xuat.MSP
                            JOIN SANPHAM ON dau_ky.MSP = SANPHAM.MSP
                            )
                            SELECT * FROM temp_table
                            WHERE TEN LIKE ? OR MSP LIKE ?
                            ORDER BY MSP;""";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setTimestamp(1, new Timestamp(timeStart.getTime()));
            pst.setTimestamp(2, new Timestamp(calendar.getTimeInMillis()));
            pst.setTimestamp(3, new Timestamp(timeStart.getTime()));
            pst.setTimestamp(4, new Timestamp(calendar.getTimeInMillis()));
            pst.setTimestamp(5, new Timestamp(timeStart.getTime()));
            pst.setTimestamp(6, new Timestamp(timeStart.getTime()));
            pst.setString(7, "%" + text + "%");
            pst.setString(8, "%" + text + "%");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int MSP = rs.getInt("MSP");
                String TEN = rs.getString("TEN");
                int SLdauky = rs.getInt("SLdauky");
                int SLnhap = rs.getInt("SLnhap");
                int SLxuat = rs.getInt("SLxuat");
                int SLcuoiky = rs.getInt("SLcuoiky");
                ThongKeTonKhoDTO p = new ThongKeTonKhoDTO(MSP, TEN, SLdauky, SLnhap, SLxuat, SLcuoiky);
                result.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

public ArrayList<ThongKeDoanhThuDTO> getDoanhThuTheoTungNam(int year_start, int year_end) {
    // SQL để tính tổng chi phí theo năm
    String sqlChiPhi = """
                WITH RECURSIVE years(year) AS (
                    SELECT ?
                    UNION ALL
                    SELECT year + 1
                    FROM years
                    WHERE year < ?
                )
                SELECT 
                    years.year AS nam,
                    COALESCE(SUM(PHIEUNHAP.TIEN), 0) AS chiphi
                FROM years
                LEFT JOIN PHIEUNHAP ON YEAR(PHIEUNHAP.TG) = years.year
                GROUP BY years.year
                ORDER BY years.year;
                """;

    // SQL để tính tổng doanh thu theo năm
    String sqlDoanhThu = """
                WITH RECURSIVE years(year) AS (
                    SELECT ?
                    UNION ALL
                    SELECT year + 1
                    FROM years
                    WHERE year < ?
                )
                SELECT 
                    years.year AS nam,
                    COALESCE(SUM(PHIEUXUAT.TIEN), 0) AS doanhthu
                FROM years
                LEFT JOIN PHIEUXUAT ON YEAR(PHIEUXUAT.TG) = years.year
                          WHERE PHIEUXUAT.TT = 1
                GROUP BY years.year
                ORDER BY years.year;
                """;

    // Tạo danh sách để lưu kết quả cuối cùng
    ArrayList<ThongKeDoanhThuDTO> result = new ArrayList<>();

    try (Connection con = JDBCUtil.getConnection();
         PreparedStatement pstChiPhi = con.prepareStatement(sqlChiPhi);
         PreparedStatement pstDoanhThu = con.prepareStatement(sqlDoanhThu)) {

        // Thiết lập giá trị cho các truy vấn
        pstChiPhi.setInt(1, year_start);
        pstChiPhi.setInt(2, year_end);
        pstDoanhThu.setInt(1, year_start);
        pstDoanhThu.setInt(2, year_end);

        // Kết quả truy vấn chi phí
        ResultSet rsChiPhi = pstChiPhi.executeQuery();

        // Kết quả truy vấn doanh thu
        ResultSet rsDoanhThu = pstDoanhThu.executeQuery();

        // Tạo map để lưu doanh thu theo năm
        Map<Integer, Long> doanhThuMap = new HashMap<>();

        // Đọc doanh thu từ ResultSet và lưu vào map
        while (rsDoanhThu.next()) {
            int nam = rsDoanhThu.getInt("nam");
            long doanhthu = rsDoanhThu.getLong("doanhthu");
            doanhThuMap.put(nam, doanhthu);
        }

        // Đọc chi phí từ ResultSet và kết hợp với doanh thu từ map
        while (rsChiPhi.next()) {
            int nam = rsChiPhi.getInt("nam");
            long chiphi = rsChiPhi.getLong("chiphi");
            
            // Tìm doanh thu tương ứng cho năm từ map
            long doanhthu = doanhThuMap.getOrDefault(nam, 0L);
            
            long loinhuan = doanhthu - chiphi;
            ThongKeDoanhThuDTO dto = new ThongKeDoanhThuDTO(nam, chiphi, doanhthu, loinhuan);
            result.add(dto);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return result;
}





    public static ArrayList<ThongKeKhachHangDTO> getThongKeKhachHang(String text, Date timeStart, Date timeEnd) {
        ArrayList<ThongKeKhachHangDTO> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeEnd.getTime());
        // Đặt giá trị cho giờ, phút, giây và mili giây của Calendar
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = """
                            WITH kh AS (
                            SELECT KHACHHANG.MKH, KHACHHANG.HOTEN , COUNT(PHIEUXUAT.MPX) AS tongsophieu, SUM(PHIEUXUAT.TIEN) AS tongsotien
                            FROM KHACHHANG
                            JOIN PHIEUXUAT ON KHACHHANG.MKH = PHIEUXUAT.MKH
                            WHERE PHIEUXUAT.TG BETWEEN ? AND ? 
                            GROUP BY KHACHHANG.MKH, KHACHHANG.HOTEN)
                            SELECT MKH,HOTEN,COALESCE(kh.tongsophieu, 0) AS SL ,COALESCE(kh.tongsotien, 0) AS total 
                            FROM kh WHERE HOTEN LIKE ? OR MKH LIKE ?""";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setTimestamp(1, new Timestamp(timeStart.getTime()));
            pst.setTimestamp(2, new Timestamp(calendar.getTimeInMillis()));
            pst.setString(3, "%" + text + "%");
            pst.setString(4, "%" + text + "%");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int MKH = rs.getInt("MKH");
                String tenkh = rs.getString("HOTEN");
                int SL = rs.getInt("SL");
                long TIEN = rs.getInt("total");
                ThongKeKhachHangDTO x = new ThongKeKhachHangDTO(MKH, tenkh, SL, TIEN);
                result.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


  public ArrayList<ThongKeTheoThangDTO> getThongKeTheoThang(int year) {
    ArrayList<ThongKeTheoThangDTO> result = new ArrayList<>();

    // SQL để tính tổng chi phí theo tháng
    String sqlChiPhi = """
                SELECT MONTH(TG) AS thang, 
                       COALESCE(SUM(TIEN), 0) AS chiphi
                FROM PHIEUNHAP
                WHERE YEAR(TG) = ?
                GROUP BY MONTH(TG)
                ORDER BY MONTH(TG);
                """;

    // SQL để tính tổng doanh thu theo tháng
    String sqlDoanhThu = """
                SELECT MONTH(TG) AS thang, 
                       COALESCE(SUM(TIEN), 0) AS doanhthu
                FROM PHIEUXUAT
                WHERE YEAR(TG) = ? AND TT = 1
                GROUP BY MONTH(TG)
                ORDER BY MONTH(TG);
                """;

    // Tạo danh sách để lưu kết quả chi phí và doanh thu theo tháng
    ArrayList<ThongKeTheoThangDTO> chiPhiList = new ArrayList<>(Collections.nCopies(12, new ThongKeTheoThangDTO(0, 0, 0, 0)));
    
    try (Connection con = JDBCUtil.getConnection();
         PreparedStatement pstChiPhi = con.prepareStatement(sqlChiPhi);
         PreparedStatement pstDoanhThu = con.prepareStatement(sqlDoanhThu)) {

        // Thiết lập năm cho các truy vấn
        pstChiPhi.setInt(1, year);
        pstDoanhThu.setInt(1, year);

        // Kết quả truy vấn chi phí
        ResultSet rsChiPhi = pstChiPhi.executeQuery();
        // Kết quả truy vấn doanh thu
        ResultSet rsDoanhThu = pstDoanhThu.executeQuery();

        // Cập nhật danh sách chi phí
        while (rsChiPhi.next()) {
            int thang = rsChiPhi.getInt("thang") - 1; // Đánh chỉ số từ 0 đến 11
            int chiphi = rsChiPhi.getInt("chiphi");
            chiPhiList.set(thang, new ThongKeTheoThangDTO(thang + 1, chiphi, 0, -chiphi));
        }

        // Cập nhật danh sách doanh thu và lãi lỗ
        while (rsDoanhThu.next()) {
            int thang = rsDoanhThu.getInt("thang") - 1; // Đánh chỉ số từ 0 đến 11
            int doanhthu = rsDoanhThu.getInt("doanhthu");
            ThongKeTheoThangDTO dto = chiPhiList.get(thang);
            int chiphi = dto.getChiphi();
            int loinhuan = doanhthu - chiphi;
            chiPhiList.set(thang, new ThongKeTheoThangDTO(thang + 1, chiphi, doanhthu, loinhuan));
        }
        
        result.addAll(chiPhiList);

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return result;
}



    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKeTungNgayTrongThang(int thang, int nam) {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = new ArrayList<>();
        try {
            String ngayString = nam + "-" + thang + "-" + "01";
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT \n"
                    + "  dates.date AS ngay, \n"
                    + "  COALESCE(SUM(CTPHIEUNHAP.TIENNHAP), 0) AS chiphi, \n"
                    + "  COALESCE(SUM(CTPHIEUXUAT.TIENXUAT), 0) AS doanhthu\n"
                    + "FROM (\n"
                    + "  SELECT DATE('" + ngayString + "') + INTERVAL c.number DAY AS date\n"
                    + "  FROM (\n"
                    + "    SELECT 0 AS number\n"
                    + "    UNION ALL SELECT 1\n"
                    + "    UNION ALL SELECT 2\n"
                    + "    UNION ALL SELECT 3\n"
                    + "    UNION ALL SELECT 4\n"
                    + "    UNION ALL SELECT 5\n"
                    + "    UNION ALL SELECT 6\n"
                    + "    UNION ALL SELECT 7\n"
                    + "    UNION ALL SELECT 8\n"
                    + "    UNION ALL SELECT 9\n"
                    + "    UNION ALL SELECT 10\n"
                    + "    UNION ALL SELECT 11\n"
                    + "    UNION ALL SELECT 12\n"
                    + "    UNION ALL SELECT 13\n"
                    + "    UNION ALL SELECT 14\n"
                    + "    UNION ALL SELECT 15\n"
                    + "    UNION ALL SELECT 16\n"
                    + "    UNION ALL SELECT 17\n"
                    + "    UNION ALL SELECT 18\n"
                    + "    UNION ALL SELECT 19\n"
                    + "    UNION ALL SELECT 20\n"
                    + "    UNION ALL SELECT 21\n"
                    + "    UNION ALL SELECT 22\n"
                    + "    UNION ALL SELECT 23\n"
                    + "    UNION ALL SELECT 24\n"
                    + "    UNION ALL SELECT 25\n"
                    + "    UNION ALL SELECT 26\n"
                    + "    UNION ALL SELECT 27\n"
                    + "    UNION ALL SELECT 28\n"
                    + "    UNION ALL SELECT 29\n"
                    + "    UNION ALL SELECT 30\n"
                    + "  ) AS c\n"
                    + "  WHERE DATE('" + ngayString + "') + INTERVAL c.number DAY <= LAST_DAY('" + ngayString + "')\n"
                    + ") AS dates\n"
                    + "LEFT JOIN PHIEUXUAT ON DATE(PHIEUXUAT.TG) = dates.date\n"
                    + "LEFT JOIN CTPHIEUXUAT ON PHIEUXUAT.MPX = CTPHIEUXUAT.MPX\n"
                    + "LEFT JOIN SANPHAM ON SANPHAM.MSP = CTPHIEUXUAT.MSP\n"
                    + "LEFT JOIN CTPHIEUNHAP ON SANPHAM.MSP = CTPHIEUNHAP.MSP\n"
                    + "GROUP BY dates.date\n"
                    + "ORDER BY dates.date;";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Date ngay = rs.getDate("ngay");
                int chiphi = rs.getInt("chiphi");
                int doanhthu = rs.getInt("doanhthu");
                int loinhuan = doanhthu - chiphi;
                ThongKeTungNgayTrongThangDTO tn = new ThongKeTungNgayTrongThangDTO(ngay, chiphi, doanhthu, loinhuan);
                result.add(tn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKe7NgayGanNhat() {
    ArrayList<ThongKeTungNgayTrongThangDTO> result = new ArrayList<>();

    // SQL để tính tổng tiền của phiếu nhập
    String sqlChiPhi = """
                WITH RECURSIVE dates(date) AS (
                    SELECT DATE_SUB(CURDATE(), INTERVAL 6 DAY)
                    UNION ALL
                    SELECT DATE_ADD(date, INTERVAL 1 DAY)
                    FROM dates
                    WHERE date < CURDATE()
                )
                SELECT 
                    dates.date AS ngay,
                    COALESCE(SUM(PHIEUNHAP.TIEN), 0) AS chiphi
                FROM dates
                LEFT JOIN PHIEUNHAP ON DATE(PHIEUNHAP.TG) = dates.date
                GROUP BY dates.date
                ORDER BY dates.date;
                """;

    // SQL để tính tổng tiền của phiếu xuất với TT = 1
    String sqlDoanhThu = """
                WITH RECURSIVE dates(date) AS (
                    SELECT DATE_SUB(CURDATE(), INTERVAL 6 DAY)
                    UNION ALL
                    SELECT DATE_ADD(date, INTERVAL 1 DAY)
                    FROM dates
                    WHERE date < CURDATE()
                )
                SELECT 
                    dates.date AS ngay,
                    COALESCE(SUM(PHIEUXUAT.TIEN), 0) AS doanhthu
                FROM dates
                LEFT JOIN PHIEUXUAT ON DATE(PHIEUXUAT.TG) = dates.date
                WHERE PHIEUXUAT.TT = 1
                GROUP BY dates.date
                ORDER BY dates.date;
                """;

    try (Connection con = JDBCUtil.getConnection();
         PreparedStatement pstChiPhi = con.prepareStatement(sqlChiPhi);
         ResultSet rsChiPhi = pstChiPhi.executeQuery();
         
         PreparedStatement pstDoanhThu = con.prepareStatement(sqlDoanhThu);
         ResultSet rsDoanhThu = pstDoanhThu.executeQuery()) {

        // Tạo map để lưu chi phí theo ngày
        Map<Date, Integer> chiPhiMap = new HashMap<>();

        // Lưu kết quả chi phí vào map
        while (rsChiPhi.next()) {
            Date ngay = rsChiPhi.getDate("ngay");
            int chiphi = rsChiPhi.getInt("chiphi");
            chiPhiMap.put(ngay, chiphi);
        }

        // Tạo map để lưu doanh thu theo ngày
        Map<Date, Integer> doanhThuMap = new HashMap<>();

        // Lưu kết quả doanh thu vào map
        while (rsDoanhThu.next()) {
            Date ngay = rsDoanhThu.getDate("ngay");
            int doanhthu = rsDoanhThu.getInt("doanhthu");
            doanhThuMap.put(ngay, doanhthu);
        }

        // Kết hợp dữ liệu từ hai map để tạo kết quả
        Set<Date> allDates = new HashSet<>();
        allDates.addAll(chiPhiMap.keySet());
        allDates.addAll(doanhThuMap.keySet());

        for (Date ngay : allDates) {
            int chiphi = chiPhiMap.getOrDefault(ngay, 0);
            int doanhthu = doanhThuMap.getOrDefault(ngay, 0);
            int loinhuan = doanhthu - chiphi;

            result.add(new ThongKeTungNgayTrongThangDTO(ngay, chiphi, doanhthu, loinhuan));
        }

        // Sắp xếp kết quả theo ngày
        result.sort(Comparator.comparing(ThongKeTungNgayTrongThangDTO::getNgay));

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return result;
}







    public ArrayList<ThongKeTungNgayTrongThangDTO> getThongKeTuNgayDenNgay(String star, String end) {
        ArrayList<ThongKeTungNgayTrongThangDTO> result = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String setStar = "SET @start_date = '" + star + "'";
            String setEnd = "SET @end_date = '" + end + "'  ;";
            String sqlSelect = "SELECT \n"
                    + "  dates.date AS ngay, \n"
                    + "  COALESCE(SUM(CTPHIEUNHAP.TIENNHAP), 0) AS chiphi, \n"
                    + "  COALESCE(SUM(CTPHIEUXUAT.TIENXUAT), 0) AS doanhthu\n"
                    + "FROM (\n"
                    + "  SELECT DATE_ADD(@start_date, INTERVAL c.number DAY) AS date\n"
                    + "  FROM (\n"
                    + "    SELECT a.number + b.number * 31 AS number\n"
                    + "    FROM (\n"
                    + "      SELECT 0 AS number\n"
                    + "      UNION ALL SELECT 1\n"
                    + "      UNION ALL SELECT 2\n"
                    + "      UNION ALL SELECT 3\n"
                    + "      UNION ALL SELECT 4\n"
                    + "      UNION ALL SELECT 5\n"
                    + "      UNION ALL SELECT 6\n"
                    + "      UNION ALL SELECT 7\n"
                    + "      UNION ALL SELECT 8\n"
                    + "      UNION ALL SELECT 9\n"
                    + "      UNION ALL SELECT 10\n"
                    + "      UNION ALL SELECT 11\n"
                    + "      UNION ALL SELECT 12\n"
                    + "      UNION ALL SELECT 13\n"
                    + "      UNION ALL SELECT 14\n"
                    + "      UNION ALL SELECT 15\n"
                    + "      UNION ALL SELECT 16\n"
                    + "      UNION ALL SELECT 17\n"
                    + "      UNION ALL SELECT 18\n"
                    + "      UNION ALL SELECT 19\n"
                    + "      UNION ALL SELECT 20\n"
                    + "      UNION ALL SELECT 21\n"
                    + "      UNION ALL SELECT 22\n"
                    + "      UNION ALL SELECT 23\n"
                    + "      UNION ALL SELECT 24\n"
                    + "      UNION ALL SELECT 25\n"
                    + "      UNION ALL SELECT 26\n"
                    + "      UNION ALL SELECT 27\n"
                    + "      UNION ALL SELECT 28\n"
                    + "      UNION ALL SELECT 29\n"
                    + "      UNION ALL SELECT 30\n"
                    + "    ) AS a\n"
                    + "    CROSS JOIN (\n"
                    + "      SELECT 0 AS number\n"
                    + "      UNION ALL SELECT 1\n"
                    + "      UNION ALL SELECT 2\n"
                    + "      UNION ALL SELECT 3\n"
                    + "      UNION ALL SELECT 4\n"
                    + "      UNION ALL SELECT 5\n"
                    + "      UNION ALL SELECT 6\n"
                    + "      UNION ALL SELECT 7\n"
                    + "      UNION ALL SELECT 8\n"
                    + "      UNION ALL SELECT 9\n"
                    + "      UNION ALL SELECT 10\n"
                    + "    ) AS b\n"
                    + "  ) AS c\n"
                    + "  WHERE DATE_ADD(@start_date, INTERVAL c.number DAY) <= @end_date\n"
                    + ") AS dates\n"
                    + "LEFT JOIN PHIEUXUAT ON DATE(PHIEUXUAT.TG) = dates.date\n"
                    + "LEFT JOIN CTPHIEUXUAT ON PHIEUXUAT.MPX = CTPHIEUXUAT.MPX\n"
                    + "LEFT JOIN SANPHAM ON SANPHAM.MSP = CTPHIEUXUAT.MSP\n"
                    + "LEFT JOIN CTPHIEUNHAP ON SANPHAM.MSP = CTPHIEUNHAP.MSP\n"
                    + "GROUP BY dates.date\n"
                    + "ORDER BY dates.date;";

            PreparedStatement pstStart = con.prepareStatement(setStar);
            PreparedStatement pstEnd = con.prepareStatement(setEnd);
            PreparedStatement pstSelect = con.prepareStatement(sqlSelect);

            pstStart.execute();
            pstEnd.execute();
            ResultSet rs = pstSelect.executeQuery();
            while (rs.next()) {
                Date ngay = rs.getDate("ngay");
                int chiphi = rs.getInt("chiphi");
                int doanhthu = rs.getInt("doanhthu");
                int loinhuan = doanhthu - chiphi;
                ThongKeTungNgayTrongThangDTO tn = new ThongKeTungNgayTrongThangDTO(ngay, chiphi, doanhthu, loinhuan);
                result.add(tn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
