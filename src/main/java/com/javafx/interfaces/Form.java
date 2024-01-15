package com.javafx.interfaces;

public interface Form<T> {
      void fillFields(T t);
      void clearFields();
      boolean allFieldsCompleted();

      T getInstance();
}
