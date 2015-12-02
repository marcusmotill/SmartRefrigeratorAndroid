package eventhorizon.smartrefrigerator;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by marcusmotill on 11/29/15.
 */
@ParseClassName("Code")
public class QRCodeObject extends ParseObject {
    public QRCodeObject() {

    }

    public void setItemName(String itemName) {
        put("itemName", itemName);
    }

    public void setQRCode(String code) {
        put("QRCode", code);
    }

    public void setThumbnailName(String thumbnailName) {
        put("thumbnailName", thumbnailName);
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
