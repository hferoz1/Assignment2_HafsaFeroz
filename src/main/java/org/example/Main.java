package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class for character in the game
 */
abstract class Character {
    private String name;
    private int health;
    private int strength;
    private int craft;
    private Item leftHand;
    private Item rightHand;
    private List<Item> inventory;

    /**
     * character constryctor
     * @param name character's name
     * @param health base health value
     * @param strength base strength value
     * @param craft base craft value
     */
    public Character(String name, int health, int strength, int craft) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.craft = craft;
        this.inventory = new ArrayList<>();
    }

    /**
     * get character's name
     * @return character's name
     */
    public String getName() {
        return name;
    }

    /**
     * get current health value
     * @return current health value
     */
    public int getHealth() {
        return health;
    }

    /**
     * set health value
     * @param health new health value
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * get strength value
     * @return current strength value
     */
    public int getStrength() {
        return strength;
    }

    /**
     * get craft value
     * @return current craft value
     */
    public int getCraft() {
        return craft;
    }

    /**
     * get all items in inventory
     * @return list of inventory items
     */
    public List<Item> getInventory() {
        return inventory;
    }

    /**
     * add item to inventory
     * @param item item to add to inventory
     */
    public void addItem(Item item) {
        inventory.add(item);
    }

    /**
     * get item in left hand
     * @return item in left hand or null
     */
    public Item getLeftHand() {
        return leftHand;
    }

    /**
     * set item in left hand
     * @param item item held in left hand
     */
    public void setLeftHand(Item item) {
        this.leftHand = item;
    }

    /**
     * get item in right hand
     * @return item in right hand or null
     */
    public Item getRightHand() {
        return rightHand;
    }

    /**
     * set item in right hand
     * @param item item held in right hand
     */
    public void setRightHand(Item item) {
        this.rightHand = item;
    }

    /**
     * calculates total strength including items
     * @return total strength
     */
    public int getTotalStrength() {
        int totalStrength = strength;
        if (leftHand != null) {
            totalStrength += leftHand.getStrength();
        }
        if (rightHand != null) {
            totalStrength += rightHand.getStrength();
        }
        return totalStrength;
    }

    /**
     * calculates total craft including items
     * @return total craft
     */
    public int getTotalCraft() {
        int totalCraft = craft;
        if (leftHand != null) {
            totalCraft += leftHand.getCraft();
        }
        if (rightHand != null) {
            totalCraft += rightHand.getCraft();
        }
        return totalCraft;
    }

    /**
     * @param damage damage to take
     * @return true if still alive, otherwise false
     */
    public boolean takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
        return health > 0;
    }

    /**
     * check character is alive
     * @return true if alive, otherwise false
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * string of character data
     * @return string with character details including health, strength, and craft
     */
    @Override
    public String toString() {
        return name + " (Health: " + health + ", Strength: " + strength + ", Craft: " + craft + ")";
    }
}

/**
 * monster that guards doors and fights character
 */
class Monster extends Character {
    /**
     * create new monster
     * @param name monster's name
     * @param strength monster's strength
     * @param craft monster's craft
     * @param health monster's health value
     */
    public Monster(String name, int strength, int craft, int health) {
        super(name, health, strength, craft);
    }

    /**
     * check if monster uses strength or craft
     * @return true if uses strength, otherwise false if uses craft
     */
    public boolean usesStrength() {
        return getCraft() == 0;
    }

    @Override
    public String toString() {
        if (usesStrength()) {
            return getName() + " (Strength: " + getStrength() + ", Health: " + getHealth() + ")";
        } else {
            return getName() + " (Craft: " + getCraft() + ", Health: " + getHealth() + ")";
        }
    }
}

/**
 * wizard character with high craft
 */
class Wizard extends Character {
    /**
     * create a new wizard
     * @param name wizard's name
     */
    public Wizard(String name) {
        super(name, 15, 2, 5);
    }

    @Override
    public String toString() {
        return "Wizard " + super.toString();
    }
}

/**
 * warrior character with high strength
 */
class Warrior extends Character {
    /**
     * create new warrior
     * @param name warrior's name
     */
    public Warrior(String name) {
        super(name, 20, 5, 2);
    }

    @Override
    public String toString() {
        return "Warrior " + super.toString();
    }
}

/**
 * abstract class for items that can be picked up and equipped
 */
abstract class Item {
    private String name;
    private int strength;
    private int craft;

    /**
     * create new item
     * @param name item name
     * @param strength strength bonus
     * @param craft craft bonus
     */
    public Item(String name, int strength, int craft) {
        this.name = name;
        this.strength = strength;
        this.craft = craft;
    }

    /**
     * get item name
     * @return item name
     */
    public String getName() {
        return name;
    }

    /**
     * get strength bonus
     * @return strength value
     */
    public int getStrength() {
        return strength;
    }

    /**
     * get craft bonus
     * @return craft value
     */
    public int getCraft() {
        return craft;
    }

