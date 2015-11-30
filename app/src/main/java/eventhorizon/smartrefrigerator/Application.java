package eventhorizon.smartrefrigerator;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by marcusmotill on 11/28/15.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "W4JNsYXU8rAyCKsvQL9a6LVPVmEJsuGYU3T7HQNa", "fqrrMZmTiGag2ozfF5WHn2Y2YsKBiux1F2Vmgg5k");
        ParseObject.registerSubclass(RefrigeratorObject.class);
        ParseObject.registerSubclass(QRCodeObject.class);
    }
}
