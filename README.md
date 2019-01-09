# SapiAdvertiserAndroidProject

## Prerequisites

Android Studio, Java Development Kit(JDK), Phone/Tablet or Emulator.

## Installing
  By clicking on the Run button in Android Studio you can install the application on your phone.

### Android Studio as your main IDE

The recommended IDE for Android development is [Android Studio](https://developer.android.com/sdk/installing/studio.html) because it is developed and constantly updated by Google, has good support for Gradle, contains a range of useful monitoring and analysis tools and is fully tailored for Android development.


### Firebase
We used Firebase Realtime database for storing data and Firebase Storage for storageing images.
For autentification we used the firebase autentification with phonenumber.
  # Realtime Database
    Firebase provides a realtime database and backend as a service. The service provides application developers an API that allows application data to be synchronized across clients and stored on Firebase's cloud.[20][21] The company provides client libraries that enable integration with Android, iOS, JavaScript, Java, Objective-C, Swift and Node.js applications. The database is also accessible through a REST API and bindings for several JavaScript frameworks such as AngularJS, React, Ember.js and Backbone.js.[22] The REST API uses the Server-Sent Events protocol, which is an API for creating HTTP connections for receiving push notifications from a server. Developers using the realtime database can secure their data by using the company's server-side-enforced security rules.[23] Cloud Firestore which is Firebase's next generation of the Realtime Database was released for beta use.
# Firebase Storage
  Firebase Storage provides secure file uploads and downloads for Firebase apps, regardless of network quality. The developer can use it to store images, audio, video, or other user-generated content. Firebase Storage is backed by Google Cloud Storage.[24] 
  

## Glide
[Glide](https://github.com/bumptech/glide) is another option for loading and caching images. It has support for animated GIFs, circular images and claims of better performance than Picasso, but also a bigger method count.

### Android SDK

Place your [Android SDK](https://developer.android.com/sdk/installing/index.html?pkg=tools) somewhere in your home directory or some other application-independent location. Some distributions of IDEs include the SDK when installed, and may place it under the same directory as the IDE. This can be bad when you need to upgrade (or reinstall) the IDE, as you may lose your SDK installation, forcing a long and tedious redownload.

Also avoid putting the SDK in a system-level directory that might need root permissions, to avoid permissions issues.

### Build system

Your default option should be [Gradle](https://gradle.org) using the [Android Gradle plugin](https://developer.android.com/studio/build/index.html). 

It is important that your application's build process is defined by your Gradle files, rather than being reliant on IDE specific configurations. This allows for consistent builds between tools and better support for continuous integration systems.

### Project structure

Although Gradle offers a large degree of flexibility in your project structure, unless you have a compelling reason to do otherwise, you should accept its [default structure](https://developer.android.com/studio/build/index.html#sourcesets) as this simplify your build scripts. 

### Gradle configuration

**General structure.** Follow [Google's guide on Gradle for Android](https://developer.android.com/studio/build/index.html).

**minSdkVersion: 21** We recommend to have a look at the [Android version usage chart](https://developer.android.com/about/dashboards/index.html#Platform) before defining the minimum API required. 

**Small tasks.** Instead of (shell, Python, Perl, etc) scripts, you can make tasks in Gradle. Just follow [Gradle's documentation](http://www.gradle.org/docs/current/userguide/userguide_single.html#N10CBF) for more details. Google also provides some helpful [Gradle recipes](https://developer.android.com/studio/build/gradle-tips.html), specific to Android.

**Passwords.** In your app's `build.gradle` we need to avoid storaging password,it may appear in version control.

### Activities and Fragments

Because of Android API's history, you can loosely consider Fragments as UI pieces of a screen. In other words, Fragments are normally related to UI. Activities can be loosely considered to be controllers, they are especially important for their lifecycle and for managing state. However, you are likely to see variation in these roles: activities might take UI roles and [fragments might be used solely as controllers]. 
Here is some advice on what to be careful with, but take them with a grain of salt:

- Avoid using [nested fragments](https://developer.android.com/about/versions/android-4.2.html#NestedFragments) extensively, because [matryoshka bugs](http://delyan.me/android-s-matryoshka-problem/) can occur. Use nested fragments only when it makes sense (for instance, fragments in a horizontally-sliding ViewPager inside a screen-like fragment) or if it's a well-informed decision.
- Avoid putting too much code in Activities. Whenever possible, keep them as lightweight containers, existing in your application primarily for the lifecycle and other important Android-interfacing APIs. Prefer single-fragment activities instead of plain activities - put UI code into the activity's fragment. This makes it reusable in case you need to change it to reside in a tabbed layout, or in a multi-fragment tablet screen. Avoid having an activity without a corresponding fragment, unless you are making an informed decision.

### Java packages structure

### Resources

**Naming.** Follow the convention of prefixing the type. Examples: `fragment_advertisement_list.xml`, `activity_main.xml`.

**Organizing layout XMLs.** If you're unsure how to format a layout XML, the following convention may help.

- One attribute per line, indented by 4 spaces
- `android:id` as the first attribute always
- `android:layout_****` attributes at the top
- `style` attribute at the bottom
- Tag closer `/>` on its own line, to facilitate ordering and adding attributes.
- Rather than hard coding `android:text`, consider using [Designtime attributes](http://tools.android.com/tips/layout-designtime-attributes) available for Android Studio.

```xml
    <LinearLayout
        android:id="@+id/fragment_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
    </LinearLayout>

    <android.support.constraint.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="visible">

        <android.support.design.widget.BottomNavigationView
            android:id="@+id/navigation"
            android:layout_width="0dp"
            android:layout_height="51dp"
            android:layout_marginTop="8dp"
            android:layout_marginBottom="8dp"
            android:background="?android:attr/windowBackground"
            android:visibility="visible"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintHorizontal_bias="0.0"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintVertical_bias="1.0"
            app:menu="@menu/navigation" />
    </android.support.constraint.ConstraintLayout>
</android.support.constraint.ConstraintLayout>

### Data storage

#### SharedPreferences

### Use continuous integration

Continuous integration systems let you automatically build and test your project every time you push updates to version control. Continuous integration also runs static code analysis tools, generates the APK files and distributes them.

