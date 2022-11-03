package utility;


import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

/**
 * This class is used for storing default settings.
 */
public final class Settings {

    public static final double SCENE_WIDTH = 832;
    public static final double SCENE_HEIGHT = 832;
    public static final double SPRITE_SIZE = 64;

    public static final int AMOUNT_MAPS = 6;
    public static final float GRAVITY_CONSTANT = 5.f;

    public static final double BUBBLE_INIT_SPEED = 7;
    public static final double BUBBLE_FLY_SPEED = 3;
    public static final double BUBBLE_LIVE_TIME = 300;
    public static final double BUBBLE_FLY_TIME = 30;
    public static final double BUBBLE_POWERUP_FLY_TIME = 3 * BUBBLE_FLY_TIME;

    public static final double PLAYER_SPEED = 5.0;
    public static final int PLAYER_LIVES = 5;

    public static final int POINTS_PLAYER_DIE = -25;
    public static final int POINTS_KILL_MONSTER = 10;
    public static final int POINTS_LEVEL_COMPLETE = 30;

    public static final double MONSTER_SPEED = 3.5;

    public static final double JUMP_SPEED = 3 * PLAYER_SPEED;
    public static final double JUMP_SPEED_WALKER = 3 * MONSTER_SPEED;
    public static final double JUMP_HEIGHT_WALKER = 200;

    /**
     * Get the name of the player.
     * @param index Which player.
     * @return The name.
     */
    public static String getName(int index) {
        return names[index];
    }

    /**
     * Set the name of the player.
     * @param name The name to be set.
     * @param index The index of the current player.
     */
    public static void setName(String name, int index) {
        names[index] = name;
    }

    private static String[] names = new String[2];
    public static final String MUSIC_THEME_SONG = "music/themesong.mp3";
    public static final String MUSIC_BOSS_SONG = "music/boss.mp3";
    public static final String MUSIC_GAMEWON_SONG = "music/gamewon.mp3";
    public static final String MUSIC_GAMEOVER_SONG = "music/gameover.mp3";
    public static final String MUSIC_MENU_SONG = "music/menu.mp3";
    public static final double SPRITE_FINAL_ENEMY_SIZE = 256;

    private static String propertyFileName;
    private static String scoresFileName;
    private static Properties properties;
    private static Properties highscores;

    /**
     * The private constructor that does nothing.
     * This is a utility class.
     */
    private Settings() {

    }

   
    /**
     * Get a specific score.
     * @param name the name of the player of the highscore.
     * @return the score of the highscore.
     */
    public static String getHighscore(String name) {
        return getHighscoreValue(name);
    }


    /**
     * Get a specific value.
     * @param key the key of the property.
     * @return the value of the property.
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get a specific value of a highscore.
     * @param key the key of the property.
     * @return the value of the property.
     */
    public static String getHighscoreValue(String key) {
        return highscores.getProperty(key);
    }

 
    /**
     * Get the Set of property keys.
     * @return the Set of keys.
     */
    public static Set<String> keys() {
        return properties.stringPropertyNames();
    }

    /**
     * Get the Set of property keys for the highscore.
     * @return the Set of keys.
     */
    public static Set<String> highscoreKeys() {
        return highscores.stringPropertyNames();
    }

    /**
     * Get the property filename.
     * @return the filename
     */
    public static String getFileName() {
        return propertyFileName;
    }
}
