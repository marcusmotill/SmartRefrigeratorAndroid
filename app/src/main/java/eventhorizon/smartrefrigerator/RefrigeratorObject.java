package eventhorizon.smartrefrigerator;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by marcusmotill on 11/28/15.
 */

@ParseClassName("Refrigerator")
public class RefrigeratorObject extends ParseObject {
    public RefrigeratorObject() {
    }

    public String getItemName() {
        return getString("itemName");
    }

    public String getQRCode() {
        return getString("QRCode");
    }

    public String getThumbnailName() {
        return getString("thumbnailName");
    }
}
