package kz.springboot.springbootdemo;

import kz.springboot.springbootdemo.entities.Language;
import kz.springboot.springbootdemo.entities.Size;
import kz.springboot.springbootdemo.repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

@SpringBootApplication
public class SpringbootdemoApplication {
	@Autowired
	SizeRepository sizeRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringbootdemoApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
			Size size = new Size("S");
			Size size1 = new Size("M");
			Size size2 = new Size("L");
			Size size3 = new Size("XL");
			Size size4 = new Size("XXL");

			Size size5 = new Size("XXXS");
			Size size6 = new Size("XXS");
			Size size7 = new Size("XS");
			Size size8 = new Size("S");

			sizeRepository.save(size);
			sizeRepository.save(size1);
			sizeRepository.save(size2);
			sizeRepository.save(size3);
			sizeRepository.save(size4);
			sizeRepository.save(size5);
			sizeRepository.save(size6);
			sizeRepository.save(size7);
			sizeRepository.save(size8);



			// KZ

			Language languageKZ1 = new Language("kz", "label.home", "Басты бет");
			Language languageKZ2 = new Language("kz", "navbar.bestseller", "Бестселлерлер");
			Language languageKZ3 = new Language("kz", "navbar.catalogue", "Каталог");
			Language languageKZ4 = new Language("kz", "navbar.howorder", "Қалай тапсырыс беруге болады");
			Language languageKZ5 = new Language("kz", "navbar.aboutauthor", "Автор туралы");
			Language languageKZ6 = new Language("kz", "navbar.collaborations", "Ынтымақтастық");
			Language languageKZ7 = new Language("kz", "navbar.blog", "Блог");
			Language languageKZ8 = new Language("kz", "navbar.contacts", "Байланыс нөмірі");
			Language languageKZ9 = new Language("kz", "navbar.drop.shoppers", "Шопперлер");
			Language languageKZ10 = new Language("kz", "navbar.drop.tshirts", "Футболкалар");
			Language languageKZ11 = new Language("kz", "navbar.drop.hoodie", "Худи");
			Language languageKZ12 = new Language("kz", "navbar.drop.cases", "Тысқаптар");
			Language languageKZ13 = new Language("kz", "navbar.drop.posters", "Постерлер");
			Language languageKZ14 = new Language("kz", "navbar.drop.postcards", "Ашықхаттар");
			Language languageKZ15 = new Language("kz", "navbar.drop.candles", "Майшамдар");
			Language languageKZ16 = new Language("kz", "label.recomm", "Көруді ұсынамыз");
			Language languageKZ17 = new Language("kz", "label.newitems", "Жаңа тауарлар");
			Language languageKZ18 = new Language("kz", "text.step1", "1 кезең");
			Language languageKZ19 = new Language("kz", "text.step2", "2 кезең");
			Language languageKZ20 = new Language("kz", "text.step3", "3 кезең");
			Language languageKZ21 = new Language("kz", "text.step4", "4 кезең");
			Language languageKZ22 = new Language("kz", "text.step5", "5 кезең");
			Language languageKZ23 = new Language("kz", "text.step1.p1", "Каталогқа өтіп, сізді қызықтыратын өнімді таңдаңыз");
			Language languageKZ24 = new Language("kz", "text.step1.p2", "сізді қызықтыратын өнімді таңдаңыз");
			Language languageKZ25 = new Language("kz", "text.step1.span", "каталогқа");


			// RU

