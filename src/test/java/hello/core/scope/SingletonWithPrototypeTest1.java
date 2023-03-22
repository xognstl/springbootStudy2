package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

/*
* 프로토타입 스코프 - 싱글톤 빈과 함께 사용시 문제점
* 스프링 컨테이너에 프로토타입 스코프를 빈이 요청하면 항상 새로운 객체 인스턴스를 생성해서 반환한다.
* prototypeFind() 에서는 스프링 컨테이너에 직접 프로토타입을 요청함.
* 클라이언트가 프로토타입 요청시 새로운 빈을 생성해서 반환해주는걸 알 수 있다.
*
* singletonClientUsePrototype() 에서는 싱글톤 빈과 함께 프로토타입 빈을 사용한다.
* 스프링컨테이너 생성 -> 의존 주입 후 주입 시점에 프로토타입도 같이 요청
* 클라이언트가 프로토타입 빈을 내부 필드에 보관함(참조값)
* 클라이언트 내부의 프로토타입 빈은 이미 주입 시점에 주입이 끝난 빈
* 싱글톤과 함께 사용하면 생성시점에 의존관계 주입을 받고 프로토타입은 싱글톤빈과 함께 계속 유지 된다.
*
* 이런 문제점은 provider를 사용하면 됨.
* */
public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count1).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean {
        //싱글톤 프로토타입 같이
//        private final PrototypeBean prototypeBean; // 생성시점에 주입
//
//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }
//
//        public int logic() {
//            prototypeBean.addCount();
//            int count = prototypeBean.getCount();
//            return count;
//        }

        /*
        * 싱글톤 빈과 프로토타입 빈을 함께 사용 시 항상 새로운 프로토타입 빈을 만드는 방법.
        * 간단하게는 logic() 함수 안에 PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class); 처럼
        * 프로토타입 빈을 사용할 때마다 스프링컨테이너에 새로 요청 하는것.
        * 이렇게 하면 스프링 컨테이너에 종속적인 코드가 되고 단위테스트가 어려워짐.
        * 그래서 ObjectProvider 사용
        * getObject 호출 할때마다 해당 빈 찾아서 반환
        * */
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
//
//        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
//            //getObject 호출 시 그때서야 스프링컨테이너에서 프로토타입을 찾아서 반환
//            prototypeBean.addCount();
//            int count = prototypeBean.getCount();
//            return count;
//        }

        /* JSR-330 Provider 사용
        * 라이브러리 가져와야하는게 귀찮음.
        * */
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            //getObject 호출 시 그때서야 스프링컨테이너에서 프로토타입을 찾아서 반환
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
