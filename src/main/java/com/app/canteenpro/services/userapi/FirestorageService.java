package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.MediaDataDto;
import com.app.canteenpro.common.Enums;
import com.app.canteenpro.database.models.MediaMetaData;
import com.app.canteenpro.database.repositories.MediaMetaDataRepo;
import com.app.canteenpro.common.appConstants;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class FirestorageService {
    private final Storage storage;

    private final MediaMetaDataRepo repo;

    Pattern BASE64_PATTERN = Pattern.compile(appConstants.BASE64_VALIDATION_REGEX);

    @Value("${firebase.storage-bucket}")
    private String bucketName;

    public FirestorageService(MediaMetaDataRepo repo) throws IOException {
        this.repo = repo;

        ClassPathResource resource = new ClassPathResource("confidential/firebase-bucket-key.json");
        InputStream serviceAccount = resource.getInputStream();

        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount)
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    public MediaMetaData uploadMedia(String base64FileString, MediaDataDto initialMediaData, Enums.FILE_TYPES filetype) {
        final String fileType =  base64FileString.split(":")[1].split(";")[0];
        final String uploadFolder;

        if(filetype == Enums.FILE_TYPES.IMAGE) {
            uploadFolder = "image";
        } else if(filetype == Enums.FILE_TYPES.AUDIO) {
            uploadFolder = "audio";

        } else if(filetype == Enums.FILE_TYPES.VIDEO) {
            uploadFolder = "video";
        } else {
            uploadFolder = "";
        }

        // Remove data prefix
        if(base64FileString.contains(",")) {
            base64FileString = base64FileString.split(",")[1];
        }

        // Whether valid base64 string or not
        if (!BASE64_PATTERN.matcher(base64FileString).matches()) {
            throw new IllegalArgumentException("File is empty. Please upload a valid file.");
        }

        String guid = UUID.randomUUID().toString();
        String mediaFileUniqueName = guid + "." + initialMediaData.getExtension();
        String path = uploadFolder + "/" + mediaFileUniqueName;

        BlobId blobId = BlobId.of(this.bucketName, path);

        // Set file type
        BlobInfo blobInfo = BlobInfo
                .newBuilder(blobId)
                .setContentType(fileType)
                .build();

        // Upload file to blob
        storage.create(blobInfo, Base64.getDecoder().decode(base64FileString));

        // Make file public
        storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        // Construct and return the public URL
        System.out.println("https://storage.googleapis.com/" + this.bucketName + "/" + mediaFileUniqueName);


        // Store media meta data
        MediaMetaData metaData = new MediaMetaData();
        metaData.setGuid(guid);
        metaData.setExtension(initialMediaData.getExtension());
        if(initialMediaData.getFileName() != null && !initialMediaData.getFileName().isEmpty()) {
            metaData.setFilename(initialMediaData.getFileName());
        }
        repo.save(metaData);

        return metaData;
    }

    public void deleteMedia(String fileNameWithExtension, Enums.FILE_TYPES filetype) {
        String uploadFolder;
        if(filetype == Enums.FILE_TYPES.IMAGE) {
            uploadFolder = "image";
        } else if(filetype == Enums.FILE_TYPES.AUDIO) {
            uploadFolder = "audio";

        } else if(filetype == Enums.FILE_TYPES.VIDEO) {
            uploadFolder = "video";
        } else {
            uploadFolder = "file";
        }

        String path = uploadFolder + "/" + fileNameWithExtension;
        BlobId blobId = BlobId.of(this.bucketName, path);
        this.storage.delete(blobId);
    }
}
