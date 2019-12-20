package com.service.AdminServices.Impl;

import com.Exception.CommonException;
import com.enums.ResultEnum;
import com.mapper.ProductMapper;
import com.model.Product;
import com.service.AdminServices.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品管理
 *
 * @author:HGF
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<Product> getProducts() {
        return productMapper.selectAll();
    }

    @Override
    public Product getProduct(int id) {
        Product product=productMapper.selectByPrimaryKey(id);
        if(product==null) throw new CommonException(ResultEnum.PRODUCT_ID_MISS);
        return product;
    }

    @Override
    @Transactional
    public void addProduct(Product product) {
        product.setQuantity(0);
        productMapper.insert(product);
    }

    @Override
    @Transactional
    public void updateProduct(Product product) {
        productMapper.updateByPrimaryKey(product);
    }
}
