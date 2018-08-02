package ldt.springframework.springmvc.sercurity.encrypt;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/18/18
 */
public interface EncryptionService {

    String encryptString(String input);

    boolean checkPassword(String plainPassword,
                          String encryptedPassword);
}
