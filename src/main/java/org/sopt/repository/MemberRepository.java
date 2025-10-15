package org.sopt.repository;

import org.sopt.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    List<Member> findAll();
    void deleteById(Long id);
    boolean existById(Long id);
    boolean existsByEmail(String email);
    Long nextId();
}
