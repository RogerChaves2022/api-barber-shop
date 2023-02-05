package com.roger.agenda.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roger.agenda.model.entity.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

}
