package yao.player;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Bruce Yao on 2017/4/21.
 */

public abstract class PlayItem {

    public String name;
    public String[] names;

    public PlayItem(String name) {
        this.name = name;
    }

    public PlayItem(String[] names) {
        this.names = names;
    }

    public Intent buildIntent(Context context) {
        Intent intent = new Intent(context, PlayerActivity.class);
        return intent;
    }

}
