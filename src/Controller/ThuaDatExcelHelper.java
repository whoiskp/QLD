/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ThuaDat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

/**
 * Class hỗ trợ việc xử lí file Excel đối với đối tượng Thửa Đất
 *
 * @version https://o7planning.org/vi/11259/doc-ghi-file-excel-trong-java-su-dung-apache-poi
 * @author MyPC
 */
public class ThuaDatExcelHelper {

    /**
     * Định dạng Header font chữ đậm hơn.
     */
    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    /**
     * Tạo file excel với thông tin được lưu vào
     *
     * @param filePath Link file sau khi tạo
     * @param List     dữ liệu ghi ra file
     * @throws java.io.IOException
     */
    public static void CreateExcel(String filePath, ArrayList<ThuaDat> List) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Student sheet");
        int rowNum = 0;
        Cell cell;
        Row row;

        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rowNum);

        //STT
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("STT");
        cell.setCellStyle(style);
        //Địa chỉ
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Địa chỉ");
        cell.setCellStyle(style);
        //Diện tích
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Diện tích");
        cell.setCellStyle(style);
        // Chủ sở hữu hiện tại
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Chủ sở hữu hiện tại");
        cell.setCellStyle(style);
        //Loại nhà
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Loại nhà");
        cell.setCellStyle(style);
        //Mục đích sử dụng
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Mục đích sử dụng");
        cell.setCellStyle(style);
        //Giá Tiền
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Giá Tiền");
        cell.setCellStyle(style);

        //DATA
        for (ThuaDat td : List) {
            rowNum++;
            row = sheet.createRow(rowNum);

            //STT
            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(rowNum);
            //Địa chỉ
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(td.getDiachi());
            //Diện tích
            cell = row.createCell(2, CellType.NUMERIC);
            cell.setCellValue(td.getDientich());
            //Chủ sở hữu hiện tại
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(td.getChusohuu());
            //Loại nhà
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(td.getLoainha());
            //Mục đích sử dụng
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(td.getLoainha());
            //Giá tiền
            cell = row.createCell(6, CellType.NUMERIC);
            cell.setCellValue(td.getGiatien());
        }
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        System.out.println("Đã tạo file ở địa chỉ: " + file.getAbsolutePath());
    }

    /**
     * Đọc dữ liệu từ file Excel lưu thành danh sách thửa đất trong Hệ thống.
     *
     * @exception IOException Chưa xử lý lỗi nếu file không đúng định dạng Thửa Đất cần đọc vào
     * @param filePath Đường dẫn đến file XLS
     *
     * @return Trả về danh sách đọc được từ file
     */
    public static ArrayList<ThuaDat> ReadExcelThuaDat(String filePath) throws IOException {
        FileInputStream inputStream = null;
        ArrayList<ThuaDat> list = new ArrayList<>();
        try {
            // Đọc một file XSL.
            inputStream = new FileInputStream(new File(filePath));
            // Đối tượng workbook cho file XSL.
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            // Lấy ra sheet đầu tiên từ workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            // Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
            Iterator<Row> rowIterator = sheet.iterator();

//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//
//                // Lấy Iterator cho tất cả các cell của dòng hiện tại.
//                Iterator<Cell> cellIterator = row.cellIterator();
//
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//
//                    // Đổi thành getCellType() nếu sử dụng POI 4.x
//                    CellType cellType = cell.getCellTypeEnum();
//
//                    switch (cellType) {
//                        case _NONE:
//                            System.out.print("");
//                            System.out.print("\t");
//                            break;
//                        case BOOLEAN:
//                            System.out.print(cell.getBooleanCellValue());
//                            System.out.print("\t");
//                            break;
//                        case BLANK:
//                            System.out.print("");
//                            System.out.print("\t");
//                            break;
//                        case FORMULA:
//                            // Công thức
//                            System.out.print(cell.getCellFormula());
//                            System.out.print("\t");
//
//                            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
//
//                            // In ra giá trị từ công thức
//                            System.out.print(evaluator.evaluate(cell).getNumberValue());
//                            break;
//                        case NUMERIC:
//                            System.out.print(cell.getNumericCellValue());
//                            System.out.print("\t");
//                            break;
//                        case STRING:
//                            System.out.print(cell.getStringCellValue());
//                            System.out.print("\t");
//                            break;
//                        case ERROR:
//                            System.out.print("!");
//                            System.out.print("\t");
//                            break;
//                    }
//                }
//                System.out.println("");
//            }

            if (sheet != null && sheet.getLastRowNum() > 0) {
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null && row.getLastCellNum() > 0) {
                        ThuaDat td = new ThuaDat();
                        // Cell 0: STT
                        // Cell 1: Địa chỉ
                        td.setDiachi(row.getCell(1).getStringCellValue());
                        // Cell 2: Diện Tích
                        td.setDientich((float) row.getCell(2).getNumericCellValue());
                        // Cell 3: Chủ sở hữu
                        td.setChusohuu(row.getCell(3).getStringCellValue());
                        // Cell 4: Loại nhà
                        td.setLoainha(row.getCell(4).getStringCellValue());
                        // Cell 5: Mục đích sử dụng
                        td.setMucdichsudung(row.getCell(5).getStringCellValue());
                        // Cell 6: Giá Tiền
                        td.setGiatien((long) row.getCell(6).getNumericCellValue());
                        list.add(td);
                    }
                }
                workbook.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ThuaDatExcelHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(ThuaDatExcelHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
}
