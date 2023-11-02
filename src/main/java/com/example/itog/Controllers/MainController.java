package com.example.itog.Controllers;


import com.example.itog.Repository.*;
import com.example.itog.models.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import java.util.List;

@Controller
public class MainController {
    private final CuisineRepository cuisineRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final ReservationRepository reservationRepository;
    private final RestaurantCuisineRepository restaurantCuisineRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserFavoritesRepository userFavoritesRepository;
    private final UserRepository userRepository;
    @GetMapping("/")
    public String mainPage(){
        return "MainPAge";
    }
    @Autowired
    public MainController(CuisineRepository cuisineRepository, MenuRepository menuRepository, OrderRepository orderRepository, ReservationRepository reservationRepository, RestaurantCuisineRepository restaurantCuisineRepository, RestaurantRepository restaurantRepository, UserFavoritesRepository userFavoritesRepository, UserRepository userRepository) {
        this.cuisineRepository = cuisineRepository;
        this.menuRepository = menuRepository;
        this.orderRepository = orderRepository;
        this.reservationRepository = reservationRepository;
        this.restaurantCuisineRepository = restaurantCuisineRepository;
        this.restaurantRepository = restaurantRepository;
        this.userFavoritesRepository = userFavoritesRepository;
        this.userRepository = userRepository;
    }

//    @GetMapping("/Cuisines")
//    public String getCuisines(Model model) {
//        model.addAttribute("cuisines", cuisineRepository.findAll());
//        return "Cuisines";
//    }
    @GetMapping("/Cuisines")
    public String getCuisines(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Cuisine> cuisines;
        if (StringUtils.hasText(search)) {
            cuisines = cuisineRepository.findAllBycuisineType(search);
        }
        else {
            cuisines = cuisineRepository.findAll();
        }
        model.addAttribute("cuisines", cuisines);
        return "Cuisines";
    }

    @PostMapping("/addCuisine")
    public String addCuisine(@RequestParam("cuisineType") @NotBlank(message = "Cuisine type is required") String cuisineType) {
        Cuisine cuisine = new Cuisine();
        cuisine.setCuisineType(cuisineType);
        cuisineRepository.save(cuisine);
        return "redirect:/Cuisines";
    }


    @GetMapping("/editCuisine/{id}")
    public String editCuisineForm(@PathVariable int id, Model model) {
        Cuisine cuisine = cuisineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cuisine Id:" + id));
        model.addAttribute("cuisine", cuisine);
        return "editCuisine";
    }

    @PostMapping("/editCuisine/{id}")
    public String editCuisine(@PathVariable int id, @RequestParam("cuisineType") String cuisineType) {
        Cuisine cuisine = cuisineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cuisine Id:" + id));
        cuisine.setCuisineType(cuisineType);
        cuisineRepository.save(cuisine);
        return "redirect:/Cuisines";
    }

    @GetMapping("/deleteCuisine/{id}")
    public String deleteCuisine(@PathVariable int id) {
        Cuisine cuisine = cuisineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cuisine Id:" + id));
        cuisineRepository.deleteById(id);
        return "redirect:/Cuisines";
    }

//    @GetMapping("/Menus")
//    public String getMenus(Model model) {
//        List<Menu> menus = menuRepository.findAll();
//        List<Restaurant> restaurants = restaurantRepository.findAll();
//
//        model.addAttribute("menus", menus);
//        model.addAttribute("restaurants", restaurants);
//
//        return "Menus";
//    }

    @GetMapping("/Menus")
    public String getMenus(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Menu> menus;
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (StringUtils.hasText(search)) {
            menus = menuRepository.findAllBydishName(search);
        }
        else {
            menus = menuRepository.findAll();
        }
        model.addAttribute("menus", menus);
        model.addAttribute("restaurants", restaurants);
        return "Menus";
    }

    @PostMapping("/addMenu")
    public String addMenu(@RequestParam int restaurantId,
                          @RequestParam String dishName,
                          @RequestParam double price) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        Menu menu = new Menu(null, restaurant, dishName, price);
        menuRepository.save(menu);
        return "redirect:/Menus";
    }

    @GetMapping("/editMenu/{id}")
    public String editMenuForm(@PathVariable int id, Model model) {
        Menu menu = menuRepository.getById(id);
        List<Restaurant> restaurants = restaurantRepository.findAll();

        model.addAttribute("menu", menu);
        model.addAttribute("restaurants", restaurants);

        return "editMenu";
    }

    @PostMapping("/editMenu/{id}")
    public String editMenu(@PathVariable Long id,
                           @RequestParam int restaurantId,
                           @RequestParam String dishName,
                           @RequestParam double price) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        Menu menu = new Menu(id, restaurant, dishName, price);
        menuRepository.save(menu);
        return "redirect:/Menus";
    }

    @GetMapping("/deleteMenu/{id}")
    public String deleteMenu(@PathVariable int id) {
        menuRepository.deleteById(id);
        return "redirect:/Menus";
    }

