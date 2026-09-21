package com.softdesign.livetest.domain.produto;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public record Produto(@Id String id, String nome, String descricao, BigDecimal valor) {
}
