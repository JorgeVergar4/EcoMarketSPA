package com.ecomarketspa.EcoMarketSPA.Repository;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    ProductModel findByNombre(String nombre);

    Optional<ProductModel> findById(Integer idProducto);

}
