package com.example.demo.controller;

import com.example.demo.entity.MyUser;
import com.example.demo.repository.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/my-user")
public class MyUserController {

    @Autowired
    private MyUserRepo myUserRepo;

    //----------------------------REGISTER-----------------
    @GetMapping(value = "/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new MyUser());
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute("user") @RequestBody MyUser user) {
        myUserRepo.save(user);
        return "redirect:/my-user/login";
    }

    //------------------------------------------------------------------------------

    //----------------------------LOGIN-----------------
    @GetMapping(value = "/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new MyUser());
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginForm(@ModelAttribute("user") @RequestBody MyUser user) {
        Optional<MyUser> myUser = myUserRepo.findByUsername(user.getUsername());
        if (myUser.isPresent()) {
            if (myUser.get().getPassword().equals(user.getPassword()))
                return "redirect:/my-user/index";
            else
                return "wrong-password";
        } else {
            return "user-not-found";
        }
    }

    //------------------------------------------------------------------------------

    @RequestMapping(value = {"/index"})
    public String index() {
        return "index";
    }


}
