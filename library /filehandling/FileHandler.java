package library.filehandling;

public interface FileHandler {
    String[] read();
    void writeAll(String[] data);
    default void append(String line) {}
}
