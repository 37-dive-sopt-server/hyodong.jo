package org.sopt.domain;

public class Member {

    private Long id;
    private String name;
    private String birth;
    private String email;
    private Gender gender;

    public enum Gender {
        MALE("남성"),
        FEMALE("여성");

        private String label;

        Gender(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public Member(Long id, String name, String birth, String email, Gender gender) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.email = email;
        this.gender = gender;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }
}
