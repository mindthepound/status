package io.spin.status.util;

import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Component
public class FileUtil {

    public byte[] convertMultipartFileToByteArray(
            MultipartFile multipartFile
    ) {
        try {
            File file = new File(
                    System.getProperty("java.io.tmpdir") +
                            System.getProperty("file.separator") +
                            multipartFile.getOriginalFilename()
            );
            multipartFile.transferTo(file);
            return IOUtils.toByteArray(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            log.info("MultipartFile to FileInputStream Exception " + e.getMessage());
        }
        throw new RuntimeException("Please check MultipartFile : " + multipartFile.getOriginalFilename());
    }

}
