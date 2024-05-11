package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface FileRepository {
    @Insert("INSERT INTO FILES(" +
            "filename, " +
            "contenttype, " +
            "filesize, " +
            "filedata, " +
            "userid) VALUES (" +
            "#{filename}, " +
            "#{contenttype}, " +
            "#{filesize}, " +
            "#{filedata}, " +
            "#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insert(File file);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userid = #{userid}")
    File getFile(String filename, Integer userid);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    File getFileById(Integer fileid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getAllFiles(Integer userid);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    void delete(Integer fileid);
}
