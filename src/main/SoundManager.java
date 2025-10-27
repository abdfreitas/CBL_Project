package src.main;

import java.net.URL;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;


/** ADD COMMENT. */
public class SoundManager {

    private final URL[] urls = new URL[32];
    private Clip musicClip;                    // single music line
    private final Set<Clip> activeSfx = new HashSet<>(); // currently playing SFX
    private int maxSfx = 16;                   // limit concurrent SFX
    private float musicGainDb = 0.0f;          // overall music volume trim
    private float sfxGainDb = 0.0f;            // overall SFX volume trim

    /** ADD COMMENT. */
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

    /** Music. */
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

    /** ADD COMMENT. */
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

    /** ADD COMMENT. */
    public void setMusicGainDb(float db) { // e.g., -10f to lower
        musicGainDb = db;
        if (musicClip != null) {
            setGainSafe(musicClip, db);
        }
    }

    /** SFX. */
    public void playSfx(int index) {
        // Enforce simple limit so you don't exhaust lines
        if (activeSfx.size() >= maxSfx) {
            return;
        }

        try {
            final AudioInputStream ais = AudioSystem.getAudioInputStream(urls[index]);
            final Clip sfx = AudioSystem.getClip();
            sfx.open(ais);  // Clip keeps its own data; we can close stream now
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

    public void setSfxGainDb(float db) {
        sfxGainDb = db;
    }

    /** ADD COMMENT. */
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

    /** Utilities. */
    private static void setGainSafe(Clip clip, float db) {
        try {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(db);
        } catch (IllegalArgumentException ignored) {
            // Device/format may not support MASTER_GAIN
        }
    }

    public void setMaxConcurrentSfx(int max) {
        this.maxSfx = Math.max(1, max);
    }
}
