package ldt.springframework.tigibusiness.services;

import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.LearnTracking;
import ldt.springframework.tigibusiness.domain.User;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/18/18
 */
public interface UserService extends CRUDService<User>{

    User login(String username, String password);

    User findByUserName(String userName);

    List<User> findAllByCustomerFirstNameOrCustomerLastName(String firstName, String lastName);

    void updateLoginUserDataToSession(HttpServletRequest request, CartService cartService, User loginUser);

    void updateCurrentUserDataToSession(HttpServletRequest request, CartService cartService, Integer userId);

    void updateLogoutUserToSession(WebRequest request, SessionStatus status);

    User saveOrUpdateUserForm(UserForm userForm);

    User saveUserForm(UserForm userForm);

    User updateUserForm(UserForm userForm);

    boolean checkCourseOwned(User user, Integer courseId);
}
