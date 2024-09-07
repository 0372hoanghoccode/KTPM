package BUS;

import DAO.NhomQuyenDAO;
import DAO.TaiKhoanDAO;
import DAO.TaiKhoanKHDAO;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;
import java.util.ArrayList;

public class TaiKhoanBUS {
    private ArrayList<TaiKhoanDTO> listTaiKhoan;
    private ArrayList<TaiKhoanDTO> listTaikhoanKH;
    private NhomQuyenDAO nhomQuyenDAO = NhomQuyenDAO.getInstance();
    
    public TaiKhoanBUS(){
        this.listTaiKhoan  = TaiKhoanDAO.getInstance().selectAll();
    }
    
    public ArrayList<TaiKhoanDTO> getTaiKhoanAll(){
        this.listTaiKhoan  = TaiKhoanDAO.getInstance().selectAll();
        return listTaiKhoan;
    }

    public ArrayList<TaiKhoanDTO> getTaiKhoanAllKH(){
        this.listTaikhoanKH = TaiKhoanKHDAO.getInstance().selectAll();
        return listTaikhoanKH ;
    }
    
    public TaiKhoanDTO getTaiKhoan(int index){
        return listTaiKhoan.get(index);
    }
    public int getTaiKhoanByMaNV(int manv){
        int i = 0;
        int vitri = -1;
        while (i < this.listTaiKhoan.size() && vitri == -1) {
            if (listTaiKhoan.get(i).getMNV()== manv) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public int getTaiKhoanByMaKH(int manv){
        int i = 0;
        int vitri = -1;
        while (i < this.listTaikhoanKH.size() && vitri == -1) {
            if (listTaikhoanKH.get(i).getMNV()== manv) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }
    
    public NhomQuyenDTO getNhomQuyenDTO(int manhom){
        return nhomQuyenDAO.selectById(manhom+"");
    }
    
    public void addAcc(TaiKhoanDTO tk){
        TaiKhoanDAO.getInstance().insert(tk);
    }
    
    
    public void addAccKH(TaiKhoanDTO tk){
        TaiKhoanKHDAO.getInstance().insert(tk);
    }

    public void updateAcc(TaiKhoanDTO tk){
        TaiKhoanDAO.getInstance().update(tk);
    }

    public void updateAccKH(TaiKhoanDTO tk){
        TaiKhoanKHDAO.getInstance().update(tk);
    }

    public boolean checkTDN(String TDN){
        TaiKhoanDTO tk = TaiKhoanDAO.getInstance().selectByUser(TDN);
        if(tk != null) return false;
        tk = TaiKhoanKHDAO.getInstance().selectByUser(TDN);
        if(tk != null) return false;
        return true;
    }
    
    
    public boolean checkSDT(String sdt) {
    TaiKhoanDTO tk = TaiKhoanDAO.getInstance().selectBySDT(sdt);
    if (tk != null) return false; // Số điện thoại đã tồn tại trong TaiKhoanDAO
    tk = TaiKhoanKHDAO.getInstance().selectBySDT(sdt);
    if (tk != null) return false; // Số điện thoại đã tồn tại trong TaiKhoanKHDAO
    return true; // Số điện thoại không tồn tại trong cả hai cơ sở dữ liệu
}

    public boolean checkEmail(String email) {
    TaiKhoanDTO tk = TaiKhoanDAO.getInstance().selectByEmail(email);
    if (tk != null) return false; // Email đã tồn tại trong TaiKhoanDAO
    tk = TaiKhoanKHDAO.getInstance().selectByEmail(email);
    if (tk != null) return false; // Email đã tồn tại trong TaiKhoanKHDAO
    return true; // Email không tồn tại trong cả hai cơ sở dữ liệu
}

    
    public void deleteAcc(int manv){
        
    }
    public ArrayList<TaiKhoanDTO> search(String txt, String type) {
        ArrayList<TaiKhoanDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (TaiKhoanDTO i : listTaiKhoan) {
                    if (Integer.toString(i.getMNV()).contains(txt) || i.getTDN().contains(txt) ) {
                        result.add(i);
                    }
                }
            }
            case "Mã nhân viên" -> {
                for (TaiKhoanDTO i : listTaiKhoan) {
                    if (Integer.toString(i.getMNV()).contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Username" -> {
                for (TaiKhoanDTO i : listTaiKhoan) {
                    if (i.getTDN().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<TaiKhoanDTO> searchKH(String txt, String type) {
        ArrayList<TaiKhoanDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (TaiKhoanDTO i : listTaikhoanKH) {
                    if (Integer.toString(i.getMNV()).contains(txt) || i.getTDN().contains(txt) ) {
                        result.add(i);
                    }
                }
            }
            case "Mã nhân viên" -> {
                for (TaiKhoanDTO i : listTaikhoanKH) {
                    if (Integer.toString(i.getMNV()).contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Username" -> {
                for (TaiKhoanDTO i : listTaikhoanKH) {
                    if (i.getTDN().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
            }
        }
        return result;
    }

}
