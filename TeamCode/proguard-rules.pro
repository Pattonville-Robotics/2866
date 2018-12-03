#-verbose
-dontobfuscate

#Keep annotations
-keepattributes *Annotation*

#Team Code
-keep,includedescriptorclasses class org.pattonvillerobotics.** {
    *;
}

-dontwarn com.sun.tools.**
-dontwarn com.google.common.**
-dontwarn com.google.gson.**
-dontwarn com.google.errorprone.**
-dontwarn com.qualcomm.analytics.**
-dontwarn com.qualcomm.wirelessp2p.**
-dontwarn com.osterhoutgroup.**
-dontwarn org.apache.commons.math3.geometry.euclidean.twod.Line
-dontwarn org.checkerframework.**
-dontwarn afu.org.checkerframework.**