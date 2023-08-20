package com.web.restaurante.business;

import java.util.List;
import com.web.restaurante.model.Pedido;
import com.web.restaurante.model.Usuario;

public interface PedidoService {
	
	public List<Pedido> listarPedido();
	public void registrarPedido(Pedido obj);
	public void eliminarPedido(int id);
	public Pedido listaPedidoPorId(int id);
	public void registrarPedidos(List<Pedido> pedidos);

	public Pedido buscarUltimoPedidoPorUsuarioCliente(Usuario usuarioCliente);
	public List<Pedido> listarPorUsuario(Usuario usuario);
	public int buscarUltimoIdPedidoPorUsuario(int id);
}
