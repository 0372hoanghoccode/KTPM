package DTO;

import java.util.Objects;

public class SanPhamDTO {

    private int MSP;
    private String TEN;
    private String HINHANH;
    private String DANHMUC;
    private int NAMXB;
    private int MNXB;
    private String TENTG;
    private int MKVS;
    private int TIENX;
    private int SL;

    // Constructor mặc định
    public SanPhamDTO() {
    }

    // Constructor với tất cả các tham số
    public SanPhamDTO(int MSP, String TEN, String HINHANH, String DANHMUC, int NAMXB, int MNXB, String TENTG, int MKVS, int TIENX, int SL) {
        this.MSP = MSP;
        this.TEN = TEN;
        this.HINHANH = HINHANH;
        this.DANHMUC = DANHMUC;
        this.NAMXB = NAMXB;
        this.MNXB = MNXB;
        this.TENTG = TENTG;
        this.MKVS = MKVS;
        this.TIENX = TIENX;
        this.SL = SL;
    }

    // Getter và Setter cho MSP
    public int getMSP() {
        return MSP;
    }

    public void setMSP(int MSP) {
        this.MSP = MSP;
    }

    // Getter và Setter cho TEN
    public String getTEN() {
        return TEN;
    }

    public void setTEN(String TEN) {
        this.TEN = TEN;
    }

    // Getter và Setter cho HINHANH
    public String getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(String HINHANH) {
        this.HINHANH = HINHANH;
    }

    // Getter và Setter cho DANHMUC
    public String getDANHMUC() {
        return DANHMUC;
    }

    public void setDANHMUC(String DANHMUC) {
        this.DANHMUC = DANHMUC;
    }

    // Getter và Setter cho NAMXB
    public int getNAMXB() {
        return NAMXB;
    }

    public void setNAMXB(int NAMXB) {
        this.NAMXB = NAMXB;
    }

    // Getter và Setter cho MNXB
    public int getMNXB() {
        return MNXB;
    }

    public void setMNXB(int MNXB) {
        this.MNXB = MNXB;
    }

    // Getter và Setter cho TENTG
    public String getTENTG() {
        return TENTG;
    }

    public void setTENTG(String TENTG) {
        this.TENTG = TENTG;
    }

    // Getter và Setter cho MKVS
    public int getMKVS() {
        return MKVS;
    }

    public void setMKVS(int MKVS) {
        this.MKVS = MKVS;
    }

    // Getter và Setter cho TIENX
    public int getTIENX() {
        return TIENX;
    }

    public void setTIENX(int TIENX) {
        this.TIENX = TIENX;
    }

    // Getter và Setter cho SL
    public int getSL() {
        return SL;
    }

    public void setSL(int SL) {
        this.SL = SL;
    }

    @Override
    public String toString() {
        return "SanPhamDTO{" +
                "MSP=" + MSP +
                ", TEN='" + TEN + '\'' +
                ", HINHANH='" + HINHANH + '\'' +
                ", DANHMUC='" + DANHMUC + '\'' +
                ", NAMXB=" + NAMXB +
                ", MNXB=" + MNXB +
                ", TENTG='" + TENTG + '\'' +
                ", MKVS=" + MKVS +
                ", TIENX=" + TIENX +
                ", SL=" + SL +
                '}';
    }
}
