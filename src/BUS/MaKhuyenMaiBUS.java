package BUS;

import DAO.MaKhuyenMaiDAO;
import DAO.ChiTietMaKhuyenMaiDAO;
import DTO.MaKhuyenMaiDTO;
import DTO.ChiTietMaKhuyenMaiDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
public class MaKhuyenMaiBUS {

    private final MaKhuyenMaiDAO mkmDAO = new MaKhuyenMaiDAO();
    private final ChiTietMaKhuyenMaiDAO ctmkmDAO = new ChiTietMaKhuyenMaiDAO();
    public ArrayList<MaKhuyenMaiDTO> listMKM = new ArrayList<>();
    public ArrayList<ChiTietMaKhuyenMaiDTO> listctMKM = new ArrayList<>();

    public MaKhuyenMaiBUS() {
        listMKM = mkmDAO.selectAll();
    }

    public ArrayList<MaKhuyenMaiDTO> getAll() {
        listMKM = mkmDAO.selectAll();
        return this.listMKM;
    }

    public ArrayList<ChiTietMaKhuyenMaiDTO> getAllct() {
        return this.listctMKM;
    } 

    public MaKhuyenMaiDTO getByIndex(int index) {
        return this.listMKM.get(index);
    }

    public int getIndexByMKM(String makhachhang) {
        int i = 0;
        int vitri = -1;
        while (i < this.listMKM.size() && vitri == -1) {
            if (listMKM.get(i).getMKM() == makhachhang) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public ArrayList<ChiTietMaKhuyenMaiDTO> getChiTietMKM(String mkm) {
        ArrayList<ChiTietMaKhuyenMaiDTO> arr = ctmkmDAO.selectAll(mkm);
        return arr;
    }

    public Boolean add(MaKhuyenMaiDTO kh) {
        boolean check = mkmDAO.insert(kh) != 0;
        if (check) {
            this.listMKM.add(kh);
        }
        return check;
    }

    public Boolean checkTT(String kh) {
        for(MaKhuyenMaiDTO i : listMKM) if(i.getMKM().equals(kh)) return false;
        return true;
    }

    public Boolean delete(MaKhuyenMaiDTO kh) {
        boolean check = mkmDAO.delete(kh.getMKM()) != 0;
        if (check) {
            this.listMKM.remove(getIndexByMKM(kh.getMKM()));
        }
        return check;
    }

    public Boolean update(MaKhuyenMaiDTO kh) {
        boolean check = mkmDAO.update(kh) != 0;
        if (check) {
            this.listMKM.set(getIndexByMKM(kh.getMKM()), kh);
        }
        return check;
    }

    public ArrayList<MaKhuyenMaiDTO> search(String text) {
        ArrayList<MaKhuyenMaiDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        for (MaKhuyenMaiDTO i : this.listMKM) {
            if (i.getMKM().toLowerCase().contains(text)) {
                result.add(i);
            }
        }
        return result;
    }

    public MaKhuyenMaiDTO selectMkm(String makh) {
        return mkmDAO.selectById(makh);
    }

    public ChiTietMaKhuyenMaiDTO findCT(ArrayList<ChiTietMaKhuyenMaiDTO> ctphieu, int masp) {
        ChiTietMaKhuyenMaiDTO p = null;
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

    public ArrayList<ChiTietMaKhuyenMaiDTO> Getctmkm(int masp) {
        ArrayList<ChiTietMaKhuyenMaiDTO> p = new ArrayList<ChiTietMaKhuyenMaiDTO>();
        listctMKM = ctmkmDAO.selectAll();
        for(ChiTietMaKhuyenMaiDTO i : listctMKM) if(i.getMSP() == masp && !validateSelectDate(i)) p.add(i);
        return p;
    } 

    public boolean validateSelectDate(DTO.ChiTietMaKhuyenMaiDTO tmp) {
        MaKhuyenMaiDTO a = selectMkm(tmp.getMKM());
        Date time_start = a.getTGBD();
        Date time_end = a.getTGKT();
        Date current_date = new Date();
        if (time_start != null && time_start.after(current_date)) {
            return false;
        }
        if (time_end != null && time_end.after(current_date)) {
            return false;
        }
        if (time_start != null && time_end != null && time_start.after(time_end)) {
            return false;
        }
        return true;
    }
    
public ArrayList<MaKhuyenMaiDTO> fillerPhieuNhap(Date time_s, Date time_e) {
    // Convert input dates to Timestamps
    Timestamp time_start = new Timestamp(time_s.getTime());
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(time_e);
    // Set time to the end of the day
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    
    Timestamp time_end = new Timestamp(calendar.getTimeInMillis());

    ArrayList<MaKhuyenMaiDTO> result = new ArrayList<>();
    
    for (MaKhuyenMaiDTO phieuNhap : getAll()) {
        Timestamp tgBD = phieuNhap.getTGBD();
        Timestamp tgKT = phieuNhap.getTGKT();
        
        // Check if the date range of `phieuNhap` overlaps with the search range
          if ((tgBD.compareTo(time_end) <= 0 && tgKT.compareTo(time_start) >= 0) ||
            (tgBD.before(time_start) && tgKT.after(time_end))) {
            result.add(phieuNhap);
        }
    }

    return result;
}

    
    public boolean add(MaKhuyenMaiDTO phieu, ArrayList<ChiTietMaKhuyenMaiDTO> ctPhieu) {
        boolean check = mkmDAO.insert(phieu) != 0;
        if (check) {
            check = ctmkmDAO.insert(ctPhieu) != 0;
        }
        return check;
    }

    public int cancelMKM(String makm) {
        return mkmDAO.cancelMKM(makm);
    }
}
