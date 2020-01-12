# HappyTimer- An Android Timer UI Library
[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=plastic)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![](https://jitpack.io/v/happysingh23828/HappyTimer.svg)](https://jitpack.io/#happysingh23828/HappyTimer)

<p align="center">
 <center><img width="100%%"  src="screenshots/Happytimer.png"></a></center>
</p>


## Prerequisites

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):
```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

## Dependency

Add this to your module's `build.gradle` file (make sure the version matches the JitPack badge above):

```gradle
dependencies {
	...
	implementation 'com.github.happysingh23828:HappyTimer:1.0.0'
}
```

## Usage
In this library all the UI widgets are using a common [HappyTimer](https://github.com/happysingh23828/HappyTimer/blob/master/HappyTimer/src/main/java/com/androchef/happytimer/countdowntimer/HappyTimer.kt) class for implementing timer.

### HappyTimer.kt 
```kotlin
        //Initialize Timer with seconds
        val happyTimer = HappyTimer(60)
        
        //set OnTickListener for getting updates on time. [Optional]
        happyTimer.setOnTickListener(object :HappyTimer.OnTickListener{

            //OnTick
            override fun onTick(completedSeconds: Int, remainingSeconds: Int) {

            }

            //OnTimeUp
            override fun onTimeUp() {

            }
        })

        //set OnStateChangeListener [RUNNING, FINISHED, PAUSED, RESUMED, UNKNOWN, RESET, STOPPED] [Optional]
        happyTimer.setOnStateChangeListener(object : HappyTimer.OnStateChangeListener{
            override fun onStateChange(state: HappyTimer.State, completedSeconds: Int, remainingSeconds: Int) {
                // write your code here for State Changes
            }
        })

        //Start Timer
        happyTimer.start()

        //Pause Timer
        happyTimer.pause()

        //Resume Timer
        happyTimer.resume()

        //Stop Timer
        happyTimer.stop()

        //Reset Timer
        happyTimer.resetTimer()

```

### Note : To avoid MemoryLeaks always stop the timer in onDestroy().

### CircularCountDownView

#### Layout(XML)
```xml
<com.androchef.happytimer.countdowntimer.CircularCountDownView
            android:id="@+id/circularCountDownView"
            android:layout_width="200dp"
            android:layout_height="200dp"
            app:stroke_foreground_color="@color/colorLightBlue"
            app:stroke_background_color="@color/colorPrimaryDark"
            app:stroke_background_thickness="31dp"
            app:stroke_foreground_thickness="40dp"
            app:timer_text_color="@color/colorLightBlue"
            app:timer_text_shown="true"
            app:timer_text_isBold="true"
            app:timer_text_format="HOUR_MINUTE_SECOND"
            app:timer_text_size="20sp"
            app:timer_type="COUNT_UP"
            />

```
#### Activity Or Fragment

**You can set these properties in your java or kotlin code as well.**
```kotlin
class DemoCircularCountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_circular_count_down)

        //Set configuration for timer UI
        circularCountDownView.isTimerTextShown = true
        circularCountDownView.timerType = HappyTimer.Type.COUNT_UP
        circularCountDownView.timerTextFormat = CircularCountDownView.TextFormat.HOUR_MINUTE_SECOND
        circularCountDownView.strokeThicknessForeground = 10f
        circularCountDownView.strokeThicknessBackground = 10f
        circularCountDownView.strokeColorBackground = ContextCompat.getColor(this, R.color.colorGrey)
        circularCountDownView.strokeColorForeground = ContextCompat.getColor(this, R.color.colorLightBlue)
        circularCountDownView.timerTextColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        circularCountDownView.timerTextIsBold = true
        circularCountDownView.timerTextSize = 13f //this will automatically converted to sp value.

        //Initialize Your Timer with seconds
        circularCountDownView.initTimer(60)

        //set OnTickListener for getting updates on time. [Optional]
        circularCountDownView.setOnTickListener(object : HappyTimer.OnTickListener {

            //OnTick
            override fun onTick(completedSeconds: Int, remainingSeconds: Int) {

            }

            //OnTimeUp
            override fun onTimeUp() {

            }
        })

        //set OnStateChangeListener [RUNNING, FINISHED, PAUSED, RESUMED, UNKNOWN, RESET, STOPPED] [Optional]
        circularCountDownView.setStateChangeListener(object : HappyTimer.OnStateChangeListener {
            override fun onStateChange(
                state: HappyTimer.State,
                completedSeconds: Int,
                remainingSeconds: Int
            ) {
                // write your code here for State Changes
            }
        })
        
        //Call these functions to perform actions
        //Start Timer
        circularCountDownView.startTimer()

        //Pause Timer
        circularCountDownView.pauseTimer()

        //Resume Timer
        circularCountDownView.resumeTimer()

        //Stop Timer
        circularCountDownView.stopTimer()

        //Reset Timer
        circularCountDownView.resetTimer()

        //get Total Seconds
        val totalSeconds = circularCountDownView.getTotalSeconds()

    }

}

