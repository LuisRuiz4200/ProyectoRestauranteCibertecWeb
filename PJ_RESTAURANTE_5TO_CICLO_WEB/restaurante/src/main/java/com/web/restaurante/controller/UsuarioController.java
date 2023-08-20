package com.web.restaurante.controller;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import com.web.restaurante.business.DistritoService;
import com.web.restaurante.business.TipoUsuarioService;
import com.web.restaurante.business.UsuarioService;
import com.web.restaurante.model.Usuario;
import com.web.restaurante.reuzable.EncodeBase64;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	@Autowired
	private DistritoService distritoService;
	@Autowired
	private TipoUsuarioService tipoUsuarioService;
	
	@GetMapping("/login")
	public String login (Model model) {
		
		model.addAttribute("usuario",new Usuario());
		//session.invalidate();
		//session.removeAttribute("usuario");
		
		return "login";
	}
	
	@GetMapping("/logout")
	public String cerrarSession (HttpSession session) {
		
		session.removeAttribute("usuario");
		session.removeAttribute("carrito");
		session.removeAttribute("tipoUsuario");
		
		return "/logout";
	}
	
	/*
	 * @PostMapping("/iniciarSesion") private String
	 * iniciarSesion(@ModelAttribute("usuario") Usuario usuario,Model model) {
	 * 
	 * List<Usuario> listaUsuario= service.listarUsuario(); String codigo_usuario =
	 * usuario.getCodUsuario(); String password_usuario =
	 * usuario.getPasswordUsuario();
	 * 
	 * for (Usuario obj :listaUsuario) {
	 * if(obj.getCodUsuario().equals(codigo_usuario) &&
	 * obj.getPasswordUsuario().equals(password_usuario)) {
	 * 
	 * model.addAttribute("accesoCorrecto",obj.getNomUsuario());
	 * model.addAttribute("imagen_usuario",new
	 * EncodeBase64().base64ToString(obj.getImagenUsuario()));
	 * 
	 * //session.setAttribute("usuario", obj);
	 * 
	 * return "/index"; } }
	 * 
	 * model.addAttribute("error","Las credenciales no son correctas"); return
	 * "loginRestaurante"; }
	 */
	
	@GetMapping({"/listaUsuario"})
	public String listaUsuario (Model model) {
		
		List<Usuario> listaUsuario = service.listarUsuario();
		Usuario usuario = new Usuario();
		usuario.setEstadoUsuario("ACTIVO");
		usuario.setFecharegUsuario(new java.sql.Date(new java.util.Date().getTime()));
		
		
		model.addAttribute("listaUsuario",listaUsuario);
		model.addAttribute("Base64",new EncodeBase64());
		model.addAttribute("usuario",usuario);
		model.addAttribute("listaDistrito",distritoService.listarDistrito());
		model.addAttribute("listaTipoUsuario",tipoUsuarioService.listarTipoUsuario());
		
		
		return "listaUsuario";
	}
	
	@GetMapping("/registraUsuario")
	public String registrarUsuario(Model model) {
		
		Usuario usuario = new Usuario();
		usuario.setEstadoUsuario("ACTIVO");
		usuario.setFecharegUsuario(new java.sql.Date(new java.util.Date().getTime()));
		
		model.addAttribute("usuario",usuario);
		model.addAttribute("listaDistrito",distritoService.listarDistrito());
		model.addAttribute("listaTipoUsuario",tipoUsuarioService.listarTipoUsuario());
		
		return "/registraUsuario";
	}
	
	@GetMapping("/actualizaUsuario/{id}")
	public String actualizaUsuario(@PathVariable(value="id") int id, Model model) {
		
		
		Usuario usuario = service.buscarUsuario(id);
		
		model.addAttribute("usuario",usuario);
		model.addAttribute("listaDistrito",distritoService.listarDistrito());
		model.addAttribute("listaTipoUsuario",tipoUsuarioService.listarTipoUsuario());
		
		
		return "/registraUsuario";
	}
	
	@PostMapping("/registraUsuario/guardar")
	public String actualizarUsuario (MultipartFile imagen ,@ModelAttribute("usuario") Usuario usuario,
			BindingResult result,Model model,RedirectAttributes flash) throws IOException {

		usuario.setImagenUsuario(imagen.getBytes());
			
		/*NO ME PERMITE ACTUALIZAR AL USUARIO*/
		
		Usuario usuarioExiste=service.buscarPorEmail(usuario.getEmailUsuario());
		if(Objects.nonNull(usuarioExiste)&& usuario.getIdUsuario()==0) {
			result.reject("emailUsuario",null,"Ya existe un usuario con el email proporcionado");
		} 
		
		if(result.hasErrors()) {
			model.addAttribute("usuario",usuario);
			return "redirect:/registraUsuario";
		} 

		service.registrarUsuario(usuario);
		
		return "redirect:/registraUsuario?success";
	}
	

	
}
