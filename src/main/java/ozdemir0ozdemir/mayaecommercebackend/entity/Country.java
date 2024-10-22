package ozdemir0ozdemir.mayaecommercebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "countries")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "states")
@ToString(exclude = "states")
public class Country {

    @Id
    @SequenceGenerator(name = "countries_id_gen", sequenceName = "countries_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "countries_id_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "country_id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<State> states;

}
