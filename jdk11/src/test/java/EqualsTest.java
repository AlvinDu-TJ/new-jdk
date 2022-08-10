import feature.jdk11.equals.ObjectEuqals;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public final class EqualsTest {
    @Test
    public void equalsTest() {
        EqualsVerifier.simple().forClass(ObjectEuqals.class).verify();
    }
}
