/*
 * Copyright 2015 Edison Spencer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cv.com.spencer.motus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class MotusImageView  extends ImageView implements SensorEventListener {

    protected Matrix mMatrix = new Matrix();
    private SensorManager mSensorManager;
    private int mMultiplier;

    public MotusImageView(Context context) {
        super(context);
        if(!isInEditMode())
            initMotusImageView(null);
    }

    public MotusImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())
            initMotusImageView(attrs);
    }

    public MotusImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode())
            initMotusImageView(attrs);
    }

    /**
     * This method returns the multiplier values currently being used.
     * @return The multiplier value.
     */
    public int getMultiplier() {
        return this.mMultiplier;
    }

    /**
     * This method sets the value passed in {@code multiplier}, as the multipler factor
     * @param multiplier - A value that will enhance the movement of the image.
     */
    public void setMultiplier(int multiplier) {
        this.mMultiplier = multiplier;
    }

    /**
     * This method registers the {@code MotusImageView} as a listener of the sensor changes.
     * @return <b>true</b> if the registration was successful, <b>false</b> otherwise.
     */
    public boolean registerListener() {
        if(mSensorManager != null)
            return mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        return false;
    }

    /**
     * This method unregisters the {@code MotusImageView} as a listener of the sensor changes.
     */
    public void unregisterListener() {
        if(mSensorManager != null)
            mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // readjust the background image according to the new sensor readings
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            readjustBackground(event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    /**
     * This method is used to initialize the default values of the {@code MotusImageView} attributes,
     * as well as setting the background image and start listening to the sensor events.
     * @param attrs - Eventual attributes that have been set for the {@code MotusImageView} in the layout.
     */
    private void initMotusImageView(AttributeSet attrs) {
        // read attribute values or set the defaults
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MotusImageView);
        mMultiplier = array.getInteger(R.styleable.MotusImageView_multiplier, getResources().getInteger(R.integer.default_multiplier));
        array.recycle();

        // set the scale type to matrix in order to center the image in the middle of the screen
        setScaleType(ScaleType.MATRIX);
        centerBackgroundImage();

        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        registerListener();
    }

    /**
     * This method is used in order to center the middle of the background image in the middle of the {@code MotusImageView} area.
     */
    private void centerBackgroundImage() {
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Drawable backgroundDrawable = getDrawable();
                if(backgroundDrawable != null) {
                    // apply the translation in order to center the image in the middle of the screen
                    mMatrix.postTranslate((getWidth() - backgroundDrawable.getIntrinsicWidth()) / 2, (getHeight() - backgroundDrawable.getIntrinsicHeight()) / 2);
                    setImageMatrix(mMatrix);
                }
                return true;
            }
        });
    }

    /**
     * This method is called in order to make readjustments to the background according to the readings present in the parameters.
     * @param xAxis - The new reading from the X axis.
     * @param yAxis - The new reading from the Y axis.
     * @param zAxis - The new reading from the Z axis.
     */
    private void readjustBackground(float xAxis, float yAxis, float zAxis) {
        if(getDrawable() == null) return; // do nothing if no drawable is found

        // else, translate the image on the opposite direction multiplied by a certain coefficient
        mMatrix.setTranslate((xAxis * mMultiplier), -(yAxis * mMultiplier));
        setImageMatrix(mMatrix);
    }

}
