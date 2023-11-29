/**
 * The Transcript class represents a data structure for storing information related to transcription.
 * It includes fields for the audio URL, transcript ID, transcription status, transcribed text, and language code.
 */
public class Transcript {

    // Fields for storing transcription-related information.
    private String audio_url;       // URL of the audio file to be transcribed.
    private String id;              // Unique identifier for the transcription.
    private String status;          // Status of the transcription process (e.g., "completed", "in-progress").
    private String text;            // Transcribed text generated from the audio file.
    private String language_code;   // Code representing the language of the transcribed text.

    // Getter and Setter methods for the language code.
    public String getLanguage_code() {
        return language_code;
    }
    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    // Getter and Setter methods for the audio URL.
    public String getAudio_url() {
        return audio_url;
    }
    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    // Getter and Setter methods for the transcript ID.
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter methods for the transcription status.
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    // Getter and Setter methods for the transcribed text.
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
