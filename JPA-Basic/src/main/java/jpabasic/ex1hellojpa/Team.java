package jpabasic.ex1hellojpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    List<Member2> members = new ArrayList<Member2>();


/*
    연관관계 편의 메소드
    public void addMember(Member2 member2) {
        member2.setTeam(this);
        members.add(member2);
    }*/
}
