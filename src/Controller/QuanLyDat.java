/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.ModuleController.InsertToListOrdered;
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
        String diachi, chusohuu, loainha, mucdichsudung;
        float dientich;
        long giatien;
        System.out.print("Địa chỉ: ");
        diachi = in.nextLine();
        System.out.print("Diện tích: ");
        dientich = Float.parseFloat(in.nextLine());
        System.out.print("Chủ sở hữu hiện tại: ");
        chusohuu = in.nextLine();
        System.out.print("Loại nhà: ");
        loainha = in.nextLine();
        System.out.print("Mục đích sử dụng: ");
        mucdichsudung = in.nextLine();
        System.out.print("Giá tiền: ");
        giatien = Integer.parseInt(in.nextLine());
        return new ThuaDat(diachi, chusohuu, loainha, mucdichsudung, dientich, giatien);
    }

    /**
     * In tên tất cả các DS hiện có trong hệ thống
     */
    public void ShowAllListInSystem() {
        System.out.print("Các DS hiện có trong hệ thống: ");
        common.ConfigData.sysData.forEach((k, v) -> {
            System.out.print(k + "\t");
        });
        System.out.println("");
    }

    /** In dữ liệu hiện có trong danh sach ra màn hình */
    public void ShowDataInList() {
        ShowAllListInSystem();
        System.out.println("Nhập tên DS muốn xem: ");
        String nameList;
        nameList = in.nextLine();
        ConfigData.sysData.get(nameList).forEach((td) -> {
            System.out.println(td.toString());
        });

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
        ShowAllListInSystem();
        System.out.print("Tên DS xuất: ");
        String listName = in.nextLine();
        // Lưu dữ liệu vào hệ thống
        ModuleController.WriteDataToXLSFile(filePath, ConfigData.sysData.get(listName));
    }

    public void ChenThuaDatVaoDS() {
        ShowAllListInSystem();
        System.out.print("Lưu vào DS: ");
        String listName = in.nextLine();
        ThuaDat td = NhapThuaDat();
        ArrayList<ThuaDat> kq = InsertToListOrdered(ConfigData.sysData.get(listName), td);
        ConfigData.sysData.put(listName, kq);
    }

    public void XoaDuLieuTheoYC() {
        ShowAllListInSystem();
        System.out.print("Chọn DS thực hiện yc: ");
        String listName = in.nextLine();
        System.out.print("Nhập thông tin cần xóa: ");
        String yeuCauXoa = in.nextLine();
        ArrayList<ThuaDat> kq = ModuleController.DeleteByQuery(ConfigData.sysData.get(listName), yeuCauXoa);
        ConfigData.sysData.put(listName, kq);
    }

    public void TimKiemThongTin() {
        ShowAllListInSystem();
        System.out.print("Chọn DS thực hiện yc: ");
        String listName = in.nextLine();
        System.out.print("Nhập thông tin cần tìm kiếm: ");
        String query = in.nextLine();
        ArrayList<Integer> kq = ModuleController.SearchData(ConfigData.sysData.get(listName), query);
        System.out.println("Kết quả tìm được: ");
        System.out.println(ConfigData.titleShow);
        kq.forEach((index) -> {
            System.out.println(String.format("|%4s", index) + ConfigData.sysData.get(listName).get(index).toString());
        });
    }

    public void MergeDanhSach() {
        try (Scanner in = new Scanner(System.in)) {
            ShowAllListInSystem();
            System.out.print("Chọn DS1: ");
            String ds1 = in.nextLine();
            System.out.print("Chọn DS1: ");
            String ds2 = in.nextLine();
            System.out.println("Chọn tiêu chí gộp: ");
            System.out.println("0. Địa chỉ");
            System.out.println("1. Giá tiền");
            System.out.println("2. Diện tích");
            System.out.print("Bạn chọn: ");
            int choose = Integer.parseInt(in.nextLine());
            ConfigData.TieuChiSoSanh tieuChiSoSanh = ConfigData.TieuChiSoSanh.values()[choose];
            System.out.println("Sắp xếp theo: ");
            System.out.println("0. Tăng dần");
            System.out.println("1. Giảm dần");
            System.out.print("Bạn chọn: ");
            choose = Integer.parseInt(in.nextLine());
            ConfigData.SapXep sapXep = ConfigData.SapXep.values()[choose];
            ArrayList<ThuaDat> ketqua = ModuleController.MergeList(ConfigData.sysData.get(ds1), ConfigData.sysData.get(ds2), tieuChiSoSanh, sapXep);
            System.out.println("Kết quả sau khi gộp: ");
            showData(ketqua);
        }
    }

    public void SapXepTheoTieuChiBanDau() {
        try (Scanner in = new Scanner(System.in)) {
            ShowAllListInSystem();
            System.out.println("Nhập DS thực hiện: ");
            String listName = in.nextLine();
            ArrayList<ThuaDat> ketqua = ModuleController.HeapsortByAddress(common.ConfigData.sysData.get(listName));
            ConfigData.sysData.put(listName, ketqua);
            System.out.println("Mảng vừa sắp xếp: ");
            showData(ketqua);
        }
    }
}
