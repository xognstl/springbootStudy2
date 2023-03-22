package hello.core.order;

public interface OrderService {
    // 클라이언트가 주문생성할때 파라미터를 넘기고 주문결과를 반환한다. 1,4
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
