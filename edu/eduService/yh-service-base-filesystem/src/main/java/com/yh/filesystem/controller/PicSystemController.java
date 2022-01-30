package com.yh.filesystem.controller;

import com.lxw.api.filesystem.FileSystemControllerApi;
import com.lxw.framework.domain.filesystem.response.UploadFileResult;
import com.yh.filesystem.service.PicSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
@RequestMapping("filesystem")
public class PicSystemController implements FileSystemControllerApi {
@Autowired
private PicSystemService picSystemService;

    @Override
    @PostMapping("/upload")
    public UploadFileResult uploadPic(MultipartFile file, String businesskey, String filetag, String metadata) {
       return picSystemService.uploadPic(file,businesskey,filetag,metadata);
    }
}
