package by.gvu.testtracingrestclient.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@Builder
public record CategoryDetails(
        String categoryName,
        List<Product> productList,
        Integer productCount
) {
}
