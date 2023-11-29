import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * The ApiRequestManager class manages communication with the AssemblyAI API for transcription tasks.
 * It handles creating and sending HTTP requests, processing responses, and monitoring the transcription status.
 */
public class ApiRequestManager {

    // Fields for managing the Transcript object, Gson for JSON processing, and HTTP client.
    private Transcript transcript;
    private Gson gson;
    private HttpClient httpClient;

    // API endpoint and key for AssemblyAI.
    private String endpoint = "https://api.assemblyai.com/v2/transcript";
    private final String ApiKeyAI = "key";  // Replace with the actual API key.

    /**
     * Constructs an ApiRequestManager object with the provided Transcript and Gson instances.
     *
     * @param transcript The Transcript object to manage for transcription data.
     * @param gson       The Gson instance for JSON processing.
     */
    public ApiRequestManager(Transcript transcript, Gson gson) {
        this.transcript = transcript;
        this.gson = gson;
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Generates a JSON body based on user input for audio URL and language code.
     *
     * @param userInput An integer representing user input for audio selection (1 or 2).
     * @return A String representing the JSON body for the API request.
     */
    public String getJsonBody(int userInput) {
        // Set audio URL and language code based on user input.
        if (userInput == 1) {
            transcript.setAudio_url("https://github.com/csalazar24/Mp4Transcriber/blob/main/Songs/despierto.mp4?raw=true");
            transcript.setLanguage_code("es");
        } else if (userInput == 2) {
            transcript.setAudio_url("https://github.com/csalazar24/Mp4Transcriber/blob/main/Songs/tummy.mp4?raw=true");
            transcript.setLanguage_code("en_us");
        }

        // Convert Transcript object to JSON.
        return gson.toJson(transcript);
    }

    /**
     * Creates a POST request with the provided JSON body.
     *
     * @param jsonBody A String representing the JSON body for the POST request.
     * @return An HttpRequest object for the POST request.
     * @throws URISyntaxException If the URI is invalid.
     */
    public HttpRequest makePostRequest(String jsonBody) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(endpoint))
                .header("Authorization", ApiKeyAI)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
    }

    /**
     * Creates a GET request using information from the provided response.
     *
     * @param response The HttpResponse containing information about the transcribed data.
     * @return An HttpRequest object for the GET request.
     * @throws URISyntaxException If the URI is invalid.
     * @throws InterruptedException If the thread is interrupted during sleep.
     * @throws IOException If an I/O error occurs.
     */
    public HttpRequest makeGetRequest(HttpResponse<String> response)
            throws URISyntaxException, InterruptedException, IOException {
        // Extract Transcript information from the response.
        transcript = gson.fromJson(response.body(), Transcript.class);

        // Create a GET request with the transcript ID.
        return HttpRequest.newBuilder()
                .uri(new URI(endpoint + "/" + transcript.getId()))
                .header("Authorization", ApiKeyAI)
                .GET()
                .build();
    }

    /**
     * Sends a POST request and retrieves the response.
     *
     * @param postRequest An HttpRequest object representing the POST request.
     * @return The HttpResponse containing the response from the POST request.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the thread is interrupted during sleep.
     */
    public HttpResponse<String> getPostResponse(HttpRequest postRequest)
            throws IOException, InterruptedException {
        return httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Initiates and monitors the transcription process using a GET request.
     *
     * @param getRequest An HttpRequest object representing the GET request for monitoring the transcription.
     * @throws InterruptedException If the thread is interrupted during sleep.
     * @throws IOException If an I/O error occurs.
     */
    public void startTranscription(HttpRequest getRequest) throws InterruptedException, IOException {
        while (true) {
            // Send a GET request and retrieve the response.
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            // Update the Transcript object based on the response.
            transcript = gson.fromJson(getResponse.body(), Transcript.class);

            // Print the transcription status.
            System.out.println(transcript.getStatus());

            // Check if transcription is completed or an error occurred.
            if ("completed".equals(transcript.getStatus()) || "error".equals(transcript.getStatus())) {
                System.out.println("Transcription Completed!");
                System.out.println(transcript.getText());
                break;
            }

            // Sleep for 1 second before checking the status again.
            Thread.sleep(1000);
        }
    }
}
