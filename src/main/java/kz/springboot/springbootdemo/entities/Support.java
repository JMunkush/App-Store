package kz.springboot.springbootdemo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "support_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",length = 255)
    private String name;

    @Column(name = "email",length = 255)
    private String email;

    @Column(name = "phone",length = 255)
    private String phone;

    @Column(name = "title",length = 255)
    private String title;

    @Column(name = "message",length = 255)
    private String message;

}
