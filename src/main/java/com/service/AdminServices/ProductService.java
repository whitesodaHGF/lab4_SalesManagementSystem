package com.service.AdminServices;

import com.model.Product;

import java.util.List;

/**
 * 商品管理
 *
 * @author:HGF
 */
public interface ProductService {

    List<Product> getProducts();

    Product getProduct(int id);

    void addProduct(Product product);

    void updateProduct(Product product);
}
