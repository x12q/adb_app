Look into adb proto
Some clues here

The official source:
The proto definitions are in AOSP:
- frameworks/base/core/proto/android/content/package.proto
- frameworks/base/core/proto/android/server/packagemanagerservice.proto

You can find them on AOSP's source browser: https://cs.android.com

Pre-built Java/Kotlin library:
Google publishes the compiled proto classes as part of the Android platform tools. The relevant artifact is:

implementation "com.android.tools.ddms:ddmlib:<version>"

But more directly, the proto definitions are compiled into:
implementation "com.google.android.tools:android-proto:<version>"

Practical problem though:
The dumpsys package --proto output uses internal Android proto schemas that aren't guaranteed stable across API versions, and the compiled classes aren't always published to Maven. Many tools that consume this
(like Android Studio) bundle the proto descriptors directly from AOSP source.

Most realistic approach:
1. Download the .proto files from AOSP source for your target Android version
2. Compile them yourself with protoc
3. Use the generated classes in your project

Alternatively — since you're building an ADB tool — adb shell appops get <package> or parsing dumpsys package text output might be more reliable across Android versions than relying on the proto schema staying
stable.