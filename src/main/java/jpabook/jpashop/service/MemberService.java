package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//읽기에는 readOnly= true를 넣어라
@Transactional(readOnly = true)
@RequiredArgsConstructor //final이 있는 field만 매개변수로 생성자 만듬
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional //쓰기에서는 readOnly 안됨
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검사
        memberRepository.save(member);
        //em.persist를 통해 영속성 컨텍스트에 키 밸류 값으로 저장되는데 이때 key가 바로 pk값이다 여기서는 id
        //따라서 @GeneratedValue 에 의해 id가 생성되고 항상 id가 있는 것이 보장된다
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }//WAS가 여러개가 뜨기 때문에 동시에 같은 이름이 등록될 수 있음. 취약성이 있는 로직임

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name); //영속성으로 관리 되기 때문에 알아서 커밋됨
    }
}
