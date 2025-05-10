package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * test class for character
 */
class CharacterTest {
    private Wizard wizard;
    private Warrior warrior;

    @BeforeEach
    void setUp() {
        wizard = new Wizard("Merlin");
        warrior = new Warrior("Thor");
    }

    /**
     * test for wizard character having correct starting values
     */
    @Test
    void testWizardInitialization() {
        assertEquals("Merlin", wizard.getName());
        assertEquals(15, wizard.getHealth());
        assertEquals(2, wizard.getStrength());
        assertEquals(5, wizard.getCraft());
        assertTrue(wizard.isAlive());
    }

    /**
     * test for warrior character having correct starting values
     */
    @Test
    void testWarriorInitialization() {
        assertEquals("Thor", warrior.getName());
        assertEquals(20, warrior.getHealth());
        assertEquals(5, warrior.getStrength());
        assertEquals(2, warrior.getCraft());
        assertTrue(warrior.isAlive());
    }

    /**
     * test character takes damage properly
     */
    @Test
    void testTakeDamage() {
        assertTrue(wizard.takeDamage(5));
        assertEquals(10, wizard.getHealth());
        assertTrue(wizard.isAlive());
    }

    /**
     * Test character dies when health < 0
     */
    @Test
    void testCharacterDeath() {
        assertFalse(wizard.takeDamage(20));
        assertEquals(0, wizard.getHealth());
        assertFalse(wizard.isAlive());
    }

    /**
     * test adding items to inventory
     */
    @Test
    void testAddItemToInventory() {
        Item axe = new Axe();
        wizard.addItem(axe);
        assertEquals(1, wizard.getInventory().size());
        assertTrue(wizard.getInventory().contains(axe));
    }

    /**
     * test holding items in hands
     */
    @Test
    void testEquipItems() {
        Item axe = new Axe();
        Item shield = new Shield();

        wizard.setLeftHand(axe);
        wizard.setRightHand(shield);

        assertEquals(axe, wizard.getLeftHand());
        assertEquals(shield, wizard.getRightHand());
    }

    /**
     * test strength calculations with equipped items
     */
    @Test
    void testTotalStrengthWithItems() {
        Item axe = new Axe();
        wizard.setLeftHand(axe);

        assertEquals(5, wizard.getTotalStrength());
    }

    /**
     * test total craft calculation with equipped items
     */
    @Test
    void testTotalCraftWithItems() {
        Item shield = new Shield();
        wizard.setRightHand(shield);

        assertEquals(6, wizard.getTotalCraft());
    }
}

/**
 * test class for item use
 */
class ItemTest {
    /**
     * test creating axe item
     */
    @Test
    void testAxeCreation() {
        Axe axe = new Axe();
        assertEquals("Axe", axe.getName());
        assertEquals(3, axe.getStrength());
        assertEquals(0, axe.getCraft());
    }

    /**
     * test creating shield item
     */
    @Test
    void testShieldCreation() {
        Shield shield = new Shield();
        assertEquals("Shield", shield.getName());
        assertEquals(1, shield.getStrength());
        assertEquals(1, shield.getCraft());
    }

    /**
     * test item toString method
     */
    @Test
    void testItemToString() {
        Item axe = new Axe();
        assertEquals("Axe (Strength: 3, Craft: 0)", axe.toString());
    }
}

/**
 * test class for monsters
 */
class MonsterTest {
    /**
     * test creating monster
     */
    @Test
    void testStrengthMonster() {
        Monster goblin = new Monster("Goblin", 3, 0, 5);
        assertTrue(goblin.usesStrength());
        assertEquals(3, goblin.getStrength());
        assertEquals(0, goblin.getCraft());
        assertEquals(5, goblin.getHealth());
    }

    /**
     * test creating monster
     */
    @Test
    void testCraftMonster() {
        Monster spider = new Monster("Spider", 0, 4, 6);
        assertFalse(spider.usesStrength());
        assertEquals(0, spider.getStrength());
        assertEquals(4, spider.getCraft());
        assertEquals(6, spider.getHealth());
    }

    /**
     * Test monster toString
     */
    @Test
    void testStrengthMonsterToString() {
        Monster goblin = new Monster("Goblin", 3, 0, 5);
        assertEquals("Goblin (Strength: 3, Health: 5)", goblin.toString());
    }

    /**
     * Test monster toString
     */
    @Test
    void testCraftMonsterToString() {
        Monster spider = new Monster("Spider", 0, 4, 6);
        assertEquals("Spider (Craft: 4, Health: 6)", spider.toString());
    }
}

