/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import common.ConfigData;

/**
 *
 * @author MyPC
 */
public class ThuaDat implements Comparable<ThuaDat> {

    private String diachi, chusohuu, loainha, mucdichsudung;
    private float dientich;
    private long giatien;

    public ThuaDat(String diachi, String chusohuu, String loainha, String mucdichsudung, float dientich, long giatien) {
        this.diachi = diachi;
        this.chusohuu = chusohuu;
        this.loainha = loainha;
        this.mucdichsudung = mucdichsudung;
        this.dientich = dientich;
        this.giatien = giatien;
    }

    public ThuaDat() {
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getChusohuu() {
        return chusohuu;
    }

    public void setChusohuu(String chusohuu) {
        this.chusohuu = chusohuu;
    }

    public String getLoainha() {
        return loainha;
    }

    public void setLoainha(String loainha) {
        this.loainha = loainha;
    }

    public String getMucdichsudung() {
        return mucdichsudung;
    }

    public void setMucdichsudung(String mucdichsudung) {
        this.mucdichsudung = mucdichsudung;
    }

    public float getDientich() {
        return dientich;
    }

    public void setDientich(float dientich) {
        this.dientich = dientich;
    }

    public long getGiatien() {
        return giatien;
    }

    public void setGiatien(long giatien) {
        this.giatien = giatien;
    }

    @Override
    public String toString() { // Format data in lên cho đẹp xem thêm tại https://dzone.com/articles/java-string-format-examples
        return String.format(ConfigData.formatData, diachi, dientich, chusohuu, loainha, mucdichsudung, giatien);
    }

    /**
     * Đề bài yêu cầu sắp xếp tăng dần theo số nhà
     * Hàm so sánh số nhà lấy ra từ địa chỉ nhà
     *
     * @return 1: lớn hơn | 0: bằng nhau | -1: nhỏ hơn
     */
    @Override
    public int compareTo(ThuaDat t) {
        int soNha1 = Integer.parseInt(this.diachi.substring(0, this.diachi.indexOf(" "))); // số nhà 1
        int soNha2 = Integer.parseInt(t.diachi.substring(0, t.diachi.indexOf(" "))); // số nhà cần so sánh
        return Integer.compare(soNha1, soNha2);
    }

    public int SosanhTheoTieuChi(ThuaDat t, ConfigData.TieuChiSoSanh tieuChiSoSanh) {
        switch (tieuChiSoSanh) {
            case GiaTien:
                return Long.compare(this.giatien, t.giatien); // Theo tiêu chí Giá tiền
            case DienTich:
                return Float.compare(this.dientich, t.dientich); // Theo tiêu chí Diện tích
            default:
                return compareTo(t); // Mặc định là sắp xếp theo tiêu chí Địa chỉ
        }
    }
}
