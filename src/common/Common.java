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
public class Common {

    public static Map<String, ArrayList<ThuaDat>> sysData = new HashMap<String, ArrayList<ThuaDat>>();

    // Kiểu dữ liệu enum, giá trị mặc định bắt đầu bằng 0
    public enum menu {  // Quản lý tác vụ menu
        importExcel, // Đọc từ file Excel
        exportExcel, // Xuất dữ liệu ra file Excel
        show, // Xem dữ liệu các danh sách hiện có
        heapsort, // Sắp xếp danh sách sử dụng Heapsort
        insert, // Chén dữ liệu vào danh sách đảm bảo thứ tự
        delete, // Xóa dữ liệu theo thông tin
        search, // Tìm kiếm dữ liệu
        merge           // Gộp 2 danh sách
    }
    public static final String formatData = "|%-30s|%15s|%-25s|%-12s|%-20s|%20s|"; // Định dạng mặc định để xuất dữ liệu ra màn hình
    public static final String titleShow = String.format("|%4s" + formatData, "STT", "Địa chỉ", "Diện tích", "Chủ sở hữu hiện tại", "Loại nhà", "Mục đích sử dụng", "Giá tiền");
    public static String strMenu
            = "----------- Menu ---------\n"
            + "1. Đọc file Excel\n"
            + "2. Hiển thị danh sách sinh viên\n"
            + "3. Chèn một sinh viên\n"
            + "4. Xóa một sinh viên\n"
            + "5. Sắp xếp danh sách\n"
            + "6. Tim kiem\n"
            + "7. Gộp danh sách phụ vào danh sách chính\n"
            + "8. Xuất file Excel\n"
            + "0. Thoat\n"
            + "--------------------------\n"
            + "Nhap x: ";

    public static boolean SoSanhTheoTieuChi(ThuaDat td1, ThuaDat td2, Common.TieuChiSoSanh tieuChiSoSanh, Common.SapXep kieuSapXep) {
        if (kieuSapXep == Common.SapXep.TangDan) {
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
