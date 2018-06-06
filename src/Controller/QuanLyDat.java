/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ThuaDat;
import common.ConfigData;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author MyPC
 */
public class QuanLyDat {

    Scanner in = new Scanner(System.in);

    public QuanLyDat() {
    }

    /**
     * Tạo dữ liệu Thửa đất nhập từ bàn phím
     *
     * @return Dữ liệu Thửa đất
     */
    private ThuaDat NhapThuaDat() {
        String diachi, chusohuu, loainha = "", mucdichsudung = "";
        float dientich;
        long giatien;
        System.out.println("Nhập thông tin Thửa Đất cần thêm");
        System.out.print("Địa chỉ: ");
        diachi = in.nextLine();
        System.out.print("Diện tích: ");
        dientich = Float.parseFloat(in.nextLine());
        System.out.print("Chủ sở hữu hiện tại: ");
        chusohuu = in.nextLine();

        int chon = 0; // Gán = 0 để vào vòng lặp
        // Nhập Mục đích sử dụng
        while (chon < 1 || chon > 2) {
            System.out.print(ConfigData.strNhapMucDichSD);
            chon = Integer.parseInt(in.nextLine());
            switch (chon) {
                case 1:
                    mucdichsudung = "Nhà ở";
                    break;
                case 2:
                    mucdichsudung = "Kinh doanh";
                    break;
                default:
                    System.err.println(ConfigData.strDataInputNotValid);
            }
        }

        // Nhập Loại nhà
        chon = 0; // Gán = 0 để vào vòng lặp
        while (chon < 1 || chon > 4) {
            System.out.print(ConfigData.strNhapLoaiNha);
            chon = Integer.parseInt(in.nextLine());
            if (chon >= 1 && chon <= 4) {
                loainha = "Nhà cấp " + chon;
            } else {
                System.err.println(ConfigData.strDataInputNotValid);
            }
        }

        System.out.print("Giá tiền: ");
        giatien = Integer.parseInt(in.nextLine());
        return new ThuaDat(diachi, chusohuu, loainha, mucdichsudung, dientich, giatien);
    }

    /** In dữ liệu hiện có trong danh sach ra màn hình */
    public void ShowDataInList() {
        showData(ConfigData.sysData.get(chonDanhSachThucHien()));
    }

    /**
     * Kiểm tra danh sách có tồn tại trong hệ thống hay không?
     *
     * @param nameList Tên danh sách cần kiểm tra
     *
     * @return true | false
     */
    private boolean isListValid(String nameList) {
        if (ConfigData.sysData.get(nameList) != null) {
            return true;
        } else {
            System.err.println(ConfigData.strDataInputNotValid);
            return false;
        }
    }

    /**
     * Chọn danh sách thực hiện tác vụ
     *
     * @return Tên danh sách có dữ liệu != null
     */
    public String chonDanhSachThucHien() {
        while (true) {
            System.out.print("Các DS hiện có trong hệ thống: ");
            common.ConfigData.sysData.forEach((k, v) -> {
                System.out.print(k + "\t");
            });
            System.out.println("");
            System.out.print("Nhập tên DS muốn Thực hiện: ");
            String nameList;
            nameList = in.nextLine();
            if (isListValid(nameList)) {
                return nameList;
            }
        }
    }

    /**
     * In dữ liệu trong list ra màn hình
     *
     * @param listTD
     */
    public void showData(ArrayList<ThuaDat> listTD) {
        System.out.println(ConfigData.titleShow);
        for (int i = 0; i < listTD.size(); i++) {
            System.out.println(String.format("|%4s", i + 1) + listTD.get(i).toString());
        }
    }

