package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialRepository {

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) VALUES (" +
            "#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredentialByCredentialId(Integer credentialid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void delete(Integer credentialid);

    @Select( "SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentialsByUserId(Integer userid);

    @Update("UPDATE credentials " +
            "SET url = #{url}, username = #{username}, key = #{key}, " +
            "password = #{password} " +
            "WHERE credentialid = #{credentialid}")
    int update(
            String url, String username, String key, String password, Integer credentialid);
}