package com.ecomarketspa.EcoMarketSPA.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "products_table")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Integer id_producto;

    String nombre_producto;

    String descripcion_producto;

    Double precio_producto;

    Integer stock_producto;

    Boolean estado_producto;

}
