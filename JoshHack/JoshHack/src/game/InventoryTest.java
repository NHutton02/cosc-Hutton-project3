package game;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

public class InventoryTest {

    private Item createItem(String name) {
        return new Item('!', Color.RED, name, null);
    }

    @Test
    void constructor_createsInventoryWithRequestedCapacity() {
        Inventory inventory = new Inventory(3);

        assertEquals(3, inventory.getItems().length);
    }

    @Test
    void get_returnsItemAtRequestedIndex() {
        Inventory inventory = new Inventory(2);
        Item potion = createItem("Potion");

        inventory.add(potion);

        assertSame(potion, inventory.get(0));
    }

    @Test
    void add_emptyInventory_addsItemToFirstSlot() {
        Inventory inventory = new Inventory(3);
        Item potion = createItem("Potion");

        inventory.add(potion);

        assertSame(potion, inventory.get(0));
    }

    @Test
    void add_firstSlotOccupied_addsItemToNextAvailableSlot() {
        Inventory inventory = new Inventory(3);
        Item potion = createItem("Potion");
        Item sword = createItem("Sword");

        inventory.add(potion);
        inventory.add(sword);

        assertSame(potion, inventory.get(0));
        assertSame(sword, inventory.get(1));
    }

    @Test
    void add_fullInventory_doesNotAddExtraItem() {
        Inventory inventory = new Inventory(2);
        Item potion = createItem("Potion");
        Item sword = createItem("Sword");
        Item food = createItem("Food");

        inventory.add(potion);
        inventory.add(sword);
        inventory.add(food);

        assertSame(potion, inventory.get(0));
        assertSame(sword, inventory.get(1));
        assertFalse(inventory.contains(food));
    }

    @Test
    void remove_existingItem_removesItem() {
        Inventory inventory = new Inventory(2);
        Item potion = createItem("Potion");

        inventory.add(potion);
        inventory.remove(potion);

        assertNull(inventory.get(0));
        assertFalse(inventory.contains(potion));
    }

    @Test
    void remove_itemNotPresent_leavesInventoryUnchanged() {
        Inventory inventory = new Inventory(2);
        Item potion = createItem("Potion");
        Item sword = createItem("Sword");

        inventory.add(potion);
        inventory.remove(sword);

        assertSame(potion, inventory.get(0));
        assertFalse(inventory.contains(sword));
    }

    @Test
    void isFull_partiallyFilledInventory_returnsFalse() {
        Inventory inventory = new Inventory(2);
        inventory.add(createItem("Potion"));

        assertFalse(inventory.isFull());
    }

    @Test
    void isFull_allSlotsFilled_returnsTrue() {
        Inventory inventory = new Inventory(2);
        inventory.add(createItem("Potion"));
        inventory.add(createItem("Sword"));

        assertTrue(inventory.isFull());
    }

    @Test
    void contains_existingItem_returnsTrue() {
        Inventory inventory = new Inventory(2);
        Item potion = createItem("Potion");

        inventory.add(potion);

        assertTrue(inventory.contains(potion));
    }

    @Test
    void contains_missingItem_returnsFalse() {
        Inventory inventory = new Inventory(2);
        Item potion = createItem("Potion");
        Item sword = createItem("Sword");

        inventory.add(potion);

        assertFalse(inventory.contains(sword));
    }
}