    @Override
    public String toString() {
        return name + " (Strength: " + strength + ", Craft: " + craft + ")";
    }
}

/**
 * axe item with strength bonus
 */
class Axe extends Item {
    /**
     * create new axe
     */
    public Axe() {
        super("Axe", 3, 0);
    }
}

/**
 * shield item gives defense
 */
class Shield extends Item {
    /**
     * create new shield
     */
    public Shield() {
        super("Shield", 1, 1);
    }
}

/**
 * chamber in dungeon
 */
class Chamber {
    private List<Door> doors;
    private List<Item> items;

    /**
     * create empty chamber
     */
    public Chamber() {
        this.doors = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    /**
     * create chamber with an item
     * @param item item to place in chamber
     */
    public Chamber(Item item) {
        this();
        if (item != null) {
            items.add(item);
        }
    }

    /**
     * get all doors connected to chamber
     * @return list of doors
     */
    public List<Door> getDoors() {
        return doors;
    }

    /**
     * get all items in chamber
     * @return list of items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * add door to chamber
     * @param door door to add
     */
    public void addDoor(Door door) {
        doors.add(door);
    }

    /**
     * remove item from chamber
     * @param item item to remove
     * @return true if item was removed, otherwise false
     */
    public boolean removeItem(Item item) {
        return items.remove(item);
    }
}

/**
 * door connects two chambers
 */
class Door {
    private Chamber chamber1;
    private Chamber chamber2;
    private Monster guardian;

    /**
     * create door between two chambers
     * @param chamber1 first chamber
     * @param chamber2 seconder chamber
     * @param guardian monster guarding the door
     */
    private Door(Chamber chamber1, Chamber chamber2, Monster guardian) {
        this.chamber1 = chamber1;
        this.chamber2 = chamber2;
        this.guardian = guardian;
        chamber1.addDoor(this);
        chamber2.addDoor(this);
    }

    /**
     * method to connect two chambers
     * @param chamber1 first chamber
     * @param chamber2 second chamber
     * @return created door
     */
    public static Door connect(Chamber chamber1, Chamber chamber2) {
        return new Door(chamber1, chamber2, null);
    }

    /**
     * method to connect two chambers with a guardian
     * @param chamber1 first chamber
     * @param chamber2 second chamber
     * @param guardian monster guarding the door
     * @return created door
     */
    public static Door connect(Chamber chamber1, Chamber chamber2, Monster guardian) {
        return new Door(chamber1, chamber2, guardian);
    }

    /**
     * get other chamber from given chamber
     * @param current current chamber
     * @return chamber on other side of door
     */
    public Chamber getOtherChamber(Chamber current) {
        if (current == chamber1) return chamber2;
        if (current == chamber2) return chamber1;
        return null;
    }

    /**
     * check if door is locked
     * @return true if locked, otherwise false
     */
    public boolean isLocked() {
        return guardian != null && guardian.isAlive();
    }

    /**
     * get monster guarding door
     * @return monster
     */
    public Monster getGuardian() {
        return guardian;
    }

    /**
     * remove monster (after defeat)
     */
    public void removeGuardian() {
        this.guardian = null;
    }

    /**
     * string info for door
     * @return string with door details
     */
    @Override
    public String toString() {
        if (isLocked()) {
            return "Door guarded by " + guardian;
        } else {
            return "Unguarded door";
        }
    }
}

/**
 * dungeon that contains chambers and player
 */
class Dungeon {
    private Character player;
    private Chamber currentChamber;
    private Chamber goalChamber;

    /**
     * create new dungeon
     * @param player player character
     * @param startChamber starting chamber
     * @param goalChamber goal chamber
     */
    public Dungeon(Character player, Chamber startChamber, Chamber goalChamber) {
        this.player = player;
        this.currentChamber = startChamber;
        this.goalChamber = goalChamber;
    }

    /**
     * get current chamber player is in
     * @return current chamber
     */
    public Chamber getCurrentChamber() {
        return currentChamber;
    }

    /**
     * get player character
     * @return player character
     */
    public Character getPlayer() {
        return player;
    }

    /**
     * set current chamber
     * @param chamber new current chamber
     */
    public void setCurrentChamber(Chamber chamber) {
        this.currentChamber = chamber;
    }

    /**
     * check if game is finished
     * @return true if game is finished, otherwise false
     */
    public boolean isFinished() {
        return !player.isAlive() || currentChamber == goalChamber;
    }

    /**
     * get all actions for the player
     * @return list of possible actions
     */
    public List<Action> getActions() {
        List<Action> actions = new ArrayList<>();
        for (Door door : currentChamber.getDoors()) {
            if (!door.isLocked()) {
                actions.add(new Move(this, door));
            } else {
                actions.add(new Fight(this, door.getGuardian(), door));
            }
        }
        for (Item item : currentChamber.getItems()) {
            actions.add(new Pick(this, item));
        }

        return actions;
    }
}

/**
 * abstract class for all actions a player can take
 */
abstract class Action {
    protected Dungeon dungeon;

