package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TextUI {
    public void play(Dungeon d) {
        while (!d.isFinished()) {
            print(d);
            Action a = ask(d);
            a.execute();
        }
    }

    private void print(Dungeon d) {
        Chamber r = d.getCurrentChamber();
        StringBuilder s = new StringBuilder();

        s.append("You are in a chamber with " + r.getDoors().size() + " doors\n");
        s.append("There are " + r.getItems().size() + " items in the chamber\n");

        if (!r.getDoors().isEmpty()) {
            s.append("\nDoors:\n");
            for (Door door : r.getDoors()) {
                if (door.isLocked()) {
                    Monster monster = door.getGuardian();
                    s.append("  - Door guarded by: " + monster.getName());
                    if (monster.usesStrength()) {
                        s.append(" (Strength: " + monster.getStrength());
                    } else {
                        s.append(" (Craft: " + monster.getCraft());
                    }
                    s.append(", Health: " + monster.getHealth() + ")\n");
                } else {
                    s.append("  - Unguarded door\n");
                }
            }
        }

        if (!r.getItems().isEmpty()) {
            s.append("\nItems in this chamber:\n");
            for (Item item : r.getItems()) {
                s.append("  - " + item + "\n");
            }
        }

        Character player = d.getPlayer();
        s.append("\nPlayer status: " + player.getName() + " - Health: " + player.getHealth());
        s.append(", Strength: " + player.getTotalStrength());
        s.append(", Craft: " + player.getTotalCraft());

        if (player.getLeftHand() != null || player.getRightHand() != null) {
            s.append("\nEquipped: ");
            if (player.getLeftHand() != null) {
                s.append("Left hand: " + player.getLeftHand().getName() + " ");
            }
            if (player.getRightHand() != null) {
                s.append("Right hand: " + player.getRightHand().getName());
            }
        }

        System.out.println(s.toString());
    }

    private Action ask(Dungeon d) {
        StringBuilder s = new StringBuilder();
        s.append("\nHere are your options:\n");
        List<Action> actions = d.getActions();
        for (int i = 0; i < actions.size(); i++) {
            Action a = actions.get(i);
            s.append("\t" + i + ": " + a.toString() + "\n");
        }
        System.out.println(s.toString());

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        try {
            System.out.print("Enter your choice: ");
            String input = reader.readLine();
            int command = Integer.parseInt(input);

            if (command >= 0 && command < actions.size()) {
                return actions.get(command);
            } else {
                return new PrintError(d, new IllegalArgumentException("Invalid choice. Please enter a number between 0 and " + (actions.size() - 1)));
            }
        } catch (IOException e) {
            return new PrintError(d, e);
        } catch (NumberFormatException e) {
            return new PrintError(d, new IllegalArgumentException("Please enter a valid number"));
        }
    }
}