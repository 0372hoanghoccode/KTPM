-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 07, 2024 at 04:45 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quanlycuahangsach`
--

-- --------------------------------------------------------

--
-- Table structure for table `ctgiohang`
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
-- Table structure for table `ctmakhuyenmai`
--

CREATE TABLE `ctmakhuyenmai` (
  `MKM` varchar(255) NOT NULL COMMENT 'Mã khuyến mãi',
  `MSP` int(11) NOT NULL COMMENT 'Mã sản phẩm',
  `PTG` int(11) NOT NULL COMMENT 'Phần trăm giảm'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ctmakhuyenmai`
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
-- Table structure for table `ctphieukiemke`
--

CREATE TABLE `ctphieukiemke` (
  `MPKK` int(11) NOT NULL COMMENT 'Mã phiếu kiểm kê',
  `MSP` int(11) NOT NULL COMMENT 'Mã sản phẩm',
  `TRANGTHAISP` int(11) NOT NULL COMMENT 'Trạng thái sản phẩm',
  `GHICHU` varchar(255) DEFAULT NULL COMMENT 'Ghi chú'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ctphieukiemke`
--

INSERT INTO `ctphieukiemke` (`MPKK`, `MSP`, `TRANGTHAISP`, `GHICHU`) VALUES
(1, 1, 1, 'Hư');

-- --------------------------------------------------------

--
-- Table structure for table `ctphieunhap`
--

CREATE TABLE `ctphieunhap` (
  `MPN` int(11) NOT NULL COMMENT 'Mã phiếu nhập',
  `MSP` int(11) NOT NULL COMMENT 'Mã sản phẩm',
  `SL` int(11) NOT NULL COMMENT 'Số lượng',
  `TIENNHAP` int(11) NOT NULL COMMENT 'Tiền nhập',
  `HINHTHUC` int(11) NOT NULL DEFAULT 0 COMMENT 'Tổng tiền'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ctphieunhap`
--

INSERT INTO `ctphieunhap` (`MPN`, `MSP`, `SL`, `TIENNHAP`, `HINHTHUC`) VALUES
(1, 1, 2, 20000, 0),
(1, 2, 2, 40000, 0),
(2, 3, 2, 40000, 0),
(2, 4, 2, 80000, 0);

-- --------------------------------------------------------

--
-- Table structure for table `ctphieutra`
--

CREATE TABLE `ctphieutra` (
  `MPX` int(11) NOT NULL COMMENT 'Mã phiếu xuất',
  `MSP` int(11) NOT NULL COMMENT 'Mã sản phẩm',
  `SL` int(11) NOT NULL COMMENT 'Số lượng',
  `TIENTHU` int(11) NOT NULL COMMENT 'Tiền thu',
  `LYDO` varchar(255) NOT NULL COMMENT 'Lý do'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ctphieutra`
--

INSERT INTO `ctphieutra` (`MPX`, `MSP`, `SL`, `TIENTHU`, `LYDO`) VALUES
(1, 1, 2, 100000, 'Hư');

-- --------------------------------------------------------

--
-- Table structure for table `ctphieuxuat`
--

CREATE TABLE `ctphieuxuat` (
  `MPX` int(11) NOT NULL COMMENT 'Mã phiếu xuất',
  `MSP` int(11) NOT NULL COMMENT 'Mã sản phẩm',
  `MKM` varchar(255) DEFAULT NULL COMMENT 'Mã khuyến mãi',
  `SL` int(11) NOT NULL COMMENT 'Số lượng',
  `TIENXUAT` int(11) NOT NULL COMMENT 'Tiền xuất'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ctphieuxuat`
--

INSERT INTO `ctphieuxuat` (`MPX`, `MSP`, `MKM`, `SL`, `TIENXUAT`) VALUES
(1, 1, NULL, 2, 100000),
(1, 2, NULL, 2, 200000),
(2, 3, NULL, 2, 300000),
(2, 4, NULL, 2, 400000);

-- --------------------------------------------------------

--
-- Table structure for table `ctquyen`
--

CREATE TABLE `ctquyen` (
  `MNQ` int(11) NOT NULL COMMENT 'Mã nhóm quyền',
  `MCN` varchar(50) NOT NULL COMMENT 'Mã chức năng',
  `HANHDONG` varchar(255) NOT NULL COMMENT 'Hành động thực hiện'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ctquyen`
--

INSERT INTO `ctquyen` (`MNQ`, `MCN`, `HANHDONG`) VALUES
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
-- Table structure for table `danhmucchucnang`
--

CREATE TABLE `danhmucchucnang` (
  `MCN` varchar(50) NOT NULL COMMENT 'Mã chức năng',
  `TEN` varchar(255) NOT NULL COMMENT 'Tên chức năng',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `danhmucchucnang`
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
-- Table structure for table `giohang`
--

CREATE TABLE `giohang` (
  `MKH` int(11) NOT NULL COMMENT 'Mã khách hàng',
  `TIEN` int(11) NOT NULL COMMENT 'Tổng tiền',
  `TG` datetime DEFAULT current_timestamp() COMMENT 'Thời gian tạo',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
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
-- Dumping data for table `khachhang`
--

INSERT INTO `khachhang` (`MKH`, `HOTEN`, `NGAYTHAMGIA`, `DIACHI`, `SDT`, `EMAIL`, `TT`) VALUES
(1, 'Phạm Văn Bình', '2024-06-01', '123 Lý Tự Trọng, Quận 1, TP. Hồ Chí Minh', '0912345678', NULL, 1),
(2, 'Nguyễn Thị Hoa', '2024-06-15', '456 Nguyễn Trãi, Quận Thanh Xuân, Hà Nội', '0987654321', NULL, 1),
(3, 'Lê Quốc Cường', '2024-07-05', '789 Trần Quang Khải, Quận 5, TP. Hồ Chí Minh', '0931234567', NULL, 1),
(4, 'Trần Minh Nhật', '2024-07-20', '12 Phan Bội Châu, Thành phố Huế', '0945678901', NULL, 1),
(5, 'Đỗ Thị Mai', '2024-08-10', '34 Nguyễn Huệ, Thành phố Đà Nẵng', '0956789012', NULL, 1),
(6, 'Lý Thanh Huyền', '2024-06-25', '55 Phạm Văn Đồng, Quận Bắc Từ Liêm, Hà Nội', '0967890123', NULL, 1),
(7, 'Ngô Hữu Tài', '2024-07-18', '77 Trần Hưng Đạo, Quận Hoàn Kiếm, Hà Nội', '0978901234', NULL, 1),
(8, 'Vũ Minh Khang', '2024-08-02', '88 Lý Thường Kiệt, Quận Hải Châu, Đà Nẵng', '0989012345', NULL, 1),
(9, 'Bùi Thị Lan', '2024-06-12', '99 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', '0911234567', NULL, 1),
(10, 'Trần Anh Tuấn', '2024-07-25', '123 Võ Văn Kiệt, Thành phố Cần Thơ', '0902345678', NULL, 1),
(11, 'Nguyễn Văn Hùng', '2024-08-15', '456 Điện Biên Phủ, Thành phố Vinh, Nghệ An', '0945123456', NULL, 1),
(12, 'Hoàng Thu Hằng', '2024-06-30', '789 Tô Hiến Thành, Thành phố Nha Trang', '0923456789', NULL, 1),
(13, 'Phạm Văn Minh', '2024-07-11', '12 Cao Thắng, Quận 10, TP. Hồ Chí Minh', '0931230987', NULL, 1),
(14, 'Lê Thanh Phong', '2024-08-08', '34 Hai Bà Trưng, Quận 1, TP. Hồ Chí Minh', '0910987654', NULL, 1),
(15, 'Nguyễn Hoài Nam', '2024-07-30', '56 Trần Phú, Thành phố Đà Lạt', '0923451098', NULL, 1),
(16, 'Lý Thị Thu', '2024-08-25', '78 Đặng Văn Ngữ, Thành phố Pleiku', '0912348765', NULL, 1),
(17, 'Trần Đức Thắng', '2024-06-22', '90 Nguyễn Văn Cừ, Thành phố Đà Nẵng', '0908765432', NULL, 1),
(18, 'Võ Quang Hải', '2024-07-10', '56 Hoàng Diệu, Quận Hải Châu, Đà Nẵng', '0916543212', NULL, 1),
(19, 'Nguyễn Hải Dương', '2024-08-05', '123 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', '0935123409', NULL, 1),
(20, 'Phạm Thị Bích', '2024-07-28', '321 Bạch Đằng, Quận Hải Châu, Đà Nẵng', '0945123410', NULL, 1),
(21, 'Trần Văn Minh', '2024-08-12', '789 Nguyễn Văn Linh, Quận 7, TP. Hồ Chí Minh', '0912345610', NULL, 1),
(22, 'Nguyễn Thị Hồng', '2024-08-10', '45 Lý Tự Trọng, Quận 1, TP. Hồ Chí Minh', '0922345611', NULL, 1),
(23, 'Lê Văn Hoàng', '2024-08-09', '123 Trần Phú, Quận 5, TP. Hồ Chí Minh', '0932345612', NULL, 1),
(24, 'Nguyễn Thị Kim', '2024-08-08', '32 Phan Đăng Lưu, Quận Bình Thạnh, TP. Hồ Chí Minh', '0942345613', NULL, 1),
(25, 'Phạm Văn Toàn', '2024-08-07', '654 Lê Văn Sỹ, Quận 3, TP. Hồ Chí Minh', '0952345614', NULL, 1),
(26, 'Nguyễn Văn Thắng', '2024-08-06', '89 Võ Thị Sáu, Quận 3, TP. Hồ Chí Minh', '0962345615', NULL, 1),
(27, 'Lê Thị Mai', '2024-08-05', '123 Cộng Hòa, Quận Tân Bình, TP. Hồ Chí Minh', '0972345616', NULL, 1),
(28, 'Trần Quốc Anh', '2024-08-04', '456 Trần Hưng Đạo, Quận 1, TP. Hồ Chí Minh', '0982345617', NULL, 1),
(29, 'Nguyễn Văn Tú', '2024-08-03', '789 Đinh Tiên Hoàng, Quận Bình Thạnh, TP. Hồ Chí Minh', '0913345618', NULL, 1),
(30, 'Lê Văn Đức', '2024-08-02', '987 Trường Chinh, Quận Tân Bình, TP. Hồ Chí Minh', '0923345619', NULL, 1),
(31, 'Nguyễn Thị Bích', '2024-08-01', '23 Lê Lợi, Quận 1, TP. Hồ Chí Minh', '0933345620', NULL, 1),
(32, 'Phạm Văn Thanh', '2024-07-31', '654 Nguyễn Trãi, Quận 5, TP. Hồ Chí Minh', '0943345621', NULL, 1),
(33, 'Trần Thị Hoa', '2024-07-30', '789 Lê Quang Định, Quận Bình Thạnh, TP. Hồ Chí Minh', '0953345622', NULL, 1),
(34, 'Nguyễn Văn Khoa', '2024-07-29', '321 Phạm Ngũ Lão, Quận 1, TP. Hồ Chí Minh', '0963345623', NULL, 1),
(35, 'Lê Thị Ngọc', '2024-07-28', '123 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', '0973345624', NULL, 1),
(36, 'Phạm Văn Dũng', '2024-07-27', '567 Điện Biên Phủ, Quận 3, TP. Hồ Chí Minh', '0983345625', NULL, 1),
(37, 'Nguyễn Thị Hạnh', '2024-07-26', '789 Cách Mạng Tháng 8, Quận 10, TP. Hồ Chí Minh', '0914345626', NULL, 1),
(38, 'Trần Văn Tâm', '2024-07-25', '321 Võ Văn Kiệt, Quận 1, TP. Hồ Chí Minh', '0924345627', NULL, 1),
(39, 'Lê Thị Thu', '2024-07-24', '987 Nguyễn Thái Học, Quận 1, TP. Hồ Chí Minh', '0934345628', NULL, 1),
(40, 'Nguyễn Văn Bình', '2024-07-23', '456 Hoàng Sa, Quận 3, TP. Hồ Chí Minh', '0944345629', NULL, 1),
(41, 'Phạm Văn Khánh', '2024-07-22', '123 Nguyễn Trãi, Quận 5, TP. Hồ Chí Minh', '0915145629', NULL, 1),
(42, 'Trần Thị Thanh', '2024-07-21', '987 Lê Hồng Phong, Quận 10, TP. Hồ Chí Minh', '0925145630', NULL, 1),
(43, 'Nguyễn Văn Long', '2024-07-20', '456 Cách Mạng Tháng 8, Quận 3, TP. Hồ Chí Minh', '0935145631', NULL, 1),
(44, 'Lê Thị Hương', '2024-07-19', '789 Phạm Văn Đồng, Quận Gò Vấp, TP. Hồ Chí Minh', '0945145632', NULL, 1),
(45, 'Phạm Văn Hà', '2024-07-18', '123 Điện Biên Phủ, Quận 10, TP. Hồ Chí Minh', '0955145633', NULL, 1),
(46, 'Nguyễn Văn Lợi', '2024-07-17', '456 Lê Quang Định, Quận Bình Thạnh, TP. Hồ Chí Minh', '0965145634', NULL, 1),
(47, 'Trần Thị Lan', '2024-07-16', '987 Võ Văn Kiệt, Quận 1, TP. Hồ Chí Minh', '0975145635', NULL, 1),
(48, 'Lê Văn Tùng', '2024-07-15', '321 Phan Đăng Lưu, Quận Phú Nhuận, TP. Hồ Chí Minh', '0985145636', NULL, 1),
(49, 'Nguyễn Thị Vân', '2024-07-14', '123 Lý Thường Kiệt, Quận 11, TP. Hồ Chí Minh', '0916145637', NULL, 1),
(50, 'Trần Văn Hùng', '2024-07-13', '456 Hùng Vương, Quận 6, TP. Hồ Chí Minh', '0926145638', NULL, 1),
(51, 'Lê Thị Lý', '2024-07-12', '789 Nguyễn Oanh, Quận Gò Vấp, TP. Hồ Chí Minh', '0936145639', NULL, 1),
(52, 'Phạm Văn Lâm', '2024-07-11', '123 Phan Văn Trị, Quận Gò Vấp, TP. Hồ Chí Minh', '0946145640', NULL, 1),
(53, 'Nguyễn Thị Liên', '2024-07-10', '321 Nguyễn Đình Chiểu, Quận 3, TP. Hồ Chí Minh', '0956145641', NULL, 1),
(54, 'Trần Văn Phúc', '2024-07-09', '987 Lê Văn Sỹ, Quận Phú Nhuận, TP. Hồ Chí Minh', '0966145642', NULL, 1),
(55, 'Lê Văn An', '2024-07-08', '456 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh', '0976145643', NULL, 1),
(56, 'Nguyễn Văn Duy', '2024-07-07', '789 Nguyễn Văn Linh, Quận 7, TP. Hồ Chí Minh', '0986145644', NULL, 1),
(57, 'Phạm Thị Yến', '2024-07-06', '123 Đinh Tiên Hoàng, Quận 1, TP. Hồ Chí Minh', '0917145645', NULL, 1),
(58, 'Trần Văn Toản', '2024-07-05', '456 Bạch Đằng, Quận Bình Thạnh, TP. Hồ Chí Minh', '0927145646', NULL, 1),
(59, 'Nguyễn Thị Thanh', '2024-07-04', '789 Lê Lợi, Quận 1, TP. Hồ Chí Minh', '0937145647', NULL, 1),
(60, 'Lê Văn Hùng', '2024-07-03', '123 Cách Mạng Tháng 8, Quận 10, TP. Hồ Chí Minh', '0947145648', NULL, 1),
(61, 'Phạm Thị Lan', '2024-06-28', 'Số 55 Quang Trung, Nguyễn Du, Hai Bà Trưng, Hà Nội', '0965333410', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `khuvucsach`
--

CREATE TABLE `khuvucsach` (
  `MKVS` int(11) NOT NULL COMMENT 'Mã khu vực sách',
  `TEN` varchar(255) NOT NULL COMMENT 'Tên khu vực sách',
  `GHICHU` varchar(255) DEFAULT '' COMMENT 'Ghi chú',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `khuvucsach`
--

INSERT INTO `khuvucsach` (`MKVS`, `TEN`, `GHICHU`, `TT`) VALUES
(1, 'Khu vực A', 'Sách dành cho giới trẻ', 1),
(2, 'Khu vực B', 'Văn học - Nghệ thuật', 1),
(3, 'Khu vực C', 'Văn học thiếu nhi', 1),
(4, 'Khu vực D', 'Sách Chính trị - Xã hội', 1);

-- --------------------------------------------------------

--
-- Table structure for table `makhuyenmai`
--

CREATE TABLE `makhuyenmai` (
  `MKM` varchar(255) NOT NULL COMMENT 'Mã khuyến mãi',
  `MNV` int(11) NOT NULL COMMENT 'Mã nhân viên',
  `TGBD` date NOT NULL COMMENT 'Thời gian bắt đầu',
  `TGKT` date NOT NULL COMMENT 'Thời gian kết thúc',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `makhuyenmai`
--

INSERT INTO `makhuyenmai` (`MKM`, `MNV`, `TGBD`, `TGKT`, `TT`) VALUES
('GT2024', 1, '2024-04-01', '2024-05-01', 1),
('MINGEY2024', 1, '2024-05-01', '2024-05-20', 1);

-- --------------------------------------------------------

--
-- Table structure for table `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `MNCC` int(11) NOT NULL COMMENT 'Mã nhà cung cấp',
  `TEN` varchar(255) NOT NULL COMMENT 'Tên NCC',
  `DIACHI` varchar(255) DEFAULT NULL COMMENT 'Địa chỉ',
  `SDT` varchar(12) DEFAULT NULL COMMENT 'Số điện thoại',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT 'Email',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nhacungcap`
--

INSERT INTO `nhacungcap` (`MNCC`, `TEN`, `DIACHI`, `SDT`, `EMAIL`, `TT`) VALUES
(1, 'NHÀ SÁCH CÁ CHÉP', 'Số 211-213 đường Võ Văn Tần, Phường 5, Quận 3, Thành phố Hồ Chí Minh', '024 3994 715', 'cskh@cachep.vn', 1),
(2, 'FAHASA', 'Số 387 – 389 đường Hai Bà Trưng, Phường 8, Quận 3, Thành phố Hồ Chí Minh', '1900 636467', 'contacts@fahasabooks.vn', 1),
(3, 'NHÃ NAM', 'Số 59, Đỗ Quang, Trung Hoà, Cầu Giấy, Hà Nội', '02435146876', '', 1),
(4, 'KIM ĐỒNG', 'Số 55 Quang Trung, Nguyễn Du, Hai Bà Trưng, Hà Nội', '1900571595', 'cskh_online@nxbkimdong.com.vn', 1),
(5, 'ARTBOOK', 'Số 1B1 Nguyễn Đình Chiểu, Phường Đa Kao, Quận 1, Thành phố Hồ Chí Minh', '0283 9101 14', 'info.artbooks@gmail.com', 1),
(6, 'ALPHA BOOKS', 'Số 138C đường Nguyễn Đình Chiểu, Phường 6, Quận 3, Thành phố Hồ Chí Minh.', '0932 329 959', 'salesonline@alphavn.com', 1);

-- --------------------------------------------------------

--
-- Table structure for table `nhanvien`
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
-- Dumping data for table `nhanvien`
--

INSERT INTO `nhanvien` (`MNV`, `HOTEN`, `GIOITINH`, `NGAYSINH`, `SDT`, `EMAIL`, `TT`) VALUES
(1, 'Nguyễn Mai Linh', 0, '1990-10-01', '0987654321', 'nguyenlinh@gmail.com', 1),
(2, 'Lê Huy Nam', 1, '1991-02-02', '0981234567', 'lenam@gmail.com', 1),
(3, 'Trần Mai Thảo', 0, '1992-03-03', '0976543210', 'tranthao@gmail.com', 1),
(4, 'Phạm Quang Huy', 1, '1993-04-04', '0965432109', 'phamhuy@gmail.com', 1),
(5, 'Lê Long Nghĩa', 1, '1994-05-05', '0954321098', 'lelongnghia@gmail.com', 1);

-- --------------------------------------------------------

--
-- Table structure for table `nhaxuatban`
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
-- Dumping data for table `nhaxuatban`
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
-- Table structure for table `nhomquyen`
--

CREATE TABLE `nhomquyen` (
  `MNQ` int(11) NOT NULL COMMENT 'Mã nhóm quyền',
  `TEN` varchar(255) NOT NULL COMMENT 'Tên nhóm quyền',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nhomquyen`
--

INSERT INTO `nhomquyen` (`MNQ`, `TEN`, `TT`) VALUES
(1, 'Quản lý cửa hàng', 1),
(2, 'Nhân viên bán hàng', 1),
(3, 'Nhân viên quản lý kho', 1),
(4, 'Khách hàng', 1),
(5, 'Người xem', 1);

-- --------------------------------------------------------

--
-- Table structure for table `phieukiemke`
--

CREATE TABLE `phieukiemke` (
  `MPKK` int(11) NOT NULL COMMENT 'Mã phiếu kiểm kê',
  `MNV` int(11) NOT NULL COMMENT 'Mã nhân viên',
  `TG` datetime DEFAULT current_timestamp() COMMENT 'Thời gian tạo',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `phieukiemke`
--

INSERT INTO `phieukiemke` (`MPKK`, `MNV`, `TG`, `TT`) VALUES
(1, 1, '2024-04-01 01:09:27', 1);

-- --------------------------------------------------------

--
-- Table structure for table `phieunhap`
--

CREATE TABLE `phieunhap` (
  `MPN` int(11) NOT NULL COMMENT 'Mã phiếu nhập',
  `MNV` int(11) NOT NULL COMMENT 'Mã nhân viên',
  `MNCC` int(11) NOT NULL COMMENT 'Mã nhà cung cấp',
  `TIEN` int(11) NOT NULL COMMENT 'Tổng tiền',
  `TG` datetime DEFAULT current_timestamp() COMMENT 'Thời gian tạo',
  `TT` int(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `phieunhap`
--

INSERT INTO `phieunhap` (`MPN`, `MNV`, `MNCC`, `TIEN`, `TG`, `TT`) VALUES
(1, 1, 1, 100000, '2024-04-01 01:09:27', 1),
(2, 1, 1, 200000, '2024-04-02 01:09:27', 1);

-- --------------------------------------------------------

--
-- Table structure for table `phieutra`
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
-- Dumping data for table `phieutra`
--

INSERT INTO `phieutra` (`MPX`, `MNV`, `MKH`, `TIEN`, `TG`, `TT`) VALUES
(1, 1, 1, 100000, '2024-04-20 17:34:12', 1);

-- --------------------------------------------------------

--
-- Table structure for table `phieuxuat`
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
-- Dumping data for table `phieuxuat`
--

INSERT INTO `phieuxuat` (`MPX`, `MNV`, `MKH`, `TIEN`, `TG`, `TT`) VALUES
(1, 1, 1, 100000, '2024-04-18 17:34:12', 1),
(2, 1, 2, 200000, '2024-04-17 18:19:51', 1);

-- --------------------------------------------------------

--
-- Table structure for table `sanpham`
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
-- Dumping data for table `sanpham`
--

INSERT INTO `sanpham` (`MSP`, `TEN`, `HINHANH`, `DANHMUC`, `NAMXB`, `MNXB`, `TENTG`, `MKVS`, `TIENX`, `TIENN`, `SL`, `ISBN`, `TT`) VALUES
(1, 'Cho Tôi Xin Một Vé Đi Tuổi Thơ', 'cho_toi_xin_mot_ve_di_tuoi_tho.jpeg', 'Văn học thiếu nhi', 2018, 1, 'Nguyễn Nhật Ánh', 3, 95000, 90000, 5, '9786042088501', 1),
(2, 'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', 'toi_thay_hoa_vang_tren_co_xanh.jpg', 'Văn học thiếu nhi', 2019, 5, 'Nguyễn Nhật Ánh', 3, 110000, 100000, 5, '9786042086402', 1),
(3, 'Tôi Là Bêtô', 'toi_la_beto.jpg', 'Văn học thiếu nhi', 2021, 6, 'Nguyễn Nhật Ánh', 3, 88000, 85000, 5, '9786042087403', 1),
(4, 'Cô Gái Đến Từ Hôm Qua', 'co_gai_den_tu_hom_qua.jpg', 'Văn học thiếu nhi', 2020, 4, 'Nguyễn Nhật Ánh', 3, 95000, 90000, 5, '9786042083404', 1),
(5, 'Con Chó Nhỏ Mang Giỏ Hoa Hồng', 'con_cho_nho_mang_gio_hoa_hong.jpg', 'Văn học thiếu nhi', 2022, 7, 'Nguyễn Nhật Ánh', 3, 120000, 115000, 5, '9786042087805', 1),
(6, 'Có Hai Con Mèo Ngồi Bên Cửa Sổ', 'co_hai_con_meo_ngoi_ben_cua_so.jpg', 'Văn học thiếu nhi', 2019, 2, 'Nguyễn Nhật Ánh', 3, 105000, 100000, 5, '9786042083406', 1),
(7, 'Mắt Biếc', 'mat_biec.jpg', 'Văn học thiếu nhi', 2021, 8, 'Nguyễn Nhật Ánh', 3, 100000, 95000, 5, '9786042086907', 1),
(8, 'Chúc Một Ngày Tốt Lành', 'chuc_mot_ngay_tot_lanh.jpg', 'Văn học thiếu nhi', 2020, 9, 'Nguyễn Nhật Ánh', 3, 95000, 90000, 5, '9786042082308', 1),
(9, 'Ngồi Khóc Trên Cây', 'ngoi_khoc_tren_cay.jpg', 'Văn học thiếu nhi', 2022, 11, 'Nguyễn Nhật Ánh', 3, 105000, 100000, 5, '9786042085309', 1),
(10, 'Ngày Xưa Có Một Chuyện Tình', 'ngay_xua_co_mot_chuyen_tinh.jpg', 'Văn học thiếu nhi', 2021, 3, 'Nguyễn Nhật Ánh', 3, 115000, 110000, 5, '9786042289510', 1),
(11, 'Ám thị tâm lý', 'kogoiob1cgjlqhndkc0dcw1hzj1kqook.png', 'Sách dành cho giới trẻ', 2022, 21, 'Patrick King - Huy Nguyễn', 1, 134000, 100000, 5, '9786046863710', 1),
(12, 'Không gì là không thể', 'xtx0d0apioa66abawbnpybv4v8wsb54p.png', 'Sách dành cho giới trẻ', 2020, 3, 'George Matthew Adams', 1, 63500, 60000, 5, '8935086837611', 1),
(13, 'Tư duy ngược', '050d67e2d58f5e291cbf25da53f55ef7.jpg', 'Sách dành cho giới trẻ', 2020, 10, 'Nguyễn Anh Dũng', 1, 125000, 100000, 5, '9786043440212', 1),
(14, 'Bạn đắt giá bao nhiêu?', 'txq47334nnwj1cw5f5ux7egme8erj2k7.jpg', 'Sách dành cho giới trẻ', 2021, 9, 'Vãn Tình', 1, 107000, 100000, 5, '8936186543513', 1),
(15, 'Hãy gọi tên tôi', '90adcd1cc9b81aa80a272a653c7785e5.png', 'Sách dành cho giới trẻ', 2020, 11, 'Chanel Miller', 1, 118000, 100000, 5, '9786046863714', 1),
(16, 'Đời ngắn đừng ngủ dài', 'q9rcr9y1ax8gb1u372kxb0eivlg2xegc.jpeg', 'Sách dành cho giới trẻ', 2020, 2, 'Robin Sharma', 1, 64000, 60000, 5, '8934974158615', 1),
(17, 'Tội ác sau những bức tranh', 'rl7k9yn47k492vlxrbbnx9jmdy3ps89x.jpeg', 'Văn học - Nghệ thuật', 2022, 6, 'Jason Rekulak', 1, 146000, 100000, 5, '8934974158616', 1),
(18, 'Người rỗng', '93lh9xopdaqdtimbzosmo3n3h3evhmcv.jpeg', 'Văn học - Nghệ thuật', 2020, 22, 'John Dickson Carr', 2, 70000, 60000, 2, '9786049847217', 1),
(19, 'Tạp văn Nguyễn Ngọc Tư', '4b1ea768cbbef544c4ca16fe1967634b.jpg', 'Văn học - Nghệ thuật', 2021, 2, 'Nguyễn Ngọc Tư', 2, 72000, 60000, 5, '8934974168618', 1),
(20, 'Bông sen vàng', 'ythnuw9ks3kk4l0lb1pa6fdtoo1tz32a.jpeg', 'Văn học - Nghệ thuật', 2022, 5, 'Sơn Tùng', 2, 144000, 60000, 5, '8934974168619', 1),
(21, 'Hoa tuylip đen', 'sbgtbeq0vz1dhnag0qf3jrp6enxjgw20.jpeg', 'Văn học - Nghệ thuật', 2017, 22, 'Alexandre Dumas cha', 2, 49000, 30000, 5, '8936067597020', 1),
(22, 'Truyện ngụ ngôn E Dốp', '883868zq1vtv4qqm7plgby2g8k0i2mkv.jpeg', 'Văn học - Nghệ thuật', 2019, 22, 'Aesop', 2, 59000, 30000, 5, '9786049633121', 1),
(23, 'Nhật ký trong tù', 'cd19aa6163295ef2dff24012f78b9aec.jpg', 'Văn học - Nghệ thuật', 2021, 5, 'Hồ Chí Minh', 2, 105000, 50000, 5, '9786043238122', 1),
(24, 'Phận liễu', 'detep2imksxnh97om03avji0q1p47clp.jpeg', 'Văn học - Nghệ thuật', 2020, 12, 'Chu Thanh Hương', 2, 162000, 50000, 5, '9786047244323', 1),
(25, 'Đồng tiền hạnh phúc', '6619719473f6f132a18182f019364abf.jpg', 'Văn học - Nghệ thuật', 2020, 13, 'Ken Honda', 2, 85500, 50000, 5, '8935095629624', 1),
(26, 'Lũ trẻ thủy tinh', 'rnwjyi3x3zzxzjxlq4ba0zaeqpu0lugk.jpeg', 'Văn học thiếu nhi', 2021, 1, 'Kristina Ohlsson', 3, 28000, 10000, 5, '9786042190825', 1),
(27, 'Lũ trẻ đường ray', '22znw9gr4514dul0lc5rgisvc47qxfw6.jpeg', 'Văn học thiếu nhi', 2020, 22, 'Edith Nesbit', 3, 63000, 50000, 5, '9786049693426', 1),
(28, 'Tớ sợ cái đồng hồ', 'fs2xgxjq2yswm6l33gfv50mwg6mgu4qm.jpeg', 'Văn học thiếu nhi', 2018, 14, 'Nguyễn Quỳnh Mai', 3, 52000, 30000, 5, '9786045653027', 1),
(29, 'Khu rừng trong thành phố', 'cmpq0o6lb0jqmna7f9n2k7y61z84if0u.jpeg', 'Văn học thiếu nhi', 2018, 14, 'Nguyễn Quỳnh Mai', 3, 58000, 30000, 5, '9786045653028', 1),
(30, 'Đảo ngàn sao', '5hghrc3synmydygcyzwsl7noz3pt7u31.jpeg', 'Văn học thiếu nhi', 2021, 1, 'Emma Karinsdotter', 3, 48000, 30000, 5, '9786042221629', 1),
(31, 'Cậu bé bạc', 'ivssbyx4axf57c2tsww8kqyt1a4xauhv.jpeg', 'Văn học thiếu nhi', 2020, 1, 'Kristina Ohlsson', 3, 30000, 10000, 5, '9786042186330', 1),
(32, 'Ngựa ô yêu dấu', 'wf05ukmavgsdar1a772qc24c1iawtcfg.jpeg', 'Văn học thiếu nhi', 2022, 23, 'Anna Sewell', 3, 109000, 50000, 5, '9786043565031', 1),
(33, 'Chuyện con mèo dạy hải âu bay', 'iuj2x3gbldratw5xzjg9s1aterb66k8t.jpeg', 'Văn học thiếu nhi', 2019, 4, 'Luis Sepúlveda', 3, 42000, 10000, 5, '8935235222132', 1),
(34, 'Chú bé mang Pyjama sọc', 'o53krzx7g5du11thdcme6xl2uqd2s8gp.jpeg', 'Văn học thiếu nhi', 2018, 4, 'John Boyne', 3, 58000, 30000, 5, '8935235217833', 1),
(35, 'Những lá thư thời chiến Việt Nam (Tuyển tập)', '25ac83e2311e9fcaf146c655f672d6eb.jpg', 'Sách Chính trị - Xã hội', 2023, 5, 'Đặng Vương Hưng', 4, 144000, 100000, 5, '8935279148634', 1),
(36, 'Kỷ yếu Hoàng Sa', 'kyyeuhoangsa.jpg', 'Sách Chính trị - Xã hội', 2014, 15, 'UBND Huyện Hoàng Sa', 4, 153000, 100000, 5, '9786048002935', 1),
(37, 'Dấu ấn Việt Nam trên Biển Đông', '1odzbcuzqspuou03uwdnstegf6pe4jp6.jpeg', 'Sách Chính trị - Xã hội', 2012, 15, 'TS. Trần Công Trục', 4, 191000, 100000, 5, '9786048018736', 1),
(38, 'Chân dung Ngô Tất Tố', 'ytja13yxd96huojklmyy1cadwq4rykoi.jpeg', 'Sách Chính trị - Xã hội', 2014, 15, 'Cao Đắc Điểm - Ngô Thị Thanh Lịch', 4, 38000, 20000, 5, '9786048005237', 1),
(39, 'Chính sách đối ngoại đổi mới của Việt Nam (1986 - 2015)', 'eplg2rc40dd1zfp0wzp4zo5s0aok0khp.jpeg', 'Sách Chính trị - Xã hội', 2018, 16, 'PGS. TS. Phạm Quang Minh', 4, 56000, 30000, 5, '9786047744738', 1),
(40, 'Đặc trưng và vai trò của tầng lớp trung lưu ở Việt Nam', 'v4e1gkm2jgz2b7tdktk504lsgemrff59.jpeg', 'Sách Chính trị - Xã hội', 2022, 17, 'TS. Lê Kim Sa', 4, 81000, 70000, 5, '9786043089539', 1),
(41, 'Sức mạnh mềm văn hóa Trung Quốc thời Tập Cận Bình và ứng xử của Việt Nam', 'smvhchina.jpg', 'Sách Chính trị - Xã hội', 2022, 17, 'Ths.Chử Thị Bích Thu - TS.Trần Thị Thủy (Đồng chủ biên)', 4, 99000, 50000, 5, '97860430839440', 1),
(42, 'Đường tới Điện Biên Phủ', 'dbd44e00c80b8c79694bc2a87a36c20f.jpg', 'Sách Chính trị - Xã hội', 2018, 15, 'Đại tướng Võ Nguyên Giáp', 4, 47000, 30000, 5, '9786048030741', 1),
(43, 'Đường tới Truông Bồn huyền thoại', 'wofawcrdyttsuu37j3mh06i2m6ii0lq0.jpeg', 'Sách Chính trị - Xã hội', 2019, 18, 'Văn Hiền', 4, 150000, 100000, 5, '9786049642942', 1),
(44, 'Vương Dương Minh toàn thư', '8b90cf59071f3ed109d17770a0ec50ed.jpg', 'Sách Chính trị - Xã hội', 2023, 15, 'Túc Dịch Minh - Nguyễn Thanh Hải', 4, 443000, 300000, 5, '9786048083043', 1),
(45, 'Thoát khỏi địa ngục Khmer đỏ - Hồi ký của một người còn sống', '7lmy4xhmjhgiqap0p6b2mt8ft12ju5pu.png', 'Sách Chính trị - Xã hội', 2019, 5, 'Denise Affonco', 4, 74000, 50000, 5, '9786045751744', 1),
(46, 'Điện Biên Phủ - Điểm hẹn lịch sử', '240bb8a0096e82c9769587fdb0ccfe2a.jpg', 'Sách Chính trị - Xã hội', 2018, 15, 'Đại tướng Võ Nguyên Giáp', 4, 53000, 30000, 5, '9786048030745', 1),
(47, 'Sử liệu cổ nhạc Việt Nam', 'kzaj4gc27vz9pguxepwe3uoz630caym2.jpeg', 'Sách Chính trị - Xã hội', 2020, 19, 'Đặng Hoành Loan', 4, 405000, 30000, 5, '9786047029346', 1),
(48, 'Sự sinh thành Việt Nam', '7p1zz1z2gdgxk3f7p62nrd0xdn24sn0x.jpeg', 'Sách Chính trị - Xã hội', 2018, 16, 'GS. Hà Văn Tấn', 4, 96000, 30000, 5, '9786047730047', 1),
(49, 'Người Dao Tiền ở Việt Nam', 'lc205cd61ud39xfvfom6lqxbeu8rklw5.jpeg', 'Sách Chính trị - Xã hội', 2021, 17, 'Lý Hành Sơn', 4, 157000, 100000, 5, '9786043086048', 1),
(50, 'Tôn tử binh pháp', 'wufet92dnkp0jt7yzehtazcyvrpnhll3.jpeg', 'Sách Chính trị - Xã hội', 2019, 20, 'Tôn Tử', 4, 64000, 30000, 5, '8935235222549', 1),
(51, '5 đường mòn Hồ Chí Minh', '48a0hx2ovces506vnslpveb0qjcy6gi9.jpeg', 'Sách Chính trị - Xã hội', 2020, 15, 'Đặng Phong', 4, 161000, 100000, 5, '9786048049650', 1),
(52, 'Việt Nam bản hùng ca giữ nước', '363454f37c5e79344b2a87e4d0155e7e.png', 'Sách Chính trị - Xã hội', 2021, 15, 'Đặng Văn Việt', 4, 256000, 100000, 5, '9786048052551', 1),
(53, 'Bất khuất Mường Lò', 'w1xynsdkve2rv9k58gdkn8eixbvulr5y.jpeg', 'Sách Chính trị - Xã hội', 2023, 19, 'Trần Cao Đàm', 4, 108000, 50000, 5, '9786047035652', 1),
(54, 'Nếm trải Điện Biên', 'hgnpj4w7mbutt0tg9pjbsa8eu15q9k3a.jpeg', 'Sách Chính trị - Xã hội', 2018, 15, 'Cao Tiến Lê', 4, 33000, 10000, 5, '9786048032653', 1),
(55, 'Đường Bác Hồ Đi Cứu Nước', 'oqzeqlleza3c8550w5jjg54kvloow7oy.jpeg', 'Sách Chính trị - Xã hội', 2021, 5, 'GS.TS. Trình Quang Phú', 4, 148000, 100000, 5, '9786045767554', 1),
(56, 'Ký ức chiến trận - Quảng Trị 1972 - 2022 (Bìa cứng) - Nguyễn Xuân Vượng', 'ds7l546w53f0otq26c67em4mle8xoszq.jpeg', 'Sách Chính trị - Xã hội', 2022, 10, 'Nguyễn Xuân Vượng', 4, 160000, 100000, 5, '9786043566655', 1),
(57, 'Nhà Giả Kim', 'nhagiakim.jpg', 'Văn học - Nghệ thuật', 2018, 10, 'Paulo Coelho', 2, 86000, 80000, 5, '9786048934256', 1),
(58, 'Đắc Nhân Tâm', 'dacnhantam.jpg', 'Sách kỹ năng sống', 2021, 2, 'Dale Carnegie', 1, 79000, 75000, 5, '9786045874757', 1),
(59, 'Cách Nghĩ Để Thành Công', '39fed2e244.jpg', 'Sách kỹ năng sống', 2019, 5, 'Napoleon Hill', 1, 125000, 115000, 5, '9786045877858', 1),
(60, 'Hạt Giống Tâm Hồn', '9187a42c48.jpg', 'Sách kỹ năng sống', 2020, 7, 'Nhiều tác giả', 1, 98000, 95000, 5, '9786047787259', 1),
(61, 'Quẳng Gánh Lo Đi Và Vui Sống', '6d39e618e7.jpg', 'Sách kỹ năng sống', 2022, 4, 'Dale Carnegie', 1, 84000, 80000, 5, '9786045874760', 1),
(62, 'Đọc Vị Bất Kỳ Ai', 'dda18bf0f4.jpg', 'Sách kỹ năng sống', 2021, 6, 'David J. Lieberman', 1, 95000, 90000, 5, '9786045879861', 1),
(63, 'Bố Già', 'bo_gia_2d8f821de0.jpg', 'Tiểu thuyết', 2017, 9, 'Mario Puzo', 2, 120000, 100000, 5, '9786047736862', 1),
(64, 'Cuộc Sống Không Giới Hạn', '68a453691c.jpg', 'Sách kỹ năng sống', 2020, 3, 'Nick Vujicic', 1, 115000, 110000, 5, '9786047734463', 1),
(65, 'Đời Thay Đổi Khi Chúng Ta Thay Đổi', '36b80ea2e.jpg', 'Sách kỹ năng sống', 2021, 11, 'Andrew Matthews', 1, 88000, 80000, 5, '9786045865664', 1),
(66, 'Người Giàu Có Nhất Thành Babylon', 'e3e1ac2968.jpg', 'Sách tài chính', 2022, 12, 'George Samuel Clason', 1, 65000, 60000, 5, '9786048017565', 1),
(67, '7 Thói Quen Để Thành Đạt', 'eccc8473ce.jpg', 'Sách kỹ năng sống', 2020, 8, 'Stephen R. Covey', 1, 120000, 115000, 5, '9786047735666', 1),
(68, 'Hành Trình Về Phương Đông', 'b9d61fdb08.jpg', 'Văn học - Nghệ thuật', 2019, 2, 'Baird T. Spalding', 2, 99000, 90000, 5, '9786045232467', 1),
(69, 'Tội Ác Và Hình Phạt', '5735b91186.jpg', 'Văn học - Nghệ thuật', 2021, 3, 'Fyodor Dostoevsky', 2, 150000, 140000, 5, '9786045235668', 1),
(70, 'Người Bán Hàng Vĩ Đại Nhất Thế Giới', '3336c606e3.jpg', 'Sách kỹ năng sống', 2021, 14, 'Og Mandino', 1, 69000, 65000, 5, '9786045845669', 1),
(71, 'Nỗi Buồn Chiến Tranh', 'noi_buon_chien_tranh.jpeg', 'Văn học - Nghệ thuật', 2020, 2, 'Bảo Ninh', 2, 135000, 120000, 5, '9786049823470', 1),
(72, 'Số Đỏ', 'so_do.jpeg', 'Văn học - Nghệ thuật', 2019, 5, 'Vũ Trọng Phụng', 2, 120000, 100000, 5, '9786042086571', 1),
(73, 'Những Ngã Tư Và Những Cột Đèn', 'nhung_nga_tu_va_nhung_cot_den.jpeg', 'Văn học - Nghệ thuật', 2021, 6, 'Trần Dần', 2, 150000, 140000, 5, '9786042093472', 1),
(74, 'Sống Mòn', 'song_mon.jpeg', 'Văn học - Nghệ thuật', 2018, 8, 'Nam Cao', 2, 95000, 90000, 5, '9786049812473', 1),
(75, 'Tuổi Thơ Dữ Dội', 'tuoi_tho_du_doi.jpeg', 'Văn học - Nghệ thuật', 2020, 9, 'Phùng Quán', 2, 160000, 150000, 5, '9786042087674', 1),
(76, 'Thời Xa Vắng', 'thoi_xa_vang.jpeg', 'Văn học - Nghệ thuật', 2021, 4, 'Lê Lựu', 2, 130000, 120000, 5, '9786047785675', 1),
(77, 'Chùa Đàn', 'chua_dan.jpeg', 'Văn học - Nghệ thuật', 2019, 3, 'Nguyễn Tuân', 2, 115000, 110000, 5, '9786048033476', 1),
(78, 'Bỉ Vỏ', 'bi_vo.jpeg', 'Văn học - Nghệ thuật', 2020, 10, 'Nguyên Hồng', 2, 140000, 130000, 5, '9786047796577', 1),
(79, 'Tắt Đèn', 'tat_den.jpeg', 'Văn học - Nghệ thuật', 2021, 7, 'Ngô Tất Tố', 2, 95000, 90000, 5, '9786045667778', 1),
(80, 'Chuyện Kể Năm 2000', 'chuyen_ke_nam_2000.jpeg', 'Văn học - Nghệ thuật', 2022, 12, 'Bùi Ngọc Tấn', 2, 160000, 150000, 5, '9786047785479', 1),
(81, 'Đất Rừng Phương Nam', 'dat_rung_phuong_nam.jpeg', 'Văn học thiếu nhi', 2019, 5, 'Đoàn Giỏi', 3, 115000, 110000, 5, '9786042066780', 1),
(82, 'Cơ Hội Của Chúa', 'co_hoi_cua_chua.jpeg', 'Văn học - Nghệ thuật', 2021, 6, 'Nguyễn Việt Hà', 2, 125000, 120000, 5, '9786049832381', 1),
(83, 'Thiên Sứ', 'thien_su.jpeg', 'Văn học - Nghệ thuật', 2020, 8, 'Phạm Thị Hoài', 2, 130000, 125000, 5, '9786047723482', 1),
(84, 'Dế Mèn Phiêu Lưu Ký', 'de_men_phieu_luu_ky.jpeg', 'Văn học thiếu nhi', 2022, 11, 'Tô Hoài', 3, 90000, 85000, 5, '9786042082383', 1),
(85, 'Sông Côn Mùa Lũ', 'song_con_mua_lu.jpeg', 'Văn học - Nghệ thuật', 2020, 15, 'Nguyễn Mộng Giác', 2, 145000, 135000, 5, '9786047785684', 1),
(86, 'Bến Không Chồng', 'ben_khong_chong.jpeg', 'Văn học - Nghệ thuật', 2021, 4, 'Dương Hướng', 2, 125000, 120000, 5, '9786047784585', 1),
(87, 'Đêm Núm Sen', 'dem_num_sen.jpeg', 'Văn học - Nghệ thuật', 2019, 7, 'Trần Dần', 2, 115000, 110000, 5, '9786046773486', 1),
(88, 'Trư Cuồng', 'tru_cuong.jpeg', 'Văn học - Nghệ thuật', 2020, 6, 'Nguyễn Xuân Khánh', 2, 135000, 125000, 5, '9786047582387', 1),
(89, 'Giông Tố', 'giong_to.jpeg', 'Văn học - Nghệ thuật', 2021, 2, 'Vũ Trọng Phụng', 2, 95000, 90000, 5, '9786042086588', 1),
(90, 'Tố Tâm', 'to_tam.jpeg', 'Văn học - Nghệ thuật', 2022, 9, 'Hoàng Ngọc Phách', 2, 145000, 135000, 5, '9786045687389', 1),
(91, 'Hồ Quý Ly', 'ho_quy_ly.jpeg', 'Văn học - Nghệ thuật', 2020, 8, 'Nguyễn Xuân Khánh', 2, 180000, 170000, 5, '9786048967590', 1),
(92, 'Miền Hoang Tưởng', 'mien_hoang_tuong.jpeg', 'Văn học - Nghệ thuật', 2021, 13, 'Nguyễn Xuân Khánh', 2, 165000, 155000, 5, '9786047845491', 1),
(93, 'Những Thiên Đường Mù', 'nhung_thien_duong_mu.jpeg', 'Văn học - Nghệ thuật', 2019, 5, 'Dương Thu Hương', 2, 135000, 125000, 5, '9786042345692', 1),
(94, 'Hồn Bướm Mơ Tiên', 'hon_buom_mo_tien.jpeg', 'Văn học - Nghệ thuật', 2022, 4, 'Khái Hưng', 2, 125000, 115000, 5, '9786044565693', 1),
(95, 'Đoạn Tuyệt', 'doan_tuyet.jpeg', 'Văn học - Nghệ thuật', 2021, 11, 'Nhất Linh', 2, 95000, 90000, 5, '9786048674594', 1),
(96, 'Làm Đĩ', 'lam_di.jpeg', 'Văn học - Nghệ thuật', 2020, 16, 'Vũ Trọng Phụng', 2, 115000, 105000, 5, '9786043245695', 1),
(97, 'Bếp Lửa', 'bep_lua.jpeg', 'Văn học - Nghệ thuật', 2021, 9, 'Thanh Tâm Tuyền', 2, 85000, 80000, 5, '9786046789796', 1),
(98, 'Mùa Lá Rụng Trong Vườn', 'mua_la_rung_trong_vuon.jpeg', 'Văn học - Nghệ thuật', 2019, 12, 'Ma Văn Kháng', 2, 125000, 115000, 5, '9786043244597', 1),
(99, 'Mảnh Đất Lắm Người Nhiều Ma', 'manh_dat_lam_nguoi_nhieu_ma.jpeg', 'Văn học - Nghệ thuật', 2020, 3, 'Nguyễn Khắc Trường', 2, 145000, 135000, 5, '9786049897598', 1),
(100, 'Tiêu Sơn Tráng Sĩ', 'tieu_son_trang_si.jpeg', 'Văn học - Nghệ thuật', 2022, 5, 'Khái Hưng', 2, 110000, 105000, 5, '9786047896599', 1),
(101, 'Bước Đường Cùng', 'buoc_duong_cung.jpeg', 'Văn học - Nghệ thuật', 2022, 5, 'Khái Hưng', 2, 110000, 105000, 5, '97860478965100', 1);

-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
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
-- Dumping data for table `taikhoan`
--

INSERT INTO `taikhoan` (`MNV`, `MK`, `TDN`, `MNQ`, `TT`, `OTP`) VALUES
(1, '$2a$12$6GSkiQ05XjTRvCW9MB6MNuf7hOJEbbeQx11Eb8oELil1OrCq6uBXm', 'admin', 1, 1, 'null'),
(2, '$2a$12$6GSkiQ05XjTRvCW9MB6MNuf7hOJEbbeQx11Eb8oELil1OrCq6uBXm', 'NV2', 2, 1, 'null'),
(3, '$2a$12$6GSkiQ05XjTRvCW9MB6MNuf7hOJEbbeQx11Eb8oELil1OrCq6uBXm', 'NV3', 3, 1, 'null');

-- --------------------------------------------------------

--
-- Table structure for table `taikhoankh`
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
-- Dumping data for table `taikhoankh`
--

INSERT INTO `taikhoankh` (`MKH`, `MK`, `TDN`, `MNQ`, `TT`, `OTP`) VALUES
(1, '$2a$12$6GSkiQ05XjTRvCW9MB6MNuf7hOJEbbeQx11Eb8oELil1OrCq6uBXm', 'KH1', 4, 1, 'null');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ctgiohang`
--
ALTER TABLE `ctgiohang`
  ADD PRIMARY KEY (`MKH`,`MSP`),
  ADD KEY `FK_MSP_CTGIOHANG` (`MSP`),
  ADD KEY `FK_MKM_CTGIOHANG` (`MKM`);

--
-- Indexes for table `ctmakhuyenmai`
--
ALTER TABLE `ctmakhuyenmai`
  ADD PRIMARY KEY (`MKM`,`MSP`),
  ADD KEY `FK_MSP_CTMAKHUYENMAI` (`MSP`);

--
-- Indexes for table `ctphieukiemke`
--
ALTER TABLE `ctphieukiemke`
  ADD PRIMARY KEY (`MPKK`,`MSP`),
  ADD KEY `FK_MSP_CTPHIEUKIEMKE` (`MSP`);

--
-- Indexes for table `ctphieunhap`
--
ALTER TABLE `ctphieunhap`
  ADD PRIMARY KEY (`MPN`,`MSP`),
  ADD KEY `FK_MSP_CTPHIEUNHAP` (`MSP`);

--
-- Indexes for table `ctphieutra`
--
ALTER TABLE `ctphieutra`
  ADD PRIMARY KEY (`MPX`,`MSP`),
  ADD KEY `FK_MSP_CTPHIEUTRA` (`MSP`);

--
-- Indexes for table `ctphieuxuat`
--
ALTER TABLE `ctphieuxuat`
  ADD PRIMARY KEY (`MPX`,`MSP`),
  ADD KEY `FK_MSP_CTPHIEUXUAT` (`MSP`),
  ADD KEY `FK_MKM_CTPHIEUXUAT` (`MKM`);

--
-- Indexes for table `ctquyen`
--
ALTER TABLE `ctquyen`
  ADD PRIMARY KEY (`MNQ`,`MCN`,`HANHDONG`),
  ADD KEY `FK_MCN_CTQUYEN` (`MCN`);

--
-- Indexes for table `danhmucchucnang`
--
ALTER TABLE `danhmucchucnang`
  ADD PRIMARY KEY (`MCN`);

--
-- Indexes for table `giohang`
--
ALTER TABLE `giohang`
  ADD PRIMARY KEY (`MKH`);

--
-- Indexes for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MKH`),
  ADD UNIQUE KEY `SDT` (`SDT`),
  ADD UNIQUE KEY `EMAIL` (`EMAIL`);

--
-- Indexes for table `khuvucsach`
--
ALTER TABLE `khuvucsach`
  ADD PRIMARY KEY (`MKVS`);

--
-- Indexes for table `makhuyenmai`
--
ALTER TABLE `makhuyenmai`
  ADD PRIMARY KEY (`MKM`),
  ADD KEY `FK_MNV_MAKHUYENMAI` (`MNV`);

--
-- Indexes for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`MNCC`);

--
-- Indexes for table `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`MNV`),
  ADD UNIQUE KEY `EMAIL` (`EMAIL`);

--
-- Indexes for table `nhaxuatban`
--
ALTER TABLE `nhaxuatban`
  ADD PRIMARY KEY (`MNXB`);

--
-- Indexes for table `nhomquyen`
--
ALTER TABLE `nhomquyen`
  ADD PRIMARY KEY (`MNQ`);

--
-- Indexes for table `phieukiemke`
--
ALTER TABLE `phieukiemke`
  ADD PRIMARY KEY (`MPKK`),
  ADD KEY `FK_MNV_PHIEUKIEMKE` (`MNV`);

--
-- Indexes for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`MPN`),
  ADD KEY `FK_MNV_PHIEUNHAP` (`MNV`),
  ADD KEY `FK_MNCC_PHIEUNHAP` (`MNCC`);

--
-- Indexes for table `phieutra`
--
ALTER TABLE `phieutra`
  ADD PRIMARY KEY (`MPX`),
  ADD KEY `FK_MNV_PHIEUTRA` (`MNV`),
  ADD KEY `FK_MKH_PHIEUTRA` (`MKH`);

--
-- Indexes for table `phieuxuat`
--
ALTER TABLE `phieuxuat`
  ADD PRIMARY KEY (`MPX`),
  ADD KEY `FK_MNV_PHIEUXUAT` (`MNV`),
  ADD KEY `FK_MKH_PHIEUXUAT` (`MKH`);

--
-- Indexes for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`MSP`),
  ADD UNIQUE KEY `ISBN` (`ISBN`),
  ADD KEY `FK_MNXB_SANPHAM` (`MNXB`),
  ADD KEY `FK_MKVS_SANPHAM` (`MKVS`);

--
-- Indexes for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`MNV`,`TDN`),
  ADD UNIQUE KEY `TDN` (`TDN`),
  ADD KEY `FK_MNQ_TAIKHOAN` (`MNQ`);

--
-- Indexes for table `taikhoankh`
--
ALTER TABLE `taikhoankh`
  ADD PRIMARY KEY (`MKH`,`TDN`),
  ADD UNIQUE KEY `TDN` (`TDN`),
  ADD KEY `FK_MNQ_TAIKHOANKH` (`MNQ`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `MKH` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã khách hàng', AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT for table `khuvucsach`
--
ALTER TABLE `khuvucsach`
  MODIFY `MKVS` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã khu vực sách', AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `MNCC` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã nhà cung cấp', AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `nhanvien`
--
ALTER TABLE `nhanvien`
  MODIFY `MNV` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã nhân viên', AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `nhaxuatban`
--
ALTER TABLE `nhaxuatban`
  MODIFY `MNXB` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã nhà xuất bản', AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `nhomquyen`
--
ALTER TABLE `nhomquyen`
  MODIFY `MNQ` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã nhóm quyền', AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `phieukiemke`
--
ALTER TABLE `phieukiemke`
  MODIFY `MPKK` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã phiếu kiểm kê', AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `MPN` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã phiếu nhập', AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `phieuxuat`
--
ALTER TABLE `phieuxuat`
  MODIFY `MPX` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã phiếu xuất', AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `MSP` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã sản phẩm', AUTO_INCREMENT=102;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ctgiohang`
--
ALTER TABLE `ctgiohang`
  ADD CONSTRAINT `FK_MKH_CTGIOHANG` FOREIGN KEY (`MKH`) REFERENCES `giohang` (`MKH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MKM_CTGIOHANG` FOREIGN KEY (`MKM`) REFERENCES `makhuyenmai` (`MKM`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MSP_CTGIOHANG` FOREIGN KEY (`MSP`) REFERENCES `sanpham` (`MSP`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `ctmakhuyenmai`
--
ALTER TABLE `ctmakhuyenmai`
  ADD CONSTRAINT `FK_MKM_CTMAKHUYENMAI` FOREIGN KEY (`MKM`) REFERENCES `makhuyenmai` (`MKM`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MSP_CTMAKHUYENMAI` FOREIGN KEY (`MSP`) REFERENCES `sanpham` (`MSP`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `ctphieukiemke`
--
ALTER TABLE `ctphieukiemke`
  ADD CONSTRAINT `FK_MPKK_CTPHIEUKIEMKE` FOREIGN KEY (`MPKK`) REFERENCES `phieukiemke` (`MPKK`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MSP_CTPHIEUKIEMKE` FOREIGN KEY (`MSP`) REFERENCES `sanpham` (`MSP`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `ctphieunhap`
--
ALTER TABLE `ctphieunhap`
  ADD CONSTRAINT `FK_MPN_CTPHIEUNHAP` FOREIGN KEY (`MPN`) REFERENCES `phieunhap` (`MPN`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MSP_CTPHIEUNHAP` FOREIGN KEY (`MSP`) REFERENCES `sanpham` (`MSP`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `ctphieutra`
--
ALTER TABLE `ctphieutra`
  ADD CONSTRAINT `FK_MPX_CTPHIEUTRA` FOREIGN KEY (`MPX`) REFERENCES `phieutra` (`MPX`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MSP_CTPHIEUTRA` FOREIGN KEY (`MSP`) REFERENCES `ctphieuxuat` (`MSP`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `ctphieuxuat`
--
ALTER TABLE `ctphieuxuat`
  ADD CONSTRAINT `FK_MKM_CTPHIEUXUAT` FOREIGN KEY (`MKM`) REFERENCES `makhuyenmai` (`MKM`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MPX_CTPHIEUXUAT` FOREIGN KEY (`MPX`) REFERENCES `phieuxuat` (`MPX`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MSP_CTPHIEUXUAT` FOREIGN KEY (`MSP`) REFERENCES `sanpham` (`MSP`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `ctquyen`
--
ALTER TABLE `ctquyen`
  ADD CONSTRAINT `FK_MCN_CTQUYEN` FOREIGN KEY (`MCN`) REFERENCES `danhmucchucnang` (`MCN`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MNQ_CTQUYEN` FOREIGN KEY (`MNQ`) REFERENCES `nhomquyen` (`MNQ`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `giohang`
--
ALTER TABLE `giohang`
  ADD CONSTRAINT `FK_MKH_GIOHANG` FOREIGN KEY (`MKH`) REFERENCES `khachhang` (`MKH`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `makhuyenmai`
--
ALTER TABLE `makhuyenmai`
  ADD CONSTRAINT `FK_MNV_MAKHUYENMAI` FOREIGN KEY (`MNV`) REFERENCES `nhanvien` (`MNV`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `phieukiemke`
--
ALTER TABLE `phieukiemke`
  ADD CONSTRAINT `FK_MNV_PHIEUKIEMKE` FOREIGN KEY (`MNV`) REFERENCES `nhanvien` (`MNV`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD CONSTRAINT `FK_MNCC_PHIEUNHAP` FOREIGN KEY (`MNCC`) REFERENCES `nhacungcap` (`MNCC`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MNV_PHIEUNHAP` FOREIGN KEY (`MNV`) REFERENCES `nhanvien` (`MNV`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `phieutra`
--
ALTER TABLE `phieutra`
  ADD CONSTRAINT `FK_MKH_PHIEUTRA` FOREIGN KEY (`MKH`) REFERENCES `khachhang` (`MKH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MNV_PHIEUTRA` FOREIGN KEY (`MNV`) REFERENCES `nhanvien` (`MNV`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MPX_PHIEUTRA` FOREIGN KEY (`MPX`) REFERENCES `phieuxuat` (`MPX`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `phieuxuat`
--
ALTER TABLE `phieuxuat`
  ADD CONSTRAINT `FK_MKH_PHIEUXUAT` FOREIGN KEY (`MKH`) REFERENCES `khachhang` (`MKH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MNV_PHIEUXUAT` FOREIGN KEY (`MNV`) REFERENCES `nhanvien` (`MNV`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `FK_MKVS_SANPHAM` FOREIGN KEY (`MKVS`) REFERENCES `khuvucsach` (`MKVS`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MNXB_SANPHAM` FOREIGN KEY (`MNXB`) REFERENCES `nhaxuatban` (`MNXB`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD CONSTRAINT `FK_MNQ_TAIKHOAN` FOREIGN KEY (`MNQ`) REFERENCES `nhomquyen` (`MNQ`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MNV_TAIKHOAN` FOREIGN KEY (`MNV`) REFERENCES `nhanvien` (`MNV`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `taikhoankh`
--
ALTER TABLE `taikhoankh`
  ADD CONSTRAINT `FK_MKH_TAIKHOANKH` FOREIGN KEY (`MKH`) REFERENCES `khachhang` (`MKH`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_MNQ_TAIKHOANKH` FOREIGN KEY (`MNQ`) REFERENCES `nhomquyen` (`MNQ`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
