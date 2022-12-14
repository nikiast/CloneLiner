package com.clone.liner.service;

import com.clone.liner.model.product.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    private static final String DEFAULT_PHOTO = "noPhoto.jpg";

    @Value("${upload.path}")
    private String uploadPath;

    public boolean addImage(MultipartFile file, Product product) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                product.setFilename(resultFilename);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            product.setFilename(DEFAULT_PHOTO);
            return false;
        }
    }
}
