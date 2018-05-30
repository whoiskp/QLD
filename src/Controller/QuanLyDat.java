/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.ModuleController.InsertToListOrdered;
import Model.ThuaDat;
import common.Common;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author MyPC
 */
public class QuanLyDat {

    /**
     * Tạo dữ liệu Thửa đất nhập từ bàn phím
     *
     * @return Dữ liệu Thửa đất
     */
    private ThuaDat NhapThuaDat() {
        String diachi, chusohuu, loainha, mucdichsudung;
        float dientich;
        long giatien;
        try (Scanner in = new Scanner(System.in)) {
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
        }
        return new ThuaDat(diachi, chusohuu, loainha, mucdichsudung, dientich, giatien);
    }

    /**
     * In tên tất cả các DS hiện có trong hệ thống
     */
    public void ShowAllListInSystem() {
        System.out.println("Các DS hiện có trong hệ thống: ");
        common.Common.sysData.forEach((k, v) -> System.out.println(k + "\t"));
    }

    /** In dữ liệu hiện có trong danh sach ra màn hình */
    public void ShowDataInList() {
        ShowAllListInSystem();
        System.out.println("Nhập tên DS muốn xem: ");
        String nameList;
        try (Scanner in = new Scanner(System.in)) {
            nameList = in.nextLine();
            Common.sysData.get(nameList).forEach((td) -> {
                System.out.println(td.toString());
            });
        }
    }

    public void ReadFromExcel() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Nhập đường đẫn file đọc dữ liệu (*.xls): ");
            String filePath = in.nextLine();
            String listName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
            System.out.print("Tên DS lưu trong hệ thống (" + listName + "):");
            String listSave = in.nextLine();
            if (listSave != "\n") {
                listName = listSave;
            }
            // Lưu dữ liệu vào hệ thống 
            Common.sysData.put(listName, ModuleController.ReadDataFromXLSFile(filePath));
        }
    }

    public void WriteDataToExcel() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Nhập đường đẫn file ghi dữ liệu (*.xls): ");
            String filePath = in.nextLine();
            ShowAllListInSystem();
            System.out.print("Tên DS xuất: ");
            String listName = in.nextLine();
            // Lưu dữ liệu vào hệ thống
            ModuleController.WriteDataToXLSFile(filePath, Common.sysData.get(listName));
        }
    }

    public void ChenThuaDatVaoDS() {
        try (Scanner in = new Scanner(System.in)) {
            ShowAllListInSystem();
            System.out.print("Lưu vào DS: ");
            String listName = in.nextLine();
            ThuaDat td = NhapThuaDat();
            ArrayList<ThuaDat> kq = InsertToListOrdered(Common.sysData.get(listName), td);
            Common.sysData.put(listName, kq);
        }
    }

    public void XoaDuLieuTheoYC() {
        try (Scanner in = new Scanner(System.in)) {
            ShowAllListInSystem();
            System.out.print("Chọn DS thực hiện yc: ");
            String listName = in.nextLine();
            System.out.print("Nhập thông tin cần xóa: ");
            String yeuCauXoa = in.nextLine();
            ArrayList<ThuaDat> kq = ModuleController.DeleteByQuery(Common.sysData.get(listName), yeuCauXoa);
            Common.sysData.put(listName, kq);
        }
    }

    public void TimKiemThongTin() {
        try (Scanner in = new Scanner(System.in)) {
            ShowAllListInSystem();
            System.out.print("Chọn DS thực hiện yc: ");
            String listName = in.nextLine();
            System.out.print("Nhập thông tin cần tìm kiếm: ");
            String query = in.nextLine();
            ArrayList<Integer> kq = ModuleController.SearchData(Common.sysData.get(listName), query);
            System.out.println("Kết quả tìm được: ");
            System.out.println(Common.titleShow);
            kq.forEach((index) -> {
                System.out.println(String.format("|%4s", index) + Common.sysData.get(listName).get(index).toString());
            });
        }
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
            Common.TieuChiSoSanh tieuChiSoSanh = Common.TieuChiSoSanh.values()[choose];
            System.out.println("Sắp xếp theo: ");
            System.out.println("0. Tăng dần");
            System.out.println("1. Giảm dần");
            System.out.print("Bạn chọn: ");
            choose = Integer.parseInt(in.nextLine());
            Common.SapXep sapXep = Common.SapXep.values()[choose];
            ArrayList<ThuaDat> ketqua = ModuleController.MergeList(Common.sysData.get(ds1), Common.sysData.get(ds2), tieuChiSoSanh, sapXep);
            System.out.println("Kết quả sau khi gộp: ");
            System.out.println(Common.titleShow);
            for (int i = 0; i < ketqua.size(); i++) {
                System.out.println(String.format("|%4s ", i) + ketqua.get(i).toString());
            }
        }
    }

    public void SapXepTheoTieuChiBanDau() {
        try (Scanner in = new Scanner(System.in)) {
            ShowAllListInSystem();
            System.out.println("Nhập DS thực hiện: ");
            String listName = in.nextLine();
            ArrayList<ThuaDat> ketqua = ModuleController.HeapsortByAddress(common.Common.sysData.get(listName));
            Common.sysData.put(listName, ketqua);
            System.out.println("Mảng vừa sắp xếp: ");
            System.out.println(Common.titleShow);
            for (int i = 0; i < ketqua.size(); i++) {
                System.out.println(String.format("|%4s ", i) + ketqua.get(i).toString());
            }
        }
    }
}
