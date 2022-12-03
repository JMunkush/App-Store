package kz.springboot.springbootdemo.repositories;

import kz.springboot.springbootdemo.entities.Pictures;
import kz.springboot.springbootdemo.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PictureRepository extends JpaRepository<Pictures,Long> {
}
