import java.awt.image.BufferedImage;
import java.util.*;

class SimulationState {
    private final Map<Positionable, BufferedImage> modelsImages;

    SimulationState() {
        modelsImages = new HashMap<>();
    }

    <T extends Positionable> void importNewModel(T model, BufferedImage image) {
        modelsImages.put(model, image);
    }

    Map<Positionable, BufferedImage>  getState() {
        return modelsImages;
    }
}
