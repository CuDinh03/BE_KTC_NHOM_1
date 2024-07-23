package ktc.nhom1ktc.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateCategoryEvent extends ApplicationEvent {
    private final String message;

    public CreateCategoryEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}