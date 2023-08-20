package com.web.restaurante.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="tb_producto_pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto_Pedido {
	@ManyToOne
	@JoinColumn(name="id_pedido")
	private Pedido pedido;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_producto_pedido")
	private int idProductoPedido;
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;
	@Column(name="cantidad_producto")
	private int cantidadProducto;
}
