DROP DATABASE IF EXISTS BiosRentACar;

CREATE DATABASE BiosRentACar;

USE BiosRentACar;

CREATE TABLE Sucursales (
    Codigo INT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL);

CREATE TABLE Empleados(
	Usuario VARCHAR(16) PRIMARY KEY,
    Contrasena VARCHAR(8) NOT NULL,
    CodigoSucursal INT NOT NULL,
    FOREIGN KEY (CodigoSucursal) REFERENCES Sucursales (Codigo)
);

CREATE TABLE Clientes(
	Ci VARCHAR(8) PRIMARY KEY,
    NombreCompleto VARCHAR(50),
    Telefono VARCHAR(20)
);

CREATE TABLE Vehiculos(
	Matricula VARCHAR(7) PRIMARY KEY,
    Tipo VARCHAR(50) NOT NULL,
    Descripcion VARCHAR(50) NOT NULL,
    PrecioPorDia FLOAT,
    Activo Boolean
);

CREATE TABLE SucursalVehiculo(
	CodigoSucursal INT,
	Matricula VARCHAR(7),
    FOREIGN KEY (CodigoSucursal) REFERENCES Sucursales (Codigo),
    FOREIGN KEY (Matricula) REFERENCES Vehiculos (Matricula),
	PRIMARY KEY (CodigoSucursal , Matricula)
);

CREATE TABLE Alquileres(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    CiCliente VARCHAR(8) NOT NULL,
    CodigoSucursal INT NOT NULL,
    Matricula VARCHAR(7) NOT NULL,
    Fecha DATE NOT NULL,
    CantidadDias INT NOT NULL,
    ImporteSeguro FLOAT NOT NULL,
    ImporteDeposito FLOAT NOT NULL,
    FOREIGN KEY (CiCliente) REFERENCES Clientes (Ci),
    FOREIGN KEY (CodigoSucursal) REFERENCES Sucursales (Codigo),
    FOREIGN KEY (Matricula) REFERENCES Vehiculos (Matricula)
);

CREATE TABLE Devoluciones(
	IdAlquiler INT NOT NULL,
    CodigoSucursal INT NOT NULL,
    Fecha DATE NOT NULL,
    PRIMARY KEY (IdAlquiler,CodigoSucursal),
    FOREIGN KEY (IdAlquiler) REFERENCES Alquileres (Id),
    FOREIGN KEY (CodigoSucursal) REFERENCES Sucursales (Codigo)
);


DELIMITER //

CREATE PROCEDURE altaCliente(pCi VARCHAR(8), pNombreCompleto VARCHAR(50), pTelefono VARCHAR(20), OUT pMensajeError VARCHAR(100))
cuerpo:BEGIN

IF EXISTS (SELECT * FROM Clientes WHERE Ci = pCi) THEN
	SET pMensajeError = 'El Cliente ya existe.';
   
			LEAVE cuerpo;
END IF;

    INSERT INTO Clientes (Ci, NombreCompleto, Telefono)
    VALUES (pCi, pNombreCompleto, pTelefono);
  END// 

  
  CREATE PROCEDURE modificarCliente(pCi VARCHAR(8), pNombreCompleto VARCHAR(50), pTelefono VARCHAR(20), OUT pMensajeError VARCHAR(100))
cuerpo:BEGIN

IF NOT EXISTS (SELECT * FROM Clientes WHERE Ci = pCi) THEN
	SET pMensajeError = 'El Cliente no existe.';
   
			LEAVE cuerpo;
END IF;
    
    UPDATE Clientes SET NombreCompleto = pNombreCompleto, Telefono = pTelefono 
	WHERE Ci = pCi;
  END//  
  
  
  CREATE PROCEDURE bajaCliente (pCi VARCHAR(8), OUT pMensajeError VARCHAR(100))
cuerpo:BEGIN
IF NOT EXISTS (SELECT * FROM Clientes WHERE Ci = pCi) THEN
	SET pMensajeError = 'El Cliente no existe.';
			LEAVE cuerpo;
END IF;

