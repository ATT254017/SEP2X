package Model;

import java.util.ArrayList;

/**
 * Created by Afonso on 5/26/2017.
 */
public class ItemsList {
    private ArrayList<Item> itemsList;

    public ItemsList() {
        itemsList = new ArrayList<>();
    }

    public void addItem(Item item) {
        itemsList.add(item);
    }

    public void removeItem(Item item) {
        itemsList.remove(item);
    }

    public Item getItemByName(String itemName) {
        for (int i = 0; i < itemsList.size(); i++) {
            if (itemsList.get(i).getItemName().equals(itemName)) {
                return itemsList.get(i);
            }
        }
        return null;
    }
}
