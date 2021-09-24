package jpabasic.ex1hellojpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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

    // 기간 Period
    @Embedded
    private Period workPeriod;
    /* private LocalDateTime startDate;
    private LocalDateTime endDate;*/


    // 주소 Address
    @Embedded
    private Address homeAddress;
    /*private String city;
    private String street;
    private String zipcode;*/

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

/*    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns =
        @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressHistory = new ArrayList<>();*/

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

/*    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "city",
                                column=@Column(name ="WORK_CITY")),
                        @AttributeOverride(name = "street",
                                column=@Column(name = "WORK_STREET")),
                        @AttributeOverride(name = "zipcode",
                                column=@Column(name = "WORK_ZIPCODE"))
    })
    private Address workAddress;*/

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
