/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import Model.ThuaDat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MyPC
 */
public class ConfigData {

    public static Map<String, ArrayList<ThuaDat>> sysData = new HashMap<String, ArrayList<ThuaDat>>();

    public static final String pathLoadImportData = "D:\\DemoQLD\\LoadData\\";
    public static final String pathLoadExportData = "D:\\DemoQLD\\ExportData\\";

    public static final String formatData = "|%-30s|%15s|%-25s|%-12s|%-20s|%20s|"; // Định dạng mặc định để xuất dữ liệu ra màn hình
    public static final String titleShow = String.format("|%4s" + formatData, "STT", "Địa chỉ", "Diện tích", "Chủ sở hữu hiện tại", "Loại nhà", "Mục đích sử dụng", "Giá tiền");
    public static String strMenu
            = "----------- Menu ---------\n"
            + "1. Đọc file Excel\n"
            + "2. Xem danh sách dữ liệu\n"
            + "3. Chèn dữ liệu\n"
            + "4. Xóa dữ liệu theo yêu cầu\n"
            + "5. Sắp xếp danh sách\n"
            + "6. Tim kiem\n"
            + "7. Gộp danh sách phụ vào danh sách chính\n"
            + "8. Xuất file Excel\n"
            + "0. Thoat\n"
            + "--------------------------\n"
            + "Nhap x: ";

    public static String strNhapLoaiNha
            = "Loại nhà: \n 1.Nhà ở \n 2. Kinh doanh \n Bạn chọn: ";
    public static String strNhapMucDichSD
            = "Mục đích sử dụng: \n 1. Nhà cấp 1 \n 2. Nhà cấp 2 \n 3. Nhà cấp 3 \n 4. Nhà cấp 4 \n Bạn chọn: ";
    public static String strChonTieuChi
            = "Chọn tiêu chí gộp: \n 0. Địa chỉ\n 1. Giá tiền\n 2. Diện tích\n Bạn chọn: ";
    public static String strChonSapXep
            = "Sắp xếp theo:  \n0. Tăng dần \n1. Giảm dần \nBạn chọn: ";

    public static String strDataInputNotValid = "Dữ liệu không hợp lệ! Vui lòng nhập lại";

    public static boolean SoSanhTheoTieuChi(ThuaDat td1, ThuaDat td2, ConfigData.TieuChiSoSanh tieuChiSoSanh, ConfigData.SapXep kieuSapXep) {
        if (kieuSapXep == ConfigData.SapXep.TangDan) {
            return td1.SosanhTheoTieuChi(td2, tieuChiSoSanh) == 1;
        } else {
            return td1.SosanhTheoTieuChi(td2, tieuChiSoSanh) == -1;
        }
    }

    public enum TieuChiSoSanh {
        DiaChi,
        GiaTien,
        DienTich
    }

    public enum SapXep {
        TangDan,
        GiamDan
    }
}
