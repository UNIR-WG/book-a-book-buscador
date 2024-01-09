package net.unir.missi.desarrollowebfullstack.bookabook.data.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Author {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String nationality;
    private String email;
    private String webSite;
    private String biography;



    public boolean isValid(){
        return !Objects.equals(this.firstName, "") && !Objects.equals(this.lastName, "")
                && this.birthDate!=null && !Objects.equals(this.email, "")
                && !Objects.equals(this.nationality, "");

    }

}