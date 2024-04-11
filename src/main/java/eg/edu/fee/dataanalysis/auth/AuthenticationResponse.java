package eg.edu.fee.dataanalysis.auth;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {

}
