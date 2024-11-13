package DTO.ThongKe;

public class ThongKeSanPhamDTO {
    private int maSP;       // Product ID
    private String tenSP;   // Product Name
    private int maLoHang;   // Batch ID
    private int MPX;        // Invoice ID (previously maHoaDon)
    private int MPN;        // Entry Receipt ID (previously maPhieuNhap)
    private double tongTienNhap;  // Total Entry Price
    private double tongTienXuat;  // Total Exit Price

    // Getter and Setter for maSP
    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    // Getter and Setter for tenSP
    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    // Getter and Setter for maLoHang
    public int getMaLoHang() {
        return maLoHang;
    }

    public void setMaLoHang(int maLoHang) {
        this.maLoHang = maLoHang;
    }

    // Getter and Setter for MPX (Invoice ID)
    public int getMPX() {
        return MPX;
    }

    public void setMPX(int MPX) {
        this.MPX = MPX;
    }

    // Getter and Setter for MPN (Entry Receipt ID)
    public int getMPN() {
        return MPN;
    }

    public void setMPN(int MPN) {
        this.MPN = MPN;
    }

    // Getter and Setter for tongTienNhap
    public double getTongTienNhap() {
        return tongTienNhap;
    }

    public void setTongTienNhap(double tongTienNhap) {
        this.tongTienNhap = tongTienNhap;
    }

    // Getter and Setter for tongTienXuat
    public double getTongTienXuat() {
        return tongTienXuat;
    }

    public void setTongTienXuat(double tongTienXuat) {
        this.tongTienXuat = tongTienXuat;
    }
}
