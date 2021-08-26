package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional //이게 test 케이스에 있으면 테스트하고 바로 롤백을 해버림
    @Rollback(value = false) //이렇게하면 테스트지만 커밋해줌
    public void test_1() {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // 오류남 No EntityManager with actual transaction available
        // 트랜잭션이 없다는 뜻 -> entity manager를 통한 모든 데이터 변경은 항상 트랜잭션 안에서 이루어져야함

        Assertions.assertThat(findMember).isEqualTo(member);
        // 같은 Transaction안에서 저장하고 조회하면 영속성 컨텐스트가 같음 (id)같으므로
        //영속성 컨텍스트안에 그  id로 관리되는 대상이 존재하므로, 같다고 나옴. 1차 캐쉬에서 꺼내짐

    }
}