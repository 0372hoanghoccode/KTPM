package BUS;

import DAO.ChiTietLoHangDAO;
import DAO.ChiTietPhieuNhapDAO;
import DAO.PhieuNhapDAO;
import DTO.ChiTietLoHangDTO;
import DTO.ChiTietPhieuDTO;
import DTO.ChiTietPhieuNhapDTO;
import DTO.SanPhamDTO;
import DTO.PhieuNhapDTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class PhieuNhapBUS {

    public final PhieuNhapDAO phieunhapDAO = new PhieuNhapDAO();
    public final ChiTietPhieuNhapDAO ctPhieuNhapDAO = new ChiTietPhieuNhapDAO();

    NhanVienBUS nvBUS = new NhanVienBUS();

    ArrayList<PhieuNhapDTO> listPhieuNhap;

    public PhieuNhapBUS() {
    }

    public ArrayList<PhieuNhapDTO> getAll() {
        this.listPhieuNhap = phieunhapDAO.selectAll();
        return this.listPhieuNhap;
    }

    public ArrayList<PhieuNhapDTO> getAllList() {
        return this.listPhieuNhap;
    }
    
    
      public static int getProductQuantityInLot(String lotCode, int productCode) {
        return ChiTietLoHangDAO.getProductQuantityInLot(lotCode, productCode);
    }
    
     public static boolean checkProductInLot(String lotCode, int productCode) {
        return ChiTietLoHangDAO.checkProductInLot(lotCode, productCode);
    }
     
     public boolean updateProductInLot(ChiTietLoHangDTO productDetail) {
    return ChiTietLoHangDAO.updateProductInLot(productDetail);
}
     
     public boolean addProductToLot(ChiTietLoHangDTO productDetail) {
    return ChiTietLoHangDAO.addProductToLot(productDetail);
}


    public ArrayList<SanPhamDTO> convertHashMapToArray(HashMap<Integer, ArrayList<SanPhamDTO>> chitietsanpham) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        for (ArrayList<SanPhamDTO> ctsp : chitietsanpham.values()) {
            result.addAll(ctsp);
        }
        return result;
    }

    public ArrayList<ChiTietPhieuNhapDTO> getChiTietPhieu(int maphieunhap) {
        return ctPhieuNhapDAO.selectAll(Integer.toString(maphieunhap));
    }

    public ArrayList<ChiTietPhieuDTO> getChiTietPhieu_Type(int maphieunhap) {
        ArrayList<ChiTietPhieuNhapDTO> arr = ctPhieuNhapDAO.selectAll(Integer.toString(maphieunhap));
        ArrayList<ChiTietPhieuDTO> result = new ArrayList<>();
        for (ChiTietPhieuDTO i : arr) {
            result.add(i);
        }
        return result;
    }

    public int getMPMAX() {
        ArrayList<PhieuNhapDTO> listPhieuNhap = phieunhapDAO.selectAll();
        int s = 1;
        for (PhieuNhapDTO i : listPhieuNhap) {
            if(i.getMP() > s) s = i.getMP();
        }
        return s;
    }

    public boolean add(PhieuNhapDTO phieu, ArrayList<ChiTietPhieuNhapDTO> ctPhieu, HashMap<Integer, ArrayList<SanPhamDTO>> chitietsanpham) {
        boolean check = phieunhapDAO.insert(phieu) != 0;
        if (check) {
            check = ctPhieuNhapDAO.insert(ctPhieu) != 0;
        }
        return check;
    }

public ChiTietPhieuNhapDTO findCT(ArrayList<ChiTietPhieuNhapDTO> ctphieu, int masp, int mlh) {
    // Duyệt qua từng đối tượng trong danh sách chi tiết phiếu nhập
    for (ChiTietPhieuNhapDTO item : ctphieu) {
        // Kiểm tra nếu mã sản phẩm và mã lô đều khớp
        if (item.getMSP() == masp && item.getMLH() == mlh) {
            return item; // Trả về đối tượng tìm thấy
        }
    }
    return null; // Nếu không tìm thấy, trả về null
}


    public long getTIEN(ArrayList<ChiTietPhieuNhapDTO> ctphieu) {
        long result = 0;
        for (ChiTietPhieuNhapDTO item : ctphieu) {
            result += item.getTIEN() * item.getSL();
        }
        return result;
    }

    public ArrayList<PhieuNhapDTO> fillerPhieuNhap(int type, String input, int manv, Date time_s, Date time_e, String price_minnn, String price_maxxx) {
        Long price_min = !price_minnn.equals("") ? Long.valueOf(price_minnn) : 0L;
        Long price_max = !price_maxxx.equals("") ? Long.valueOf(price_maxxx) : Long.MAX_VALUE;
        Timestamp time_start = new Timestamp(time_s.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time_e.getTime());
        // Đặt giá trị cho giờ, phút, giây và mili giây của Calendar
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        Timestamp time_end = new Timestamp(calendar.getTimeInMillis());
        ArrayList<PhieuNhapDTO> result = new ArrayList<>();
        for (PhieuNhapDTO phieuNhap : getAllList()) {
            boolean match = false;
            switch (type) {
                case 0 -> { 
                    if (Integer.toString(phieuNhap.getMP()).contains(input)
                            || nvBUS.getNameById(phieuNhap.getMNV()).toLowerCase().contains(input)) {
                        match = true;
                    }
                }
                case 1 -> {
                    if (Integer.toString(phieuNhap.getMP()).contains(input)) {
                        match = true;
                    }
                }
                
                case 2 -> {
                    if (nvBUS.getNameById(phieuNhap.getMNV()).toLowerCase().contains(input)) {
                        match = true;
                    }
                }
            }

            if (match
                    && (manv == 0 || phieuNhap.getMNV() == manv) 
                    && (phieuNhap.getTG().compareTo(time_start) >= 0)
                    && (phieuNhap.getTG().compareTo(time_end) <= 0)
                    && phieuNhap.getTIEN() >= price_min
                    && phieuNhap.getTIEN() <= price_max) {
                result.add(phieuNhap);
            }
        }

        return result;
    }

    public boolean checkSLPn(int maphieu) {
        return phieunhapDAO.checkSLPn(maphieu);
    }

//    public int cancelPhieuNhap(int maphieu) {
//        return phieunhapDAO.cancelPhieuNhap(maphieu);
//    }

}
