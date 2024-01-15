package org.example.firstwebauth.Controller;

import jakarta.validation.Valid;
import org.example.firstwebauth.Model.User;
import org.example.firstwebauth.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    ArrayList<PersonaForm>utenti = new ArrayList<>();

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
    public String postLogin(LoginForm loginForm) {
        User user = userRepository.login(loginForm.username, loginForm.password);

        if(user == null){
            return "loginuser";
        }else{
            return "redirect:/home";
        }
    }

    @GetMapping("/home")
    public String showHome(Model m ) {
        m.addAttribute("libri",BookController.libri); //da sostituire con la parte del DB
        return "home";
    }

}

