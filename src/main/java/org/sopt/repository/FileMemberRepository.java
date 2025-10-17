package org.sopt.repository;


import org.sopt.domain.Member;

import java.io.*;
import java.util.*;

public class FileMemberRepository implements MemberRepository {

    private static final String FILE_PATH = "members.dat";
    private final Map<Long, Member> store = LoadFromFile();
    private long sequence;

    public FileMemberRepository() {
        this.sequence = store.keySet().stream().mapToLong(Long::longValue).max().orElse(0) + 1;
    }

    @Override
    public Long nextId() {
        return sequence++;
    }

    @Override
    public Member save(Member member) {
        store.put(member.getId(), member);
        saveToFile();
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
        saveToFile();
    }

    @Override
    public boolean existById(Long id) {
        return store.containsKey(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return store.values().stream().anyMatch(member -> member.getEmail().equals(email));
    }

    private Map<Long, Member> LoadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (Map<Long, Member>) ois.readObject();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(store);
        } catch (IOException e) {
            throw new RuntimeException("저장 중 오류 발생");
        }
    }


}
