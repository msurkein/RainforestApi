import com.google.common.io.ByteStreams;
import com.rainforestautomation.model.CurrentSummationDelivered;
import com.rainforestautomation.model.InstantaneousDemand;
import com.surkein.raven.RavenXmlHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public class TestXmlSchema {
    RavenXmlHandler handler;

    @Before
    public void setup() {
        handler = new RavenXmlHandler();
    }
    @Test
    public void testInstantaneousDemand() {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("InstantaneousDemand.xml");
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

    @Test
    public void testSummationDelivered() {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("CurrentSummationDelivered.xml");

        try {
            byte[] bytes = ByteStreams.toByteArray(inputStream);
            CurrentSummationDelivered delivered = handler.unmarshall(new String(bytes));
            Assert.assertNotNull(delivered);
            Assert.assertTrue(delivered.getDemandAsDecimal() + " != " + BigDecimal.valueOf(45998.796), delivered.getDemandAsDecimal().compareTo(BigDecimal.valueOf(45998.796)) == 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
