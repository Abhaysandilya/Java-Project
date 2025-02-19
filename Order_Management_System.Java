import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<Integer, Product> inventory = new HashMap<>();
    private static Queue<Order> orderQueue = new LinkedList<>();

    public static void main(String[] args) {
        // Adding initial products to the inventory
        inventory.put(1, new Product(1, "Laptop", 50000, 10));
        inventory.put(2, new Product(2, "Mobile", 30000, 5));
        inventory.put(3, new Product(3, "Tablet", 15000, 20));

        while (true) {
            System.out.println("\n-- E-commerce Order Management System --");
            System.out.println("1. Display Inventory");
            System.out.println("2. Add Product");
            System.out.println("3. Remove Product");
            System.out.println("4. Place an Order");
            System.out.println("5. Process Orders");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    displayInventory();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    removeProduct();
                    break;
                case 4:
                    placeOrder();
                    break;
                case 5:
                    processOrders();
                    break;
                case 6:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayInventory() {
        System.out.println("Inventory:");
        for (Product p : inventory.values()) {
            System.out.println("Product ID: " + p.productID + ", Name: " + p.name + ", Price: " + p.price + ", Stock: " + p.stock);
        }
    }

    private static void addProduct() {
        System.out.print("Enter Product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Product Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Product Stock: ");
        int stock = scanner.nextInt();

        inventory.put(id, new Product(id, name, price, stock));
        System.out.println("Product added successfully!");
    }

    private static void removeProduct() {
        System.out.print("Enter Product ID to remove: ");
        int id = scanner.nextInt();
        if (inventory.remove(id) != null) {
            System.out.println("Product removed successfully!");
        } else {
            System.out.println("Product not found!");
        }
    }

    private static void placeOrder() {
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter order date: ");
        String orderDate = scanner.nextLine();

        System.out.print("Enter number of products to order: ");
        int numProducts = 0;
        while (true) {
            if (scanner.hasNextInt()) {
                numProducts = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                break;
            } else {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next();  // Clear invalid input
            }
        }

        List<Product> orderedProducts = new ArrayList<>();
        double totalAmount = 0;

        for (int i = 0; i < numProducts; i++) {
            System.out.print("Enter product ID: ");
            int productID = scanner.nextInt();
            if (inventory.containsKey(productID) && inventory.get(productID).stock > 0) {
                Product product = inventory.get(productID);
                orderedProducts.add(product);
                totalAmount += product.price;
                product.stock--;
            } else {
                System.out.println("Product not found or out of stock.");
            }
        }

        if (!orderedProducts.isEmpty()) {
            orderQueue.add(new Order(orderQueue.size() + 1, customerName, orderDate, orderedProducts, totalAmount));
            System.out.println("Order placed successfully! Total Amount: " + totalAmount);
        }
    }

    private static void processOrders() {
        if (orderQueue.isEmpty()) {
            System.out.println("No orders to process.");
            return;
        }
        Order order = orderQueue.poll();
        System.out.println("Processing Order ID: " + order.orderID + " for " + order.customerName);
        System.out.println("Order Status: Shipped");
    }
}

class Product {
    int productID;
    String name;
    double price;
    int stock;

    public Product(int productID, String name, double price, int stock) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}

class Order {
    int orderID;
    String customerName;
    String orderDate;
    List<Product> products;
    double totalAmount;

    public Order(int orderID, String customerName, String orderDate, List<Product> products, double totalAmount) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.products = products;
        this.totalAmount = totalAmount;
    }
}
