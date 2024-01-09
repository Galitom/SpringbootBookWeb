package org.example.firstwebauth.Controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class UserController {

    ArrayList<PersonaForm>utenti = new ArrayList<>();

    @GetMapping("/registrazione")
    public String showRegister(PersonaForm personaForm){
        return "registrazioneuser";
    }

    @PostMapping("/postRegistrazione")
    public String postRegistrazione(@Valid PersonaForm personaForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "registrazioneuser";
        }
        utenti.add(personaForm);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin(LoginForm loginForm) {
        return "loginuser";
    }

    @PostMapping("/postLogin")
    public String postLogin(LoginForm loginForm) {
        boolean b = false;

        for (PersonaForm pf : utenti){
            if(pf.username.equals(loginForm.username) && pf.password.equals(loginForm.password)){
                b = true;
            }
        }

        if(b){
            return "redirect:/home";
        }else{
            return "loginuser";
        }
    }

    @GetMapping("/home")
    public String showHome(Model m ) {
        m.addAttribute("libri",BookController.libri); //da sostituire con la parte del DB
        return "home";
    }

}

