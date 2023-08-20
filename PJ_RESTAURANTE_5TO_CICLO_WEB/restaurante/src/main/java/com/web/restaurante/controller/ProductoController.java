package com.web.restaurante.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.swing.JOptionPane;

import com.web.restaurante.business.CategoriaProService;
import com.web.restaurante.business.ProductoService;
import com.web.restaurante.model.CategoriaProducto;
import com.web.restaurante.model.Producto;
import com.web.restaurante.model.Producto_Pedido;

import jakarta.servlet.http.HttpSession;
@Controller
public class ProductoController {
	
	
	@Autowired
	private ProductoService service;
	//para combobox
	@Autowired
	private CategoriaProService serviceCat;
	
	@GetMapping("listaProducto/{rol}")
	public String listadoEmpleados(@PathVariable(value = "rol") String rol,Model model,HttpSession session) {
		
		model.addAttribute("listaProductos",service.listaProducto());
		model.addAttribute("listaCategoria",serviceCat.listaCateProducto());
		
		
		Producto_Pedido productoPedido = new Producto_Pedido();
		productoPedido.setCantidadProducto(1);
		
		model.addAttribute("productoPedido",productoPedido);
		model.addAttribute("Base64",new org.apache.tomcat.util.codec.binary.Base64());
		
		if (session.getAttribute("carrito")==null) {
			session.setAttribute("carrito", new ArrayList<Producto_Pedido>());
		}
		
		
		if(rol.equals("colaborador")) return "producto";
		
		return "homeProducto";
		
	}
	
	@GetMapping("/nuevoProducto")
	public String registroProductoFormulario(Model model) {
		//
		List<CategoriaProducto> dataCat=serviceCat.listaCateProducto();
		
		Producto producto=new Producto();
	
		model.addAttribute("producto",producto);
		model.addAttribute("cate",dataCat);
		//retornamos a la vista registroEmpleado
		return "registroProducto";
	}

	@PostMapping("/guardarProducto")
	public String registroProducto(@ModelAttribute("producto") Producto producto,MultipartFile imagen,
			Model model) throws IOException {
		
		//producto.setImagenProducto(imagen.getBytes());
		
		service.registrarProducto(producto);
		
		return "redirect:/listaProducto/colaborador";
	}
	
	@GetMapping("/actualizaProducto/{id}") 
	public String actualizaProductoFormulario(@PathVariable(value="id") int id,Model model) {
	
		Producto producto=service.listaProductoPorId(id);
		//Llenamos combo
		 List<CategoriaProducto> dataCat=serviceCat.listaCateProducto();
		 model.addAttribute("cate",dataCat);
		 
	
		model.addAttribute("producto",producto);
		//model.addAttribute("categoriaId",producto.getCategoriaProducto().getIdCategoriaProducto());
		model.addAttribute("categoriaId",producto.getCate().getIdCategoriaProducto());
		//y redireccionamos a la vista o html actualizaProducto
		return "actualizaProducto";
	}
	@GetMapping("/eliminarProducto/{id}")
	public String eliminaProducto(@PathVariable(value="id") int id) {
		
		service.eliminarProducto(id);
		return "redirect:/listaProducto/colaborador";
		/* return "redirect:/"; */
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/agregarCarro/{id}")
	public String llenarCarroCompra (@PathVariable(value="id")int id,
			@ModelAttribute("productoPedido")Producto_Pedido productoPedido,Model model,HttpSession session,
			RedirectAttributes flash) {
		
		String mensaje="¡ PRODUCTO AGREGADO !";
		
		Producto producto = service.listaProductoPorId(id);
		
		productoPedido.setProducto(producto);
		
		((ArrayList<Producto_Pedido>)session.getAttribute("carrito")).add(productoPedido);

		flash.addFlashAttribute("mensaje",mensaje);
		flash.addFlashAttribute("idProducto",id);
		
		return "redirect:/listaProducto/cliente";
	}
	
	
}// Fin de producto controller