```

### DynamicCountDownView

#### Layout(XML)
```xml
<com.androchef.happytimer.countdowntimer.DynamicCountDownView
            android:id="@+id/dynamicCountDownView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:dynamic_timer_text_color="@android:color/white"
            app:dynamic_timer_text_separator_color="@color/colorGrey"
            app:dynamic_timer_text_size="12sp"
            app:dynamic_timer_separator_text_size="15sp"
            app:dynamic_timer_text_isBold="true"
            app:dynamic_timer_text__separator_isBold="true"
            app:dynamic_timer_text_separator=":"
            app:dynamic_show_hour="true"
            app:dynamic_show_labels="true"
            app:dynamic_show_minutes="true"
            app:dynamic_show_seconds="true"
            />
```

#### Activity Or Fragment
**You can set these properties in your java or kotlin code as well.**
```kotlin
class DemoDynamicCountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_dynamic_count_down)

        dynamicCountDownView.separatorString = ":"
        dynamicCountDownView.timerTextColor = ContextCompat.getColor(this, R.color.colorGrey)
        dynamicCountDownView.timerTextSeparatorColor = ContextCompat.getColor(this, R.color.colorAccent)
        dynamicCountDownView.timerTextSeparatorSize = 15f //this will automatically converted to sp value.
        dynamicCountDownView.timerTextSize = 15f //this will automatically converted to sp value.
        dynamicCountDownView.showHour = true
        dynamicCountDownView.showMinutes = true
        dynamicCountDownView.showSeconds = true
        dynamicCountDownView.showSeparators = true
        dynamicCountDownView.timerTextIsBold = true
        dynamicCountDownView.timerTextSeparatorIsBold = true
        dynamicCountDownView.timerType = HappyTimer.Type.COUNT_UP

        //Set timer text background as a rectangle
        dynamicCountDownView.setRectangularBackground()

        //Set timer text background as a circle
        dynamicCountDownView.setRoundedBackground()

        //set custom background for timer text
        dynamicCountDownView.customBackgroundDrawable =
            ContextCompat.getDrawable(this, R.drawable.bg_textview_count_down_circle)

        //Initialize Your Timer with seconds
        dynamicCountDownView.initTimer(60)

        //set OnTickListener for getting updates on time. [Optional]
        dynamicCountDownView.setOnTickListener(object : HappyTimer.OnTickListener {

            //OnTick
            override fun onTick(completedSeconds: Int, remainingSeconds: Int) {

            }

            //OnTimeUp
            override fun onTimeUp() {

            }
        })

        //set OnStateChangeListener [RUNNING, FINISHED, PAUSED, RESUMED, UNKNOWN, RESET, STOPPED] [Optional]
        dynamicCountDownView.setStateChangeListener(object : HappyTimer.OnStateChangeListener {
            override fun onStateChange(
                state: HappyTimer.State,
                completedSeconds: Int,
                remainingSeconds: Int
            ) {
                // write your code here for State Changes
            }
        })

        //Call these functions to perform actions
        //Start Timer
        dynamicCountDownView.startTimer()

        //Pause Timer
        dynamicCountDownView.pauseTimer()

        //Resume Timer
        dynamicCountDownView.resumeTimer()

        //Stop Timer
        dynamicCountDownView.stopTimer()

        //Reset Timer
        dynamicCountDownView.resetTimer()

    }

}
```

### DynamicCountDownView

#### Layout(XML)
```xml
<com.androchef.happytimer.countdowntimer.NormalCountDownView
            android:id="@+id/normalCountDownView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:normal_timer_text_size="30sp"
            app:normal_timer_label_text_size="18sp"
            app:normal_timer_text_color="@color/colorLightBlue"
            app:normal_timer_text_label_color="@color/colorGrey"
            app:normal_timer_text__label_isBold="true"
            app:normal_timer_text_isBold="true"
            app:show_labels="true"
            app:show_hour="true"
            app:show_minutes="true"
            app:show_seconds="true"
            />
