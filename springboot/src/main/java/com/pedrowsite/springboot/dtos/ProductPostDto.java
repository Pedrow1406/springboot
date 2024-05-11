package com.pedrowsite.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductPostDto(@NotBlank String name, @NotNull BigDecimal price) {}
