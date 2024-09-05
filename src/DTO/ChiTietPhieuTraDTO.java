package DTO;



public class ChiTietPhieuTraDTO extends ChiTietPhieuDTO{
    private String LYDO;

    public ChiTietPhieuTraDTO(String LYDO) {
        this.LYDO = LYDO;
    }

    public ChiTietPhieuTraDTO(int MP, int MSP, int SL, int TIENTRA, String LYDO) {
        super(MP, MSP, SL, TIENTRA);
        this.LYDO = LYDO;
    }

    public String getLYDO() {
        return LYDO;
    }

    public void setLYDO(String HINHTHUC) {
        this.LYDO = HINHTHUC;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((LYDO == null) ? 0 : LYDO.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChiTietPhieuTraDTO other = (ChiTietPhieuTraDTO) obj;
        if (LYDO == null) {
            if (other.LYDO != null)
                return false;
        } else if (!LYDO.equals(other.LYDO))
            return false;
        return true;
    }

    

    
} 
