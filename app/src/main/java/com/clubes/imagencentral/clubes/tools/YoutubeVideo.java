package com.clubes.imagencentral.clubes.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Lau on 23/02/2015.
 */
public class YoutubeVideo{
    public YoutubeVideo() {
    }
    public void verVideo(Context context,String idvideo){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + idvideo));
        intent.putExtra("force_fullscreen",true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }
}
