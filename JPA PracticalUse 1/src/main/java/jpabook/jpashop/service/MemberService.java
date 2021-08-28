package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
// JPA의 모든 데이터 로직이나 변경들은 트랜잭션안에서 실행되어야함.
// readOnly = true : JPA가 조회하는 곳에서는 성능을 최적화 : 영속성 컨텍스트를 더티체킹을 안하며, 읽기 전용이니까 리소스 많이 쓰지 말고 단순하게 그냥 읽어 라고 지시
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * */
    @Transactional // readonly없이 따로 설정은 해당 메소드에서 우선권가짐.
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);  //jpa에서 엔티티매니저가 persist를 하면 순간적으로 영속성 컨텍스트가 넘어간 값을 올림(member).
        // 그 때, key랑 value 형식으로 넘어가는데 그 키를 엔티티의 id 값(pk)로 사용함 . 그래서 id값이 생성되어있는것이 보장됨.
        // 따라서 아직 db에 들어간 시점이 아닌데도 member.getId를 꺼내올 수 있음
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findbyName = memberRepository.findByName(member.getName());
        //사실상 문제가 있음. 영시 영분 영초에 동시에 가입을 한다면 ? 최후의 방어가 필요. 멀티쓰레드를 고려해 마지막 검증문으로 멤버의 이름을 유니크 제약조건으로 거는 방법 등을 적용해야함
        if(!findbyName.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     * */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
