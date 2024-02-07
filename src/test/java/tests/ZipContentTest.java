package tests;

import com.codeborne.pdftest.PDF;
import static org.assertj.core.api.Assertions.assertThat;
import data.CSVData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.file.FileHelper;
import java.io.File;
import java.io.IOException;


public class ZipContentTest extends TestBase{

    FileHelper fileHelper = new FileHelper();
    String outputDirectory = "files/out";
    @Test
    @DisplayName("Test extracting PDF file and check its attributes")
    void extractPDFAndParseTest() throws IOException {
        File pdfFilePath = fileHelper.getFileFromZipArchive("files/in/files_first_task.zip",
                "pdf-test.pdf", outputDirectory);
        PDF pdfFile = new PDF(pdfFilePath);
        assertThat(pdfFile.creator).isEqualTo("Acrobat PDFMaker 7.0.7 for Word");
        assertThat(pdfFile.author).isEqualTo("Yukon Department of Education");
        assertThat(pdfFile.content.length).isGreaterThan(0);
    }
    @Test
    @DisplayName("Test extracting CSV file and check first row")
    void extractCSVAndParseTest() {
        File csvFilePath = fileHelper.getFileFromZipArchive("files/in/files_first_task.zip",
                "Sample-Spreadsheet-10-rows.csv", outputDirectory);
        fileHelper.checkCSVStringInFile(csvFilePath, CSVData.FIRSTROW.getContent(), 0);

    }

    @Test
    @DisplayName("Test extracting XLSX file and checking a row column content")
    void extractXLSXAndParseTest() {
        File xlsxFilePath = fileHelper.getFileFromZipArchive("files/in/files_first_task.zip",
                "file_example_XLSX_10.xlsx", outputDirectory);
        fileHelper.checkXLSXCellInFile(xlsxFilePath, "Melgar", "Sheet1",8 , 2 );

    }


}
