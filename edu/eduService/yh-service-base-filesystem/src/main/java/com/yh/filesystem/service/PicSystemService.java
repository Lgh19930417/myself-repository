package com.yh.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.lxw.framework.domain.filesystem.FileSystem;
import com.lxw.framework.domain.filesystem.response.FileSystemCode;
import com.lxw.framework.domain.filesystem.response.UploadFileResult;
import com.lxw.framework.exception.ExceptionCast;
import com.lxw.framework.model.response.CommonCode;
import com.yh.filesystem.dao.FileSystemRespository;
import lombok.extern.slf4j.Slf4j;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@Slf4j
public class PicSystemService {

    @Value("${yh.fastdfs.tracker_servers}")
    private String tracker_servers;

    @Value("${yh.fastdfs.connect_timeout_in_seconds}")
    private Integer connect_timeout_in_seconds;

    @Value("${yh.fastdfs.network_timeout_in_seconds}")
    private Integer network_timeout_in_seconds;

    @Value("${yh.fastdfs.charset}")
    private String charset;
    @Autowired
    private FileSystemRespository fileSystemRespository;

    public UploadFileResult uploadPic(MultipartFile file, String businesskey, String filetag, String metadata) {
        //关键参数校验
        if(file==null||businesskey==null||filetag==null){
            ExceptionCast.cast(CommonCode.ILLARGS);
        }
        //初始化fastDFS
        init();
        //将图片上传到fastDFS服务器中
        String fileId = uploadPic2fastDFS(file);
        //将fileID上传到MongoDB中
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFileName(file.getOriginalFilename());
        fileSystem.setFileSize(file.getSize());
        fileSystem.setFilePath(fileId);
        fileSystem.setFiletag(filetag);
        fileSystem.setFileType(file.getContentType());
        fileSystem.setBusinesskey(businesskey);
        if(metadata!=null){
            fileSystem.setMetadata(JSON.parseObject(metadata, Map.class));
        }
        FileSystem save = fileSystemRespository.save(fileSystem);
        UploadFileResult uploadFileResult = new UploadFileResult(CommonCode.SUCCESS, save);
        return uploadFileResult;
    }

    //初始化fastDFS
    private void init(){
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    //将图片上传到fastDFS服务器中
    private String uploadPic2fastDFS(MultipartFile file){
        //关键参数校验
        if(file==null){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        try {
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();

            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storageServer);
            String originalFilename = file.getOriginalFilename();
            String fileType = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            String fileId = storageClient1.upload_file1(file.getBytes(), fileType, null);
            return fileId;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }


}
