package kz.springboot.springbootdemo.repositories;

import kz.springboot.springbootdemo.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SizeRepository extends JpaRepository<Size, Long> {
    Size findBySizeName(String sizeName);

}
