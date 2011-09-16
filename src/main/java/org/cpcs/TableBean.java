package org.cpcs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component("tableBean")
public class TableBean implements Serializable {

  private final static String[] colors;

  private final static String[] manufacturers;

  static {
    colors = new String[10];
    colors[0] = "Black";
    colors[1] = "White";
    colors[2] = "Green";
    colors[3] = "Red";
    colors[4] = "Blue";
    colors[5] = "Orange";
    colors[6] = "Silver";
    colors[7] = "Yellow";
    colors[8] = "Brown";
    colors[9] = "Maroon";

    manufacturers = new String[10];
    manufacturers[0] = "Mercedes";
    manufacturers[1] = "BMW";
    manufacturers[2] = "Volvo";
    manufacturers[3] = "Audi";
    manufacturers[4] = "Renault";
    manufacturers[5] = "Opel";
    manufacturers[6] = "Volkswagen";
    manufacturers[7] = "Chrysler";
    manufacturers[8] = "Ferrari";
    manufacturers[9] = "Ford";
  }

  private List<String> cars;

  private String selectedCar;

  public TableBean() {
    cars = new ArrayList<String>();

    populateRandomCars(cars, 50);
  }

  public String getSelectedCar() {
    return selectedCar;
  }

  public void setSelectedCar(String selectedCar) {
    this.selectedCar = selectedCar;
  }

  private void populateRandomCars(List<String> list, int size) {
    Random rand = new Random(System.currentTimeMillis());

    for (int i = 0; i < size; i++)
      list.add(manufacturers[Math.abs(rand.nextInt() % 9)] + " " + colors[Math.abs(rand.nextInt() % 9)]);
  }

  public List<String> getCars() {
    return cars;
  }

}
