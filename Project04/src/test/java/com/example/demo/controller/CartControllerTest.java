package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.demo.TestUtils.createCart;
import static com.example.demo.TestUtils.createUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class CartControllerTest {
    @InjectMocks
    private CartController cartController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        when(userRepository.findByUsername("TestUsername")).thenReturn( createUser() );
        when(itemRepository.findById(1L)).thenReturn( Optional.of( TestUtils.createItem(1)));
    }

    @Test
    public void testAddToCart() {

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("TestUsername");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(3);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);

        assertEquals(200, response.getStatusCodeValue());

        Cart cart = response.getBody();
        Cart generatedCart = createCart( createUser());

        Item item = TestUtils.createItem(modifyCartRequest.getItemId());
        BigDecimal itemPrice = item.getPrice();


        BigDecimal expectedTotal = itemPrice.multiply(BigDecimal.valueOf(modifyCartRequest.getQuantity()))
                .add(generatedCart.getTotal());


        assertEquals("TestUsername", cart.getUser().getUsername());
        assertEquals(generatedCart.getItems().size() + modifyCartRequest.getQuantity(), cart.getItems().size());
        assertEquals(TestUtils.createItem(1), cart.getItems().get(0));
        assertEquals(expectedTotal, cart.getTotal());
    }

    @Test
    public void testRemoveFromCart() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("TestUsername");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(1);

        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart actualCart = response.getBody();
        Cart generatedCart = createCart(createUser());

        Item item = TestUtils.createItem(modifyCartRequest.getItemId());

        BigDecimal itemPrice = item.getPrice();

        BigDecimal expectedTotal = generatedCart.getTotal().subtract(itemPrice.multiply( BigDecimal.valueOf(modifyCartRequest.getQuantity())));

        assertEquals("TestUsername", actualCart.getUser().getUsername());

        assertEquals(generatedCart.getItems().size() - modifyCartRequest.getQuantity(), actualCart.getItems().size());

        assertEquals(expectedTotal, actualCart.getTotal());
    }


}
