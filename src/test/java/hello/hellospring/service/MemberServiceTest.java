package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService = new MemberService();
    MemoryMemberRepository serviceRepository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        serviceRepository.clearStore();
    }

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("james");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void join_duplicate(){
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

        /*try{
            memberService.join(member2);
            fail();
        }catch(IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        }*/
    }

}