package BUS;

import DAO.KhuVucSachDAO;
import DTO.KhuVucSachDTO;
import java.util.ArrayList;

public class KhuVucSachBUS {

    private final KhuVucSachDAO kvkDAO = new KhuVucSachDAO();
    private ArrayList<KhuVucSachDTO> listKVK = new ArrayList<>();

    public KhuVucSachBUS getInstance() {
        return new KhuVucSachBUS();
    }
    
    public KhuVucSachBUS() {
        listKVK = kvkDAO.selectAll();
    }

    public ArrayList<KhuVucSachDTO> getAll() {
        return this.listKVK;
    }

    public KhuVucSachDTO getByIndex(int index) {
        return this.listKVK.get(index);
    }

    public int getIndexByMaLH(int makhuvuc) {
        int i = 0;
        int vitri = -1;
        while (i < this.listKVK.size() && vitri == -1) {
            if (listKVK.get(i).getMakhuvuc() == makhuvuc) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public boolean add(KhuVucSachDTO kvk) {
        boolean check = kvkDAO.insert(kvk) != 0;
        if (check) {
            this.listKVK.add(kvk);
        }
        return check;
    }

    public boolean delete(KhuVucSachDTO kvk, int index) {
        boolean check = kvkDAO.delete(Integer.toString(kvk.getMakhuvuc())) != 0;
        if (check) {
            this.listKVK.remove(index);
        }
        return check;
    }

    public boolean update(KhuVucSachDTO kvk) {
        boolean check = kvkDAO.update(kvk) != 0;
        if (check) {
            this.listKVK.set(getIndexByMaKVK(kvk.getMakhuvuc()), kvk);
        }
        return check;
    }

    public int getIndexByMaKVK(int makvk) {
        int i = 0;
        int vitri = -1;
        while (i < this.listKVK.size() && vitri == -1) {
            if (listKVK.get(i).getMakhuvuc() == makvk) {
                vitri = i;
                break;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public ArrayList<KhuVucSachDTO> search(String txt, String type) {
        ArrayList<KhuVucSachDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (KhuVucSachDTO i : listKVK) {
                    if (Integer.toString(i.getMakhuvuc()).contains(txt) || i.getTenkhuvuc().toLowerCase().contains(txt)){
                        result.add(i);
                    }
                }
            }
            case "Mã khu vực sách" -> {
                for (KhuVucSachDTO i : listKVK) {
                    if (Integer.toString(i.getMakhuvuc()).contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Tên khu vực sách" -> {
                for (KhuVucSachDTO i : listKVK) {
                    if (i.getTenkhuvuc().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
            }
        }
        return result;
    }
    
    public String[] getArrTenKhuVuc() {
        int size = listKVK.size();
        String[] result = new String[size];
        for(int i = 0; i < size; i++) {
            result[i] = listKVK.get(i).getTenkhuvuc();
        }
        return result;
    }
    
    public String getTenKhuVuc(int makhuvuc) {
        return this.listKVK.get(this.getIndexByMaKVK(makhuvuc)).getTenkhuvuc();
    }
}
