package control;

import javax.sound.sampled.*;

public class AudioManager {
    private Clip musica;
    private FloatControl controlVolumen;

    // Reproducir música en loop
    public void reproducirMusicaLoop(String ruta) {
        try {
            if (musica != null && musica.isRunning()) musica.stop();
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    getClass().getClassLoader().getResource(ruta));
            musica = AudioSystem.getClip();
            musica.open(audioStream);
            musica.loop(Clip.LOOP_CONTINUOUSLY);

            // Guardar control de volumen
            if (musica.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                controlVolumen = (FloatControl) musica.getControl(FloatControl.Type.MASTER_GAIN);
                setVolumen(0.5f); // volumen inicial 50%
            }

        } catch (Exception e) {
            System.out.println("Error al reproducir música: " + ruta + " (" + e.getMessage() + ")");
        }
    }

    // Reproducir efecto corto
    public void reproducirEfecto(String ruta) {
        try {
            Clip efecto = AudioSystem.getClip();
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    getClass().getClassLoader().getResource(ruta));
            efecto.open(audioStream);
            efecto.start();
        } catch (Exception e) {
            System.out.println("Error al reproducir efecto: " + ruta + " (" + e.getMessage() + ")");
        }
    }

    // Detener música de fondo
    public void detenerMusica() {
        if (musica != null && musica.isRunning()) musica.stop();
    }

    // Ajustar volumen (0.0f = silencio, 1.0f = máximo)
    public void setVolumen(float nivel) {
        if (controlVolumen != null) {
            // Volumen en decibelios (-80 = silencio, 6 = máximo)
            float volumenDB = (float) (Math.log10(Math.max(nivel, 0.01)) * 20.0);
            controlVolumen.setValue(volumenDB);
        }
    }
}
