package kz.springboot.springbootdemo.db;

import java.util.ArrayList;

public class DBManager {
    private static ArrayList<ShopItem> shopItems = new ArrayList<>();

    static {
        shopItems.add(new ShopItem(1L,"Test","Test","test",10,2,true,false,"https://www.ixbt.com/img/n1/news/2019/10/2/iPhone-12-white-e1574381879755_large.jpg"));
//        shopItems.add(new ShopItem(2L,"Samsung","Telephone",12000,10,5,"https://i.gadgets360cdn.com/large/samsung_galaxy_a51_image_1580126685655.jpg"));
//        shopItems.add(new ShopItem(3L,"Redmi","Telephone",12000,10,3,"https://www.ixbt.com/img/n1/news/2019/9/2/a50-1_c7n4_large.jpg"));
//        shopItems.add(new ShopItem(4L,"Redmi","Telephone",12000,10,5,"https://www.ixbt.com/img/n1/news/2019/9/2/a50-1_c7n4_large.jpg"));

    }
    private static Long id = 5L;
    public static ArrayList<ShopItem> getShopItems(){
        return shopItems;
    }
    public static void addTask(ShopItem item){
        item.setId(id);
        shopItems.add(item);
        id++;
    }
}
