package ktc.nhom1ktc.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateAccountEvent extends ApplicationEvent {
    private final String message;

    public CreateAccountEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}