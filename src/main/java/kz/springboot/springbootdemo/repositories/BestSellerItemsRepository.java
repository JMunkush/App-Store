package kz.springboot.springbootdemo.repositories;

import kz.springboot.springbootdemo.entities.BestSellerItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BestSellerItemsRepository extends JpaRepository<BestSellerItems, Long> {

}
