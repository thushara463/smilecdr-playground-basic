import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

/**
 * Read a file content from the resources folder
 * @author thushara
 */
public final class FileReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleClient.class);

    /**
     * Read lastnames from the given file in the resources folder
     * @param filename - name of the file
     * @return - List<String> with lastnames
     * @throws URISyntaxException - URISyntaxException if the URI syntax is not valid
     * @throws IOException - IOException if the content is not readable
     */
    List<String> loadLastNames(String filename) throws URISyntaxException, IOException {
        URL resource = this.getClass().getClassLoader().getResource(filename);

        if (null == resource) {
            //LOGGER.error("file not found! " + FILE_NAME);
            throw new IllegalArgumentException("file not found! " + filename);
        }

        return Files.readAllLines(new File(resource.toURI()).toPath());
    }
}
