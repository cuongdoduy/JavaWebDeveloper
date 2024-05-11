package com.udacity.jwdnd.course1.cloudstorage.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;

import java.util.List;

@Mapper
public interface NoteRepository {
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNoteById(Integer noteid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotesByUserId(Integer userid);

    @Insert("INSERT INTO NOTES(" +
            "notetitle, " +
            "notedescription, " +
            "userid) VALUES (" +
            "#{notetitle}, " +
            "#{notedescription}, " +
            "#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int addNote(Note note);

    @Update("UPDATE NOTES SET " +
            "notetitle = #{noteTitle}, " +
            "notedescription = #{noteDescription} " +
            "WHERE noteid = #{noteId}")
    void updateNoteById(String noteTitle, String noteDescription, Integer noteId);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void deleteNoteById(Integer noteid);
}
