package kz.springboot.springbootdemo.Controllers;

import kz.springboot.springbootdemo.entities.*;

import kz.springboot.springbootdemo.services.ItemService;
import kz.springboot.springbootdemo.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import org.w3c.dom.html.HTMLTableRowElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.ServerEndpoint;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Security;
import java.sql.Date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${file.avatar.viewPath}")
    private String viewPath;

    @Value("${file.avatar.uploadPath}")
    private String uploadPath;

    @Value("${file.avatar.defaultPicture}")
    private String defaultPicture;

    @Value("static/item_pictures/")
    private String viewPath_pictures;

    @Value("target/classes/static/item_pictures/")
    private String uploadPath_pictures;

    @GetMapping(value = "/")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public String index(Model model, HttpServletRequest request){
        model.addAttribute("currentUser", getUserData());
        List<Items> items = itemService.getAll();
        List<Countries> countries = itemService.getAllCountries();
        HttpSession session = request.getSession();
        List<Items_Basket> items_basket = (List<Items_Basket>) session.getAttribute("basket");
        int amount_sum = 0;
        if(items_basket != null) {
            for (Items_Basket i : items_basket) {
                amount_sum = amount_sum + i.getAmount();
            }
        }
        model.addAttribute("amount",amount_sum);
        model.addAttribute("currentUser",getUserData());
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("shopItems",items);
        return "index";
    }

    @GetMapping(value = "/bestsellers")
    public String bestsellers(Model model, HttpServletRequest request){
        model.addAttribute("currentUser",getUserData());
        List<Items> items = itemService.getAll();
        List<Countries> countries = itemService.getAllCountries();
        HttpSession session = request.getSession();
        List<Items_Basket> items_basket = (List<Items_Basket>) session.getAttribute("basket");
        int amount_sum = 0;
        if(items_basket != null) {
            for (Items_Basket i : items_basket) {
                amount_sum = amount_sum + i.getAmount();
            }
        }
        model.addAttribute("amount",amount_sum);
        model.addAttribute("currentUser",getUserData());
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("shopItems",items);

        return "bestsellers";
    }

    @GetMapping(value = "/deleteCateg")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteCategories(@RequestParam(name = "id")Long id){
        Categories categories = itemService.getCategory(id);
        itemService.deleteCategory(categories);
        return "redirect:/category_page";
    }

    @PostMapping(value = "/edit_Category")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editCategory(@RequestParam(name = "id")Long id,
                               @RequestParam(name = "name")String name,
                               @RequestParam(name = "logoURL")String url){
        Categories category = itemService.getCategory(id);
        category.setName(name);
        category.setLogoURL(url);
        itemService.saveCategory(category);
        return "redirect:/category_page";
    }

    @PostMapping(value = "/addCategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addCategory(@RequestParam(name = "category_name")String name,
                               @RequestParam(name = "url")String url) {
        itemService.saveCategory(new Categories(null,name,url));
        return "redirect:/category_page";
    }

    @GetMapping(value = "/category_page")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String category(Model model){
        List<Categories> categories = itemService.getAllCategories();
        model.addAttribute("currentUser",getUserData());
        model.addAttribute("categories",categories);
        return "category_page";
    }

    @PostMapping(value = "/deleteCategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String deleteCategory(@RequestParam(name = "item_id")Long item_id,
                                  @RequestParam(name = "category_id")Long category_id){
        Categories category = itemService.getCategory(category_id);
        if(category != null) {
            Items item = itemService.get(item_id);
            if (item != null) {
                List<Categories> categories = item.getCategories();
                categories.remove(category);
                itemService.save(item);
                return "redirect:/details_items?id="+item_id;
            }
        }
        return "redirect:/";
    }

    @PostMapping(value = "/assignedCategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String assigned(@RequestParam(name = "item_id")Long item_id,
                            @RequestParam(name = "category_id")Long category_id){
        Categories category = itemService.getCategory(category_id);
        if(category != null){
            Items item = itemService.get(item_id);
            if(item != null) {
                List<Categories> categories = item.getCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }
                categories.add(category);
                itemService.save(item);
                System.out.println(category.getName() + " " + category.getLogoURL());
                return "redirect:/details_items?id="+ item_id;
            }
        }
        return "redirect:/";
    }

