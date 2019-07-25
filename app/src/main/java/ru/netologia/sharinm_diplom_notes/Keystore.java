package ru.netologia.sharinm_diplom_notes;

public interface Keystore {

    boolean hasPassword();

    boolean checkPassword(String password);

    void saveNewPassword(String password);
}
