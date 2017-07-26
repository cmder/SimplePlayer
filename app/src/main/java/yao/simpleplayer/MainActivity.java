package yao.simpleplayer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import yao.player.UriPlayItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void play(View view){
        UriPlayItem uriPlayItem =
                new UriPlayItem("test", Environment.getExternalStorageDirectory().getPath() + "/sample/test.mp4");
        this.startActivity(uriPlayItem.buildIntent(this));
    }
}
