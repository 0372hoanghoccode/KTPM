
package DTO;

import java.sql.Timestamp;

public class PhieuNhapDTO extends PhieuDTO {

    public PhieuNhapDTO(long TIENN) {
        super(); 
    }

    public PhieuNhapDTO(int MP, int MNV, Timestamp TG, long TIENN, int TT ) {
        super(MP, MNV, TG, TIENN, TT);
    }


    @Override
    public int hashCode() {
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
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "PhieuNhapDTO{}" + super.toString();
    }
}
