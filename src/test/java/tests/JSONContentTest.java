package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import model.TVSeries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.file.FileHelper;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JSONContentTest {
    FileHelper fileHelper = new FileHelper();
    @Test
    @DisplayName("Test checking JSON content")
    void extractCheckJSONTest() {
        TypeReference<List<TVSeries>> tvSeriestypeReference = new TypeReference<>() {};
        List<TVSeries> seriesList = fileHelper.readJson("files/in/TV2.json", tvSeriestypeReference);
        assertThat(seriesList).hasSize(2);
        assertThat(seriesList.get(0).getFirstYear()).isEqualTo(2008);
        assertThat(seriesList.get(1).getActors().get(0).getLastName()).isEqualTo("Carell");
    }
}
