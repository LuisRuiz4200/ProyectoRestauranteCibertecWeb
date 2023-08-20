package com.web.restaurante.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "tb_compra")
@Data
public class Compra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_compra")
	private int idCompra;
	@ManyToOne
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;
	@ManyToOne
	@JoinColumn(name = "id_medio_pago")
	private MedioPago medioPago;
	@Column(name = "monto_compra")
	private double montoCompra;
	@Column(name = "fechareg_compra")
	private Date fecharegCompra;
	@Column(name = "estado_compra")
	private String estadoCompra;
	
}
