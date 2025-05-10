package org.example;

public class Game {
    public static void main(String[] args) {
        System.out.println("--------------------------------------------------");
        System.out.println("Welcome to the Dungeon Role-Playing Game!");
        System.out.println("Find your way through the dungeon to the goal chamber.");
        System.out.println("You must defeat monsters and pick up items along the way.");
        System.out.println("Good luck, brave adventurer!");
        System.out.println("--------------------------------------------------\n");

        Chamber[] chambers = new Chamber[]{
                new Chamber(),
                new Chamber(new Axe()),
                new Chamber(new Shield()),
                new Chamber(),
                new Chamber()
        };

        Door.connect(chambers[0], chambers[1]);
        Door.connect(chambers[1], chambers[2], new Monster("Goblin", 1, 0, 3));
        Door.connect(chambers[2], chambers[3], new Monster("Spider", 0, 3, 5));
        Door.connect(chambers[3], chambers[4]);

        Character player = new Wizard("Gandalf");

        Dungeon d = new Dungeon(player, chambers[0], chambers[4]);

        TextUI ui = new TextUI();
        ui.play(d);

        System.out.println("\n--------------------------------------------------");
        if (player.isAlive() && d.getCurrentChamber() == chambers[4]) {
            System.out.println("Congratulations! You've reached the goal chamber and won the game!");
        } else if (!player.isAlive()) {
            System.out.println("Game Over! Your character has been defeated.");
        } else {
            System.out.println("You have left the game before reaching the goal.");
        }

        System.out.println("Thank you for playing the Dungeon Role-Playing Game!");
        System.out.println("--------------------------------------------------");
    }
}
