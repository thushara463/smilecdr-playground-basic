import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class FileReaderTest {

    FileReader fileReader;

    @Before
    public void setUp() {
         fileReader = new FileReader();
    }

    @Test
    public void testFileReaderThrowException() {
        String filename = "names.txt";

        Assert.assertThrows(IllegalArgumentException.class, () -> fileReader.loadLastNames(filename));
    }

    @Test
    public void testFileNames() throws URISyntaxException, IOException {
        String filename = "lastnames.txt";
        List<String> lastnames = fileReader.loadLastNames(filename);

        Assert.assertFalse(lastnames.isEmpty());
        Assert.assertEquals(20, lastnames.size());
    }
}