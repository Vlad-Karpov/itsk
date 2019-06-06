package dochebank;

import org.junit.Test;

/**
 * Reverse linked list.
 */
public class ItemNext {

    class Item {

        public int nm;
        public Item next = null;

        Item(int nm, Item next) {
            this.nm = nm;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "nm=" + nm +
                    '}';
        }
    }

    @Test
    public void reverseItemTest(){
        Item item5 = new Item(5, null);
        Item item4 = new Item(4, item5);
        Item item3 = new Item(3, item4);
        Item item2 = new Item(2, item3);
        Item item1 = new Item(1, item2);
        outLinkedList(item1);
        Item item = reverseItem(item1);
        outLinkedList(item);

        Item item6 = new Item(6, null);
        outLinkedList(item6);
        item = reverseItem(item6);
        outLinkedList(item);

    }

    public void outLinkedList(Item item) {
        Item current = item;
        if (current.next != null) {
            do {
                System.out.print(current);
                current = current.next;
            } while(current != null);
        } else {
            System.out.print(current);
        }
        System.out.println("");
    }

    public Item reverseItem(Item item){
        Item current = item;
        Item prev = null;
        Item next;
        if (current != null) {
            do {
                next = current.next;
                current.next = prev;
                prev = current;
                current = next;
            } while(current != null);
        }
        return prev;
    }

}
