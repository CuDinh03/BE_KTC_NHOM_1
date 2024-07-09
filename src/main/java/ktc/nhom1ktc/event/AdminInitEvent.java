package ktc.nhom1ktc.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AdminInitEvent extends ApplicationEvent {
    private final String message;

    public AdminInitEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
