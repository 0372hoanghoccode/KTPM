package DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class KhuVucSach1DTO {

    private String MLH;    // Mã lô hàng
    private Timestamp ngay; // Ngày được thay đổi thành Timestamp
    private int TT;        // Trạng thái

    // Constructor mặc định
    public KhuVucSach1DTO() {
    }

    // Constructor với tất cả các tham số
    public KhuVucSach1DTO(String MLH, Timestamp ngay, int TT) {
        this.MLH = MLH;
        this.ngay = ngay;
        this.TT = TT;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.MLH);
        hash = 97 * hash + Objects.hashCode(this.ngay);
        hash = 97 * hash + this.TT;
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
        if (!Objects.equals(this.MLH, other.MLH)) {
            return false;
        }
        return Objects.equals(this.ngay, other.ngay);
    }

    @Override
    public String toString() {
        return "KhuVucSach1DTO{" +
                "MLH='" + MLH + '\'' +
                ", ngay=" + ngay +
                ", TT=" + TT +
                '}';
    }
}