//    @PostMapping(value = "/addSize")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    public String addSize(@RequestParam(name = "size_name")String name) {
//        itemService.saveSize(new Size(null,name));
//        return "redirect:/size_page";
//    }
//
//    @GetMapping(value = "/size_page")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    public String size(Model model){
//        List<Size> sizes = itemService.getAllSize();
//        model.addAttribute("currentUser",getUserData());
//        model.addAttribute("sizes",sizes);
//        return "size_page";
//    }
//
//    @PostMapping(value = "/deleteSize")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
//    public String deleteSize(@RequestParam(name = "item_id")Long item_id,
//                                 @RequestParam(name = "size_id")Long size_id){
//        Size size = itemService.getSize(size_id);
//        if(size != null) {
//            Items item = itemService.get(item_id);
//            if (item != null) {
//                List<Size> sizes = item.getSizes();
//                sizes.remove(size);
//                itemService.save(item);
//                return "redirect:/details_items?id="+item_id;
//            }
//        }
//        return "redirect:/";
//    }
//
//    @PostMapping(value = "/edit_Size")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    public String editSize(@RequestParam(name = "id")Long id,
//                               @RequestParam(name = "name")String name){
//        Size size = itemService.getSize(id);
//        size.setName(name);
//        itemService.saveSize(size);
//        return "redirect:/size_page";
//    }

    @GetMapping(value = "/deleteItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String deleteItem(@RequestParam(name = "id")Long id){
        Items items = itemService.get(id);
        itemService.delete(items);
        return "redirect:/item_page";
    }

    @GetMapping(value = "/deleteBrand")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteBrand(@RequestParam(name = "id")Long id){
        Brands brands = itemService.getBrand(id);
        itemService.deleteBrand(brands);
        return "redirect:/brand_page";
    }

    @GetMapping(value = "/deleteCountry")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteCountry(@RequestParam(name = "id")Long id){
        Countries country = itemService.getCountry(id);
        itemService.deleteCountry(country);
        return "redirect:/adminPage";
    }

    @GetMapping(value = "/details_items")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String details_items(Model model,@RequestParam(name = "id")Long id){
        model.addAttribute("currentUser",getUserData());
        Items item = itemService.get(id);
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Pictures> pictures = itemService.getAllPictures();
        categories = categories.stream()
                .filter(o -> !item.getCategories().contains(o))
                .collect(Collectors.toList());
        model.addAttribute("pictures",pictures);
        model.addAttribute("categories",categories);
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("item",item);
        return "details_item";
    }

    @GetMapping(value = "/item_page")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String item_page(Model model){
        //model.addAttribute("currentUser",getUserData());
        List<Items> items = itemService.findAllSortedByInTop();
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        if(items != null) {
            model.addAttribute("shopItemss", items);
        }
        return "item_page";
    }

    @PostMapping(value = "/edit_brand")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String edit_brand(@RequestParam(name = "id")Long id,
                              @RequestParam(name = "brand_name")String brand_name,
                              @RequestParam(name = "country_id")Long country_id){
        Brands brands = itemService.getBrand(id);
        Countries counrty = itemService.getCountry(country_id);
        brands.setName(brand_name);
        brands.setCountries(counrty);
        itemService.addBrand(brands);
        return "redirect:/brand_page";
    }

    @GetMapping(value = "/details_brand")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String details_brand(Model model,@RequestParam(name = "id")Long id){
        model.addAttribute("currentUser",getUserData());
        List<Countries> countries = itemService.getAllCountries();
        Brands brand = itemService.getBrand(id);
        model.addAttribute("brand",brand);
        model.addAttribute("countries",countries);
        return "details_brand";
    }

    @GetMapping(value = "/brand_page")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String brandPage(Model model){
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("currentUser",getUserData());
        model.addAttribute("countries",countries);
        model.addAttribute("brands",brands);
        return "brand_page";
    }

    @PostMapping(value = "/edit_country")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String edit_country(@RequestParam(name = "id")Long id,
                                @RequestParam(name = "country_name")String country_name,
                                @RequestParam(name = "code")String code){
        Countries country = itemService.getCountry(id);
        country.setName(country_name);
        country.setCode(code);
        itemService.saveCountry(country);
        return "redirect:/adminPage";
    }

    @GetMapping(value = "/details_country")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String details_country(Model model,@RequestParam(name = "id",defaultValue = "1")Long country_id){
        Countries country = itemService.getCountry(country_id);
        model.addAttribute("currentUser",getUserData());
        model.addAttribute("country",country);
        return "details_country";
    }

    @GetMapping(value = "/adminPage")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String admin(Model model){
        model.addAttribute("currentUser",getUserData());
        List<Countries> countries = itemService.getAllCountries();
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        return "adminPage";
    }

    @GetMapping(value = "detailsBrand")
    public String detailsBrand(Model model,@RequestParam(name = "id")Long brand_id){
        Brands brand = itemService.getBrand(brand_id);
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("currentUser",getUserData());
        if(brand != null){
            List<Items> items = itemService.findAllByBrandsOrderByPriceAsc(brand);
            model.addAttribute("items",items);
        }
        model.addAttribute("brands",brands);
        return "search";
    }

    @PostMapping(value = "/addBrand")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addBrand(@RequestParam(name = "brand_name")String brand_name,
                            @RequestParam(name = "country_id")Long country_id){
        Countries country = itemService.getCountry(country_id);
        if(country != null) {
            itemService.addBrand(new Brands(null, brand_name,country));
        }
        return "redirect:/brand_page";
    }

    @PostMapping(value = "/addCountry")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addCountry(Model model,
                              @RequestParam(name = "country_name",defaultValue = "no country")String country_name,
                              @RequestParam(name = "code",defaultValue = "no code")String code){
        itemService.saveCountry(new Countries(null,country_name,code));
        model.addAttribute("currentUser",getUserData());
        return "redirect:/adminPage";
    }

    @GetMapping(value = "/sorted")
    public String sorted(Model model,
                          @RequestParam(name = "search_item",defaultValue = "")String search_item,
                          @RequestParam(name = "price_to",defaultValue = "0")double price_to,
                          @RequestParam(name = "price_from",defaultValue = "0")double price_from,
                          @RequestParam(name = "ascending",defaultValue = "no")String sort,
                          @RequestParam(name = "brand_id",defaultValue = "no ")Long brand_id){
        model.addAttribute("currentUser",getUserData());
        List<Items> items = null;
        Brands brand = itemService.getBrand(brand_id);
        if(brand != null){
            if(search_item.equals("")) {
                if (sort.equals("ascending")) {
                    items = itemService.findAllByBrandsOrderByPriceAsc(brand);
                } else {
                    items = itemService.findAllByBrandsOrderByPriceDesc(brand);
                }
            }else{
                if (sort.equals("ascending")) {
                    items = itemService.findAllByBrandsAndNameOrderByPriceAsc(price_from, price_to, search_item, brand);
                } else {
                    items = itemService.findAllByBrandsAndNameOrderByPriceDesc(price_from, price_to, search_item, brand);
                }
            }
        }
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("items",items);
        return "search";
    }

    @PostMapping(value = "/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String save(Model model, @RequestParam(name = "id")Long id,
                        @RequestParam(name = "brand_id")Long brand_id,
                        @RequestParam(name = "name")String name,
                        @RequestParam(name = "description")String description,
                        @RequestParam(name = "price")double price,
//                        @RequestParam(name = "amount-shop")int amount_sh,
//                        @RequestParam(name = "amount-stock")int amount_st,
                        @RequestParam(name = "compound")String compound,
//                        @RequestParam(name = "top")boolean top,
//                        @RequestParam(name = "best")boolean best,
                        @RequestParam(name = "small_url")String small_url){
        model.addAttribute("currentUser",getUserData());
        Items items = itemService.get(id);
        Brands brands = itemService.getBrand(brand_id);
        items.setName(name);
        items.setDescription(description);
        items.setBrands(brands);
        items.setPrice(price);
        items.setCompound(compound);
//        items.setInTopPage(top);
//        items.setBest(best);
        items.setSmallPicURL(small_url);
//        items.setAmount_sh(amount_sh);
//        items.setAmount_st(amount_st);
        itemService.save(items);
        return "redirect:/item_page";
    }

    @GetMapping(value = "/search")
    public String search(Model model,
                          @RequestParam(name = "search_name",defaultValue = " ")String search_name){
        model.addAttribute("currentUser",getUserData());
        List<Brands> brands = itemService.getAllBrands();
        List<Items> items = itemService.findAllByNameLikeOrderByPriceDesc(search_name);
        model.addAttribute("brands",brands);
        model.addAttribute("items",items);
        model.addAttribute("search_name",search_name);
        return "search";
    }

    @GetMapping(value = "/details")
    public String details(Model model,HttpServletRequest request,@RequestParam(name = "id")Long id){
        model.addAttribute("currentUser",getUserData());
        Items item = itemService.get(id);
        List<Brands> brands = itemService.getAllBrands();
        List<Categories> categories = itemService.getAllCategories();
        List<Pictures> pictures = itemService.getAllPictures();
        pictures = pictures.stream()
                .filter(o -> o.getItem().getId()==item.getId())
                .collect(Collectors.toList());
        HttpSession session = request.getSession();
        List<Items_Basket> items_basket = (List<Items_Basket>) session.getAttribute("basket");
        int amount_sum = 0;
        if(items_basket != null) {
            for (Items_Basket i : items_basket) {
                amount_sum = amount_sum + i.getAmount();
            }
        }
        model.addAttribute("amount",amount_sum);
        model.addAttribute("pictures",pictures);
        model.addAttribute("currentUser",getUserData());
        model.addAttribute("categories",categories);
        model.addAttribute("brands",brands);
        model.addAttribute("item",item);
        return "details";
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String add(Model model, @RequestParam(name = "name")String name,
                       @RequestParam(name = "description")String description,
                       @RequestParam(name = "price")double price,
//                       @RequestParam(name = "top")boolean top,
                       @RequestParam(name = "small_url")String small_url,
//                       @RequestParam(name = "amount-shop")int amount_sh,
//                       @RequestParam(name = "amount-stock")int amount_st,
                       @RequestParam(name = "compound")String compound,
//                       @RequestParam(name = "best")boolean best,
                       @RequestParam(name = "brand_id")Long brand_id) {
        model.addAttribute("currentUser",getUserData());
        Brands brand = itemService.getBrand(brand_id);
        if(brand != null) {
            Items items = new Items();
            items.setName(name);
            items.setDescription(description);
            items.setCompound(compound);
            items.setAddedDate(Date.valueOf(LocalDate.now()));
            items.setBrands(brand);
            items.setPrice(price);
//            items.setAmount_sh(amount_sh);
//            items.setAmount_st(amount_st);
//            items.setInTopPage(top);
//            items.setBest(best);
            items.setSmallPicURL(small_url);
           itemService.save(items);
        }
        return "redirect:/item_page";
    }


    @PostMapping(value = "/editRoles")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editRoles(@RequestParam(name = "id")Long id,
                            @RequestParam(name = "name")String name,
                            @RequestParam(name = "description")String description){
        Roles role = userService.getRole(id);
        if(role != null){
            role.setName(name);
            role.setDescription(description);
            userService.saveRole(role);
        }
        return "redirect:/rolesPage";
    }

    @GetMapping(value = "/rolesPage")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String rolesPage(Model model){
        model.addAttribute("currentUser",getUserData());
        List<Roles> roles = userService.findAllRoles();
        model.addAttribute("roles",roles);
        return "rolesPage";
    }


    @PostMapping(value = "/addUser")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addUser(@RequestParam(name = "full_name")String name,
                          @RequestParam(name = "email")String email,
                          @RequestParam(name = "password")String password){
        Users users = new Users();
        users.setFull_name(name);
        users.setEmail(email);
        List<Roles> roles = new ArrayList<>();
        Roles role = userService.getRole(1L);
        roles.add(role);
        users.setRoles(roles);
        users.setPassword(bCryptPasswordEncoder.encode(password));
        userService.save(users);

        return "redirect:/usersPage";
    }

    @PostMapping(value = "/addRole")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addRole(@RequestParam(name = "role_name")String name,
                          @RequestParam(name = "description")String description){

        Roles role = new Roles();
        role.setName(name);
        role.setDescription(description);
        userService.saveRole(role);

        return "redirect:/rolesPage";
    }
    @GetMapping(value = "/deleteRoles")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteRoles(@RequestParam(name = "id")Long role_id){
        Roles role = userService.getRole(role_id);
        userService.deleteRole(role);
        return "redirect:/rolesPage";
    }

    @GetMapping(value = "/deleteRole")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteRole(@RequestParam(name = "user_id")Long user_id,
                             @RequestParam(name = "role_id")Long role_id){
        Roles role = userService.getRole(role_id);
        if(role != null) {
            Users user = userService.getUser(user_id);
            if (user != null) {
                List<Roles> roles = user.getRoles();
                roles.remove(role);
                userService.save(user);
                return "redirect:/userDetails?id="+ user_id;
            }
        }

        return "redirect:/usersPage";
    }

    @PostMapping(value = "/assignedRoles")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String assignedRoles(Model model,@RequestParam(name = "user_id")Long user_id,
                                @RequestParam(name = "role_id")Long role_id){
        Roles role = userService.getRole(role_id);
        if(role != null){
            Users user = userService.getUser(user_id);
            if(user != null) {
                List<Roles> roles = user.getRoles();
                if (roles == null) {
                    roles = new ArrayList<>();
                }
                roles.add(role);
                userService.save(user);
                return "redirect:/userDetails?id="+ user_id;
            }
        }
        return "redirect:/usersPage";

    }


    @GetMapping(value = "/deleteUser")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteUser(@RequestParam(name = "id")Long id){
        Users user = userService.getUser(id);
        if(user != null) {
            userService.delete(user);
        }
        return "redirect:/usersPage";
    }

    @PostMapping(value = "/editUser")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editUser(Model model,@RequestParam(name = "id")Long id,
                           @RequestParam(name = "full_name")String full_name,
                           @RequestParam(name = "email")String email,
                           @RequestParam(name = "password") String password){
        Users user = userService.getUser(id);
        if(user != null){
            user.setFull_name(full_name);
            user.setEmail(email);
            user.setPassword(password);
            userService.save(user);
        }

        return "redirect:/userDetails?id="+ id;
    }

    @GetMapping(value = "/userDetails")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String userDetails(Model model,@RequestParam(name = "id")Long id){
        Users user = userService.getUser(id);
        model.addAttribute("user",user);
        List<Roles> roles = userService.findAllRoles();
        model.addAttribute("roles",roles);
        return "userDetails";
    }
    @GetMapping(value = "/usersPage")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String usersPage(Model model){
        model.addAttribute("currentUser",getUserData());
        List<Users> users = userService.getAllUsers();
        List<Roles> roles = userService.findAllRoles();
        model.addAttribute("roles",roles);
        model.addAttribute("users",users);
        return "usersPage";
    }

    @GetMapping(value = "/403")
    public String accesDenied(Model model){
        model.addAttribute("currentUser",getUserData());
        return "403";
    }

    @GetMapping(value = "/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){

        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("currentUser",getUserData());
        model.addAttribute("brands",brands);

        return "profile";
    }

    @PostMapping(value = "/registered")
    public String registered(Model model,@RequestParam(name = "email")String email,
                             @RequestParam(name = "full_name")String full_name,
                             @RequestParam(name = "password")String password,
                             @RequestParam(name = "re_password")String re_password){
        Users user = new Users();
        List<Roles> roles = new ArrayList<>();
        Roles role = userService.getRole(1L);
        roles.add(role);
        String error_re = "Please, again enter the password";
        if(password.equals(re_password)){
            user.setEmail(email);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setFull_name(full_name);
            user.setRoles(roles);
            userService.save(user);
            return "redirect:/login";
        }
        model.addAttribute("error",error_re);
        return "register";
    }

    @GetMapping(value = "/register")
    public String  register(){

        return "register";
    }

    @PostMapping(value = "/updatePassword")
    @PreAuthorize("isAuthenticated()")
    public String updatePassword(Model model,@RequestParam(name = "old_password")String old_password,
                                 @RequestParam(name = "new_password")String new_password,
                                 @RequestParam(name = "re_password")String re_password){
        String error_re = "Please, again enter the password";
        String success = "Successfully saved";
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("currentUser",getUserData());
        model.addAttribute("brands",brands);
        Users user = getUserData();
        if(user.getPassword().equals(getUserData().getPassword())){

            if(new_password.equals(re_password)){
                user.setPassword(bCryptPasswordEncoder.encode(new_password));
                userService.save(user);
                model.addAttribute("success",success);
                return "profile";
            }
            model.addAttribute("error",error_re);
            return "profile";
        }

        return "redirect:/";
    }

    @PostMapping(value = "/updateProfile")
    @PreAuthorize("isAuthenticated()")
    public String updateProfile(Model model,@RequestParam(name = "full_name")String full_name){
        Users user = getUserData();
        user.setFull_name(full_name);
        userService.save(user);
        return "redirect:/profile";
    }

    @PostMapping(value = "/uploadAvatar")
    @PreAuthorize("isAuthenticated()")
    public String uploadAvatar(Model model,@RequestParam(name = "user_ava")MultipartFile file){

        if(file.getContentType().equals("image/jpeg")||file.getContentType().equals("image/png")) {
            try {
                String success = "Successfully saved";
                Users currentUser = getUserData();

                String picName = DigestUtils.sha1Hex("avatar_"+currentUser.getId()+"_!Picture");
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadPath + picName+".jpg");
                Files.write(path, bytes);

                currentUser.setUserAvatar(picName);

                List<Brands> brands = itemService.getAllBrands();
                model.addAttribute("brands",brands);
                model.addAttribute("success_ava",success);
                model.addAttribute("currentUser",getUserData());
                return "profile";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/profile";
    }

    @GetMapping(value = "/viewphoto/{url}",produces = {MediaType.IMAGE_JPEG_VALUE})
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody byte[] viewProfilePhoto(@PathVariable(name = "url")String url) throws IOException{
        String pictureUrl = viewPath+defaultPicture;
        if(url != null && !url.equals("null")){
            pictureUrl = viewPath+url+".jpg";
        }

        InputStream in;

        try {
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();
        }catch (Exception e){
            ClassPathResource resource = new ClassPathResource(viewPath+defaultPicture);
            in = resource.getInputStream();
            e.printStackTrace();
        }

        return IOUtils.toByteArray(in);
    }

    @PostMapping(value = "/deletePicture")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String deletePicture(@RequestParam(name = "item_id")Long item_id,
                                @RequestParam(name = "picture_id")Long picture_id){
        Pictures picture = itemService.getPicture(picture_id);
        itemService.deletePictires(picture);
        return "redirect:/details_items?id="+item_id;
    }

    @GetMapping(value = "/viewphoto_item/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody byte[] viewProfilePhoto_Item(@PathVariable(name = "url")String url) throws IOException{
        String pictureUrl = viewPath_pictures+defaultPicture;
        if(url != null && !url.equals("null")){
            pictureUrl = viewPath_pictures+url+".jpg";
        }

        InputStream in;

        try {
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();
        }catch (Exception e){
            ClassPathResource resource = new ClassPathResource(viewPath_pictures+defaultPicture);
            in = resource.getInputStream();
            e.printStackTrace();
        }

        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/basketPage")
    public String basketPage(Model model,HttpServletRequest request,@RequestParam(name = "id")Long id){
        HttpSession session = request.getSession();
        double sum = 0;
        List<Items_Basket> items = (List<Items_Basket>) session.getAttribute("basket");
        if(items == null) {
            items = new ArrayList<>();
            Items item = itemService.get(id);
            Items_Basket basketItem = new Items_Basket(id, item.getName(), 1, item.getPrice());
            items.add(basketItem);

        } else {
            Items_Basket basketItem = null;
            for (Items_Basket i : items) {
                if (i.getId().equals(id) ) {
                    basketItem = i;
                    break;
                }
            }
            if (basketItem != null) {
                basketItem.setAmount(basketItem.getAmount() + 1);

            } else {
                Items item = itemService.get(id);
                Items_Basket basketItem_other = new Items_Basket(id, item.getName(), 1, item.getPrice());
                basketItem = basketItem_other;
                items.add(basketItem);
            }
        }
        for (Items_Basket i : items) {
            sum = sum + i.getPrice() * i.getAmount();
        }
        int amount_sum = 0;
        for(Items_Basket i: items){
            amount_sum = amount_sum + i.getAmount();
        }
        model.addAttribute("amount",amount_sum);
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        session.setAttribute("basket",items);
        model.addAttribute("currentUser",getUserData());
        model.addAttribute("sum",sum);
        return "basketPage";
    }
    @PostMapping(value = "/checkIn")
    public String checkIn(HttpServletRequest request,@RequestParam(name = "full_name")String full_name,
                          @RequestParam(name = "card_number")String card_number,
                          @RequestParam(name = "expiration")String expiration,
                          @RequestParam(name = "cvv")String cvv){
        HttpSession session = request.getSession();
        List<Items_Basket> items = (List<Items_Basket>) session.getAttribute("basket");

        Items itemm = new Items();
        double sum = 0;
        for (Items_Basket i : items) {
            sum = sum + i.getPrice()*i.getAmount();
        }
        String items_data = "";
        for(Items_Basket i:items){
            items_data = items_data+i.getName() + " ";
        }

        Items_Basket items_basket = new Items_Basket();
        items_basket.setUser_name(full_name);
        items_basket.setPrice(sum);
        items_basket.setItems_name(items_data);
        //items_basket.setItems(itemm);
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        items_basket.setAddedDate(date);

        itemService.saveItems_Basket(items_basket);
        items.clear();
       session.setAttribute("basket",items);
        //items_basket.setName();

        return "redirect:/";
    }
    @GetMapping(value = "/minusAmount")
    public String minusAmount(Model model,HttpServletRequest request,@RequestParam(name = "id")Long id){
        HttpSession session = request.getSession();
        double sum = 0;
        List<Items_Basket> items = (List<Items_Basket>) session.getAttribute("basket");
        Items_Basket basketItem = null;
        for (Items_Basket i : items) {
            if (i.getId().equals(id) ) {
                basketItem = i;
                break;
            }
        }
        if (basketItem != null) {
            basketItem.setAmount(basketItem.getAmount() - 1);
            if(basketItem.getAmount() == 0){
                items.remove(basketItem);
            }
        }
        for (Items_Basket i : items) {
            sum = sum + i.getPrice()*i.getAmount();
        }
        int amount_sum = 0;
        for(Items_Basket i: items){
            amount_sum = amount_sum + i.getAmount();
        }
        model.addAttribute("amount",amount_sum);
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("sum",sum);
        session.setAttribute("basket",items);
        model.addAttribute("currentUser",getUserData());

        return "basketPage";
    }

    @GetMapping(value = "/clearBasket")
    public String clearBasket(Model model,HttpServletRequest request){
        double sum = 0;
        HttpSession session = request.getSession();
        List<Items_Basket> items = (List<Items_Basket>) session.getAttribute("basket");
        items.clear();
        for (Items_Basket i : items) {
            sum = sum + i.getPrice()*i.getAmount();
        }
        int amount_sum = 0;
        for(Items_Basket i: items){
            amount_sum = amount_sum + i.getAmount();
        }
        model.addAttribute("amount",amount_sum);
        model.addAttribute("sum",sum);
        session.setAttribute("basket",items);
        model.addAttribute("currentUser",getUserData());
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        return "basketPage";
    }

    @PostMapping(value = "/uploadPictureItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String uploadPictureItem(Model model, @RequestParam(name = "item_picture") MultipartFile file,
                                    @RequestParam(name = "item_id")Long item_id){
        java.util.Date date = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        if(file.getContentType().equals("image/jpeg")||file.getContentType().equals("image/png")) {
            try {
                Pictures picture = new Pictures();
                Items item = itemService.get(item_id);
                String success = "Successfully saved";

                String picName = DigestUtils.sha1Hex("avatar_"+file.getOriginalFilename()+"_!Picture");
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadPath_pictures + picName+".jpg");
                Files.write(path, bytes);

                picture.setUrl(picName);
                picture.setItem(item);
                picture.setAdded_Date(date);
                itemService.addPicture(picture);
                return "redirect:/details_items?id="+item_id;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @GetMapping(value = "/itemsBasket_page")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String itemsBasket_page(Model model){
        List<Items_Basket> items_baskets = itemService.getAllItems_baskets();
        model.addAttribute("baskets_users",items_baskets);
        return "itemsBasket_page";
    }

    public Users getUserData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            Users myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }

    @GetMapping(value = "/tshirts")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public String tshirts(Model model,HttpServletRequest request){
        model.addAttribute("currentUser",getUserData());
        List<Items> items = itemService.findAllSortedByInTop();
        List<Countries> countries = itemService.getAllCountries();
        HttpSession session = request.getSession();
        List<Items_Basket> items_basket = (List<Items_Basket>) session.getAttribute("basket");
        int amount_sum = 0;
        if(items_basket != null) {
            for (Items_Basket i : items_basket) {
                amount_sum = amount_sum + i.getAmount();
            }
        }
        model.addAttribute("amount",amount_sum);
        model.addAttribute("currentUser",getUserData());
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("shopItems",items);
        return "tshirts";
    }

    @GetMapping(value = "/sweethots")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public String sweethots(Model model,HttpServletRequest request){
        model.addAttribute("currentUser",getUserData());
        List<Items> items = itemService.findAllSortedByInTop();
        List<Countries> countries = itemService.getAllCountries();
        HttpSession session = request.getSession();
        List<Items_Basket> items_basket = (List<Items_Basket>) session.getAttribute("basket");
        int amount_sum = 0;
        if(items_basket != null) {
            for (Items_Basket i : items_basket) {
                amount_sum = amount_sum + i.getAmount();
            }
        }
        model.addAttribute("amount",amount_sum);
        model.addAttribute("currentUser",getUserData());
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("shopItems",items);
        return "sweethots";
    }

    @GetMapping(value = "/howOrder")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public String howOrder(Model model,HttpServletRequest request){
        model.addAttribute("currentUser",getUserData());
        List<Items> items = itemService.findAllSortedByInTop();
        List<Countries> countries = itemService.getAllCountries();
        HttpSession session = request.getSession();
        List<Items_Basket> items_basket = (List<Items_Basket>) session.getAttribute("basket");
        int amount_sum = 0;
        if(items_basket != null) {
            for (Items_Basket i : items_basket) {
                amount_sum = amount_sum + i.getAmount();
            }
        }
        model.addAttribute("amount",amount_sum);
        model.addAttribute("currentUser",getUserData());
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("shopItems",items);
        return "howOrder";
    }


    @GetMapping(value = "/aboutAuthor")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public String aboutAuthor(Model model,HttpServletRequest request){
        model.addAttribute("currentUser",getUserData());
        List<Items> items = itemService.findAllSortedByInTop();
        List<Countries> countries = itemService.getAllCountries();
        HttpSession session = request.getSession();
        List<Items_Basket> items_basket = (List<Items_Basket>) session.getAttribute("basket");
        int amount_sum = 0;
        if(items_basket != null) {
            for (Items_Basket i : items_basket) {
                amount_sum = amount_sum + i.getAmount();
            }
        }
        model.addAttribute("amount",amount_sum);
        model.addAttribute("currentUser",getUserData());
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("shopItems",items);
        return "aboutAuthor";
    }

    @GetMapping(value = "/collab")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public String collab(Model model,HttpServletRequest request){
        model.addAttribute("currentUser",getUserData());
        List<Items> items = itemService.findAllSortedByInTop();
        List<Countries> countries = itemService.getAllCountries();
        HttpSession session = request.getSession();
        List<Items_Basket> items_basket = (List<Items_Basket>) session.getAttribute("basket");
        int amount_sum = 0;
        if(items_basket != null) {
            for (Items_Basket i : items_basket) {
                amount_sum = amount_sum + i.getAmount();
            }
        }
        model.addAttribute("amount",amount_sum);
        model.addAttribute("currentUser",getUserData());
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("shopItems",items);
        return "collab";
    }

    @GetMapping(value = "/blog")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public String blog(Model model,HttpServletRequest request){
        model.addAttribute("currentUser",getUserData());
        List<Items> items = itemService.findAllSortedByInTop();
        List<Countries> countries = itemService.getAllCountries();
        HttpSession session = request.getSession();
        List<Items_Basket> items_basket = (List<Items_Basket>) session.getAttribute("basket");
        int amount_sum = 0;
        if(items_basket != null) {
            for (Items_Basket i : items_basket) {
                amount_sum = amount_sum + i.getAmount();
            }
        }
        model.addAttribute("amount",amount_sum);
        model.addAttribute("currentUser",getUserData());
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("shopItems",items);
        return "blog";
    }

    @GetMapping(value = "/contacts")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public String contacts(Model model,HttpServletRequest request){
        model.addAttribute("currentUser",getUserData());
        List<Items> items = itemService.findAllSortedByInTop();
        List<Countries> countries = itemService.getAllCountries();
        HttpSession session = request.getSession();
        List<Items_Basket> items_basket = (List<Items_Basket>) session.getAttribute("basket");
        int amount_sum = 0;
        if(items_basket != null) {
            for (Items_Basket i : items_basket) {
                amount_sum = amount_sum + i.getAmount();
            }
        }
        model.addAttribute("amount",amount_sum);
        model.addAttribute("currentUser",getUserData());
        List<Brands> brands = itemService.getAllBrands();
        model.addAttribute("brands",brands);
        model.addAttribute("countries",countries);
        model.addAttribute("shopItems",items);
        return "contacts";
    }


}
