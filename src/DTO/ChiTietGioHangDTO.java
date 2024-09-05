package DTO;

import java.util.Objects;

public class ChiTietGioHangDTO {
    int MKH;
    int MSP;
    String MKM;
    int SL;
    int TIENGIO;

    

    public ChiTietGioHangDTO(int mKH, int mSP, String mKM, int sL, int tIENGIO) {
        MKH = mKH;
        MSP = mSP;
        MKM = mKM;
        SL = sL;
        TIENGIO = tIENGIO;
    }

    public ChiTietGioHangDTO(){}

    public int getMKH() {
        return MKH;
    }

    public void setMKH(int mKH) {
        MKH = mKH;
    }

    public int getMSP() {
        return MSP;
    }

    public void setMSP(int mSP) {
        MSP = mSP;
    }

    public int getSL() {
        return SL;
    }

    public void setSL(int sL) {
        SL = sL;
    }

    public int getTIENGIO() {
        return TIENGIO;
    }

    public void setTIENGIO(int tIEN) {
        TIENGIO = tIEN;
    };

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
        final ChiTietGioHangDTO other = (ChiTietGioHangDTO) obj;
        
        if (this.MKH != other.MKH) {
            return false;
        }
        if (this.TIENGIO != other.TIENGIO) {
            return false;
        }
    
        return Objects.equals(this.SL, other.SL);
    }

    public String getMKM() {
        return MKM;
    }

    public void setMKM(String mKM) {
        MKM = mKM;
    }

    @Override
    public String toString() {
        return "ChiTietGioHangDTO [MKH=" + MKH + ", MSP=" + MSP + ", MKM=" + MKM + ", SL=" + SL + ", TIENGIO=" + TIENGIO
                + "]";
    }

}
