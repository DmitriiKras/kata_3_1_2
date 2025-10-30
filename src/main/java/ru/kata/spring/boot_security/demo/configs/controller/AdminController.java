package ru.kata.spring.boot_security.demo.configs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.configs.Service.UserService;
import ru.kata.spring.boot_security.demo.configs.model.User;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;
    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String showAllUsers(Model model, Principal principal) {
        //Получение всех пользователей из DAO м передача их на представление

        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);

        User admin = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", admin);
         return "AllUsers";

    }
    @GetMapping("/addNewUser")
    public String addNewUser(Model model) {

        User user=new User();
        model.addAttribute("user",user);
        return "user-info";
    }


    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        // Кодируем пароль перед сохранением
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/updateInfo")
    public String updateUser(@RequestParam("id") long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user",user);
        return "user-info";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        System.out.println(user);
        return "user-info";
    }
}
