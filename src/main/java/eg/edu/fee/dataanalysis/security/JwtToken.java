package eg.edu.fee.dataanalysis.security;

import eg.edu.fee.dataanalysis.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Length;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jwt_token")
public class JwtToken {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, length = Length.LONG)
    private String token;
    private boolean expired;
    private boolean revoked;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
