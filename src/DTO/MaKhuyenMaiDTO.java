package DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class MaKhuyenMaiDTO {
    private int MKM;
    private int MNV;
    private Timestamp TGBD;
    private Timestamp TGKT;
    private int TT ; 
    public MaKhuyenMaiDTO() {

    }

    public MaKhuyenMaiDTO(int mKM, int mNV, Timestamp tGBD, Timestamp tGKT , int Trangthai ) {
        MKM = mKM;
        MNV = mNV;
        TGBD = tGBD;
        TGKT = tGKT;
        TT = Trangthai ; 
    }
    public int getMKM() {
        return MKM;
    }
    public void setMKM(int mKM) {
        MKM = mKM;
    }
    public int getMNV() {
        return MNV;
    }
    public void setMNV(int mNV) {
        MNV = mNV;
    }
    
      public int getTT() {
        return TT;
    }
    public void setTT(int Trangthai) {
        TT = Trangthai;
    }
    public Timestamp getTGBD() {
        return TGBD;
    }
    public void setTGBD(Timestamp tGBD) {
        TGBD = tGBD;
    }
    public Timestamp getTGKT() {
        return TGKT;
    }
    public void setTGKT(Timestamp tGKT) {
        TGKT = tGKT;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.MKM);
        hash = 79 * hash + MNV;
        hash = 79 * hash + Objects.hashCode(this.TGBD);
        hash = 79 * hash + Objects.hashCode(this.TGKT);
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
        final MaKhuyenMaiDTO other = (MaKhuyenMaiDTO) obj;
        if (other.getMNV() != this.getMNV()) {
            return false;
        }
        if (!Objects.equals(other.getMKM(), this.getMKM())) {
            return false;
        }
        if (!Objects.equals(other.getTGBD(), this.getTGBD())) {
            return false;
        }
        if (!Objects.equals(other.getTGKT(),this.getTGKT())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ma Khuyen mai {" + "Ma khuyen mai = " + MKM + ", Ma nhan vien = " + MNV + ", Thoi gian bat dau = " + TGBD + ", Thoi gian ket thuc = " + TGKT + '}';
    }
}
