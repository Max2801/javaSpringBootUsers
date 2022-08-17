package test1.com.test1.controller;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping(value = "/images")
@Order
public class ImageUploadController {    
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> upload(@RequestParam("picUpl") MultipartFile picUpl) {
        String originalFileName = picUpl.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        final String validContentType = "image/";
        final List<String> validExtensionList = Arrays.asList(".jpg", ".jpeg", ".gif", ".png");

        if(!picUpl.isEmpty()) {
            if(picUpl.getContentType().startsWith(validContentType) && validExtensionList.contains(extension)) {                
                try{
                    File newFile = new File(UUID.randomUUID().toString() + extension);
                    picUpl.transferTo(newFile);
                    return new ResponseEntity<>(newFile.toURI().toString(), HttpStatus.CREATED);
                }
                catch(IOException ex){
                    return new ResponseEntity<>(String.format("Error in file upload [%s]: %s", originalFileName,
                            ex.getMessage()),
                        HttpStatus.INTERNAL_SERVER_ERROR);
                }                
            }
            else {
                return new ResponseEntity<>("Need only image", HttpStatus.RESET_CONTENT);
            }
        }
        else {
            return new ResponseEntity<>("Empty file", HttpStatus.NO_CONTENT);
        }         
    }
}
