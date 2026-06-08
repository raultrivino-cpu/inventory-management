-- =====================================================
-- EMPRESA
-- =====================================================

CREATE TABLE empresa (
    nit VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(500) NOT NULL,
    direccion VARCHAR(500),
    telefono VARCHAR(20)
);

-- =====================================================
-- PRODUCTO
-- =====================================================

CREATE TABLE producto (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(500) NOT NULL,
    caracteristicas VARCHAR(500),

    precio_pesos NUMERIC(15,2),
    precio_dolares NUMERIC(15,2),
    precio_euros NUMERIC(15,2),

    empresa_nit VARCHAR(20) NOT NULL,

    CONSTRAINT fk_producto_empresa
        FOREIGN KEY (empresa_nit)
        REFERENCES empresa(nit)
);

-- =====================================================
-- CATEGORIA
-- =====================================================

CREATE TABLE categoria (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(500) NOT NULL,
    caracteristicas VARCHAR(500)
);

-- =====================================================
-- PRODUCTO_CATEGORIA
-- =====================================================

CREATE TABLE producto_categoria (
    id SERIAL PRIMARY KEY,

    producto_id INTEGER NOT NULL,
    categoria_id INTEGER NOT NULL,

    CONSTRAINT fk_producto_categoria_producto
        FOREIGN KEY (producto_id)
        REFERENCES producto(id),

    CONSTRAINT fk_producto_categoria_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categoria(id),

    CONSTRAINT uk_producto_categoria
        UNIQUE (producto_id, categoria_id)
);

-- =====================================================
-- CLIENTE
-- =====================================================

CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,

    identificacion VARCHAR(15) NOT NULL,
    tipo_id VARCHAR(2) NOT NULL,

    nombre VARCHAR(500) NOT NULL,
    apellido VARCHAR(500) NOT NULL,

    telefono VARCHAR(20),
    email VARCHAR(100) UNIQUE
);

-- =====================================================
-- ORDEN
-- =====================================================

CREATE TABLE orden_compra (
    id SERIAL PRIMARY KEY,

    fecha DATE NOT NULL,

    cliente_id INTEGER NOT NULL,

    CONSTRAINT fk_orden_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES cliente(id)
);

-- =====================================================
-- PRODUCTO_ORDEN
-- =====================================================

CREATE TABLE producto_orden (
    id SERIAL PRIMARY KEY,

    producto_id INTEGER NOT NULL,
    orden_id INTEGER NOT NULL,

    CONSTRAINT fk_producto_orden_producto
        FOREIGN KEY (producto_id)
        REFERENCES producto(id),

    CONSTRAINT fk_producto_orden_orden
        FOREIGN KEY (orden_id)
        REFERENCES orden_compra(id),

    CONSTRAINT uk_producto_orden
        UNIQUE (producto_id, orden_id)
);

-- =====================================================
-- ROL
-- =====================================================

CREATE TABLE rol (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO rol (nombre)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_EXTERNAL');

-- =====================================================
-- USUARIO
-- =====================================================

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,

    login VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,

    rol_id INTEGER NOT NULL,

    CONSTRAINT fk_usuario_rol
        FOREIGN KEY (rol_id)
        REFERENCES rol(id)
);