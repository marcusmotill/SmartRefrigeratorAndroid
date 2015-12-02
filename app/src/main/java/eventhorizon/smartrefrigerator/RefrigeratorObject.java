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

    public void setItemName(String itemName) {
        put("itemName", itemName);
    }

    public void setQRCode(String code) {
        put("QRCode", code);
    }

    public void setThumbnailName(String thumbnailName) {
        put("thumbnailName", thumbnailName);
    }

    public void setIsCustom(boolean isCustom) {
        put("isCustom", isCustom);
    }

    public boolean isCustom() {
        return getBoolean("isCustom");
    }
}
