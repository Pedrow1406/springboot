package com.pedrowsite.springboot.dtos;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductPatchDto(String name, BigDecimal price) {}
