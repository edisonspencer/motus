# Motus

Motus is a small library that allows you to have a background that moves slightly.

You can try out the sample application available [in the Play Store](https://play.google.com/store/apps/details?id=cv.com.spencer.motusdemo)

<img alt="MotusDemo Screenshot" src="https://raw.github.com/edisonspencer/motus/master/screenshot.png" height="450px" />

It can also be used in conjunction with [Picasso][1]!

### Usage

*For a working implementation of this project see the `app/` folder.*

*For the source code see the `motus/` folder.*

  You can use the widget through the layout by declaring it as follows:
  
	<cv.com.spencer.android.motus.MotusImageView
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		android:src="@drawable/plate_blurred" />

# Customization

 * `multiplier` Enhance the image movement (**default value is 3**)
		 
### Warning

  Note that this library was created in order to apply a slight movement to the background image.
  However, since you can set any value to the **`multiplier`** attribute, if you set it too high and
  your image is not that big, the content of the image might get rendered completely off screen.

### TODO

  Make this library available through [Gradle][2].
  
# Developed By

 * Edison Spencer - <edison@spencer.com.cv>
  
# License

	Copyright 2015 Edison Spencer

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.

[1]: https://github.com/square/picasso
[2]: https://gradle.org/
