package kz.springboot.springbootdemo.repositories;

import kz.springboot.springbootdemo.entities.Brands;
import kz.springboot.springbootdemo.entities.Categories;
import kz.springboot.springbootdemo.entities.Items;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Items,Long> {

    List<Items> findAllByNameContainingOrderByPriceAsc(String name);
    List<Items> findAllByNameContainingOrderByPriceDesc(String name);
    List<Items> findAllByCategories(Categories categories);


    List<Items> findAllByPriceBetweenAndNameContainingAndBrandsOrderByPriceAsc(double price1, double price2, String name,Brands brands_id);
    List<Items> findAllByPriceBetweenAndNameContainingAndBrandsOrderByPriceDesc(double price1, double price2, String name,Brands brands_id);

    List<Items> findAllByBrandsOrderByPriceAsc(Brands brands_id);
    List<Items> findAllByBrandsOrderByPriceDesc(Brands brands_id);

    List<Items> findAll(Sort sort);
}
