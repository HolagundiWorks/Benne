# Production R8 / ProGuard rules for Sarala Kannada
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Preserve BroadcastReceiver referenced in AndroidManifest.xml
-keep class com.example.notifications.StudyReminderReceiver { *; }

# Strip verbose debug logging in release builds for security & performance
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
}
