package com.web.restaurante.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

	@GetMapping("/registrarUsuario")
	private String loginUsuario () {
		return "registrarUsuario";
	}
	
}
