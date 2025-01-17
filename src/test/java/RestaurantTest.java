import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    private void setup() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        setup();
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        LocalTime time = LocalTime.parse("12:30:00");
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(time);
        assertTrue(mockedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        setup();
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        LocalTime time = LocalTime.parse("23:30:00");
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(time);
        assertFalse(mockedRestaurant.isRestaurantOpen());
    }

    @Test
    public void get_total_item_amount_should_return_sum_of_selected_item_prices() {
        setup();
        List<Item> selectedItems = new ArrayList<>();
        selectedItems.add(restaurant.findItemByName("Sweet corn soup"));
        selectedItems.add(restaurant.findItemByName("Vegetable lasagne"));
        assertEquals(388, restaurant.getTotalItemAmount(selectedItems));
    }

    @Test
    public void get_total_item_amount_should_return_zero_if_no_items_are_selected() {
        setup();
        List<Item> selectedItems = new ArrayList<>();
        assertEquals(0, restaurant.getTotalItemAmount(selectedItems));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        setup();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        setup();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        setup();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}