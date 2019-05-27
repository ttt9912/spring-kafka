package app.api;

public interface ApiElement<KEY extends ApiKey> {
    KEY getKey();
    String getTopic();
}
