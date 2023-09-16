create table pacientes(
    id bigint not null auto_increment,
    nombre VARCHAR(100) not null,
    correo varchar(100) not null unique,
    telefono varchar(20) not null,
    documento VARCHAR(6) not null unique,
    calle varchar(100) not null,
    distrito varchar(100) not null,
    complemento varchar(100),
    numero varchar(20),
    ciudad varchar(100) not null,
    primary key(id)
);