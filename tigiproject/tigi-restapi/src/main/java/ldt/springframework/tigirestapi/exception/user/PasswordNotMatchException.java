package ldt.springframework.tigirestapi.exception.user;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/6/18
 */
public class PasswordNotMatchException extends RuntimeException{
    public PasswordNotMatchException(String password, String passwordConf){
        super("Not match!\n " +
                "Password : " + password + "\n" +
                "Password Confirm : " + passwordConf);
    }
}
