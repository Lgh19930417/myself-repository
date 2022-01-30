package com.lxw.manage_media.controller;

import com.lxw.api.media.MediaUploadControllerApi;
import com.lxw.framework.domain.media.response.CheckChunkResult;
import com.lxw.framework.model.response.ResponseResult;
import com.lxw.manage_media.service.MediaUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class MediaUploadController implements MediaUploadControllerApi {
    @Autowired
    private MediaUploadService mediaUploadService;
    @Override
    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        return mediaUploadService.register(fileMd5,fileName,fileSize,mimetype,fileExt);
    }

    @Override
    public CheckChunkResult checkChunk(String fileMd5, Integer chunk, Integer chunkSize) {
        return mediaUploadService.checkChunk(fileMd5,chunk,chunkSize);
    }

    @Override
    public ResponseResult uploadChunk(MultipartFile file, Integer chunk, String fileMd5) {
        return mediaUploadService.uploadChunk(file,chunk,fileMd5);
    }

    @Override
    public ResponseResult mergeChunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        return mediaUploadService.mergeChunks(fileMd5,fileName,fileSize,mimetype,fileExt);
    }

}
