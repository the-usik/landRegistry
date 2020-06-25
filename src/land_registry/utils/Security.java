package land_registry.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
    public static @Nullable String generateHash(@NotNull String inputString) {
        try {
            String HASH_GENERATION_ALGORITHM = "SHA-256";
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_GENERATION_ALGORITHM);
            byte[] encodedBytes = messageDigest.digest(inputString.getBytes(StandardCharsets.UTF_8));

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < encodedBytes.length; i++) {
                String hex = Integer.toHexString(0xff & encodedBytes[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
