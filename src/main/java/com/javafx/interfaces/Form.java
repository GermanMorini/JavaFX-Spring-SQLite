package com.javafx.interfaces;

// TODO: elegirle un mejor nombre, quiero decir que es rellenable, como un formulario
public interface Form<T> {
      void fillFields(T t);
      void clearFields();
      boolean allFieldsCompleted();

      T getInstance();
}
