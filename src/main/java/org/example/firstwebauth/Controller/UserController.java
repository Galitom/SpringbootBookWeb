package org.example.firstwebauth.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.firstwebauth.Model.Book;
import org.example.firstwebauth.Model.BookRepository;
import org.example.firstwebauth.Model.User;
import org.example.firstwebauth.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    ArrayList<PersonaForm>utenti = new ArrayList<>();

    @GetMapping("/")
    public String dashboard(){
        return "dashboard";
    }

    @GetMapping("/registrazione")
    public String showRegister(PersonaForm personaForm){
        return "registrazioneuser";
    }

    @PostMapping("/postRegistrazione")
    public String  postRegistrazione(@Valid PersonaForm personaForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "registrazioneuser";
        }
        userRepository.save(new User(personaForm.name,personaForm.surname,personaForm.username,personaForm.password));
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin(LoginForm loginForm) {
        return "loginuser";
    }

    @PostMapping("/postLogin")
    public String postLogin(LoginForm loginForm, HttpSession session) {
        User user = userRepository.login(loginForm.username, loginForm.password);

        if(user != null){
            session.setAttribute("user", user);
            System.out.println(session.getAttribute("user"));
            return "redirect:/home";
        }else{
            return "loginUser";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
            session.setAttribute("user", null);
            return "redirect:/login";
    }

    @GetMapping("/home")
    public String showHome(Model m, HttpSession session) {

        User user = (User) session.getAttribute("user");
        m.addAttribute("libri",bookRepository.findByUserId(user.getId()));

        return "home";
    }

    @GetMapping("/profilo")
    public String showProfilo(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        return "profilo";
    }

    @GetMapping("/modificaUtente")
    public String mostraModificaForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "modificaProfilo";
    }

    @PostMapping("/modificaPostUtente")
    public String postModificaForm(User user, HttpSession session) {
        User user1 = (User) session.getAttribute("user");
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());

        userRepository.save(user1);

        return "redirect:/profilo";
    }

    @GetMapping("/delete")
    public String removeUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Book> bookOptional = bookRepository.findByUserId(user.getId());
        for (Book b : bookOptional){
            b.setUser(null);
            bookRepository.save(b);
        }

        userRepository.delete(user);

        session.setAttribute("user",null);

        return "redirect:/registrazione";
    }


//    @GetMapping("buttare")
//    @ResponseBody
//    public String buttare(){
//        return Json;
//    }

}

