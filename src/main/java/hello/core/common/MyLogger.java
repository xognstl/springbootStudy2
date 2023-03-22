package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

/*
* request 빈은 고객이 요청이 들어와서 나갈때까지만 살아있음.
* 그래서 에러가남 .=> provider 로  사용
*
* 프록시 사용 시 MyLogger의 가짜 프록시 클래스를 만들어 HTTP request와 상관없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘수 있다.
*
* */
@Component
//@Scope(value = "request") // ObjectProvider 사용시 이렇게
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }

}
