package ktc.nhom1ktc.dto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String name, lastName, username, mail;
    String token;
    boolean authenticated; // true = dung u,p // ngc lai
}
