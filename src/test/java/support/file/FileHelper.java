package support.file;

import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileHelper {
    ClassLoader classLoader = FileHelper.class.getClassLoader();

    public void cleanDirectory(String path) {
        URL resourcesUrl = classLoader.getResource(path);
        if (resourcesUrl != null) {
            File folder = new File(resourcesUrl.getFile());
            try {
                FileUtils.cleanDirectory(folder);
            } catch (IOException e) {
                System.err.println("Cannot clean up target directory");
            }
        } else {
            System.err.println("Cannot retrieve path.");
        }
    }

    public File getFile(String path) {
        URL resourcesUrl = classLoader.getResource(path);
        return (resourcesUrl != null) ? new File(resourcesUrl.getFile()) : null;
    }

    public <T> T readJson(String path, TypeReference<T> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = this.getFile(path);
        try {
            return objectMapper.readValue(jsonFile, typeReference);
        } catch (IllegalArgumentException | IOException e) {
            System.err.println("Cannot read JSON file ");
            return null;
        }
    }


    public File getFileFromZipArchive(String zipFilePath, String fileNameToExtract, String outDirectory) {
        String outputDirectory = outDirectory;
        URL resourcesUrl = classLoader.getResource(zipFilePath);
        URL resourcesOutputUrl = classLoader.getResource(outputDirectory);
        try {
            try (ZipFile zipFile = new ZipFile(new File(resourcesUrl.getFile()))) {
                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    if (entry.getName().equals(fileNameToExtract)) {
                        // Get input stream from the entry
                        try (InputStream inputStream = zipFile.getInputStream(entry)) {

                            // Create output directory if it doesn't exist
                            File testOutDir = new File(resourcesOutputUrl.getFile());

                            // Create output file
                            String outputPath = testOutDir.getAbsolutePath() + File.separator + fileNameToExtract;
                            try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
                                byte[] buffer = new byte[1024];
                                int bytesRead;
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                                System.out.println("File extracted successfully to: " + outputPath);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            URL outputFileURL = classLoader.getResource(outputDirectory + "/" + fileNameToExtract);
            return new File(outputFileURL.getFile());
        } catch (NullPointerException e) {
            System.err.println("Cannot unpack file");
            return null;
        }

    }

    private InputStream getInputStreamFromFile(File file) {
        InputStream targetStream = null;
        try {
            targetStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return targetStream;
    }

    private List<String[]> getCSVContent(File file) {

        List<String[]> content = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(this.getInputStreamFromFile(file)))) {
            content = csvReader.readAll();
        } catch (IOException | CsvException e) {
            System.err.println("Cannot read CSV content");
        }
        return content;
    }

    public void checkCSVStringInFile(File csvFile, String[] checkedStringArray, int index) {
        List<String[]> content = this.getCSVContent(csvFile);
        Assertions.assertArrayEquals(checkedStringArray, content.get(index));
    }

    public void checkXLSXCellInFile(File XLSXFile, String checkedContent, String sheetTitle, int row, int column) {
        XLS xls = new XLS(XLSXFile);
        Assertions.assertEquals(
                checkedContent,
                xls.excel.getSheet(sheetTitle)
                        .getRow(row)
                        .getCell(column)
                        .getStringCellValue()
        );
    }


}

