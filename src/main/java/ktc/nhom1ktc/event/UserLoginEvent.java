package ktc.nhom1ktc.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserLoginEvent extends ApplicationEvent {

    public UserLoginEvent(Object source) {
        super(source);
    }
}