IF EXISTS (SELECT * FROM Alquileres WHERE CiCliente = pCi) THEN 

		SET pMensajeError = 'El Cliente no se puede eliminar, ya ha realizado alquileres.';
			LEAVE cuerpo;
END IF;
        
     DELETE FROM Clientes 
	WHERE Ci = pCi;
  END//  
  

CREATE PROCEDURE ListarVehiculosSucursal(pSucursal INT)
cuerpo:BEGIN
	SELECT Vehiculos.*, SucursalVehiculo.CodigoSucursal FROM Vehiculos INNER JOIN SucursalVehiculo ON Vehiculos.Matricula = SucursalVehiculo.Matricula 
    WHERE SucursalVehiculo.CodigoSucursal = pSucursal AND Vehiculos.Activo = 1;
END//

CREATE PROCEDURE TrasladarVehiculo(pMatricula VARCHAR(7),pSucursal INT, OUT pMensajeError VARCHAR(100))
cuerpo:BEGIN
	IF NOT EXISTS(
		SELECT *
        FROM Vehiculos
        WHERE Matricula = pMatricula) THEN
			SET pMensajeError = 'El vehiculo no existe.';
            
			LEAVE cuerpo;
	END IF;
    
    IF NOT EXISTS(
		SELECT *
        FROM SucursalVehiculo
        WHERE Matricula = pMatricula) THEN
			SET pMensajeError = 'El vehiculo se encuentra alquilado.';
			LEAVE cuerpo;
	END IF;
    
    UPDATE SucursalVehiculo SET CodigoSucursal = pSucursal WHERE Matricula = pMatricula;
    
END//

CREATE PROCEDURE AlquilarVehiculo(pMatricula varchar(7),pCiCliente VARCHAR(8), pCodigoSucursal INT,pCantidadDias INT, pImporteSeguro FLOAT, pImporteDeposito FLOAT, OUT pMensajeError VARCHAR(100))
cuerpo:BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

 -- Compruebo existencia del vehiculo.
	IF NOT EXISTS(
		SELECT *
        FROM Vehiculos
        WHERE Matricula = pMatricula  AND Activo = 1) THEN
			SET pMensajeError = 'El vehiculo no existe.';
            
			LEAVE cuerpo;
	END IF;

-- Compruebo existencia del cliente.
	IF NOT EXISTS(
		SELECT *
        FROM Clientes
        WHERE Ci = pCiCliente) THEN
			SET pMensajeError = 'El cliente no existe.';
            
			LEAVE cuerpo;
	END IF;

-- Compruebo existencia de la sucursal.
	IF NOT EXISTS(
		SELECT *
        FROM Sucursales
        WHERE Codigo = pCodigoSucursal) THEN
			SET pMensajeError = 'La Sucursal no existe.';
            
			LEAVE cuerpo;
	END IF;

-- Compruebo que el vehiculo no este alquilado.    
    IF NOT EXISTS(
		SELECT *
        FROM SucursalVehiculo
        WHERE Matricula = pMatricula) THEN
			SET pMensajeError = 'El vehiculo se encuentra alquilado.';
			LEAVE cuerpo;
	END IF;
  
  -- Compruebo que el vehiculo sea de la sucursal indicada.
    IF NOT EXISTS(
		SELECT *
        FROM SucursalVehiculo
        WHERE Matricula = pMatricula && CodigoSucursal = pCodigoSucursal) THEN
			SET pMensajeError = 'El vehiculo se encuentra en otra sucursal.';
			LEAVE cuerpo;
	END IF;
    
	-- Compruebo que el cliente no tenga un alquiler activo.
    IF EXISTS(
		SELECT *
        FROM Alquileres 
        WHERE CiCliente = pCiCliente 
        AND NOT EXISTS (SELECT * FROM Devoluciones WHERE Devoluciones.IdAlquiler = Alquileres.Id))
        THEN
			SET pMensajeError = 'El cliente ya tiene un alquiler activo.';
			LEAVE cuerpo;
	END IF;
    
    IF EXISTS(
    SELECT * 
    FROM Alquileres 
    WHERE CiCliente = pCiCliente
    AND NOT EXISTS(SELECT * FROM Devoluciones WHERE Devoluciones.IdAlquiler = Alquileres.Id)
    ) THEN 
		SET pMensajeError = 'El cliente cuenta con alquileres activos.';
		LEAVE cuerpo;
	END IF;

	START TRANSACTION;
		INSERT Alquileres (CiCliente,CodigoSucursal,Matricula,Fecha,CantidadDias,ImporteSeguro,ImporteDeposito) 
		VALUES (pCiCliente,pCodigoSucursal,pMatricula,NOW(),pCantidadDias,pImporteSeguro,pImporteDeposito);
        
        DELETE FROM SucursalVehiculo WHERE Matricula = pMatricula;
	COMMIT;
    
    
