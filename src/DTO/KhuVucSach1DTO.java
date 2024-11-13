package DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class KhuVucSach1DTO {

    private String MLH;    // Mã lô hàng
    private Timestamp ngay; // Ngày được thay đổi thành Timestamp
    private int TT;        // Trạng thái
    private int TongSoSp;  // Tổng số sản phẩm
    private double TongTien; // Tổng tiền

    // Constructor mặc định
    public KhuVucSach1DTO() {
    }

    // Constructor với tất cả các tham số
    public KhuVucSach1DTO(String MLH, Timestamp ngay, int TT, int TongSoSp, double TongTien) {
        this.MLH = MLH;
        this.ngay = ngay;
        this.TT = TT;
        this.TongSoSp = TongSoSp;
        this.TongTien = TongTien;
    }

    // Getter và Setter cho MLH
    public String getMLH() {
        return MLH;
    }

    public void setMLH(String MLH) {
        this.MLH = MLH;
    }

    // Getter và Setter cho ngay
    public Timestamp getNgay() {
        return ngay;
    }

    public void setNgay(Timestamp ngay) {
        this.ngay = ngay;
    }

    // Getter và Setter cho TT
    public int getTT() {
        return TT;
    }

    public void setTT(int TT) {
        this.TT = TT;
    }

    // Getter và Setter cho TongSoSp
    public int getTongSoSp() {
        return TongSoSp;
    }

    public void setTongSoSp(int TongSoSp) {
        this.TongSoSp = TongSoSp;
    }

    // Getter và Setter cho TongTien
    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double TongTien) {
        this.TongTien = TongTien;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.MLH);
        hash = 97 * hash + Objects.hashCode(this.ngay);
        hash = 97 * hash + this.TT;
        hash = 97 * hash + this.TongSoSp;
        hash = 97 * hash + Double.hashCode(this.TongTien);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KhuVucSach1DTO other = (KhuVucSach1DTO) obj;
        if (this.TT != other.TT) {
            return false;
        }
        if (this.TongSoSp != other.TongSoSp) {
            return false;
        }
        if (Double.compare(this.TongTien, other.TongTien) != 0) {
            return false;
        }
        if (!Objects.equals(this.MLH, other.MLH)) {
            return false;
        }
        return Objects.equals(this.ngay, other.ngay);
    }
}
