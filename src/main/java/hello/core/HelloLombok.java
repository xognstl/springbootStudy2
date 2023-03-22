package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString // hellolombok을 그냥 출력해도 스트링으로 반환
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("태훈");

        String name = helloLombok.getName();
        System.out.println("name = " + name);

        System.out.println(helloLombok);
    }
}
