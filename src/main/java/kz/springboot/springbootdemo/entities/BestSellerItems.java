package kz.springboot.springbootdemo.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_bestselleritems")
@Data
public class BestSellerItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Items> items;
    private Date addedDate;
}