//    @GetMapping("/Restaurants")
//    public String getRestaurants(Model model) {
//        List<Restaurant> restaurants = restaurantRepository.findAll();
//        model.addAttribute("restaurants", restaurants);
//        return "Restaurant";
//    }

    @GetMapping("/Restaurants")
    public String getRestaurants(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Restaurant> restaurants;
        if (StringUtils.hasText(search)) {
            restaurants = restaurantRepository.findAllByname(search);
        }
        else {
            restaurants = restaurantRepository.findAll();
        }
        model.addAttribute("restaurants", restaurants);
        return "Restaurant";
    }

    @PostMapping("/addRestaurant")
    public String addRestaurant(@RequestParam String name,
                                @RequestParam String address,
                                @RequestParam String phoneNumber,
                                @RequestParam int capacity) {
        Restaurant restaurant = new Restaurant(null, name, address, phoneNumber, capacity);
        restaurantRepository.save(restaurant);
        return "redirect:/Restaurants";
    }

    @GetMapping("/editRestaurant/{id}")
    public String editRestaurantForm(@PathVariable int id, Model model) {
        Restaurant restaurant = restaurantRepository.getById(id);
        model.addAttribute("restaurant", restaurant);
        return "editRestaurant";
    }

    @PostMapping("/editRestaurant/{id}")
    public String editRestaurant(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam String address,
                                 @RequestParam String phoneNumber,
                                 @RequestParam int capacity) {
        Restaurant restaurant = new Restaurant(id, name, address, phoneNumber, capacity);
        restaurantRepository.save(restaurant);
        return "redirect:/Restaurants";
    }

    @GetMapping("/deleteRestaurant/{id}")
    public String deleteRestaurant(@PathVariable int id) {
        restaurantRepository.deleteById(id);
        return "redirect:/Restaurants";
    }

    @GetMapping("/restaurantCuisines")
    public String getRestaurantCuisines(Model model) {
        List<RestaurantCuisine> restaurantCuisines = restaurantCuisineRepository.findAll();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<Cuisine> cuisines = cuisineRepository.findAll();

        model.addAttribute("restaurantCuisines", restaurantCuisines);
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("cuisines", cuisines);

        return "restaurantCuisines";
    }

    @PostMapping("/addRestaurantCuisine")
    public String addRestaurantCuisine(@RequestParam int restaurantId,
                                       @RequestParam int cuisineId) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        Cuisine cuisine = cuisineRepository.getById(cuisineId);
        RestaurantCuisine restaurantCuisine = new RestaurantCuisine(null, restaurant, cuisine);
        restaurantCuisineRepository.save(restaurantCuisine);
        return "redirect:/restaurantCuisines";
    }

    @GetMapping("/editRestaurantCuisine/{id}")
    public String editRestaurantCuisineForm(@PathVariable int id, Model model) {
        RestaurantCuisine restaurantCuisine = restaurantCuisineRepository.getById(id);
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<Cuisine> cuisines = cuisineRepository.findAll();

        model.addAttribute("restaurantCuisine", restaurantCuisine);
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("cuisines", cuisines);

        return "editRestaurantCuisine";
    }

    @PostMapping("/editRestaurantCuisine/{id}")
    public String editRestaurantCuisine(@PathVariable Long id,
                                        @RequestParam int restaurantId,
                                        @RequestParam int cuisineId) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        Cuisine cuisine = cuisineRepository.getById(cuisineId);
        RestaurantCuisine restaurantCuisine = new RestaurantCuisine(id, restaurant, cuisine);
        restaurantCuisineRepository.save(restaurantCuisine);
        return "redirect:/restaurantCuisines";
    }

    @GetMapping("/deleteRestaurantCuisine/{id}")
    public String deleteRestaurantCuisine(@PathVariable int id) {
        restaurantCuisineRepository.deleteById(id);
        return "redirect:/restaurantCuisines";
    }

//    @GetMapping("/Users")
//    public String getUsers(Model model) {
//        model.addAttribute("users", userRepository.findAll());
//        return "Users";
//    }


    @GetMapping("/Users")
    public String getUsers(@RequestParam(name = "search", required = false) String search, Model model) {
        List<User> users;
        if (StringUtils.hasText(search)) {
            users = userRepository.findAllByfirstName(search);
        }
        else {
            users = userRepository.findAll();
        }
        model.addAttribute("users", users);
        return "Users";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam String email,
                          @RequestParam String password) {
        User user = new User(null, firstName, lastName, email, password);
        userRepository.save(user);
        return "redirect:/Users";
    }

    @GetMapping("/editUser/{id}")
    public String editUserForm(@PathVariable int id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/editUser/{id}")
    public String editUser(@PathVariable int id,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String email,
                           @RequestParam String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return "redirect:/Users";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return "redirect:/Users";
    }

    @GetMapping("/Orders")
    public String getOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("reservations", reservationRepository.findAll());
        model.addAttribute("menus", menuRepository.findAll());
        return "Orders";
    }

