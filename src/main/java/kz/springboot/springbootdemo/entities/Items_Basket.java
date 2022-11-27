package kz.springboot.springbootdemo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "items_basket_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items_Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 255)
    private String name;
    @Column(name = "user_name", length = 255)
    private String user_name;
    @Column(name = "amount")
    private int amount;
    private String description;
    @Column(name = "price")
    private double price;
    @Column(name = "added_Date")
    private Date addedDate;
    @Column(name = "items_name")
    private String items_name;

    public Items_Basket(Long id, String name, int amount, double price) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
    }
}
