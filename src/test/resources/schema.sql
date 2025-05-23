CREATE TABLE products_table (
                                id_producto BIGINT PRIMARY KEY AUTO_INCREMENT,
                                nombre_producto VARCHAR(255) NOT NULL,
                                descripcion VARCHAR(255),
                                precio DOUBLE PRECISION NOT NULL,
                                stock INTEGER NOT NULL,
                                estado BOOLEAN NOT NULL
);

CREATE TABLE users_table (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             login VARCHAR(255) NOT NULL UNIQUE,
                             password VARCHAR(255) NOT NULL,
                             email VARCHAR(255) NOT NULL UNIQUE,
                             address VARCHAR(255) NOT NULL
);