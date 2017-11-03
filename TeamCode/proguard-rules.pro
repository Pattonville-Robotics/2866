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
-dontwarn com.qualcomm.analytics.**
-dontwarn org.apache.commons.math3.geometry.euclidean.twod.Line