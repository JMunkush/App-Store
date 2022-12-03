package kz.springboot.springbootdemo.repositories;

import kz.springboot.springbootdemo.entities.InTopPageItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InTopPageItemsRepository extends JpaRepository<InTopPageItems, Long> {
}
