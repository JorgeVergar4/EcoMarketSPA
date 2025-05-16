package com.ecomarketspa.EcoMarketSPA.Service;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;

import java.util.List;

public interface ProductService {


    ProductModel saveProducto(ProductModel product);

    ProductModel getIdProducto(Long id);

    ProductModel updateProducto(ProductModel product);

    String deleteProducto(Long id);

    List<ProductModel> findAll();
}