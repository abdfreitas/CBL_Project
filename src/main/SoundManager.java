package src.main;

import java.net.URL;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

/*
 * Important to note: 
 * For this class, we make it known that this is not our original work. 
 * This class was very heavily referenced and coded using ChatGPT.
 * However, we attempted our best to look over the code and understand what is 
 * happening in order to learn something from it for future projects.
 */

/**
 * Handles all sound in the game, background music and sound effects.
 * 
 * Only one music track plays at a time, but multiple SFX can overlap.
 */
public class SoundManager {

    private final URL[] urls = new URL[32];              // list of sound file paths
    private Clip musicClip;                              // single music line
    private final Set<Clip> activeSfx = new HashSet<>(); // currently playing SFX
    private int maxSfx = 16;                             // limit concurrent SFX
    private float musicGainDb = 0.0f;                    // overall music volume trim
    private float sfxGainDb = 0.0f;                      // overall SFX volume trim

    /**
     * Loads all sound file locations into memory.
     * 
     * We only load the file URLs here, the actual audio data
     * is read later when we play a clip.
     */
    public SoundManager() {
        urls[0]  = getClass().getResource("/sound/Retro 8 bit slow wav.wav");
        urls[1]  = getClass().getResource("/sound/coin.wav");
        urls[2]  = getClass().getResource("/sound/powerup.wav");
        urls[3]  = getClass().getResource("/sound/unlock.wav");
        urls[4]  = getClass().getResource("/sound/fanfare.wav");
        urls[5]  = getClass().getResource("/sound/receivedamage.wav");
        urls[6]  = getClass().getResource("/sound/hitmonster.wav");
        urls[7]  = getClass().getResource("/sound/dooropen.wav");
        urls[8]  = getClass().getResource("/sound/music/carpenter_brut_-_roller_mobster.wav");
        urls[9]  = getClass().getResource("/sound/music/devils_meat_grinder_-_otxo_ost.wav");
        urls[10] = getClass().getResource("/sound/music/turbo_killer.wav");
        urls[11] = getClass().getResource("/sound/music/wire_-_otxo_ost.wav");
    }

    /**
     * Plays a music track from the loaded list.
     * 
     * Stops any currently playing music, opens the new one, and loops (if requested)
     *
     * @param index index of the track in the urls[] list
     * @param loop  true if the track should repeat forever
     */
    public void playMusic(int index, boolean loop) {
        stopMusic(); // stop & release current track
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(urls[index])) {
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);
            setGainSafe(musicClip, musicGainDb);
            musicClip.setFramePosition(0);
            
            if (loop) {
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            musicClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops and closes the currently playing music track.
     */
    public void stopMusic() {
        if (musicClip != null) {
            try {
                if (musicClip.isRunning()) {
                    musicClip.stop();
                } 
                musicClip.flush();
            } finally {
                musicClip.close();
                musicClip = null;
            }
        }
    }

    /**
     * Adjusts the current music volume.
     * 
     * @param db decibel value 
     */
    public void setMusicGainDb(float db) { // e.g., -10f to lower
        musicGainDb = db;
        if (musicClip != null) {
            setGainSafe(musicClip, db);
        }
    }

    /**
     * Plays a short sound effect.
     * 
     * Uses a separate Clip for each sound so multiple effects
     * can overlap without cutting each other off.
     * 
     * @param index index of the sound effect in the urls[] list
     */
    public void playSfx(int index) {
        // Enforce simple limit so you don't exhaust lines
        if (activeSfx.size() >= maxSfx) {
            return;
        }

        try {
            final AudioInputStream ais = AudioSystem.getAudioInputStream(urls[index]);
            final Clip sfx = AudioSystem.getClip();
            sfx.open(ais);  // Clip keeps its own data, so we can close stream now
            ais.close();

            setGainSafe(sfx, sfxGainDb);
            activeSfx.add(sfx);

            // Auto-cleanup when done
            sfx.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    sfx.stop();
                    sfx.flush();
                    sfx.close();
                    activeSfx.remove(sfx);
                }
            });

            sfx.setFramePosition(0);
            sfx.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the SFX volume for future sounds.
     * @param db new volume in decibels
     */
    public void setSfxGainDb(float db) {
        sfxGainDb = db;
    }

    /**
     * Stops all active sound effects currently playing.
     * (useful when switching scenes or pausing the game)
     */
    public void stopAllSfx() {
        for (Clip c : new ArrayList<>(activeSfx)) {
            try { 
                if (c.isRunning()) {
                    c.stop(); 
                }
            } finally { 
                c.close(); 
            }
        }
        activeSfx.clear();
    }

    /**
     * Tries to safely set the audio gain (volume) of a clip.
     * If the system doesn’t support it, the method just ignores it.
     */
    private static void setGainSafe(Clip clip, float db) {
        try {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(db);
        } catch (IllegalArgumentException ignored) {
            // Device/format may not support MASTER_GAIN
        }
    }

    /**
     * Sets the maximum number of sound effects that can play at once.
     * @param max number of allowed concurrent SFX 
     */
    public void setMaxConcurrentSfx(int max) {
        this.maxSfx = Math.max(1, max);
    }
}
