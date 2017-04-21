package com.honzar.adtutils.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

import cz.mafra.jizdnirady.lib.base.CommonClasses.CmnIcon;
import cz.mafra.jizdnirady.lib.base.CommonClasses.LargeHash;
import cz.mafra.jizdnirady.lib.base.CustomCollections.CacheWeakRef;
import cz.mafra.jizdnirady.lib.base.CustomCollections.ISynchronizedCache;

public class BitmapUtils {
    private static final ISynchronizedCache<LargeHash, CmnIcon> cache = new CacheWeakRef<LargeHash, CmnIcon>().getSynchronizedCache();

    public static Bitmap decodeBitmapFromBase64(String base64) {
        byte[] b = Base64.decode(base64, Base64.DEFAULT);
        Bitmap ret = BitmapFactory.decodeByteArray(b, 0, b.length);
        return ret;
    }

    /**
     * Pokud je true, tak se ikona perzistuje do souboru (pouziti u online dat) - ZATIM NEIMPLEMENTOVANO!
     */
    public static void addIcon(CmnIcon icon, boolean persist) {
        if (persist)
            throw new RuntimeException("Not implemented");
        cache.put(icon.getIconId(), icon);
    }

    public static CmnIcon getIcon(LargeHash iconId) {
        return cache.get(iconId);
    }

    public static LargeHash createBitmapHash(Bitmap bitmap) {
        try {
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (bitmap.compress(CompressFormat.PNG, 100, stream) == false)
                throw new RuntimeException();
            md5Digest.update(stream.toByteArray());
            stream.close();

            byte[] md5 = md5Digest.digest();
            return new LargeHash(md5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Drawable getDrawableWithIntrisicBounds(Context context, int rid) {
        Drawable ret = context.getResources().getDrawable(rid).mutate();
        ret.setBounds(0, 0, ret.getIntrinsicWidth(), ret.getIntrinsicHeight());
        return ret;
    }
}
