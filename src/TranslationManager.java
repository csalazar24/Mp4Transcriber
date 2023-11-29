/**
 * The TranslationManager class facilitates the translation of transcribed text using the Google Cloud Translation API.
 * It requires a Transcript object containing the text to be translated and a user input indicating the target language.
 * The translation is performed based on the specified target language and returned as a String.
 */
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.TranslateOptions;

public class TranslationManager {

    // Fields for storing the Transcript object and user input.
    private Transcript transcript;
    private int userInput;

    // Google Cloud Translation API key for authentication.
    private final String ApiKeyGCT = "key";  // Replace with the actual API key.

    /**
     * Constructs a TranslationManager object with the provided Transcript and user input.
     *
     * @param transcript The Transcript object containing text to be translated.
     * @param userInput  An integer representing user input for the target language (1 or 2).
     */
    public TranslationManager(Transcript transcript, int userInput) {
        this.transcript = transcript;
        this.userInput = userInput;
    }

    /**
     * Translates the text from the Transcript object based on the user's request.
     *
     * @return A String representing the translated text, or a message indicating that translation did not work.
     */
    public String Translate() {

        // Create a Translate service using the Google Cloud Translation API key.
        Translate translate = TranslateOptions.newBuilder().setApiKey(ApiKeyGCT).build().getService();

        // Perform translation based on the user's specified target language.
        Translation translation;
        if (userInput == 1) {
            translation = translate.translate(transcript.getText(), Translate.TranslateOption.targetLanguage("en"));
            return translation.getTranslatedText();
        } else if (userInput == 2) {
            translation = translate.translate(transcript.getText(), Translate.TranslateOption.targetLanguage("es"));
            return translation.getTranslatedText();
        }

        // Return a message indicating that translation did not work for any reason.
        return "Translation did not work";
    }
}
