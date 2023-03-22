package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
        // 스프링은 모든게 ApplicationContext로 시작함. 스프링 컨테이너라고 생각하면됨.
        // 객체를 관리, @Bean 같은거 .
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 기존에는 appConfig 에서 직접 찾아왔지만 스프링 컨테이너에서 찾아옴.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        // appConfig 사용
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
        // 의존주입 하기전엔 이렇게 구현체를 직접 가져옴
//        MemberService memberService = new MemberServiceImpl();
        Member member =  new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
