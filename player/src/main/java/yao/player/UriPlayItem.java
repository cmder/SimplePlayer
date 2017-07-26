package yao.player;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Bruce Yao on 2017/4/21.
 */

public final class UriPlayItem extends PlayItem {
    public final String uri;

    public UriPlayItem(String name, String uri) {
        super(name);
        this.uri = uri;
    }

    @Override
    public Intent buildIntent(Context context) {
        return super.buildIntent(context)
                .setData(Uri.parse(uri))
                .putExtra(PlayerActivity.TITLE_EXTRA, name)
                .setAction(PlayerActivity.ACTION_VIEW);
    }
}
