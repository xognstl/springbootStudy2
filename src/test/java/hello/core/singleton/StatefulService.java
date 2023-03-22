package hello.core.singleton;

public class StatefulService {

    private int price; // 상태를 유지하는 필드

    public void order(String name, int price) {
        System.out.println("name = " + name + " :: price = " + price);
        this.price = price; // 여기가 문제
    }
    // 이렇게 하면 test에 있는 문제점이 발생하지 않는다.
//      전역변수도 주석
//    public int order(String name, int price) {
//        System.out.println("name = " + name + " :: price = " + price);
//        return price;
//    }

    public int getPrice(){
        return price;
    }
}
