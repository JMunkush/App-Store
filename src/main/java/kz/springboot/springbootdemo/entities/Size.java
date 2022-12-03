package kz.springboot.springbootdemo.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_size")
@Data
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "size_name")
    private String sizeName;
    @ManyToOne
    private Items items;

    public Size(String sizeName) {
        this.sizeName = sizeName;
    }

    public Size() {}
}
