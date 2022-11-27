package kz.springboot.springbootdemo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_pictures")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pictures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url", length = 255)
    private String url;

    @Column(name = "added_Date")
    private Date added_Date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Items item;


}
