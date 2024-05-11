package com.udacity.jwdnd.course1.cloudstorage.cotrollers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @PostMapping("/addNote")
    public String addNote(
            @RequestParam String noteTitle,
            @RequestParam String noteDescription,
            @RequestParam Integer noteId,
            Authentication authentication) {
        String userName = (String) authentication.getPrincipal();
        User user = userService.getUser(userName);
        if (noteId != null) {
            Boolean bool = noteService.updateNote(noteTitle,noteDescription,noteId);
            return bool?"redirect:/result?isSuccess=true":"redirect:/result?isSuccess=false";
        }
        Note note = new Note(null,noteTitle,noteDescription,user.getUserId());
        Boolean bool = noteService.addNote(note);
        return bool?"redirect:/result?isSuccess=true":"redirect:/result?isSuccess=false";
    }

    @GetMapping("/deletenote/{noteId}")
    public String deleteNote(
            @PathVariable Integer noteId
    ) {
        Boolean bool = noteService.deleteNoteById(noteId);
        return bool?"redirect:/result?isSuccess=true":"redirect:/result?isSuccess=false";
    }
}
