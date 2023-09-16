create table usuarios(
    id bigint not null auto_increment,
    nombre VARCHAR(100) not null,
    clave VARCHAR(300) not null,

    primary key(id)
);