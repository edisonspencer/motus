package cv.com.spencer.motusdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cv.com.spencer.motus.MotusImageView;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private MotusImageView mSurfer;

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            mSurfer = rootView.findViewById(R.id.surfer);

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();

            // this call is optional. The listener is automatically registered when the MotusImageView is instantiated
            mSurfer.registerListener();
        }

        @Override
        public void onPause() {
            super.onPause();

            // this call is optional. But should be called in order to unregister the listener, thus preventing unnecessary calculations
            mSurfer.unregisterListener();
        }
    }
}
