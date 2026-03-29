package com.ecommerce.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AiService {

    @Autowired(required = false)
    private ChatModel chatModel;

    public String generateResponse(String message) {
        if (chatModel == null) {
            return "AI service is not available. Please ensure Ollama is running on localhost:11434.";
        }
        try {
            log.info("Generating AI response for message: {}", message);
            return chatModel.call(message);
        } catch (Exception e) {
            log.error("Error generating AI response: {}", e.getMessage());
            return "I'm sorry, I'm having trouble connecting to my local brain right now. Please try again later.";
        }
    }

    public ChatResponse generateChatResponse(Prompt prompt) {
        if (chatModel == null) {
            throw new IllegalStateException("ChatModel not available. Ollama may not be running.");
        }
        return chatModel.call(prompt);
    }
}
