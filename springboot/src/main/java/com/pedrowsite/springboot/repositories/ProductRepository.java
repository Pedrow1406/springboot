package com.pedrowsite.springboot.repositories;

import com.pedrowsite.springboot.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {}
