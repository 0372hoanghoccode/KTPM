package DTO;



public class ChiTietPhieuXuatDTO extends ChiTietPhieuDTO{
    private int MKM;

    public ChiTietPhieuXuatDTO(int MKM) {
        this.MKM = MKM;
    }

//    public ChiTietPhieuXuatDTO(int MP, int MSP, int SL, int TIENXUAT, int MKM) {
//        super(MP, MSP, SL, TIENXUAT);
//        this.MKM = MKM;
//    }
     public ChiTietPhieuXuatDTO(int MP, int MSP, int SL, int TIENXUAT , int giaGiam , int giaThanhToan , int MKM) {
        super(MP, MSP, SL, TIENXUAT,giaGiam,giaThanhToan , MKM);
      
       
     }

//    public int getMKM() {
//        return MKM;
//    }
//
//    public void setMKM(int MKM) {
//        this.MKM = MKM;
//    }

   



} 
