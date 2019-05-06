package Audio;

import javax.sound.sampled.*;

public class AudioPlayer {

    private Clip clip;

    public AudioPlayer(String path) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));

            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,  baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat,ais);

            clip = AudioSystem.getClip();
            clip.open(dais);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    private void stop() {
        if(clip.isRunning())
            clip.stop();
    }

    public void close() {
        stop();
        clip.stop();
    }

}
