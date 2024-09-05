package BUS;

import DAO.NhaXuatBanDAO;
import DTO.NhaXuatBanDTO;
import java.util.ArrayList;

public class NhaXuatBanBUS {

    private final NhaXuatBanDAO NxbDAO = new NhaXuatBanDAO();
    private ArrayList<NhaXuatBanDTO> listNxb = new ArrayList<>();

    public NhaXuatBanBUS() {
        this.listNxb = NxbDAO.selectAll();
    }

    public ArrayList<NhaXuatBanDTO> getAll() {
        return this.listNxb;
    }

    public NhaXuatBanDTO getByIndex(int index) {
        return this.listNxb.get(index);
    }

    public boolean add(NhaXuatBanDTO ncc) {
        boolean check = NxbDAO.insert(ncc) != 0;
        if (check) {
            this.listNxb.add(ncc);
        }
        return check;
    }

    public boolean delete(NhaXuatBanDTO ncc, int index) {
        boolean check = NxbDAO.delete(Integer.toString(ncc.getManxb())) != 0;
        if (check) {
            this.listNxb.remove(index);
        }
        return check;
    }

    public boolean update(NhaXuatBanDTO ncc) {
        boolean check = NxbDAO.update(ncc) != 0;
        if (check) {
            this.listNxb.set(getIndexByMaNXB(ncc.getManxb()), ncc);
        }
        return check;
    }

    public int getIndexByMaNXB(int mancc) {
        int i = 0;
        int vitri = -1;
        while (i < this.listNxb.size() && vitri == -1) {
            if (listNxb.get(i).getManxb() == mancc) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public ArrayList<NhaXuatBanDTO> search(String txt, String type) {
        ArrayList<NhaXuatBanDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (NhaXuatBanDTO i : listNxb) {
                    if (Integer.toString(i.getManxb()).contains(txt) || i.getTennxb().contains(txt) || i.getDiachi().contains(txt) || i.getEmail().contains(txt) || i.getSdt().contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Mã nhà xuất bản" -> {
                for (NhaXuatBanDTO i : listNxb) {
                    if (Integer.toString(i.getManxb()).contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Tên nhà xuất bản" -> {
                for (NhaXuatBanDTO i : listNxb) {
                    if (i.getTennxb().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Địa chỉ" -> {
                for (NhaXuatBanDTO i : listNxb) {
                    if (i.getDiachi().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Số điện thoại" -> {
                for (NhaXuatBanDTO i : listNxb) {
                    if (i.getSdt().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Email" -> {
                for (NhaXuatBanDTO i : listNxb) {
                    if (i.getEmail().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
            }
        }
        return result;
    }

    public String[] getArrTenNhaXuatBan() {
        int size = listNxb.size();
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = listNxb.get(i).getTennxb();
        }
        return result;
    }

    public String getTenNhaXuatBan(int mancc) {
        return this.listNxb.get(getIndexByMaNXB(mancc)).getTennxb();
    }

    public NhaXuatBanDTO findCT(ArrayList<NhaXuatBanDTO> ncc, String tenncc) {
        NhaXuatBanDTO p = null;
        int i = 0;
        while (i < ncc.size() && p == null) {
            if (ncc.get(i).getTennxb().equals(tenncc)) {
                p = ncc.get(i);
            } else {
                i++;
            }
        }
        return p;
    }
}