```

#### Activity Or Fragment
**You can set these properties in your java or kotlin code as well.**
```kotlin
class NormalCountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_count_down)


        normalCountDownView.timerTextColor = ContextCompat.getColor(this, R.color.colorLightBlue)
        normalCountDownView.timerTextLabelColor = ContextCompat.getColor(this, R.color.colorLightBlue)
        normalCountDownView.timerTextIsBold = false
        normalCountDownView.timerTextLabelIsBold = false
        normalCountDownView.timerTextSize = 15f //this will automatically converted to sp value.
        normalCountDownView.timerTextLabelSize = 12f //this will automatically converted to sp value.
        normalCountDownView.showHour = true
        normalCountDownView.showMinutes = true
        normalCountDownView.showSeconds = true
        normalCountDownView.timerType = HappyTimer.Type.COUNT_DOWN


        //Initialize Your Timer with seconds
        normalCountDownView.initTimer(60)

        //set OnTickListener for getting updates on time. [Optional]
        normalCountDownView.setOnTickListener(object : HappyTimer.OnTickListener {

            //OnTick
            override fun onTick(completedSeconds: Int, remainingSeconds: Int) {

            }

            //OnTimeUp
            override fun onTimeUp() {

            }
        })

        //set OnStateChangeListener [RUNNING, FINISHED, PAUSED, RESUMED, UNKNOWN, RESET, STOPPED] [Optional]
        normalCountDownView.setStateChangeListener(object : HappyTimer.OnStateChangeListener {
            override fun onStateChange(
                state: HappyTimer.State,
                completedSeconds: Int,
                remainingSeconds: Int
            ) {
                // write your code here for State Changes
            }
        })

        //Call these functions to perform actions
        //Start Timer
        normalCountDownView.startTimer()

        //Pause Timer
        normalCountDownView.pauseTimer()

        //Resume Timer
        normalCountDownView.resumeTimer()

        //Stop Timer
        normalCountDownView.stopTimer()

        //Reset Timer
        normalCountDownView.resetTimer()

    }
}
```






## Donation
If this project help you reduce time to develop, you can give me a cup of coffee :) 

<a href="https://www.buymeacoffee.com/UE8o2WT" target="_blank"><img src="https://bmc-cdn.nyc3.digitaloceanspaces.com/BMC-button-images/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

## Contributing

Please fork this repository and contribute back using
[pull requests](https://github.com/happysingh23828/HappyTimer/pulls).

Any contributions, large or small, major features, bug fixes, are welcomed and appreciated
but will be thoroughly reviewed .

### Contact - Let's become friend
- [Androchef Blog](https://androchef.com/)
- [Androchef Youtube](https://www.youtube.com/channel/UCILhpbLSFkGzsiCYAeR30DA)
- [Github](https://github.com/happysingh23828)
- [Linkedin](https://www.linkedin.com/in/happpysingh23828/)


## License

* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

```
Copyright 2019 Happy Singh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
