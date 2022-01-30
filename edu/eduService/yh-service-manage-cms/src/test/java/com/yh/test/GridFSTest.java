package com.yh.test;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFSTest {

    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;

    @Test
    public void testGridFSUpload() throws FileNotFoundException {
        String path = this.getClass().getResource("/templates").getPath();
        String file=path+"/course.ftl";
        FileInputStream fis = new FileInputStream(new File(file));
        ObjectId objectId = gridFsTemplate.store(fis, "gs-course.ftl");
        System.out.println(objectId);//616ad032abfcc731ecf7d132
        //616edf1543db3806245e7141
    }

    @Test
    public void testGridFSDownload() throws IOException {
        //文件id
        String  fileId="616ad032abfcc731ecf7d132";
        //获取GridFSFile对象
        GridFSFile gridFSFile = gridFsTemplate.findOne((Query.query(Criteria.where("_id").is(fileId))));
        //打开下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getFilename());
        //将下载流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
        String s = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
        System.out.println(s);
    }
}
