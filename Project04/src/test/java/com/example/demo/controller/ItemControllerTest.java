package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.demo.TestUtils.createItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        when(itemRepository.findAll()).thenReturn( Arrays.asList(createItem(1), createItem(2)));
        when(itemRepository.findById(1L)).thenReturn( Optional.of( createItem(1)));
        when(itemRepository.findByName("item")).thenReturn( Arrays.asList(createItem(1), createItem(2)));
    }

    @Test
    public void testGetItems() {
        ResponseEntity<Item> response = itemController.getItemById(1L);

        assertEquals(200, response.getStatusCodeValue());
        Item item = response.getBody();

        assertNotNull( item );

        assertEquals(createItem(1).getId(), item.getId());
    }

    @Test
    public void testGetItemsByName() {
        ResponseEntity<List<Item>> response = itemController.getItemsByName("item");

        assertEquals(200, response.getStatusCodeValue());

        List<Item> items = response.getBody();

        assertNotNull( items );

        assertEquals(createItem(1).getId(), items.get( 0 ).getId());
    }

    @Test
    public void testGetAllItems() {
        ResponseEntity<List<Item>> response = itemController.getItems();

        assertEquals(200, response.getStatusCodeValue());

        List<Item> items = response.getBody();

        assertNotNull( items );

        assertEquals(2, items.size());
    }
}
