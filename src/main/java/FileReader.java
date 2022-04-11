import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

public final class FileReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleClient.class);

    List<String> loadLastNames(String filename) throws URISyntaxException, IOException {
        URL resource = this.getClass().getClassLoader().getResource(filename);

        if (null == resource) {
            //LOGGER.error("file not found! " + FILE_NAME);
            throw new IllegalArgumentException("file not found! " + filename);
        }

        return Files.readAllLines(new File(resource.toURI()).toPath());
    }
}
