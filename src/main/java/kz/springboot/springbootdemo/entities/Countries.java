package kz.springboot.springbootdemo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "t_countries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Countries {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     private Long id;
     @Column(name = "name",length = 255)
     private String name;
     @Column(name = "code",length = 255)
     private String code;
}
