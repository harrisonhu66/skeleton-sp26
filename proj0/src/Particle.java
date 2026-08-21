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
        lifespan = LIFESPANS.getOrDefault(flavor, -1);
    }

    public Color color() {
        if (flavor == ParticleFlavor.EMPTY) {
            return Color.BLACK;
        }
        if (flavor == ParticleFlavor.SAND) {
            return Color.YELLOW;
        }
        if (flavor == ParticleFlavor.BARRIER) {
            return Color.GRAY;
        }
        if (flavor == ParticleFlavor.WATER) {
            return Color.BLUE;
        }
        if (flavor == ParticleFlavor.FOUNTAIN) {
            return Color.CYAN;
        }
        if (flavor == ParticleFlavor.FLOWER) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FLOWER_LIFESPAN)) / FLOWER_LIFESPAN;
            int r = 120 + (int) Math.round((255 - 120) * ratio);
            int g = 70 + (int) Math.round((141 - 70) * ratio);
            int b = 80 + (int) Math.round((161 - 80) * ratio);
            return new Color(r, g, b);
        }
        if (flavor == ParticleFlavor.PLANT) {
            double ratio = (double) Math.max(0, Math.min(lifespan, PLANT_LIFESPAN)) / PLANT_LIFESPAN;
            int g = 120 + (int) Math.round((255 - 120) * ratio);
            return new Color(0, g, 0);
        }
        if (flavor == ParticleFlavor.FIRE) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FIRE_LIFESPAN)) / FIRE_LIFESPAN;
            int r = (int) Math.round(255 * ratio);
            return new Color(r, 0, 0);
        }
        return Color.GRAY;
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
        int i = StdRandom.uniformInt(10);
        if (i == 0) {
            var up = neighbors.get(Direction.UP);
            if (up.flavor == ParticleFlavor.EMPTY) {
                up.flavor = flavor;
                up.lifespan = LIFESPANS.get(flavor);
            }
        } else if (i == 1) {
            var left = neighbors.get(Direction.LEFT);
            if (left.flavor == ParticleFlavor.EMPTY) {
                left.flavor = flavor;
                left.lifespan = LIFESPANS.get(flavor);
            }
        } else if (i == 2) {
            var right = neighbors.get(Direction.RIGHT);
            if (right.flavor == ParticleFlavor.EMPTY) {
                right.flavor = flavor;
                right.lifespan = LIFESPANS.get(flavor);
            }
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
        for (var neighbor : neighbors.values()) {
            if (neighbor.flavor == ParticleFlavor.PLANT || neighbor.flavor == ParticleFlavor.FLOWER) {
                int chance = StdRandom.uniformInt(10);
                if (chance < 4) {
                    neighbor.flavor = ParticleFlavor.FIRE;
                    neighbor.lifespan = LIFESPANS.get(neighbor.flavor);
                }
            }
        }
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
        if (flavor == ParticleFlavor.PLANT || flavor == ParticleFlavor.FLOWER) {
            grow(neighbors);
        }
        if (flavor == ParticleFlavor.FIRE) {
            burn(neighbors);
        }
    }

    public void decrementLifespan() {
        if (lifespan > 0) {
            lifespan -= 1;
        }
        if (lifespan == 0) {
            flavor = ParticleFlavor.EMPTY;
            lifespan = -1;
        }
    }
}