package org.example.model.repository;

import org.example.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    boolean existsById(Long aLong);

    Order findByUserUserIdAndPaymentStatus(Long userId, boolean paymentStatus);

    void deleteByUserUserIdAndPaymentStatus(Long userId, boolean paymentStatus);

    List<Order> findAllByPaymentStatus(boolean paymentStatus);
}