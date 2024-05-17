package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ChatGPPT {

    // Define the API endpoint
    //  private static final String API_ENDPOINT = "https://cheapest-gpt-4-turbo-gpt-4-vision-chatgpt-openai-ai-api.p.rapidapi.com/v1/chat/completions";

    // Define your RapidAPI key
    //  private final String RAPIDAPI_KEY = "2a918cb012msh0cb66835be4e5f9p15e83djsnc799b4ae31cc";

    private static final String API_ENDPOINT = "https://cheapest-gpt-4-turbo-gpt-4-vision-chatgpt-openai-ai-api.p.rapidapi.com/v1/chat/completionsss";


    private final String RAPIDAPI_KEY = "2a918cb012msh0cb66835be4e5f9p15e83djsnc799b4ae31ccsss";

    private final OkHttpClient client = new OkHttpClient();

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField userInputField;

    @FXML
    public void sendMessage() {
        String userInput = userInputField.getText().trim();
        if (!userInput.isEmpty()) {
            // Display user message in chat area
            displayMessage("You: " + userInput);

            // Create the request body
            JSONObject messageJson = new JSONObject();
            messageJson.put("role", "user");
            messageJson.put("content", userInput);

            JSONArray messagesArray = new JSONArray();
            messagesArray.put(messageJson);

            JSONObject requestBodyJson = new JSONObject();
            requestBodyJson.put("messages", messagesArray);
            requestBodyJson.put("model", "gpt-4-turbo-2024-04-09");
            requestBodyJson.put("max_tokens", 100);
            requestBodyJson.put("temperature", 0.9);

            // Build the request body
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBodyJson.toString());

            // Build the request
            Request request = new Request.Builder()
                    .url(API_ENDPOINT)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("X-RapidAPI-Key", RAPIDAPI_KEY)
                    .addHeader("X-RapidAPI-Host", "cheapest-gpt-4-turbo-gpt-4-vision-chatgpt-openai-ai-api.p.rapidapi.com")
                    .build();

            // Execute the request asynchronously
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            String responseBody = response.body().string();
                            JSONObject jsonResponse = new JSONObject(responseBody);
                            JSONArray choices = jsonResponse.getJSONArray("choices");
                            if (choices.length() > 0) {
                                JSONObject firstChoice = choices.getJSONObject(0);
                                JSONObject message = firstChoice.getJSONObject("message");
                                String chatResponse = message.getString("content");
                                // Display chatbot response in chat area
                                displayMessage("ChatGPT: " + chatResponse);
                            } else {
                                displayMessage("Error: No response from the ChatGPT API");
                            }
                        } else {
                            displayMessage("Error: Failed to get response from the ChatGPT API");
                        }
                    } catch (JSONException | IOException e) {
                        displayMessage("Error: Invalid response from the ChatGPT API");
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    displayMessage("Error: Failed to communicate with the ChatGPT API");
                }
            });

            // Clear user input field
            userInputField.clear();
        }
    }

    private void displayMessage(String message) {
        Platform.runLater(() -> chatArea.appendText(message + "\n"));
    }
}
