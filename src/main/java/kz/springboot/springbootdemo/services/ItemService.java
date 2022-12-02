package kz.springboot.springbootdemo.services;

import kz.springboot.springbootdemo.entities.*;
import kz.springboot.springbootdemo.repositories.BannerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;


public interface ItemService {
    List<Items> findAllByNameLikeOrderByPriceDesc(String name);

    List<Items> getAll();

    Items get(Long id);

    void delete(Items items);

    void delete(Long id);

    Items save(Items items);

    List<Items> findAllSortedByInTop();

    List<Items> findAllByBrandsAndNameOrderByPriceAsc(double price1, double price2, String name,Brands brands);
    List<Items> findAllByBrandsAndNameOrderByPriceDesc(double price1, double price2, String name,Brands brands);

    List<Items> findAllByBrandsOrderByPriceAsc(Brands brands_id);
    List<Items> findAllByBrandsOrderByPriceDesc(Brands brands_id);

    List<Countries> getAllCountries();
    Countries saveCountry(Countries countries);
    Countries getCountry(Long id);
    void deleteCountry(Countries countries);

    List<Brands> getAllBrands();
    Brands addBrand(Brands brands);
    Brands getBrand(Long id);
    void deleteBrand(Brands brands);

    List<Pictures> getAllPictures();
    Pictures addPicture(Pictures picture);
    Pictures getPicture(Long id);
    void deletePictires(Pictures pictures);

    List<Categories> getAllCategories();
    Categories getCategory(Long id);
    Categories saveCategory(Categories categories);
    void deleteCategory(Categories categories);

    List<Items_Basket> getAllItems_baskets();
    Items_Basket saveItems_Basket(Items_Basket items_basket);

    List<Banners> getAllBanners();
    Banners getBanner(Long id);
    Banners saveBanner(Banners banners);
    void deleteBanner(Banners banners);

    List<Size> getAllSize();
    Size getSize(Long id);
    Size saveSize(Size size);
    void deleteSize(Size size);

    List<BestSellerItems> getAllBestSellerItems();
    List<InTopPageItems> getAllIntTopPageItems();

}
