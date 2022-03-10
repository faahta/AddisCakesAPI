package et.addis.home_cakes.util;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by Fassil on 25/02/22.
 */
@Service
public class PasswordGenerator {

    public String generate(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

}
