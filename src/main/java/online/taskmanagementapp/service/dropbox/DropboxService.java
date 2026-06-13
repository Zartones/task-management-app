package online.taskmanagementapp.service.dropbox;

import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public interface DropboxService {
    String uploadFile(MultipartFile file, String folder);

    InputStream downloadFile(String filePath);

}
