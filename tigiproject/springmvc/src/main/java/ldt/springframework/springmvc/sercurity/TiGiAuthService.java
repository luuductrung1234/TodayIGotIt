package ldt.springframework.springmvc.sercurity;

import java.util.concurrent.Callable;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/29/18
 */



public interface TiGiAuthService {
    public <T> T sessionCheckLogin (T fallbackResult, Callable<T> todo);
}
