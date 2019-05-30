package app.api;

public interface ApiElement<KEY extends ApiKey> {
    public abstract KEY getKey();
    public abstract String getTopic();
}
