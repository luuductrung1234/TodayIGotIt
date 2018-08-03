package ldt.springframework.tigibusiness.repository.springdatarepository;

import ldt.springframework.tigibusiness.domain.Cart;
import ldt.springframework.tigibusiness.repository.CartRepository;
import ldt.springframework.tigibusiness.repository.springdatarepository.data.CartSpringData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */

@Repository
@Profile(value = {"springdatajpa", "jpadao"})
public class CartRepositorySpringDataImpl implements CartRepository {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CartSpringData cartSpringData;



    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        List<Cart> carts = new ArrayList<>();
        cartSpringData.findAll().forEach(carts::add);

        return carts;
    }

    @Override
    public Cart getById(Integer id) {
        if(cartSpringData.findById(id).isPresent()){
            return cartSpringData.findById(id).get();
        }
        return null;
    }

    @Override
    public Cart saveOrUpdate(Cart cart) {
        return cartSpringData.save(cart);
    }

    @Override
    public void delete(Integer id) {
        cartSpringData.deleteById(id);
    }
}
