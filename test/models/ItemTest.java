package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.*;

//Výpis 40
public class ItemTest {

    //Testování modelu Item
    @Test
    public void testItemModel(){
        //play test helper pro fakeapp s inmemory db
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            @Override
            public void run() {
                String name = "TestItemName";
                String author = "TestItemAuthor";
                String genre = "TestItemGenre";
                String typeformat = "TestItemTypeFormat";
                int year = 2019;
                String textShort = "TestItemName";
                String textLong = "TestItemName";

                Item item = new Item(name, author, genre, typeformat, year, textShort, textLong);
                item.save();
                Item savedItem  = Item.find.byId(item.id);

                assertNotNull(savedItem);
                assertEquals(savedItem.name, name);
                assertEquals(savedItem.author, author);
                assertEquals(savedItem.genre, genre);
                assertEquals(savedItem.typeFormat, typeformat);
                assertEquals(savedItem.year, year);
                assertEquals(savedItem.textShort, textShort);
                assertEquals(savedItem.textLong, textLong);
            }
        });

    }
}
