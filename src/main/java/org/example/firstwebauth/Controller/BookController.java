package org.example.firstwebauth.Controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.ArrayList;

@Controller
public class BookController {

    static ArrayList<Book>libri = new ArrayList<>();

    @GetMapping("/createBook")
    public String createBook(Book book){
        return "createbook";
    }

    @PostMapping("/postStoreBook")
    public String storeBook(@Valid Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "createbook";
        }
        libri.add(book);
        model.addAttribute("libri",libri);
        return "redirect:/home";
    }


}
