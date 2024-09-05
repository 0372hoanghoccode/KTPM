package BUS;

import DAO.ChiTietPhieuTraDAO;
import DAO.PhieuTraDAO;
import DTO.ChiTietPhieuDTO;
import DTO.ChiTietPhieuTraDTO;
import DTO.PhieuTraDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class PhieuTraBUS {

    private final PhieuTraDAO phieuTraDAO = PhieuTraDAO.getInstance();

    private final ChiTietPhieuTraDAO chiTietPhieuTraDAO = ChiTietPhieuTraDAO.getInstance();
    private ArrayList<PhieuTraDTO> listPhieuTra;

    NhanVienBUS nvBUS = new NhanVienBUS();
    KhachHangBUS khBUS = new KhachHangBUS();

    public PhieuTraBUS() {
        this.listPhieuTra = phieuTraDAO.selectAll();
    }

    public ArrayList<PhieuTraDTO> getAll() {
        this.listPhieuTra = phieuTraDAO.selectAll();
        return this.listPhieuTra;
    }

    public PhieuTraDTO getSelect(int index) {
        return listPhieuTra.get(index);
    }

    public void cancel(int px) {
        phieuTraDAO.cancel(px);
    }

    public void remove(int px) {
        listPhieuTra.remove(px);
    }

    public void insert(PhieuTraDTO px, ArrayList<ChiTietPhieuTraDTO> ct) {
        phieuTraDAO.insert(px); //ghi phieu xuat vao sql
        chiTietPhieuTraDAO.insert(ct); //goi updatesoluongTon cua sanphamDAO để chỉnh số lượng trong kho -> ghi váo sql
    }

    public ArrayList<ChiTietPhieuTraDTO> selectCTP(int maphieu) {
        return chiTietPhieuTraDAO.selectAll(Integer.toString(maphieu));
    }

    public int getMPMAX() {
        ArrayList<PhieuTraDTO> listPhieuTra = phieuTraDAO.selectAll();
        int s = 1;
        for (PhieuTraDTO i : listPhieuTra) {
            if(i.getMP() > s) s = i.getMP();
        }
        return s;
    }

    public ChiTietPhieuTraDTO findCT(ArrayList<ChiTietPhieuTraDTO> ctphieu, int masp) {
        ChiTietPhieuTraDTO p = null;
        int i = 0;
        while (i < ctphieu.size() && p == null) {
            if (ctphieu.get(i).getMSP() == masp) {
                p = ctphieu.get(i);
            } else {
                i++;
            }
        }
        return p;
    }

    public ArrayList<ChiTietPhieuDTO> getChiTietPhieu_Type(int maphieunhap) {
        ArrayList<ChiTietPhieuTraDTO> arr = chiTietPhieuTraDAO.selectAll(Integer.toString(maphieunhap));
        ArrayList<ChiTietPhieuDTO> result = new ArrayList<>();
        for (ChiTietPhieuDTO i : arr) {
            result.add(i);
        }
        return result;
    }

    public ArrayList<PhieuTraDTO> fillerPhieuTra(int type, String input, int makh, int manv, Date time_s, Date time_e, String price_minnn, String price_maxxx) {
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

        ArrayList<PhieuTraDTO> result = new ArrayList<>();
        for (PhieuTraDTO phieuXuat : getAll()) {
            boolean match = false;
            switch (type) {
                case 0 -> {
                    if (Integer.toString(phieuXuat.getMP()).contains(input)
                            || khBUS.getTenKhachHang(phieuXuat.getMKH()).toLowerCase().contains(input)
                            || nvBUS.getNameById(phieuXuat.getMNV()).toLowerCase().contains(input)) {
                        match = true;
                    }
                }
                case 1 -> {
                    if (Integer.toString(phieuXuat.getMP()).contains(input)) {
                        match = true;
                    }
                }
                case 2 -> {
                    if (khBUS.getTenKhachHang(phieuXuat.getMKH()).toLowerCase().contains(input)) {
                        match = true;
                    }
                }
                case 3 -> {
                    if (nvBUS.getNameById(phieuXuat.getMNV()).toLowerCase().contains(input)) {
                        match = true;
                    }
                }
            }

            if (match
                    && (manv == 0 || phieuXuat.getMNV() == manv) && (makh == 0 || phieuXuat.getMKH() == makh)
                    && (phieuXuat.getTG().compareTo(time_start) >= 0)
                    && (phieuXuat.getTG().compareTo(time_end) <= 0)
                    && phieuXuat.getTIEN() >= price_min
                    && phieuXuat.getTIEN() <= price_max) {
                result.add(phieuXuat);
            }
        }

        return result;
    }

    public boolean checkSLPt(int maphieu) {
        return phieuTraDAO.checkSLPt(maphieu);
    }

    public int cancelPhieuTra(int maphieu) {
        return phieuTraDAO.cancelPhieuTra(maphieu);
    }

    public long getTIEN(ArrayList<ChiTietPhieuTraDTO> ctphieu) {
        long result = 0;
        for (ChiTietPhieuTraDTO item : ctphieu) {
            result += item.getTIEN() * item.getSL();
        }
        return result;
    }

    public boolean add(PhieuTraDTO phieu, ArrayList<ChiTietPhieuTraDTO> ctPhieu) {
        boolean check = phieuTraDAO.insert(phieu) != 0;
        if (check) {
            check = chiTietPhieuTraDAO.insert(ctPhieu) != 0;
        }
        return check;
    }

    public String[] getArrMPX() {
        this.listPhieuTra = phieuTraDAO.selectAll();
        int size = listPhieuTra.size();
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = Integer.toString(listPhieuTra.get(i).getMP());
        }
        return result;
    }
}
