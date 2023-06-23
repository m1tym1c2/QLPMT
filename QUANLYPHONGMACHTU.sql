CREATE DATABASE QUANLYPHONGMACHTU
USE QUANLYPHONGMACHTU

CREATE TABLE BENHNHAN (
	MaBenhNhan varchar(20) primary key,
	TenBenhNhan nvarchar(50),
	GioiTinh nvarchar(3),
	NamSinh int,
	DiaChi nvarchar(100) )

CREATE TABLE PHIEUKHAMBENH (
	MaPhieuKhamBenh varchar(20) primary key,
	MaBenhNhan varchar(20),
	NgayKham smalldatetime,
	MaLoaiBenh varchar(20),
	TrieuChung nvarchar(100),
	TienKham int,
	TienThuoc int,
	MaNhanVien varchar(20) )

CREATE TABLE LOAIBENH (
	MaLoaiBenh varchar(20) primary key,
	TenLoaiBenh nvarchar(50) )

CREATE TABLE CT_PHIEUKHAMBENH (
	MaPhieuKhamBenh varchar(20),
	MaThuoc varchar(20),
	CONSTRAINT PK_CT_PHIEUKHAMBENH primary key (MaPhieuKhamBenh, MaThuoc),
	SoLuongDung int,
	DonGiaThuoc int )

CREATE TABLE THUOC (
	MaThuoc varchar(20) primary key,
	TenThuoc nvarchar(50),
	TenDonViTinh nvarchar(20),
	SoLuongTon int,
	LoaiThuoc nvarchar(50),
	MaCachDung varchar(20),
	FileAnhThuoc nvarchar(max) )

CREATE TABLE CACHDUNG (
	MaCachDung varchar(20) primary key,
	TenCachDung nvarchar(100) )

CREATE TABLE DONVITINH (
	TenDonViTinh nvarchar(20) primary key )

CREATE TABLE PHIEUNHAPTHUOC (
	MaPhieuNhapThuoc varchar(20) primary key,
	GiaTriPhieuNhap int,
	NgayNhap smalldatetime )

CREATE TABLE CT_PHIEUNHAPTHUOC (
	MaPhieuNhapThuoc varchar(20),
	MaThuoc varchar(20),
	CONSTRAINT PK_CT_PHIEUNHAPTHUOC primary key (MaPhieuNhapThuoc, MaThuoc),
	SoLuongNhap int,
	DonGiaNhap int,
	DonGiaBan int )

CREATE TABLE HOADON (
	MaHoaDon varchar(20) primary key,
	MaPhieuKhamBenh varchar(20),
	GiaTriHoaDon int,
	MaNhanVien varchar(20) )

CREATE TABLE BAOCAOTHANG (
	Thang int,
	Nam int,
	CONSTRAINT PK_BAOCAOTHANG primary key (Thang, Nam),
	DoanhThuThang int )

CREATE TABLE CT_BAOCAOTHANG (
	Ngay int,
	Thang int,
	Nam int,
	CONSTRAINT PK_CT_BAOCAOTHANG primary key (Ngay, Thang, Nam),
	DoanhThuNgay int,
	SoBenhNhan int,
	TiLe float )

CREATE TABLE BAOCAOSUDUNGTHUOC (
	Thang int,
	Nam int,
	MaThuoc varchar(20),
	CONSTRAINT PK_BAOCAOSUDUNGTHUOC primary key (Thang, Nam, MaThuoc),
	SoLuongDung int,
	SoLanDung int )

CREATE TABLE THAMSO (
	TenThamSo varchar(50) primary key,
	GiaTri int )

CREATE TABLE NHANVIEN (
	MaNhanVien varchar(20) primary key,
	CMND varchar(20),
	NgaySinh smalldatetime,
	DiaChi nvarchar(200),
	Email varchar(50),
	LuongCB int,
	HeSo Float,
	HinhAnh varchar(200),
	MatKhau varchar(max),
	TenNhanVien nvarchar(100),
	TienThuong int)

CREATE TABLE CHUCNANG (
	MaChucNang varchar(20) primary key,
	TenChucNang nvarchar(50) )

