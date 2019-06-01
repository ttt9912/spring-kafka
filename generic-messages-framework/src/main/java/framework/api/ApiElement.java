package framework.api;

public interface ApiElement<KEY extends ApiKey> {
    KEY getKey();

    String getTopic();
}
