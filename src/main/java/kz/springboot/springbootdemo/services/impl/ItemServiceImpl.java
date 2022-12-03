package kz.springboot.springbootdemo.services.impl;

import kz.springboot.springbootdemo.entities.*;
import kz.springboot.springbootdemo.entities.Items_Basket;
import kz.springboot.springbootdemo.repositories.*;
import kz.springboot.springbootdemo.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private Items_BasketRepository items_basketRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private InTopPageItemsRepository inTopItemsRepository;

    @Autowired
    private BestSellerItemsRepository bestSellerItemsRepository;

    @Override
    public List<Items> findAllByNameLikeOrderByPriceDesc(String name) {
        return itemRepository.findAllByNameContainingOrderByPriceAsc(name);
    }

    @Override
    public List<Items> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public Items get(Long id) {
        return itemRepository.getOne(id);
    }

    @Override
    public void delete(Items items) {
        itemRepository.delete(items);
    }

    @Override
    public void delete(Long id) {itemRepository.deleteById(id);}

    @Override
    @Transactional
    public Items save(Items items) {

        if(items.isBest()){
            BestSellerItems bestSellerItems = new BestSellerItems();
            bestSellerItems.setItems(items);
            bestSellerItems.setAddedDate(new Date());
            bestSellerItemsRepository.save(bestSellerItems);
        }

        if(items.isInTopPage()){
            InTopPageItems inTopPageItems = new InTopPageItems();
            inTopPageItems.setItems(items);
            inTopPageItems.setAddedDate(new Date());
            inTopItemsRepository.save(inTopPageItems);
        }

        return itemRepository.save(items);
    }

//    @Override
//    public List<Items> findAllSortedByInTop() {
//        return itemRepository.findAll(Sort.by("inTopPage").descending());
//    }

    @Override
    public List<Items> findAllSortedByInTop() {
        return itemRepository.findAll();
    }


    @Override
    public List<Items> findAllByBrandsAndNameOrderByPriceDesc(double price1, double price2, String name,Brands brand_id) {
        return itemRepository.findAllByPriceBetweenAndNameContainingAndBrandsOrderByPriceDesc(price1,price2,name,brand_id);
    }

    @Override
    public List<Items> findAllByBrandsOrderByPriceAsc(Brands brands_id) {
        return itemRepository.findAllByBrandsOrderByPriceAsc(brands_id);
    }

    @Override
    public List<Items> findAllByBrandsOrderByPriceDesc(Brands brands_id) {
        return itemRepository.findAllByBrandsOrderByPriceDesc(brands_id);
    }

    @Override
    public List<Items> findAllByBrandsAndNameOrderByPriceAsc(double price1, double price2, String name,Brands brands) {
        return itemRepository.findAllByPriceBetweenAndNameContainingAndBrandsOrderByPriceAsc(price1,price2,name,brands);
    }

    @Override
    public List<Countries> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Countries saveCountry(Countries countries) {
        return countryRepository.save(countries);
    }

    @Override
    public Countries getCountry(Long id) {
        return countryRepository.getOne(id);
    }

    @Override
    public void deleteCountry(Countries countries) {
        countryRepository.delete(countries);
    }

    @Override
    public List<Brands> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brands addBrand(Brands brands) {
        return brandRepository.save(brands);
    }

    @Override
    public Brands getBrand(Long id) {
        return brandRepository.getOne(id);
    }

    @Override
    public void deleteBrand(Brands brands) {
        brandRepository.delete(brands);
    }

    @Override
    public List<Pictures> getAllPictures() {
        return pictureRepository.findAll();
    }

    @Override
    public Pictures addPicture(Pictures picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public Pictures getPicture(Long id) {
        return pictureRepository.getOne(id);
    }

    @Override
    public void deletePictires(Pictures pictures) {
        pictureRepository.delete(pictures);
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Categories getCategory(Long id) {
        return categoryRepository.getOne(id);
    }

    @Override
    public Categories saveCategory(Categories categories) {
        return categoryRepository.save(categories);
    }

    @Override
    public void deleteCategory(Categories categories) {
        categoryRepository.delete(categories);
    }

    @Override
    public List<Items_Basket> getAllItems_baskets() {
        return  items_basketRepository.findAll();
    }

    @Override
    public Items_Basket saveItems_Basket(Items_Basket items_basket) {
        return items_basketRepository.save(items_basket);
    }

    @Override
    public List<Banners> getAllBanners() {
        return bannerRepository.findAll();
    }

    @Override
    public Banners getBanner(Long id) {
        return bannerRepository.getOne(id);
    }

    @Override
    public Banners saveBanner(Banners banners) {
        return bannerRepository.save(banners);
    }

    @Override
    public void deleteBanner(Banners banners) {
        bannerRepository.delete(banners);
    }

    @Override
    public List<Size> getAllSize() {
        return sizeRepository.findAll();
    }

    @Override
    public Size getSize(Long id) {
        return sizeRepository.getOne(id);
    }

    @Override
    public Size saveSize(Size size) {
        Size sizeForDb = sizeRepository.findBySizeName(size.getSizeName());
        if(sizeForDb == null && !size.getSizeName().equals("")){
            sizeRepository.save(size);
        }
        return sizeRepository.save(size);
    }

    @Override
    public void deleteSize(Size size) {
        sizeRepository.delete(size);
    }

    @Override
    public List<BestSellerItems> getAllBestSellerItems() {
        return bestSellerItemsRepository.findAll();
    }

    @Override
    public List<InTopPageItems> getAllIntTopPageItems() {
        return inTopItemsRepository.findAll();
    }

    @Override
    public List<Items> getAllItemsByCategories(Categories categories) {
        return itemRepository.findAllByCategories(categories);
    }


}