/**
 * test class for chamber
 */
class ChamberTest {
    private Chamber chamber;

    @BeforeEach
    void setUp() {
        chamber = new Chamber();
    }

    /**
     * test creating empty chamber
     */
    @Test
    void testEmptyChamber() {
        assertTrue(chamber.getDoors().isEmpty());
        assertTrue(chamber.getItems().isEmpty());
    }

    /**
     * test creating chamber with an item
     */
    @Test
    void testChamberWithItem() {
        Item axe = new Axe();
        Chamber itemChamber = new Chamber(axe);
        assertEquals(1, itemChamber.getItems().size());
        assertTrue(itemChamber.getItems().contains(axe));
    }

    /**
     * test adding door to chamber
     */
    @Test
    void testAddDoor() {
        Chamber chamber2 = new Chamber();
        Door door = Door.connect(chamber, chamber2);

        assertTrue(chamber.getDoors().contains(door));
        assertEquals(1, chamber.getDoors().size());
    }

    /**
     * test removing item from chamber
     */
    @Test
    void testRemoveItem() {
        Item axe = new Axe();
        Chamber itemChamber = new Chamber(axe);

        assertTrue(itemChamber.removeItem(axe));
        assertFalse(itemChamber.getItems().contains(axe));
        assertTrue(itemChamber.getItems().isEmpty());
    }

    /**
     * test removing item that doesn't exist
     */
    @Test
    void testRemoveNonExistentItem() {
        Item axe = new Axe();
        assertFalse(chamber.removeItem(axe));
    }
}

/**
 * test class for door
 */
class DoorTest {
    private Chamber chamber1;
    private Chamber chamber2;

    @BeforeEach
    void setUp() {
        chamber1 = new Chamber();
        chamber2 = new Chamber();
    }

    /**
     * test creating an unguarded door
     */
    @Test
    void testUnguardedDoor() {
        Door door = Door.connect(chamber1, chamber2);
        assertFalse(door.isLocked());
        assertNull(door.getGuardian());
    }

    /**
     * test creating a guarded door
     */
    @Test
    void testGuardedDoor() {
        Monster goblin = new Monster("Goblin", 3, 0, 5);
        Door door = Door.connect(chamber1, chamber2, goblin);

        assertTrue(door.isLocked());
        assertEquals(goblin, door.getGuardian());
    }

    /**
     * test getting other chamber
     */
    @Test
    void testGetOtherChamber() {
        Door door = Door.connect(chamber1, chamber2);

        assertEquals(chamber2, door.getOtherChamber(chamber1));
        assertEquals(chamber1, door.getOtherChamber(chamber2));
    }

    /**
     * test getting other chamber with invalid chamber
     */
    @Test
    void testGetOtherChamberInvalid() {
        Door door = Door.connect(chamber1, chamber2);
        Chamber otherChamber = new Chamber();

        assertNull(door.getOtherChamber(otherChamber));
    }

    /**
     * test removing guardian unlocks door
     */
    @Test
    void testRemoveGuardian() {
        Monster goblin = new Monster("Goblin", 3, 0, 5);
        Door door = Door.connect(chamber1, chamber2, goblin);

        assertTrue(door.isLocked());
        door.removeGuardian();
        assertFalse(door.isLocked());
        assertNull(door.getGuardian());
    }

    /**
     * test door toString for guarded door
     */
    @Test
    void testGuardedDoorToString() {
        Monster goblin = new Monster("Goblin", 3, 0, 5);
        Door door = Door.connect(chamber1, chamber2, goblin);

        assertEquals("Door guarded by Goblin (Strength: 3, Health: 5)", door.toString());
    }

    /**
     * test door toString for unguarded door
     */
    @Test
    void testUnguardedDoorToString() {
        Door door = Door.connect(chamber1, chamber2);
        assertEquals("Unguarded door", door.toString());
    }
}

/**
 * test class for dungeon
 */
class DungeonTest {
    private Dungeon dungeon;
    private Chamber startChamber;
    private Chamber goalChamber;
    private Character player;

    @BeforeEach
    void setUp() {
        startChamber = new Chamber();
        goalChamber = new Chamber();
        player = new Wizard("Gandalf");
        dungeon = new Dungeon(player, startChamber, goalChamber);
    }

    /**
     * test dungeon initialization
     */
    @Test
    void testDungeonInitialization() {
        assertEquals(player, dungeon.getPlayer());
        assertEquals(startChamber, dungeon.getCurrentChamber());
        assertFalse(dungeon.isFinished());
    }

