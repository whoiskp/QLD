/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ThuaDat;
import common.Common;
import java.util.ArrayList;

/**
 * docs: https://ilaptrinh.wordpress.com/2013/01/11/heap-sort/
 * Sắp xếp tăng dần danh sách thửa đất
 *
 * @author MyPC
 */
public class HeapSort {
//    private static int[] array = new int[]{5, 3, 6, 4, 8, 9, 1, 10};
//    private static ArrayList<ThuaDat> listThuaDat;
//    public static void main(String[] args) {
//        try {
//            init();
//            heapSort();
//            for (ThuaDat i : listThuaDat) {
//                System.out.println(i.toString());
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(HeapSort.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    private static void init() throws IOException {
//        String filePath = "D:\\DemoQLD\\Quan3.xls";
//        listThuaDat = ThuaDatExcelHelper.ReadExcelThuaDat(filePath);
//    }

    /**
     * Thực hiện việc sắp xếp các Thửa đất trong danh sách theo thứ tự tăng dần Số nhà
     *
     * @param listThuaDat   Danh sách các thửa đất chưa sắp xếp
     * @param tieuChiSoSanh Tiêu chí so sánh
     * @param kieuSapXep    Tăng dần | Giảm dần
     *
     * @return Danh sách đã sắp xếp
     */
    public static ArrayList<ThuaDat> Sort(ArrayList<ThuaDat> listThuaDat, Common.TieuChiSoSanh tieuChiSoSanh, Common.SapXep kieuSapXep) {

        int length = listThuaDat.size();

        buildMaxHeap(listThuaDat, length, tieuChiSoSanh, kieuSapXep);
        for (int i = length - 1; i > 0; i--) {
            ThuaDat temp = listThuaDat.get(0);
            listThuaDat.set(0, listThuaDat.get(i));
            listThuaDat.set(i, temp);
            maxHeapify(listThuaDat, 1, i, tieuChiSoSanh, kieuSapXep);
        }

        return listThuaDat;
    }

    private static void buildMaxHeap(ArrayList<ThuaDat> array, int heapSize, Common.TieuChiSoSanh tieuChiSoSanh, Common.SapXep kieuSapXep) {
        if (array == null) {
            throw new NullPointerException("null");
        }
        if (array.size() <= 0 || heapSize <= 0) {
            throw new IllegalArgumentException("illegal");
        }
        if (heapSize > array.size()) {
            heapSize = array.size();
        }

        for (int i = heapSize / 2; i > 0; i--) {
            maxHeapify(array, i, heapSize, tieuChiSoSanh, kieuSapXep);
        }
        // Bỏ cmt: In ra cây để xem kết quả sau khi build cây MaxHeap
        //        for (int i : array) {
        //            System.out.println(i + " ");
        //        }
    }

    /**
     * Vì sắp xếp tăng dần nên tạo MaxHeap
     * Tạo Max Heap: Giá trị của mỗi node >= giá trị của các node con nó
     * => Node lớn nhất là node gốc
     *
     * Nếu ycbt là sắp xếp giảm dần => Cần viết minHeap.
     * + Min Heap: Giá trị của mỗi node <= giá trị của các node con nó
     * => Node nhỏ nhất là node gốc
     */
    private static void maxHeapify(ArrayList<ThuaDat> array, int index, int heapSize, Common.TieuChiSoSanh tieuChiSoSanh, Common.SapXep kieuSapXep) {
        int l = index * 2; // giá trị bên trái
        int r = l + 1; // giá trị bên phải
        int largest; // giá trị lớn nhất

        if (l <= heapSize && Common.SoSanhTheoTieuChi(array.get(l - 1), array.get(index - 1), tieuChiSoSanh, kieuSapXep)) {
            largest = l;
        } else {
            largest = index;
        }

        if (r <= heapSize && Common.SoSanhTheoTieuChi(array.get(r - 1), array.get(largest - 1), tieuChiSoSanh, kieuSapXep)) {
            largest = r;
        }

        if (largest != index) {
            ThuaDat temp = array.get(index - 1);
            array.set(index - 1, array.get(largest - 1));
            array.set(largest - 1, temp);
            maxHeapify(array, largest, heapSize, tieuChiSoSanh, kieuSapXep);
        }
    }
}
