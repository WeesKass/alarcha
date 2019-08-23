package com.dmalataeva.alarcha.services;

import com.dmalataeva.alarcha.entities.CustomerEntity;
import com.dmalataeva.alarcha.entities.OrderEntity;
import com.dmalataeva.alarcha.models.Order;
import com.dmalataeva.alarcha.models.OrderAndShipping;
import com.dmalataeva.alarcha.models.Shipping;
import com.dmalataeva.alarcha.repositories.OrderRepository;
import com.dmalataeva.alarcha.util.exception.BadRequestException;
import com.dmalataeva.alarcha.util.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShippingService shippingService;

    /*public OrderService() {
        customerService = new CustomerService();
        shippingService = new ShippingService();
    }*/

    public Order getOrderById(int orderId) throws Exception {
        return new Order(orderRepository.findById(orderId).orElseThrow(RecordNotFoundException::new));
    }

    public List<Order> getOrderByCustomerId(int customerId) {
        List<OrderEntity> resEntities = orderRepository.findOrdersByCustomerId(customerId);
        List<Order> res = new ArrayList<>();
        for (OrderEntity entity:resEntities) {
            res.add(new Order(entity));
        }

        return res;
    }

    public List<Order> getOrderByShippingId(int shippingId) {
        List<OrderEntity> resEntities = orderRepository.findOrdersByShippingId(shippingId);
        List<Order> res = new ArrayList<>();
        for (OrderEntity entity:resEntities) {
            res.add(new Order(entity));
        }

        return res;
    }

    public Order saveOrder(OrderAndShipping orderAndShipping) throws Exception {

        // check customer id
        try {
            customerService.getCustomerById(orderAndShipping.getCustomerId());
        } catch (RecordNotFoundException e) {
            throw new BadRequestException();
        }

        // add shipping
        Shipping shipping = shippingService.saveShipping(orderAndShipping.extractShipping());

        // set the new id
        Order order = orderAndShipping.extractOrder();
        order.setShippingId(shipping.getShippingId());

        OrderEntity saveResult = orderRepository.save(order.convertToEntity());
        return new Order(saveResult);
    }

    public void deleteOrderById(int orderId) {
        orderRepository.deleteById(orderId);
    }
}
