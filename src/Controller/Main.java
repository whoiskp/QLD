/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author MyPC
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        QuanLyDat qld = new QuanLyDat();
        qld.LoadDataToSystem();
        
        System.out.print("Các DS hiện có trong hệ thống: ");
        common.ConfigData.sysData.forEach((k, v) -> {
            System.out.print(k + "\t");
        });
        System.out.println("");

        int x = -1;
        while (x <= 8 && x != 0) {
            System.out.print(common.ConfigData.strMenu);
            x = Integer.parseInt(sc.nextLine());
            switch (x) {
                case 1:
                    qld.ReadFromExcel();
                    break;
                case 2:
                    qld.ShowDataInList();
                    break;
                case 3:
                    qld.ChenThuaDatVaoDS();
                    break;
                case 4:
                    qld.XoaDuLieuTheoYC();
                    break;
                case 5:
                    qld.SapXepTheoTieuChiBanDau();
                    break;
                case 6:
                    qld.TimKiemThongTin();
                    break;
                case 7:
                    qld.MergeDanhSach();
                    break;
                case 8:
                    qld.WriteDataToExcel();
                    break;
            }
        }
    }
}
