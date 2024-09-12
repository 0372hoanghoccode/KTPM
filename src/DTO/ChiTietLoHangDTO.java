package DTO;

import java.util.Objects;

public class ChiTietLoHangDTO {

    private String MLH;
    private int MSP;
    private int GiaNhap;
    private int SoLuong;

    // Constructor mặc định
    public ChiTietLoHangDTO() {
    }

    // Constructor với tất cả các tham số
    public ChiTietLoHangDTO(String MLH, int MSP, int GiaNhap, int SoLuong) {
        this.MLH = MLH;
        this.MSP = MSP;
        this.GiaNhap = GiaNhap;
        this.SoLuong = SoLuong;
    }

    // Getter và Setter cho MLH
    public String getMLH() {
        return MLH;
    }

    public void setMLH(String MLH) {
        this.MLH = MLH;
    }

    // Getter và Setter cho MSP
    public int getMSP() {
        return MSP;
    }

    public void setMSP(int MSP) {
        this.MSP = MSP;
    }

    // Getter và Setter cho GiaNhap
    public int getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(int GiaNhap) {
        this.GiaNhap = GiaNhap;
    }

    // Getter và Setter cho SoLuong
    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    // Phương thức để tính giá bán
    public int getGiaBan() {
        return this.GiaNhap * 2; // Giá bán là giá nhập nhân 2
    }

    @Override
    public String toString() {
        return "ChiTietLoHangDTO{" +
                "MLH='" + MLH + '\'' +
                ", MSP=" + MSP +
                ", GiaNhap=" + GiaNhap +
                ", SoLuong=" + SoLuong +
                ", GiaBan=" + getGiaBan() + // Bao gồm giá bán trong toString
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(MLH, MSP, GiaNhap, SoLuong);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChiTietLoHangDTO other = (ChiTietLoHangDTO) obj;
        return MSP == other.MSP &&
               GiaNhap == other.GiaNhap &&
               SoLuong == other.SoLuong &&
               Objects.equals(MLH, other.MLH);
    }
}
