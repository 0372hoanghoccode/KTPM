package DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class GioHangDTO {
    private int MKH;
    private long TIEN;
    private Timestamp TG;
    private int TT;

    public GioHangDTO
    ( int MKH,long TIEN, Timestamp TG, int TT) {
        this.MKH = MKH;
        this.TIEN = TIEN;
        this.TG = TG;
        this.TT = TT;
    }

    public GioHangDTO
    (){}


    public int getMKH() {
        return MKH;
    }

    public void setMKH(int mKH) {
        MKH = mKH;
    }

    public long getTIEN() {
        return TIEN;
    }

    public void setTIEN(long tIEN) {
        TIEN = tIEN;
    }

    public Timestamp getTG() {
        return TG;
    }

    public void setTG(Timestamp tG) {
        TG = tG;
    }

    public int getTT() {
        return TT;
    }

    public void setTT(int tT) {
        TT = tT;
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
        final GioHangDTO  other = (GioHangDTO) obj;
        
        if (this.MKH != other.MKH) {
            return false;
        }
        if (this.TIEN != other.TIEN) {
            return false;
        }
        if (this.TT != other.TT) {
            return false;
        }
        return Objects.equals(this.TG, other.TG);
    }

    @Override
    public String toString() {
        return "GioHangDTO [MKH=" + MKH + ", TIEN=" + TIEN + ", TG=" + TG + ", TT=" + TT + "]";
    }

}
