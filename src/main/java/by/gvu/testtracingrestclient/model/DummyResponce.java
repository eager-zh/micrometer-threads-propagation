package by.gvu.testtracingrestclient.model;

import java.util.List;

public record DummyResponce(
        List<Product> products,
        Integer total,
        Integer skip,
        Integer limit
) { }