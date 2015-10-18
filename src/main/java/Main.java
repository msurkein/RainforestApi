import com.surkein.raven.RavenXmlClient;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String args[]) {
        RavenXmlClient client = new RavenXmlClient("COM3");
        try {
            try {
                CompletableFuture<Object> deviceInfo = client.getDeviceInfo();
                deviceInfo.whenComplete((o, t) -> {
                    System.out.println(o);
                    t.printStackTrace();
                });
                CompletableFuture<Object> connectionStatus = client.getConnectionStatus();
                connectionStatus.whenComplete((o, t) -> {
                    System.out.println(o);
                    t.printStackTrace();
                });
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