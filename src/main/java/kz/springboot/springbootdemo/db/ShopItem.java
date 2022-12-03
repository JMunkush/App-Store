package kz.springboot.springbootdemo.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopItem {
    private Long id;
    private String name;
    private String description;
    private String compound;
    private int price;
    private int amount;
    private boolean inTopPage;
    private boolean best;
    private String pictureUrl;
}
