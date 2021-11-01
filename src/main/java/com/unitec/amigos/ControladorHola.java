package com.unitec.amigos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class ControladorHola {

    // Este primer recurso es tu hola mundo de un servicio REST que usa el método GET

    @GetMapping("/Hola")
    public String Saludar(){

        return "Hola desde mi primer servico REST";
     }
}
