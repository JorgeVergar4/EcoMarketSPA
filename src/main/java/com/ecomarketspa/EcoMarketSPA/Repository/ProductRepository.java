package com.ecomarketspa.EcoMarketSPA.Repository;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class ProductRepository {
    //Arreglo que guardará los product
    private List<ProductModel> listaProductos = new ArrayList<>();


    // Metodo que retora todos los producto
    public List<ProductModel> obtenerProductos() {
        return listaProductos;
    }

    //Buscar un producto por su id
    public ProductModel buscarPorId(int id_producto) {
        for (ProductModel producto : listaProductos) {
            if (producto.getId_producto() == id_producto) {
                return producto;
            }
        }
        return null;
    }

    //Buscar un producto por su nombre
    public ProductModel buscarPorNombre(String nombre_producto) {
        for (ProductModel producto : listaProductos) {
            if (producto.getNombre_producto().equals(nombre_producto)) {
                return producto;
            }
        }
        return null;
    }

    public ProductModel guardar(ProductModel producto) {
        listaProductos.add(producto);
        return producto;
    }

    public ProductModel actualizar(ProductModel producto) {
        int id = 0;
        int idPosicion = 0;

        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getId_producto() == producto.getId_producto()) {
                id = producto.getId_producto();
                idPosicion = i;
            }
        }

        ProductModel producto1 = new ProductModel();
        producto1.setId_producto(id);
        producto1.setNombre_producto(producto1.getNombre_producto());
        producto1.setDescripcion_producto(producto1.getDescripcion_producto());
        producto1.setPrecio_producto(producto1.getPrecio_producto());
        producto1.setStock_producto(producto1.getStock_producto());
        producto1.setEstado_producto(producto1.getEstado_producto());

        listaProductos.set(idPosicion, producto1);
        return producto1;
    }

    public void eliminar(int id) {
        int idPosicion = 0;
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getId_producto() == id) {
                idPosicion = i;
                break;
            }
        }
    }




}
