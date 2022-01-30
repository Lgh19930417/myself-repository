package com.lxw.api.filesystem;

import com.lxw.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.multipart.MultipartFile;

@Api("图片管理接口")
public interface FileSystemControllerApi {
    @ApiOperation("图片上传")
    @ApiParam(name ="file",value = "页面",type = "MultipartFile")
    public UploadFileResult uploadPic(MultipartFile file, String businesskey, String filetag, String metadata);
}
