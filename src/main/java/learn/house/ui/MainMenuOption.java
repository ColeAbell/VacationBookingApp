package learn.house.ui;

public enum MainMenuOption {

    ADD_GUEST (1, "Add A Guest Profile"),

    UPDATE_GUEST (2, "Update Guest Profile"),

    VIEW_GUEST (3, "View A Guest's Profile"),

    DELETE_GUEST (4, "Delete A Guest Profile"),

    ADD_HOST (5, "Add A Host Profile"),

    UPDATE_HOST (6, "Update A Host Profile"),

    VIEW_HOST (7, "View A Host Profile / Reservations"),

    DELETE_HOST (8, "Delete A Host Profile"),

    MAKE_RESERVATION (9, "Make A Reservation"),

    UPDATE_RESERVATION (10, "Edit A Reservation"),

    DELETE_RESERVATION (11, "Cancel A Reservation"),

    EXIT (12, "Exit");

    private int value;
    private String message;

    private MainMenuOption(int value, String message){
        this.value = value;
        this.message = message;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : MainMenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }


}
