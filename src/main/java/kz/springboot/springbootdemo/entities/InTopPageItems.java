package kz.springboot.springbootdemo.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_in_top_page_items")
@Data
public class InTopPageItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Items> items;
    private Date addedDate;

}
