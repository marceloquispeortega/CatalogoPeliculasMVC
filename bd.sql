/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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