package by.gvu.testtracingrestclient.controller;

import by.gvu.testtracingrestclient.model.CategoryDetails;
import by.gvu.testtracingrestclient.service.TracingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class SimpleController {

    private final TracingService tracingService;

    @GetMapping("/categoryInfo")
    public List<CategoryDetails> getListProducts() {
        return tracingService.getCategoryDetailsList();
    }
}
