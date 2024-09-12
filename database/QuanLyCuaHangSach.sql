-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th9 11, 2024 lúc 06:07 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlycuahangsach`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ctgiohang`
--

CREATE TABLE `ctgiohang` (
  `MKH` int(11) NOT NULL COMMENT 'Mã khách hàng',
  `MSP` int(11) NOT NULL COMMENT 'Mã sản phẩm',
  `MKM` varchar(255) DEFAULT NULL COMMENT 'Mã khuyến mãi',
  `SL` int(11) NOT NULL COMMENT 'Số lượng',
  `TIENGIO` int(11) NOT NULL COMMENT 'Tiền giỏ'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ctmakhuyenmai`
--

CREATE TABLE `ctmakhuyenmai` (
  `MKM` varchar(255) NOT NULL COMMENT 'Mã khuyến mãi',
  `MSP` int(11) NOT NULL COMMENT 'Mã sản phẩm',
  `PTG` int(11) NOT NULL COMMENT 'Phần trăm giảm'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ctmakhuyenmai`
--

INSERT INTO `ctmakhuyenmai` (`MKM`, `MSP`, `PTG`) VALUES
('GT2024', 1, 20),
('GT2024', 2, 20),
('GT2024', 3, 20),
('MINGEY2024', 4, 80),
('MINGEY2024', 5, 50),
('MINGEY2024', 6, 60);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ctphieunhap`
--

CREATE TABLE `ctphieunhap` (
  `MPN` int(11) NOT NULL COMMENT 'Mã phiếu nhập',
  `MSP` int(11) NOT NULL COMMENT 'Mã sản phẩm',
  `SL` int(11) NOT NULL COMMENT 'Số lượng',
  `TIENNHAP` int(11) NOT NULL COMMENT 'Tiền nhập',
  `HINHTHUC` int(11) NOT NULL DEFAULT 0 COMMENT 'Tổng tiền'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ctphieunhap`
--

INSERT INTO `ctphieunhap` (`MPN`, `MSP`, `SL`, `TIENNHAP`, `HINHTHUC`) VALUES
(1, 1, 2, 20000, 0),
(1, 2, 2, 40000, 0),
(2, 3, 2, 40000, 0),
(2, 4, 2, 80000, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ctphieuxuat`
--

CREATE TABLE `ctphieuxuat` (
  `MPX` int(11) NOT NULL COMMENT 'Mã phiếu xuất',
  `MSP` int(11) NOT NULL COMMENT 'Mã sản phẩm',
  `MKM` varchar(255) DEFAULT NULL COMMENT 'Mã khuyến mãi',
  `SL` int(11) NOT NULL COMMENT 'Số lượng',
  `TIENXUAT` int(11) NOT NULL COMMENT 'Tiền xuất'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ctphieuxuat`
--

INSERT INTO `ctphieuxuat` (`MPX`, `MSP`, `MKM`, `SL`, `TIENXUAT`) VALUES
(1, 1, NULL, 2, 100000),
(1, 2, NULL, 2, 200000),
(2, 3, NULL, 2, 300000),
(2, 4, NULL, 2, 400000),
(4, 1, NULL, 1, 134000),
(4, 2, NULL, 3, 63500);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ctquyen`
--

CREATE TABLE `ctquyen` (
  `MNQ` int(11) NOT NULL COMMENT 'Mã nhóm quyền',
  `MCN` varchar(50) NOT NULL COMMENT 'Mã chức năng',
  `HANHDONG` varchar(255) NOT NULL COMMENT 'Hành động thực hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ctquyen`
--

INSERT INTO `ctquyen` (`MNQ`, `MCN`, `HANHDONG`) VALUES
(1, 'giohang', 'view'),
(1, 'khachhang', 'create'),
(1, 'khachhang', 'delete'),
(1, 'khachhang', 'update'),
(1, 'khachhang', 'view'),
(1, 'khuvucsach', 'create'),
(1, 'khuvucsach', 'delete'),
(1, 'khuvucsach', 'update'),
(1, 'khuvucsach', 'view'),
(1, 'kiemke', 'create'),
(1, 'kiemke', 'delete'),
(1, 'kiemke', 'update'),
(1, 'kiemke', 'view'),
(1, 'makhuyenmai', 'create'),
(1, 'makhuyenmai', 'delete'),
(1, 'makhuyenmai', 'update'),
(1, 'makhuyenmai', 'view'),
(1, 'nhacungcap', 'create'),
(1, 'nhacungcap', 'delete'),
(1, 'nhacungcap', 'update'),
(1, 'nhacungcap', 'view'),
(1, 'nhanvien', 'create'),
(1, 'nhanvien', 'delete'),
(1, 'nhanvien', 'update'),
(1, 'nhanvien', 'view'),
(1, 'nhaphang', 'create'),
(1, 'nhaphang', 'delete'),
(1, 'nhaphang', 'update'),
(1, 'nhaphang', 'view'),
(1, 'nhaxuatban', 'create'),
(1, 'nhaxuatban', 'delete'),
(1, 'nhaxuatban', 'update'),
(1, 'nhaxuatban', 'view'),
(1, 'nhomquyen', 'create'),
(1, 'nhomquyen', 'delete'),
(1, 'nhomquyen', 'update'),
(1, 'nhomquyen', 'view'),
(1, 'sanpham', 'create'),
(1, 'sanpham', 'delete'),
(1, 'sanpham', 'update'),
(1, 'sanpham', 'view'),
(1, 'taikhoan', 'create'),
(1, 'taikhoan', 'delete'),
(1, 'taikhoan', 'update'),
(1, 'taikhoan', 'view'),
(1, 'taikhoankh', 'create'),
(1, 'taikhoankh', 'delete'),
(1, 'taikhoankh', 'update'),
(1, 'taikhoankh', 'view'),
(1, 'thongke', 'create'),
(1, 'thongke', 'delete'),
(1, 'thongke', 'update'),
(1, 'thongke', 'view'),
(1, 'trahang', 'create'),
(1, 'trahang', 'delete'),
(1, 'trahang', 'update'),
(1, 'trahang', 'view'),
(1, 'xuathang', 'create'),
(1, 'xuathang', 'delete'),
(1, 'xuathang', 'update'),
(1, 'xuathang', 'view'),
(2, 'khachhang', 'create'),
(2, 'khachhang', 'delete'),
(2, 'khachhang', 'update'),
(2, 'khachhang', 'view'),
(2, 'nhaxuatban', 'view'),
(2, 'sanpham', 'view'),
(2, 'trahang', 'create'),
(2, 'trahang', 'delete'),
(2, 'trahang', 'update'),
(2, 'trahang', 'view'),
(2, 'xuathang', 'create'),
(2, 'xuathang', 'update'),
(2, 'xuathang', 'view'),
(3, 'kiemke', 'create'),
(3, 'kiemke', 'delete'),
(3, 'kiemke', 'update'),
(3, 'kiemke', 'view'),
(3, 'nhacungcap', 'create'),
(3, 'nhacungcap', 'delete'),
(3, 'nhacungcap', 'update'),
(3, 'nhacungcap', 'view'),
(3, 'nhaphang', 'create'),
(3, 'nhaphang', 'update'),
(3, 'nhaphang', 'view'),
(3, 'nhaxuatban', 'create'),
(3, 'nhaxuatban', 'delete'),
(3, 'nhaxuatban', 'update'),
(3, 'nhaxuatban', 'view'),
(3, 'sanpham', 'create'),
(3, 'sanpham', 'delete'),
(3, 'sanpham', 'update'),
(3, 'sanpham', 'view'),
(4, 'donhang', 'create'),
(4, 'donhang', 'delete'),
(4, 'donhang', 'update'),
(4, 'donhang', 'view'),
(4, 'giohang', 'create'),
(4, 'giohang', 'delete'),
(4, 'giohang', 'update'),
(4, 'giohang', 'view'),
(4, 'xemhang', 'create'),
(4, 'xemhang', 'view'),
(5, 'xemhang', 'view');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danhmucchucnang`
--

CREATE TABLE `danhmucchucnang` (
  `MCN` varchar(50) NOT NULL COMMENT 'Mã chức năng',
  `TEN` varchar(255) NOT NULL COMMENT 'Tên chức năng',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `danhmucchucnang`
--

INSERT INTO `danhmucchucnang` (`MCN`, `TEN`, `TT`) VALUES
('donhang', 'Chức năng xem đơn hàng', 1),
('giohang', 'Chức năng xem giỏ hàng', 1),
('khachhang', 'Quản lý khách hàng', 1),
('khuvucsach', 'Quản lý khu vực sách', 1),
('kiemke', 'Quản lý kiểm kê', 1),
('makhuyenmai', 'Quản lý mã khuyến mãi', 1),
('nhacungcap', 'Quản lý nhà cung cấp', 1),
('nhanvien', 'Quản lý nhân viên', 1),
('nhaphang', 'Quản lý nhập hàng', 1),
('nhaxuatban', 'Quản lý nhà xuất bản', 1),
('nhomquyen', 'Quản lý nhóm quyền', 1),
('sanpham', 'Quản lý sản phẩm', 1),
('taikhoan', 'Quản lý tài khoản', 1),
('taikhoankh', 'Quản lý tài khoản khách hàng', 1),
('thongke', 'Quản lý thống kê', 1),
('trahang', 'Quản lý trả hàng', 1),
('xemhang', 'Chức năng xem hàng', 1),
('xuathang', 'Quản lý xuất hàng', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `giohang`
--

CREATE TABLE `giohang` (
  `MKH` int(11) NOT NULL COMMENT 'Mã khách hàng',
  `TIEN` int(11) NOT NULL COMMENT 'Tổng tiền',
  `TG` datetime DEFAULT current_timestamp() COMMENT 'Thời gian tạo',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `giohang`
--

INSERT INTO `giohang` (`MKH`, `TIEN`, `TG`, `TT`) VALUES
(19, 0, '2024-09-10 00:52:04', 1),
(20, 0, '2024-09-11 13:09:11', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `MKH` int(11) NOT NULL COMMENT 'Mã khách hàng',
  `HOTEN` varchar(255) NOT NULL COMMENT 'Họ và tên KH',
  `NGAYTHAMGIA` date NOT NULL COMMENT 'Ngày tạo dữ liệu',
  `DIACHI` varchar(255) DEFAULT NULL COMMENT 'Địa chỉ',
  `SDT` varchar(11) NOT NULL COMMENT 'Số điện thoại',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT 'Email',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`MKH`, `HOTEN`, `NGAYTHAMGIA`, `DIACHI`, `SDT`, `EMAIL`, `TT`) VALUES
(1, 'Nguyễn Văn A', '2024-04-15', 'Gia Đức, Ân Đức, Hoài Ân, Bình Định', '0387913347', NULL, 1),
(2, 'Trần Nhất Nhất', '2024-04-15', '205 Trần Hưng Đạo, Phường 10, Quận 5, Thành phố Hồ Chí Minh', '0123456789', NULL, 1),
(3, 'Hoàng Gia Bo', '2024-04-15', 'Khoa Trường, Hoài Ân, Bình Định', '0987654321', NULL, 1),
(4, 'Hồ Minh Hưng', '2024-04-15', 'Khoa Trường, Hoài Ân, Bình Định', '0867987456', NULL, 1),
(5, 'Nguyễn Thị Minh Anh', '2024-04-16', '123 Phố Huế, Quận Hai Bà Trưng, Hà Nội', '0935123456', NULL, 1),
(6, 'Trần Đức Minh', '2024-04-16', '789 Đường Lê Hồng Phong, Thành phố Đà Nẵng', '0983456789', NULL, 1),
(7, 'Lê Hải Yến', '2024-04-16', '456 Tôn Thất Thuyết, Quận 4, Thành phố Hồ Chí Minh', '0977234567', NULL, 1),
(8, 'Phạm Thanh Hằng', '2024-04-16', '102 Lê Duẩn, Thành phố Hải Phòng', '0965876543', NULL, 1),
(9, 'Hoàng Đức Anh', '2024-04-16', '321 Lý Thường Kiệt, Thành phố Cần Thơ', '0946789012', NULL, 1),
(10, 'Ngô Thanh Tùng', '2024-04-16', '987 Trần Hưng Đạo, Quận 1, Thành phố Hồ Chí Minh', '0912345678', NULL, 1),
(11, 'Võ Thị Kim Ngân', '2024-04-16', '555 Nguyễn Văn Linh, Quận Nam Từ Liêm, Hà Nội', '0916789123', NULL, 1),
(12, 'Đỗ Văn Tú', '2024-04-30', '777 Hùng Vương, Thành phố Huế', '0982345678', NULL, 1),
(13, 'Lý Thanh Trúc', '2024-04-16', '888 Nguyễn Thái Học, Quận Ba Đình, Hà Nội', '0982123456', NULL, 1),
(14, 'Bùi Văn Hoàng', '2024-04-16', '222 Đường 2/4, Thành phố Nha Trang', '0933789012', NULL, 1),
(15, 'Lê Văn Thành', '2024-04-16', '23 Đường 3 Tháng 2, Quận 10, TP. Hồ Chí Minh', '0933456789', NULL, 1),
(16, 'Nguyễn Thị Lan Anh', '2024-04-16', '456 Lê Lợi, Quận 1, TP. Hà Nội', '0965123456', NULL, 1),
(17, 'Phạm Thị Mai', '2024-04-17', '234 Lê Hồng Phong, Quận 5, TP. Hồ Chí Minh', '0946789013', NULL, 1),
(18, 'Hoàng Văn Nam', '2024-04-17', ' 567 Phố Huế, Quận Hai Bà Trưng, Hà Nội', '0912345679', NULL, 1),
(19, 'Hoàng Hoàn', '2024-09-10', NULL, '0123123123', 'hoanhoan@gmail.com', 1),
(20, 'ttttttt', '2024-09-11', NULL, '0111111111', 'trang@gmail.com', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khuvucsach`
--

CREATE TABLE `khuvucsach` (
  `MKVS` int(11) NOT NULL COMMENT 'Mã khu vực sách',
  `TEN` varchar(255) NOT NULL COMMENT 'Tên khu vực sách',
  `GHICHU` varchar(255) DEFAULT '' COMMENT 'Ghi chú',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khuvucsach`
--

INSERT INTO `khuvucsach` (`MKVS`, `TEN`, `GHICHU`, `TT`) VALUES
(1, 'Khu vực A', 'Sách dành cho giới trẻ', 1),
(2, 'Khu vực B', 'Văn học - Nghệ thuật', 1),
(3, 'Khu vực C', 'Văn học thiếu nhi', 1),
(4, 'Khu vực D', 'Sách Chính trị - Xã hội', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `makhuyenmai`
--

CREATE TABLE `makhuyenmai` (
  `MKM` varchar(255) NOT NULL COMMENT 'Mã khuyến mãi',
  `MNV` int(11) NOT NULL COMMENT 'Mã nhân viên',
  `TGBD` date NOT NULL COMMENT 'Thời gian bắt đầu',
  `TGKT` date NOT NULL COMMENT 'Thời gian kết thúc',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `makhuyenmai`
--

INSERT INTO `makhuyenmai` (`MKM`, `MNV`, `TGBD`, `TGKT`, `TT`) VALUES
('GT2024', 1, '2024-04-01', '2024-05-01', 1),
('MINGEY2024', 1, '2024-05-01', '2024-05-20', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
  `MNV` int(11) NOT NULL COMMENT 'Mã nhân viên',
  `HOTEN` varchar(255) NOT NULL COMMENT 'Họ và tên NV',
  `GIOITINH` int(11) NOT NULL COMMENT 'Giới tính',
  `NGAYSINH` date NOT NULL COMMENT 'Ngày sinh',
  `SDT` varchar(11) NOT NULL COMMENT 'Số điện thoại',
  `EMAIL` varchar(50) NOT NULL COMMENT 'Email',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`MNV`, `HOTEN`, `GIOITINH`, `NGAYSINH`, `SDT`, `EMAIL`, `TT`) VALUES
(1, 'Bùi Quang Minh Hiếu', 0, '2077-01-01', '0505555505', 'remchan.com@gmail.com', 1),
(2, 'Trương Hiếu', 1, '2023-05-06', '0123456789', 'nguyeney111@gmail.com', 1),
(3, 'HoangHocCode', 1, '2004-07-17', '0377984957', 'hoen@gmail.com', 1),
(4, 'Hoàng Hoàn', 1, '2004-04-11', '0355374322', 'appleanime2501@gmail.com', 1),
(5, 'Hoanghoccode', 1, '2004-04-11', '0123456789', 'chinchin@gmail.com', 1),
(6, 'Nguyễn Văn A', 1, '1004-04-03', '0123456789', 'ngocan@gmail.com', 1),
(7, 'Hiếu Ác', 1, '2004-08-16', '0123123123', 'hieuaclatao@gmail.com', -1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhaxuatban`
--

CREATE TABLE `nhaxuatban` (
  `MNXB` int(11) NOT NULL COMMENT 'Mã nhà xuất bản',
  `TEN` varchar(255) NOT NULL COMMENT 'Tên nhà xuất bản',
  `DIACHI` varchar(255) DEFAULT NULL COMMENT 'Địa chỉ',
  `SDT` varchar(12) DEFAULT NULL COMMENT 'Số điện thoại',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT 'Email',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhaxuatban`
--

INSERT INTO `nhaxuatban` (`MNXB`, `TEN`, `DIACHI`, `SDT`, `EMAIL`, `TT`) VALUES
(1, 'NXB Kim Đồng', 'Số 55 Quang Trung, Nguyễn Du, Hai Bà Trưng, Hà Nội', '1900571595', 'cskh_online@nxbkimdong.com.vn', 1),
(2, 'NXB Trẻ', '161B Lý Chính Thắng, phường Võ Thị Sáu, Quận 3, TP. Hồ Chí Minh', '02839316289', 'hopthubandoc@nxbtre.com.vn', 1),
(3, 'NXB Tổng hợp thành phố Hồ Chí Minh', '62 Nguyễn Thị Minh Khai, Phường Đa Kao, Quận 1, TP. HCM', '02838256804', 'nstonhop@gmail.com', 1),
(4, 'NXB Hội Nhà văn', 'số 65 Nguyễn Du, Quận Hai Bà Trưng, Hà Nội', '02438222135', 'nhaxuatbanhnv@gmail.com', 1),
(5, 'NXB Chính trị Quốc gia Sự thật', '6/86 Duy tân, Cầu Giấy, Hà Nội', '02438221581', 'phathanh@nxbctqg.vn', 1),
(6, 'NXB Phụ nữ Việt Nam', '39 Hàng Chuối, Quận Hai Bà Trưng, Hà Nội', '02439710717', 'truyenthongvaprnxbpn@gmail.com', 1),
(7, 'NXB Lao Động', '175 Giảng Võ, Đống Đa, Hà Nội', '', 'nxblaodong@yahoo.com', 1),
(8, 'NXB Đinh Tị Book', 'Số 78, Đường số 1, P. 4, Q. Gò Vấp, TP. Hồ Chí Minh', '02473093388', 'contacts@dinhtibooks.vn', 1),
(9, 'NXB Nhã Nam', 'Số 59, Đỗ Quang, Trung Hoà, Cầu Giấy, Hà Nội', '02435146876', '', 1),
(10, 'NXB Nhà xuất bản Dân Trí', ' Số 9, ngõ 26, phố Hoàng Cầu, phường Ô Chợ Dừa, quận Đống Đa, Hà Nội', '02466860751', 'nxbdantri@gmail.com', 1),
(11, 'NXB Văn hóa - Văn nghệ TP. HCM', '88-90 Ký Con, P. Nguyễn Thái Bình, Q. 1, Tp. Hồ Chí Minh', '02838216009', ' nxbvhvn@nxbvanhoavannghe.org.vn', 1),
(12, 'NXB Công an nhân dân', '100 Yết Kiêu, Hai Bà Trưng, Hà Nội', '0692342969', '', 1),
(13, 'NXB Đại Học Kinh Tế Quốc Dân', '207 Đường Giải Phóng - Hà Nội', '02436282487', 'nxb@neu.edu.vn', 1),
(14, 'NXB Đông Tây', 'Số 9 Lê Văn Thiêm - Thanh Xuân - Hà Nội', '02422157878', 'lienhe@dongtay.vn', 1),
(15, 'NXB Thông tin & Truyền thông', 'Tầng 6, Tòa nhà Cục Tần số vô tuyến điện, 115 Trần Duy Hưng, Hà Nội', '02435772139', 'nxb.tttt@mic.gov.vn', 1),
(16, 'NXB Thế Giới', '7 Đ. Nguyễn Thị Minh Khai, Bến Nghé, Quận 1, Thành phố Hồ Chí Minh', '02838220102', '', 1),
(17, 'NXB Khoa học xã hội', '26 Lý Thường Kiệt, Phường Hàng Bài, Quận Hoàn Kiếm, Hà Nội', '', 'nxbkhxh@gmail.com', 1),
(18, 'NXB Nghệ An', 'Số 37B, Lê Hồng Phong, thành phố Vinh, Nghệ An', '02383844748', 'nhaxuatbannghean@gmail.com', 1),
(19, 'NXB Văn hóa dân tộc', ' Số 19 Nguyễn Bỉnh Khiêm,Quận Hai Bà Trưng,TP. Hà Nội', '02438263070', 'nxbvanhoadantoc@yahoo.com.vn', 1),
(20, 'NXB Hồng Đức', '65 P. Tràng Thi, Hàng Bông, Hoàn Kiếm, Hà Nội', '02439260024', '', 1),
(21, 'NXB Công thương', 'Tầng 4 - Tòa nhà Bộ Công thương, số 655 Phạm Văn Đồng - Bắc Từ Liêm - Hà Nội - Việt Nam', '02439341562', 'nxbct@moit.gov.vn', 1),
(22, 'NXB Văn học', '18 Nguyễn Trường Tộ - Ba Đình - Hà Nội', '02437163409', 'info@nxbvanhoc.com.vn', 1),
(23, 'NXB Dân Trí', 'Số 9, ngõ 26, phố Hoàng Cầu, phường Ô Chợ Dừa, quận Đống Đa, Hà Nội', '02466860751', 'nxbdantri@gmail.com', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhomquyen`
--

CREATE TABLE `nhomquyen` (
  `MNQ` int(11) NOT NULL COMMENT 'Mã nhóm quyền',
  `TEN` varchar(255) NOT NULL COMMENT 'Tên nhóm quyền',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhomquyen`
--

INSERT INTO `nhomquyen` (`MNQ`, `TEN`, `TT`) VALUES
(1, 'Quản lý cửa hàng', 1),
(2, 'Nhân viên bán hàng', 1),
(3, 'Nhân viên quản lý kho', 1),
(4, 'Khách hàng', 1),
(5, 'Người xem', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieunhap`
--

CREATE TABLE `phieunhap` (
  `MPN` int(11) NOT NULL COMMENT 'Mã phiếu nhập',
  `MNV` int(11) NOT NULL COMMENT 'Mã nhân viên',
  `TIEN` int(11) NOT NULL COMMENT 'Tổng tiền',
  `TG` datetime DEFAULT current_timestamp() COMMENT 'Thời gian tạo',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieunhap`
--

INSERT INTO `phieunhap` (`MPN`, `MNV`, `TIEN`, `TG`, `TT`) VALUES
(1, 1, 100000, '2024-04-01 01:09:27', 1),
(2, 1, 200000, '2024-04-02 01:09:27', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieutra`
--

CREATE TABLE `phieutra` (
  `MPX` int(11) NOT NULL COMMENT 'Mã phiếu xuất',
  `MNV` int(11) NOT NULL COMMENT 'Mã nhân viên',
  `MKH` int(11) NOT NULL COMMENT 'Mã khách hàng',
  `TIEN` int(11) NOT NULL COMMENT 'Tổng tiền',
  `TG` datetime DEFAULT current_timestamp() COMMENT 'Thời gian tạo',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieutra`
--

INSERT INTO `phieutra` (`MPX`, `MNV`, `MKH`, `TIEN`, `TG`, `TT`) VALUES
(1, 1, 1, 100000, '2024-04-20 17:34:12', 1),
(4, 1, 19, 140000, '2024-09-11 13:13:44', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieuxuat`
--

CREATE TABLE `phieuxuat` (
  `MPX` int(11) NOT NULL COMMENT 'Mã phiếu xuất',
  `MNV` int(11) DEFAULT 1 COMMENT 'Mã nhân viên',
  `MKH` int(11) NOT NULL COMMENT 'Mã khách hàng',
  `TIEN` int(11) NOT NULL COMMENT 'Tổng tiền',
  `TG` datetime DEFAULT current_timestamp() COMMENT 'Thời gian tạo',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieuxuat`
--

INSERT INTO `phieuxuat` (`MPX`, `MNV`, `MKH`, `TIEN`, `TG`, `TT`) VALUES
(1, 1, 1, 100000, '2024-04-18 17:34:12', 1),
(2, 1, 2, 200000, '2024-04-17 18:19:51', 1),
(3, 1, 19, 2816000, '2024-09-10 01:02:26', 2),
(4, 1, 20, 324500, '2024-09-11 13:09:59', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `MSP` int(11) NOT NULL COMMENT 'Mã sản phẩm',
  `TEN` varchar(255) NOT NULL COMMENT 'Tên sản phẩm',
  `HINHANH` varchar(255) NOT NULL COMMENT 'Hình sản phẩm',
  `DANHMUC` varchar(255) NOT NULL COMMENT 'Danh mục',
  `NAMXB` int(11) NOT NULL COMMENT 'Năm xuất bản',
  `MNXB` int(11) NOT NULL COMMENT 'Mã nhà xuất bản',
  `TENTG` varchar(255) NOT NULL COMMENT 'Tên tác giả',
  `MKVS` int(11) NOT NULL COMMENT 'Mã khu vực sách',
  `TIENX` int(11) NOT NULL COMMENT 'Tiền xuất',
  `TIENN` int(11) NOT NULL COMMENT 'Tiền nhập',
  `SL` int(11) DEFAULT 0 COMMENT 'Số lượng',
  `ISBN` varchar(255) NOT NULL COMMENT 'Mã ISBN',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`MSP`, `TEN`, `HINHANH`, `DANHMUC`, `NAMXB`, `MNXB`, `TENTG`, `MKVS`, `TIENX`, `TIENN`, `SL`, `ISBN`, `TT`) VALUES
(1, 'Ám thị tâm lý', 'kogoiob1cgjlqhndkc0dcw1hzj1kqook.png', 'Sách dành cho giới trẻ', 2022, 21, 'Patrick King - Huy Nguyễn', 1, 134000, 100000, 5, '9786046863748', 1),
(2, 'Không gì là không thể', 'xtx0d0apioa66abawbnpybv4v8wsb54p.png', 'Sách dành cho giới trẻ', 2020, 3, 'George Matthew Adams', 1, 63500, 60000, 5, '8935086837665', 1),
(3, 'Tư duy ngược', '050d67e2d58f5e291cbf25da53f55ef7.jpg', 'Sách dành cho giới trẻ', 2020, 10, 'Nguyễn Anh Dũng', 1, 125000, 100000, 5, '9786043440287', 1),
(4, 'Bạn đắt giá bao nhiêu?', 'txq47334nnwj1cw5f5ux7egme8erj2k7.jpg', 'Sách dành cho giới trẻ', 2021, 9, 'Vãn Tình', 1, 107000, 100000, 5, '8936186543500', 1),
(5, 'Hãy gọi tên tôi', '90adcd1cc9b81aa80a272a653c7785e5.png', 'Sách dành cho giới trẻ', 2020, 11, 'Chanel Miller', 1, 118000, 100000, 5, '9786046863747', 1),
(6, 'Đời ngắn đừng ngủ dài', 'q9rcr9y1ax8gb1u372kxb0eivlg2xegc.jpeg', 'Sách dành cho giới trẻ', 2020, 2, 'Robin Sharma', 1, 64000, 60000, 5, '8934974158691', 1),
(7, 'Tội ác sau những bức tranh', 'rl7k9yn47k492vlxrbbnx9jmdy3ps89x.jpeg', 'Văn học - Nghệ thuật', 2022, 6, 'Jason Rekulak', 2, 146000, 100000, 5, '8934974158692', 1),
(8, 'Người rỗng', '93lh9xopdaqdtimbzosmo3n3h3evhmcv.jpeg', 'Văn học - Nghệ thuật', 2020, 22, 'John Dickson Carr', 2, 70000, 60000, 2, '9786049847257', 1),
(9, 'Tạp văn Nguyễn Ngọc Tư', '4b1ea768cbbef544c4ca16fe1967634b.jpg', 'Văn học - Nghệ thuật', 2021, 2, 'Nguyễn Ngọc Tư', 2, 72000, 60000, 5, '8934974168607', 1),
(10, 'Bông sen vàng', 'ythnuw9ks3kk4l0lb1pa6fdtoo1tz32a.jpeg', 'Văn học - Nghệ thuật', 2022, 5, 'Sơn Tùng', 2, 144000, 60000, 5, '8934974168617', 1),
(11, 'Hoa tuylip đen', 'sbgtbeq0vz1dhnag0qf3jrp6enxjgw20.jpeg', 'Văn học - Nghệ thuật', 2017, 22, 'Alexandre Dumas cha', 2, 49000, 30000, 5, '8936067597097', 1),
(12, 'Truyện ngụ ngôn E Dốp', '883868zq1vtv4qqm7plgby2g8k0i2mkv.jpeg', 'Văn học - Nghệ thuật', 2019, 22, 'Aesop', 2, 59000, 30000, 5, '9786049633198', 1),
(13, 'Nhật ký trong tù', 'cd19aa6163295ef2dff24012f78b9aec.jpg', 'Văn học - Nghệ thuật', 2021, 5, 'Hồ Chí Minh', 2, 105000, 50000, 5, '9786043238112', 1),
(14, 'Phận liễu', 'detep2imksxnh97om03avji0q1p47clp.jpeg', 'Văn học - Nghệ thuật', 2020, 12, 'Chu Thanh Hương', 2, 162000, 50000, 5, '9786047244300', 1),
(15, 'Đồng tiền hạnh phúc', '6619719473f6f132a18182f019364abf.jpg', 'Văn học - Nghệ thuật', 2020, 13, 'Ken Honda', 2, 85500, 50000, 5, '8935095629664', 1),
(16, 'Lũ trẻ thủy tinh', 'rnwjyi3x3zzxzjxlq4ba0zaeqpu0lugk.jpeg', 'Văn học thiếu nhi', 2021, 1, 'Kristina Ohlsson', 3, 28000, 10000, 5, '9786042190862', 1),
(17, 'Lũ trẻ đường ray', '22znw9gr4514dul0lc5rgisvc47qxfw6.jpeg', 'Văn học thiếu nhi', 2020, 22, 'Edith Nesbit', 3, 63000, 50000, 5, '9786049693465', 1),
(18, 'Tớ sợ cái đồng hồ', 'fs2xgxjq2yswm6l33gfv50mwg6mgu4qm.jpeg', 'Văn học thiếu nhi', 2018, 14, 'Nguyễn Quỳnh Mai', 3, 52000, 30000, 5, '9786045653012', 1),
(19, 'Khu rừng trong thành phố', 'cmpq0o6lb0jqmna7f9n2k7y61z84if0u.jpeg', 'Văn học thiếu nhi', 2018, 14, 'Nguyễn Quỳnh Mai', 3, 58000, 30000, 5, '9786045653005', 1),
(20, 'Đảo ngàn sao', '5hghrc3synmydygcyzwsl7noz3pt7u31.jpeg', 'Văn học thiếu nhi', 2021, 1, 'Emma Karinsdotter', 3, 48000, 30000, 5, '9786042221603', 1),
(21, 'Cậu bé bạc', 'ivssbyx4axf57c2tsww8kqyt1a4xauhv.jpeg', 'Văn học thiếu nhi', 2020, 1, 'Kristina Ohlsson', 3, 30000, 10000, 5, '9786042186315', 1),
(22, 'Ngựa ô yêu dấu', 'wf05ukmavgsdar1a772qc24c1iawtcfg.jpeg', 'Văn học thiếu nhi', 2022, 23, 'Anna Sewell', 3, 109000, 50000, 5, '9786043565027', 1),
(23, 'Chuyện con mèo dạy hải âu bay', 'iuj2x3gbldratw5xzjg9s1aterb66k8t.jpeg', 'Văn học thiếu nhi', 2019, 4, 'Luis Sepúlveda', 3, 42000, 10000, 5, '8935235222113', 1),
(24, 'Chú bé mang Pyjama sọc', 'o53krzx7g5du11thdcme6xl2uqd2s8gp.jpeg', 'Văn học thiếu nhi', 2018, 4, 'John Boyne', 3, 58000, 30000, 5, '8935235217898', 1),
(25, 'Những lá thư thời chiến Việt Nam (Tuyển tập)', '25ac83e2311e9fcaf146c655f672d6eb.jpg', 'Sách Chính trị - Xã hội', 2023, 5, 'Đặng Vương Hưng', 4, 144000, 100000, 5, '8935279148646', 1),
(26, 'Kỷ yếu Hoàng Sa', 'kry3vs4zab398dch1jc23zycidl0jvaq.jpeg', 'Sách Chính trị - Xã hội', 2014, 15, 'UBND Huyện Hoàng Sa', 4, 153000, 100000, 5, '9786048002930', 1),
(27, 'Dấu ấn Việt Nam trên Biển Đông', '1odzbcuzqspuou03uwdnstegf6pe4jp6.jpeg', 'Sách Chính trị - Xã hội', 2012, 15, 'TS. Trần Công Trục', 4, 191000, 100000, 5, '9786048018740', 1),
(28, 'Chân dung Ngô Tất Tố', 'ytja13yxd96huojklmyy1cadwq4rykoi.jpeg', 'Sách Chính trị - Xã hội', 2014, 15, 'Cao Đắc Điểm - Ngô Thị Thanh Lịch', 4, 38000, 20000, 5, '9786048005214', 1),
(29, 'Chính sách đối ngoại đổi mới của Việt Nam (1986 - 2015)', 'eplg2rc40dd1zfp0wzp4zo5s0aok0khp.jpeg', 'Sách Chính trị - Xã hội', 2018, 16, 'PGS. TS. Phạm Quang Minh', 4, 56000, 30000, 5, '9786047744749', 1),
(30, 'Đặc trưng và vai trò của tầng lớp trung lưu ở Việt Nam', 'v4e1gkm2jgz2b7tdktk504lsgemrff59.jpeg', 'Sách Chính trị - Xã hội', 2022, 17, 'TS. Lê Kim Sa', 4, 81000, 70000, 5, '9786043089585', 1),
(31, 'Sức mạnh mềm văn hóa Trung Quốc thời Tập Cận Bình và ứng xử của Việt Nam', 'qkphoffa38djvveox22613t5qx00ha8c.jpeg', 'Sách Chính trị - Xã hội', 2022, 17, 'Ths.Chử Thị Bích Thu - TS.Trần Thị Thủy (Đồng chủ biên)', 4, 99000, 50000, 5, '97860430839493', 1),
(32, 'Đường tới Điện Biên Phủ', 'dbd44e00c80b8c79694bc2a87a36c20f.jpg', 'Sách Chính trị - Xã hội', 2018, 15, 'Đại tướng Võ Nguyên Giáp', 4, 47000, 30000, 5, '9786048030759', 1),
(33, 'Đường tới Truông Bồn huyền thoại', 'wofawcrdyttsuu37j3mh06i2m6ii0lq0.jpeg', 'Sách Chính trị - Xã hội', 2019, 18, 'Văn Hiền', 4, 150000, 100000, 5, '9786049642937', 1),
(34, 'Vương Dương Minh toàn thư', '8b90cf59071f3ed109d17770a0ec50ed.jpg', 'Sách Chính trị - Xã hội', 2023, 15, 'Túc Dịch Minh - Nguyễn Thanh Hải', 4, 443000, 300000, 5, '9786048083021', 1),
(35, 'Thoát khỏi địa ngục Khmer đỏ - Hồi ký của một người còn sống', '7lmy4xhmjhgiqap0p6b2mt8ft12ju5pu.png', 'Sách Chính trị - Xã hội', 2019, 5, 'Denise Affonco', 4, 74000, 50000, 5, '9786045751718', 1),
(36, 'Điện Biên Phủ - Điểm hẹn lịch sử', '240bb8a0096e82c9769587fdb0ccfe2a.jpg', 'Sách Chính trị - Xã hội', 2018, 15, 'Đại tướng Võ Nguyên Giáp', 4, 53000, 30000, 5, '9786048030742', 1),
(37, 'Sử liệu cổ nhạc Việt Nam', 'kzaj4gc27vz9pguxepwe3uoz630caym2.jpeg', 'Sách Chính trị - Xã hội', 2020, 19, 'Đặng Hoành Loan', 4, 405000, 30000, 5, '9786047029396', 1),
(38, 'Sự sinh thành Việt Nam', '7p1zz1z2gdgxk3f7p62nrd0xdn24sn0x.jpeg', 'Sách Chính trị - Xã hội', 2018, 16, 'GS. Hà Văn Tấn', 4, 96000, 30000, 5, '9786047730087', 1),
(39, 'Người Dao Tiền ở Việt Nam', 'lc205cd61ud39xfvfom6lqxbeu8rklw5.jpeg', 'Sách Chính trị - Xã hội', 2021, 17, 'Lý Hành Sơn', 4, 157000, 100000, 5, '9786043086072', 1),
(40, 'Tôn tử binh pháp', 'wufet92dnkp0jt7yzehtazcyvrpnhll3.jpeg', 'Sách Chính trị - Xã hội', 2019, 20, 'Tôn Tử', 4, 64000, 30000, 5, '8935235222564', 1),
(41, '5 đường mòn Hồ Chí Minh', '48a0hx2ovces506vnslpveb0qjcy6gi9.jpeg', 'Sách Chính trị - Xã hội', 2020, 15, 'Đặng Phong', 4, 161000, 100000, 5, '9786048049669', 1),
(42, 'Việt Nam bản hùng ca giữ nước', '363454f37c5e79344b2a87e4d0155e7e.png', 'Sách Chính trị - Xã hội', 2021, 15, 'Đặng Văn Việt', 4, 256000, 100000, 5, '9786048052508', 1),
(43, 'Bất khuất Mường Lò', 'w1xynsdkve2rv9k58gdkn8eixbvulr5y.jpeg', 'Sách Chính trị - Xã hội', 2023, 19, 'Trần Cao Đàm', 4, 108000, 50000, 5, '9786047035649', 1),
(44, 'Nếm trải Điện Biên', 'hgnpj4w7mbutt0tg9pjbsa8eu15q9k3a.jpeg', 'Sách Chính trị - Xã hội', 2018, 15, 'Cao Tiến Lê', 4, 33000, 10000, 5, '9786048032661', 1),
(45, 'Đường Bác Hồ Đi Cứu Nước', 'oqzeqlleza3c8550w5jjg54kvloow7oy.jpeg', 'Sách Chính trị - Xã hội', 2021, 5, 'GS.TS. Trình Quang Phú', 4, 148000, 100000, 5, '9786045767559', 1),
(46, 'Ký ức chiến trận - Quảng Trị 1972 - 2022 (Bìa cứng) - Nguyễn Xuân Vượng', 'ds7l546w53f0otq26c67em4mle8xoszq.jpeg', 'Sách Chính trị - Xã hội', 2022, 10, 'Nguyễn Xuân Vượng', 4, 160000, 100000, 5, '9786043566628', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `MNV` int(11) NOT NULL COMMENT 'Mã nhân viên',
  `MK` varchar(255) NOT NULL COMMENT 'Mật khẩu',
  `TDN` varchar(255) NOT NULL COMMENT 'Tên đăng nhập',
  `MNQ` int(11) NOT NULL COMMENT 'Mã nhóm quyền',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái',
  `OTP` varchar(50) DEFAULT NULL COMMENT 'Mã OTP'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`MNV`, `MK`, `TDN`, `MNQ`, `TT`, `OTP`) VALUES
(1, '$2a$12$6GSkiQ05XjTRvCW9MB6MNuf7hOJEbbeQx11Eb8oELil1OrCq6uBXm', 'admin', 1, 1, 'null'),
(2, '$2a$12$6GSkiQ05XjTRvCW9MB6MNuf7hOJEbbeQx11Eb8oELil1OrCq6uBXm', 'NV2', 2, 1, 'null'),
(3, '$2a$12$6GSkiQ05XjTRvCW9MB6MNuf7hOJEbbeQx11Eb8oELil1OrCq6uBXm', 'NV3', 3, 1, 'null'),
(4, '$2a$12$4d4ksJo3HRLxs5id9Zk29ukXK5XmhcRsNAR/uc4tNGYmr.wq31BsK', 'nvienkho', 3, 0, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoankh`
--

CREATE TABLE `taikhoankh` (
  `MKH` int(11) NOT NULL COMMENT 'Mã khách hàng',
  `MK` varchar(255) NOT NULL COMMENT 'Mật khẩu',
  `TDN` varchar(255) NOT NULL COMMENT 'Tên đăng nhập',
  `MNQ` int(11) NOT NULL COMMENT 'Mã nhóm quyền',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái',
  `OTP` varchar(50) DEFAULT NULL COMMENT 'Mã OTP'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoankh`
--

INSERT INTO `taikhoankh` (`MKH`, `MK`, `TDN`, `MNQ`, `TT`, `OTP`) VALUES
(1, '$2a$12$6GSkiQ05XjTRvCW9MB6MNuf7hOJEbbeQx11Eb8oELil1OrCq6uBXm', 'KH1', 4, 1, 'null'),
(19, '123123123', 'hoanghoan', 4, 1, NULL),
(20, '123123123', 'trang123', 4, 1, NULL);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `ctgiohang`
--
ALTER TABLE `ctgiohang`
  ADD PRIMARY KEY (`MKH`,`MSP`),
  ADD KEY `FK_MSP_CTGIOHANG` (`MSP`),
  ADD KEY `FK_MKM_CTGIOHANG` (`MKM`);

--
-- Chỉ mục cho bảng `ctmakhuyenmai`
--
ALTER TABLE `ctmakhuyenmai`
  ADD PRIMARY KEY (`MKM`,`MSP`),
  ADD KEY `FK_MSP_CTMAKHUYENMAI` (`MSP`);

--
-- Chỉ mục cho bảng `ctphieunhap`
--
ALTER TABLE `ctphieunhap`
  ADD PRIMARY KEY (`MPN`,`MSP`),
  ADD KEY `FK_MSP_CTPHIEUNHAP` (`MSP`);

--
-- Chỉ mục cho bảng `ctphieuxuat`
--
ALTER TABLE `ctphieuxuat`
  ADD PRIMARY KEY (`MPX`,`MSP`),
  ADD KEY `FK_MSP_CTPHIEUXUAT` (`MSP`),
  ADD KEY `FK_MKM_CTPHIEUXUAT` (`MKM`);

--
-- Chỉ mục cho bảng `ctquyen`
--
ALTER TABLE `ctquyen`
  ADD PRIMARY KEY (`MNQ`,`MCN`,`HANHDONG`),
  ADD KEY `FK_MCN_CTQUYEN` (`MCN`);

--
-- Chỉ mục cho bảng `danhmucchucnang`
--
ALTER TABLE `danhmucchucnang`
  ADD PRIMARY KEY (`MCN`);

--
-- Chỉ mục cho bảng `giohang`
--
ALTER TABLE `giohang`
  ADD PRIMARY KEY (`MKH`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MKH`),
  ADD UNIQUE KEY `SDT` (`SDT`),
  ADD UNIQUE KEY `EMAIL` (`EMAIL`);

--
-- Chỉ mục cho bảng `khuvucsach`
--
ALTER TABLE `khuvucsach`
  ADD PRIMARY KEY (`MKVS`);

--
-- Chỉ mục cho bảng `makhuyenmai`
--
ALTER TABLE `makhuyenmai`
  ADD PRIMARY KEY (`MKM`),
  ADD KEY `FK_MNV_MAKHUYENMAI` (`MNV`);

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`MNV`),
  ADD UNIQUE KEY `EMAIL` (`EMAIL`);

--
-- Chỉ mục cho bảng `nhaxuatban`
--
ALTER TABLE `nhaxuatban`
  ADD PRIMARY KEY (`MNXB`);

--
-- Chỉ mục cho bảng `nhomquyen`
--
ALTER TABLE `nhomquyen`
  ADD PRIMARY KEY (`MNQ`);

--
-- Chỉ mục cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`MPN`),
  ADD KEY `FK_MNV_PHIEUNHAP` (`MNV`);

--
-- Chỉ mục cho bảng `phieuxuat`
--
ALTER TABLE `phieuxuat`
  ADD PRIMARY KEY (`MPX`),
  ADD KEY `FK_MNV_PHIEUXUAT` (`MNV`),
  ADD KEY `FK_MKH_PHIEUXUAT` (`MKH`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`MSP`),
  ADD UNIQUE KEY `ISBN` (`ISBN`),
  ADD KEY `FK_MNXB_SANPHAM` (`MNXB`),
  ADD KEY `FK_MKVS_SANPHAM` (`MKVS`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`MNV`,`TDN`),
  ADD UNIQUE KEY `TDN` (`TDN`),
  ADD KEY `FK_MNQ_TAIKHOAN` (`MNQ`);

--
-- Chỉ mục cho bảng `taikhoankh`
--
ALTER TABLE `taikhoankh`
  ADD PRIMARY KEY (`MKH`,`TDN`),
  ADD UNIQUE KEY `TDN` (`TDN`),
  ADD KEY `FK_MNQ_TAIKHOANKH` (`MNQ`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `MKH` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã khách hàng', AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT cho bảng `khuvucsach`
--
ALTER TABLE `khuvucsach`
  MODIFY `MKVS` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã khu vực sách', AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  MODIFY `MNV` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã nhân viên', AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `nhaxuatban`
--
ALTER TABLE `nhaxuatban`
  MODIFY `MNXB` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã nhà xuất bản', AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT cho bảng `nhomquyen`
--
ALTER TABLE `nhomquyen`
  MODIFY `MNQ` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã nhóm quyền', AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `MPN` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã phiếu nhập', AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `phieuxuat`
--
ALTER TABLE `phieuxuat`
  MODIFY `MPX` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã phiếu xuất', AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `MSP` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã sản phẩm', AUTO_INCREMENT=47;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `ctgiohang`
--
ALTER TABLE `ctgiohang`
  ADD CONSTRAINT `FK_MKH_CTGIOHANG` FOREIGN KEY (`MKH`) REFERENCES `giohang` (`MKH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MKM_CTGIOHANG` FOREIGN KEY (`MKM`) REFERENCES `makhuyenmai` (`MKM`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MSP_CTGIOHANG` FOREIGN KEY (`MSP`) REFERENCES `sanpham` (`MSP`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `ctmakhuyenmai`
--
ALTER TABLE `ctmakhuyenmai`
  ADD CONSTRAINT `FK_MKM_CTMAKHUYENMAI` FOREIGN KEY (`MKM`) REFERENCES `makhuyenmai` (`MKM`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MSP_CTMAKHUYENMAI` FOREIGN KEY (`MSP`) REFERENCES `sanpham` (`MSP`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `ctphieunhap`
--
ALTER TABLE `ctphieunhap`
  ADD CONSTRAINT `FK_MPN_CTPHIEUNHAP` FOREIGN KEY (`MPN`) REFERENCES `phieunhap` (`MPN`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MSP_CTPHIEUNHAP` FOREIGN KEY (`MSP`) REFERENCES `sanpham` (`MSP`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `ctphieuxuat`
--
ALTER TABLE `ctphieuxuat`
  ADD CONSTRAINT `FK_MKM_CTPHIEUXUAT` FOREIGN KEY (`MKM`) REFERENCES `makhuyenmai` (`MKM`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MPX_CTPHIEUXUAT` FOREIGN KEY (`MPX`) REFERENCES `phieuxuat` (`MPX`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MSP_CTPHIEUXUAT` FOREIGN KEY (`MSP`) REFERENCES `sanpham` (`MSP`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `ctquyen`
--
ALTER TABLE `ctquyen`
  ADD CONSTRAINT `FK_MCN_CTQUYEN` FOREIGN KEY (`MCN`) REFERENCES `danhmucchucnang` (`MCN`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MNQ_CTQUYEN` FOREIGN KEY (`MNQ`) REFERENCES `nhomquyen` (`MNQ`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `giohang`
--
ALTER TABLE `giohang`
  ADD CONSTRAINT `FK_MKH_GIOHANG` FOREIGN KEY (`MKH`) REFERENCES `khachhang` (`MKH`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `makhuyenmai`
--
ALTER TABLE `makhuyenmai`
  ADD CONSTRAINT `FK_MNV_MAKHUYENMAI` FOREIGN KEY (`MNV`) REFERENCES `nhanvien` (`MNV`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD CONSTRAINT `FK_MNV_PHIEUNHAP` FOREIGN KEY (`MNV`) REFERENCES `nhanvien` (`MNV`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `phieuxuat`
--
ALTER TABLE `phieuxuat`
  ADD CONSTRAINT `FK_MKH_PHIEUXUAT` FOREIGN KEY (`MKH`) REFERENCES `khachhang` (`MKH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MNV_PHIEUXUAT` FOREIGN KEY (`MNV`) REFERENCES `nhanvien` (`MNV`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `FK_MKVS_SANPHAM` FOREIGN KEY (`MKVS`) REFERENCES `khuvucsach` (`MKVS`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MNXB_SANPHAM` FOREIGN KEY (`MNXB`) REFERENCES `nhaxuatban` (`MNXB`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD CONSTRAINT `FK_MNQ_TAIKHOAN` FOREIGN KEY (`MNQ`) REFERENCES `nhomquyen` (`MNQ`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MNV_TAIKHOAN` FOREIGN KEY (`MNV`) REFERENCES `nhanvien` (`MNV`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `taikhoankh`
--
ALTER TABLE `taikhoankh`
  ADD CONSTRAINT `FK_MKH_TAIKHOANKH` FOREIGN KEY (`MKH`) REFERENCES `khachhang` (`MKH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MNQ_TAIKHOANKH` FOREIGN KEY (`MNQ`) REFERENCES `nhomquyen` (`MNQ`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
