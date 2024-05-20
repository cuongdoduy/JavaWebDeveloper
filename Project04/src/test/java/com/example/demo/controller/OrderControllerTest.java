package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.demo.TestUtils.createOrders;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {

        when(userRepository.findByUsername("test")).thenReturn( TestUtils.createUser() );
        when(orderRepository.findByUser(any())).thenReturn(createOrders());
    }

    @Test
    public void testSubmitOrder() {

        ResponseEntity<UserOrder> response = orderController.submit("test");

        assertNotNull( response );

        assertEquals( 200, response.getStatusCodeValue() );

        UserOrder order = response.getBody();

        assertNotNull( order );
        assertEquals( TestUtils.createItems() , order.getItems() );
        assertEquals( TestUtils.createUser().getId() , order.getUser().getId() );

    }

    @Test
    public void testGetOrdersForUser() {

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("test");

        assertNotNull( response );

        assertEquals( 200, response.getStatusCodeValue() );

        assertNotNull( response.getBody() );

        List<UserOrder> orders = response.getBody();

        assertEquals( 2, orders.size() );

    }


}
