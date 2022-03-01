package hello.hellospring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;

class MemberServiceTest {

	MemberService memberService;
	MemoryMemberRepository memberRepository;
	
	@BeforeEach
	public void beforeEach() {
		memberRepository  = new MemoryMemberRepository();
		memberService = new MemberService(memberRepository);
	}
	
	@AfterEach
	public void afterEach() {
		memberRepository.clearStore();
	}
	
	@Test
	void 회원가입() {
		Member member = new Member();
		member.setName("hello");
		
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
	
	@Test
	void findMembers() {
		Member member1 = new Member();
		member1.setName("member1");
		
		Member member2 = new Member();
		member2.setName("member2");
		
		List<Member> members = memberService.findMembers();
		
		
	}
	
	@Test
	void findOne() {
		
	}

}
