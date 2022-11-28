package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostedBy {

    private Long id;

    private String email;

    private String username;

    private String imageUrl;

}
