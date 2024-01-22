package net.unir.missi.desarrollowebfullstack.bookabook.data.model.api;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ClientDto {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;
}
