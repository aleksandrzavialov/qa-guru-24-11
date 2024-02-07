package tests;

import org.junit.jupiter.api.AfterAll;
import support.file.FileHelper;

public class TestBase {

    @AfterAll
    static void afterAll() {
        FileHelper fh = new FileHelper();
        fh.cleanDirectory("files/out" );
    }
}
