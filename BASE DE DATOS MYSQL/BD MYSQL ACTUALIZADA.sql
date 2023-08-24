drop database if exists BD_RESTAURANTE_5TO_CICLO;

create database BD_RESTAURANTE_5TO_CICLO;

use BD_RESTAURANTE_5TO_CICLO;

create table tb_tipo_usuario(
	id_tipo_usuario int auto_increment primary key,
    des_tipo_usuario varchar(100) not null
);

create table tb_tipo_colaborador(
	id_tipo_colaborador int auto_increment primary key,
    des_tipo_colaborador varchar(100) not null
);

create table tb_distrito (
	id_distrito int auto_increment primary key,
    des_distrito varchar(100) not null
);

create table tb_mediopago(
	id_medio_pago int auto_increment primary key,
    des_medio_pago varchar(100) not null
);

create table tb_categoria_producto(
	id_categoria_producto int auto_increment primary key,
    des_categoria_producto varchar(100) not null
);

create table tb_usuario(
	id_usuario int auto_increment primary key,
    id_tipo_usuario int not null , 
    cod_usuario varchar(100) not null,
    nom_usuario varchar(100) not null,
    ape_usuario varchar(100) not null,
    tel_usuario char(12),
    cel_usuario char(12)not null,
    id_distrito int not null ,
    dir_usuario varchar(100) not null,
    dni_usuario char(8) not null,
    email_usuario varchar(100) not null,
    password_usuario varchar(300) not null,
    imagen_usuario longblob ,
    fechaReg_usuario DateTime not null,
    fechaAct_usuario DateTime ,
    estado_usuario varchar(100) not null,
	
    foreign key (id_tipo_usuario) references tb_tipo_usuario(id_tipo_usuario),
    foreign key (id_distrito) references tb_distrito(id_distrito)
);

create table tb_colaborador(
	id_colaborador int auto_increment primary key,
    id_tipo_colaborador int not null,
    nom_colaborador varchar(100) not null,
    ape_colaborador varchar(100) not null,
    dni_colaborador char(8) not null,
    imagen_colaborador longblob,
    fechareg_colaborador DateTime not null,
    fechaact_colaborador DateTime null,
    estado_colaborador varchar(100) not null,
    
    foreign key (id_tipo_colaborador) references tb_tipo_colaborador (id_tipo_colaborador)
    
);

create table tb_direntrega_usuario(
	id_usuario int not null,
    id_direntrega int auto_increment,
    id_distrito int not null,
    des_direntrega varchar(100) not null,
    fechareg_direntrega datetime not null,
    estado_direntrega varchar(100) not null,
    
    primary key (id_direntrega, id_usuario),
    #key(id_usuario),
    foreign key (id_usuario) references tb_usuario (id_usuario),
    foreign key (id_distrito) references tb_distrito(id_distrito)
);


create table tb_pedido(
	id_pedido int auto_increment primary key,
    id_usuario_cliente int not null,
    id_direntrega int references tb_direntrega,
    id_colaborador_repartidor int not null,
    tiempoentrega_pedido time not null,
    fechareg_pedido datetime not null,
    fechaact_pedido datetime,
    estado_pedido varchar (100) not null,
    
    foreign key (id_usuario_cliente) references tb_usuario (id_usuario),
    foreign key (id_colaborador_repartidor) references tb_colaborador (id_colaborador),
	foreign key (id_usuario_cliente, id_direntrega) references tb_direntrega_usuario (id_usuario, id_direntrega)
);

create table tb_producto (
	id_producto int auto_increment primary key,
    id_categoria_producto int not null,
    nom_producto varchar(100) not null,
    des_producto varchar(500),
    preciouni_producto double not null,
    stock_producto int not null,
    imagen_producto longblob,
    estado_producto varchar(100) not null,
    
    foreign key (id_categoria_producto) references tb_categoria_producto (id_categoria_producto)
);

create table  tb_producto_pedido (
	id_pedido int not null,
    id_producto_pedido int not null auto_increment,
    id_producto  int not null,
    cantidad_producto int not null,
    
    primary key(id_producto_pedido, id_pedido),
    foreign key (id_pedido) references tb_pedido (id_pedido),
    foreign key (id_producto) references tb_producto (id_producto)
);

create table tb_seguimiento_pedido(
	id_pedido int not null,
    id_seguimiento_pedido int auto_increment,
    fechareg_seguimiento_pedido datetime not null,
    estado_seguimmiento_pedido varchar(100) not null,
    
    primary key (id_seguimiento_pedido,id_pedido),
    foreign key (id_pedido) references tb_pedido(id_pedido)

);

create table tb_compra (
	id_compra int auto_increment primary key,
    id_pedido int not null unique,
    id_medio_pago int not null,
    monto_compra double not null,
    fechareg_compra datetime not null,
    estado_compra varchar(100) not null,
    
    foreign key (id_pedido) references tb_pedido(id_pedido),
    foreign key (id_medio_pago) references tb_mediopago(id_medio_pago)

);

create table tb_comentario (
	id_comentario int auto_increment primary key,
    id_usuario_cliente int not null,
    des_comentario varchar(500) not null,
    cantestrella_comentario int(5) not null,
    fechareg_comentario datetime not null,
    estado_comentario varchar(100) not null,
    
    foreign key (id_usuario_cliente) references tb_usuario (id_usuario)
);
