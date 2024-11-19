package DTO;

// import java.util.Objects;

public class ChiTietPhieuDTO {
    private int MP;
    private int MSP;
    private int SL;
    private int TIEN;
    private int giaGiam ;
    private int giaThanhToan ; 
    private int MKM ; 
    public ChiTietPhieuDTO() {
    }

    public ChiTietPhieuDTO(int MP, int MSP, int SL, int TIEN) {
        this.MP = MP;
        this.MSP = MSP;
        this.SL = SL;
        this.TIEN = TIEN;
    }
       public ChiTietPhieuDTO(int MP, int MSP, int SL, int TIEN , int giagiam , int giathanhtoan , int MKM) {
        this.MP = MP;
        this.MSP = MSP;
        this.SL = SL;
        this.TIEN = TIEN;
        this.giaGiam = giagiam ;
        this.giaThanhToan = giathanhtoan ; 
        this.MKM = MKM ; 
    }
       
          public int getMKM () {
        return MKM ;
        
    }

    public void setKMK(int MP) {
        this.MKM = MP;
    }
       
     public int getGiaGiam () {
        return giaGiam ;
        
    }

    public void setGiaGiam(int MP) {
        this.giaGiam = MP;
    }
    public int getGiaThanhToan () {
        return giaThanhToan  ;
        
    }

    public void setGiaThanhToan(int MP) {
        this.giaThanhToan = MP;
    }

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public int getMSP() {
        return MSP;
    }

    public void setMSP(int MSP) {
        this.MSP = MSP;
    }

    public int getSL() {
        return SL;
    }

    public void setSL(int SL) {
        this.SL = SL;
    }

    public int getTIEN() {
        return TIEN;
    }

    public void setTIEN(int TIEN) {
        this.TIEN = TIEN;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.MP;
        hash = 19 * hash + this.MSP;
        hash = 19 * hash + this.SL;
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
        final ChiTietPhieuDTO other = (ChiTietPhieuDTO) obj;
        if (this.MP != other.MP) {
            return false;
        }
        if (this.MSP != other.MSP) {
            return false;
        }
        if (this.SL != other.SL) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuDTO{" + "MP=" + MP + ", MSP=" + MSP + ", SL=" + SL + ", TIEN=" + TIEN +'}';
    }

    
}
