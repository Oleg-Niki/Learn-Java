package guessinggame;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GuessingGameHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final int SECRET_NUMBER = new Random().nextInt(10) + 1;

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        try {
            // Extract the body from the input (function URL includes it here)
            String bodyJson = (String) input.get("body");
            if (bodyJson == null) {
                // No body provided
                response.put("statusCode", 400);
                response.put("headers", headers);
                response.put("body", "{\"error\":\"Missing request body\"}");
                return response;
            }

            // Parse JSON body
            Map<String, Object> requestBody = MAPPER.readValue(bodyJson, new TypeReference<Map<String,Object>>(){});

            // Get action from parsed body
            String action = (String) requestBody.get("action");

            if ("init".equals(action)) {
                Map<String, Object> responseBody = Map.of(
                        "message", "Game initialized! Guess a number between 1 and 100.",
                        "status", "ready"
                );
                response.put("statusCode", 200);
                response.put("headers", headers);
                response.put("body", MAPPER.writeValueAsString(responseBody));

            } else if ("guess".equals(action)) {
                int guess = Integer.parseInt(requestBody.get("guess").toString());

                String message;
                String status;
                if (guess < SECRET_NUMBER) {
                    message = "Too low! Try again.";
                    status = "continue";
                } else if (guess > SECRET_NUMBER) {
                    message = "Too high! Try again.";
                    status = "continue";
                } else {
                    message = "Congratulations! You guessed the correct number!";
                    status = "win";
                }

                Map<String, Object> responseBody = Map.of(
                        "message", message,
                        "status", status
                );
                response.put("statusCode", 200);
                response.put("headers", headers);
                response.put("body", MAPPER.writeValueAsString(responseBody));

            } else {
                // Invalid action
                Map<String, Object> responseBody = Map.of("error", "Invalid action. Use 'init' or 'guess'.");
                response.put("statusCode", 400);
                response.put("headers", headers);
                response.put("body", MAPPER.writeValueAsString(responseBody));
            }

        } catch (JsonProcessingException e) {
            try {
                String errorBody = MAPPER.writeValueAsString(Map.of("error", "JSON Processing Error: " + e.getMessage()));
                response.put("statusCode", 500);
                response.put("headers", headers);
                response.put("body", errorBody);
            } catch (JsonProcessingException ex) {
                response.put("statusCode", 500);
                response.put("headers", headers);
                response.put("body", "{\"error\":\"Internal Server Error\"}");
            }

        } catch (Exception e) {
            try {
                String errorBody = MAPPER.writeValueAsString(Map.of("error", "Internal Server Error: " + e.getMessage()));
                response.put("statusCode", 500);
                response.put("headers", headers);
                response.put("body", errorBody);
            } catch (JsonProcessingException ex) {
                response.put("statusCode", 500);
                response.put("headers", headers);
                response.put("body", "{\"error\":\"Internal Server Error\"}");
            }
        }

        return response;
    }
}
