/**
 * Author:  marcelo
 * Created: 03-jul-2018
 */

CREATE TABLE categorias (
  id int(11) NOT NULL AUTO_INCREMENT,
  codigo varchar(15) NOT NULL,
  nombre varchar(30) NOT NULL,
  descripcion text,
  creacion datetime NOT NULL,
  modificacion datetime,
  creado_por int(11) NOT NULL,
  modificado_por int(11),
  PRIMARY KEY(id)
);

CREATE TABLE peliculas (
  id int(11) NOT NULL AUTO_INCREMENT,
  codigo varchar(15) NOT NULL,
  titulo varchar(120) NOT NULL,
  descripcion text NOT NULL,
  duracion varchar(15) NOT NULL,
  anio integer NOT NULL,
  creacion datetime NOT NULL,
  modificacion datetime,
  creado_por int(11) NOT NULL,
  modificado_por int(11),
  categoria_id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (categoria_id) REFERENCES categorias (id)
);

CREATE TABLE usuarios (
  id int(11) NOT NULL AUTO_INCREMENT,
  login varchar(15) NOT NULL,
  contrasenia varchar(60) NOT NULL,
  nombre varchar(30) NOT NULL,
  ultimo_acceso datetime,
  rol varchar(15) NOT NULL,
  habilitado char(1) NOT NULL,
  creacion datetime NOT NULL,
  modificacion datetime,
  creado_por int(11) NOT NULL,
  modificado_por int(11),
  PRIMARY KEY (id)
);

INSERT INTO categorias (codigo, nombre, descripcion, creacion, creado_por)
VALUES ('A-1000', 'Comedia', '', NOW(), 1);
INSERT INTO categorias (codigo, nombre, descripcion, creacion, creado_por)
VALUES ('A-2000', 'Familiar', '', NOW(), 1);
INSERT INTO categorias (codigo, nombre, descripcion, creacion, creado_por)
VALUES ('B-1000', 'Acción', '', NOW(), 1);
INSERT INTO categorias (codigo, nombre, descripcion, creacion, creado_por)
VALUES ('B-2000', 'Terror', '', NOW(), 1);
INSERT INTO categorias (codigo, nombre, descripcion, creacion, creado_por)
VALUES ('C-1000', 'Animadas', '', NOW(), 1);

INSERT INTO peliculas (codigo, titulo, descripcion, duracion, anio, creacion, creado_por, categoria_id)
VALUES ('A-1001', '¿Qué pasó ayer?', '', '01:15:00', 2010, NOW(), 1, 1);
INSERT INTO peliculas (codigo, titulo, descripcion, duracion, anio, creacion, creado_por, categoria_id)
VALUES ('A-1002', 'Zoolander', '', '01:20:00', 2009, NOW(), 1, 1);
INSERT INTO peliculas (codigo, titulo, descripcion, duracion, anio, creacion, creado_por, categoria_id)
VALUES ('A-2001', 'La Joya de la Familia', '', '01:25:00', 2006, NOW(), 1, 2);
INSERT INTO peliculas (codigo, titulo, descripcion, duracion, anio, creacion, creado_por, categoria_id)
VALUES ('B-1001', 'Los Vengadores', '', '01:35:00', 2016, NOW(), 1, 3);
INSERT INTO peliculas (codigo, titulo, descripcion, duracion, anio, creacion, creado_por, categoria_id)
VALUES ('B-1002', 'Batman: El Caballero de la Noche', '', '01:45:00', 2012, NOW(), 1, 3);
INSERT INTO peliculas (codigo, titulo, descripcion, duracion, anio, creacion, creado_por, categoria_id)
VALUES ('B-2001', 'El Aro', '', '01:25:00', 2006, NOW(), 1, 4);
INSERT INTO peliculas (codigo, titulo, descripcion, duracion, anio, creacion, creado_por, categoria_id)
VALUES ('B-2002', 'El Conjuro', '', '01:28:00', 2008, NOW(), 1, 4);
INSERT INTO peliculas (codigo, titulo, descripcion, duracion, anio, creacion, creado_por, categoria_id)
VALUES ('C-1001', 'Coco', '', '01:21:00', 2017, NOW(), 1, 5);
INSERT INTO peliculas (codigo, titulo, descripcion, duracion, anio, creacion, creado_por, categoria_id)
VALUES ('C-1002', 'Los Increibles 2', '', '01:18:00', 2018, NOW(), 1, 5);

INSERT INTO usuarios (login, contrasenia, nombre, rol, habilitado, creacion, creado_por)
VALUES ('jperez', SHA1('123456'), 'Juan Perez', 'operador', 'S', NOW(), 1);
INSERT INTO usuarios (login, contrasenia, nombre, rol, habilitado, creacion, creado_por)
VALUES ('mquispe', SHA1('123456'), 'Marcelo Quispe Ortega', 'administrador', 'S', NOW(), 1);