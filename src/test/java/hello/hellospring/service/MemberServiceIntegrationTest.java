package hello.hellospring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

@SpringBootTest //스프링 컨테이너와 함께 테스트를 함.
//@Transactional //테스트 케이스에 있을 때는 테스트를 끝내고 데이터를 롤백함. 모든 테스트를 정상적으로 진행하기 위함.
class MemberServiceIntegrationTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	
	@Test
	@Commit
	void 회원가입() {
		Member member = new Member();
		member.setName("spring101");
		
		Long saveId = memberService.join(member);
		//Long result2 = memberService.join(member);
		
		Member findMember = memberService.findOne(saveId).get();
		assertThat(findMember.getName()).isEqualTo(member.getName());
		
	}
	
	@Test
	void 중복_회원_예외() {
		Member member1 = new Member();
		member1.setName("hello");
		Member member2 = new Member();
		member2.setName("hello");
		
		memberService.join(member1);
		
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
		
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
	}

}
