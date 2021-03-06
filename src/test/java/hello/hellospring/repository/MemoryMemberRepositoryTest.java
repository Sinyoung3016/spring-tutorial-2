package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("sinyoung");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("mike");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("jason");
        repository.save(member2);

        Member result = repository.findByName("jason").get();
        assertThat(member2).isEqualTo(result);
    }

    @Test
    public void findByID() {
        Member member1 = new Member();
        member1.setName("mike");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("jason");
        repository.save(member2);

        Member result = repository.findById((long) 1).get();
        assertThat(member1.getName()).isEqualTo(result.getName());

    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("mike");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("jason");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
