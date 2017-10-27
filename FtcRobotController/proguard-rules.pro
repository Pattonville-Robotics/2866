#-verbose
-dontobfuscate

#Robot Controller Code
-keep,includedescriptorclasses class org.firstinspires.ftc.robotcontroller.** {
    *;
}