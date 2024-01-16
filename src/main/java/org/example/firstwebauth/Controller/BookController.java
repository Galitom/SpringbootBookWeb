package org.example.firstwebauth.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.firstwebauth.Model.BookRepository;
import org.example.firstwebauth.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    BookRepository bookRepository;
    static ArrayList<Book>libri = new ArrayList<>();

    @GetMapping("/createBook")
    public String createBook(Book book){
        return "createbook";
    }

    @GetMapping("/dettaglio")
    public String dettaglioBook(@RequestParam("bookId") Integer bookId, Model m, HttpSession session){

        Optional<Book> bookDettaglio = bookRepository.findById(bookId);
        Book book = null;

        if (bookDettaglio.isPresent()) {
            book = bookDettaglio.get();
        }

        m.addAttribute("libro",book);
        return "dettagliobook";
    }

    @PostMapping("/postStoreBook")
    public String storeBook(@Valid Book book, BindingResult bindingResult, Model model, HttpSession session){

        User user = (User) session.getAttribute("user");


        if(bindingResult.hasErrors()){
            return "createbook";
        }
        System.out.println("Controllo sessione:" + session.getAttribute("user"));
        book.setUser(user);
        bookRepository.save(book);
        return "redirect:/home";
    }


}
