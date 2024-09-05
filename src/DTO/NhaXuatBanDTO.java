package DTO;

import java.util.Objects;

public class NhaXuatBanDTO {
    private int MNXB;
    private String TEN;
    private String DIACHI;
    private String EMAIL;
    private String SDT;

    public NhaXuatBanDTO() {
    }

    public NhaXuatBanDTO(int MNXB, String TEN, String DIACHI, String EMAIL, String SDT) {
        this.MNXB = MNXB;
        this.TEN = TEN;
        this.DIACHI = DIACHI;
        this.EMAIL = EMAIL;
        this.SDT = SDT;
    }

    public int getManxb() {
        return MNXB;
    }

    public void setMxb(int MNXB) {
        this.MNXB = MNXB;
    }

    public String getTennxb() {
        return TEN;
    }

    public void setTennxb(String TEN) {
        this.TEN = TEN;
    }

    public String getDiachi() {
        return DIACHI;
    }

    public void setDiachi(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public String getEmail() {
        return EMAIL;
    }

    public void setEmail(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getSdt() {
        return SDT;
    }

    public void setSdt(String SDT) {
        this.SDT = SDT;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.MNXB;
        hash = 67 * hash + Objects.hashCode(this.TEN);
        hash = 67 * hash + Objects.hashCode(this.DIACHI);
        hash = 67 * hash + Objects.hashCode(this.EMAIL);
        hash = 67 * hash + Objects.hashCode(this.SDT);
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
        final NhaXuatBanDTO other = (NhaXuatBanDTO) obj;
        if (this.getManxb() != other.getManxb()) {
            return false;
        }
        if (!Objects.equals(this.getTennxb(), other.getTennxb())) {
            return false;
        }
        if (!Objects.equals(this.getDiachi(), other.getDiachi())) {
            return false;
        }
        if (!Objects.equals(this.getEmail(), other.getEmail())) {
            return false;
        }
        return !Objects.equals(this.getSdt(), other.getSdt());
    }

    @Override
    public String toString() {
        return "NhaCungCap{" + "Ma nha xuat ban = " + MNXB + ", Ten nha xuat ban = " + TEN + ", Dia chi = " + DIACHI + ", Email = " + EMAIL + ", So dien thoai = " + SDT + '}';
    }
    
}
