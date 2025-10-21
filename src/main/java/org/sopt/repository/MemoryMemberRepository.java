package org.sopt.repository;

import org.sopt.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static final Map<Long, Member> store = new HashMap<>();
    private Long sequence = 1L;


    public Long nextId(){
        return sequence++;
    }

    public Member save(Member member) {

        store.put(member.getId(), member);
        return member;

    }


    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }


    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public boolean existById(Long id) {
        return store.containsKey(id);
    }

    public boolean existsByEmail(String email) {
        return store.values().stream().anyMatch(member -> member.getEmail().equals(email));
    }
}