    public void LoadDataToSystem() {
        try (Stream<Path> paths = Files.walk(Paths.get(common.ConfigData.pathLoadImportData))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach((path) -> {
                        String filePath = path.toString();
                        String listName = filePath.toString().substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
                        ArrayList<ThuaDat> ketqua = ModuleController.ReadDataFromXLSFile(filePath);
                        showData(ketqua);
                        // Lưu dữ liệu vào hệ thống 
                        System.out.println(String.format("Load thành công file: %s", filePath));
                        ConfigData.sysData.put(listName, ketqua);
                    });
        } catch (IOException ex) {
            Logger.getLogger(QuanLyDat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ReadFromExcel() {
        System.out.println(String.format("Đường dẫn mặc định (%s) ", ConfigData.pathLoadImportData));
        System.out.print("Nhập tên file đọc dữ liệu (*.xls): ");
        String filePath = in.nextLine();
        if (!filePath.contains("\\")) {
            filePath = ConfigData.pathLoadImportData + filePath;
        }
        String listName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
        System.out.print("Tên DS lưu trong hệ thống (" + listName + "):");
        String listSave = in.nextLine();
        if (listSave != "\n") {
            listName = listSave;
        }
        ArrayList<ThuaDat> ketqua = ModuleController.ReadDataFromXLSFile(filePath);
        showData(ketqua);
        // Lưu dữ liệu vào hệ thống 
        ConfigData.sysData.put(listName, ketqua);
    }

    public void WriteDataToExcel() {
        System.out.print("Nhập đường đẫn file ghi dữ liệu (*.xls): ");
        String filePath = in.nextLine();
        String listName = chonDanhSachThucHien();
        // Lưu dữ liệu vào hệ thống
        ModuleController.WriteDataToXLSFile(filePath, ConfigData.sysData.get(listName));
    }

    public void ChenThuaDatVaoDS() {
        String listName = chonDanhSachThucHien();

        // Kiểm tra xem danh sách đã được sắp xếp hay chưa?
        if (!ModuleController.IsListSortedAscByAddress(ConfigData.sysData.get(listName))) {
            System.out.println("Danh sách chưa được sắp xếp! SX ngay (y/n): ");
            String choose = in.nextLine();
            if (choose.equalsIgnoreCase("Y") || choose == "\n") {
                ArrayList<ThuaDat> ketqua = ModuleController.HeapsortByAddress(common.ConfigData.sysData.get(listName));
                ConfigData.sysData.put(listName, ketqua);
                System.out.println("Mảng vừa sắp xếp: ");
                showData(ketqua);
            }
        }
        ThuaDat td = NhapThuaDat();
        ArrayList<ThuaDat> kq = ModuleController.InsertToListOrdered(ConfigData.sysData.get(listName), td);
        ConfigData.sysData.put(listName, kq);
        System.out.println("DS sau khi thêm!");
        showData(kq);
    }

    public void XoaDuLieuTheoYC() {
        String listName = chonDanhSachThucHien();
        System.out.print("Nhập thông tin cần xóa: ");
        String yeuCauXoa = in.nextLine();
        ArrayList<ThuaDat> kq = ModuleController.DeleteByQuery(ConfigData.sysData.get(listName), yeuCauXoa);
        ConfigData.sysData.put(listName, kq);
        System.out.println("Dữ liệu danh sách sau khi xóa: ");
        showData(ConfigData.sysData.get(listName));
    }

    public void TimKiemThongTin() {
        String listName = chonDanhSachThucHien();
        System.out.print("Nhập thông tin cần tìm kiếm: ");
        String query = in.nextLine();
        ArrayList<Integer> kq = ModuleController.SearchData(ConfigData.sysData.get(listName), query);
        System.out.println("Kết quả tìm được: ");
        System.out.println(ConfigData.titleShow);
        kq.forEach((index) -> {
            System.out.println(String.format("|%4s", index + 1) + ConfigData.sysData.get(listName).get(index).toString());
        });
    }

    public void MergeDanhSach() {
        String ds1 = "", ds2 = "";
        while (ds1.equals(ds2)) {
            System.out.println("----CHỌN DANH SÁCH 1----");
            ds1 = chonDanhSachThucHien();
            System.out.println("----CHỌN DANH SÁCH 2----");
            ds2 = chonDanhSachThucHien();

            if (ds1.equals(ds2)) {
                System.out.print("DS1 và DS2 đang trùng nhau! Tiếp tục (Y/N): ");
                String choose = in.nextLine();
                if (choose.equalsIgnoreCase("Y") || choose == "\n") {
                    break;
                }
            }
        }

        ConfigData.TieuChiSoSanh tieuChiSoSanh = ConfigData.TieuChiSoSanh.DiaChi;
        ConfigData.SapXep sapXep = ConfigData.SapXep.values()[0];
        int chon = -1; // Gán = -1 để vào vòng lặp
        // Chọn Tiêu chí
        do {
            System.out.print(ConfigData.strChonTieuChi);
            chon = Integer.parseInt(in.nextLine());
            if (chon >= 0 && chon <= 2) {
                tieuChiSoSanh = ConfigData.TieuChiSoSanh.values()[chon];
                break;
            }
            System.err.println(ConfigData.strDataInputNotValid);
        } while (true);

        chon = -1; // Gán = -1 để vào vòng lặp
        // Chọn Tiêu chí
        do {
            System.out.print(ConfigData.strChonSapXep);
            chon = Integer.parseInt(in.nextLine());
            if (chon == 0 || chon == 1) {
                sapXep = ConfigData.SapXep.values()[chon];
                break;
            }
            System.err.println(ConfigData.strDataInputNotValid);
        } while (true);

        ArrayList<ThuaDat> ketqua = ModuleController.MergeList(ConfigData.sysData.get(ds1), ConfigData.sysData.get(ds2), tieuChiSoSanh, sapXep);
        System.out.println("Kết quả sau khi gộp: ");
        showData(ketqua);
    }

    public void SapXepTheoTieuChiBanDau() {
        String listName = chonDanhSachThucHien();
        ArrayList<ThuaDat> ketqua = ModuleController.HeapsortByAddress(common.ConfigData.sysData.get(listName));
        ConfigData.sysData.put(listName, ketqua);
        System.out.println("Mảng vừa sắp xếp: ");
        showData(ketqua);
    }
}
