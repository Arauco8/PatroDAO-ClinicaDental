create table IF NOT EXISTS pacientes(id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      apellido varchar(255),
                                      nombre varchar (255),
                                      documento varchar (255),
                                      fechaIngreso TIMESTAMP without time zone,
                                      domicilioId int);
create table IF NOT EXISTS odontologos(id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      matricula varchar(255),
                                      apellido varchar (255),
                                      nombre varchar (255));
create table IF NOT EXISTS domicilios(id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      calle varchar(255),
                                      numero varchar (255),
                                      localidad varchar (255),
                                      provincia varchar (255));