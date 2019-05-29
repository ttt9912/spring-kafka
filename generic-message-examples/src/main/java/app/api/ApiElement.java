package app.api;

import java.io.Serializable;

public abstract class ApiElement<KEY extends ApiKey> implements Serializable {
    public abstract KEY getKey();
    public abstract String getTopic();
}
