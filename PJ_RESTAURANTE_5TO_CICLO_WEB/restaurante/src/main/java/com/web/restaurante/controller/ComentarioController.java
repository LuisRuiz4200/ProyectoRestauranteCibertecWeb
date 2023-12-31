package com.web.restaurante.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.web.restaurante.business.ComentarioService;
import com.web.restaurante.model.Comentario;
import com.web.restaurante.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class ComentarioController {

	@Autowired
	ComentarioService comentarioService;
	
	@GetMapping("/comentario")
	public String nuevoComentario (Model model)
	{
		
		model.addAttribute("listaComentario",comentarioService.listar());
		model.addAttribute("comentario",new Comentario());
		
		return "comentario";
	}
	
	@PostMapping("/grabarComentario")
	public String grabarComentario (@ModelAttribute("comentario") Comentario obj, HttpSession session) {
		
		Usuario user =(Usuario) session.getAttribute("usuario");
		
		obj.setFecharegComentario(new Date(new java.util.Date().getTime()));
		obj.setEstadoComentario("ACTIVO");
		obj.setUsuarioCliente(user);
		
		comentarioService.agregar(obj);
		
		return "redirect:/comentario";
	}
}
