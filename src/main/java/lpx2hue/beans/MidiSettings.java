package lpx2hue.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by nijhora1 on 15/06/16.
 */
@Component
public class MidiSettings {

    @Value("${midi.input}")
    private String input;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
