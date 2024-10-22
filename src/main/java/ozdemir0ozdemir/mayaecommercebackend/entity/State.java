package ozdemir0ozdemir.mayaecommercebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "states")
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = "country")
public class State {

    @Id
    @SequenceGenerator(name = "states_id_gen", sequenceName = "states_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "states_id_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "state_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
