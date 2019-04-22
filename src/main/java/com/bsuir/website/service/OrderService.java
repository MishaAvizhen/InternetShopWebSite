package com.bsuir.website.service;

import com.bsuir.website.entity.Advert;
import com.bsuir.website.entity.UserOrder;
import com.bsuir.website.entity.OrderItem;
import com.bsuir.website.entity.User;
import com.bsuir.website.repository.AdvertRepository;
import com.bsuir.website.repository.OrderItemRepository;
import com.bsuir.website.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    private AdvertRepository advertRepository;
    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(AdvertRepository advertRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.advertRepository = advertRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public UserOrder makeOrder(User orderedByUser, List<Integer> advertIds) {
        Map<Advert, Integer> advertToAmountMap = groupAdverts(advertIds);
        UserOrder userUserOrder = new UserOrder();
        userUserOrder.setOrderedByUser(orderedByUser);
        UserOrder savedUserOrder = orderRepository.save(userUserOrder);
        List<OrderItem> savedUserOrderItems = new ArrayList<>();
        for (Map.Entry<Advert, Integer> advertToAmountEntry : advertToAmountMap.entrySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setAdvert(advertToAmountEntry.getKey());
            orderItem.setAmount(advertToAmountEntry.getValue());
            orderItem.setUserOrder(savedUserOrder);
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            savedUserOrderItems.add(savedOrderItem);
        }
        savedUserOrder.setOrderItems(savedUserOrderItems);
        return orderRepository.save(savedUserOrder);
    }

    private Map<Advert, Integer> groupAdverts(List<Integer> advertIds) {
        Map<Advert, Integer> advertToAmountMap = new HashMap<>();
        for (Integer advertId : advertIds) {
            Optional<Advert> advertByIdOptional = advertRepository.findById(advertId);
            if (advertByIdOptional.isPresent()) {
                Advert advert = advertByIdOptional.get();
                if (!advertToAmountMap.containsKey(advert)) {
                    advertToAmountMap.put(advert, 1);
                } else {
                    Integer curAmount = advertToAmountMap.get(advert);
                    curAmount++;
                    advertToAmountMap.put(advert, curAmount);
                }
            }
        }
        return advertToAmountMap;
    }
}
