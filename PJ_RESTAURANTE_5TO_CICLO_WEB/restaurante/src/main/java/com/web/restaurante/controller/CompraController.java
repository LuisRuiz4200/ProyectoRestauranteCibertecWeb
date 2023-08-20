package com.web.restaurante.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import com.web.restaurante.business.CompraService;
import com.web.restaurante.business.MedioPagoService;
import com.web.restaurante.business.PedidoService;
import com.web.restaurante.business.Producto_PedidoService;
import com.web.restaurante.model.Compra;
import com.web.restaurante.model.MedioPago;
import com.web.restaurante.model.Pedido;
import com.web.restaurante.model.Producto_Pedido;
import com.web.restaurante.model.Usuario;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CompraController{
	@Autowired
	private CompraService compraService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private Producto_PedidoService producto_PedidoService;
	@Autowired
	private MedioPagoService medioPagoService;
	
	
	@GetMapping("/listaCompra")
	public String listaCompra(Model model,HttpSession session) {
		
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		List<Compra> listaCompra = compraService.listarPorUsuario(usuario);
		
		model.addAttribute("listaCompra",listaCompra);
		
		return "listaCompra";
	}
	
	@GetMapping("/registraCompra")
	public String nuevaCompra(Model model,HttpSession session) {
		
		Usuario usuario = (Usuario)session.getAttribute("usuario"); 
		
		Pedido pedido = pedidoService.buscarUltimoPedidoPorUsuarioCliente(usuario);
		
		Compra compra = new Compra();
		compra.setEstadoCompra("REGISTRADO");
		compra.setFecharegCompra(new java.sql.Date(new Date().getTime()));
		compra.setPedido(pedido);
		compra.setMontoCompra(producto_PedidoService.getMontoTotalPorPedido(pedido));
		
		List<Producto_Pedido> listaProductoPedido  = producto_PedidoService.listarPorPedido(pedido);
		List<MedioPago> listaMedioPago = medioPagoService.listar();
		
		
		model.addAttribute("listaMedioPago",listaMedioPago);
		model.addAttribute("listaProductoPedido", listaProductoPedido);
		model.addAttribute("compra",compra);
		
		return "registraCompra";
	}
	
	@PostMapping("/guardaCompra")
	public String guardaCompra (@ModelAttribute("compra") @Valid Compra compra, Model model,
			RedirectAttributes flash) {
		
		String mensaje = "";
		
		compraService.agregar(compra);
		
		mensaje = "Compra registrada";
		
		flash.addAttribute("mensaje",mensaje);
		
		return "redirect:/listaCompra";
	}
	
}
