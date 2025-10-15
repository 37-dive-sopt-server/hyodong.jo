package org.sopt;

import org.sopt.controller.MemberController;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.exception.custom.AgeException;
import org.sopt.exception.custom.DuplicateEmailException;
import org.sopt.exception.custom.MemberNotFoundException;
import org.sopt.exception.validator.MemberValidator;
import org.sopt.repository.MemoryMemberRepository;
import org.sopt.service.MemberServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {
    public static void main(String[] args) {

        MemberController memberController = new MemberController();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n✨ --- DIVE SOPT 회원 관리 서비스 --- ✨");
            System.out.println("---------------------------------");
            System.out.println("1️⃣. 회원 등록 ➕");
            System.out.println("2️⃣. ID로 회원 조회 🔍");
            System.out.println("3️⃣. 전체 회원 조회 📋");
            System.out.println("4️⃣. 회원 삭제 🗑️");
            System.out.println("5️⃣. 종료 🚪");
            System.out.println("---------------------------------");
            System.out.print("메뉴를 선택하세요: ");

            String choice = scanner.nextLine();
            String name;
            switch (choice) {
                case "1":
                    while(true) {
                        try {
                            System.out.print("등록할 회원 이름을 입력하세요: ");
                            name = scanner.nextLine();
                            MemberValidator.validateName(name);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("⚠️ " + e.getMessage());
                        }
                    }

                    String birth;
                    System.out.print("회원님의 생년월일을 입력하세요(yyyy-MM-dd 형식):");
                    while(true){
                        try{
                            birth = scanner.nextLine();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate birthDate = LocalDate.parse(birth, formatter);
                            if(birthDate.isAfter(LocalDate.now())){
                                System.out.print("⚠️ 올바른 생년월일을 입력해주세요(yyyy-MM-dd 형식):");
                                continue;
                            }
                            int age = LocalDate.now().getYear() - birthDate.getYear();
                            if(age < 20){
                                System.out.print("❌ 19세 이하는 가입할 수 없습니다. 다시 입력해주세요(yyyy-MM-dd 형식):");
                                continue;
                            }
                            break;
                        }
                        catch(DateTimeParseException e){
                            System.out.print("⚠️ 형식이 올바르지 않습니다. 'yyyy-MM-dd' 형식으로 다시 입력해주세요:");
                        }
                    }

                    String email;
                    System.out.print("회원님의 이메일을 입력하세요: ");
                    while(true) {
                        try {
                            email = scanner.nextLine();
                            MemberValidator.validateEmailFormat(email);
                            if (memberController.existsByEmail(email)) {
                                System.out.print("⚠️ 이미 등록된 이메일입니다. 다른 이메일을 입력해주세요: ");
                                continue;
                            }
                            break;
                        } catch (IllegalArgumentException e) {
                                System.out.print("⚠️ " + e.getMessage());
                        }
                    }

                    System.out.println("회원님의 성별을 선택 해주세요");
                    System.out.print("1번은 남성, 2번은 여성입니다. 1 또는 2를 입력해주세요(숫자만 입력해주세요): ");
                    Gender gender;
                    while(true){
                        String input = scanner.nextLine();
                        if(input.equals("1")){
                            gender = Gender.MALE;
                            break;
                        }
                        else if(input.equals("2")){
                            gender = Gender.FEMALE;
                            break;
                        }
                        else{
                            System.out.println("⚠️ 잘못된 입력 값입니다. 1 또는 2를 입력해주세요.");
                        }
                    }

                    try{
                        Long createdId = memberController.createMember(name,birth,email,gender);
                            System.out.println("✅ 회원 등록 완료 (ID: " + createdId + ")");
                        } catch(DuplicateEmailException e){
                        System.out.println("⚠️ " + e.getMessage());
                    } catch(AgeException e){
                        System.out.println("❌ " + e.getMessage());
                    }

                    break;

                case "2":
                    System.out.print("조회할 회원 ID를 입력하세요: ");
                    try {
                        Long id = Long.parseLong(scanner.nextLine());
                        Optional<Member> foundMember = memberController.findMemberById(id);
                        if (foundMember.isPresent()) {
                            System.out.println("✅ 조회된 회원: ID=" + foundMember.get().getId() + ", 이름=" + foundMember.get().getName() +
                            ", 생년월일=" + foundMember.get().getBirth() + ", 이메일=" + foundMember.get().getEmail() + ", 성별=" + foundMember.get().getGender().getLabel());
                        } else {
                            System.out.println("⚠️ 해당 ID의 회원을 찾을 수 없습니다.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ 유효하지 않은 ID 형식입니다. 숫자를 입력해주세요.");
                    }
                    break;

                case "3":
                    List<Member> allMembers = memberController.getAllMembers();
                    if (allMembers.isEmpty()) {
                        System.out.println("ℹ️ 등록된 회원이 없습니다.");
                    }
                    else {
                        System.out.println("--- 📋 전체 회원 목록 📋 ---");
                        for (Member member : allMembers) {
                            System.out.println("👤 ID=" + member.getId() + ", 이름=" + member.getName()+
                                    ", 생년월일=" + member.getBirth() + ", 이메일=" + member.getEmail() + ", 성별=" + member.getGender().getLabel());
                        }
                        System.out.println("--------------------------");
                    }
                    break;

                case "4":
                    System.out.print("삭제할 회원 ID를 입력하세요: ");
                    try {
                        Long id = Long.parseLong(scanner.nextLine());
                        memberController.deleteMemberById(id);
                        System.out.println("✅회원 삭제(ID="+id+")가 정상적으로 완료되었습니다.");
                    }
                    catch (NumberFormatException e) {
                        System.out.println("❌ 유효하지 않은 ID 형식입니다. 숫자를 입력해주세요.");
                    }
                    catch (MemberNotFoundException e) {
                        System.out.println("⚠️" + e.getMessage());
                    }
                    break;

                case "5":
                    System.out.println("👋 서비스를 종료합니다. 안녕히 계세요!");
                    scanner.close();
                    return;
                default:
                    System.out.println("🚫 잘못된 메뉴 선택입니다. 다시 시도해주세요.");
            }
        }
    }
}