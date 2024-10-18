import java.util.ArrayList;
import java.util.List;

// Модель товара
class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    public String getName() {
        return name;
    }
}

// Класс заказа
class Order {
    private List<Product> products = new ArrayList<>();
    private IPayment payment;
    private IDelivery delivery;

    public void addProduct(Product product) {
        products.add(product);
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getTotalPrice();
        }
        return total;
    }

    public void setPaymentMethod(IPayment payment) {
        this.payment = payment;
    }

    public void setDeliveryMethod(IDelivery delivery) {
        this.delivery = delivery;
    }

    public void processOrder() {
        double total = calculateTotalPrice();
        payment.processPayment(total);
        delivery.deliverOrder(this);
    }

    public List<Product> getProducts() {
        return products;
    }
}

// Интерфейс для различных способов оплаты
interface IPayment {
    void processPayment(double amount);
}

// Оплата кредитной картой
class CreditCardPayment implements IPayment {
    @Override
    public void processPayment(double amount) {
        System.out.println("Оплата " + amount + " обработана через кредитную карту.");
    }
}

// Оплата через PayPal
class PayPalPayment implements IPayment {
    @Override
    public void processPayment(double amount) {
        System.out.println("Оплата " + amount + " обработана через PayPal.");
    }
}

// Оплата банковским переводом
class BankTransferPayment implements IPayment {
    @Override
    public void processPayment(double amount) {
        System.out.println("Оплата " + amount + " обработана через банковский перевод.");
    }
}

// Интерфейс для доставки
interface IDelivery {
    void deliverOrder(Order order);
}

// Доставка курьером
class CourierDelivery implements IDelivery {
    @Override
    public void deliverOrder(Order order) {
        System.out.println("Заказ доставлен курьером.");
    }
}

// Доставка почтой
class PostDelivery implements IDelivery {
    @Override
    public void deliverOrder(Order order) {
        System.out.println("Заказ доставлен почтой.");
    }
}

// Самовывоз
class PickUpPointDelivery implements IDelivery {
    @Override
    public void deliverOrder(Order order) {
        System.out.println("Заказ готов к самовывозу в пункте выдачи.");
    }
}

// Интерфейс для уведомлений
interface INotification {
    void sendNotification(String message);
}

// Уведомление по email
class EmailNotification implements INotification {
    @Override
    public void sendNotification(String message) {
        System.out.println("Email отправлен: " + message);
    }
}

// Уведомление по SMS
class SmsNotification implements INotification {
    @Override
    public void sendNotification(String message) {
        System.out.println("SMS отправлено: " + message);
    }
}

// Интерфейс для расчета скидок
interface IDiscount {
    double applyDiscount(double price);
}

// Класс для расчета скидок
class DiscountCalculator {
    private IDiscount discount;

    public void setDiscountStrategy(IDiscount discount) {
        this.discount = discount;
    }

    public double calculateDiscountedPrice(double price) {
        return discount.applyDiscount(price);
    }
}

// Пример стратегии: скидка 10%
class TenPercentDiscount implements IDiscount {
    @Override
    public double applyDiscount(double price) {
        return price * 0.9;
    }
}

// Пример стратегии: скидка 5%
class FivePercentDiscount implements IDiscount {
    @Override
    public double applyDiscount(double price) {
        return price * 0.95;
    }
}

public class Main {
    public static void main(String[] args) {
        // Создаем заказ
        Order order = new Order();
        order.addProduct(new Product("Ноутбук", 1000.00, 1));
        order.addProduct(new Product("Мышь", 50.00, 2));

        // Устанавливаем способ оплаты
        order.setPaymentMethod(new CreditCardPayment());

        // Устанавливаем способ доставки
        order.setDeliveryMethod(new CourierDelivery());

        // Уведомление клиента
        INotification notification = new EmailNotification();
        notification.sendNotification("Ваш заказ обрабатывается.");

        // Расчет цены со скидкой
        DiscountCalculator discountCalculator = new DiscountCalculator();
        discountCalculator.setDiscountStrategy(new TenPercentDiscount());
        double discountedPrice = discountCalculator.calculateDiscountedPrice(order.calculateTotalPrice());
        System.out.println("Цена со скидкой: " + discountedPrice);

        // Обработка заказа
        order.processOrder();

        // Отправляем уведомление о завершении заказа
        notification.sendNotification("Ваш заказ обработан.");
    }
}
