package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        //스캔 대상 제외
)
// 컴포넌트 스캔이란 스프링 빈을 가져와 등록해주는것.
//컴포넌트스캔 어노테이션은 @Component 붙은 클래스를 찾아서 자동으로 스프링빈에 등록
public class AutoAppConfig {

//    @Bean(name = "memoryMemberRepository")
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}

