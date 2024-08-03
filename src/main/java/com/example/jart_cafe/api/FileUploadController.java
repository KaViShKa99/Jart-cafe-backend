package com.example.jart_cafe.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

//@RestController
//@RequestMapping("/api/images")
//public class FileUploadController {
//
//    private final String UPLOAD_DIR = "uploads/";
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
//        try {
//            // Save the file to the specified directory
//            String fileName = file.getOriginalFilename();
//            Path path = Paths.get(UPLOAD_DIR + fileName);
//            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//
//            // Generate the image URL
//            String baseUrl = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
//            String imageUrl = baseUrl + "/api/images/" + fileName;
//
//            return ResponseEntity.ok(imageUrl);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
//        }
//    }
//    @GetMapping("/{filename}")
//    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
//        try {
//            // Load the file as a resource
//            Path path = Paths.get(UPLOAD_DIR + filename);
//            Resource resource = new UrlResource(path.toUri());
//
//            if (resource.exists() || resource.isReadable()) {
//                return ResponseEntity.ok()
//                        .contentType(MediaType.IMAGE_JPEG)
//                        .body(resource);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//        } catch (MalformedURLException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @DeleteMapping("/delete/{fileName}")
//    public ResponseEntity<String> deleteImage(@PathVariable String fileName) {
//        try {
//            // Delete the file from the specified directory
//            Path path = Paths.get(UPLOAD_DIR + fileName);
//            System.out.println(fileName);
//            if (Files.exists(path)) {
//                Files.delete(path);
//                System.out.println("Image deleted successfully");
//                return ResponseEntity.ok("Image deleted successfully");
//            } else {
//                System.out.println("Image not found");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
//            }
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image deletion failed");
//        }
//    }
//
//
//}

@RestController
@RequestMapping("/api/images")
public class FileUploadController {

    private final String ARTWORK_UPLOAD_DIR = "uploads/artwork/";
    private final String USER_UPLOAD_DIR = "uploads/user/";

    @PostMapping("/upload/artwork")
    public ResponseEntity<String> uploadArtworkImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return uploadImage(file, request, ARTWORK_UPLOAD_DIR);
    }

    @PostMapping("/upload/user")
    public ResponseEntity<String> uploadUserImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return uploadImage(file, request, USER_UPLOAD_DIR);
    }

    private ResponseEntity<String> uploadImage(MultipartFile file, HttpServletRequest request, String uploadDir) {
        try {
            // Ensure the upload directory exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file to the specified directory
            String fileName = file.getOriginalFilename();
            Path path = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Generate the image URL
            String baseUrl = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
            String imageUrl = baseUrl + "/api/images/" + uploadDir + fileName;

            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
        }
    }

    @GetMapping("/uploads/{type}/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String type, @PathVariable String filename) {
        String uploadDir;
        if ("artwork".equalsIgnoreCase(type)) {
            uploadDir = ARTWORK_UPLOAD_DIR;
        } else if ("user".equalsIgnoreCase(type)) {
            uploadDir = USER_UPLOAD_DIR;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {

            // Load the file as a resource
            Path path = Paths.get(uploadDir + filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{type}/{fileName}")
    public ResponseEntity<String> deleteImage(@PathVariable String type, @PathVariable String fileName) {
        String uploadDir;
        if ("artwork".equalsIgnoreCase(type)) {
            uploadDir = ARTWORK_UPLOAD_DIR;
        } else if ("user".equalsIgnoreCase(type)) {
            uploadDir = USER_UPLOAD_DIR;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid image type");
        }

        try {
            // Delete the file from the specified directory
            Path path = Paths.get(uploadDir + fileName);
            if (Files.exists(path)) {
                Files.delete(path);
                return ResponseEntity.ok("Image deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image deletion failed");
        }
    }

}
