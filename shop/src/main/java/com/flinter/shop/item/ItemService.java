package com.flinter.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String title, Integer price) {
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findOne(Long id) {

        return itemRepository.findById(id);
    }

    public void updateItem(Long id, String title, Integer price) throws Exception {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            item.get().setTitle(title);
            item.get().setPrice(price);
        } else {
            throw new Exception();
        }
    }
}
