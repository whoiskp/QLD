/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ThuaDat;
import common.Common;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MyPC
 */
public class ModuleController {

    /**
     * Sắp xếp mảng theo địa chỉ Thửa đất sử dụng thuật toán HeapSort
     *
     * @param listNotSorted Mảng chưa sắp xếp
     *
     * @return Mảng đã sắp xếp theo thứ tự tăng dần số nhà
     */
    public static ArrayList<ThuaDat> HeapsortByAddress(ArrayList<ThuaDat> listNotSorted) {
        return HeapSort.Sort(listNotSorted, Common.TieuChiSoSanh.DiaChi, Common.SapXep.TangDan);
    }

    /**
     * Chèn dữ liệu Thửa đất vào mảng sao cho vẫn đảm bảo tiêu chí tăng dần theo số nhà
     * vì mảng đã sắp xếp nên địa chỉ đã tăng dần.
     * Ý tưởng: Tạo mảng để chứa kết quả, thực hiện 3 bước
     * B1: Lấy các phần tử có địa chỉ <= địa chỉ Thửa đất cần thêm bỏ vào mảng Kết quả
     * B2: Chèn dữ liệu vào.
     * B3: Chèn nốt các phần tử có địa chỉ > Địa chỉ thửa đất cần thêm
     *
     * @param listOrdered Danh sách các Thửa Đất ĐÃ SẮP XẾP theo tiêu chí tăng dần địa chỉ
     * @param thuaDat     Dữ liệu cần chèn
     *
     * @return Trả về mảng chèn phần tử vào
     */
    public static ArrayList<ThuaDat> InsertToListOrdered(ArrayList<ThuaDat> listOrdered, ThuaDat thuaDat) {
        ArrayList<ThuaDat> result;
        result = new ArrayList<>();
        int i = 0;
        // Đưa các dữ liệu có địa chỉ <= địa chỉ của thửa đất cần thêm vào mảng kết quả (Để đảm bảo thứ tự)
        while (listOrdered.get(i).compareTo(thuaDat) <= 0) {
            result.add(listOrdered.get(i));
            i++;
        }
        // Thêm Dữ liệu thửa đất vào ví trí thích hợp
        result.add(thuaDat);

        // Đưa nốt các phần tử có địa chỉ > địa chỉ của Thửa Đất vào mảng kết quả
        for (; i < listOrdered.size(); i++) {
            result.add(listOrdered.get(i));
        }
        return result;
    }

    /**
     * Trả về danh sách các vị trí tìm được trong mảng
     *
     * @param listThuaDat danh sách cần tìm
     * @param query       từ khóa cần tìm
     *
     * @return danh sách vị trí trong mảng nếu tìm thấy | không tìm thấy result = null
     */
    public static ArrayList<Integer> SearchData(ArrayList<ThuaDat> listThuaDat, String query) {
        ArrayList<Integer> result; // Tạo mảng integer chứa chỉ số kết quả tìm được
        result = new ArrayList<>();
        for (int i = 0; i < listThuaDat.size(); i++) {
            if (listThuaDat.get(i).toString().contains(query)) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * Xóa phần tử theo tiêu chí
     *
     * @param listThuaDat Danh sách thửa đất chứa dữ liệu cần xóa
     * @param query       tiêu chí xóa
     *
     * @return mảng kết quả sau khi xóa
     */
    public static ArrayList<ThuaDat> DeleteByQuery(ArrayList<ThuaDat> listThuaDat, String query) {
        ArrayList<Integer> resultIndexOfDataToDelete = SearchData(listThuaDat, query);

        // Thực hiện việc xóa các phần tử thỏa điều kiện tìm được trong mảng listThuaDat
        for (Integer index : resultIndexOfDataToDelete) {
            listThuaDat.remove(index);
        }

        return listThuaDat; // Trả về mảng đã xóa
    }

    /**
     * Gộp 2 danh sách lại với nhau theo tiêu chí yêu cầu
     *
     * @param ds1     Danh sách 1
     * @param ds2     Danh sách 2
     * @param tieuChi Tiêu chí Gộp
     * @param sapXep  Tăng dần | Giảm dần
     *
     * @return Mảng đã được gộp
     */
    public static ArrayList<ThuaDat> MergeList(ArrayList<ThuaDat> ds1, ArrayList<ThuaDat> ds2, Common.TieuChiSoSanh tieuChi, Common.SapXep sapXep) {
        ArrayList<ThuaDat> result = new ArrayList<>();
        ds1.forEach((s) -> result.add(s));
        ds2.forEach((s) -> result.add(s));
        ArrayList<ThuaDat> ketqua = HeapSort.Sort(result, tieuChi, sapXep);
        return ketqua;
    }

    /**
     * Đọc dữ liệu từ file Excel lưu thành mảng
     *
     * @param filePath Đường dẫn đến file đọc dữ liệu
     *
     * @return Mảng chứa dữ liệu
     */
    public static ArrayList<ThuaDat> ReadDataFromXLSFile(String filePath) {
        try {
            return ThuaDatExcelHelper.ReadExcelThuaDat(filePath);
        } catch (IOException ex) {
            Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Ghi dữ liệu ra file Excel
     *
     * @param filePath Đường dẫn file ghi dữ liệu
     * @param listData Dữ liệu cần ghi ra file
     */
    public static void WriteDataToXLSFile(String filePath, ArrayList<ThuaDat> listData) {
        try {
            ThuaDatExcelHelper.CreateExcel(filePath, listData);
        } catch (IOException ex) {
            Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
