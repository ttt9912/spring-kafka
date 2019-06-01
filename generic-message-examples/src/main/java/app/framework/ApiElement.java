package app.framework;

public interface ApiElement<KEY extends ApiKey> {
    KEY getKey();

    String getTopic();
}
