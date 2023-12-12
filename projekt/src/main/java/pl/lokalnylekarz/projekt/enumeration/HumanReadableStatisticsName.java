package pl.lokalnylekarz.projekt.enumeration;

public enum HumanReadableStatisticsName {
    global("Łącznie wyszukiwań"),
    type("Typ placówki"),
    specialization("Specjalizacja lekarza"),
    distance("Maks. odległość"),
    nfzStatus("Status NFZ"),
    longitude("Długość geo."),
    latitude("Szerokość geo."),
    search("Nazwa placówki");

    private final String readable;

    HumanReadableStatisticsName(String readable) {
        this.readable = readable;
    }

    public String getReadable() {
        return readable;
    }
}
