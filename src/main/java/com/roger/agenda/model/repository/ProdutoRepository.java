package com.roger.agenda.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roger.agenda.model.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
