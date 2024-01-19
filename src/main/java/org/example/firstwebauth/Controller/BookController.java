package org.example.firstwebauth.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.firstwebauth.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserBookRepository userBookRepository;
    @GetMapping("/createBook")
    public String createBook(Book book, HttpSession session){
        if(session.getAttribute("user")==null){
            return "sessionerror";
        }
            return "createbook";
    }

    @PostMapping("/postStoreBook")
    public String storeBook(@Valid Book book, BindingResult bindingResult, Model model, HttpSession session){
        if(session.getAttribute("user")==null){
            return "sessionerror";
        }

        if(bindingResult.hasErrors()){
            return "createbook";
        }
        bookRepository.save(book);
        return "redirect:/home";
    }

    @GetMapping("/dettaglio")
    public String dettaglioBook(@RequestParam("bookId") Integer bookId, Model m, HttpSession session){

        if(session.getAttribute("user")==null){
            return "sessionerror";
        }

        Optional<Book> bookDettaglio = bookRepository.findById(bookId);
        Book book = null;

        if (bookDettaglio.isPresent()) {
            book = bookDettaglio.get();
        }

        //html anche il prezzo

        m.addAttribute("libro",book);
        return "dettagliobook";
    }

    @GetMapping("/modifica")
    public String mostraModificaForm(@RequestParam("bookId") int bookId, Model model, HttpSession session) {
        if(session.getAttribute("user")==null){
            return "sessionerror";
        }

        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {
            Book libro = bookOptional.get();
            model.addAttribute("book", libro);
        }
        //html anche il prezzo

        return "modificaForm";
    }

    @PostMapping("modificaPost")
    public String mostraModificaPost(@Valid Book book,BindingResult bindingResult, HttpSession session) {

        if(session.getAttribute("user")==null){
            return "sessionerror";
        }

        if(bindingResult.hasErrors()){
            return "modificaForm";
        }
        Optional<Book> bookOptional = bookRepository.findById(book.getId());

        if (bookOptional.isPresent()) {
            Book libro = bookOptional.get();
            libro.setTitolo(book.getTitolo());
            libro.setAutore(book.getAutore());
            libro.setDescrizione(book.getDescrizione());
            libro.setPrice(book.getPrice());
            System.out.printf(libro.toString());
            bookRepository.save(libro);
        }
        //html anche il prezzo

        return "redirect:/home";
    }

    @RequestMapping ("/preferiti")
    public String bookUser(@RequestParam("bookId") Integer bookId, HttpSession session){
        if(session.getAttribute("user")==null){
            return "sessionerror";
        }

        User user = (User) session.getAttribute("user");
        Optional<Book> bookDettaglio = bookRepository.findById(bookId);
        Book book = null;

        if (bookDettaglio.isPresent()) {
            book = bookDettaglio.get();
        }
        UserBook userBook = new UserBook(user,book);
        userBookRepository.save(userBook);
        return "redirect:/home";

    }

    @RequestMapping ("/togliPreferiti")
    public String togliPreferiti(@RequestParam("bookId") Integer bookId, HttpSession session){
        if(session.getAttribute("user")==null){
            return "sessionerror";
        }

        User user = (User) session.getAttribute("user");

        Optional<UserBook> optional = userBookRepository.findBooksByUserBooks(user.getId(),bookId);
        UserBook ub = null;
        if (optional.isPresent()) {
            ub = optional.get();
        }

        userBookRepository.delete(ub);

        return "redirect:/home";

    }

    @GetMapping("/remove")
    public String removeBook(@RequestParam("bookId") Integer bookId, HttpSession session){
        if(session.getAttribute("user")==null){
            return "sessionerror";
        }

        Optional<Book> bookToRemove = bookRepository.findById(bookId);

        if (bookToRemove.isPresent()) {
            Book book = bookToRemove.get();
            bookRepository.delete(book);
        }

        return "redirect:/home";
    }

}
