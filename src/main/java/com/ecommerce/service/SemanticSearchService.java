package com.ecommerce.service;

import com.ecommerce.dto.ProductEmbedding;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SemanticSearchService {

    private final ProductRepository productRepository;

    @Autowired(required = false)
    private EmbeddingModel embeddingModel;

    private final List<ProductEmbedding> searchIndex = new ArrayList<>();
    private boolean indexed = false;

    public SemanticSearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Lazy indexing — only runs when the first search is performed,
     * avoiding slow @PostConstruct blocking on app startup.
     */
    private void ensureIndexed() {
        if (indexed || embeddingModel == null) return;
        try {
            log.info("Lazy-indexing products for semantic search...");
            List<Product> products = productRepository.findAll().stream().limit(5).collect(Collectors.toList());
            searchIndex.clear();

            for (Product product : products) {
                String content = formatProductForEmbedding(product);
                List<Double> embedding = embeddingModel.embed(content);
                searchIndex.add(new ProductEmbedding(product.getId(), embedding));
            }
            indexed = true;
            log.info("Successfully indexed {} products for semantic search.", searchIndex.size());
        } catch (Exception e) {
            log.error("Failed to index products: {}", e.getMessage());
        }
    }

    public List<Long> search(String query, int limit) {
        if (embeddingModel == null) {
            log.warn("EmbeddingModel not available. Ollama may not be running.");
            return new ArrayList<>();
        }

        ensureIndexed();

        if (searchIndex.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            List<Double> queryEmbedding = embeddingModel.embed(query);

            return searchIndex.stream()
                    .sorted(Comparator.comparingDouble((ProductEmbedding pe) ->
                        cosineSimilarity(queryEmbedding, pe.getEmbedding())).reversed())
                    .limit(limit)
                    .map(ProductEmbedding::getProductId)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Semantic search failed: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private String formatProductForEmbedding(Product p) {
        return String.format("%s. %s. Category: %s. Brand: %s. Tags: %s",
                p.getTitle(),
                p.getDescription(),
                p.getCategory(),
                p.getBrand(),
                String.join(", ", p.getTags())
        );
    }

    private double cosineSimilarity(List<Double> vectorA, List<Double> vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        if (vectorA.size() != vectorB.size()) {
            return 0.0;
        }

        for (int i = 0; i < vectorA.size(); i++) {
            double a = vectorA.get(i);
            double b = vectorB.get(i);
            dotProduct += a * b;
            normA += Math.pow(a, 2);
            normB += Math.pow(b, 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
