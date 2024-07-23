package by.gvu.testtracingrestclient.service.impl;

import by.gvu.testtracingrestclient.model.CategoryDetails;
import by.gvu.testtracingrestclient.model.Product;
import by.gvu.testtracingrestclient.service.TracingService;
import io.micrometer.observation.Observation.Scope;
import io.micrometer.tracing.BaggageManager;
import io.micrometer.observation.ObservationRegistry;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class DummyJsonTracingService implements TracingService {
    
	@Autowired
	private RestClient restClient;
	
	@Autowired
	private ObservationRegistry observationRegistry;
	
	@Autowired
	private BaggageManager braveBaggageManager;	

    @Override
    public List<CategoryDetails> getCategoryDetailsList() {
        final ParameterizedTypeReference<List<String>> refCategoryList = new ParameterizedTypeReference<List<String>>() {};
        final Scope currentObservationScope = observationRegistry.getCurrentObservationScope();
        final Map<String, String> mdcContext = MDC.getCopyOfContextMap();
        final List<String> categories = restClient.post()
                .uri(uriBuilder -> uriBuilder.path("/success/categories").build())
                .retrieve()
                .body(refCategoryList);
        if (categories != null && !categories.isEmpty()) {
			return categories.parallelStream().map(category -> {
                final ParameterizedTypeReference<List<Product>> refProductList = new ParameterizedTypeReference<List<Product>>() {};
                final Scope oldCurrentObservationScope = observationRegistry.getCurrentObservationScope();
                try {
					observationRegistry.setCurrentObservationScope(currentObservationScope);
                	MDC.setContextMap(mdcContext);
					final List<Product> productList = restClient.post()
					        .uri(uriBuilder -> uriBuilder.path("/success/products/category/{categoryId}").build(category))
					        .retrieve()
					        .body(refProductList);
					return new CategoryDetails(category, productList, null);
				} finally {
					MDC.clear();
					observationRegistry.setCurrentObservationScope(oldCurrentObservationScope);
				}
            }).toList();
        }
        return Collections.emptyList();
    }
}
