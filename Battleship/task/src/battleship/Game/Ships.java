package battleship.Game;

enum Ships {
    AIRCRAFT("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    String name;
    int length;

    Ships(String name, int length) {
        this.name = name;
        this.length = length;
    }

    protected String getName() {
        return name;
    }

    public int getLengthShip() {
        return length;
    }
}
