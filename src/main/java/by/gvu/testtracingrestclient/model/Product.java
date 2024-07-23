package by.gvu.testtracingrestclient.model;

import java.util.List;

public record Product(
        Long id,
        String title,
        String description,
        String category,
        Double price,
        Double discountPercentage,
        Double rating,
        Integer stock,
        List<String> tags,
        String brand,
        String sku,
        Double weight,
        Dimensions dimensions,
        String warrantyInformation,
        String shippingInformation,
        String availabilityStatus,
        List<Review> reviews,
        String returnPolicy,
        Integer minimumOrderQuantity,
        MetaData meta,
        List<String> images,
        String thumbnail
) {
}