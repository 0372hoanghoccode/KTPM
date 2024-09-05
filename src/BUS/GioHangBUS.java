package BUS;

import DAO.ChiTietGioHangDAO;
import DAO.GioHangDAO;
import DTO.ChiTietGioHangDTO;
import DTO.SanPhamDTO;
import DTO.GioHangDTO;
import java.util.ArrayList;
import java.util.HashMap;


public class GioHangBUS {

    public final GioHangDAO giohangDAO = new GioHangDAO();
    public final ChiTietGioHangDAO ctGioHangDAO = new ChiTietGioHangDAO();

    NhanVienBUS nvBUS = new NhanVienBUS();

    ArrayList<GioHangDTO> listGioHang;
    ArrayList<ChiTietGioHangDTO> listctGioHang;

    public GioHangBUS() {
    }

    public ArrayList<GioHangDTO> getAll() {
        this.listGioHang = giohangDAO.selectAll();
        return this.listGioHang;
    }

    public ArrayList<ChiTietGioHangDTO> getAllct(int mkh) {
        this.listctGioHang = ctGioHangDAO.selectAll(mkh+"");
        return this.listctGioHang;
    }

    public ArrayList<SanPhamDTO> convertHashMapToArray(HashMap<Integer, ArrayList<SanPhamDTO>> chitietsanpham) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        for (ArrayList<SanPhamDTO> ctsp : chitietsanpham.values()) {
            result.addAll(ctsp);
        }
        return result;
    }

    public ArrayList<ChiTietGioHangDTO> getChiTietPhieu(int maphieunhap) {
        return ctGioHangDAO.selectAll(Integer.toString(maphieunhap));
    }

    public int getMPMAX() {
        ArrayList<GioHangDTO> listGioHang = giohangDAO.selectAll();
        int s = 1;
        for (GioHangDTO i : listGioHang) {
            if(i.getMKH() > s) s = i.getMKH();
        }
        return s;
    }

    public boolean add(GioHangDTO phieu, ArrayList<ChiTietGioHangDTO> ctPhieu, HashMap<Integer, ArrayList<SanPhamDTO>> chitietsanpham) {
        boolean check = giohangDAO.insert(phieu) != 0;
        if (check) {
            check = ctGioHangDAO.insert(ctPhieu) != 0;
        }
        return check;
    }

    public boolean add(GioHangDTO phieu) {
        this.listGioHang = giohangDAO.selectAll();
        for(GioHangDTO i : listGioHang) if(i.getMKH() == phieu.getMKH()) return false;
        boolean check = giohangDAO.insert(phieu) != 0;
        return check;
    }

    public ChiTietGioHangDTO checkTT(int mkh, int msp) {
        this.listctGioHang = ctGioHangDAO.selectAll(mkh+"");
        for (ChiTietGioHangDTO phieu : listctGioHang) if(phieu.getMSP() == msp) return phieu;
        return null;
    }

    public boolean addCT(ChiTietGioHangDTO ctPhieu) {
        boolean check;
        if(ctPhieu.getMKM().equals("")) check = ctGioHangDAO.insertNoMKM(ctPhieu) != 0;
        else check = ctGioHangDAO.insert(ctPhieu) != 0;
        return check;
    }

    public boolean deleteCT(ChiTietGioHangDTO ctPhieu) {
        boolean check = ctGioHangDAO.delete(ctPhieu) != 0;
        return check;
    }

    public boolean delete(int mkh) {
        boolean check = ctGioHangDAO.delete(mkh+"") != 0;
        return check;
    }

    public boolean updateCT(ChiTietGioHangDTO ctPhieu) {
        boolean check = ctGioHangDAO.update(ctPhieu, ctPhieu.getMKH() + "") != 0;
        return check;
    }

    public ChiTietGioHangDTO findCT(ArrayList<ChiTietGioHangDTO> ctphieu, int masp) {
        ChiTietGioHangDTO p = null;
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

    public long getTIEN(ArrayList<ChiTietGioHangDTO> ctphieu) {
        long result = 0;
        for (ChiTietGioHangDTO item : ctphieu) {
            result += item.getTIENGIO() * item.getSL();
        }
        return result;
    }

}