    /**
     * create new action
     * @param dungeon dungeon action takes place in
     */
    public Action(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    /**
     * execute the action
     */
    public abstract void execute();

    @Override
    public abstract String toString();
}

/**
 * move through a door to another chamber
 */
class Move extends Action {
    private Door door;

    /**
     * create move action
     * @param dungeon dungeon action takes place in
     * @param door door to move through
     */
    public Move(Dungeon dungeon, Door door) {
        super(dungeon);
        this.door = door;
    }

    @Override
    public void execute() {
        Chamber nextChamber = door.getOtherChamber(dungeon.getCurrentChamber());
        dungeon.setCurrentChamber(nextChamber);
        System.out.println("You moved to a new chamber.");
    }

    @Override
    public String toString() {
        Chamber destination = door.getOtherChamber(dungeon.getCurrentChamber());
        return "Move through " + door + " to another chamber";
    }
}

/**
 * action to fight a monster
 */
class Fight extends Action {
    private Monster monster;
    private Door door;
    private Random dice = new Random();

    /**
     * create fight action
     * @param dungeon dungeon action takes place in
     * @param monster monster to fight
     * @param door door the monster is guarding
     */
    public Fight(Dungeon dungeon, Monster monster, Door door) {
        super(dungeon);
        this.monster = monster;
        this.door = door;
    }

    @Override
    public void execute() {
        Character player = dungeon.getPlayer();
        System.out.println("You are fighting " + monster);
        boolean useStrength = monster.usesStrength();

        int playerStat = useStrength ? player.getTotalStrength() : player.getTotalCraft();
        int monsterStat = useStrength ? monster.getStrength() : monster.getCraft();

        int playerRoll = dice.nextInt(6) + 1;
        int monsterRoll = dice.nextInt(6) + 1;

        int playerTotal = playerStat + playerRoll;
        int monsterTotal = monsterStat + monsterRoll;

        System.out.println("You rolled a " + playerRoll + " + " + playerStat + " = " + playerTotal);
        System.out.println("The " + monster.getName() + " rolled a " + monsterRoll + " + " + monsterStat + " = " + monsterTotal);

        if (playerTotal > monsterTotal) {
            int damage = playerTotal - monsterTotal;
            monster.takeDamage(damage);
            System.out.println("You hit the " + monster.getName() + " for " + damage + " damage!");

            if (!monster.isAlive()) {
                System.out.println("You defeated the " + monster.getName() + "!");
                door.removeGuardian();
            } else {
                System.out.println("The " + monster.getName() + " has " + monster.getHealth() + " health remaining.");
            }
        } else if (monsterTotal > playerTotal) {
            int damage = monsterTotal - playerTotal;
            player.takeDamage(damage);
            System.out.println("The " + monster.getName() + " hit you for " + damage + " damage!");

            if (!player.isAlive()) {
                System.out.println("You have been defeated!");
            } else {
                System.out.println("You have " + player.getHealth() + " health remaining.");
            }
        } else {
            System.out.println("You both missed!");
        }
    }

    @Override
    public String toString() {
        return "Fight " + monster;
    }
}

/**
 * action to pick up an item from chamber
 */
class Pick extends Action {
    private Item item;

    /**
     * create pick action
     * @param dungeon dungeon the action takes place in
     * @param item item to pick up
     */
    public Pick(Dungeon dungeon, Item item) {
        super(dungeon);
        this.item = item;
    }

    @Override
    public void execute() {
        Character player = dungeon.getPlayer();
        Chamber currentChamber = dungeon.getCurrentChamber();

        if (currentChamber.removeItem(item)) {
            player.addItem(item);
            System.out.println("You picked up " + item);

            if (player.getLeftHand() == null) {
                player.setLeftHand(item);
                System.out.println("You equipped " + item + " in your left hand.");
            } else if (player.getRightHand() == null) {
                player.setRightHand(item);
                System.out.println("You equipped " + item + " in your right hand.");
            } else {
                System.out.println("Both hands are full. Item added to inventory.");
            }
        } else {
            System.out.println("The item is no longer in the chamber.");
        }
    }

    @Override
    public String toString() {
        return "Pick up " + item;
    }
}

/**
 * error action for when the input is invalid
 */
class PrintError extends Action {
    private Exception error;

    /**
     * create error action
     * @param dungeon dungeon action takes place in
     * @param error exception that occurred
     */
    public PrintError(Dungeon dungeon, Exception error) {
        super(dungeon);
        this.error = error;
    }

    @Override
    public void execute() {
        System.out.println("Error: " + error.getMessage());
        System.out.println("Please enter a valid number from the options above.");
    }

    @Override
    public String toString() {
        return "Error: " + error.getMessage();
    }
}