END//   


CREATE PROCEDURE CantidadAlquileres(pCiCliente VARCHAR(8), OUT pCantidadAlquileres INT)
cuerpo:BEGIN
	SET pCantidadAlquileres = (SELECT count(*) 
    FROM Alquileres 
    WHERE CiCliente = pCiCliente);
END//


CREATE PROCEDURE AlquilerActual (pCiCliente VARCHAR(8))
cuerpo:BEGIN

    SELECT * FROM Alquileres 
    WHERE CiCliente = pCiCliente
    AND NOT EXISTS(SELECT * FROM Devoluciones WHERE Devoluciones.IdAlquiler = Alquileres.Id);
    
END//


CREATE PROCEDURE AltaDevolucion (pIdAlquiler INT, pCodigoSucursal INT, pFecha DATE, OUT pMensajeError VARCHAR(100))
cuerpo:BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

/* Compruebo que exista el Alquiler*/
	IF NOT EXISTS(
		SELECT *
        FROM Alquileres
        WHERE Id = pIdAlquiler) THEN
			SET pMensajeError = 'El alquiler no existe.';
            
			LEAVE cuerpo;
	END IF;

/* Compruebo que el Alquiler no se haya devuelto. */
	IF EXISTS(
		SELECT *
        FROM Devoluciones
        WHERE IdAlquiler = pIdAlquiler) THEN
			SET pMensajeError = 'El alquiler ya se encuentra devuelto.';
			LEAVE cuerpo;
	END IF;

-- Compruebo existencia de la sucursal de destino.
	IF NOT EXISTS(
		SELECT *
        FROM Sucursales
        WHERE Codigo = pCodigoSucursal) THEN
			SET pMensajeError = 'La sucursal de destino no existe.';
            
			LEAVE cuerpo;
	END IF;
    
    /* Obtengo la matricula para dar de alta en cual sucursal donde se devuelve el vehiculo. */
    SET @matricula = (SELECT Matricula FROM Alquileres WHERE Id = pIdAlquiler);
    
	START TRANSACTION;
		INSERT INTO Devoluciones (IdAlquiler, CodigoSucursal, Fecha)
		VALUES (pIdAlquiler, pCodigoSucursal, pFecha);
        
        INSERT INTO SucursalVehiculo (CodigoSucursal, Matricula) 
        VALUES (pCodigoSucursal, @matricula);
	COMMIT;
    
    
END//

CREATE PROCEDURE altaVehiculo(pMatricula VARCHAR(7), pTipo VARCHAR(50), pDescripcion VARCHAR(50), pPrecioPorDia FLOAT, pCodigoSucursal int,  OUT pMensajeError VARCHAR(100))
cuerpo:BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

-- verifico que exista y este activo
IF EXISTS (SELECT * FROM Vehiculos WHERE Matricula = pMatricula AND activo = 1) THEN
	SET pMensajeError = 'El Vehiculo ya existe.';   
			LEAVE cuerpo;
END IF;

-- si no esta activo actualizo datos y lo dejo activo
IF EXISTS (SELECT * FROM Vehiculos WHERE Matricula = pMatricula AND activo= 0) THEN	
START TRANSACTION;
		UPDATE Vehiculos SET Tipo = pTipo, Descripcion = pDescripcion, PrecioPorDia = pPrecioPorDia, activo = 1 
		WHERE Matricula = pMatricula;
        
        INSERT sucursalvehiculo(CodigoSucursal, Matricula) VALUES (pCodigoSucursal, pMatricula);
COMMIT;
			LEAVE cuerpo;
