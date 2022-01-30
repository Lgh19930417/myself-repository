package com.lxw.manage_media.service;

import com.lxw.framework.domain.media.MediaFile;
import com.lxw.framework.domain.media.response.CheckChunkResult;
import com.lxw.framework.domain.media.response.MediaCode;
import com.lxw.framework.exception.ExceptionCast;
import com.lxw.framework.model.response.CommonCode;
import com.lxw.framework.model.response.ResponseResult;
import com.lxw.manage_media.dao.MediaFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
@Slf4j
public class MediaUploadService {
    @Value("${yh‐service‐manage‐media.upload‐location}")
    private static String uploadPath;
    @Autowired
    private MediaFileRepository mediaUploadRepository;

    /**
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt
     * @return
     */
    public ResponseResult register(String fileMd5, String fileName, Long fileSize,
                                   String mimetype, String fileExt) {

        //1,检验视频在媒资服务器是否存在
        String filePath = getFilePath(fileMd5, fileExt);
        File file = new File(filePath);
        //2,检验视频在数据库中是否存在
        Optional<MediaFile> mediaFile = mediaUploadRepository.findById(fileMd5);
        //3,判断视频是否存在
        //存在,报错
        if (file.exists() && mediaFile.isPresent()) {
            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_EXIST);
        }
        //不存在,创建视频存放的目录
        File fileDir = new File(this.getFileDirPath(fileMd5));
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取文件所在目录
     *
     * @param fileMd5
     * @return
     */
    private String getFileDirPath(String fileMd5) {
        return uploadPath + fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/";
    }

    /**
     * 获取视频上传相对路径
     *
     * @param fileMd5
     * @return
     */
    private String getFileDirRePath(String fileMd5) {
        return fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/";
    }

    /**
     * 获取文件路径
     *
     * @param fileMd5
     * @param fileExt
     * @return
     */
    public String getFilePath(String fileMd5, String fileExt) {
        return uploadPath + fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/" + fileMd5 + "." + fileExt;
    }


    /**
     * 检查块文件
     *
     * @param fileMd5
     * @param chunk
     * @param chunkSize
     * @return
     */
    public CheckChunkResult checkChunk(String fileMd5, Integer chunk, Integer chunkSize) {
        //获取块的路径
        String chunkPath = this.getFileDirPath(fileMd5) + "chunk/";
        //判断块目录是否存在
        File file = new File(chunkPath);
        if (chunk == 0) {
            if (!file.exists()) {
                file.mkdirs();
                return new CheckChunkResult(CommonCode.SUCCESS,false);
            }
        }
        //
        String chunkFilePath = this.getFileDirPath(fileMd5) + "chunk/" + chunk;
        File chunkFile = new File(chunkFilePath);
        if(!chunkFile.exists()){
            return new CheckChunkResult(CommonCode.SUCCESS,false);
        }
        long size = chunkFile.length();
        if(size!=chunkSize){
            return new CheckChunkResult(CommonCode.SUCCESS,false);
        }
        return new CheckChunkResult(CommonCode.SUCCESS,true);
    }

    /**
     * 上传块文件
     * @param file
     * @param chunk
     * @param fileMd5
     * @return
     */
    public ResponseResult uploadChunk(MultipartFile file, Integer chunk, String fileMd5) {
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            inputStream = file.getInputStream();
             fos = new FileOutputStream(new File(this.getFileDirPath(fileMd5) + "chunk/" + chunk));
            IOUtils.copy(inputStream,fos);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }

        return new ResponseResult(CommonCode.SUCCESS);
    }

    public ResponseResult mergeChunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        //找到chunk所在目录
        File file = new File(this.getFileDirPath(fileMd5) + "chunk/");
        //判断目录是否存在
        if(!file.exists()){
            ExceptionCast.cast(MediaCode.MERGE_FILE_CHECKFAIL);
        }
        //获取目录中的块文件并判断是否为空
        File[] files = file.listFiles();
        //对分片chunk进行排序
        List<File> files1 = Arrays.asList(files);
        Collections.sort(files1, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                String name1 = o1.getName();
                String name2 = o2.getName();
                return Integer.parseInt(name1)>Integer.parseInt(name2)?1:-1;
            }
        });
        //对chunk进行合并
        File fileEnd=null;
        FileOutputStream fos=null;
        FileInputStream fis=null;
        for (File file1: files1) {
            fileEnd = new File(this.getFileDirPath(fileMd5) + fileMd5 + "." + fileExt);
            int len;
            byte[] bytes=new byte[1024];
            try {
                fis=new FileInputStream(file1);
                while ((len=fis.read(bytes))!=-1){
                    fos=new FileOutputStream(fileEnd);
                    fos.write(bytes,0,len);
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }finally {
                try {
                    fis.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }

            }
        }
        //校验合并后的文件的md5和上传文件的md5是否一致
        String md5Hex = null;
        try {
            md5Hex = DigestUtils.md5Hex(new FileInputStream(fileEnd));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        if(!md5Hex.equals(fileMd5)){
            ExceptionCast.cast(MediaCode.MERGE_FILE_CHECKFAIL);
        }
        //删除原来的chunk文件夹中的文件
        file.delete();
        //向数据库中插入新的数据
//将文件信息保存到数据库
        MediaFile mediaFile=new MediaFile();
        mediaFile.setFileId(fileMd5);
        mediaFile.setFileName(fileMd5+"."+fileExt);
        mediaFile.setFileOriginalName(fileName);
        //文件路径保存相对路径
        mediaFile.setFilePath(getFileDirRePath(fileMd5));
        mediaFile.setFileSize(fileSize);
        mediaFile.setUploadTime(new Date());
        mediaFile.setMimeType(mimetype);
        mediaFile.setFileType(fileExt);
        //状态为上传成功
        mediaFile.setFileStatus("301002");
        MediaFile save = mediaUploadRepository.save(mediaFile);
        //返回响应结果
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
