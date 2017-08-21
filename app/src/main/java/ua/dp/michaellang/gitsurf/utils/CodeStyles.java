package ua.dp.michaellang.gitsurf.utils;

import com.protectsoft.webviewcode.Settings;

/**
 * Date: 15.07.2017
 *
 * @author Michael Lang
 */
public enum CodeStyles {
    DEFAULT(Settings.WithStyle.DEFAULT),
    AGATE(Settings.WithStyle.AGATE),
    ANDROID_STUDIO(Settings.WithStyle.ANDROIDSTUDIO),
    ARDUINO_LIGHT(Settings.WithStyle.ARDUINO_LIGHT),
    ARTA(Settings.WithStyle.ARTA),
    ASCETIC(Settings.WithStyle.ASCETIC),
    ATELIER_DARK(Settings.WithStyle.ATELIER_DARK),
    ATELIER_LIGHT(Settings.WithStyle.ATELIER_LIGHT),
    ATELIER_FOREST_DARK(Settings.WithStyle.ATELIER_FOREST_DARK),
    DARK_STYLE(Settings.WithStyle.DARKSTYLE),
    DARKULA(Settings.WithStyle.DARKULA),
    DOCCO(Settings.WithStyle.DOCCO),
    FAR(Settings.WithStyle.FAR),
    GITHUB(Settings.WithStyle.GITHUB),
    GITHUB_GIST(Settings.WithStyle.GITHUBGIST),
    GOOGLE_CODE(Settings.WithStyle.GOOGLECODE),
    IDEA(Settings.WithStyle.IDEA),
    MAGULA(Settings.WithStyle.MAGULA),
    OBSIDIAN(Settings.WithStyle.OBSIDIAN),
    XCODE(Settings.WithStyle.XCODE);

    private String value;

    CodeStyles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
