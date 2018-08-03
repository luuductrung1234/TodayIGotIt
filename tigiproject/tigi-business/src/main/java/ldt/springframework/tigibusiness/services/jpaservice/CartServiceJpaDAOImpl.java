package ldt.springframework.tigibusiness.services.jpaservice;

import ldt.springframework.tigibusiness.domain.*;
import ldt.springframework.tigibusiness.enums.OrderStatus;
import ldt.springframework.tigibusiness.repository.CartRepository;
import ldt.springframework.tigibusiness.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/23/18
 */


@Service
@Profile("jpadao")
public class CartServiceJpaDAOImpl
    implements CartService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CartRepository cartRepository;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        return cartRepository.listAll();
    }

    @Override
    public Cart getById(Integer id) {
        return cartRepository.getById(id);
    }


    @Override
    public Cart saveOrUpdate(Cart cart) {
        return cartRepository.saveOrUpdate(cart);
    }

    @Override
    public void delete(Integer id) {
        cartRepository.delete(id);
    }


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public int getContentCount(User user) {
        int count = 0;
        for (CartDetails cd:
                user.getCart().getCartDetails()) {
            count += cd.getQuantity();
        }

        return count;
    }

    @Override
    public void addToCart(User user, Course course){
        for (CartDetails cd:
                user.getCart().getCartDetails()) {
            if(cd.getCourse().getId().equals(course.getId())){
                int quantity = cd.getQuantity() + 1;
                cd.setQuantity(quantity);
                return;
            }
        }

        user.getCart().addCartDetail(new CartDetails(1, course));
    }

    @Override
    public void removeFromCart(User user, Integer courseId) {
        for (CartDetails cd:
                user.getCart().getCartDetails()) {
            if(cd.getCourse().getId().equals(courseId)){
                int quantity = cd.getQuantity() - 1;

                if(quantity == 0){
                    user.getCart().removeCartDetail(cd);
                }else{
                    cd.setQuantity(quantity);
                }
                return;
            }
        }
    }

    @Override
    public Order convertCartToOrder(Cart cart, User user) {

        Order result = new Order(user.getCustomer().getShippingAddress(), null, OrderStatus.NEW);
        for (CartDetails cd:
             cart.getCartDetails()) {
            result.addOrderDetails(new OrderDetails(cd.getQuantity(), cd.getCourse()));
        }

        return result;
    }

    @Override
    public BigDecimal calculateTotalPrice(Cart cart){
        BigDecimal total = new BigDecimal(0);
        for (CartDetails cd:
                cart.getCartDetails()) {
            BigDecimal quan = new BigDecimal(cd.getQuantity());
            total = total.add(cd.getCourse().getPrice().multiply(quan));
        }

        return total;
    }

    @Override
    public boolean cartIsEmpty(Cart cart) {
        int count = 0;
        if(cart.getCartDetails().isEmpty()){
            return true;
        }

        for (CartDetails cd:
                cart.getCartDetails()) {
            count += cd.getQuantity();
        }
        return count == 0;
    }

    @Override
    public void clearCart(User user) {
        for (CartDetails cd:
                user.getCart().getCartDetails()) {
            cd.setCart(null);
        }
        user.getCart().setCartDetails(null);
    }
}
