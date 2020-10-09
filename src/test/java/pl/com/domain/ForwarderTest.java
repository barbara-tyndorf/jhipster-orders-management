package pl.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.com.web.rest.TestUtil;

public class ForwarderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Forwarder.class);
        Forwarder forwarder1 = new Forwarder();
        forwarder1.setId(1L);
        Forwarder forwarder2 = new Forwarder();
        forwarder2.setId(forwarder1.getId());
        assertThat(forwarder1).isEqualTo(forwarder2);
        forwarder2.setId(2L);
        assertThat(forwarder1).isNotEqualTo(forwarder2);
        forwarder1.setId(null);
        assertThat(forwarder1).isNotEqualTo(forwarder2);
    }
}