//    @GetMapping("/Orders")
//    public String getOrders(@RequestParam(name = "search", required = false) String search, Model model) {
//        List<Order> orders;
//        List<Restaurant> restaurants = restaurantRepository.findAll();
//        List<Menu> menus = menuRepository.findAll();
//        if (StringUtils.hasText(search)) {
//            orders = orderRepository.findAllByity(search);
//        }
//        else {
//            orders = orderRepository.findAll();
//        }
//        model.addAttribute("orders", orders);
//        model.addAttribute("reservations", restaurants);
//        model.addAttribute("menus", menus);
//        return "Orders";
//    }

    @PostMapping("/addOrder")
    public String addOrder(@RequestParam int reservationId,
                           @RequestParam int menuId,
                           @RequestParam int quantity) {
        Reservation reservation = reservationRepository.getById(reservationId);
        Menu menu = menuRepository.getById(menuId);
        Order order = new Order(null, reservation, menu, quantity);
        orderRepository.save(order);
        return "redirect:/Orders";
    }

    @GetMapping("/editOrder/{id}")
    public String editOrderForm(@PathVariable int id, Model model) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
        List<Reservation> reservations = reservationRepository.findAll();
        List<Menu> menus = menuRepository.findAll();

        model.addAttribute("order", order);
        model.addAttribute("reservations", reservations);
        model.addAttribute("menus", menus);
        return "editOrder";
    }

    @PostMapping("/editOrder/{id}")
    public String editOrder(@PathVariable int id,
                            @RequestParam int reservationId,
                            @RequestParam int menuId,
                            @RequestParam int quantity) {
        Reservation reservation = reservationRepository.getById(reservationId);
        Menu menu = menuRepository.getById(menuId);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
        order.setReservation(reservation);
        order.setMenu(menu);
        order.setQuantity(quantity);
        orderRepository.save(order);
        return "redirect:/Orders";
    }

    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable int id) {
        orderRepository.deleteById(id);
        return "redirect:/Orders";
    }

    @GetMapping("/Reservations")
    public String getReservations(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("restaurants", restaurantRepository.findAll());
        return "Reservations";
    }

    @PostMapping("/addReservation")
    public String addReservation(@RequestParam int userId,
                                 @RequestParam int restaurantId,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                                 @RequestParam int partySize) {
        User user = userRepository.getById(userId);
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        Reservation reservation = new Reservation(null, user, restaurant, date, time, partySize);
        reservationRepository.save(reservation);
        return "redirect:/Reservations";
    }

    @GetMapping("/editReservation/{id}")
    public String editReservationForm(@PathVariable int id, Model model) {
        Reservation reservation = reservationRepository.getById(id);
        model.addAttribute("reservation", reservation);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("restaurants", restaurantRepository.findAll());
        return "editReservation";
    }

    @PostMapping("/editReservation/{id}")
    public String editReservation(@PathVariable Long id,
                                  @RequestParam int userId,
                                  @RequestParam int restaurantId,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                                  @RequestParam int partySize) {
        User user = userRepository.getById(userId);
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        Reservation reservation = new Reservation(id, user, restaurant, date, time, partySize);
        reservationRepository.save(reservation);
        return "redirect:/Reservations";
    }

    @GetMapping("/deleteReservation/{id}")
    public String deleteReservation(@PathVariable int id) {
        reservationRepository.deleteById(id);
        return "redirect:/Reservations";
    }

    @GetMapping("/userFavorites")
    public String getUserFavorites(Model model) {
        List<UserFavorites> userFavorites = userFavoritesRepository.findAll();
        List<User> users = userRepository.findAll();
        List<Restaurant> restaurants = restaurantRepository.findAll();

        model.addAttribute("userFavorites", userFavorites);
        model.addAttribute("users", users);
        model.addAttribute("restaurants", restaurants);

        return "userFavorites";
    }

    @PostMapping("/addUserFavorite")
    public String addUserFavorite(@RequestParam int userId,
                                  @RequestParam int restaurantId) {
        User user = userRepository.getById(userId);
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        UserFavorites userFavorites = new UserFavorites(null, user, restaurant);
        userFavoritesRepository.save(userFavorites);
        return "redirect:/userFavorites";
    }

    @GetMapping("/editUserFavorite/{id}")
    public String editUserFavoriteForm(@PathVariable int id, Model model) {
        UserFavorites userFavorites = userFavoritesRepository.getById(id);
        List<User> users = userRepository.findAll();
        List<Restaurant> restaurants = restaurantRepository.findAll();

        model.addAttribute("userFavorite", userFavorites);
        model.addAttribute("users", users);
        model.addAttribute("restaurants", restaurants);

        return "editUserFavorite";
    }

    @PostMapping("/editUserFavorite/{id}")
    public String editUserFavorite(@PathVariable Long id,
                                   @RequestParam int userId,
                                   @RequestParam int restaurantId) {
        User user = userRepository.getById(userId);
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        UserFavorites userFavorites = new UserFavorites(id, user, restaurant);
        userFavoritesRepository.save(userFavorites);
        return "redirect:/userFavorites";
    }

    @GetMapping("/deleteUserFavorite/{id}")
    public String deleteUserFavorite(@PathVariable int id) {
        userFavoritesRepository.deleteById(id);
        return "redirect:/userFavorites";
    }

}
