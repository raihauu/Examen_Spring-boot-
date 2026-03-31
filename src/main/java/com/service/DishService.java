package com.service;

import com.model.Dish;

public List<Dish> createDishes(List<DishCreate> inputs) throws SQLException {

    List<Dish> result = new ArrayList<>();

    for (DishCreate dto : inputs) {

        if (dishRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException(
                    "Dish.name=" + dto.getName() + " already exists"
            );
        }

        Dish dish = new Dish();
        dish.setName(dto.getName());
        dish.setCategory(dto.getCategory());
        dish.setPrice(dto.getPrice());

        result.add(dishRepository.save(dish));
    }

    return result;
}