    /**
     * test moving to different chamber
     */
    @Test
    void testMoveToNewChamber() {
        Chamber newChamber = new Chamber();
        dungeon.setCurrentChamber(newChamber);
        assertEquals(newChamber, dungeon.getCurrentChamber());
    }

    /**
     * test game finishes when player reaches goal
     */
    @Test
    void testGameFinishAtGoal() {
        dungeon.setCurrentChamber(goalChamber);
        assertTrue(dungeon.isFinished());
    }

    /**
     * test game finishes when player dies
     */
    @Test
    void testGameFinishOnDeath() {
        player.takeDamage(100);
        assertTrue(dungeon.isFinished());
    }

    /**
     * test getting actions in empty chamber
     */
    @Test
    void testGetActionsEmptyChamber() {
        assertTrue(dungeon.getActions().isEmpty());
    }

    /**
     * test getting actions with unguarded door
     */
    @Test
    void testGetActionsWithUnguardedDoor() {
        Chamber otherChamber = new Chamber();
        Door.connect(startChamber, otherChamber);

        assertEquals(1, dungeon.getActions().size());
        assertTrue(dungeon.getActions().get(0) instanceof Move);
    }

    /**
     * test getting actions with guarded door
     */
    @Test
    void testGetActionsWithGuardedDoor() {
        Chamber otherChamber = new Chamber();
        Monster goblin = new Monster("Goblin", 3, 0, 5);
        Door.connect(startChamber, otherChamber, goblin);

        assertEquals(1, dungeon.getActions().size());
        assertTrue(dungeon.getActions().get(0) instanceof Fight);
    }

    /**
     * test getting actions with items in chamber
     */
    @Test
    void testGetActionsWithItems() {
        Item axe = new Axe();
        Chamber itemChamber = new Chamber(axe);
        dungeon.setCurrentChamber(itemChamber);

        assertEquals(1, dungeon.getActions().size());
        assertTrue(dungeon.getActions().get(0) instanceof Pick);
    }
}

/**
 * test class for action
 */
class ActionTest {
    private Dungeon dungeon;
    private Chamber chamber1;
    private Chamber chamber2;
    private Character player;

    @BeforeEach
    void setUp() {
        chamber1 = new Chamber();
        chamber2 = new Chamber();
        player = new Wizard("Gandalf");
        dungeon = new Dungeon(player, chamber1, chamber2);
    }

    /**
     * test move action
     */
    @Test
    void testMoveAction() {
        Door door = Door.connect(chamber1, chamber2);
        Move move = new Move(dungeon, door);

        assertEquals(chamber1, dungeon.getCurrentChamber());
        move.execute();
        assertEquals(chamber2, dungeon.getCurrentChamber());
    }

    /**
     * test Pick, adds item to inventory
     */
    @Test
    void testPickAction() {
        Item axe = new Axe();
        Chamber itemChamber = new Chamber(axe);
        dungeon.setCurrentChamber(itemChamber);

        Pick pick = new Pick(dungeon, axe);
        pick.execute();

        assertTrue(player.getInventory().contains(axe));
        assertFalse(itemChamber.getItems().contains(axe));
    }

    /**
     * test pick action, equips item in empty hand
     */
    @Test
    void testPickActionEquipsItem() {
        Item axe = new Axe();
        Chamber itemChamber = new Chamber(axe);
        dungeon.setCurrentChamber(itemChamber);

        Pick pick = new Pick(dungeon, axe);
        pick.execute();

        assertEquals(axe, player.getLeftHand());
    }

    /**
     * test fight action with successful attack
     */
    @Test
    void testFightActionSuccess() {
        Monster weakGoblin = new Monster("Goblin", 1, 0, 1);
        Door door = Door.connect(chamber1, chamber2, weakGoblin);
        Fight fight = new Fight(dungeon, weakGoblin, door);

        for (int i = 0; i < 20; i++) {
            fight.execute();
            if (!weakGoblin.isAlive()) {
                break;
            }
        }

        assertFalse(weakGoblin.isAlive());
        assertFalse(door.isLocked());
    }

    /**
     * test PrintError
     */
    @Test
    void testPrintErrorAction() {
        Exception error = new IllegalArgumentException("Test error");
        PrintError printError = new PrintError(dungeon, error);

        assertDoesNotThrow(() -> printError.execute());
        assertTrue(printError.toString().contains("Error"));
    }
}