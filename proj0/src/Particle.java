import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;
import java.util.Map;

public class Particle {
    public ParticleFlavor flavor;
    public int lifespan;

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                   ParticleFlavor.PLANT, PLANT_LIFESPAN,
                   ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public Particle(ParticleFlavor flavor) {
        this.flavor = flavor;
        lifespan = -1;
    }

    public Color color() {
        var dispatch = Map.of(
            ParticleFlavor.EMPTY, Color.BLACK,
            ParticleFlavor.SAND, Color.YELLOW,
            ParticleFlavor.BARRIER, Color.GRAY,
            ParticleFlavor.WATER, Color.BLUE,
            ParticleFlavor.FOUNTAIN, Color.CYAN,
            ParticleFlavor.PLANT, new Color(0, 255, 0),
            ParticleFlavor.FIRE, new Color(255, 0, 0),
            ParticleFlavor.FLOWER, new Color(255, 141, 161)
        );
        return dispatch.getOrDefault(flavor, Color.GRAY);
    }

    public void moveInto(Particle other) {
        other.flavor = flavor;
        other.lifespan = lifespan;
        flavor = ParticleFlavor.EMPTY;
        lifespan = -1;
    }

    public void fall(Map<Direction, Particle> neighbors) {
        var down = neighbors.get(Direction.DOWN);
        if (down.flavor == ParticleFlavor.EMPTY) {
            moveInto(down);
        }
    }

    public void flow(Map<Direction, Particle> neighbors) {
        int i = StdRandom.uniformInt(3);
        if (i == 0) {

        } else if (i == 1) {
            var left = neighbors.get(Direction.LEFT);
            if (left.flavor == ParticleFlavor.EMPTY) {
                moveInto(left);
            }
        } else {
            var right = neighbors.get(Direction.RIGHT);
            if (right.flavor == ParticleFlavor.EMPTY) {
                moveInto(right);
            }
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
    }

    public void burn(Map<Direction, Particle> neighbors) {
    }

    public void action(Map<Direction, Particle> neighbors) {
        if (flavor == ParticleFlavor.EMPTY) {
            return;
        }
        if (flavor != ParticleFlavor.BARRIER) {
            fall(neighbors);
        }
        if (flavor == ParticleFlavor.WATER) {
            flow(neighbors);
        }
    }
}