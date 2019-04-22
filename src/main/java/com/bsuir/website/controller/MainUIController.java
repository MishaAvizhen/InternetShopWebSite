package com.bsuir.website.controller;

import com.bsuir.website.constants.RoleConstants;
import com.bsuir.website.dto.AdvertUpdateDto;
import com.bsuir.website.entity.Advert;
import com.bsuir.website.entity.UserOrder;
import com.bsuir.website.entity.User;
import com.bsuir.website.repository.AdvertRepository;
import com.bsuir.website.service.OrderService;
import com.bsuir.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@SessionAttributes({"currentUser", "cart"})
public class MainUIController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/")
    public String welcome(ModelMap modelMap) {
        initSession(modelMap);
        modelMap.put("message", "Hello World");
        return "index";
    }

    @RequestMapping("/catalog")
    public String goToCatalog(ModelMap modelMap) {
        initSession(modelMap);
        return "catalog";
    }

    @RequestMapping("/login")
    public String loginPage(ModelMap model) {
        initSession(model);
        return "login";
    }

    @RequestMapping("/logout")
    public String logoutUser(ModelMap model) {
        model.remove("currentUser");
        initSession(model);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(ModelMap model, @RequestParam("username") String login, @RequestParam("password") String password) {
        User logonUser = userService.loginUserOrReturnNull(login, password);
        if (logonUser != null) {
            model.addAttribute("currentUser", logonUser);
            return "index";
        } else {
            model.addAttribute("msg", "Пользователь с именем " + login + " не найден");
            return "login";
        }
    }

    @RequestMapping(value = "/advert/{id}", method = RequestMethod.GET)
    public ModelAndView goToAdvert(@PathVariable Integer id, ModelMap model) {
        return goToAdvertWithMode(id, model, false);
    }

    @RequestMapping(value = "/editAdvert/{id}", method = RequestMethod.GET)
    public ModelAndView goToAdvertWithEditMode(@PathVariable Integer id, ModelMap model) {
        return goToAdvertWithMode(id, model, true);
    }

    private ModelAndView goToAdvertWithMode(Integer id, ModelMap model, boolean isEditMode) {
        ModelAndView modelAndView = new ModelAndView("advert");
        initSession(model);
        Optional<Advert> advertOptional = advertRepository.findById(id);
        if (advertOptional.isPresent()) {
            modelAndView.addObject("advert", advertOptional.get());
            modelAndView.addObject("isEditMode", isEditMode);
        }
        return modelAndView;
    }

    private void initSession(ModelMap model) {
        if(!model.containsAttribute("currentUser")) {
            User guest = new User("guest", "", "Guest", "Guest", RoleConstants.CLIENT_ROLE);
            guest.setId(0);
            model.addAttribute("currentUser", guest);
        }
        if (!model.containsAttribute("cart")) {
            model.addAttribute("cart", new LinkedList<Integer>());
        }
    }

    @RequestMapping(value = "/addToCart/{advertId}", method = RequestMethod.GET)
    public ModelAndView addAdvertToCart(@PathVariable Integer advertId, @ModelAttribute("cart") List<Integer> cart) {
        ModelAndView modelAndView = new ModelAndView("advert");
        Optional<Advert> advertOptional = advertRepository.findById(advertId);
        if (advertOptional.isPresent()) {
            cart.add(advertId);
            modelAndView.addObject("advert", advertOptional.get());
            modelAndView.addObject("addToCartMsg", "Инструмент был добавлен в корзину");

        }
        return modelAndView;
    }

    @RequestMapping("/cart")
    public ModelAndView goToCart(@ModelAttribute("cart") List<Integer> cart) {
        ModelAndView modelAndView = new ModelAndView("cart");
        List<Advert> currentUserCartAdverts = new ArrayList<>();
        Double paymentAmount = 0d;
        for (Integer advertId : cart) {
            Optional<Advert> advertOptional = advertRepository.findById(advertId);
            if (advertOptional.isPresent()) {
                Advert advert = advertOptional.get();
                paymentAmount += advert.getPrice();
                currentUserCartAdverts.add(advert);
            }
        }
        modelAndView.addObject("cartAdverts", currentUserCartAdverts);
        modelAndView.addObject("paymentAmount", paymentAmount);
        return modelAndView;
    }

    @RequestMapping(value = "/removeFromCart/{advertId}", method = RequestMethod.GET)
    public ModelAndView removeFromCart(@PathVariable Integer advertId, @ModelAttribute("cart") List<Integer> cart) {
        cart.remove(advertId);
        return goToCart(cart);
    }

    @RequestMapping(value = "/makeOrder", method = RequestMethod.GET)
    public ModelAndView makeOrder(@ModelAttribute("cart") List<Integer> cart, @ModelAttribute("currentUser") User currentUser) {
        UserOrder userOrder = orderService.makeOrder(currentUser, cart);
        cart.clear();
        ModelAndView modelAndView = new ModelAndView("order");
        modelAndView.addObject("userOrder", userOrder);
        return modelAndView;
    }

    @RequestMapping(value = "/removeAdvert/{id}", method = RequestMethod.GET)
    public String removeAdvert(@PathVariable Integer id, @ModelAttribute("currentUser") User currentUser, ModelMap modelMap) {
        if (currentUser.isAdmin()) {
            advertRepository.deleteById(id);
        }
        return goToCatalog(modelMap);
    }

    @RequestMapping(value = "/admin/editAdvert", method = RequestMethod.POST)
    public ModelAndView editAdvert(@ModelAttribute("currentUser") User currentUser, ModelMap model, HttpServletRequest request) {
        if (currentUser.isAdmin()) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Integer advertId = Integer.valueOf(parameterMap.get("advertId")[0]);
            Optional<Advert> advertOptional = advertRepository.findById(advertId);
            if (advertOptional.isPresent()) {
                Advert advert = advertOptional.get();

                advert.setName(parameterMap.get("advertName")[0]);
                advert.setGroupName(parameterMap.get("advertGroup")[0]);
                advert.setPrice(Double.valueOf(parameterMap.get("advertPrice")[0].replace(",", ".")));
                advert.setCountry(parameterMap.get("advertCountry")[0]);
                advert.setGuaranteeAmountOfMonth(Integer.valueOf(parameterMap.get("advertGuarantee")[0]));
                advert.setDescription(parameterMap.get("advertDescription")[0]);
                advertRepository.save(advert);
                return goToAdvert(advert.getId(), model);
            }
        }
        return new ModelAndView("catalog");
    }


}
