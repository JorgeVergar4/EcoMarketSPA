package com.ecomarketspa.EcoMarketSPA.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO que representa un producto en el sistema")
public class ProductDto {

    @Schema(description = "ID del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Café Orgánico")
    private String nombre;

    @Schema(description = "Descripción detallada del producto", example = "Café cultivado a más de 1000 msnm")
    private String descripcion;

    @Schema(description = "Precio del producto en CLP", example = "4990")
    private Double precio;

    @Schema(description = "Cantidad disponible en stock", example = "100")
    private Integer stock;

    @Schema(description = "Estado del producto (activo/inactivo)", example = "true")
    private Boolean estado;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
