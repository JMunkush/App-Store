package kz.springboot.springbootdemo.repositories;

import kz.springboot.springbootdemo.entities.Items_Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface Items_BasketRepository extends JpaRepository<Items_Basket,Long> {
}
