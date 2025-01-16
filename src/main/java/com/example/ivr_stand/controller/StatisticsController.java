package com.example.ivr_stand.controller;

import com.example.ivr_stand.model.SearchRequest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.core.io.ClassPathResource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private static final String STATISTICS_DIR = "statistics";
    private static final String EXCEL_FILE_NAME = "data.xlsx";

    // Метод для получения пути к файлу Excel
    private Path getStatisticsPath() throws IOException {
        Path statisticsDir = Paths.get("target/statistics");
        if (!Files.exists(statisticsDir)) {
            Files.createDirectories(statisticsDir);
        }
        return statisticsDir.resolve(EXCEL_FILE_NAME);
    }

    // Метод для добавления информации о пользователе в Excel
    @PostMapping("/user-info")
    public String handleSearch(@ModelAttribute SearchRequest request) {
        String userInput = request.getQuery();
        if (userInput == null || userInput.trim().isEmpty()) {
            return "Ошибка: Поле поиска не может быть пустым!";
        }

        try {
            appendToExcelPageOne(userInput);
            return "redirect:/";
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка при записи в Excel: " + e.getMessage();
        }
    }

    // Метод для обновления статистики по услуге (например, услуга получила ответ или нужна помощь)
    @PostMapping("/services/{action}")
    public String updateServiceStatistics(@PathVariable("action") String action, @RequestParam String serviceId, @ModelAttribute SearchRequest request, @RequestParam String serviceName) {
        try {
            int service_id = Integer.parseInt(serviceId);
            String searchText = request.getQuery();
            if ("answered".equalsIgnoreCase(action)) {
                updateServiceStatisticsExcel(service_id, true, "", serviceName);
                return "redirect:/";
            } else if ("help-needed".equalsIgnoreCase(action)) {
                updateServiceStatisticsExcel(service_id, false, searchText, serviceName);
                return "redirect:/";
            } else {
                return "Ошибка: Неверное действие. Используйте 'answered' или 'help-needed'.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка при обновлении статистики: " + e.getMessage();
        }
    }

    // Метод для добавления информации о пользователе на первой странице Excel
    private void appendToExcelPageOne(String contactInfo) throws IOException {
        Path filePath = getStatisticsPath();
        File file = filePath.toFile();
        boolean fileExists = file.exists();

        Workbook workbook;
        Sheet sheet;

        if (fileExists) {
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
            }
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Контакты");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Контактная информация");
            headerRow.createCell(1).setCellValue("Дата и время добавления");
        }

        int rowNum = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(contactInfo);
        row.createCell(1).setCellValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }

        workbook.close();
    }


    // Метод для обновления статистики по услугам в Excel
    private void updateServiceStatisticsExcel(int serviceId, boolean isAnswered, String contact, String serviceName) throws IOException {
        Path filePath = getStatisticsPath();
        File file = filePath.toFile();
        boolean fileExists = file.exists();

        Workbook workbook;
        Sheet serviceSheet;

        if (fileExists) {
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(fis);
                serviceSheet = workbook.getSheet("Услуги");
                if (serviceSheet == null) {
                    serviceSheet = createServicesSheet(workbook);
                }
            }
        } else {
            workbook = new XSSFWorkbook();
            serviceSheet = createServicesSheet(workbook);
        }

        boolean serviceFound = false;
        for (int i = 1; i <= serviceSheet.getLastRowNum(); i++) {
            Row row = serviceSheet.getRow(i);
            if (row != null && row.getCell(0).getNumericCellValue() == serviceId) {
                Cell targetCell = isAnswered ? row.getCell(2) : row.getCell(3);
                if (targetCell == null) {
                    targetCell = row.createCell(isAnswered ? 2 : 3);
                    targetCell.setCellValue(0);
                }
                targetCell.setCellValue(targetCell.getNumericCellValue() + 1);
                serviceFound = true;
                break;
            }
        }

        // Если услуга не найдена, создаем новую строку
        if (!serviceFound) {
            int rowNum = serviceSheet.getLastRowNum() + 1;
            Row row = serviceSheet.createRow(rowNum);
            row.createCell(0).setCellValue(serviceId);
            row.createCell(1).setCellValue(serviceName);
            row.createCell(2).setCellValue(isAnswered ? 1 : 0);
            row.createCell(3).setCellValue(isAnswered ? 0 : 1);
        }

        // Если нужна помощь, добавляем контактную информацию на другой лист
        if (!isAnswered && !contact.isEmpty()) {
            appendContactInfoToAnotherSheet(workbook, contact);
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        }

        workbook.close();
    }

    // Метод для создания листа статистики по услугам, если его нет
    private Sheet createServicesSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Услуги");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Название услуги");
        headerRow.createCell(2).setCellValue("Получил ответ");
        headerRow.createCell(3).setCellValue("Нужна была помощь");
        return sheet;
    }

    // Метод для добавления контактной информации в отдельный лист Excel
    private void appendContactInfoToAnotherSheet(Workbook workbook, String contactInfo) throws IOException {
        Sheet contactSheet = workbook.getSheet("Контактная информация");
        if (contactSheet == null) {
            contactSheet = workbook.createSheet("Контактная информация");
            Row headerRow = contactSheet.createRow(0);
            headerRow.createCell(0).setCellValue("Контактная информация");
            headerRow.createCell(1).setCellValue("Дата и время добавления");
        }

        int rowNum = contactSheet.getLastRowNum() + 1;
        Row row = contactSheet.createRow(rowNum);
        row.createCell(0).setCellValue(contactInfo);
        row.createCell(1).setCellValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // Записываем изменения в файл
        try (FileOutputStream fos = new FileOutputStream(getStatisticsPath().toFile())) {
            workbook.write(fos);
        }
    }
}
