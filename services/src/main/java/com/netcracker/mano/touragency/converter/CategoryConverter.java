package com.netcracker.mano.touragency.converter;


import com.netcracker.mano.touragency.dto.CategoryDTO;
import com.netcracker.mano.touragency.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public Category convertToEntity(CategoryDTO categoryDTO) {
        return Category.builder().name(categoryDTO.getName()).id(categoryDTO.getId()).build();
    }

    public CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}
