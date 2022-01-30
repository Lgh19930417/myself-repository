package com.lxw.manage_media.dao;

import com.lxw.framework.domain.media.MediaFile;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MediaFileRepository extends MongoRepository<MediaFile,String> {
}
