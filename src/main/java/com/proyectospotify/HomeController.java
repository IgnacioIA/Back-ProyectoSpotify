package com.proyectospotify;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String inicio() {
        return "API de Spotify Backend funcionando correctamente";
    }
}