CREATE TABLE PHANQUYEN (
	MaNhanVien varchar(20),
	MaChucNang varchar(20),
	CONSTRAINT PK_PHANQUYEN primary key (MaNhanVien, MaChucNang) )

ALTER TABLE PHANQUYEN ADD
	CONSTRAINT FK_PHANQUYEN_CHUCNANG FOREIGN KEY (MaChucNang)
	REFERENCES CHUCNANG (MaChucNang),
	CONSTRAINT FK_PHANQUYEN_NHANVIEN FOREIGN KEY (MaNhanVien)
	REFERENCES NHANVIEN (MaNhanVien)

ALTER TABLE HOADON ADD 
	CONSTRAINT FK_HOADON_PHIEUKHAMBENH FOREIGN KEY (MaPhieuKhamBenh) 
	REFERENCES PHIEUKHAMBENH (MaPhieuKhamBenh),
	CONSTRAINT FK_HOADON_NHANVIEN FOREIGN KEY (MaNhanVien)
	REFERENCES NHANVIEN (MaNhanVien)

ALTER TABLE CT_PHIEUKHAMBENH ADD
	CONSTRAINT FK_CT_PHIEUKHAMBENH_PHIEUKHAMBENH FOREIGN KEY (MaPhieuKhamBenh)
	REFERENCES PHIEUKHAMBENH (MaPhieuKhamBenh),
	CONSTRAINT FK_CT_PHIEUKHAMBENH_THUOC FOREIGN KEY (MaThuoc)
	REFERENCES THUOC (MaThuoc)

ALTER TABLE CT_PHIEUNHAPTHUOC ADD
	CONSTRAINT FK_CT_PHIEUNHAPTHUOC_PHIEUNHAPTHUOC FOREIGN KEY (MaPhieuNhapThuoc)
	REFERENCES PHIEUNHAPTHUOC (MaPhieuNhapThuoc),
	CONSTRAINT FK_CT_PHIEUNHAPTHUOC_THUOC FOREIGN KEY (MaThuoc)
	REFERENCES THUOC (MaThuoc)

ALTER TABLE THUOC ADD
	CONSTRAINT FK_THUOC_DONVITINH FOREIGN KEY (TenDonViTinh)
	REFERENCES DONVITINH (TenDonViTinh),
	CONSTRAINT FK_THUOC_CACHDUNG FOREIGN KEY (MaCachDung)
	REFERENCES CACHDUNG (MaCachDung)

ALTER TABLE PHIEUKHAMBENH ADD
	CONSTRAINT FK_PHIEUKHAMBENH_LOAIBENH FOREIGN KEY (MaLoaiBenh)
	REFERENCES LOAIBENH (MaLoaiBenh),
	CONSTRAINT FK_PHIEUKHAMBENH_BENHNHAN FOREIGN KEY (MaBenhNhan)
	REFERENCES BENHNHAN (MaBenhNhan),
	CONSTRAINT FK_PHIEUKHAMBENH_NHANVIEN FOREIGN KEY (MaNhanVien)
	REFERENCES NHANVIEN (MaNhanVien)

ALTER TABLE CT_BAOCAOTHANG ADD
	CONSTRAINT FK_CT_BAOCAOTHANG_BAOCAOTHANG FOREIGN KEY (Thang, Nam)
	REFERENCES BAOCAOTHANG (Thang, Nam)

ALTER TABLE BAOCAOSUDUNGTHUOC ADD
	CONSTRAINT FK_BAOCAOSUDUNGTHUOC_THUOC FOREIGN KEY (MaThuoc)
	REFERENCES THUOC (MaThuoc)

INSERT INTO THAMSO VALUES ('SoBenhNhanToiDa', 40)
INSERT INTO THAMSO VALUES ('TienKham', 30000)
INSERT INTO THAMSO VALUES ('TiGiaNhapBan', 110)

INSERT INTO LOAIBENH VALUES('LB001', N'Viêm họng')
INSERT INTO LOAIBENH VALUES('LB002', N'Viêm da dị ứng')
INSERT INTO LOAIBENH VALUES('LB003', N'Rối loạn tiêu hóa')
INSERT INTO LOAIBENH VALUES('LB004', N'Đau bao tử')
INSERT INTO LOAIBENH VALUES('LB005', N'Viêm khớp thoái hóa')

