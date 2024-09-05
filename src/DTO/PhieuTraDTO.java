package DTO;

import java.sql.Timestamp;

public class PhieuTraDTO extends PhieuDTO{
    private int MKH;

    public PhieuTraDTO(int MKH) {
        this.MKH = MKH;
    }

    public PhieuTraDTO(int MKH, int MP, int MNV, Timestamp TG, long TIENX, int TT) {
        super(MP, MNV, TG, TIENX, TT);
        this.MKH = MKH;
    }

    public int getMKH() {
        return MKH;
    }

    public void setMKH(int MKH) {
        this.MKH = MKH;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.MKH;
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
        final PhieuTraDTO other = (PhieuTraDTO) obj;
        return this.MKH == other.MKH;
    }

    @Override
    public String toString() {
        return "PhieuTraDTO{" + "MKH=" + MKH + '}';
    }

    
}
