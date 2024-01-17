package org.example.firstwebauth.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.firstwebauth.Model.Book;
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

    @GetMapping("/modifica")
    public String mostraModificaForm(@RequestParam("bookId") int bookId, Model model) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {
            Book libro = bookOptional.get();
            model.addAttribute("book", libro);
        }
        return "modificaForm";
    }

    @PostMapping("modificaPost")
    public String mostraModificaPost(@Valid Book book,BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "modificaForm";
        }
        Optional<Book> bookOptional = bookRepository.findById(book.getId());

        if (bookOptional.isPresent()) {
            Book libro = bookOptional.get();
            libro.setTitolo(book.getTitolo());
            libro.setAutore(book.getAutore());
            libro.setDescrizione(book.getDescrizione());
            System.out.printf(libro.toString());
            bookRepository.save(libro);
        }
        return "redirect:/home";
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

    @GetMapping("/remove")
    public String removeBook(@RequestParam("bookId") Integer bookId){
        Optional<Book> bookToRemove = bookRepository.findById(bookId);

        if (bookToRemove.isPresent()) {
            Book book = bookToRemove.get();
            bookRepository.delete(book);
        }

        return "redirect:/home";
    }

//    @GetMapping("/getjson")
//    public String getBookJosn(){
//        Iterable<Book> books = bookRepository.findAll();
//
//
//    }

}
