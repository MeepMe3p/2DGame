package javafx_plus_willpower.record;

public record ChatConfig(
        String fxmlFilePath,
        String title
){
    private ChatConfig() {
        this(
                "/javafx_plus_willpower/Chat.fxml",
                "Chat Box"
        );
    }
    public static ChatConfig createWithDefaults() {
        return new ChatConfig();
    }
}
