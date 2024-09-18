package DTO;

import java.util.Objects;

public class ChiTietGioHangDTO {
    private int MKH;
    private int MSP;
    private int SL;
    private int TIENGIO;
    private int GIAGIAM;
    private int GIATHANHTOAN;
    private String MKM;

    public ChiTietGioHangDTO(int mkh, int msp, int sl, int tiengio, int giagiam, int giathanhtoan, String makm) {
        this.MKH = mkh;
        this.MSP = msp;
        this.SL = sl;
        this.TIENGIO = tiengio;
        this.GIAGIAM = giagiam;
        this.GIATHANHTOAN = giathanhtoan;
        this.MKM = makm;
    }

    // Getters
    public int getMKH() {
        return MKH;
    }

    public int getMSP() {
        return MSP;
    }

    public int getSL() {
        return SL;
    }

    public int getTIENGIO() {
        return TIENGIO;
    }

    public int getGIAGIAM() {
        return GIAGIAM;
    }

    public int getGIATHANHTOAN() {
        return GIATHANHTOAN;
    }

    public String getMKM() {
        return MKM;
    }

    // Setters
    public void setMKH(int mkh) {
        this.MKH = mkh;
    }

    public void setMSP(int msp) {
        this.MSP = msp;
    }

    public void setSL(int sl) {
        this.SL = sl;
    }

    public void setTIENGIO(int tiengio) {
        this.TIENGIO = tiengio;
    }

    public void setGIAGIAM(int giagiam) {
        this.GIAGIAM = giagiam;
    }

    public void setGIATHANHTOAN(int giathanhtoan) {
        this.GIATHANHTOAN = giathanhtoan;
    }

    public void setMKM(String makm) {
        this.MKM = makm;
    }
}
