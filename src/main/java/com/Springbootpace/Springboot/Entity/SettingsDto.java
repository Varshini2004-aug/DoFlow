package com.Springbootpace.Springboot.Entity;

public class SettingsDto {

    private boolean darkMode;
    private String fontSize;
    private boolean emailNotif;
    private boolean taskReminder;
    private boolean autoSave;
    private boolean focusMode;

    public SettingsDto() {}

    public boolean isDarkMode() { return darkMode; }
    public void setDarkMode(boolean darkMode) { this.darkMode = darkMode; }

    public String getFontSize() { return fontSize; }
    public void setFontSize(String fontSize) { this.fontSize = fontSize; }

    public boolean isEmailNotif() { return emailNotif; }
    public void setEmailNotif(boolean emailNotif) { this.emailNotif = emailNotif; }

    public boolean isTaskReminder() { return taskReminder; }
    public void setTaskReminder(boolean taskReminder) { this.taskReminder = taskReminder; }

    public boolean isAutoSave() { return autoSave; }
    public void setAutoSave(boolean autoSave) { this.autoSave = autoSave; }

    public boolean isFocusMode() { return focusMode; }
    public void setFocusMode(boolean focusMode) { this.focusMode = focusMode; }

    public static SettingsDto getOrDefault(Long id, SettingsDto defaultSettings) {
        return defaultSettings;  // You may replace this with actual logic later
    }
}
