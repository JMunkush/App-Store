package kz.springboot.springbootdemo.services;

import kz.springboot.springbootdemo.entities.Language;

import java.util.List;

public interface LanguageService {

    Language getByKeyAndLocale(String key, String locale);

    List<Language> getAllLanguagesByLocale(String locale);

    List<Language> getAllLanguagesByKey(String key);

    void saveLanguage(Language language);

    void deleteLanguageById(int id);

    void deleteLanguage(Language language);

}