END IF;

-- lo agrego
START TRANSACTION;
	INSERT INTO Vehiculos (Matricula, Tipo, Descripcion, PrecioPorDia, activo)
	VALUES (pMatricula, pTipo, pDescripcion, pPrecioPorDia, 1);
    
	INSERT sucursalvehiculo(CodigoSucursal, Matricula) VALUES (pCodigoSucursal, pMatricula);
COMMIT;
END// 

CREATE PROCEDURE modificarVehiculo(pMatricula VARCHAR(7), pTipo VARCHAR(50), pDescripcion VARCHAR(50), pPrecioPorDia FLOAT, OUT pMensajeError VARCHAR(100))
cuerpo:BEGIN

-- verifico que este activo
IF EXISTS (SELECT * FROM Vehiculos WHERE Matricula = pMatricula AND activo = 0) THEN
	SET pMensajeError = 'El Vehiculo no existe.';   
			LEAVE cuerpo;
END IF;

-- actualizo
UPDATE Vehiculos SET Tipo = pTipo, Descripcion = pDescripcion, PrecioPorDia = pPrecioPorDia
WHERE Matricula = pMatricula;		
	LEAVE cuerpo;
	
  END// 

CREATE PROCEDURE bajaVehiculo(pMatricula VARCHAR(7), OUT pMensajeError VARCHAR(100))
cuerpo:BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

-- verifico que exista 
IF NOT EXISTS (SELECT * FROM Vehiculos WHERE Matricula = pMatricula AND ACTIVO = 1) THEN
	SET pMensajeError = 'El Vehiculo no existe.';   
			LEAVE cuerpo;
END IF;

-- si tiene alquileres activos no lo puedo borrar
IF ((SELECT COUNT(alquileres.id) 
	FROM Vehiculos 
	JOIN ALQUILERES ON VEHICULOS.MATRICULA = ALQUILERES.MATRICULA 
	WHERE VEHICULOS.Matricula = pMatricula AND activo = 1)
!= (SELECT COUNT(Alquileres.id) 
	FROM Alquileres
	JOIN Devoluciones ON Devoluciones.IdAlquiler = Alquileres.Id
	WHERE Alquileres.Matricula = pMatricula))
THEN
    SET pMensajeError = 'El vehiculo tiene alquileres activos.';
		LEAVE cuerpo;
END IF;

-- verifico si  tiene alquileres hago baja logica
IF EXISTS (SELECT * FROM Vehiculos 
			JOIN Alquileres ON Alquileres.Matricula = Vehiculos.Matricula
            WHERE Vehiculos.Matricula = pMatricula AND activo = 1) THEN
START TRANSACTION;
	DELETE FROM sucursalvehiculo where Matricula = pMatricula;
	UPDATE Vehiculos SET activo = 0 WHERE Matricula = pMatricula;	
COMMIT;
	LEAVE cuerpo;
END IF;

-- si no tiene alquileres asociados lo borro
START TRANSACTION;
DELETE FROM sucursalvehiculo where Matricula = pMatricula;
DELETE FROM Vehiculos WHERE Matricula = pMatricula;
COMMIT;
LEAVE cuerpo;
	
END// 


DELIMITER ;


