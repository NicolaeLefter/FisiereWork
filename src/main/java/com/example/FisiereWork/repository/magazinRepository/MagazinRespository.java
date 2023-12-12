package com.example.FisiereWork.repository.magazinRepository;

import com.example.FisiereWork.entity.magazinEntity.Magazin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazinRespository extends JpaRepository<Magazin, Integer> {
}
