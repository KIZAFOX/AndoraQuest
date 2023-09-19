package fr.kizafox.andora.tools.base64.heads;

public enum HeadUnit {

    WARRIOR("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTc5ZjIxZGU5MWJkMmI3MmY0ZmQ0NTJhZmJlODg4ZDAxMjJjMWQyZDM0YTRkMzg0N2Q1MzM4NzRiYTlmNDk0YiJ9fX0="),
    DWARF("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzk3YzE3MzJjYTBjY2QwOGM4NzcwNWE4NDEwNzkyN2ZiZjU5ZmFiNDVmODJhODg0MDA3MTE4OWZiNjFjZGI1NCJ9fX0="),
    ARCHER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTkzZWU1YmIwYzdmYWNjYTBmM2RmZTA5NDMwYzViODRhOTBlNjU4OGQwYTEwOTlkYTg1YjZlYWViODk1OGY5YSJ9fX0="),
    WIZARD("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JmYzE3ZWQ5MjhhZGZhZmZmYmY5ZjkxNTg5ZjBkNWI3YWIyMTZmNzRjMGQ3MjE0ZjI5ZTY5NDM4ZTYwOTdiMCJ9fX0=");

    private final String url;

    HeadUnit(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
