/**
 * 
 */
package com.dm.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dm.model.FileMetadata;
import com.dm.sdk.model.Documents;
import com.dm.service.FileStorageService;

/**
 * @author mary.jane
 *
 */
@RestController
public class FileRestController {

	private static final Logger logger = LoggerFactory.getLogger(FileRestController.class);

    @Autowired
    private FileStorageService FileStorageService;

    @PostMapping("/uploadFile")
    public Documents uploadFile(@RequestBody FileMetadata fileMetadata) {
        fileMetadata = FileStorageService.storeFile(fileMetadata);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileMetadata.getId())
                .toUriString();

        return null; //new Documents(fileDocument.getFilename(), fileDownloadUri,
//        		fileDocument.getContentType(), 0);
    }

    @PostMapping("/uploadMultipleFiles")
    public List<Documents> uploadMultipleFiles(@RequestParam("files") FileMetadata[] fileDocuments) {
        return Arrays.asList(fileDocuments)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        FileMetadata FileMetadata = FileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(FileMetadata.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + FileMetadata.getFilename() + "\"")
                .body(new ByteArrayResource(FileMetadata.getContent()));
    }
    
}
