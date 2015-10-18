import com.google.common.io.ByteStreams;
import com.surkein.raven.RavenXmlHandler;
import com.surkein.raven.model.InstantaneousDemand;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public class TestXmlSchema {
    @Test
    public void testInstantaneousDemand() {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("InstantaneousDemand.xml");
        RavenXmlHandler handler = new RavenXmlHandler();
        try {
            byte[] bytes = ByteStreams.toByteArray(inputStream);
            InstantaneousDemand unmarshall = handler.unmarshall(new String(bytes));
            Assert.assertNotNull(unmarshall);
            Assert.assertNotNull(unmarshall.getDemand());
            Assert.assertNotNull(unmarshall.getDeviceMacId());
            Assert.assertNotNull(unmarshall.getDigitsLeft());
            Assert.assertNotNull(unmarshall.getDigitsRight());
            Assert.assertNotNull(unmarshall.getDivisor());
            Assert.assertNotNull(unmarshall.getMeterMacId());
            Assert.assertNotNull(unmarshall.getSuppressLeadingZero());
            Assert.assertNotNull(unmarshall.getTimeStamp());
            Assert.assertTrue(unmarshall.getDemandAsDecimal().compareTo(BigDecimal.valueOf(1.764)) == 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
