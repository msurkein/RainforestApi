import com.surkein.raven.RavenXmlClient;
import com.surkein.raven.model.DeviceInfo;

public class Main {
    public static void main(String args[]) {
        RavenXmlClient client = new RavenXmlClient("COM3");
        try {
            try {
                DeviceInfo deviceInfo = client.getDeviceInfo();
                System.out.println(deviceInfo);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            client.close();
        }
    }
}