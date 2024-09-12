package DTO;


// import DTO.ChiTietPhieuDTO;

public class ChiTietPhieuNhapDTO extends ChiTietPhieuDTO{
    private int MLH;

    public ChiTietPhieuNhapDTO(int TIENNHAP, int MLH) {
        this.MLH = MLH;
    }

    public ChiTietPhieuNhapDTO(int MP, int MSP, int SL, int TIENNHAP, int MLH) {
        super(MP, MSP, SL, TIENNHAP);
        this.MLH = MLH;
    }

    public int getMLH() {
        return MLH;
    }

    public void setMLH(int MLH) {
        this.MLH = MLH;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.MLH;
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
        final ChiTietPhieuNhapDTO other = (ChiTietPhieuNhapDTO) obj;
        return this.MLH == other.MLH;
    }
} 
