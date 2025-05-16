package com.ecomarketspa.EcoMarketSPA.Controller;

import com.ecomarketspa.EcoMarketSPA.Model.ProductModel;
import com.ecomarketspa.EcoMarketSPA.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Página principal de productos (lista de productos)
    @GetMapping("/productos")
    public String mostrarProductos(Model model) {
        List<ProductModel> productos = productService.findAll();
        model.addAttribute("products", productos);
        return "list_product";
    }

    // Mostrar formulario para agregar producto
    @GetMapping("/productos/agregar")
    public String showAddProductForm(Model model) {
        model.addAttribute("productRequest", new ProductModel());
        return "add_product";
    }

    // Procesar registro de producto
    @PostMapping("/productos/agregar")
    public String saveProduct(@ModelAttribute("productRequest") ProductModel productModel) {
        System.out.println("Producto recibido: " + productModel);
        ProductModel saved = productService.saveProducto(productModel);
        return saved == null ? "error_page" : "redirect:/productos";
    }

    // Mostrar formulario de edición
    @GetMapping("/productos/editar/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        ProductModel product = productService.getIdProducto(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "edit_product"; // Asegúrate de tener esta vista
        }
        return "error_page";
    }

    // Procesar edición
    @PostMapping("/productos/editar")
    public String updateProduct(@ModelAttribute ProductModel product) {
        productService.updateProducto(product);
        return "redirect:/productos";
    }

    // Eliminar producto
    @GetMapping("/productos/eliminar/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProducto(id);
        return "redirect:/productos";
    }
}
