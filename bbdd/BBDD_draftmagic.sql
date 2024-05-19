-- BASE DE DATOS DEL PFC DE DRAFT DE MAGIC DE ALBA SANCHEZ-MIGALLON ARIAS
DROP DATABASE IF EXISTS DRAFT_MAGIC;
CREATE DATABASE IF NOT EXISTS DRAFT_MAGIC CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE DRAFT_MAGIC;

-- Informacion de las cartas y estadisticas de cartas. Mezcla la info del json con las estadisticas del csv
CREATE TABLE IF NOT EXISTS CARTA(
	ID_CARTA INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la carta',
    NOMBRE_ORIGINAL VARCHAR(100) COMMENT 'Nombre original de la carta en ingles',
    NOMBRE_ESPANIOL VARCHAR(100) COMMENT 'Nombre de la traduccion al espaniol de la carta',
    RAREZA VARCHAR(45) COMMENT 'Rareza de la carta',
    COSTE INT  DEFAULT 0 COMMENT 'Coste de mana de la carta',
    COLOR VARCHAR(30) COMMENT 'Color o colores de carta',
    TIPO VARCHAR(100) COMMENT 'Tipo de carta',
    PNG VARCHAR(300) COMMENT 'Direccion de descarga de png de la carta en espaniol',
    COLECCION VARCHAR(45) COMMENT 'Nombre de la coleccion a la que pertenece la carta',
    SEEN INT  DEFAULT 0 COMMENT 'Cantidad de veces que se ha visto la carta en draft',
    ALSA DECIMAL(4,2) DEFAULT 0 COMMENT 'Ultima ronda en la que se vio la carta y no se pickeo',
    ATA DECIMAL(4,2) DEFAULT 0 COMMENT 'Ronda en la que se suele pickear la carta',
    PICKED INT  DEFAULT 0 COMMENT 'Cuantas veces se ha pickeado la carta en un draft',
    GP INT DEFAULT 0 COMMENT 'Porcentaje de veces que se ha pickeado la carta',
    GP_WR DECIMAL(4,2) DEFAULT 0 COMMENT 'Winrate de la carta',
	CONSTRAINT PK_CARTA PRIMARY KEY (ID_CARTA)
)COMMENT 'Tabla de las cartas y estadisticas de cartas';

ALTER TABLE CARTA CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Tabla de usuarios registrados
CREATE TABLE USUARIO(
	ID_USUARIO INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador del usuario',
	NOMBRE varchar(50) NOT NULL COMMENT 'Nombre del usuario',
	CONTRASENIA varchar(30) NOT NULL COMMENT 'Contrasenia del usuario',
    NUM_DRAFT INT DEFAULT 0 COMMENT 'Numero de drafts jugados por el usuario',
	CONSTRAINT PK_USUARIO PRIMARY KEY (ID_USUARIO)
)COMMENT 'Datos usuario';

ALTER TABLE USUARIO CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Historico de mazos construidos con las estadisticas
CREATE TABLE IF NOT EXISTS MAZO(
	ID_MAZO INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador del mazo',
	FECHA_CREACION TIMESTAMP NULL COMMENT 'Fecha de creacion del mazo',
	CANTIDAD_CARTAS INT NOT NULL DEFAULT 0 COMMENT 'Cantidad del cartas que componen el mazo',
    WINRATE DECIMAL(6,2) NOT NULL DEFAULT 0 COMMENT 'WINRATE del mazo',
    CURVA_MANA INT DEFAULT 0 COMMENT 'Curva de mana del mazo',
    COLOR_W INT DEFAULT 0 COMMENT 'Cantidad de cartas del mazo de color blanco',
    COLOR_B INT DEFAULT 0 COMMENT 'Cantidad de cartas del mazo de color negro',
    COLOR_R INT DEFAULT 0 COMMENT 'Cantidad de cartas del mazo de color rojo, ',
    COLOR_G INT DEFAULT 0 COMMENT 'Cantidad de cartas del mazo de color verde',
    COLOR_U INT DEFAULT 0 COMMENT 'Cantidad de cartas del mazo de color azul',
    MULTI_COLOR INT DEFAULT 0 COMMENT 'Cantidad de cartas del mazo con varios colores',
    CRIATURAS INT DEFAULT 0 COMMENT 'Cantidad de cartas de tipo criatura',
    ARTEFACTOS INT DEFAULT 0 COMMENT 'Cantidad de cartas de tipo artefacto',
	INSTANTANEOS INT DEFAULT 0 COMMENT 
    'Cantidad de cartas de tipo instantaneo, encantamiento y brujeria',
    COMUNES INT DEFAULT 0 COMMENT 'Cantidad de cartas de rareza comun',
    INFRECUENTES INT DEFAULT 0 COMMENT 'Cantidad de cartas de rareza infrecuente',
    RARAS INT DEFAULT 0 COMMENT 'Cantidad de cartas de rareza rara',
    LEGENDARIAS INT DEFAULT 0 COMMENT 'Cantidad de cartas de rareza legendaria',
    ID_USUARIO INT COMMENT 'Identificador del usuario creador del mazo',
    CONSTRAINT PK_MAZO PRIMARY KEY (ID_MAZO),
	CONSTRAINT FK_MAZO_ID_USUARIO FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID_USUARIO)
)COMMENT 'Tabla de historico de mazos construidos';

ALTER TABLE MAZO CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

