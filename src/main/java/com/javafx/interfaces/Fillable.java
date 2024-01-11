package com.javafx.interfaces;

// TODO: elegirle un mejor nombre, quiero decir que es rellenable, como un formulario
public interface Fillable<T> {
      void fillEntries(T t);
      void clearEntries();
      boolean allFieldsCompleted();
}
