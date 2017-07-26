package yao.player;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Bruce Yao on 2017/4/21.
 */

public final class PlayList extends PlayItem {
    public final UriPlayItem[] children;

    public PlayList(String[] names, UriPlayItem... children) {
        super(names);
        this.children = children;
    }

    @Override
    public Intent buildIntent(Context context) {
        String[] uris = new String[children.length];
        for (int i = 0; i < children.length; i++) {
            uris[i] = children[i].uri;
        }
        return super.buildIntent(context)
                .putExtra(PlayerActivity.URI_LIST_EXTRA, uris)
                .putExtra(PlayerActivity.TITLE_LIST_EXTRA, names)
                .setAction(PlayerActivity.ACTION_VIEW_LIST);
    }
}
