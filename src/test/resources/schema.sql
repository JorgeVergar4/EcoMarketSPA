CREATE TABLE products_table (
                                id_producto BIGINT PRIMARY KEY AUTO_INCREMENT,
                                nombre_producto VARCHAR(255) NOT NULL,
                                descripcion VARCHAR(255),
                                precio DOUBLE PRECISION NOT NULL,
                                stock INTEGER NOT NULL,
                                estado BOOLEAN NOT NULL
);