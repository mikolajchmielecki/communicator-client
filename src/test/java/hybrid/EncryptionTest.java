package hybrid;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionTest {

    private Hybrid encryption = new Hybrid();

    @Test
    public void encryptionTest() {
        String message = "ASDasdfaSDFasdfasfgASDa\nsdfaSDFasdfasfgASDa\nsdfaSDFasdfas}fgASDasdf{aSDFasdfasfg$ASDasdfaSDF@asdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfgASDasdfaSDFasdfasfg";
        String encrypted = encryption.encrypt(message);
        assertEquals(message, encryption.decrypt(encrypted));
    }

}