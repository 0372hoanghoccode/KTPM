// /*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
// package BUS;

// import DAO.ChiTietLoHangDAO;

// import DAO.KhuVucSach1DAO;

// import DTO.ChiTietPhieuDTO;
// import DTO.KhuVucSach1DTO;
// import DTO.PhieuXuatDTO;
// import java.util.ArrayList;

// /**
// *
// * @author ASUS Vivobook
// */
// public class lohangBUS {
//         private ArrayList<KhuVucSach1DTO> listLH;

//         public ArrayList<KhuVucSach1DTO> search(String txt, String type) {
//         ArrayList<KhuVucSach1DTO> result = new ArrayList<>();
//         txt = txt.toLowerCase();
//         switch (type) {
//             case "Tất cả" -> {
//                 for (KhuVucSach1DTO i : listLH) {
//                     if (i.getMLH().toLowerCase().contains(txt) ) {
//                         result.add(i);
//                     }
//                 }
//             }
//             case "Mã khu vực lô" -> {
//                 for (KhuVucSach1DTO i : listLH) {
//                     if (i.getMLH().toLowerCase().contains(txt)) {
//                         result.add(i);
//                     }
//                 }
//             }
//         }
//         return result;
//     }
// }
