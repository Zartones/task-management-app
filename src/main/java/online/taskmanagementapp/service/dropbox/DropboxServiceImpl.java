package online.taskmanagementapp.service.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DropboxServiceImpl implements DropboxService {

    private final DbxClientV2 dropboxClient;

    @Override
    public String uploadFile(MultipartFile file, String folder) {
        String filePath = "/" + folder + "/" + file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            FileMetadata metadata = dropboxClient.files()
                    .uploadBuilder(filePath)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(inputStream);
            return metadata.getId();
        } catch (DbxException | IOException e) {
            throw new RuntimeException("Failed to upload file to Dropbox: " + e.getMessage());
        }
    }

    @Override
    public InputStream downloadFile(String dropboxFileId) {
        try {
            return dropboxClient.files()
                    .downloadBuilder(dropboxFileId)
                    .start()
                    .getInputStream();
        } catch (DbxException e) {
            throw new RuntimeException("Failed to download file from Dropbox: " + e.getMessage());
        }
    }
}
