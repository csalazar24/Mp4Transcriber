/**
 * The Main class is the entry point of the application, where the transcription and translation process are initiated.
 * It uses Gson for JSON processing and communicates with a remote API to perform transcription and translation tasks.
 */
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        // Create a Transcript object to store transcribed data.
        Transcript transcript = new Transcript();

        // Initialize Gson for JSON processing.
        Gson gson = new Gson();

        // Display instructions to the user.
        Console.printInstructions();

        // Prompt the user for input.
        int userInput = Console.readNumber("Number: ", 1, 2);

        // Create an instance of ApiRequestManager for handling API requests.
        ApiRequestManager apiManager = new ApiRequestManager(transcript, gson);

        // Generate JSON body based on user input.
        String jsonBody = apiManager.getJsonBody(userInput);

        // Create a POST request with the generated JSON body.
        HttpRequest completedPostRequest = apiManager.makePostRequest(jsonBody);

        // Send the POST request and retrieve the response.
        HttpResponse<String> postResponse = apiManager.getPostResponse(completedPostRequest);

        // Create a GET request the id we got back from the POST response.
        HttpRequest completedGetRequest = apiManager.makeGetRequest(postResponse);

        // Initiate transcription by sending the GET request.
        apiManager.startTranscription(completedGetRequest);

        // Retrieve the transcribed data.
        transcript = apiManager.getTranscript();

        // Create a TranslationManager for translating the transcribed data.
        TranslationManager translationManager = new TranslationManager(transcript, userInput);

        // Display the translated result to the user.
        System.out.println(translationManager.Translate());
    }
}