			Language languageRU1 = new Language("ru", "label.home", "Главная");
			Language languageRU2 = new Language("ru", "navbar.bestseller", "Бестселлеры");
			Language languageRU3 = new Language("ru", "navbar.catalogue", "Каталог");
			Language languageRU4 = new Language("ru", "navbar.howorder", "Как заказать");
			Language languageRU5 = new Language("ru", "navbar.aboutauthor", "Об авторе");
			Language languageRU6 = new Language("ru", "navbar.collaborations", "Коллаборации");
			Language languageRU7 = new Language("ru", "navbar.blog", "Блог");
			Language languageRU8 = new Language("ru", "navbar.contacts", "Контакты");
			Language languageRU9 = new Language("ru", "navbar.drop.shoppers", "Шопперы");
			Language languageRU10 = new Language("ru", "navbar.drop.tshirts", "Футболки");
			Language languageRU11 = new Language("ru", "navbar.drop.hoodie", "Худи");
			Language languageRU12 = new Language("ru", "navbar.drop.cases", "Чехлы");
			Language languageRU13 = new Language("ru", "navbar.drop.posters", "Постеры");
			Language languageRU14 = new Language("ru", "navbar.drop.postcards", "Открытки");
			Language languageRU15 = new Language("ru", "navbar.drop.candles", "Свечи");
			Language languageRU16 = new Language("ru", "label.recomm", "Рекомендуем посмотреть");
			Language languageRU17 = new Language("ru", "label.newitems", "Новые товары");
			Language languageRU18 = new Language("ru", "text.step1", "Шаг 1");
			Language languageRU19 = new Language("ru", "text.step2", "Шаг 2");
			Language languageRU20 = new Language("ru", "text.step3", "Шаг 3");
			Language languageRU21 = new Language("ru", "text.step4", "Шаг 4");
			Language languageRU22 = new Language("ru", "text.step5", "Шаг 5");
			Language languageRU23 = new Language("ru", "text.step1.p1", "Перейти в каталог и выбрать интересующий вас товар. Выбрать размер одежды или постера/модель телефона/вид коллекции");
			Language languageRU24 = new Language("ru", "text.step1.p2", "и выбрать интересующий вас товар. Выбрать размер одежды или постера/модель телефона/вид коллекции");
			Language languageRU25 = new Language("ru", "text.step1.span", "каталог");


			// EN
			Language languageEN1 = new Language("en", "label.home", "Home");
			Language languageEN2 = new Language("en", "navbar.bestseller", "Best-sellers");
			Language languageEN3 = new Language("en", "navbar.catalogue", "CataLogue");
			Language languageEN4 = new Language("en", "navbar.howorder", "How to order");
			Language languageEN5 = new Language("en", "navbar.aboutauthor", "About author");
			Language languageEN6 = new Language("en", "navbar.collaborations", "Collaborations");
			Language languageEN7 = new Language("en", "navbar.blog", "Blog");
			Language languageEN8 = new Language("en", "navbar.contacts", "Contacts");
			Language languageEN9 = new Language("en", "navbar.drop.shoppers", "Shopper bags");
			Language languageEN10 = new Language("en", "navbar.drop.tshirts", "T-shirts");
			Language languageEN11 = new Language("en", "navbar.drop.hoodie", "Hoodies");
			Language languageEN12 = new Language("en", "navbar.drop.cases", "Cases");
			Language languageEN13 = new Language("en", "navbar.drop.posters", "Posters");
			Language languageEN14 = new Language("en", "navbar.drop.postcards", "Postcards");
			Language languageEN15 = new Language("en", "navbar.drop.candles", "Candles");
			Language languageEN16 = new Language("en", "label.recomm", "Recommended to see");
			Language languageEN17 = new Language("en", "label.newitems", "New goods");
			Language languageEN18 = new Language("en", "text.step1", "Step 1");
			Language languageEN19 = new Language("en", "text.step2", "Step 2");
			Language languageEN20 = new Language("en", "text.step3", "Step 3");
			Language languageEN21 = new Language("en", "text.step4", "Step 4");
			Language languageEN22 = new Language("en", "text.step5", "Step 5");
			Language languageEN23 = new Language("en", "text.step1.p2", "and select the product you are interested in");
			Language languageEN24 = new Language("en", "text.step1.p1", "Go to the catalog and select the product you are interested in");
			Language languageEN25 = new Language("en", "text.step1.span", "catalog");

		};
	}



}
