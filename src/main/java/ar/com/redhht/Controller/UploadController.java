package ar.com.redhht.Controller;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    private final String uploadDir = "." + File.separator + "images";

    @RequestMapping({"/upload"})
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile multipartFile, ServletRequest servletRequest) {
        try {
            HashMap<String, String> response = new HashMap<>();
            String filePath = saveImage(multipartFile);
            response.put("location", "/images/" + multipartFile.getOriginalFilename());
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            return ResponseEntity.status((HttpStatusCode)HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    @GetMapping({"/images/{filename}"})
    public ResponseEntity<Resource> getImage(@PathVariable String filename, HttpServletResponse httpServletResponse, @Value("${spring.web.resources.cache.cachecontrol.max-age}") int age) {
        httpServletResponse.addHeader("Cache-Control", "max-age=" + age);
        try {
            Path filePath = Paths.get(this.uploadDir, new String[0]).resolve(filename);
            UrlResource urlResource = new UrlResource(filePath.toUri());
            if (urlResource.exists())
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(urlResource);
            return ResponseEntity.notFound().build();
        } catch (MalformedURLException e) {
            return ResponseEntity.status((HttpStatusCode)HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(this.uploadDir, new String[0]);
        if (!Files.exists(uploadPath, new java.nio.file.LinkOption[0]))
            Files.createDirectories(uploadPath, (FileAttribute<?>[])new FileAttribute[0]);
        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
        return filePath.toString();
    }
}