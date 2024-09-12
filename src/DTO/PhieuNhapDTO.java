//package DTO;
//
//import java.sql.Timestamp;
//
//public class PhieuNhapDTO extends PhieuDTO{
//    private int MNCC;
//    public PhieuNhapDTO(int MNCC, long TIENN) {
//        this.MNCC = MNCC;
//    }
//
//    public PhieuNhapDTO(int MNCC, int MP, int MNV, Timestamp TG, long TIENN, int TT) {
//        super(MP, MNV, TG, TIENN, TT);
//        this.MNCC = MNCC;
//    }
//
//    public int getMNCC() {
//        return MNCC;
//    }
//
//    public void setMNCC(int MNCC) {
//        this.MNCC = MNCC;
//    }
//
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 67 * hash + this.MNCC;
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final PhieuNhapDTO other = (PhieuNhapDTO) obj;
//        return this.MNCC == other.MNCC;
//    }
//
//    @Override
//    public String toString() {
//        return "PhieuNhapDTO{" + "MNCC=" + MNCC +  '}'+super.toString();
//    }
//}

package DTO;

import java.sql.Timestamp;

public class PhieuNhapDTO extends PhieuDTO {
    // Đã loại bỏ thuộc tính MNCC

    // Constructor không còn chứa MNCC
    public PhieuNhapDTO(long TIENN) {
        super(); // Gọi constructor của lớp cha nếu cần
        // Các thuộc tính cần thiết khác sẽ được thêm vào đây nếu có
    }

    // Constructor khác không còn chứa MNCC
    public PhieuNhapDTO(int MP, int MNV, Timestamp TG, long TIENN, int TT) {
        super(MP, MNV, TG, TIENN, TT);
    }

    // Xóa các phương thức getMNCC và setMNCC
    // Không còn hashCode() và equals() dựa trên MNCC

    @Override
    public int hashCode() {
        // Có thể tùy chỉnh hashCode nếu cần dựa trên các thuộc tính còn lại
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Có thể bổ sung thêm logic so sánh dựa trên các thuộc tính khác
        return super.equals(obj);
    }

    @Override
    public String toString() {
        // Chỉ còn các thông tin cần thiết khác, MNCC đã bị loại bỏ
        return "PhieuNhapDTO{}" + super.toString();
    }
}
