package br.edu.atitus.apisample.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.apisample.entities.RegisterEntity;

public interface RegisterRepository extends JpaRepository<RegisterEntity, UUID>{

}
