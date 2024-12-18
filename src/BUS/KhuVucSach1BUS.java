package BUS;

import DAO.KhuVucSach1DAO;

import DTO.KhuVucSach1DTO;

import java.util.ArrayList;

public class KhuVucSach1BUS {

    private final KhuVucSach1DAO kvkDAO = new KhuVucSach1DAO();
    private ArrayList<KhuVucSach1DTO> listKVK = new ArrayList<>();

    public KhuVucSachBUS getInstance() {
        return new KhuVucSachBUS();
    }
    
//    public KhuVucSach1BUS() {
//        listKVK = kvkDAO.selectAll();
//    }

    public ArrayList<KhuVucSach1DTO> getAll() {
        return this.listKVK;
    }
//
//    public KhuVucSachDTO getByIndex(int index) {
//        return this.listKVK.get(index);
//    }
//
//    public int getIndexByMaLH(int makhuvuc) {
//        int i = 0;
//        int vitri = -1;
//        while (i < this.listKVK.size() && vitri == -1) {
//            if (listKVK.get(i).getMakhuvuc() == makhuvuc) {
//                vitri = i;
//            } else {
//                i++;
//            }
//        }
//        return vitri;
//    }
//
//    public boolean add(KhuVucSachDTO kvk) {
//        boolean check = kvkDAO.insert(kvk) != 0;
//        if (check) {
//            this.listKVK.add(kvk);
//        }
//        return check;
//    }

    public boolean delete(KhuVucSach1DTO kvk, int index) {
        boolean check = kvkDAO.delete(kvk.getMLH()) != 0;
        if (check) {
            this.listKVK.remove(index);
        }
        return check;
    }
//
//    public boolean update(KhuVucSachDTO kvk) {
//        boolean check = kvkDAO.update(kvk) != 0;
//        if (check) {
//            this.listKVK.set(getIndexByMaKVK(kvk.getMakhuvuc()), kvk);
//        }
//        return check;
//    }
//
//    public int getIndexByMaKVK(int makvk) {
//        int i = 0;
//        int vitri = -1;
//        while (i < this.listKVK.size() && vitri == -1) {
//            if (listKVK.get(i).getMakhuvuc() == makvk) {
//                vitri = i;
//                break;
//            } else {
//                i++;
//            }
//        }
//        return vitri;
//    }

//    public ArrayList<KhuVucSachDTO> search(String txt, String type) {
//        ArrayList<KhuVucSachDTO> result = new ArrayList<>();
//        txt = txt.toLowerCase();
//        switch (type) {
//            case "Tất cả" -> {
//                for (KhuVucSachDTO i : listKVK) {
//                    if (Integer.toString(i.getMakhuvuc()).contains(txt) || i.getTenkhuvuc().toLowerCase().contains(txt)){
//                        result.add(i);
//                    }
//                }
//            }
//            case "Mã khu vực sách" -> {
//                for (KhuVucSachDTO i : listKVK) {
//                    if (Integer.toString(i.getMakhuvuc()).contains(txt)) {
//                        result.add(i);
//                    }
//                }
//            }
//            case "Tên khu vực sách" -> {
//                for (KhuVucSachDTO i : listKVK) {
//                    if (i.getTenkhuvuc().toLowerCase().contains(txt)) {
//                        result.add(i);
//                    }
//                }
//            }
//        }
//        return result;
//    }
//    
   
}
