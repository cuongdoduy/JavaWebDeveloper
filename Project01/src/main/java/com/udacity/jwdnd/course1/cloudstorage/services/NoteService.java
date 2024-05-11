package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.repository.FileRepository;
import com.udacity.jwdnd.course1.cloudstorage.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class NoteService {
    private NoteRepository noteRepository;
    private final Logger logger = Logger.getLogger(NoteService.class.getName());

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public boolean addNote(Note note) {
        try {
            noteRepository.addNote(note);
            return true;
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    public boolean updateNote(String noteTitle, String noteDescription, Integer noteId) {
        try {
            noteRepository.updateNoteById(noteTitle, noteDescription, noteId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteNoteById(Integer noteId) {
        try {
            noteRepository.deleteNoteById(noteId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Note> getNote(Integer userid) {
        return noteRepository.getNotesByUserId(userid);
    }


    public List<Note>  getAllNotes(Integer userId) {
        return noteRepository.getNotesByUserId(userId);
    }

}
