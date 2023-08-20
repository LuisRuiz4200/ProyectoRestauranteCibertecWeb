package com.web.restaurante.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.exolab.castor.types.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.restaurante.business.ColaboradorService;
import com.web.restaurante.business.Direntrega_UsuarioService;
import com.web.restaurante.business.PedidoService;
import com.web.restaurante.business.ProductoService;
import com.web.restaurante.business.Producto_PedidoService;
import com.web.restaurante.business.UsuarioService;
import com.web.restaurante.model.Colaborador;
import com.web.restaurante.model.Direntrega_Usuario;
import com.web.restaurante.model.Pedido;
import com.web.restaurante.model.Producto;
import com.web.restaurante.model.Producto_Pedido;
import com.web.restaurante.model.Usuario;
import com.web.restaurante.repository.ProductoRepository;
import com.web.restaurante.reuzable.EncodeBase64;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	@Autowired (required = false)
	private UsuarioService usuarioService;
	@Autowired (required = false)
	private ColaboradorService colaboradorService;
	@Autowired
	private Producto_PedidoService producto_PedidoService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private Direntrega_UsuarioService direntrega_UsuarioService;
	
	@GetMapping("/listaPedido")
	public String listadoPedido(Model model,HttpSession session) {
		
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		List<Pedido> listaPedido = new ArrayList<>();
		listaPedido = pedidoService.listarPorUsuario(usuario);
		
		
		
		model.addAttribute("listaPedido",listaPedido);
		model.addAttribute("Base64",new EncodeBase64());
		return "listaPedido";
	}
	
	@GetMapping("/nuevoPedido")
	public String registroPedidoFormulario(Model model, HttpSession session) {
		
		Pedido pedido = new Pedido();
		pedido.setEstadoPedido("ACTIVO");
		pedido.setFecharegPedido(new Date(new java.util.Date().getTime()));
		
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		
		List<Direntrega_Usuario> listaDirentrega = direntrega_UsuarioService.listarPorUsuario(usuario);
		List<Colaborador> listaColaborador = colaboradorService.listar();
		
		model.addAttribute("listaColaborador",listaColaborador);
		model.addAttribute("listaDirentrega",listaDirentrega);
		model.addAttribute("pedido",pedido);
		
		return "registraPedido";
	}
	
	@PostMapping("/guardarPedido")
	public String registroPedido(@ModelAttribute("pedido") Pedido pedido, HttpSession session) {

	    Usuario usuario = (Usuario) session.getAttribute("usuario");

	    pedido.setUsuarioCliente(usuario);
	    pedido.setTiempoEntregaPedido(new java.sql.Time(0));
	    pedido.setFechaactPedido(new Date(new java.util.Date().getTime()));

	    // Registrar el pedido en la base de datos
	    pedidoService.registrarPedido(pedido);

	    /* AGREGAMOS LOS PRODUCTOS AL DETALLE DEL PEDIDO */

	    List<Producto_Pedido> listaCarrito = (List<Producto_Pedido>) session.getAttribute("carrito");

	    for (Producto_Pedido producto : listaCarrito) {
	        producto.setPedido(pedido);
	        producto_PedidoService.agregar(producto);
	    }

	    // Limpiar la sesión del carrito
	    listaCarrito.clear();
	    session.setAttribute("carrito", listaCarrito);

	    return "redirect:/registraCompra";
	}
	
	@GetMapping("/actualizarPedido/{id}")
	public String actualizarPedidoFormulario(@PathVariable(value="id") int id, Model model) {
		Pedido pedido = pedidoService.listaPedidoPorId(id);
		model.addAttribute("pedido",pedido);
		
		return "actualizarPedido";
	}
	
	@GetMapping("/eliminarPedido/{id}")
	public String eliminarPedido(@PathVariable(value="id") int id) {
		pedidoService.eliminarPedido(id);
		return "refirect:/";
	}
	
	
	@GetMapping("/carroCompra")
	public String carroCompra (Model model,HttpSession session) {
		
		registroPedidoFormulario(model, session);
		
		return "carroCompra";
	}
	
	@GetMapping("/limpiarCarro")
	public String limpiarCarro(Model model,HttpSession session) {
		
		((ArrayList<Producto_Pedido>)session.getAttribute("carrito")).clear();
		
		return "redirect:/carroCompra";
	}
	
	
	
	
} //Fin de pedido controller
