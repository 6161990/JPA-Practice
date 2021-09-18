package jpabasic.ex1hellojpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

/*
    @Column(name = "TEAM_ID")
    private Long teamId;
*/
    @ManyToOne(fetch = FetchType.EAGER) //프록시 객체로 조회함
    @JoinColumn(name="TEAM_ID")
    private Team team;

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    /*
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products= new ArrayList<>(); */


    /*
    private int age;

    @Column(precision = 30)
    private BigDecimal testAge;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Transient
    private int temp;

    @Lob
    private String description;
*/



}
