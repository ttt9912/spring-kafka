package springkafka.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Greeting {
    public static final String GREETING_TOPIC = "greeting";

    private String msg;
    private String name;
}
