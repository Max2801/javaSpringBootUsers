package test1.com.test1;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.stereotype.Component;

@Component
public class ImageDirectoryInitializer implements CommandLineRunner {
    
    private static String directory;

    public static String getDirectory() {
        return directory;
    }

    @Autowired
    MultipartProperties mp;

    @Override
    public void run(String... args) throws Exception {
        var imageDirectory = new File(mp.getLocation());
        if(imageDirectory.isDirectory() && imageDirectory.canWrite()) {
            directory = imageDirectory.getAbsolutePath() + File.separator;
        }
        else
            throw new Exception("Необходимо указать корректный путь multipart.location в application.properties с правами на запись");
    }    
}
