package com.example.tutorsFinderSystem.configuration;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
// import java.util.Objects;

@Configuration
public class GoogleDriveConfig {

    @Bean
    public Drive googleDrive() throws IOException, GeneralSecurityException {
        // Đọc file credentials từ resources
        InputStream in = getClass().getResourceAsStream("/google-drive-credentials.json");
        if (in == null) {
            throw new IOException("Resource not found: /google-drive-credentials.json");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(in)
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("TutorsFinderSystem")
                .build();
    }
}