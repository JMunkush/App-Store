package kz.springboot.springbootdemo.services.impl;

import javassist.NotFoundException;
import kz.springboot.springbootdemo.entities.Language;
import kz.springboot.springbootdemo.repositories.LanguageRepository;
import kz.springboot.springbootdemo.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Language getByKeyAndLocale(String key, String locale) {
        Language language = languageRepository.findByMessageKeyAndLocale(key, locale);

        if(language == null){
            try {
                throw new NotFoundException(String.format("language with key and locale '%s' '%s'", key, locale));
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

        return language;
    }

    // получаем с базы все переводы по языку  [ getAllLanguagesByLocale("kz"); ]

    @Override
    public List<Language> getAllLanguagesByLocale(String locale) {
        return languageRepository.findAllByLocale(locale);
    }

    // получаем с базы все переводы по ключу  [ getAllLanguagesByLocale("home.welcome"); ]
    //                                        [ getAllLanguagesByLocale("info.items"); и тд]

    @Override
    public List<Language> getAllLanguagesByKey(String key) {
        return languageRepository.findAllByMessageKey(key);
    }


    @Override
    public void saveLanguage(Language language) {
        languageRepository.save(language);
    }

    @Override
    public void deleteLanguageById(int id) {
        languageRepository.deleteById(id);
    }

    @Override
    public void deleteLanguage(Language language) {
        languageRepository.delete(language);
    }


}
