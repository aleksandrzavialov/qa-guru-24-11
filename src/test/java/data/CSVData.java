package data;

public enum CSVData {

    FIRSTROW(new String[]{"1", "Eldon Base for stackable storage shelf, platinum", "Muhammed MacIntyre", "3","-213.25", "38.94", "35", "Nunavut", "Storage & Organization", "0.8"});

    private final String[] content;

    CSVData(String[] content) {
        this.content = content;
    }

    public String[] getContent() {
        return content;
    }
}
