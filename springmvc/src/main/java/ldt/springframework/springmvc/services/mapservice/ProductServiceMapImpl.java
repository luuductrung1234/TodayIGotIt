package ldt.springframework.springmvc.services.mapservice;

import ldt.springframework.springmvc.domain.DomainObject;
import ldt.springframework.springmvc.domain.Product;
import ldt.springframework.springmvc.services.ProductService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/15/18
 */

@Service
@Profile("map")
public class ProductServiceMapImpl extends AbstractMapService implements ProductService {

    // =======================================
    // =        Constructors Section         =
    // =======================================

    public ProductServiceMapImpl() {
    }


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Product getById(Integer id){
        return (Product) super.getById(id);
    }

    @Override
    public Product saveOrUpdate(Product product){
        return (Product) super.saveOrUpdateDomainObject(product);
    }

    @Override
    public void delete(Integer id){
        super.deleteDomainObject(id);
    }
}
