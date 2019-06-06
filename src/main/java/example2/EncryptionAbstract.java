package example2;

/**
 * На всякий случай, скорее всего во всех крипторах будет общая функциональность.
 */
public abstract class EncryptionAbstract implements EncryptionIntf {
    @Override
    public abstract String crypt(String text, String key);

}
