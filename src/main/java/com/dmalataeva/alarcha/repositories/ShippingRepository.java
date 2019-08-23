package com.dmalataeva.alarcha.repositories;

import com.dmalataeva.alarcha.entities.ShippingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<ShippingEntity, Integer> {
}