/*--------------------INSERTS-----------------*/
INSERT INTO Sucursales(Codigo,Nombre) VALUES(1,"Atlantida");
INSERT INTO Sucursales(Codigo,Nombre) VALUES(2,"Tangamandapio");
INSERT INTO Sucursales(Codigo,Nombre) VALUES(3,"Melo");
INSERT INTO Empleados(Usuario,Contrasena,CodigoSucursal) VALUES("user","user",1);
INSERT INTO Empleados(Usuario,Contrasena,CodigoSucursal) VALUES("dhernandez","password",1);
INSERT INTO Empleados(Usuario,Contrasena,CodigoSucursal) VALUES("user2","user2",2);
INSERT INTO EMPLEADOS(Usuario,Contrasena,CodigoSucursal) VALUES("dboz","1234","2");
INSERT INTO Empleados(Usuario,Contrasena,CodigoSucursal) VALUES("user3","user3",3);
INSERT INTO EMPLEADOS(Usuario,Contrasena,CodigoSucursal) VALUES("bbardesio","1234","3");
/* Vehiculos sin alquiler */
INSERT INTO VEHICULOS (matricula, tipo, descripcion, preciopordia, activo) VALUES ("ABC1234", "Compacto", "Bueno, bonito y barato.", 100, 1); 
INSERT INTO VEHICULOS (matricula, tipo, descripcion, preciopordia, activo) VALUES ("CBA4321", "Grande", "Malo, feo y caro.", 10000, 1); 
INSERT INTO VEHICULOS (matricula, tipo, descripcion, preciopordia, activo) VALUES ("AMN7495", "Pickup", "Linda camionetita.", 7520, 1); 
INSERT INTO VEHICULOS (matricula, tipo, descripcion, preciopordia, activo) VALUES ("ERT2568", "Gigante", "Tremendo camion.", 350000, 1); 
INSERT INTO VEHICULOS (matricula, tipo, descripcion, preciopordia, activo) VALUES ("STD7452", "Mini", "Auto miniatura.", 200, 1); 
INSERT INTO VEHICULOS (matricula, tipo, descripcion, preciopordia, activo) VALUES ("AAB7412", "Bus", "Un bondi.", 150000, 1); 
INSERT INTO VEHICULOS (matricula, tipo, descripcion, preciopordia, activo) VALUES ("AAB8521", "Compacto", "Lindo autito.", 12000, 1); 

/* Vehiculos alquilados */
INSERT INTO VEHICULOS (matricula, tipo, descripcion, preciopordia, activo) VALUES ("AAE3698", "Alto", "Alto auto.", 12400, 1); 
INSERT INTO VEHICULOS (matricula, tipo, descripcion, preciopordia, activo) VALUES ("AAS7458", "Bajo", "Bajo auto.", 12100, 1); 

INSERT INTO SUCURSALVEHICULO (codigoSucursal, matricula) VALUES (1,"ABC1234");
INSERT INTO SUCURSALVEHICULO (codigoSucursal, matricula) VALUES (1,"CBA4321");
INSERT INTO SUCURSALVEHICULO (codigoSucursal, matricula) VALUES (2,"AMN7495");
INSERT INTO SUCURSALVEHICULO (codigoSucursal, matricula) VALUES (2,"ERT2568");
INSERT INTO SUCURSALVEHICULO (codigoSucursal, matricula) VALUES (2,"STD7452");
INSERT INTO SUCURSALVEHICULO (codigoSucursal, matricula) VALUES (3,"AAB7412");
INSERT INTO SUCURSALVEHICULO (codigoSucursal, matricula) VALUES (3,"AAB8521");

/* Clientes sin alquileres */
INSERT INTO CLIENTES (Ci, NombreCompleto, Telefono) VALUES ("54025652","Damian Boz","094391883");
INSERT INTO CLIENTES (Ci, NombreCompleto, Telefono) VALUES ("12345678","User Random","094111222");
INSERT INTO CLIENTES (Ci, NombreCompleto, Telefono) VALUES ("12345679","User Aleatorio","094222333");

/* Clientes con alquileres */
INSERT INTO CLIENTES (Ci, NombreCompleto, Telefono) VALUES ("18077970","Ragnar Lodbrock","099391983");
INSERT INTO Clientes (Ci,NombreCompleto,Telefono) VALUES ('48524325','Pepe Trueno','099852963');

insert into alquileres (CiCliente,CodigoSucursal,Matricula,Fecha,CantidadDias,ImporteSeguro,ImporteDeposito) 
values ("48524325", 1, "AAE3698", "2019-07-13",60,5000,12000);
insert into alquileres (CiCliente,CodigoSucursal,Matricula,Fecha,CantidadDias,ImporteSeguro,ImporteDeposito) 
values ("18077970", 1, "AAS7458", "2019-07-03",5,5000,12000);

/*--------------------Fin Inserts-----------------*/

/*
select * from vehiculos;
select * from sucursalvehiculo;
SELECT * FROM SUCURSALES;
SELECT * from alquileres;
select * from devoluciones;
select * from empleados;

*/

