package com.conectaco.conectaco.repository;

import com.conectaco.conectaco.entity.TuristaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TuristaRepository extends JpaRepository<TuristaEntity, Integer> {
    void deleteByIdUsuario(Integer idUsuario);
}
