package com.yh.filesystem.dao;

import com.lxw.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileSystemRespository extends MongoRepository<FileSystem,String> {
}
