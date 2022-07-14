package learn.house.ui;

public enum MainMenuOption {

    ADD_GUEST (1, "Add A Guest Profile"),

    UPDATE_GUEST (2, "Update Guest Profile"),

    VIEW_GUEST (3, "View A Guest Profile"),

    ADD_HOST (4, "Add A Host Profile"),

    UPDATE_HOST (5, "Update A Host Profile"),

    VIEW_HOST (6, "View A Host Profile"),

    MAKE_RESERVATION (7, "Make A Reservation"),

    UPDATE_RESERVATION (8, "Edit A Reservation"),

    DELETE_RESERVATION (9, "Cancel A Reservation"),

    EXIT (10, "Exit");

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
