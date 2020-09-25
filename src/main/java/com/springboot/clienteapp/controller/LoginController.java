package com.springboot.clienteapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String error,
                        @RequestParam(value = "logout",required = false) String logout,
                        Model model , Principal principal, RedirectAttributes attributes){

        if(error!=null){
            model.addAttribute("error","ERROR DE ACCESO: Usuario y/o Contraseña son incorrectos}");
        }
        if (principal!=null){
          attributes.addFlashAttribute("Warning","ATENCION: Ud ya ha iniciado sesion previamente");
          return "redirect:/index";
        }
        if(logout!=null){
            model.addAttribute("succes","ATENCION: has finalizado sesion con exito}");
        }
        return "login";
    }
}
