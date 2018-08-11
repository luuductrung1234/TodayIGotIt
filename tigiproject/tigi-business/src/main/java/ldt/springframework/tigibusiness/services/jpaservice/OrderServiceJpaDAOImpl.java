package ldt.springframework.tigibusiness.services.jpaservice;

import ldt.springframework.tigibusiness.commands.statistic.ReceiptByDay;
import ldt.springframework.tigibusiness.commands.statistic.ReceiptByMonth;
import ldt.springframework.tigibusiness.commands.statistic.ReceiptByYear;
import ldt.springframework.tigibusiness.domain.*;
import ldt.springframework.tigibusiness.enums.OwerType;
import ldt.springframework.tigibusiness.repository.OrderRepository;
import ldt.springframework.tigibusiness.services.CartService;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigibusiness.services.OrderService;
import ldt.springframework.tigibusiness.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/23/18
 */


@Service
@Profile("jpadao")
public class OrderServiceJpaDAOImpl implements OrderService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    CourseService courseService;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        return orderRepository.listAll();
    }

    @Override
    public Order getById(Integer id) {
        return orderRepository.getById(id);
    }

    @Override
    public Order saveOrUpdate(Order order) {
        return orderRepository.saveOrUpdate(order);
    }

    @Override
    public void delete(Integer id) {
        orderRepository.delete(id);
    }


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public BigDecimal calculateTotalPrice(Order order){
        BigDecimal total = new BigDecimal(0);
        for (OrderDetails od:
             order.getOrderDetails()) {
            BigDecimal quan = new BigDecimal(od.getQuantity());
            total = total.add(od.getCourse().getPrice().multiply(quan));
        }

        return total;
    }

    @Override
    public void increaseQuantity(Order order, Integer courseId) {
        for (OrderDetails od:
                order.getOrderDetails()) {
            if(od.getCourse().getId().equals(courseId)){
                int quantity = od.getQuantity() + 1;
                od.setQuantity(quantity);
                return;
            }
        }
    }

    @Override
    public void decreaseQuantity(Order order, Integer courseId) {
        for (OrderDetails od:
                order.getOrderDetails()) {
            if(od.getCourse().getId().equals(courseId)){
                int quantity = od.getQuantity() - 1;
                if(quantity == 0){
                    removeFromOrder(order, courseId);
                }else {
                    od.setQuantity(quantity);
                }
                return;
            }
        }
    }

    @Override
    public void removeFromOrder(Order order, Integer courseId) {
        for (OrderDetails od:
                order.getOrderDetails()) {
            if(od.getCourse().getId().equals(courseId)){
                order.removeOrderDetail(od);
                return;
            }
        }
    }

    @Override
    public boolean orderIsEmpty(Order order) {
        int count = 0;
        if(order.getOrderDetails().isEmpty()){
            return true;
        }

        for (OrderDetails od:
                order.getOrderDetails()) {
            count += od.getQuantity();
        }
        return count == 0;
    }

    @Override
//    @Transactional
    public void pay(boolean isSinglePay, User curUser, Order newOrder){
        for (OrderDetails orderDetails : newOrder.getOrderDetails()) {
            orderDetails.setOrder(newOrder);
            curUser.addCourseOwer(new CourseOwner(OwerType.BUY, orderDetails.getCourse(), 0f, "", ""));

            // TODO : Check increase buy count
            orderDetails.getCourse().setBuyCount(orderDetails.getCourse().getBuyCount() + 1);
            orderDetails.setCourse(courseService.saveOrUpdate(orderDetails.getCourse()));
        }

        if (isSinglePay) {
            curUser.addOrders(newOrder);
            userService.saveOrUpdate(curUser);
        } else {
            curUser.addOrders(newOrder);
            curUser.removeCart(curUser.getCart());
            userService.saveOrUpdate(curUser);
            curUser.setCart(new Cart());
            userService.saveOrUpdate(curUser);
        }
    }

    @Override
    public List<ReceiptByDay> receiptByDay(int modify) {
        LocalDate defaultDate = LocalDate.now();
        int day = defaultDate.getDayOfMonth() + modify;
        Month month = defaultDate.getMonth();
        int year = defaultDate.getYear();

        List<ReceiptByDay> receiptByDays = new ArrayList<>();
        for (int i = -3; i <= 3; i++){
            List<Order> orders = orderRepository.findAllBetweenDays(LocalDate.of(year, month, day-1+i), LocalDate.of(year, month, day+1+i));

            if(!orders.isEmpty()) {
                BigDecimal total = new BigDecimal(0);
                for (Order order :
                        orders) {
                    for (OrderDetails orderDetails:
                            order.getOrderDetails()) {
                        BigDecimal quan = new BigDecimal(orderDetails.getQuantity());
                        total = total.add(orderDetails.getCourse().getPrice().multiply(quan));
                    }
                }

                receiptByDays.add(new ReceiptByDay(total, day+i, month, year));
            }
        }

        return receiptByDays;
    }

    @Override
    public List<ReceiptByMonth> receiptByMonth(int modify) {
        LocalDate defaultDate = LocalDate.now();
        Month month = defaultDate.getMonth().plus(modify);
        int year = defaultDate.getYear();


        List<ReceiptByMonth> receiptByMonths = new ArrayList<>();
        for (int i = -3; i <= 3; i++) {
            List<Order> orders = orderRepository.findAllBetweenDays(LocalDate.of(year, month.plus(-1+i), 1), LocalDate.of(year, month.plus(1+i), 30));

            if(!orders.isEmpty()) {
                BigDecimal total = new BigDecimal(0);
                for (Order order :
                        orders) {
                    for (OrderDetails orderDetails:
                            order.getOrderDetails()) {
                        BigDecimal quan = new BigDecimal(orderDetails.getQuantity());
                        total = total.add(orderDetails.getCourse().getPrice().multiply(quan));
                    }
                }

                receiptByMonths.add(new ReceiptByMonth(total, month.plus(i), year));
            }
        }

        return receiptByMonths;
    }

    @Override
    public List<ReceiptByYear> receiptByYear(int modify) {
        LocalDate defaultDate = LocalDate.now();
        int year = defaultDate.getYear() + modify;


        List<ReceiptByYear> receiptByYears = new ArrayList<>();
        for (int i = -3; i <= 3; i++) {
            List<Order> orders = orderRepository.findAllBetweenDays(LocalDate.of(year - 1 + i, Month.JANUARY, 1), LocalDate.of(year + 1 + i, Month.DECEMBER, 31));

            if(!orders.isEmpty()) {
                BigDecimal total = new BigDecimal(0);
                for (Order order :
                        orders) {
                    for (OrderDetails orderDetails:
                            order.getOrderDetails()) {
                        BigDecimal quan = new BigDecimal(orderDetails.getQuantity());
                        total = total.add(orderDetails.getCourse().getPrice().multiply(quan));
                    }
                }

                receiptByYears.add(new ReceiptByYear(total, year + i));
            }
        }

        return receiptByYears;
    }
}
