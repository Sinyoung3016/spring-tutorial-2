package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("james");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberRepository.findById(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void join_duplicate() {
        //given
        Member member1 = new Member();
        member1.setName("james");

        Member member2 = new Member();
        member2.setName("james");

        //when
        memberService.join(member1);

        //then
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
    }

    @Test
    void findMembers() {
        //given
        Member member1 = new Member();
        member1.setName("james");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("mark");
        memberRepository.save(member2);

        //when
        List<Member> members = memberService.findMembers();

        //then
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void findOne() {
        //given
        Member member1 = new Member();
        member1.setName("james");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("mark");
        memberRepository.save(member2);

        //when
        Member findMember = memberService.findOne((long) 2).get();

        //then
        assertThat(findMember.getName()).isEqualTo(member2.getName());
    }
}