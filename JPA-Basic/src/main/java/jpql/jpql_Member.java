package jpql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NamedQuery( name= "jpql_Member.findByUsername",
             query = "select m from jpql_Member m where m.username = :username"
)
public class jpql_Member {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne
    @JoinColumn(name = "JPQL_TEAM_ID")
    private jpql_Team team;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    public void changeTeam(jpql_Team team){
        this.team = team;
        team.getMembers().add(this);
    }

    @Override
    public String toString() {
        return "jpql_Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
