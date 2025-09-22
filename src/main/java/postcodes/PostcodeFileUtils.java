package postcodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostcodeFileUtils {

    // Read list of postcodes from file
    public static List<String> readPostcodesFromFile(String filePath) {
        List<String> postcodes = new ArrayList<>();
        try (InputStream inputStream = PostcodeFileUtils.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + filePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                postcodes = reader.lines().collect(Collectors.toList());
            }
        } catch (IOException e) {
            System.err.println("Error reading resource: " + e.getMessage());
        }
        return postcodes;
    }

    // Save list of postcodes to file
    public static void savePostcodesToFile(List<String> postcodes, String filePath) {
        try {
            Files.write(Path.of(filePath), postcodes);
            System.out.println("Postcodes saved to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving postcodes: " + e.getMessage());
        }
    }

}
