#-verbose
-dontobfuscate

#Team Code
-keep,includedescriptorclasses class org.pattonvillerobotics.** {
    *;
}

-dontwarn com.sun.tools.javac.model.**
-dontwarn org.pattonvillerobotics.commoncode.**
-dontwarn com.google.gson.**
-dontwarn com.vuforia.ar.pl.**
-dontwarn org.firstinspires.ftc.robotcore.**
-dontwarn com.qualcomm.analytics.**
-dontwarn sun.misc.Unsafe**
-dontwarn java.lang.ClassValue**
-dontwarn java.awt.geom.**