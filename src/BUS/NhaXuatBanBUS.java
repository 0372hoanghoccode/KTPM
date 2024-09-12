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

    public boolean add(NhaXuatBanDTO nxb) {
        boolean check = NxbDAO.insert(nxb) != 0;
        if (check) {
            this.listNxb.add(nxb);
        }
        return check;
    }

    public boolean delete(NhaXuatBanDTO nxb, int index) {
        boolean check = NxbDAO.delete(Integer.toString(nxb.getManxb())) != 0;
        if (check) {
            this.listNxb.remove(index);
        }
        return check;
    }

    public boolean update(NhaXuatBanDTO nxb) {
        boolean check = NxbDAO.update(nxb) != 0;
        if (check) {
            this.listNxb.set(getIndexByMaNXB(nxb.getManxb()), nxb);
        }
        return check;
    }

    public int getIndexByMaNXB(int manxb) {
        int i = 0;
        int vitri = -1;
        while (i < this.listNxb.size() && vitri == -1) {
            if (listNxb.get(i).getManxb() == manxb) {
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

    public String getTenNhaXuatBan(int manxb) {
        return this.listNxb.get(getIndexByMaNXB(manxb)).getTennxb();
    }

    public NhaXuatBanDTO findCT(ArrayList<NhaXuatBanDTO> nxb, String tennxb) {
        NhaXuatBanDTO p = null;
        int i = 0;
        while (i < nxb.size() && p == null) {
            if (nxb.get(i).getTennxb().equals(tennxb)) {
                p = nxb.get(i);
            } else {
                i++;
            }
        }
        return p;
    }
}
