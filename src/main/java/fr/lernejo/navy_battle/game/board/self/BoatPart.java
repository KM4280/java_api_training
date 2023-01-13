package fr.lernejo.navy_battle.game.board.self;

public class BoatPart {

    private final Boat boat;
    // contourner l'interdiction de champ final
    private final boolean[] valid = new boolean[] { true };

    public BoatPart(final Boat boat) {
        this.boat = boat;
    }

    public Boat getBoat() {
        return boat;
    }

    public boolean isValid() {
        return this.valid[0];
    }

    public void fire() {
        this.valid[0] = false;
    }
}
