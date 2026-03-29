package com.ecommerce.controller;

import com.ecommerce.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow frontend access
public class AiController {

    private final AiService aiService;
    private final com.ecommerce.service.SemanticSearchService semanticSearchService;
    private final com.ecommerce.service.ProductService productService;

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Message cannot be empty"));
        }
        
        String response = aiService.generateResponse(message);
        return ResponseEntity.ok(Map.of("response", response));
    }

    @GetMapping("/search")
    public ResponseEntity<java.util.List<?>> search(@RequestParam String query, @RequestParam(defaultValue = "5") int limit) {
        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        
        java.util.List<Long> productIds = semanticSearchService.search(query, limit);
        java.util.List<Object> results = productIds.stream()
                .map(productService::getProductById)
                .collect(java.util.stream.Collectors.toList());
        
        return ResponseEntity.ok(results);
    }
}
