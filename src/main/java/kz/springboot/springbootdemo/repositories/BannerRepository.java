package kz.springboot.springbootdemo.repositories;

import kz.springboot.springbootdemo.entities.Banners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BannerRepository extends JpaRepository<Banners, Long> {

}
