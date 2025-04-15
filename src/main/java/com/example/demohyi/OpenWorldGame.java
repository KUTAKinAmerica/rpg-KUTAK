package com.example.demohyi;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.HashSet;
import java.util.Set;

public class OpenWorldGame extends Application {
    // Хранение текущих нажатых клавиш для одновременной обработки ввода
    private Set<String> activeKeys;

    // Объект главного персонажа
    private Player player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        activeKeys = new HashSet<>();

        // Создание корневой группы и сцены
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);

        // Создаём главного героя – простой прямоугольник
        player = new Player(400, 300, 40, 40, Color.BLUE);
        root.getChildren().add(player.getShape());

        // Обработчики событий для нажатия и отпускания клавиш
        scene.setOnKeyPressed((KeyEvent event) -> {
            String code = event.getCode().toString();
            activeKeys.add(code);
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            String code = event.getCode().toString();
            activeKeys.remove(code);
        });

        primaryStage.setTitle("Open World Game - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Запускаем игровой цикл с помощью AnimationTimer
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdateTime = 0;

            @Override
            public void handle(long now) {
                if (lastUpdateTime > 0) {
                    // Вычисляем прошедшее время в секундах
                    double elapsedTime = (now - lastUpdateTime) / 1_000_000_000.0;
                    update(elapsedTime);
                }
                lastUpdateTime = now;
            }
        };
        gameLoop.start();
    }

    // Обновление состояния игры за прошедший интервал времени
    private void update(double elapsedTime) {
        // Задаём скорость персонажа (пикселей в секунду)
        double playerSpeed = 200;
        double dx = 0, dy = 0;

        // Обработка нажатых клавиш для движения
        if (activeKeys.contains("LEFT")) {
            dx -= playerSpeed * elapsedTime;
        }
        if (activeKeys.contains("RIGHT")) {
            dx += playerSpeed * elapsedTime;
        }
        if (activeKeys.contains("UP")) {
            dy -= playerSpeed * elapsedTime;
        }
        if (activeKeys.contains("DOWN")) {
            dy += playerSpeed * elapsedTime;
        }

        // Перемещаем игрока
        player.move(dx, dy);

        // Здесь можно добавить логику смены камеры, проверки границ мира и т.д.
    }
}

// Класс главного персонажа
class Player {
    private final Rectangle shape;

    // Конструктор: задаём позицию, размеры и цвет персонажа
    public Player(double x, double y, double width, double height, Color color) {
        shape = new Rectangle(width, height, color);
        shape.setTranslateX(x);
        shape.setTranslateY(y);
    }

    public Rectangle getShape() {
        return shape;
    }

    // Метод для перемещения персонажа
    public void move(double dx, double dy) {
        shape.setTranslateX(shape.getTranslateX() + dx);
        shape.setTranslateY(shape.getTranslateY() + dy);
    }
}