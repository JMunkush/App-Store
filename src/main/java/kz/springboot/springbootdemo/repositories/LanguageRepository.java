package kz.springboot.springbootdemo.repositories;

import kz.springboot.springbootdemo.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface LanguageRepository extends JpaRepository<Language, Integer> {
                                      //home.welcome,  //ru  ->      Добро Пожаловать
    Language findByMessageKeyAndLocale(String key, String locale);
    List<Language> findAllByLocale(String locale);
    List<Language> findAllByMessageKey(String key);


}
