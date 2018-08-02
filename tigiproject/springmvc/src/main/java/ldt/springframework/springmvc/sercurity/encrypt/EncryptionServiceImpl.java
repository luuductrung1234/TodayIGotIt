package ldt.springframework.springmvc.sercurity.encrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/18/18
 */


@Service
public class EncryptionServiceImpl implements EncryptionService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public String encryptString(String input) {
        return passwordEncoder.encode(input);
    }

    @Override
    public boolean checkPassword(String plainPassword, String encryptedPassword) {
        return passwordEncoder.matches(plainPassword, encryptedPassword);
    }
}
