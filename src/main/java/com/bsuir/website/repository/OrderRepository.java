package com.bsuir.website.repository;

import com.bsuir.website.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<UserOrder, Integer> {
}
