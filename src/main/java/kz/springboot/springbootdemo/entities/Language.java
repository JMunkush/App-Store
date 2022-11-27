package kz.springboot.springbootdemo.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String locale;   // ru
    @Column(name = "message_key")
    private String messageKey;  // home.welcome
    @Column(name = "message_content")
    private String messageContent; // Добро Пожаловать

    public Language(String locale, String messageKey, String messageContent) {
        this.locale = locale;
        this.messageKey = messageKey;
        this.messageContent = messageContent;
    }

    public Language() {

    }
}