INSERT INTO DONVITINH VALUES(N'Viên')
INSERT INTO DONVITINH VALUES(N'Chai')

INSERT INTO CACHDUNG VALUES('CD001', N'Ngày uống 2 lần, mỗi lần 1 viên')
INSERT INTO CACHDUNG VALUES('CD002', N'Ngày uống 2 lần, mỗi lần 2 viên')
INSERT INTO CACHDUNG VALUES('CD003', N'Ngày uống 1 lần, mỗi lần 1 viên')
INSERT INTO CACHDUNG VALUES('CD004', N'Ngày uống 1 lần, mỗi lần 2 viên')

INSERT INTO CHUCNANG VALUES('000', N'admin')
INSERT INTO CHUCNANG VALUES('001', N'Y tá phòng thuốc')
INSERT INTO CHUCNANG VALUES('002', N'Nhân viên kế toán')
INSERT INTO CHUCNANG VALUES('003', N'Quản lý nhân sự')
INSERT INTO CHUCNANG VALUES('004', N'Bác sĩ phòng mạch')

INSERT INTO THUOC VALUES('THU001', N'Penicillin', N'Viên', 0, N'Kháng sinh', 'CD001', NULL)
INSERT INTO THUOC VALUES('THU002', N'Paracetamol', N'Viên', 0, N'Hạ sốt', 'CD001', NULL)
INSERT INTO THUOC VALUES('THU003', N'Tylenol', N'Viên', 0, N'Hạ sốt', 'CD001', NULL)
INSERT INTO THUOC VALUES('THU004', N'Ibuprofen', N'Viên', 0, N'Kháng viêm', 'CD002', NULL)
INSERT INTO THUOC VALUES('THU005', N'Mobic', N'Viên', 0, N'Kháng viêm', 'CD002', NULL)

INSERT INTO THUOC VALUES('THU006', N'Alexan', N'Viên', 0, N'Giảm đau', 'CD002', NULL)
INSERT INTO THUOC VALUES('THU007', N'Oresol', N'Chai', 0, N'Tiêu chảy', 'CD003', NULL)
INSERT INTO THUOC VALUES('THU008', N'Motilum M', N'Chai', 0, N'Đau bụng', 'CD003', NULL)
INSERT INTO THUOC VALUES('THU009', N'Smecta', N'Chai', 0, N'Đau bụng', 'CD003', NULL)
INSERT INTO THUOC VALUES('THU010', N'Pentanol', N'Chai', 0, N'Da liễu', 'CD004', NULL)

INSERT INTO THUOC VALUES('THU011', N'Loratadine', N'Chai', 0, N'Kháng sinh', 'CD004', NULL)
INSERT INTO THUOC VALUES('THU012', N'Betadine', N'Chai', 0, N'Sát trùng', 'CD004', NULL)

ALTER TABLE CT_PHIEUNHAPTHUOC ADD CoSoSX nvarchar(100)


---------------------------------------------------------------------------------------------------------------------
--DELETE FROM CHUCNANG

--DELETE FROM NHANVIEN
--INSERT INTO NHANVIEN VALUES('001', '083203013', '11/02/2003', 'Ben Tre', 'rasengan2458@gmail.com', 3000000, 2.0, '', 'triet123', N'Nguyễn Huỳnh Minh Triết', 300000)
--INSERT INTO PHANQUYEN VALUES('001', '000')
--DELETE FROM PHANQUYEN

ALTER TABLE CT_PHIEUKHAMBENH DROP COLUMN SoLuong;
 
ALTER TABLE CT_PHIEUKHAMBENH ADD SoLuongDung int
ALTER TABLE CT_PHIEUKHAMBENH ADD DonGiaThuoc int
ALTER TABLE CT_PHIEUKHAMBENH DROP COLUMN LieuDung;
ALTER TABLE CT_PHIEUKHAMBENH ADD LieuDung int
ALTER TABLE	CT_PHIEUKHAMBENH DROP COLUMN DonGiaThuoc;