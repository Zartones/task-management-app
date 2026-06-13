package online.taskmanagementapp.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dropbox")
@Data
public class DropboxConfig {

    private String accessToken;
    private String appKey;
    private String appSecret;

    @Bean
    public DbxClientV2 dropboxClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("project-management-app")
                .build();
        return new DbxClientV2(config, accessToken);
    }
}
