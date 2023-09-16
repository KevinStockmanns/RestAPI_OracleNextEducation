alter table pacientes add activo tinyint;
update pacientes set activo = 1;