package com.web.restaurante.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.web.restaurante.business.CompraService;
import com.web.restaurante.model.Compra;
import com.web.restaurante.model.Pedido;
import com.web.restaurante.model.Usuario;
import com.web.restaurante.repository.CompraRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompraServiceImpl implements CompraService {

	private CompraRepository compraRepository;
	
	@Override
	public List<Compra> listar() {
		return compraRepository.findAll();
	}

	@Override
	public Compra buscar(int id) {
		return compraRepository.findById(id).get();
	}

	@Override
	public void agregar(Compra obj) {
		compraRepository.save(obj);
	}

	@Override
	public void editar(Compra obj) {
		compraRepository.save(obj);
	}

	@Override
	public void eliminar(int id) {
		compraRepository.deleteById(id);
	}

	@Override
	public List<Compra> listarPorUsuario(Usuario usuario) {
		
		List<Compra> listaCompra = new ArrayList<>();
		
		for (Compra compra :listar()) {
			if (compra.getPedido().getUsuarioCliente().getIdUsuario() == usuario.getIdUsuario()) {
				listaCompra.add(compra);
			}
		}
		
		return listaCompra;
	}

}
