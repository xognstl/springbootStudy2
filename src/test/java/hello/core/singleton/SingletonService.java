package hello.core.singleton;

public class SingletonService {
    /*
    * 싱글톤 패턴
    * 클래스 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴
    * 한 JVM 서버 안에서는 객체 인스턴스가 1개만 생성되게 하는것.
    * 그래서 객체 인스턴스를 2개 이상 생성하지 못하도록 막아야 한다.
    * */

    // 자기 자신을 내부에 private로 하나 가지고 있는다.
    // 1. static 영역에 객체를 딱 1개만 생성된다.
    private static final SingletonService instance = new SingletonService();

    //2. public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용한다.
    public static SingletonService getInstance() {
        return instance;
    }

    // 밖에서(다른 클래스) 이렇게 하면 위에 처럼 해놔도 이렇게 하면 2개 생성 가능
//    public static void main(String[] args) {
//        SingletonService singletonService1 = new SingletonService();
//        SingletonService singletonService2 = new SingletonService();
//    }

    //3. 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

    /*
    * 자바가 뜨면서 static영역에 있는 SingletonService instance를 초기화하면서 new를 생성해서 가지고 있고,
    * instance에 참조를 가져올 수 있는 방법은  return instance밖에 없다.
    * SingletonService를 생성 할 수 있는 곳은 없다.
    * */
}
