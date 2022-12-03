package kz.springboot.springbootdemo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "items_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",length = 255)
    private String name;

    @Column(name = "description",length = 255)
    private String description;

    @Column(name = "compound")
    private String compound;

    @Column(name = "price")
    private double price;

    @Column(name = "stars")
    private int stars;

//    @Column(name = "small_picurl", length = 1024)
//    private String smallPicURL;
//
//    @Column(name = "large_picurl", length = 1024)
//    private String largePicURL;

//    @Column(name = "pic_path")
//    private String picPath;

    @Column(name = "added_Date")
    private Date addedDate;

    @Column(name = "in_Top_Page")
    private boolean inTopPage;

    @Column(name = "bestseller")
    private boolean best;

    @Column(name="amount_shop")
    private int amount_sh;

    @Column(name = "amount_stock")
    private int amount_st;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brands brands;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Categories> categories;

    @OneToMany
    private List<Size> sizes;

    @Enumerated(EnumType.STRING)
    private LocalType locale;


}
