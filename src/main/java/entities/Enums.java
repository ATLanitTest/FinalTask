package entities;

public enum Enums {
    PAGE_MAIN("главная_страница"),
    PAGE_TOPIC("страница_темы"),
    PAGE_SUBSCRIBES("страница_подписки");

    private String page;
    Enums(String page) {
        this.page = page;
    }

}
