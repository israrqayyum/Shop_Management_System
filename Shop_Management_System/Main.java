import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

abstract class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public abstract void display();
}

class Electronics extends Product {
    private String brand;

    public Electronics(String name, double price, int quantity, String brand) {
        super(name, price, quantity);
        this.brand = brand;
    }

    @Override
    public void display() {
        System.out.println("Electronics: " + getName() + ", Brand: " + brand + ", Price: $" + getPrice() + ", Quantity: " + getQuantity());
    }
}

class Clothing extends Product {
    private String size;

    public Clothing(String name, double price, int quantity, String size) {
        super(name, price, quantity);
        this.size = size;
    }

    @Override
    public void display() {
        System.out.println("Clothing: " + getName() + ", Size: " + size + ", Price: $" + getPrice() + ", Quantity: " + getQuantity());
    }
}

class Books extends Product {
    private String author;

    public Books(String name, double price, int quantity, String author) {
        super(name, price, quantity);
        this.author = author;
    }

    @Override
    public void display() {
        System.out.println("Books: " + getName() + ", Author: " + author + ", Price: $" + getPrice() + ", Quantity: " + getQuantity());
    }
}

class Shop {
    private ArrayList<Product> items;

    public Shop() {
        items = new ArrayList<>();
    }

    public void addProduct(Product x) {
        items.add(x);
    }

    public void displayProducts() {
        for (Product item : items) {
            item.display();
        }
    }

    public void deleteProduct(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            System.out.println("---- Product deleted successfully ----");
        } else {
            System.out.println("---- Invalid product index ----");
        }
    }

    public int searchProduct(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Product item : items) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    public void saveRecordsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Product item : items) {
                writer.println(item.getClass().getSimpleName());
                writer.println("Name: " + item.getName());
                writer.println("Price: $" + item.getPrice());
                writer.println("Quantity: " + item.getQuantity());
                writer.println();
            }
            System.out.println("---- Records saved successfully ----");
        } catch (IOException e) {
            System.out.println("Failed to save records to file.");
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Shop sh = new Shop();
        int choice;

        do {
            System.out.println("|----------------------- Shop Management System Menu ------------------------|");
            System.out.println("|   1. Add Product                                                           |");
            System.out.println("|   2. Display Products                                                      |");
            System.out.println("|   3. Delete Product                                                        |");
            System.out.println("|   4. Search Product                                                        |");
            System.out.println("|   5. Calculate Total Price                                                 |");
            System.out.println("|   6. Save Records to File                                                  |");
            System.out.println("|   7. Exit                                                                  |");
            System.out.println("|____________________________________________________________________________|");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("|------- Add Product -------|");
                    System.out.println("| 1. Electronics            |");
                    System.out.println("| 2. Clothing               |");
                    System.out.println("| 3. Books                  |");
                    System.out.println("|___________________________|");
                    System.out.print("Enter product type: ");
                    int productType = sc.nextInt();

                    if (productType < 1 || productType > 3) {
                        System.out.println("------ Invalid Choice ------\n------ Enter Again ------");
                        break;
                    }

                    sc.nextLine();
                    System.out.print("Enter product name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter product price: ");
                    double price = sc.nextDouble();
                    System.out.print("Enter product quantity: ");
                    int quantity = sc.nextInt();
                    Product p;

                    switch (productType) {
                        case 1:
                            System.out.print("Enter brand: ");
                            sc.nextLine();
                            String brand = sc.nextLine();
                            p = new Electronics(name, price, quantity, brand);
                            break;
                        case 2:
                            System.out.print("Enter size: ");
                            sc.nextLine();
                            String size = sc.nextLine();
                            p = new Clothing(name, price, quantity, size);
                            break;
                        case 3:
                            System.out.print("Enter author: ");
                            sc.nextLine();
                            String author = sc.nextLine();
                            p = new Books(name, price, quantity, author);
                            break;
                        default:
                            System.out.println("Invalid product type.");
                            continue;
                    }

                    sh.addProduct(p);
                    System.out.println("Product added successfully.");
                    break;

                case 2:
                    System.out.println("------- Display Products -------");
                    sh.displayProducts();
                    break;

                case 3:
                    System.out.println("------- Delete Product -------");
                    System.out.print("Enter product name: ");
                    sc.nextLine();
                    String productName = sc.nextLine();
                    int productIndex = sh.searchProduct(productName);

                    if (productIndex != -1) {
                        sh.deleteProduct(productIndex);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case 4:
                    System.out.println("------- Search Product -------");
                    System.out.print("Enter product name: ");
                    sc.nextLine();
                    String searchProductName = sc.nextLine();
                    int searchProductIndex = sh.searchProduct(searchProductName);

                    if (searchProductIndex != -1) {
                        System.out.println("Product found at index: " + searchProductIndex);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case 5:
                    System.out.println("------- Calculate Total Price -------");
                    double totalPrice = sh.calculateTotalPrice();
                    System.out.println("Total Price: $" + totalPrice);
                    break;

                case 6:
                    String filename = "shop.txt";
                    System.out.println("------- Save Records to File -------");
                    sh.saveRecordsToFile(filename);
                    break;

                case 7:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 7);
    }
}